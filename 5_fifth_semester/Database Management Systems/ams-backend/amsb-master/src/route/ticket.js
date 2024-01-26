import express from "express"
import { checkTicketByPNRandSurname, payment} from "../controller/ticket.js";
import { auth } from '../auth.js';

const router = express.Router();

// router.get("/",checkTicketByPNR);
router.get("/:pnr/:surname", checkTicketByPNRandSurname);
router.post("/payment", auth, payment);

export default router;
