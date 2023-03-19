using System;

namespace basicDateModule
{
    class Program
    {
        static void Main(string[] args)
        {
            int dayInput = 0, firstYearInput = 0, monthInput = 0, century = 0, firstYearLastTwoDigit = 0, askedDay = 0, n = 0,
                 secondDayInput = 0, secondYearInput = 0, secondMonthInput = 0, secondCentury = 0, secondYearLastTwoDigit = 0, secondAskedDay = 0,
                 breaker = -1, monthInputControl = -1, secondMonthInputControl = -1, count = 0;
            string stringMonthInput = "", stringAskedDay = "",
                   stringSecondMonthInput = "", secondStringAskedDay = "",
                   season = "";
            bool startingWithJan = false;
            // 1.st date
            // Getting first dates year
            do
            {
                Console.Write("Please enter first dates year. (The year must be greater than or equal to 1777): ");
                if (Int32.TryParse(Console.ReadLine(), out firstYearInput) && firstYearInput >= 1777)
                {
                    break;
                }
                Console.WriteLine("The year must be greater than or equal to 1777.");
            }
            while (true);

            // Getting first dates month
            do
            {
                Console.Write("Please enter first dates month. (January ,February, March... ): ");
                stringMonthInput = Console.ReadLine();
                stringMonthInput = stringMonthInput.Trim();
                stringMonthInput = stringMonthInput.ToLower();

                switch (stringMonthInput)
                {
                    case "march":
                        monthInput = 1;
                        break;
                    case "april":
                        monthInput = 2;
                        break;
                    case "may":
                        monthInput = 3;
                        break;
                    case "june":
                        monthInput = 4;
                        break;
                    case "july":
                        monthInput = 5;
                        break;
                    case "august":
                        monthInput = 6;
                        break;
                    case "september":
                        monthInput = 7;
                        break;
                    case "october":
                        monthInput = 8;
                        break;
                    case "november":
                        monthInput = 9;
                        break;
                    case "december":
                        monthInput = 10;
                        break;
                    case "january":
                        monthInput = 11;
                        break;
                    case "february":
                        monthInput = 12;
                        break;
                    default:
                        break;
                }

                if (monthInput == 0)
                {
                    Console.WriteLine("Please enter the month correctly.");
                    Console.WriteLine("Months : January, February, March, April, May, June, July, August, September, October, November, December");
                }
                else
                {
                    break;
                }
            }
            while (true);
            // Getting first dates day
            while (true)
            {
                Console.Write("Please enter first dates day. (1,2,3,4...): ");
                if (Int32.TryParse(Console.ReadLine(), out dayInput))
                {
                    if (monthInput == 1 || monthInput == 3 || monthInput == 5 || monthInput == 6 || monthInput == 8 || monthInput == 10 || monthInput == 11) //These months contain 31 days
                    {
                        if (dayInput > 0 && dayInput <= 31)
                        {
                            break;
                        }
                        else
                        {
                            Console.WriteLine("There are 31 days in " + stringMonthInput + ". Please enter a valid day.");
                        }
                    }
                    else if (monthInput == 2 || monthInput == 4 || monthInput == 7 || monthInput == 9) // These months cantain 30 days
                    {
                        if (dayInput > 0 && dayInput <= 30)
                        {
                            break;
                        }
                        else
                        {
                            Console.WriteLine("There are 30 days in " + stringMonthInput + ". Please enter a valid day.");
                        }
                    }

                    else if ((monthInput == 12 && firstYearInput % 100 != 0 && firstYearInput % 4 == 0) || (monthInput == 12 && firstYearInput % 100 == 0 && firstYearInput % 400 == 0)) // February in a leap year
                    {
                        if (dayInput > 0 && dayInput <= 29)
                        {
                            break;
                        }
                        else
                        {
                            Console.WriteLine("There are 29 days in " + stringMonthInput + ". Please enter a valid day.");
                        }
                    }

                    else if ((monthInput == 12 && firstYearInput % 100 == 0 && firstYearInput % 400 != 0) || (monthInput == 12 && firstYearInput % 100 != 0 && firstYearInput % 400 != 0) || (monthInput == 12 && firstYearInput % 100 != 0 && firstYearInput % 4 != 0))    // February in a normal year  
                    {
                        if (dayInput > 0 && dayInput <= 28)
                        {
                            break;
                        }
                        else
                        {
                            Console.WriteLine("There are 28 days in " + stringMonthInput + ". Please enter a valid day.");
                        }
                    }

                    else
                    {
                        Console.WriteLine("ERROR!! Please enter a valid value.");
                    }

                }
                else
                {
                    Console.WriteLine("Please enter a valid day.");
                }
            }
            if (monthInput > 10) // for correct calculation (january and february)
            {
                firstYearInput += -1;
            }
            // date calculation  https://sites.millersville.edu/bikenaga/number-theory/calendar/calendar.html
            century = firstYearInput / 100;
            firstYearLastTwoDigit = firstYearInput % 100;
            askedDay = Convert.ToInt32(Math.Floor((dayInput + (5 * century) + firstYearLastTwoDigit + Math.Floor(Convert.ToDouble(century) / 4) + Math.Floor(Convert.ToDouble(firstYearLastTwoDigit) / 4) + (2.6 * Convert.ToDouble(monthInput) - 0.2))) % 7);
            switch (askedDay) // for printing day
            {
                case 0:
                    stringAskedDay = "Sunday";
                    break;
                case 1:
                    stringAskedDay = "Monday";
                    break;
                case 2:
                    stringAskedDay = "Tuesday";
                    break;
                case 3:
                    stringAskedDay = "Wednesday";
                    break;
                case 4:
                    stringAskedDay = "Thursday";
                    break;
                case 5:
                    stringAskedDay = "Friday";
                    break;
                case 6:
                    stringAskedDay = "Saturday";
                    break;
                default:
                    Console.WriteLine("error");
                    break;
            }
            if (monthInput > 10) // for correct calculation (january and february)
            {
                firstYearInput += 1;
            }
            Console.ForegroundColor = ConsoleColor.DarkGreen;
            Console.WriteLine("!! SUCCESFUL !!");
            Console.ForegroundColor = ConsoleColor.White;
            //2.nd date
            // Getting second dates year
            do
            {
                Console.Write("Please enter second dates year. (The year must be greater than or equal to 1777): ");
                if (Int32.TryParse(Console.ReadLine(), out secondYearInput) && secondYearInput >= 1777)
                {
                    break;
                }
                Console.WriteLine("The year must be greater than or equal to 1777.");
            }
            while (true);

            // Getting second dates month
            do
            {
                Console.Write("Please enter first dates month. (January ,February, March... ): ");
                stringSecondMonthInput = Console.ReadLine();
                stringSecondMonthInput = stringSecondMonthInput.Trim();
                stringSecondMonthInput = stringSecondMonthInput.ToLower();

                switch (stringSecondMonthInput)
                {
                    case "march":
                        secondMonthInput = 1;
                        break;
                    case "april":
                        secondMonthInput = 2;
                        break;
                    case "may":
                        secondMonthInput = 3;
                        break;
                    case "june":
                        secondMonthInput = 4;
                        break;
                    case "july":
                        secondMonthInput = 5;
                        break;
                    case "august":
                        secondMonthInput = 6;
                        break;
                    case "september":
                        secondMonthInput = 7;
                        break;
                    case "october":
                        secondMonthInput = 8;
                        break;
                    case "november":
                        secondMonthInput = 9;
                        break;
                    case "december":
                        secondMonthInput = 10;
                        break;
                    case "january":
                        secondMonthInput = 11;
                        break;
                    case "february":
                        secondMonthInput = 12;
                        break;
                    default:
                        break;
                }

                if (secondMonthInput == 0)
                {
                    Console.WriteLine("Please enter the month correctly.");
                    Console.WriteLine("Months : January, February, March, April, May, June, July, August, September, October, November, December");
                }
                else
                {
                    break;
                }
            }
            while (true);
            // Getting second dates day
            while (true)
            {
                Console.Write("Please enter second dates day. (1,2,3,4...): ");
                if (Int32.TryParse(Console.ReadLine(), out secondDayInput))
                {
                    if (secondMonthInput == 1 || secondMonthInput == 3 || secondMonthInput == 5 || secondMonthInput == 6 || secondMonthInput == 8 || secondMonthInput == 10 || secondMonthInput == 11) //These months contain 31 days
                    {
                        if (secondDayInput > 0 && secondDayInput <= 31)
                        {
                            break;
                        }
                        else
                        {
                            Console.WriteLine("There are 31 days in " + stringSecondMonthInput + ". Please enter a valid day.");
                        }
                    }
                    else if (secondMonthInput == 2 || secondMonthInput == 4 || secondMonthInput == 7 || secondMonthInput == 9) //These months contain 30 days
                    {
                        if (secondDayInput > 0 && secondDayInput <= 30)
                        {
                            break;
                        }
                        else
                        {
                            Console.WriteLine("There are 30 days in " + stringSecondMonthInput + ". Please enter a valid day.");
                        }
                    }
                    else if ((secondMonthInput == 12 && secondYearInput % 100 != 0 && secondYearInput % 4 == 0) || (secondMonthInput == 12 && secondYearInput % 100 == 0 && secondYearInput % 400 == 0)) // February in a leap year
                    {
                        if (secondDayInput > 0 && secondDayInput <= 29)
                        {
                            break;
                        }
                        else
                        {
                            Console.WriteLine("There are 29 days in " + stringSecondMonthInput + ". Please enter a valid day.");
                        }
                    }
                    else if ((secondMonthInput == 12 && secondYearInput % 100 == 0 && secondYearInput % 400 != 0) || (secondMonthInput == 12 && secondYearInput % 100 != 0 && secondYearInput % 400 != 0) || (secondMonthInput == 12 && secondYearInput % 100 != 0 && secondYearInput % 4 != 0)) // February in a normal year
                    {
                        if (secondDayInput > 0 && secondDayInput <= 28)
                        {
                            break;
                        }
                        else
                        {
                            Console.WriteLine("There are 28 days in " + stringSecondMonthInput + ". Please enter a valid day.");
                        }
                    }
                    else
                    {
                        Console.WriteLine("ERROR!! Please enter a valid value.");
                    }
                }
                else
                {
                    Console.WriteLine("Please enter a valid day.");
                }
            }
            Console.ForegroundColor = ConsoleColor.DarkGreen;
            Console.WriteLine("!! SUCCESFUL !!");
            Console.ForegroundColor = ConsoleColor.White;
            // Getting skip value
            do
            {
                Console.Write("How many days will the program skip between the given dates? [1,30]: ");
                if (Int32.TryParse(Console.ReadLine(), out n) && n > 0 && n < 31)
                {
                    break;
                }
                Console.WriteLine("Please enter a valid value.");
            }
            while (true);
            if (secondMonthInput > 10) // for correct calculation (january and february)
            {
                secondYearInput += -1;
            }
            // date calculation  https://sites.millersville.edu/bikenaga/number-theory/calendar/calendar.html
            secondCentury = secondYearInput / 100;
            secondYearLastTwoDigit = secondYearInput % 100;
            secondAskedDay = Convert.ToInt32(Math.Floor((secondDayInput + (5 * secondCentury) + secondYearLastTwoDigit + Math.Floor(Convert.ToDouble(secondCentury) / 4) + Math.Floor(Convert.ToDouble(secondYearLastTwoDigit) / 4) + (2.6 * Convert.ToDouble(secondMonthInput) - 0.2))) % 7);

            switch (secondAskedDay) // for printing day
            {
                case 0:
                    secondStringAskedDay = "Sunday";
                    break;
                case 1:
                    secondStringAskedDay = "Monday";
                    break;
                case 2:
                    secondStringAskedDay = "Tuesday";
                    break;
                case 3:
                    secondStringAskedDay = "Wednesday";
                    break;
                case 4:
                    secondStringAskedDay = "Thursday";
                    break;
                case 5:
                    secondStringAskedDay = "Friday";
                    break;
                case 6:
                    secondStringAskedDay = "Saturday";
                    break;
                default:
                    Console.WriteLine("error");
                    break;
            }
            if (secondMonthInput > 10) // for correct calculation (january and february)
            {
                secondYearInput += 1;
            }
            Console.WriteLine(" ");
            Console.WriteLine("GIVEN DATES");
            Console.WriteLine(dayInput + " " + stringMonthInput + " " + firstYearInput + "-> " + stringAskedDay);
            Console.WriteLine(secondDayInput + " " + stringSecondMonthInput + " " + secondYearInput + "-> " + secondStringAskedDay);
            Console.WriteLine("n = " + n);
            Console.WriteLine(" ");
            Console.WriteLine("The program will print a date every " + n + " days between the given dates. ");
            Console.WriteLine("Please press enter to start.");
            Console.WriteLine("");
            Console.ReadLine();
            // Year starts with march in this code. This if else statement for calculate correction
            if (monthInput == 10)
            {
                monthInputControl = ((monthInput + 2) % 12) + 12;
            }
            else
            {
                monthInputControl = ((monthInput + 2) % 12);
            }
            // Year starts with march in this code. This if else statement for calculate correction
            if (secondMonthInput == 10)
            {
                secondMonthInputControl = ((secondMonthInput + 2) % 12) + 12;
            }
            else
            {
                secondMonthInputControl = ((secondMonthInput + 2) % 12);
            }
            // if second date earlier than first code
            if (firstYearInput == secondYearInput && monthInput == secondMonthInput && dayInput > secondDayInput)
            {
                int changeday;
                changeday = secondDayInput;
                secondDayInput = dayInput;
                dayInput = changeday;
            }
            else if (firstYearInput == secondYearInput && monthInputControl > secondMonthInputControl)
            {
                int changemonth, changeday;
                changeday = secondDayInput;
                secondDayInput = dayInput;
                dayInput = changeday;

                changemonth = secondMonthInput;
                secondMonthInput = monthInput;
                monthInput = changemonth;
            }
            else if (firstYearInput > secondYearInput)
            {
                int changeYear, changemonth, changeday;
                changeday = secondDayInput;
                secondDayInput = dayInput;
                dayInput = changeday;

                changemonth = secondMonthInput;
                secondMonthInput = monthInput;
                monthInput = changemonth;

                changeYear = secondYearInput;
                secondYearInput = firstYearInput;
                firstYearInput = changeYear;
            }
            // if  y1=y2 m1=m2  and  d1 not equal to d2          
            if (firstYearInput == secondYearInput && monthInput == secondMonthInput && dayInput != secondDayInput)
            {
                while (dayInput <= secondDayInput)
                {
                    // for print season
                    if ((monthInput == 1 || monthInput == 2 || monthInput == 3) && season != "SPRING")
                    {
                        season = "SPRING";
                        Console.WriteLine("");
                        Console.ForegroundColor = ConsoleColor.DarkGreen;
                        Console.WriteLine(season);
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    else if ((monthInput == 4 || monthInput == 5 || monthInput == 6) && season != "SUMMER")
                    {
                        season = "SUMMER";
                        Console.WriteLine("");
                        Console.ForegroundColor = ConsoleColor.DarkYellow;
                        Console.WriteLine(season);
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    else if ((monthInput == 7 || monthInput == 8 || monthInput == 9) && season != "AUTUMN")
                    {
                        season = "AUTUMN";
                        Console.WriteLine("");
                        Console.ForegroundColor = ConsoleColor.DarkRed;
                        Console.WriteLine(season);
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    else if ((monthInput == 10 || monthInput == 11 || monthInput == 12) && season != "WINTER")
                    {
                        season = "WINTER";
                        Console.WriteLine("");
                        Console.ForegroundColor = ConsoleColor.DarkBlue;
                        Console.WriteLine(season);
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    if (monthInput > 10) // for correct calculation (january and february)
                    {
                        firstYearInput += -1;
                    }
                    // date calculation algorithm
                    century = firstYearInput / 100;
                    firstYearLastTwoDigit = firstYearInput % 100;
                    askedDay = Convert.ToInt32(Math.Floor((dayInput + (5 * century) + firstYearLastTwoDigit + Math.Floor(Convert.ToDouble(century) / 4) + Math.Floor(Convert.ToDouble(firstYearLastTwoDigit) / 4) + (2.6 * Convert.ToDouble(monthInput) - 0.2))) % 7);
                    switch (askedDay)
                    {
                        case 0:
                            stringAskedDay = "Sunday";
                            break;
                        case 1:
                            stringAskedDay = "Monday";
                            break;
                        case 2:
                            stringAskedDay = "Tuesday";
                            break;
                        case 3:
                            stringAskedDay = "Wednesday";
                            break;
                        case 4:
                            stringAskedDay = "Thursday";
                            break;
                        case 5:
                            stringAskedDay = "Friday";
                            break;
                        case 6:
                            stringAskedDay = "Saturday";
                            break;
                        default:
                            Console.WriteLine("error");
                            break;
                    }
                    if (monthInput > 10) // for correct calculation (january and february)
                    {
                        firstYearInput += 1;
                    }
                    Console.WriteLine(dayInput + " " + stringMonthInput + " " + firstYearInput + "-> " + stringAskedDay);
                    dayInput = dayInput + n;
                }
            }
            //end
            // if y1=y2 and        m1 not equal to m2 
            else if ((firstYearInput == secondYearInput) && (monthInput != secondMonthInput))
            {
                while ((monthInputControl < secondMonthInputControl) || (breaker <= secondDayInput))
                {
                    if ((monthInput == 1 || monthInput == 2 || monthInput == 3) && season != "SPRING")
                    {
                        season = "SPRING";
                        Console.WriteLine("");
                        Console.ForegroundColor = ConsoleColor.DarkGreen;
                        Console.WriteLine(season);
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    else if ((monthInput == 4 || monthInput == 5 || monthInput == 6) && season != "SUMMER")
                    {
                        season = "SUMMER";
                        Console.WriteLine("");
                        Console.ForegroundColor = ConsoleColor.DarkYellow;
                        Console.WriteLine(season);
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    else if ((monthInput == 7 || monthInput == 8 || monthInput == 9) && season != "AUTUMN")
                    {
                        season = "AUTUMN";
                        Console.WriteLine("");
                        Console.ForegroundColor = ConsoleColor.DarkRed;
                        Console.WriteLine(season);
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    else if ((monthInput == 10 || monthInput == 11 || monthInput == 12) && season != "WINTER")
                    {
                        season = "WINTER";
                        Console.WriteLine("");
                        Console.ForegroundColor = ConsoleColor.DarkBlue;
                        Console.WriteLine(season);
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    // Year starts with march in this code. This if else statement for calculate correction
                    if (monthInput == 10)
                    {
                        monthInputControl = ((monthInput + 2) % 12) + 12;
                    }
                    else
                    {
                        monthInputControl = ((monthInput + 2) % 12);
                    }
                    // Year starts with march in this code. This if else statement for calculate correction
                    if (secondMonthInput == 10)
                    {
                        secondMonthInputControl = ((secondMonthInput + 2) % 12) + 12;
                    }
                    else
                    {
                        secondMonthInputControl = ((secondMonthInput + 2) % 12);
                    }
                    // Months that contain 31 days
                    if (monthInput == 1 || monthInput == 3 || monthInput == 5 || monthInput == 6 || monthInput == 8 || monthInput == 10 || monthInput == 11)
                    {
                        if (monthInput > 10) // for correct calculation (january)
                        {
                            firstYearInput += -1;
                        }
                        // date calcualtion algorithm
                        century = firstYearInput / 100;
                        firstYearLastTwoDigit = firstYearInput % 100;
                        askedDay = Convert.ToInt32(Math.Floor((dayInput + (5 * century) + firstYearLastTwoDigit + Math.Floor(Convert.ToDouble(century) / 4) + Math.Floor(Convert.ToDouble(firstYearLastTwoDigit) / 4) + (2.6 * Convert.ToDouble(monthInput) - 0.2))) % 7);
                        switch (askedDay)
                        {
                            case 0:
                                stringAskedDay = "Sunday";
                                break;
                            case 1:
                                stringAskedDay = "Monday";
                                break;
                            case 2:
                                stringAskedDay = "Tuesday";
                                break;
                            case 3:
                                stringAskedDay = "Wednesday";
                                break;
                            case 4:
                                stringAskedDay = "Thursday";
                                break;
                            case 5:
                                stringAskedDay = "Friday";
                                break;
                            case 6:
                                stringAskedDay = "Saturday";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        switch (monthInput)
                        {
                            case 1:
                                stringMonthInput = "March";
                                break;
                            case 2:
                                stringMonthInput = "April";
                                break;
                            case 3:
                                stringMonthInput = "May";
                                break;
                            case 4:
                                stringMonthInput = "June";
                                break;
                            case 5:
                                stringMonthInput = "July";
                                break;
                            case 6:
                                stringMonthInput = "August";
                                break;
                            case 7:
                                stringMonthInput = "September";
                                break;
                            case 8:
                                stringMonthInput = "October";
                                break;
                            case 9:
                                stringMonthInput = "November";
                                break;
                            case 10:
                                stringMonthInput = "December";
                                break;
                            case 11:
                                stringMonthInput = "January";
                                break;
                            case 12:
                                stringMonthInput = "February";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        if (monthInput > 10) // for correct calculation (january)
                        {
                            firstYearInput += 1;
                        }

                        if (dayInput != 0 && ((secondDayInput >= dayInput) || monthInputControl != secondMonthInputControl))
                        {
                            Console.WriteLine(dayInput + " " + stringMonthInput + " " + firstYearInput + "-> " + stringAskedDay);
                        }
                        dayInput = dayInput + n;
                        
                        if (dayInput > 31) // for jump to next month
                        {
                            breaker = dayInput;
                            dayInput = dayInput % 31;
                            monthInput = monthInput + 1;
                            monthInputControl = monthInputControl + 1;
                        }

                        if (monthInputControl == secondMonthInputControl) // for finishing the code
                        {
                            breaker = dayInput;
                        }
                        
                    }
                    // for february 29
                    else if ((monthInput == 12 && firstYearInput % 100 != 0 && firstYearInput % 4 == 0) || (monthInput == 12 && firstYearInput % 100 == 0 && firstYearInput % 400 == 0))
                    {
                        if (monthInput > 10) // for correct calculation (february)
                        {
                            firstYearInput += -1;
                        }
                        //date calculation algorithm
                        century = firstYearInput / 100;
                        firstYearLastTwoDigit = firstYearInput % 100;
                        askedDay = Convert.ToInt32(Math.Floor((dayInput + (5 * century) + firstYearLastTwoDigit + Math.Floor(Convert.ToDouble(century) / 4) + Math.Floor(Convert.ToDouble(firstYearLastTwoDigit) / 4) + (2.6 * Convert.ToDouble(monthInput) - 0.2))) % 7);//step3 

                        switch (askedDay)
                        {
                            case 0:
                                stringAskedDay = "Sunday";
                                break;
                            case 1:
                                stringAskedDay = "Monday";
                                break;
                            case 2:
                                stringAskedDay = "Tuesday";
                                break;
                            case 3:
                                stringAskedDay = "Wednesday";
                                break;
                            case 4:
                                stringAskedDay = "Thursday";
                                break;
                            case 5:
                                stringAskedDay = "Friday";
                                break;
                            case 6:
                                stringAskedDay = "Saturday";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        switch (monthInput)
                        {
                            case 1:
                                stringMonthInput = "March";
                                break;
                            case 2:
                                stringMonthInput = "April";
                                break;
                            case 3:
                                stringMonthInput = "May";
                                break;
                            case 4:
                                stringMonthInput = "June";
                                break;
                            case 5:
                                stringMonthInput = "July";
                                break;
                            case 6:
                                stringMonthInput = "August";
                                break;
                            case 7:
                                stringMonthInput = "September";
                                break;
                            case 8:
                                stringMonthInput = "October";
                                break;
                            case 9:
                                stringMonthInput = "November";
                                break;
                            case 10:
                                stringMonthInput = "December";
                                break;
                            case 11:
                                stringMonthInput = "January";
                                break;
                            case 12:
                                stringMonthInput = "February";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        if (monthInput > 10) // for correct calculation (february)
                        {
                            firstYearInput += 1;
                        }

                        if (dayInput != 0 && ((secondDayInput >= dayInput) || monthInputControl != secondMonthInputControl))
                        {
                            Console.WriteLine(dayInput + " " + stringMonthInput + " " + firstYearInput + "-> " + stringAskedDay);
                        }
                        dayInput = dayInput + n;
                        if (dayInput > 29) //for jump to next month
                        {
                            breaker = dayInput;
                            dayInput = dayInput % 29;
                            monthInput = (monthInput + 1) % 12;
                            monthInputControl = monthInputControl + 1;
                        }
                        if (monthInputControl == secondMonthInputControl) // for finishing the code
                        {
                            breaker = dayInput;
                        }
                    }
                    //28 february
                    else if ((monthInput == 12 && firstYearInput % 100 == 0 && firstYearInput % 400 != 0) || (monthInput == 12 && firstYearInput % 100 != 0 && firstYearInput % 400 != 0) || (monthInput == 12 && firstYearInput % 100 != 0 && firstYearInput % 4 != 0))
                    {
                        if (monthInput > 10) // for correct calculation (february)
                        {
                            firstYearInput += -1;
                        }
                        // date calculation algorithm
                        century = firstYearInput / 100;
                        firstYearLastTwoDigit = firstYearInput % 100;
                        askedDay = Convert.ToInt32(Math.Floor((dayInput + (5 * century) + firstYearLastTwoDigit + Math.Floor(Convert.ToDouble(century) / 4) + Math.Floor(Convert.ToDouble(firstYearLastTwoDigit) / 4) + (2.6 * Convert.ToDouble(monthInput) - 0.2))) % 7);//step3 
                        switch (askedDay)
                        {
                            case 0:
                                stringAskedDay = "Sunday";
                                break;
                            case 1:
                                stringAskedDay = "Monday";
                                break;
                            case 2:
                                stringAskedDay = "Tuesday";
                                break;
                            case 3:
                                stringAskedDay = "Wednesday";
                                break;
                            case 4:
                                stringAskedDay = "Thursday";
                                break;
                            case 5:
                                stringAskedDay = "Friday";
                                break;
                            case 6:
                                stringAskedDay = "Saturday";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        switch (monthInput)
                        {
                            case 1:
                                stringMonthInput = "March";
                                break;
                            case 2:
                                stringMonthInput = "April";
                                break;
                            case 3:
                                stringMonthInput = "May";
                                break;
                            case 4:
                                stringMonthInput = "June";
                                break;
                            case 5:
                                stringMonthInput = "July";
                                break;
                            case 6:
                                stringMonthInput = "August";
                                break;
                            case 7:
                                stringMonthInput = "September";
                                break;
                            case 8:
                                stringMonthInput = "October";
                                break;
                            case 9:
                                stringMonthInput = "November";
                                break;
                            case 10:
                                stringMonthInput = "December";
                                break;
                            case 11:
                                stringMonthInput = "January";
                                break;
                            case 12:
                                stringMonthInput = "February";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        if (monthInput > 10) // for correct calculation (february)
                        {
                            firstYearInput += 1;
                        }

                        if (dayInput != 0 && ((secondDayInput >= dayInput) || monthInputControl != secondMonthInputControl))
                        {
                            Console.WriteLine(dayInput + " " + stringMonthInput + " " + firstYearInput + "-> " + stringAskedDay);
                        }
                        dayInput = dayInput + n;
                        
                        if (dayInput > 28) // for jump to next month
                        {
                            breaker = dayInput;
                            dayInput = dayInput % 28;
                            monthInput = (monthInput + 1) % 12;
                            monthInputControl = monthInputControl + 1;
                        }
                        
                        if (monthInputControl == secondMonthInputControl) // for finishing the code
                        {
                            breaker = dayInput;
                        }
                    }
                    // Months that contain 30 days
                    else if (monthInput == 2 || monthInput == 4 || monthInput == 7 || monthInput == 9)
                    {
                        // date calculation algorithm
                        century = firstYearInput / 100;
                        firstYearLastTwoDigit = firstYearInput % 100;
                        askedDay = Convert.ToInt32(Math.Floor((dayInput + (5 * century) + firstYearLastTwoDigit + Math.Floor(Convert.ToDouble(century) / 4) + Math.Floor(Convert.ToDouble(firstYearLastTwoDigit) / 4) + (2.6 * Convert.ToDouble(monthInput) - 0.2))) % 7);//step3 

                        switch (askedDay)
                        {
                            case 0:
                                stringAskedDay = "Sunday";
                                break;
                            case 1:
                                stringAskedDay = "Monday";
                                break;
                            case 2:
                                stringAskedDay = "Tuesday";
                                break;
                            case 3:
                                stringAskedDay = "Wednesday";
                                break;
                            case 4:
                                stringAskedDay = "Thursday";
                                break;
                            case 5:
                                stringAskedDay = "Friday";
                                break;
                            case 6:
                                stringAskedDay = "Saturday";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        switch (monthInput)
                        {
                            case 1:
                                stringMonthInput = "March";
                                break;
                            case 2:
                                stringMonthInput = "April";
                                break;
                            case 3:
                                stringMonthInput = "May";
                                break;
                            case 4:
                                stringMonthInput = "June";
                                break;
                            case 5:
                                stringMonthInput = "July";
                                break;
                            case 6:
                                stringMonthInput = "August";
                                break;
                            case 7:
                                stringMonthInput = "September";
                                break;
                            case 8:
                                stringMonthInput = "October";
                                break;
                            case 9:
                                stringMonthInput = "November";
                                break;
                            case 10:
                                stringMonthInput = "December";
                                break;
                            case 11:
                                stringMonthInput = "January";
                                break;
                            case 12:
                                stringMonthInput = "February";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        if (dayInput != 0 && ((secondDayInput >= dayInput) || monthInputControl != secondMonthInputControl))
                        {
                            Console.WriteLine(dayInput + " " + stringMonthInput + " " + firstYearInput + "-> " + stringAskedDay);
                        }
                        dayInput = dayInput + n;
                        if (dayInput > 30) // for jump to next month
                        {
                            breaker = dayInput;
                            dayInput = dayInput % 30;
                            monthInput = monthInput + 1;
                            monthInputControl = monthInputControl + 1;
                        }
                        if (monthInputControl == secondMonthInputControl) // for finishing the code
                        {
                            breaker = dayInput;
                        }
                    }
                }
                //end
            }
            // y1 not equal to y2 
            else if (firstYearInput != secondYearInput)
            {
                // Year starts with march in this code. This if else statement for calculate correction
                if (monthInput == 10)
                {
                    monthInputControl = ((monthInput + 2) % 12) + 12;
                }
                else
                {
                    monthInputControl = ((monthInput + 2) % 12);
                }
                // Year starts with march in this code. This if else statement for calculate correction
                if (secondMonthInput == 10)
                {
                    secondMonthInputControl = ((secondMonthInput + 2) % 12) + 12;
                }
                else
                {
                    secondMonthInputControl = ((secondMonthInput + 2) % 12);
                }
                while ((monthInputControl < secondMonthInputControl) || (breaker <= secondDayInput) || (firstYearInput < secondYearInput))
                {
                    //for print season
                    if ((monthInput == 1 || monthInput == 2 || monthInput == 3) && season != "SPRING")
                    {
                        season = "SPRING";
                        Console.WriteLine("");
                        Console.ForegroundColor = ConsoleColor.DarkGreen;
                        Console.WriteLine(season);
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    else if ((monthInput == 4 || monthInput == 5 || monthInput == 6) && season != "SUMMER")
                    {
                        season = "SUMMER";
                        Console.WriteLine("");
                        Console.ForegroundColor = ConsoleColor.DarkYellow;
                        Console.WriteLine(season);
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    else if ((monthInput == 7 || monthInput == 8 || monthInput == 9) && season != "AUTUMN")
                    {
                        season = "AUTUMN";
                        Console.WriteLine("");
                        Console.ForegroundColor = ConsoleColor.DarkRed;
                        Console.WriteLine(season);
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    else if ((monthInput == 10 || monthInput == 11 || monthInput == 12) && season != "WINTER")
                    {
                        season = "WINTER";
                        Console.WriteLine("");
                        Console.ForegroundColor = ConsoleColor.DarkBlue;
                        Console.WriteLine(season);
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    // Year starts with march in this code. This if else statement for calculate correction
                    if (monthInput == 10)
                    {
                        monthInputControl = ((monthInput + 2) % 12) + 12;
                    }
                    else
                    {
                        monthInputControl = ((monthInput + 2) % 12);
                    }
                    // Year starts with march in this code. This if else statement for calculate correction
                    if (secondMonthInput == 10)
                    {
                        secondMonthInputControl = ((secondMonthInput + 2) % 12) + 12;
                    }
                    else
                    {
                        secondMonthInputControl = ((secondMonthInput + 2) % 12);
                    }
                    // Months that contain 31 days
                    if (monthInput == 1 || monthInput == 3 || monthInput == 5 || monthInput == 6 || monthInput == 8 || monthInput == 10 || monthInput == 11)
                    {
                        // for jumping to the year
                        count = (secondYearInput - firstYearInput);
                        if (monthInput == 11 && count != 0 && startingWithJan == true)
                        {
                            firstYearInput += 1;
                            count += -1;
                            startingWithJan = false;
                        }
                        //for jumping to next year
                        if (monthInput != 11)
                        {
                            startingWithJan = true;
                        }

                        if (monthInput > 10) // for correct calculation (january)
                        {
                            firstYearInput += -1;
                        }
                        // date calculation algorithm
                        century = firstYearInput / 100;
                        firstYearLastTwoDigit = firstYearInput % 100;
                        askedDay = Convert.ToInt32(Math.Floor((dayInput + (5 * century) + firstYearLastTwoDigit + Math.Floor(Convert.ToDouble(century) / 4) + Math.Floor(Convert.ToDouble(firstYearLastTwoDigit) / 4) + (2.6 * Convert.ToDouble(monthInput) - 0.2))) % 7);//step3 
                        switch (askedDay)
                        {
                            case 0:
                                stringAskedDay = "Sunday";
                                break;
                            case 1:
                                stringAskedDay = "Monday";
                                break;
                            case 2:
                                stringAskedDay = "Tuesday";
                                break;
                            case 3:
                                stringAskedDay = "Wednesday";
                                break;
                            case 4:
                                stringAskedDay = "Thursday";
                                break;
                            case 5:
                                stringAskedDay = "Friday";
                                break;
                            case 6:
                                stringAskedDay = "Saturday";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        switch (monthInput)
                        {
                            case 1:
                                stringMonthInput = "March";
                                break;
                            case 2:
                                stringMonthInput = "April";
                                break;
                            case 3:
                                stringMonthInput = "May";
                                break;
                            case 4:
                                stringMonthInput = "June";
                                break;
                            case 5:
                                stringMonthInput = "July";
                                break;
                            case 6:
                                stringMonthInput = "August";
                                break;
                            case 7:
                                stringMonthInput = "September";
                                break;
                            case 8:
                                stringMonthInput = "October";
                                break;
                            case 9:
                                stringMonthInput = "November";
                                break;
                            case 10:
                                stringMonthInput = "December";
                                break;
                            case 11:
                                stringMonthInput = "January";
                                break;
                            case 12:
                                stringMonthInput = "February";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        if (monthInput > 10) // for correct calculation (january)
                        {
                            firstYearInput += 1;
                        }
                        if (dayInput != 0 && (((secondDayInput >= dayInput || monthInputControl != secondMonthInputControl) || firstYearInput != secondYearInput) || breaker == -1))
                        {
                            Console.WriteLine(dayInput + " " + stringMonthInput + " " + firstYearInput + "-> " + stringAskedDay);
                        }
                        dayInput = dayInput + n;
                        if (dayInput > 31) //for jump to next month
                        {
                            breaker = dayInput;
                            dayInput = dayInput % 31;
                            monthInput = monthInput + 1;
                            monthInputControl = monthInputControl + 1;
                        }
                        if (monthInputControl == secondMonthInputControl && firstYearInput == secondYearInput) // for finishing the code
                        {
                            breaker = dayInput;
                        }
                    }
                    //for february 29
                    else if ((monthInput == 12 && firstYearInput % 100 != 0 && firstYearInput % 4 == 0) || (monthInput == 12 && firstYearInput % 100 == 0 && firstYearInput % 400 == 0))
                    {
                        if (monthInput > 10) // for correct calculation (february)
                        {
                            firstYearInput += -1;
                        }
                        // date calculation algorithm
                        century = firstYearInput / 100;
                        firstYearLastTwoDigit = firstYearInput % 100;
                        askedDay = Convert.ToInt32(Math.Floor((dayInput + (5 * century) + firstYearLastTwoDigit + Math.Floor(Convert.ToDouble(century) / 4) + Math.Floor(Convert.ToDouble(firstYearLastTwoDigit) / 4) + (2.6 * Convert.ToDouble(monthInput) - 0.2))) % 7);//step3 
                        switch (askedDay)
                        {
                            case 0:
                                stringAskedDay = "Sunday";
                                break;
                            case 1:
                                stringAskedDay = "Monday";
                                break;
                            case 2:
                                stringAskedDay = "Tuesday";
                                break;
                            case 3:
                                stringAskedDay = "Wednesday";
                                break;
                            case 4:
                                stringAskedDay = "Thursday";
                                break;
                            case 5:
                                stringAskedDay = "Friday";
                                break;
                            case 6:
                                stringAskedDay = "Saturday";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        switch (monthInput)
                        {
                            case 1:
                                stringMonthInput = "March";
                                break;
                            case 2:
                                stringMonthInput = "April";
                                break;
                            case 3:
                                stringMonthInput = "May";
                                break;
                            case 4:
                                stringMonthInput = "June";
                                break;
                            case 5:
                                stringMonthInput = "July";
                                break;
                            case 6:
                                stringMonthInput = "August";
                                break;
                            case 7:
                                stringMonthInput = "September";
                                break;
                            case 8:
                                stringMonthInput = "October";
                                break;
                            case 9:
                                stringMonthInput = "November";
                                break;
                            case 10:
                                stringMonthInput = "December";
                                break;
                            case 11:
                                stringMonthInput = "January";
                                break;
                            case 12:
                                stringMonthInput = "February";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        if (monthInput > 10) // for correct calculation (february)
                        {
                            firstYearInput += 1;
                        }
                        if (dayInput != 0 && (((secondDayInput >= dayInput || monthInputControl != secondMonthInputControl) || firstYearInput != secondYearInput) || breaker == -1))
                        {
                            Console.WriteLine(dayInput + " " + stringMonthInput + " " + firstYearInput + "-> " + stringAskedDay);
                        }
                        dayInput = dayInput + n;
                        if (dayInput > 29) //for jump to next month
                        {
                            breaker = dayInput;
                            dayInput = dayInput % 29;
                            monthInput = (monthInput + 1) % 12;
                            monthInputControl = monthInputControl + 1;
                        }
                        if (monthInputControl == secondMonthInputControl && firstYearInput == secondYearInput) // for finishing the code
                        {
                            breaker = dayInput;
                        }
                    }
                    //for february 28
                    else if ((monthInput == 12 && firstYearInput % 100 == 0 && firstYearInput % 400 != 0) || (monthInput == 12 && firstYearInput % 100 != 0 && firstYearInput % 400 != 0) || (monthInput == 12 && firstYearInput % 100 != 0 && firstYearInput % 4 != 0))
                    {
                        if (monthInput > 10) // for correct calculation (february)
                        {
                            firstYearInput += -1;
                        }
                        // date calculation algorithm
                        century = firstYearInput / 100;
                        firstYearLastTwoDigit = firstYearInput % 100;
                        askedDay = Convert.ToInt32(Math.Floor((dayInput + (5 * century) + firstYearLastTwoDigit + Math.Floor(Convert.ToDouble(century) / 4) + Math.Floor(Convert.ToDouble(firstYearLastTwoDigit) / 4) + (2.6 * Convert.ToDouble(monthInput) - 0.2))) % 7);//step3 
                        switch (askedDay)
                        {
                            case 0:
                                stringAskedDay = "Sunday";
                                break;
                            case 1:
                                stringAskedDay = "Monday";
                                break;
                            case 2:
                                stringAskedDay = "Tuesday";
                                break;
                            case 3:
                                stringAskedDay = "Wednesday";
                                break;
                            case 4:
                                stringAskedDay = "Thursday";
                                break;
                            case 5:
                                stringAskedDay = "Friday";
                                break;
                            case 6:
                                stringAskedDay = "Saturday";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        switch (monthInput)
                        {
                            case 1:
                                stringMonthInput = "March";
                                break;
                            case 2:
                                stringMonthInput = "April";
                                break;
                            case 3:
                                stringMonthInput = "May";
                                break;
                            case 4:
                                stringMonthInput = "June";
                                break;
                            case 5:
                                stringMonthInput = "July";
                                break;
                            case 6:
                                stringMonthInput = "August";
                                break;
                            case 7:
                                stringMonthInput = "September";
                                break;
                            case 8:
                                stringMonthInput = "October";
                                break;
                            case 9:
                                stringMonthInput = "November";
                                break;
                            case 10:
                                stringMonthInput = "December";
                                break;
                            case 11:
                                stringMonthInput = "January";
                                break;
                            case 12:
                                stringMonthInput = "February";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        if (monthInput > 10) // for correct calculation (february)
                        {
                            firstYearInput += 1;
                        }
                        if (dayInput != 0 && (((secondDayInput >= dayInput || monthInputControl != secondMonthInputControl) || firstYearInput != secondYearInput) || breaker == -1))
                        {
                            Console.WriteLine(dayInput + " " + stringMonthInput + " " + firstYearInput + "-> " + stringAskedDay);
                        }
                        dayInput = dayInput + n;
                        if (dayInput > 28) // for jump to next month
                        {
                            breaker = dayInput;
                            dayInput = dayInput % 28;
                            monthInput = (monthInput + 1) % 12;
                            monthInputControl = monthInputControl + 1;
                        }
                        if (monthInputControl == secondMonthInputControl && firstYearInput == secondYearInput) // for finishing the code
                        {
                            breaker = dayInput;
                        }
                    }
                    // Months that contains 30 days
                    else if (monthInput == 2 || monthInput == 4 || monthInput == 7 || monthInput == 9)
                    {
                        // date calculation algorithm
                        century = firstYearInput / 100;
                        firstYearLastTwoDigit = firstYearInput % 100;
                        askedDay = Convert.ToInt32(Math.Floor((dayInput + (5 * century) + firstYearLastTwoDigit + Math.Floor(Convert.ToDouble(century) / 4) + Math.Floor(Convert.ToDouble(firstYearLastTwoDigit) / 4) + (2.6 * Convert.ToDouble(monthInput) - 0.2))) % 7);//step3 

                        switch (askedDay)
                        {
                            case 0:
                                stringAskedDay = "Sunday";
                                break;
                            case 1:
                                stringAskedDay = "Monday";
                                break;
                            case 2:
                                stringAskedDay = "Tuesday";
                                break;
                            case 3:
                                stringAskedDay = "Wednesday";
                                break;
                            case 4:
                                stringAskedDay = "Thursday";
                                break;
                            case 5:
                                stringAskedDay = "Friday";
                                break;
                            case 6:
                                stringAskedDay = "Saturday";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        switch (monthInput)
                        {
                            case 1:
                                stringMonthInput = "March";
                                break;
                            case 2:
                                stringMonthInput = "April";
                                break;
                            case 3:
                                stringMonthInput = "May";
                                break;
                            case 4:
                                stringMonthInput = "June";
                                break;
                            case 5:
                                stringMonthInput = "July";
                                break;
                            case 6:
                                stringMonthInput = "August";
                                break;
                            case 7:
                                stringMonthInput = "September";
                                break;
                            case 8:
                                stringMonthInput = "October";
                                break;
                            case 9:
                                stringMonthInput = "November";
                                break;
                            case 10:
                                stringMonthInput = "December";
                                break;
                            case 11:
                                stringMonthInput = "January";
                                break;
                            case 12:
                                stringMonthInput = "February";
                                break;
                            default:
                                Console.WriteLine("error");
                                break;
                        }
                        if (dayInput != 0 && (((secondDayInput >= dayInput || monthInputControl != secondMonthInputControl) || firstYearInput != secondYearInput) || breaker == -1))
                        {
                            Console.WriteLine(dayInput + " " + stringMonthInput + " " + firstYearInput + "-> " + stringAskedDay);
                        }
                        dayInput = dayInput + n;
                        if (dayInput > 30) // for jump to next month
                        {
                            breaker = dayInput;
                            dayInput = dayInput % 30;
                            monthInput = monthInput + 1;
                            monthInputControl = monthInputControl + 1;
                        }
                        if (monthInputControl == secondMonthInputControl && firstYearInput == secondYearInput) // for finishing the code
                        {
                            breaker = dayInput;
                        }
                    }
                }
                //end
            }
            else if ((firstYearInput == secondYearInput) && (monthInput == secondMonthInput) && (dayInput == secondDayInput)) // for same dates
            {
                Console.WriteLine("The dates are same.");
            }
            else
            {
                Console.WriteLine("!! ERROR !!");
            }
            Console.WriteLine("");
            Console.WriteLine("Please press enter to exit.");
            Console.ReadLine();
            // source: https://sites.millersville.edu/bikenaga/number-theory/calendar/calendar.html
        }
    }
}

