using System;

namespace triansh
{
    class Program
    {
        static void Main(string[] args)
        {
            string stringAx, stringAy, stringBx, stringBy, stringCx, stringCy, firstPlayersName= "Yusuf Gassaloğlu", secondPlayersName = "Jack Sparrow", thirdPlayersName = "Peter Parker", newPlayersName = "";
            int Ax=0, Ay=0, Bx=0, By=0, Cx=0, Cy=0, Sx=0, Sy=0 ,firstPlayersPriority = 1, secondPlayersPriority = 2, thirdPlayersPriority = 3, newPlayersPriority = 3;
            double firstPlayersScore = 60, secondPlayersScore = 30, thirdPlayersScore = 24, newPlayersScore = 0;

            while (true)
            {               
                Console.Clear();
                // Menu Section
                // Menu 
                Console.WriteLine("*--* MENU *--*");
                Console.WriteLine(" ");
                Console.WriteLine("1- Enter Ship Location");
                Console.WriteLine("2- Ship Info");
                Console.WriteLine("3- Shoot At The Ship");
                Console.WriteLine("4- Show High Score Table");
                Console.WriteLine("5- How To Play");
                Console.WriteLine("6- Exit");
                Console.WriteLine(" ");
                Console.Write("Select an option (1,2,3,4,5,6): ");
                string option = Console.ReadLine();
                option = option.Trim(); // Removes blank spaces, solves space-related errors early
                // Enter Ship Location Section
                if (option == "1")
                {
                    Console.Clear();
                    // Player enters values.
                    while (true)
                    {
                        Ax = 0; Ay = 0; Bx = 0; By = 0; Cx = 0; Cy = 0; //Used for debugging if the player has already entered ship info and will update ship info.
                        while (true)
                        {
                            Console.Write("Please enter ABC triangles Ax value: ");
                            stringAx = Console.ReadLine();
                            stringAx = stringAx.Trim();
                            try
                            {
                                Ax = Convert.ToInt32(stringAx);
                            }
                            catch (FormatException)
                            {
                            }

                            if (Ax > 0 && Ax < 31)
                            {
                                break;
                            }
                            else
                            {
                                Console.WriteLine(" ");
                                Console.WriteLine("Please enter a positive integer between 1 and 30. (0,31)");                               
                                Console.WriteLine("*--*     *--*");
                                Console.WriteLine(" ");
                            }
                        }
                        while (true)
                        {
                            Console.Write("Please enter ABC triangles Ay value: ");
                            stringAy = Console.ReadLine();
                            stringAy = stringAy.Trim();
                            try
                            {
                                Ay = Convert.ToInt32(stringAy);

                            }
                            catch (FormatException)
                            {
                            }
                            if (Ay > 0 && Ay < 13)
                            {
                                break;
                            }
                            else
                            {
                                Console.WriteLine(" ");
                                Console.WriteLine("Please enter a positive integer between 1 and 12. (0,13)");
                                Console.WriteLine("*--*     *--*");
                                Console.WriteLine(" ");
                            }
                        }
                        while (true)
                        {
                            Console.Write("Please enter ABC triangles Bx value: ");
                            stringBx = Console.ReadLine();
                            stringBx = stringBx.Trim();
                            try
                            {
                                Bx = Convert.ToInt32(stringBx);
                            }
                            catch (FormatException)
                            {
                            }
                            if (Bx > 0 && Bx < 31)
                            {
                                break;
                            }
                            else
                            {
                                Console.WriteLine(" ");
                                Console.WriteLine("Please enter a positive integer between 1 and 30.(0,31)");
                                Console.WriteLine("*--*     *--*");
                                Console.WriteLine(" ");
                            }
                        }
                        while (true)
                        {
                            Console.Write("Please enter ABC triangles By value: ");
                            stringBy = Console.ReadLine();
                            stringBy = stringBy.Trim();
                            try
                            {
                                By = Convert.ToInt32(stringBy);
                            }
                            catch (FormatException)
                            {
                            }
                            if (By > 0 && By < 13)
                            {
                                break;
                            }
                            else
                            {
                                Console.WriteLine(" ");
                                Console.WriteLine("Please enter a positive integer between 1 and 12. (0,13)");                                
                                Console.WriteLine("*--*     *--*");
                                Console.WriteLine(" ");
                            }
                        }
                        while (true)
                        {
                            Console.Write("Please enter ABC triangles Cx value: ");
                            stringCx = Console.ReadLine();
                            stringCx = stringCx.Trim();
                            try
                            {
                                Cx = Convert.ToInt32(stringCx);
                            }
                            catch (FormatException)
                            {
                            }
                            if (Cx > 0 && Cx < 31)
                            {
                                break;
                            }
                            else
                            {
                                Console.WriteLine(" ");
                                Console.WriteLine("Please enter a positive integer between 1 and 30. (0,31)");
                                Console.WriteLine("*--*     *--*");
                                Console.WriteLine(" ");
                            }
                        }
                        while (true)
                        {
                            Console.Write("Please enter ABC triangles Cy value: ");
                            stringCy = Console.ReadLine();
                            stringCy = stringCy.Trim();
                            try
                            {
                                Cy = Convert.ToInt32(stringCy);
                            }
                            catch (FormatException)
                            {
                            }
                            if (Cy > 0 && Cy < 13)
                            {
                                break;
                            }
                            else
                            {
                                Console.WriteLine(" ");
                                Console.WriteLine("Please enter a positive integer between 1 adn 12. (0,13)");
                                Console.WriteLine("*--*");
                                Console.WriteLine(" ");
                            }
                        }
                        //Lenght of the edges
                        double c;
                        c = ((Ax - Bx) * (Ax - Bx)) + ((Ay - By) * (Ay - By));
                        c = Math.Sqrt(c);
                        double b;
                        b = ((Cx - Ax) * (Cx - Ax)) + ((Cy - Ay) * (Cy - Ay));
                        b = Math.Sqrt(b);
                        double a;
                        a = ((Bx - Cx) * (Bx - Cx) + ((By - Cy) * (By - Cy)));
                        a = Math.Sqrt(a);
                        // The ship is triangular or not section
                        if (Math.Round(a,2) + Math.Round(b,2) > Math.Round(c,2) && Math.Round(c,2) > Math.Abs(Math.Round(a,2) - Math.Round(b,2))) 
                        {
                            Console.ForegroundColor = ConsoleColor.DarkGreen;
                            Console.WriteLine("*--* SUCCESSFUL *--*");
                            Console.ForegroundColor = ConsoleColor.White;
                            Console.WriteLine("*--* Please press enter to go to menu. *--*");
                            Console.ReadLine();
                            break;
                        }
                        else
                        {
                            Console.ForegroundColor = ConsoleColor.DarkRed;
                            Console.WriteLine("x--x The values you entered do not form triangles. x--x");
                            Console.ForegroundColor = ConsoleColor.White;
                        }
                    }       
                }
                // Ship Info Section
                else if (option == "2")
                {
                    Console.Clear();
                    if (Ax == 0 && Bx == 0 && Cx == 0 && Ay == 0 && By == 0 && Cy == 0)
                    {
                        Console.WriteLine("*--* Please enter ship location first. *--*");
                        Console.ReadLine();
                    }
                    else
                    {
                        // Values are arranged to indicate the correct place in the coordinate system.
                        Ax = Ax + 2;
                        Bx = Bx + 2;
                        Cx = Cx + 2;
                        Ay = Ay + (-12);
                        Ay = Math.Abs(Ay);
                        By = By + (-12);
                        By = Math.Abs(By);
                        Cy = Cy + (-12);
                        Cy = Math.Abs(Cy);
                        // Drawing and showing the coordinate system.
                        int b1 = 12;
                        for (int a1 = 0; a1 < 12; a1++)
                        {
                            Console.SetCursorPosition(0, a1);
                            Console.WriteLine(b1);
                            b1 = b1 - 1;
                        }
                        for (int a1 = 0; a1 < 12; a1++)
                        {
                            Console.SetCursorPosition(2, a1);
                            Console.WriteLine("|");
                        }
                        b1 = 0;
                        for (int a1 = 0; a1 < 30; a1++)
                        {
                            Console.SetCursorPosition(a1 + 3, 13);
                            b1 = b1 + 1;
                            if (b1 == 10)
                            {
                                b1 = 0;
                            }
                            Console.WriteLine(b1);
                        }
                        for (int a1 = 3; a1 < 33; a1++)
                        {
                            Console.SetCursorPosition(a1, 12);
                            Console.WriteLine("-");
                        }
                        Console.SetCursorPosition(2, 12);
                        Console.WriteLine("+");
                        // Triangle representation by marking selected points in the coordinate system.
                        Console.ForegroundColor = ConsoleColor.DarkCyan;
                        Console.SetCursorPosition(Ax, Ay);
                        Console.WriteLine("A");
                        Console.SetCursorPosition(Bx, By);
                        Console.WriteLine("B");
                        Console.SetCursorPosition(Cx, Cy);
                        Console.WriteLine("C");
                        Console.ForegroundColor = ConsoleColor.White;
                        Console.SetCursorPosition(0, 15);
                        Console.WriteLine("*--* SHIP INFO *--*");
                        Console.SetCursorPosition(0, 16);
                        Console.WriteLine(" ");
                        Ax = Ax - 2;
                        Bx = Bx - 2;
                        Cx = Cx - 2;
                        Ay = 12 - Ay;    // convert ship coordinates to original values for mathematical operations.
                        By = 12 - By;
                        Cy = 12 - Cy;
                        // 1- The size of the ship (lenght of edges)
                        double c;
                        c = ((Ax - Bx) * (Ax - Bx)) + ((Ay - By) * (Ay - By));   // We can also use Math.pow() here after converting integers to double.
                        c = Math.Sqrt(c);
                        double b;
                        b = ((Cx - Ax) * (Cx - Ax)) + ((Cy - Ay) * (Cy - Ay));   // We can also use Math.pow() here after converting integers to double.
                        b = Math.Sqrt(b);
                        double a;
                        a = ((Bx - Cx) * (Bx - Cx) + ((By - Cy) * (By - Cy)));   // We can also use Math.pow() here after converting integers to double.
                        a = Math.Sqrt(a);
                        string LenghtOfTheEdges = String.Format("The size of the ship: a = {0:0.00} b = {1:0.00} c = {2:0.00}", a, b, c);
                        Console.WriteLine(LenghtOfTheEdges);
                        
                        // 2- The perimeter of the ship
                        double perimeterOfTheShip;
                        perimeterOfTheShip = a + b + c;
                        string formattedperimeterOfTheShip;
                        formattedperimeterOfTheShip = string.Format("The perimeter of the ship: {0:0.00}", perimeterOfTheShip);
                        Console.WriteLine(formattedperimeterOfTheShip);

                        // 3- The area of the ship
                        double u;
                        u = perimeterOfTheShip / 2;
                        double theAreaOfTheShip;
                        theAreaOfTheShip = Math.Sqrt(u * (u - a) * (u - b) * (u - c));
                        string formattedAreaOfTheShip;
                        formattedAreaOfTheShip = string.Format("The area of the ship: {0:0.00}", theAreaOfTheShip);
                        Console.WriteLine(formattedAreaOfTheShip);

                        // 4- The angles of the ship
                        double cosA;
                        cosA = (((a * a) - (b * b)) - (c * c)) / ((-2) * b * c);
                        double angelA;
                        angelA = 180 / Math.PI * (Math.Acos(cosA)); //You can use 3.141592 instead of Math.PI
                        double cosB;
                        cosB = (((b * b) - (a * a)) - (c * c)) / ((-2) * a * c);
                        double angelB;
                        angelB = 180 / Math.PI * (Math.Acos(cosB)); //You can use 3.141592 instead of Math.PI
                        double cosC;
                        cosC = (((c * c) - (a * a)) - (b * b)) / ((-2) * a * b);
                        double angelC;
                        angelC = 180 / Math.PI * (Math.Acos(cosC)); //You can use 3.141592 instead of Math.PI
                        string formattedAngels;
                        formattedAngels = string.Format("The angles of the ship: A = {0:0.00} B = {1:0.00} C = {2:0.00}", angelA, angelB, angelC);
                        Console.WriteLine(formattedAngels);

                        // 5- The median points of the ship
                        double cMedianX;
                        cMedianX = (Ax + Bx) / 2;
                        double cMedianY;
                        cMedianY = (Ay + By) / 2;
                        double bMedianX;
                        bMedianX = (Ax + Cx) / 2;
                        double bMedianY;
                        bMedianY = (Ay + Cy) / 2;
                        double aMedianX;
                        aMedianX = (Bx + Cx) / 2;
                        double aMedianY;
                        aMedianY = (By + Cy) / 2;
                        string formattedMedians;
                        formattedMedians = string.Format("The median points: a -> ({0},{1})  b -> ({2},{3})  c -> ({4},{5})", aMedianX, aMedianY, bMedianX, bMedianY, cMedianX, cMedianY);
                        Console.WriteLine(formattedMedians);

                        // 6- The centroid of the ship
                        double intSumX;
                        intSumX = (Ax + Bx + Cx);
                        double sumX = Convert.ToDouble(intSumX);
                        double intSumY;
                        intSumY = (Ay + By + Cy);
                        double sumY = Convert.ToDouble(intSumY);
                        double theCentroidOfTheShipX;
                        theCentroidOfTheShipX = sumX / 3;
                        double theCentroidOfTheShipY;
                        theCentroidOfTheShipY = sumY / 3;
                        string formattedTheCentroidOfTheShip;
                        formattedTheCentroidOfTheShip = string.Format("The centroid of the ship: ({0:0.00},{1:0.00})", theCentroidOfTheShipX, theCentroidOfTheShipY);
                        Console.WriteLine(formattedTheCentroidOfTheShip);

                        //7-The length of the bisector of the point A
                        double theLenghtOfTheBisector;
                        theLenghtOfTheBisector = ((2 * Math.Sqrt(b * c * u * (u - a)) / (b + c)));
                        string formattedTheLenghtOfTheBisector;
                        formattedTheLenghtOfTheBisector = string.Format("The length of the bisector: {0:0.00}", theLenghtOfTheBisector);
                        Console.WriteLine(formattedTheLenghtOfTheBisector);

                        //8-The area of the inscribed circles
                        double rInscribedCircle;
                        rInscribedCircle = theAreaOfTheShip / u;
                        double theAreaOfTheInscribedCricle;
                        theAreaOfTheInscribedCricle = (Math.PI) * rInscribedCircle * rInscribedCircle;
                        string formattedTheAreaOfTheInscribedCricle;
                        formattedTheAreaOfTheInscribedCricle = string.Format("The area of the inscribed circle: {0:0.00}", theAreaOfTheInscribedCricle);
                        Console.WriteLine(formattedTheAreaOfTheInscribedCricle);

                        //9 - The area of the circumscribed circles
                        double rCircumscribedCircle;
                        rCircumscribedCircle = (a * b * c) / (4 * theAreaOfTheShip);
                        double theAreaOfTheCircumscribedCircle;
                        theAreaOfTheCircumscribedCircle = (Math.PI) * rCircumscribedCircle * rCircumscribedCircle;
                        string formattedTheAreaOfTheCircumscribedCircle;
                        formattedTheAreaOfTheCircumscribedCircle = string.Format("The area of circumscribed circle: {0:0.00}", theAreaOfTheCircumscribedCircle);
                        Console.WriteLine(formattedTheAreaOfTheCircumscribedCircle);

                        //10-The type of the ship 
                        string typeOfTriangle1 = "", typeOfTriangle2 = "";
                        // Equilateral, Isosceles, Scalene
                        if (a == b && b == c && c == a)
                        {
                            typeOfTriangle1 = "Equilateral Triangle";
                        }
                        else if (a != b && b != c && c != a)
                        {
                            typeOfTriangle1 = "Scalene Triangle";
                        }
                        else if (a == b && c != a)
                        {
                            typeOfTriangle1 = "Isosceles triangle";
                        }
                        else if (c == a && b != a)
                        {
                            typeOfTriangle1 = "Isosceles triangle";
                        }
                        else if (b == c && a != c)
                        {
                            typeOfTriangle1 = "Isosceles triangle";
                        }
                        else
                        {
                            Console.WriteLine("Error");
                        }
                        //Right-angled , Acute-angled, Obtuse-angled
                        if (Math.Round(angelA, 2) == 90 || Math.Round(angelB, 2) == 90 || Math.Round(angelC, 2) == 90)
                        {
                            typeOfTriangle2 = "Right-angled";
                        }
                        else if (Math.Round(angelA, 2) < 90 && Math.Round(angelB, 2) < 90 && Math.Round(angelC, 2) < 90)
                        {
                            typeOfTriangle2 = "Acute-angled";
                        }
                        else if (Math.Round(angelA, 2) > 90 || Math.Round(angelB, 2) > 90 || Math.Round(angelC, 2) > 90)
                        {
                            typeOfTriangle2 = "Obtuse-angled";
                        }
                        else
                        {
                            Console.WriteLine("Error");
                        }
                        //You can use the Pythagorean theorem instead of angles.
                        string typeOfTriangle;
                        typeOfTriangle = string.Format("The type of the ship: {0} and {1}", typeOfTriangle1, typeOfTriangle2);
                        Console.WriteLine(typeOfTriangle);
                        Console.WriteLine("");
                        Console.WriteLine("*--* Please press enter to go to menu. *--*");
                        Console.ReadLine();
                    }
                }
                //Shoot at the ship section
                else if (option == "3")
                {
                    Console.Clear();
                    if (Ax == 0 && Bx == 0 && Cx == 0 && Ay == 0 && By == 0 && Cy == 0)
                    {
                        Console.WriteLine("*--* Please enter ship location first. *--*");
                        Console.ReadLine();
                    }
                    else
                    {
                        Random random = new Random(); // Random method for generating random integer.
                        Sx = random.Next(1, 31);    // X point where the bomb will fall           
                        Sy = random.Next(1, 13);    // Y point where the bomb will fall 
                        // Values are arranged to indicate the correct place in the coordinate system.
                        Ax = Ax + 2;
                        Bx = Bx + 2;
                        Cx = Cx + 2;
                        Ay = Ay + (-12);
                        Ay = Math.Abs(Ay);
                        By = By + (-12);
                        By = Math.Abs(By);
                        Cy = Cy + (-12);
                        Cy = Math.Abs(Cy);
                        Sx = Sx + 2;
                        Sy = Sy + (-12);
                        Sy = Math.Abs(Sy);
                        // Drawing and showing the coordinate system.
                        int b1 = 12;
                        for (int a1 = 0; a1 < 12; a1++)
                        {
                            Console.SetCursorPosition(0, a1);
                            Console.WriteLine(b1);
                            b1 = b1 - 1;
                        }
                        for (int a1 = 0; a1 < 12; a1++)
                        {
                            Console.SetCursorPosition(2, a1);
                            Console.WriteLine("|");
                        }
                        b1 = 0;
                        for (int a1 = 0; a1 < 30; a1++)
                        {
                            Console.SetCursorPosition(a1 + 3, 13);
                            b1 = b1 + 1;
                            if (b1 == 10)
                            {
                                b1 = 0;
                            }
                            Console.WriteLine(b1);
                        }
                        for (int a1 = 3; a1 < 33; a1++)
                        {
                            Console.SetCursorPosition(a1, 12);
                            Console.WriteLine("-");
                        }
                        Console.SetCursorPosition(2, 12);
                        Console.WriteLine("+");
                        // Triangle representation by marking selected points in the coordinate system.
                        Console.ForegroundColor = ConsoleColor.DarkCyan;
                        Console.SetCursorPosition(Ax, Ay);
                        Console.WriteLine("A");
                        Console.SetCursorPosition(Bx, By);
                        Console.WriteLine("B");
                        Console.SetCursorPosition(Cx, Cy);
                        Console.WriteLine("C");
                        Console.ForegroundColor = ConsoleColor.White;
                        Console.SetCursorPosition(0, 15);
                        Ax = Ax - 2;
                        Bx = Bx - 2;
                        Cx = Cx - 2;
                        Ay = 12 - Ay;
                        By = 12 - By;
                        Cy = 12 - Cy; // convert ship coordinates to original values for mathematical operations.
                        Sx = Sx - 2;
                        Sy = 12 - Sy;
                        //Ship sinking control algorithm
                        // "https://blackpawn.com/texts/pointinpoly/" another ways to control
                        //AXC triangle                  
                        double aAXC;
                        aAXC = ((Sx - Cx) * (Sx - Cx) + ((Sy - Cy) * (Sy - Cy)));
                        aAXC = Math.Sqrt(aAXC);
                        double xAXC;
                        xAXC = ((Ax - Cx) * (Ax - Cx) + ((Ay - Cy) * (Ay - Cy)));
                        xAXC = Math.Sqrt(xAXC);
                        double cAXC;
                        cAXC = ((Ax - Sx) * (Ax - Sx) + ((Ay - Sy) * (Ay - Sy)));
                        cAXC = Math.Sqrt(cAXC);
                        double perimeterOfTheAXC;
                        perimeterOfTheAXC = aAXC + xAXC + cAXC;
                        double uAXC;
                        uAXC = perimeterOfTheAXC / 2;
                        double theAreaOfTheAXC;
                        theAreaOfTheAXC = Math.Sqrt(uAXC * (uAXC - aAXC) * (uAXC - xAXC) * (uAXC - cAXC));
                        //AXB triangle
                        double aAXB;
                        aAXB = ((Sx - Bx) * (Sx - Bx) + ((Sy - By) * (Sy - By)));
                        aAXB = Math.Sqrt(aAXB);
                        double xAXB;
                        xAXB = ((Ax - Bx) * (Ax - Bx) + ((Ay - By) * (Ay - By)));
                        xAXB = Math.Sqrt(xAXB);
                        double bAXB;
                        bAXB = ((Ax - Sx) * (Ax - Sx) + ((Ay - Sy) * (Ay - Sy)));
                        bAXB = Math.Sqrt(bAXB);
                        double perimeterOfTheAXB;
                        perimeterOfTheAXB = aAXB + xAXB + bAXB;
                        double uAXB;
                        uAXB = perimeterOfTheAXB / 2;
                        double theAreaOfTheAXB;
                        theAreaOfTheAXB = Math.Sqrt(uAXB * (uAXB - aAXB) * (uAXB - bAXB) * (uAXB - xAXB));
                        //CXB triangle
                        double cCXB;
                        cCXB = ((Bx - Sx) * (Bx - Sx) + ((By - Sy) * (By - Sy)));
                        cCXB = Math.Sqrt(cCXB);
                        double xCXB;
                        xCXB = ((Bx - Cx) * (Bx - Cx) + ((By - Cy) * (By - Cy)));
                        xCXB = Math.Sqrt(xCXB);
                        double bCXB;
                        bCXB = ((Cx - Sx) * (Cx - Sx) + ((Cy - Sy) * (Cy - Sy)));
                        bCXB = Math.Sqrt(bCXB);
                       
                        double perimeterOfTheCXB;
                        perimeterOfTheCXB = cCXB + xCXB + bCXB;
                        double uCXB;
                        uCXB = perimeterOfTheCXB / 2;
                        double theAreaOfTheCXB;
                        theAreaOfTheCXB = Math.Sqrt(uCXB * (uCXB - xCXB) * (uCXB - bCXB) * (uCXB - cCXB));
                        double perimeterOfTheShip;
                        perimeterOfTheShip = xAXC + xAXB + xCXB;
                        double u;
                        u = perimeterOfTheShip / 2;
                        double theAreaOfTheShip;
                        theAreaOfTheShip = Math.Sqrt(u * (u - xCXB) * (u - xAXC) * (u - xAXB));
                        theAreaOfTheShip = theAreaOfTheShip + 0.0000001; // This line is required for more accurate results (eg. 0.1+0.2 != 0.3). This line is explained on the slide much better.
                        if (theAreaOfTheAXB + theAreaOfTheAXC + theAreaOfTheCXB > theAreaOfTheShip)
                        {
                            string score = String.Format("*--* Your ship survived! Total score is : {0:0.00} *--*", theAreaOfTheShip);
                            Console.ForegroundColor = ConsoleColor.DarkGreen;
                            Console.WriteLine(score);
                            Console.ForegroundColor = ConsoleColor.White;
                            Console.WriteLine("Please do not press enter while shooting. ");
                            Console.WriteLine("If you press enter while shooting the game will not save your name. ");
                            Console.WriteLine("*--* Obey The Rules *--*");
                            Sx = Sx + 2;
                            Sy = Sy + (-12);
                            Sy = Math.Abs(Sy);
                            Console.ForegroundColor = ConsoleColor.DarkRed;
                            Console.SetCursorPosition(Sx, Sy);
                            Console.WriteLine("X");
                            System.Threading.Thread.Sleep(1000);
                            Console.SetCursorPosition(Sx, Sy);
                            Console.WriteLine(" ");
                            System.Threading.Thread.Sleep(1000);
                            Console.SetCursorPosition(Sx, Sy);
                            Console.WriteLine("X");
                            System.Threading.Thread.Sleep(1000);                     
                            Console.SetCursorPosition(Sx, Sy);
                            Console.WriteLine(" ");
                            System.Threading.Thread.Sleep(1000);
                            Console.SetCursorPosition(Sx, Sy);
                            Console.WriteLine("X");
                            System.Threading.Thread.Sleep(1000);
                            Console.SetCursorPosition(Sx, Sy);
                            Console.WriteLine(" ");
                            System.Threading.Thread.Sleep(1000);
                            Console.SetCursorPosition(Sx, Sy);
                            Console.WriteLine("X");
                            Console.ForegroundColor = ConsoleColor.White;
                            System.Threading.Thread.Sleep(1000);
                            Sx = Sx - 2;
                            Sy = 12 - Sy;
                            Console.SetCursorPosition(0, 20);
                            Console.Write("Please enter your name: ");
                            newPlayersName = Console.ReadLine();
                            newPlayersScore = theAreaOfTheShip;
                            newPlayersPriority += 1;
                            Console.WriteLine("*--* Please press enter to go to menu. *--*");
                        }
                        else
                        {
                            Console.ForegroundColor = ConsoleColor.DarkRed;
                            Console.WriteLine("x--x Your ship sank! Total score is 0. x--x");
                            Console.ForegroundColor = ConsoleColor.White;
                            Console.WriteLine("Please do not press enter while shooting.");
                            Sx = Sx + 2;
                            Sy = Sy + (-12);
                            Sy = Math.Abs(Sy);
                            Console.ForegroundColor = ConsoleColor.DarkRed;
                            Console.SetCursorPosition(Sx, Sy);
                            Console.WriteLine("X");
                            System.Threading.Thread.Sleep(1000);
                            Console.SetCursorPosition(Sx, Sy);
                            Console.WriteLine(" ");
                            System.Threading.Thread.Sleep(1000);
                            Console.SetCursorPosition(Sx, Sy);
                            Console.WriteLine("X");
                            System.Threading.Thread.Sleep(1000);
                            Console.SetCursorPosition(Sx, Sy);                               
                            Console.WriteLine(" ");
                            System.Threading.Thread.Sleep(1000);
                            Console.SetCursorPosition(Sx, Sy);
                            Console.WriteLine("X");
                            System.Threading.Thread.Sleep(1000);
                            Console.SetCursorPosition(Sx, Sy);
                            Console.WriteLine(" ");
                            System.Threading.Thread.Sleep(1000);
                            Console.SetCursorPosition(Sx, Sy);
                            Console.WriteLine("X");
                            Console.ForegroundColor = ConsoleColor.White;
                            System.Threading.Thread.Sleep(1000);
                            Sx = Sx - 2;
                            Sy = 12 - Sy;
                            Console.ForegroundColor = ConsoleColor.White;
                            Console.SetCursorPosition(0, 18);
                            Console.WriteLine("*--* Press enter to go to menu. *--*");
                        }
                        Console.ReadLine();
                    }
                }
                //High score section
                else if (option == "4")
                {
                    Console.Clear();
                    if (newPlayersScore > firstPlayersScore) // If the new player gets the highest score. (1.st)
                    {
                        thirdPlayersName = secondPlayersName;
                        thirdPlayersScore = secondPlayersScore;
                        thirdPlayersPriority = secondPlayersPriority;

                        secondPlayersName = firstPlayersName;
                        secondPlayersScore = firstPlayersScore;
                        secondPlayersPriority = firstPlayersPriority;
                       
                        firstPlayersScore = newPlayersScore;
                        firstPlayersName = newPlayersName ;
                        firstPlayersPriority = newPlayersPriority;
                    }
                    else if (newPlayersScore == firstPlayersScore && newPlayersPriority < firstPlayersPriority) // If the new player gets the same score as 1.st player. (1.st)
                    {
                        thirdPlayersName = secondPlayersName;
                        thirdPlayersScore = secondPlayersScore;
                        thirdPlayersPriority = secondPlayersPriority;

                        secondPlayersName = firstPlayersName;
                        secondPlayersScore = firstPlayersScore;
                        secondPlayersPriority = firstPlayersPriority;

                        firstPlayersScore = newPlayersScore;
                        firstPlayersName = newPlayersName;
                        firstPlayersPriority = newPlayersPriority;
                    }
                    else if (newPlayersScore == firstPlayersScore && newPlayersPriority > firstPlayersPriority) // If the new player gets the same score as 1.st player. (2.nd)
                    {
                        thirdPlayersName = secondPlayersName;
                        thirdPlayersScore = secondPlayersScore;
                        thirdPlayersPriority = secondPlayersPriority;

                        secondPlayersScore = newPlayersScore;
                        secondPlayersName = newPlayersName;
                        secondPlayersPriority = newPlayersPriority;
                    }
                    else if (newPlayersScore > secondPlayersScore) // If the new player gets the second highest score. (2.nd)
                    {
                        thirdPlayersName = secondPlayersName;
                        thirdPlayersScore = secondPlayersScore;
                        thirdPlayersPriority = secondPlayersPriority;

                        secondPlayersScore = newPlayersScore;
                        secondPlayersName = newPlayersName;
                        secondPlayersPriority = newPlayersPriority;
                    }
                    else if (newPlayersScore == secondPlayersScore && newPlayersPriority < secondPlayersPriority)// If the new player gets the same score as 2.nd player. (2.nd) 
                    {
                        thirdPlayersName = secondPlayersName;
                        thirdPlayersScore = secondPlayersScore;
                        thirdPlayersPriority = secondPlayersPriority;

                        secondPlayersScore = newPlayersScore;
                        secondPlayersName = newPlayersName;
                        secondPlayersPriority = newPlayersPriority;
                    }
                    else if (newPlayersScore == secondPlayersScore && newPlayersPriority > secondPlayersPriority)// If the new player gets the same score as 2.nd player. (3.rd)
                    {
                        thirdPlayersScore = newPlayersScore;
                        thirdPlayersName = newPlayersName;
                        thirdPlayersPriority = newPlayersPriority;
                    }
                    else if (newPlayersScore > thirdPlayersScore)// If the new player gets the same score as 3.rd player. (3.rd) 
                    {
                        thirdPlayersScore = newPlayersScore;
                        thirdPlayersName = newPlayersName;
                        thirdPlayersPriority = newPlayersPriority;
                    }
                    else if (newPlayersScore == thirdPlayersScore && newPlayersPriority < thirdPlayersPriority)// If the new player gets the same score as 3.rd player. (3.rd)
                    {
                        thirdPlayersScore = newPlayersScore;
                        thirdPlayersName = newPlayersName;
                        thirdPlayersPriority = newPlayersPriority;
                    }
                    newPlayersName = "";
                    newPlayersScore = 0;
                    Console.WriteLine("*--* HIGH SCORE TABLE *--*");
                    Console.WriteLine("           TOP 3          ");
                    Console.WriteLine(" ");
                    Console.WriteLine("1- " + firstPlayersName + " " + Math.Round(firstPlayersScore, 2));
                    Console.WriteLine("2- "+ secondPlayersName +" "+ Math.Round(secondPlayersScore,2));
                    Console.WriteLine("3- "+ thirdPlayersName +" "+ Math.Round(thirdPlayersScore,2));
                    Console.WriteLine(" ");
                    Console.WriteLine("*--* Please press enter to go to menu. *--*");
                    Console.ReadLine();
                }
                //How To Play section
                else if (option == "5")
                {
                    Console.Clear();
                    Console.WriteLine("*--*     *--*");
                    Console.WriteLine(" ");
                    Console.WriteLine("1- The player creates a triangle battleship and tries to dodge from a random shot.");
                    Console.WriteLine("2- If the ship survives, the player gets a point which is the area of the ship.");
                    Console.WriteLine("3- The game area is 30 * 12 units.");
                    Console.WriteLine("4- If the player gets a score that is equal to a score in the table, the name of the player is placed under the old one.");
                    Console.WriteLine(" ");
                    Console.WriteLine("*--*     *--*");
                    Console.WriteLine(" ");
                    Console.WriteLine("*--* Please press enter to go to menu. *--*");
                    Console.ReadLine();
                }
                //Exit Section
                else if (option == "6")
                {
                    break;
                }
                // Menu errors section
                else
                {
                    Console.WriteLine("");
                    Console.WriteLine("*--* Please enter only 1,2,3,4,5,6. *--*");
                    Console.WriteLine("*--* Please press enter to select an option again. *--*");
                    Console.WriteLine("");
                    Console.ReadLine();
                }
            }
        }
    }
}
