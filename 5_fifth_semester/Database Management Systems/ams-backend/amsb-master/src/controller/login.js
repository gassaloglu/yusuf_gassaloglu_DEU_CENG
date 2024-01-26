import jwt from 'jsonwebtoken';

import {PRIVATE_KEY} from "../auth.js";
import {checkProps} from "../checks.js"
import { asyncConnection } from "../index.js";

const PROPS = {
	is_employee : "boolean",
	key : "string",
	password : "string"
};

export const login = async (req, res) => {
	if (!checkProps(req.body, PROPS))
		return res.status(400).send("Bad credentials");

	const key = asyncConnection.escape(req.body.key);
	const password = asyncConnection.escape(req.body.password);

	if (req.body.is_employee) {
		const query = `SELECT name, surname, national_id, permission FROM personnel WHERE national_id = ${
		    key} AND password = ${password}`;
		asyncConnection.query(query)
		    .then(([[{ name, surname, national_id, permission }]]) => {
			    const credentials = {is_employee : true, id: national_id, permission};
			    const token = jwt.sign(credentials, PRIVATE_KEY);
			    res.status(200).json({ token, name, surname, permission });
		    })
		    .catch(() => res.status(404).send());
	} else {
		const query = `SELECT id FROM user WHERE email = ${key} AND password = ${password}`;
		asyncConnection.query(query)
		    .then(([[user]]) => {
			    const credentials = {is_employee: false, id: user.id};
			    const token = jwt.sign(credentials, PRIVATE_KEY);
			    res.status(200).json({token});
		    })
		    .catch(() => res.status(404).send());
	}
}
