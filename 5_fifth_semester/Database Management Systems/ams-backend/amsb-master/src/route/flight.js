import express from "express"

import {employeeAuth} from "../auth.js";
import {
	addFlight,
	cancelFlight,
	getAllFlights,
	getEmptySeats,
	getFlightsBetweenDates,
	getFlightsByDestinationAndDate,
	getFlightsByFlightNumber,
	getFlightTimeInfo,
	getLuggageList
} from "../controller/flight.js";

const router = express.Router()

router.get("/", getAllFlights);
router.get("/date", getFlightsBetweenDates);
router.get("/seats", getEmptySeats);
router.post("/addFlight", employeeAuth, addFlight);
router.post("/getPlaneSchedule", employeeAuth, getFlightTimeInfo);
router.post("/getLuggageList", employeeAuth, getLuggageList);
router.post("/cancel", employeeAuth, cancelFlight)

// Put every other route above this or the /:id will run instead of your desired one
router.get("/:flight_number", getFlightsByFlightNumber);
router.get("/:from/:to/:date", getFlightsByDestinationAndDate);

export default router;