import { json } from "express";
import {connection} from "../index.js";
import { checkAuth } from "../checks.js";

// Checks the ticket by pnr no from passenger table
export const checkTicketByPNR = async (req, res) => { 
    const body = req.body;
	if (typeof body.pnr_no !== "string") {
		res.status(400).send("Bad input");
		return;
	}
	const query =
	    'SELECT * FROM passenger WHERE pnr_no = ' + connection.escape(body.pnr_no);
	connection.query(query, function(err, result) {
		if (err || result.length === 0) {
			res.status(404).send("PNR: " + body.pnr_no + " Not Found");
			return;
		} else {
			res.status(200).json(result);
			return;
		}
	});
}

// Check the pnr no with the passenger surname
export const checkTicketByPNRandSurname = async (req,res) => {
    const { pnr, surname } = req.params;

    if (typeof pnr !== "string" || typeof surname !== "string") {
		res.status(400).send("Bad input");
		return;
	}
    const query =
	    'SELECT * FROM passenger WHERE pnr_no = ' + connection.escape(pnr) + ' AND surname = ' + connection.escape(surname);
	connection.query(query, function(err, result) {
		
		if (err || result.length === 0) {
			res.status(404).send("PNR: " + pnr + "with Surname: " + surname + " Not Found");
			return;
		} else {
			res.status(200).json(result);
			return;
		}
	});
}

export const payment = async (req, res) => {
	const body = req.body;
    body.token = req.auth.id;
	const payment_constant = 1.2;
    const MAX_CAPACITY_OF_FLIGHT = 270;

	if (typeof body.ticket_type !== "string" || typeof body.flight_number !== "string" ||
		typeof body.national_id !== "string"  || typeof body.phone !== "string" || typeof body.name !== "string" ||
		typeof body.gender !== "string" || typeof body.surname !== "string" || typeof body.birth_date !== "string" ||
		typeof body.email !== "string" || typeof body.disabled !== "string"  || typeof body.child !== "string"
		|| typeof body.seat !== "string"  ) {
		res.status(400).send("Bad credentials");
		return;
	}

	const get_user_money_query =
		'SELECT money FROM user WHERE id = ' + connection.escape(body.token);
	const flight_capacity_query = 
		"SELECT COUNT(*) AS TOTAL_PASSENGER FROM passenger WHERE flight_number = " + connection.escape(body.flight_number);
	const get_ticket_price_query = 
		'SELECT price FROM flight WHERE flight_number = ' + connection.escape(body.flight_number) + ' AND status = "scheduled"';
	
    connection.query(flight_capacity_query, function(err, result) {
        if (err || result.length === 0) {
            res.status(404).send("Flight Not Found");
            return;
        } else {
            result = Object.values(JSON.parse(JSON.stringify(result)));
            const TOTAL_PASSENGER =result[0].TOTAL_PASSENGER;

            if (TOTAL_PASSENGER < MAX_CAPACITY_OF_FLIGHT) {
                connection.query(get_user_money_query, function(err, result) {
                    if (err || result.length === 0) {
                        res.status(404).send("User money not found.");
                        return;
                    } else {
                        result = Object.values(JSON.parse(JSON.stringify(result)));
                        const USER_MONEY = result[0].money;

                        connection.query(get_ticket_price_query, function(err, result) {
                            if (err || result.length === 0) {
                                res.status(404).send("Ticket price not found.");
                                return;
                            } else {
                                result = Object.values(JSON.parse(JSON.stringify(result)));
                                let TICKET_PRICE = result[0].price;
								let ticket_type = connection.escape(body.ticket_type).toLocaleLowerCase();
                                if (ticket_type === "'advantage'") {
                                    TICKET_PRICE = TICKET_PRICE*payment_constant;
                                }
                                else if (ticket_type === "'comfort'") {
                                    TICKET_PRICE = TICKET_PRICE*payment_constant*payment_constant
                                }

                                TICKET_PRICE = parseFloat(TICKET_PRICE).toFixed(3);

                                if (USER_MONEY < TICKET_PRICE) {
                                    res.status(404).send("Not Enough Token");
                                }
                                else {
                                    let remaining_money = USER_MONEY - TICKET_PRICE;
                                    const update_user_money_query = 
                                        "UPDATE user SET money = " + remaining_money + " WHERE id = " + connection.escape(body.token);
                                    connection.query(update_user_money_query, function(err, result) {
                                        if (err || result.length === 0) {
                                            res.status(404).send("Update user money query failed.");
                                            return;
                                        } else {
                                            generateLuggageId().then(generatedLuggageId => {
                                                const luggage_id = "'" + generatedLuggageId + "'";
												let baggage_allowance = -1;
												let meal = "0";
												let cip_member = "0";

												if (ticket_type === "'essentials'") {
													baggage_allowance = "15"; 
												}
												else if (ticket_type === "'advantage'") {
													baggage_allowance = "25";
													meal = "1";
												}
												else if (ticket_type === "'comfort'") {
													baggage_allowance = "45";
													meal = "1";
													cip_member = 1;
												}

                                                let values = "VALUES (" + luggage_id + "," + 
                                                    baggage_allowance + ",'-1','-1')";
                                                let insert_luggage_query = "INSERT INTO luggage (luggage_id,baggage_allowance,weight,piece) " + values;

                                                connection.query(insert_luggage_query, function(err, result) {
                                                    if (err || result.length === 0) {
                                                        res.status(404).send("Error");
                                                        return;
                                                    } else {
                                                        generatePNR().then(generatedPNR => {
                                                            const pnr = "'" + generatedPNR + "'";

                                                            let values = "VALUES (" + connection.escape(body.national_id) + "," + 
                                                                pnr + "," + 
                                                                connection.escape(body.flight_number) + "," + 
                                                                baggage_allowance + "," + 
                                                                luggage_id + "," + 
                                                                connection.escape(body.ticket_type) + "," + 
                                                                connection.escape(body.seat) + "," + 
                                                                meal + "," + //meal
                                                                "0" + "," +  // extra luggage
                                                                "0" + "," +  // checkin
                                                                connection.escape(body.name) + "," + 
                                                                connection.escape(body.surname) + "," + 
                                                                connection.escape(body.email) + "," + 
                                                                connection.escape(body.phone) + "," + 
                                                                connection.escape(body.gender) + "," + 
                                                                connection.escape(body.birth_date) + "," + 
                                                                cip_member + "," + 
                                                                "0" + "," + // will be edited  
                                                                connection.escape(body.disabled) + "," + 
                                                                connection.escape(body.child) + "," + 
                                                                connection.escape(body.token) + ")";
                                                            let insert_passenger_query = "INSERT INTO passenger (national_id,pnr_no,flight_number,baggage_allowance,luggage_id,fare_type,seat,meal,extra_luggage,check_in,"+
                                                                "name,surname,email,phone,gender,birth_date,cip_member,vip_member,disabled,child,buyer)"+ values;

                                                            connection.query(insert_passenger_query, function(err, result) {
                                                                if (err || result.length === 0) {
                                                                    console.log(err);
                                                                    res.status(404).send("Insert passenger query failed."); // add error message
                                                                    return;
                                                                } else {
                                                                    let response = {
                                                                        "pnr" : generatedPNR
                                                                    }
                                                                    res.status(200).json(response);
                                                                    return;
                                                                }
                                                            });


                                                        });

                                                    }
                                                });
                                            });

                                        }
                                    });
                                }

                            }
                        });

                    }
                });

            }
            else {
                res.status(404).send("Flight is Full");
            }
        }
    });
}


function checkLuggageIdIfExist(luggage_id) {
	return new Promise((resolve, reject) => {
	  let query = "SELECT * FROM luggage WHERE luggage_id = " + connection.escape(luggage_id);
  
	  connection.query(query, (error, results) => {
		if (error) {
		  console.error(error);
		  reject(error);
		  return;
		}
  		resolve(results.length > 0);
	  });
	});
  }
  
  
async function generateLuggageId() {
	const min = 100000000000; 
	const max = 999999999999; 
	let loop_breaker = 0;
	let randomLuggageId = -1; 		
	let luggageIdToCheck = '-1';

	while (true && loop_breaker != 1000) {
		loop_breaker++;
		randomLuggageId = Math.floor(Math.random() * (max - min + 1)) + min;
		luggageIdToCheck = randomLuggageId;

		const exists = await checkLuggageIdIfExist(luggageIdToCheck);
		
		if (exists) {
		  luggageIdToCheck = -1;
		} else {
		  break;
		}
	  }
	return luggageIdToCheck;

	}
	function checkPNRIfExist(pnr) {
		return new Promise((resolve, reject) => {
		  let query = "SELECT * FROM passenger WHERE pnr_no = " + connection.escape(pnr);
	  
		  connection.query(query, (error, results) => {
			if (error) {
			  console.error(error);
			  reject(error);
			  return;
			}
			  resolve(results.length > 0);
		  });
		});
	  }
	  
	async function generatePNR() {
		const alphanumericChars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
		let loop_breaker = 0;
		let randomPNR = '';
		let PNRToCheck = '-1';
	
		while (true && loop_breaker != 1000) {
			loop_breaker++;
			for (let i = 0; i < 8; i++) {
				const randomIndex = Math.floor(Math.random() * alphanumericChars.length);
				randomPNR += alphanumericChars.charAt(randomIndex);
			  }
			
			PNRToCheck = randomPNR;
	
			const exists = await checkPNRIfExist(PNRToCheck);
			
			if (exists) {
			  PNRToCheck = -1;
			  randomPNR = '';
			} else {
			  break;
			}
		  }
		return PNRToCheck;
  }

