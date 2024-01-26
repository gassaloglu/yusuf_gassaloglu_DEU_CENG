import express from "express"

import {employeeAuth} from "../auth.js";
import {
	checkIn,
	employeeCheckIn,
	getAllDistinctPersons,
	getAllPassengers,
	getMealAll,
	getMealForFlight,
	getPassengerById,
	getPassengerByPnr,
	removePassenger
} from "../controller/passenger.js";

const router = express.Router()

router.get("/all", employeeAuth, getAllPassengers);
router.get("/id", employeeAuth, getPassengerById);
router.get("/pnr", employeeAuth, getPassengerByPnr);
router.post("/employeecheckin", employeeAuth, employeeCheckIn);
router.post("/checkin", checkIn);
router.post("/remove", removePassenger);

router.get("/meal", employeeAuth, getMealAll);
router.get("/meal/:flight_number", employeeAuth, getMealForFlight);

router.get("/:flight_number/:distinctive", employeeAuth, getAllDistinctPersons);

export default router;