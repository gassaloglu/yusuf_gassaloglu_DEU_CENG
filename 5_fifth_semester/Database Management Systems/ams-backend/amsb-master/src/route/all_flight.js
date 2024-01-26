import express from "express"

import {employeeAuth} from "../auth.js";
import {
	getAllFlights,
	getEmptySeats,
	getFlightsBetweenDates,
	getFlightsByDestinationAndDate,
	getFlightsByFlightNumber
} from "../controller/all_flight.js";

const router = express.Router()

router.get("/", employeeAuth, getAllFlights);
router.get("/date", employeeAuth, getFlightsBetweenDates);
router.get("/seats", employeeAuth, getEmptySeats);

// Put every other route above this or the /:id will run instead of your desired one
router.get("/:id", employeeAuth, getFlightsByFlightNumber);
router.get("/:from/:to/:date", employeeAuth, getFlightsByDestinationAndDate);

export default router;