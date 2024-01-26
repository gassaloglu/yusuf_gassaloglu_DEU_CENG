import express from "express"

import {employeeAuth} from "../auth.js";
import {addMoney, getAllEmployee, getAllUsers} from "../controller/profile.js";

const router = express.Router()

// router.get("/", getProfile);
router.get("/users", employeeAuth, getAllUsers);
router.get("/employee", employeeAuth, getAllEmployee);
router.post("/money", employeeAuth, addMoney);

export default router;