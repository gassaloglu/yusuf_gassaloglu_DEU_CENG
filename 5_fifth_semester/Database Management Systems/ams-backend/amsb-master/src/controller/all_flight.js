import {asyncConnection, connection, STATIC_TIME} from "../index.js";

export const getAllFlights =
    async (req, res) => {
	if (req.auth.permission !== "admin" && req.auth.permission !== "flight_planner" &&
	    req.auth.permission !== "passenger_services") {
		res.status(403).send("Forbidden");
		return;
	}

	setStaticTime();

	let query = "SELECT * FROM flight";
	connection.query(query, function(err, result) {
		if (err)
			res.status(404).send("Server error");
		else
			res.status(200).json(result);
	});
}

export const getFlightsByFlightNumber =
    async (req, res) => {
	const id = req.params.id;

	if (req.auth.permission !== "admin" && req.auth.permission !== "flight_planner" &&
	    req.auth.permission !== "passenger_services") {
		res.status(403).send("Forbidden");
		return;
	}
	const query = 'SELECT * FROM flight WHERE flight_number = ' + connection.escape(id);
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("Not found");
			return;
		}

		res.status(200).json(result);
		return;
	});
}

export const getFlightsByDestinationAndDate =
    async (req, res) => {
	const {from, to, date} = req.params;

	if (req.auth.permission !== "admin" && req.auth.permission !== "flight_planner" &&
	    req.auth.permission !== "passenger_services") {
		res.status(403).send("Forbidden");
		return;
	}

	if (typeof from !== "string" || typeof to !== "string" || typeof date !== "string") {
		res.status(400).send("Bad input");
		return;
	}

	const query = 'SELECT * FROM flight WHERE departure_airport = ' + connection.escape(from) +
		      ' AND destination_airport = ' + connection.escape(to) +
		      " AND departure_time BETWEEN '" + connection.escape(date).replace(/'/g, '') +
		      " 00:00:00' AND '" + connection.escape(date).replace(/'/g, '') + " 23:59:59'";
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("Not found");
			return;
		}

		res.status(200).json(result);
		return;
	});
}

export const getFlightsBetweenDates =
    async (req, res) => {
	const body = req.body;
	const rquery = req.query;

	if (req.auth.permission !== "admin" && req.auth.permission !== "flight_planner" &&
	    req.auth.permission !== "passenger_services") {
		res.status(403).send("Forbidden");
		return;
	}

	if (typeof rquery.from !== "string" || typeof rquery.to !== "string") {
		res.status(400).send("Bad input");
		return;
	}

	const query = "SELECT * FROM flight WHERE departure_time BETWEEN '" +
		      connection.escape(rquery.from).replace(/'/g, '') + " 00:00:00' AND '" +
		      connection.escape(rquery.to).replace(/'/g, '') + " 23:59:59'";
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("Not found");
			return;
		}

		res.status(200).json(result);
		return;
	});
}

export const getEmptySeats =
    async (req, res) => {
	const body = req.body;
	const rquery = req.query;

	if (req.auth.permission !== "admin" && req.auth.permission !== "flight_planner" &&
	    req.auth.permission !== "passenger_services") {
		res.status(403).send("Forbidden");
		return;
	}

	if (typeof rquery.id !== "string") {
		res.status(400).send("Bad input");
		return;
	}

	const query = 'SELECT * FROM flight WHERE flight_number = ' + connection.escape(rquery.id);
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("Flight not found");
			return;
		}

		const query = "SELECT seat FROM passenger WHERE flight_number =" +
			      connection.escape(rquery.id);
		connection.query(query, function(err, result) {
			if (err) {
				res.status(500).send("Server error.");
				return;
			} else {
				result = result.map(e => e.seat);
				res.status(200).json(result);
				return;
			}
		});
	});
}

function setStaticTime() { // set the flight statuses based on the static time of the system
	const query1 = "UPDATE flight SET status = 'completed'  WHERE arrival_time < " +
		       asyncConnection.escape(STATIC_TIME) + " AND status != 'cancelled'";
	const query2 = "UPDATE flight SET status = 'onflight'  WHERE departure_time <= " +
		       asyncConnection.escape(STATIC_TIME) +
		       " AND arrival_time >= " + asyncConnection.escape(STATIC_TIME) +
		       " AND status != 'cancelled'";
	const query3 = "UPDATE flight SET status = 'scheduled' WHERE departure_time > " +
		       asyncConnection.escape(STATIC_TIME) + " AND status != 'cancelled'";

	Promise
	    .all([
		    asyncConnection.query(query1), asyncConnection.query(query2),
		    asyncConnection.query(query3)
	    ])
	    .then(([ result1, result2, result3 ]) => {console.log})
	    .catch(console.log);
}