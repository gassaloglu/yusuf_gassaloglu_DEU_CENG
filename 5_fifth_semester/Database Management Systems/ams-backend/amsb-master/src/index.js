import cors from "cors"
import dotenv from "dotenv"
import express from "express";
import mysql from 'mysql2';

import oldFlightRoute from "./route/all_flight.js";
import flightRoute from "./route/flight.js";
import loginRoute from "./route/login.js";
import passengerRoute from "./route/passenger.js"
import planeRoute from "./route/plane.js";
import profileRoute from "./route/profile.js";
import registerRoute from "./route/register.js";
import ticketRoute from "./route/ticket.js";

dotenv.config();

const config = {
  host: process.env.CONNECTION_URL,
  user: process.env.DB_USER,
  database: process.env.DB_NAME,
  password: process.env.DB_PASS,
}
export const STATIC_TIME = '2024-01-04 10:30:00';

export const connection = mysql.createConnection(config);
export const asyncConnection = mysql.createConnection(config).promise();

connection.connect(err => {
	if (err) throw err;
    console.log(`[SYNC] Connected with config: `, config);
});

asyncConnection.connect()
    .then(() => console.log(`[ASYNC] Connected with config: `, config))
    .catch(err => { throw err });

const app = express();

app.use(express.json({ limit: "30mb", extended: true }));
app.use(express.urlencoded({ limit: "30mb", extended: true }));
app.use(cors());

app.use("/login", loginRoute);
app.use("/flight/all", oldFlightRoute);
app.use("/flight", flightRoute);
app.use("/profile", profileRoute);
app.use("/passenger", passengerRoute);
app.use("/plane", planeRoute);
app.use("/ticket", ticketRoute);
app.use("/register", registerRoute);

const PORT = process.env.PORT;
app.listen(PORT, () => console.log(`Server running on port: ${PORT}`));
