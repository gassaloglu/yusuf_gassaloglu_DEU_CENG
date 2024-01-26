import {mapObject} from 'underscore';

import {checkAuth, checkProps} from '../checks.js';
import {asyncConnection, connection, STATIC_TIME} from "../index.js";

// id|flight_number|departure_airport|destination_airport|departure_time|
// arrival_time|gate_number|plane_registration|status|price

const FLIGHT_PROPS = {
	flight_number : "string",
	departure_airport : "string",
	destination_airport : "string",
	departure_time : "string",
	arrival_time : "string",
	gate_number : "string",
	plane_registration : "string",
	status : "string",
	price : "string",
}

export const getAllFlights =
    async (req, res) => {
	setStaticTime();
	let query = "SELECT * FROM flight WHERE status = 'scheduled'";
	connection.query(query, function(err, result) {
		if (err) {
			res.status(404).send("Server error");
			return;
		}

		res.status(200).json(result);
		return;
	});
}

export const getFlightsByFlightNumber =
    async (req, res) => {
	const flight_number = req.params.flight_number;

	const query =
	    'SELECT * FROM flight WHERE flight_number = ' + connection.escape(flight_number);
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

	if (typeof from !== "string" || typeof to !== "string" || typeof date !== "string") {
		res.status(400).send("Bad input");
		return;
	}

	const query = 'SELECT * FROM flight WHERE departure_airport = ' + connection.escape(from) +
		      ' AND destination_airport = ' + connection.escape(to) +
		      " AND departure_time BETWEEN '" + connection.escape(date).replace(/'/g, '') +
		      " 00:00:00' AND '" + connection.escape(date).replace(/'/g, '') +
		      " 23:59:59' AND status = 'scheduled'";
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
	const rquery = req.query;

	if (typeof rquery.from !== "string" || typeof rquery.to !== "string") {
		res.status(400).send("Bad input");
		return;
	}

	const query = "SELECT * FROM flight WHERE departure_time BETWEEN '" +
		      connection.escape(rquery.from).replace(/'/g, '') + " 00:00:00' AND '" +
		      connection.escape(rquery.to).replace(/'/g, '') +
		      " 23:59:59' AND status = 'scheduled'";

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
	const rquery = req.query;

	if (typeof rquery.id !== "string") {
		res.status(400).send("Bad input");
		return;
	}

	const query = 'SELECT * FROM flight WHERE flight_number = ' + connection.escape(rquery.id) +
		      " AND status = 'scheduled'";
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
			}

			result = result.map(e => e.seat);
			res.status(200).json(result);
			return;
		});
	});
}

export const getFlightByUniqueIdentifier =
    async (req, res) => {
	if (typeof req.body.unique_identifier !== "string") {
		res.status(400).send("Bad input");
		return;
	}
	const body = req.body;

	var parts = body.unique_identifier.split('_');
	var flight_number = parts[0];
	var departure_time = parts[1] + '%';

	const query =
	    'SELECT * FROM flight WHERE flight_number = ' + connection.escape(flight_number) +
	    ' AND departure_time LIKE ' + connection.escape(departure_time);
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("Not found");
			return;
		}

		res.status(200).json(result);
		return;
	});
}

export const getLuggageList =
    async (req, res) => {
	if (typeof req.body.flight_number !== "string") {
		res.status(400).send("Bad input");
		return;
	}

	if (!checkAuth(req.auth.permission, [ 'admin', 'ground_services' ])) {
		res.status(403).send("Forbidden");
		return;
	}

	const body = mapObject(req.body, f => asyncConnection.escape(f));

	const query =
	    'SELECT luggage_id, weight, piece FROM luggage WHERE luggage_id IN (SELECT luggage_id FROM passenger WHERE flight_number = ' +
	    body.flight_number + ') AND baggage_allowance != NULL OR piece != -1 OR weight != -1';

	asyncConnection.query(query)
	    .then(([ output ]) => res.status(200).send(output))
	    .catch(({x}) => res.status(404).send("Not Found"));
}

export const addFlight =
    async (req, res) => {
	if (!checkProps(req.body, FLIGHT_PROPS))
		return res.status(400).send();

	if (!checkAuth(req.auth.permission, [ "admin", "flight_planner" ])) {
		res.status(403).send("Forbidden");
		return;
	}

	const body = mapObject(req.body, f => asyncConnection.escape(f));

	// check the flight number whether it exists
	const flightNumberCheck =
	    'SELECT flight_number FROM flight WHERE flight_number = ' + body.flight_number;
	connection.query(flightNumberCheck, function(err, result) {
		if (err || result.length !== 0) {
			res.status(409).send("There is already a flight with flight number " +
					     body.flight_number);
			return;
		}

		// check the plane registration first whether it exists or not
		const planeQuery =
		    "SELECT * FROM plane WHERE plane_registration = " + body.plane_registration +
		    " AND is_active = 1;";
		connection.query(planeQuery, function(err1, result1) {
			if (err1 || result1.length === 0) {
				res.status(404).send("There is not such a plane " +
						     body.plane_registration + " or " +
						     body.plane_registration + " is not active");
				return;
			}

			var status =
			    setFlightStatus(req.body.departure_time, req.body.arrival_time);
			const flightAdding = 'INSERT INTO flight VALUES(DEFAULT,' +
					     body.flight_number + "," + body.departure_airport +
					     "," + body.destination_airport + "," +
					     body.departure_time + "," + body.arrival_time + "," +
					     body.gate_number + "," + body.plane_registration +
					     ",'" + status + "'," + body.price + ");";

			connection.query(flightAdding, function(err2, result2) {
				if (err2 || result2.length === 0) {
					res.status(409).send("Cannot added");
					return;
				}

				res.status(200).json(result2);
				return;
			});
		});
	});
}

export const getFlightTimeInfo =
    async (req, res) => {
	if (typeof req.body.plane_registration !== "string") {
		res.status(400).send("Bad input");
		return;
	}

	if (!checkAuth(req.auth.permission, [ "admin", "flight_planner", "ground_services" ])) {
		res.status(403).send("Forbidden");
		return;
	}

	const body = mapObject(req.body, f => asyncConnection.escape(f));

	const query =
	    'SELECT departure_time, arrival_time FROM flight WHERE plane_registration = ' +
	    body.plane_registration + ';';
	asyncConnection.query(query)
	    .then(([ output ]) => res.status(200).send(output))
	    .catch(() => res.status(404).send("Not Found"));
}

export const cancelFlight =
    async (req, res) => {
	const body = req.body;

	if (!checkAuth(req.auth.permission, [ 'admin', 'flight_planner' ])) {
		res.status(403).send("Forbidden");
		return;
	}

	if (typeof body.flight_number !== "string") {
		res.status(400).send("Bad input");
		return;
	}

	const query =
	    "SELECT id FROM flight WHERE flight_number = " + connection.escape(body.flight_number);
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send(`Flight number of ${body.flight_number} not found`);
			return;
		}

		const query = "UPDATE flight SET status = 'cancelled' WHERE flight_number = " +
			      connection.escape(body.flight_number);
		connection.query(query, function(err, result) {
			if (err) {
				res.status(500).send(`Server error`);
				return;
			}

			res.status(200).send("Updated");
		})
	});
}

function setFlightStatus(departure_time, arrival_time) {
	const staticDate = new Date(STATIC_TIME);
	const departureDate = new Date(departure_time);
	const arrivalDate = new Date(arrival_time);

	var status = "";

	if (arrivalDate < staticDate)
		status = 'completed';
	else if (departureDate <= staticDate && arrivalDate >= staticDate)
		status = 'onflight';
	else if (departureDate > staticDate)
		status = 'scheduled';

	return status;
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