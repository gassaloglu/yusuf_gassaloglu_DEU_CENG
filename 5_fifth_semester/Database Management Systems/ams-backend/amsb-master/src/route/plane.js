import express from "express"

import {employeeAuth} from "../auth.js";
import {addPlane, getAllPlanes, getPlaneByRegistration, status} from "../controller/plane.js";

const router = express.Router()

router.get("/all", employeeAuth, getAllPlanes);
router.post("/add", employeeAuth, addPlane);
router.post("/status", employeeAuth, status);

router.get("/:registration", employeeAuth, getPlaneByRegistration);

export default router;