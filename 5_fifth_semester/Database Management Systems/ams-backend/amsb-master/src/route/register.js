import express from "express"
import {employeeAuth} from "../auth.js";
import { addEmployee, registerUser } from "../controller/register.js";

const router = express.Router();

router.post("/",registerUser);
router.post("/addEmployee", employeeAuth, addEmployee);

export default router;