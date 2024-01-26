import jwt from 'jsonwebtoken';
import {mapObject} from 'underscore';

import {PRIVATE_KEY} from '../auth.js';
import {checkProps} from '../checks.js';
import {asyncConnection} from "../index.js";

const DEFAULT_MONEY = 5000;
const REGISTER_PROPS = {
	name : "string",
	surname : "string",
	email : "string",
	password : "string",
	phone : "string",
	gender : "string",
	birth_date : "string",
}

export const registerUser =
    async (req, res) => {
	if (!checkProps(req.body, REGISTER_PROPS))
		return res.status(400).send();

	const body = mapObject(req.body, f => asyncConnection.escape(f));

	const query = 'INSERT INTO user VALUES (DEFAULT,' + body.name + ',' + body.surname + ',' +
		      body.email + ',' + body.password + ',' + body.phone + ',' + body.gender +
		      ',' + body.birth_date + ',' + DEFAULT_MONEY + ')';

	asyncConnection.query(query)
	    .then(() => {
		    const query = "SELECT id FROM user WHERE email =" + body.email;
		    asyncConnection.query(query)
			.then(([[result]]) => {
				const credentials = {is_employee : false, id : result.id};
				const token = jwt.sign(credentials, PRIVATE_KEY);
				res.status(200).json({token});
			})
			.catch(() => res.status(500).send("Server error"));
	    })
	    .catch(() => res.status(409).send("Cannot register"));
}

export const addEmployee = async (req, res) => {
	// check props will be added

	if (req.auth.permission !== "admin" && req.auth.permission !== "passenger_services") {
		res.status(403).send("Forbidden");
		return;
	}

	const body = mapObject(req.body, f => asyncConnection.escape(f));

	const query = 'INSERT INTO personnel VALUES (' + body.national_id + ',' + body.name + ',' +
		      body.surname + ',' + body.email + ',' + body.phone + ',' + body.gender + ',' +
		      body.birth_date + ',' + body.password + ',' + body.permission + ',' +
		      body.title + ');';

	// connection.query(query, function(err, result) {
	//     if (err || result.length === 0) {
	//         res.status(409).send("Cannot be added");
	//         return;
	//     } else {
	//         res.status(200).json({token: result});
	//     }
	// })

	asyncConnection.query(query)
	    .then(() => {
		    const credentials = {is_employee : true, id : body.national_id};
		    const token = jwt.sign(credentials, PRIVATE_KEY);
		    res.status(200).json({token});
	    })
	    .catch(() => res.status(409).send("Cannot register"));
}
