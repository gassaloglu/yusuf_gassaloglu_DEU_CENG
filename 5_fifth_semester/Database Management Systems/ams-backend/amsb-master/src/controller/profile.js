import {connection} from "../index.js";

export const getProfile =
    async (req, res) => {
	const body = req.body;

	if (body.isEmployee === true) {
		if (typeof body.token !== "string") {
			res.status(400).send("Bad credentials");
			return;
		}

		const query =
		    'SELECT * FROM personnel WHERE national_id = ' + connection.escape(body.token);

		connection.query(query, function(err, result) {
			if (err || result.length === 0) {
				res.status(404).send("Invalid token");
				return;
			} else {
				res.status(200).json({...result[0]});
				return;
			}
		});
	} else {
		if (typeof body.token !== "string") {
			res.status(400).send("Bad credentials");
			return;
		}

		const query = 'SELECT * FROM user WHERE id = ' + connection.escape(body.token);

		connection.query(query, function(err, result) {
			if (err || result.length === 0) {
				res.status(404).send("Invalid token");
				return;
			} else {
				res.status(200).json({...result[0]});
				return;
			}
		});
	}
}

export const getAllUsers =
    async (req, res) => {
	if (req.auth.permission !== "admin" && req.auth.permission !== "seller") {
		res.status(403).send("Forbidden");
		return;
	}

	const query = 'SELECT * FROM user';

	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("No user found");
			return;
		} else {
			res.status(200).json(result);
		}
	})
}

export const getAllEmployee =
    async (req, res) => {
	if (req.auth.permission !== "admin") {
		res.status(403).send("Forbidden");
		return;
	}

	const query = 'SELECT * FROM personnel';
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("No employee found");
			return;
		} else {
			res.status(200).json(result);
			return;
		}
	})
}

export const addMoney = async (req, res) => {
	const body = req.body;

	if (req.auth.permission !== "admin" && req.auth.permission !== "seller") {
		res.status(403).send("Forbidden");
		return;
	}

	const query = "UPDATE user SET money = money + " + connection.escape(body.money) +
		      "WHERE id = " + connection.escape(body.user_id);
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("No user found");
			return;
		}
		const query =
		    "SELECT money FROM user WHERE id = " + connection.escape(body.user_id);
		connection.query(query, function(err, result) {
			if (err || result.length === 0) {
				res.status(404).send("No user found");
				return;
			}
			res.status(200).json({"new_balance" : result[0].money});
			return;
		});
	});
}