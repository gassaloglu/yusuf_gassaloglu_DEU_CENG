import {connection} from "../index.js"

export const getAllPassengers =
    async (req, res) => {
	if (req.auth.permission !== "admin" && req.auth.permission !== "passenger_services") {
		res.status(403).send("Forbidden");
		return;
	}

	const query = 'SELECT * FROM passenger';
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("No passenger found");
			return;
		}

		res.status(200).json(result);
		return;
	})
}

export const getPassengerById =
    async (req, res) => {
	const rquery = req.query;

	if (typeof rquery.id !== "string") {
		res.status(400).send("Bad query");
		return;
	}

	if (req.auth.permission !== "admin" && req.auth.permission !== "passenger_services") {
		res.status(403).send("Forbidden");
		return;
	}

	const query = 'SELECT * FROM passenger WHERE national_id = ' + connection.escape(rquery.id);
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("No passenger found");
			return;
		}

		res.status(200).json(result[0]);
		return;
	})
}

export const getPassengerByPnr =
    async (req, res) => {
	const rquery = req.query;

	if (typeof rquery.pnr !== "string" || typeof rquery.surname !== "string") {
		res.status(400).send("Bad query");
		return;
	}

	if (req.auth.permission !== "admin" && req.auth.permission !== "passenger_services") {
		res.status(403).send("Forbidden");
		return;
	}

	const query = 'SELECT * FROM passenger WHERE pnr_no = ' + connection.escape(rquery.pnr) +
		      ' AND surname = ' + connection.escape(rquery.surname);
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("No passenger found");
			return;
		}

		res.status(200).json(result[0]);
		return;
	})
}

export const employeeCheckIn =
    async (req, res) => {
	const body = req.body;

	if (typeof body.pnr !== "string" || typeof body.piece !== "string" ||
	    typeof body.weight !== "string") {
		res.status(400).send("Bad credentials");
		return;
	}

	if (req.auth.permission !== "admin" && req.auth.permission !== "passenger_services") {
		res.status(403).send("Forbidden");
		return;
	}

	const passenger_existence_query =
	    'SELECT luggage_id FROM passenger where pnr_no = ' + connection.escape(body.pnr);

	connection.query(passenger_existence_query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("No passenger found");
			return;
		}

		const LUGGAGE_ID = result[0].luggage_id;

		const update_luggage_query =
		    "UPDATE luggage SET weight = " + connection.escape(body.weight) +
		    " , piece = " + connection.escape(body.piece) +
		    " WHERE luggage_id = " + LUGGAGE_ID;

		connection.query(update_luggage_query, function(err, result) {
			if (err || result.length === 0) {
				res.status(404).send("Luggage Insertion Error");
				return;
			}

			const update_luggage_query =
			    "UPDATE passenger SET check_in = 1 WHERE pnr_no = " +
			    connection.escape(body.pnr);

			connection.query(update_luggage_query, function(err, result) {
				if (err || result.length === 0) {
					res.status(404).send("Checkin Error");
					return;
				}

				res.status(200).send();
				return;
			})
		})
	})
}

export const checkIn =
    async (req, res) => {
	const body = req.body;

	if (typeof body.pnr !== "string") {
		res.status(400).send("Bad credentials");
		return;
	}
	const passenger_existence_query =
	    'SELECT luggage_id FROM passenger where pnr_no = ' + connection.escape(body.pnr);

	connection.query(passenger_existence_query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("No passenger found");
			return;
		}

		const LUGGAGE_ID = result[0].luggage_id;

		const update_luggage_query =
		    "UPDATE luggage SET weight = 0 , piece = 0  WHERE luggage_id = " + LUGGAGE_ID;

		connection.query(update_luggage_query, function(err, result) {
			if (err || result.length === 0) {
				res.status(404).send("Luggage Insertion Error");
				return;
			}

			const update_luggage_query =
			    "UPDATE passenger SET check_in = 1 WHERE pnr_no = " +
			    connection.escape(body.pnr);

			connection.query(update_luggage_query, function(err, result) {
				if (err || result.length === 0) {
					res.status(404).send("Checkin Error");
					return;
				}

				res.status(200).send();
				return;
			})
		})
	})
}

export const removePassenger =
    async (req, res) => {
	const body = req.body;
	if (typeof body.pnr_no !== "string" || typeof body.surname !== "string") {
		res.status(400).send("Bad credentials");
		return;
	}

	const query = 'SELECT * FROM passenger WHERE pnr_no = ' + connection.escape(body.pnr_no) +
		      " AND surname = " + connection.escape(body.surname);
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("Wrong PNR or surname");
			return;
		}

		var pres = result[0];
		if (refundMoney(pres.buyer, pres.flight_number, pres.fare_type) === null) {
			res.status(404).send("Could not refund");
			return;
		}

		const query = 'DELETE FROM passenger WHERE national_id = ' +
			      connection.escape(pres.national_id);
		connection.query(query, function(err, result) {
			if (err) {
				res.status(500).send("Server error");
				return;
			}

			const query = 'DELETE FROM luggage WHERE luggage_id = ' +
				      connection.escape(pres.luggage_id);
			connection.query(query, function(err, result) {
				if (err || result.length === 0) {
					res.status(500).send("Server error");
					return;
				}

				res.status(200).json(pres);
				return;
			});
		});
	});
}

function refundMoney(user_id, flight_number, fare_type) {
	const query = "SELECT price FROM flight WHERE flight_number = '" + flight_number + "';";
	connection.query(query, function(err, result) {
		if (err || result.length === 0 || result[0] === 0)
			return null;

		let refundVal = parseFloat(result[0].price);
		fare_type = fare_type.toLowerCase();
		if (fare_type == "advantage")
			refundVal *= 1.2;
		else if (fare_type == "comfort")
			refundVal *= (1.2 * 1.2);

		const query1 =
		    'UPDATE user SET money = money + ' + refundVal + ' WHERE id = ' + user_id;
		connection.query(query1, function(err, result) {
			if (err) {
				return null;
			} else {
				return result[0];
			}
		});
	});
}

function checkIfExists(table_name, attribute) {
	// will be continued...
}

export const getAllDistinctPersons =
    async (req, res) => {
	const {token, flight_number, distinctive} = req.params;

	if (typeof flight_number !== "string" || typeof distinctive !== "string") {
		res.status(400).send("Bad credentials");
		return;
	}

	if (req.auth.permission !== "admin" && req.auth.permission !== "passenger_services" &&
	    req.auth.permission !== "ground_services") {
		res.status(403).send("Forbidden");
		return;
	}

	let query = "";
	if (distinctive === "disabled") {
		query = 'SELECT * FROM passenger WHERE flight_number = ' +
			connection.escape(flight_number) + " AND disabled = '1'";
	} else if (distinctive === "cip_member") {
		query = 'SELECT * FROM passenger WHERE flight_number = ' +
			connection.escape(flight_number) + " AND cip_member = '1'";
	} else if (distinctive === "child") {
		query = 'SELECT * FROM passenger WHERE flight_number = ' +
			connection.escape(flight_number) + " AND child = '1'";
	} else {
		res.status(404).send("Unknown distinctive");
		return;
	}

	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("Distinct persons are not found");
			return;
		}

		res.status(200).json(result);
		return;
	});
}

export const getMealAll =
    async (req, res) => {
	if (req.auth.permission !== "admin" && req.auth.permission !== "passenger_services" &&
	    req.auth.permission !== "ground_services") {
		res.status(403).send("Forbidden");
		return;
	}

	const query = "SELECT * FROM passenger WHERE meal = 1;";
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("No meal found");
			return;
		}

		res.status(200).json(result.map((r) => {
			let {seat, name, surname, flight_number} = r;
			return {seat, name, surname, flight_number};
		}));
		return;
	});
}

export const getMealForFlight = async (req, res) => {
	const {flight_number} = req.params;

	if (req.auth.permission !== "admin" && req.auth.permission !== "passenger_services" &&
	    req.auth.permission !== "ground_services") {
		res.status(403).send("Forbidden");
		return;
	}

	const query = "SELECT * FROM passenger WHERE meal = 1 AND flight_number = " +
		      connection.escape(flight_number) + ";";
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("No meal found");
			return;
		}

		res.status(200).json(result.map((r) => {
			let {seat, name, surname} = r;
			return {seat, name, surname};
		}));
		return;
	});
}