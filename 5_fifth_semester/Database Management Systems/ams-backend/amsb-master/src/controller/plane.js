import {connection} from "../index.js"

export const getAllPlanes =
    async (req, res) => {

	const query = 'SELECT * FROM plane';

	if (req.auth.permission !== "admin" && req.auth.permission !== "flight_planner") {
		res.status(403).send("Forbidden");
		return;
	}

	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("No plane found");
			return;
		} else {
			res.status(200).json(result);
		}
	})
}

export const getPlaneByRegistration =
    async (req, res) => {
	const {registration} = req.params;

	if (typeof registration !== "string") {
		console.log(typeof registration)
		res.status(400).send("Bad query");
		return;
	}

	if (req.auth.permission !== "admin" && req.auth.permission !== "flight_planner") {
		res.status(403).send("Forbidden");
		return;
	}

	const query =
	    'SELECT * FROM plane WHERE plane_registration = ' + connection.escape(registration);
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("No plane found");
			return;
		} else {
			res.status(200).json(result[0]);
		}
	})
}

export const addPlane =
    async (req, res) => {
	const body = req.body;

	if (typeof body.registration !== "string" || typeof body.model !== "string") {
		res.status(400).send("Bad credentials");
		return;
	}

	if (req.auth.permission !== "admin") {
		res.status(403).send("Forbidden");
		return;
	}

	let values = " VALUES (" + connection.escape(body.registration) + "," +
		     connection.escape(body.model) + "," +
		     "'ADB','270','1')"
	let insertion_query =
	    "INSERT INTO plane (plane_registration,model,location,max_passengers,is_active)" +
	    values;
	connection.query(insertion_query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("Insertion error")
			return;
		} else {
			res.status(200).send();
		}
	})
}

export const status = async (req, res) => {
	const body = req.body;

	if (typeof body.status !== "string" || typeof body.registration !== "string") {
		res.status(400).send("Bad credentials");
		return;
	}

	if (req.auth.permission !== "admin") {
		res.status(403).send("Forbidden");
		return;
	}

	const update_query = "UPDATE plane SET is_active = " + connection.escape(body.status) +
			     " WHERE plane_registration = " + connection.escape(body.registration);
	connection.query(update_query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("Update error")
			return;
		} else {
			res.status(200).send();
		}
	})
}