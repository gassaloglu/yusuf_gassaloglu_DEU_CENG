using System;

namespace itTakesTwoToTango
{
    class Program
    {
        static void Main(string[] args)
        {
            string[] firstLine = new string[30], secondLine = new string[30], thirdLine = new string[30];
            int lineOnTheTable, columnOnTheTable, score = 0, matchCount = 0;
            for (int i = 0; i < 30; i++)
            {
                firstLine[i] = " "; secondLine[i] = " "; thirdLine[i] = " ";
            }
            Console.ForegroundColor = ConsoleColor.DarkYellow;
            Console.WriteLine("+------------------------------+");
            for (int i = 0; i < 3; i++)
            {
                Console.WriteLine("|                              |");
            }
            Console.WriteLine("+------------------------------+");
            Console.ResetColor();
            // for print how to play section
            Console.SetCursorPosition(9, 6); Console.ForegroundColor = ConsoleColor.Cyan; Console.WriteLine("-HOW TO PLAY-"); Console.ResetColor();
            Console.SetCursorPosition(2, 8); Console.WriteLine("- The game is played on a 3x30 board.");
            Console.SetCursorPosition(2, 10); Console.WriteLine("- In the beginning, the board is randomly  filled \n with 30 random numbers which are 1, 2 and 3.");
            Console.SetCursorPosition(2, 13); Console.WriteLine("- The arrow keys on the keyboard are used to move the cursor.");
            Console.SetCursorPosition(2, 15); Console.WriteLine("- WASD keys are used to move the number under the cursor. \nEsc used to exit from game.");
            Console.SetCursorPosition(2, 18); Console.WriteLine("- WASD keys can move only the single numbers \n(the left and right side of the number should be empty).");
            Console.SetCursorPosition(2, 21); Console.WriteLine("- In the beginning, the board is randomly filled with 30 random numbers which are 1, 2 and 3.");
            Console.SetCursorPosition(8, 22); Console.WriteLine("W : Moves the number one square up. ");
            Console.SetCursorPosition(8, 23); Console.WriteLine("S : Moves the number one square down. ");
            Console.SetCursorPosition(8, 24); Console.WriteLine("A : Moves the number to the left as much as it can go.");
            Console.SetCursorPosition(8, 25); Console.WriteLine("D : Moves the number to the right as much as it can go.");
            Console.SetCursorPosition(2, 27); Console.WriteLine("- If two identical numbers come together on the same line (by player moves or randomly)");
            Console.SetCursorPosition(8, 28); Console.WriteLine("Matching numbers are deleted from the board.");
            Console.SetCursorPosition(8, 29); Console.WriteLine("The player's score increases by 10 points.");
            Console.SetCursorPosition(8, 30); Console.WriteLine("New two random numbers are generated and randomly placed on the board.");
            // for print rank system
            Console.SetCursorPosition(72, 1); Console.ForegroundColor = ConsoleColor.Cyan; Console.WriteLine("-RANK SYSTEM-"); Console.ResetColor();
            Console.SetCursorPosition(67, 2); Console.WriteLine("0 - 500 points --> Rookie");
            Console.SetCursorPosition(67, 3); Console.WriteLine("501 - 1000 points --> Semi-Pro");
            Console.SetCursorPosition(67, 4); Console.WriteLine("1001 - 1500 points --> Professional");
            Console.SetCursorPosition(67, 5); Console.WriteLine("1501 - 2000 points --> Veteran");
            Console.SetCursorPosition(67, 6); Console.WriteLine("2001 - 2500 points --> Expert");
            Console.SetCursorPosition(67, 7); Console.WriteLine("2501 - 5000 points --> Master");
            Console.SetCursorPosition(67, 8); Console.WriteLine("5001 - 10000 points --> Legend");
            Console.SetCursorPosition(67, 9); Console.WriteLine("10000+ points --> ? *-* ?");
            // Generating random numbers on the board
            for (int i = 0; i < 30; i++)
            {
                int numberOnTheTable;
                Random random = new();
                columnOnTheTable = random.Next(0, 30);
                lineOnTheTable = random.Next(0, 3);
                numberOnTheTable = random.Next(1, 4);
                // for check if cell is empty
                if (lineOnTheTable == 0 && firstLine[columnOnTheTable] == " ")
                {
                    firstLine[columnOnTheTable] = Convert.ToString(numberOnTheTable);
                    Console.SetCursorPosition(columnOnTheTable + 1, 1); Console.WriteLine(numberOnTheTable);
                }
                else if (lineOnTheTable == 1 && secondLine[columnOnTheTable] == " ")
                {
                    secondLine[columnOnTheTable] = Convert.ToString(numberOnTheTable);
                    Console.SetCursorPosition(columnOnTheTable + 1, 2); Console.WriteLine(numberOnTheTable);
                }
                else if (lineOnTheTable == 2 && thirdLine[columnOnTheTable] == " ")
                {
                    thirdLine[columnOnTheTable] = Convert.ToString(numberOnTheTable);
                    Console.SetCursorPosition(columnOnTheTable + 1, 3); Console.WriteLine(numberOnTheTable);
                }
                else i--;
            }
            int cursorx = 1, cursory = 1;   // position of cursor
            ConsoleKeyInfo cki;               // required for readkey
            // Main game loop
            while (true)
            {
                if (Console.KeyAvailable)
                {   //read key and take action
                    cki = Console.ReadKey(true);
                    if (cki.Key == ConsoleKey.RightArrow && cursorx < 30) { cursorx++; Console.SetCursorPosition(cursorx, cursory); } // if user presses right arrow
                    else if (cki.Key == ConsoleKey.LeftArrow && cursorx > 1) { cursorx--; Console.SetCursorPosition(cursorx, cursory); } // if user presses left arrow
                    else if (cki.Key == ConsoleKey.UpArrow && cursory > 1) { cursory--; Console.SetCursorPosition(cursorx, cursory); } // if user presses up arrow
                    else if (cki.Key == ConsoleKey.DownArrow && cursory < 3) { cursory++; Console.SetCursorPosition(cursorx, cursory); } // if user presses down arrow
                    else if (cki.Key == ConsoleKey.W) // if user presses W
                    {
                        if (cursory == 3)
                        {
                            if ((cursorx != 1 && cursorx != 30 && thirdLine[cursorx - 2] == " " && thirdLine[cursorx] == " ") || (cursorx == 1 && thirdLine[cursorx] == " ") || (cursorx == 30 && thirdLine[cursorx - 2] == " "))// for move the single number
                            {
                                if (thirdLine[cursorx - 1] != " ")// checks if there is a number in the cell that cursor is located
                                {
                                    if (secondLine[cursorx - 1] == " ")// checks if there is a number in the cell that cursor will be located
                                    {
                                        secondLine[cursorx - 1] = thirdLine[cursorx - 1];
                                        thirdLine[cursorx - 1] = " ";
                                        for (int i = 0; i < 30; i++) // print the new board
                                        {
                                            Console.SetCursorPosition(i + 1, 2);
                                            Console.WriteLine(secondLine[i]);
                                            Console.SetCursorPosition(i + 1, 3);
                                            Console.WriteLine(thirdLine[i]);
                                        }
                                        cursory--;
                                    }
                                }
                            }
                        }
                        else if (cursory == 2)
                        {
                            if ((cursorx != 1 && cursorx != 30 && secondLine[cursorx - 2] == " " && secondLine[cursorx] == " ") || (cursorx == 1 && secondLine[cursorx] == " ") || (cursorx == 30 && secondLine[cursorx - 2] == " "))// for move the single number
                            {
                                if (secondLine[cursorx - 1] != " ")// checks if there is a number in the cell that cursor is located
                                {
                                    if (firstLine[cursorx - 1] == " ")// checks if there is a number in the cell that cursor will be located
                                    {   // for move value
                                        firstLine[cursorx - 1] = secondLine[cursorx - 1];
                                        secondLine[cursorx - 1] = " ";
                                        for (int i = 0; i < 30; i++) // print the new board
                                        {
                                            Console.SetCursorPosition(i + 1, 1);
                                            Console.WriteLine(firstLine[i]);
                                            Console.SetCursorPosition(i + 1, 2);
                                            Console.WriteLine(secondLine[i]);
                                        }
                                        cursory--;
                                    }
                                }

                            }
                        }
                    }
                    else if (cki.Key == ConsoleKey.S) // if user presses S
                    {
                        if (cursory == 1)
                        {
                            if ((cursorx != 1 && cursorx != 30 && firstLine[cursorx - 2] == " " && firstLine[cursorx] == " ") || (cursorx == 1 && firstLine[cursorx] == " ") || (cursorx == 30 && firstLine[cursorx - 2] == " "))// for move the single number
                            {
                                if (firstLine[cursorx - 1] != " ")// checks if there is a number in the cell that cursor is located
                                {
                                    if (secondLine[cursorx - 1] == " ")// checks if there is a number in the cell that cursor will be located
                                    {   // for move value
                                        secondLine[cursorx - 1] = firstLine[cursorx - 1];
                                        firstLine[cursorx - 1] = " ";
                                        for (int i = 0; i < 30; i++) // print the new board
                                        {
                                            Console.SetCursorPosition(i + 1, 1);
                                            Console.WriteLine(firstLine[i]);
                                            Console.SetCursorPosition(i + 1, 2);
                                            Console.WriteLine(secondLine[i]);
                                        }
                                        cursory++;
                                    }
                                }
                            }
                        }
                        else if (cursory == 2)
                        {
                            if ((cursorx != 1 && cursorx != 30 && secondLine[cursorx - 2] == " " && secondLine[cursorx] == " ") || (cursorx == 1 && secondLine[cursorx] == " ") || (cursorx == 30 && secondLine[cursorx - 2] == " "))// for move the single number
                            {
                                if (secondLine[cursorx - 1] != " ")// checks if there is a number in the cell that cursor is located
                                {
                                    if (thirdLine[cursorx - 1] == " ")// checks if there is a number in the cell that cursor will be located
                                    {   // for move value
                                        thirdLine[cursorx - 1] = secondLine[cursorx - 1];
                                        secondLine[cursorx - 1] = " ";
                                        for (int i = 0; i < 30; i++) // print the new board
                                        {
                                            Console.SetCursorPosition(i + 1, 2);
                                            Console.WriteLine(secondLine[i]);
                                            Console.SetCursorPosition(i + 1, 3);
                                            Console.WriteLine(thirdLine[i]);
                                        }
                                        cursory++;
                                    }
                                }
                            }
                        }
                    }
                    else if ((cki.Key == ConsoleKey.A) && cursorx > 1)// if user presses A
                    {
                        if (cursory == 3)
                        {
                            if ((cursorx != 1 && cursorx != 30 && thirdLine[cursorx - 2] == " " && thirdLine[cursorx] == " ") || (cursorx == 1 && thirdLine[cursorx] == " ") || (cursorx == 30 && thirdLine[cursorx - 2] == " "))// for move the single number
                            {
                                if (thirdLine[cursorx - 1] != " ")// checks if there is a number in the cell that cursor is located
                                {
                                    if (thirdLine[cursorx - 2] == " ")// checks if there is a number in the cell that cursor will be located
                                    {
                                        while (cursorx > 1 && thirdLine[cursorx - 2] == " ")
                                        {
                                            thirdLine[cursorx - 2] = thirdLine[cursorx - 1];
                                            thirdLine[cursorx - 1] = " ";
                                            cursorx--;
                                        }
                                        for (int i = 0; i < 30; i++) // print the new board
                                        {
                                            Console.SetCursorPosition(i + 1, 3);
                                            Console.WriteLine(thirdLine[i]);
                                        }
                                    }
                                }
                            }
                        }
                        else if (cursory == 2)
                        {
                            if ((cursorx != 1 && cursorx != 30 && secondLine[cursorx - 2] == " " && secondLine[cursorx] == " ") || (cursorx == 1 && secondLine[cursorx] == " ") || (cursorx == 30 && secondLine[cursorx - 2] == " "))// for move the single number
                            {
                                if (secondLine[cursorx - 1] != " ")// checks if there is a number in the cell that cursor is located
                                {
                                    if (secondLine[cursorx - 2] == " ")// checks if there is a number in the cell that cursor will be located
                                    {
                                        while (cursorx > 1 && secondLine[cursorx - 2] == " ")
                                        {
                                            secondLine[cursorx - 2] = secondLine[cursorx - 1];
                                            secondLine[cursorx - 1] = " ";
                                            cursorx--;
                                        }
                                        for (int i = 0; i < 30; i++) // print the new board
                                        {
                                            Console.SetCursorPosition(i + 1, 2);
                                            Console.WriteLine(secondLine[i]);
                                        }
                                    }
                                }
                            }
                        }
                        else if (cursory == 1)
                        {
                            if ((cursorx != 1 && cursorx != 30 && firstLine[cursorx - 2] == " " && firstLine[cursorx] == " ") || (cursorx == 1 && firstLine[cursorx] == " ") || (cursorx == 30 && firstLine[cursorx - 2] == " "))// for move the single number
                            {
                                if (firstLine[cursorx - 1] != " ")// checks if there is a number in the cell that cursor is located
                                {
                                    if (firstLine[cursorx - 2] == " ")// checks if there is a number in the cell that cursor will be located
                                    {
                                        while (cursorx > 1 && firstLine[cursorx - 2] == " ")
                                        {
                                            firstLine[cursorx - 2] = firstLine[cursorx - 1];
                                            firstLine[cursorx - 1] = " ";
                                            cursorx--;
                                        }
                                        for (int i = 0; i < 30; i++) // print the new board
                                        {
                                            Console.SetCursorPosition(i + 1, 1);
                                            Console.WriteLine(firstLine[i]);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else if ((cki.Key == ConsoleKey.D) && cursorx < 30) // if user presses D
                    {
                        if (cursory == 3)
                        {
                            if ((cursorx != 1 && cursorx != 30 && thirdLine[cursorx - 2] == " " && thirdLine[cursorx] == " ") || (cursorx == 1 && thirdLine[cursorx] == " ") || (cursorx == 30 && thirdLine[cursorx - 2] == " "))// for move the single number
                            {
                                if (thirdLine[cursorx - 1] != " ")// checks if there is a number in the cell that cursor is located
                                {
                                    if (thirdLine[cursorx] == " ")// checks if there is a number in the cell that cursor will be located
                                    {
                                        while (cursorx < 30 && thirdLine[cursorx] == " ")
                                        {
                                            thirdLine[cursorx] = thirdLine[cursorx - 1];
                                            thirdLine[cursorx - 1] = " ";
                                            cursorx++;
                                        }
                                        for (int i = 0; i < 30; i++) // print the new board
                                        {
                                            Console.SetCursorPosition(i + 1, 3);
                                            Console.WriteLine(thirdLine[i]);
                                        }
                                    }
                                }
                            }
                        }
                        else if (cursory == 2)
                        {
                            if ((cursorx != 1 && cursorx != 30 && secondLine[cursorx - 2] == " " && secondLine[cursorx] == " ") || (cursorx == 1 && secondLine[cursorx] == " ") || (cursorx == 30 && secondLine[cursorx - 2] == " "))// for move the single number
                            {
                                if (secondLine[cursorx - 1] != " ")// checks if there is a number in the cell that cursor is located
                                {
                                    if (secondLine[cursorx] == " ")// checks if there is a number in the cell that cursor will be located
                                    {
                                        while (cursorx < 30 && secondLine[cursorx] == " ")
                                        {
                                            secondLine[cursorx] = secondLine[cursorx - 1];
                                            secondLine[cursorx - 1] = " ";
                                            cursorx++;
                                        }
                                        for (int i = 0; i < 30; i++) // print the new board
                                        {
                                            Console.SetCursorPosition(i + 1, 2);
                                            Console.WriteLine(secondLine[i]);
                                        }
                                    }
                                }
                            }
                        }
                        else if (cursory == 1)
                        {
                            if ((cursorx != 1 && cursorx != 30 && firstLine[cursorx - 2] == " " && firstLine[cursorx] == " ") || (cursorx == 1 && firstLine[cursorx] == " ") || (cursorx == 30 && firstLine[cursorx - 2] == " "))// for move the single number
                            {
                                if (firstLine[cursorx - 1] != " ")// checks if there is a number in the cell that cursor is located
                                {
                                    if (firstLine[cursorx] == " ")// checks if there is a number in the cell that cursor will be located
                                    {
                                        while (cursorx < 30 && firstLine[cursorx] == " ")
                                        {
                                            firstLine[cursorx] = firstLine[cursorx - 1];
                                            firstLine[cursorx - 1] = " ";
                                            cursorx++;
                                        }
                                        for (int i = 0; i < 30; i++) // print the new board
                                        {
                                            Console.SetCursorPosition(i + 1, 1);
                                            Console.WriteLine(firstLine[i]);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else if ((cki.Key == ConsoleKey.Escape)) { Console.SetCursorPosition(0, 40); break; }; // if user presses Esc
                }
                for (int j = 0; j < 29; j++)
                {   // horizontal check
                    if ((firstLine[j] == firstLine[j + 1]) && firstLine[j] != " ")
                    {
                        firstLine[j] = " "; firstLine[j + 1] = " ";
                        for (int i = 0; i < 30; i++) // print the new board
                        {
                            Console.SetCursorPosition(i + 1, 1);
                            Console.WriteLine(firstLine[i]);
                        }
                        score += 10;
                        matchCount += 2;
                        if (OperatingSystem.IsWindows()) Console.Beep(1320, 100); //beep sound for matching
                        Console.SetCursorPosition(40, 1); Console.WriteLine("Your score is" + " -> " + score);
                        Console.SetCursorPosition(40, 2);
                        if (score < 500) { Console.WriteLine("RANK: Rookie      "); }
                        else if (score > 500 && score < 1000) { Console.ForegroundColor = ConsoleColor.Yellow; Console.WriteLine("RANK: Semi-Pro    "); Console.ResetColor(); }
                        else if (score > 1000 && score < 1500) { Console.ForegroundColor = ConsoleColor.DarkYellow; Console.WriteLine("RANK: Professional"); Console.ResetColor(); }
                        else if (score > 1500 && score < 2000) { Console.ForegroundColor = ConsoleColor.Cyan; Console.WriteLine("RANK: Veteran     "); Console.ResetColor(); }
                        else if (score > 2000 && score < 2500) { Console.ForegroundColor = ConsoleColor.Green; Console.WriteLine("RANK: Expert      "); Console.ResetColor(); }
                        else if (score > 2500 && score < 5000) { Console.ForegroundColor = ConsoleColor.Blue; Console.WriteLine("RANK: Master      "); Console.ResetColor(); }
                        else if (score > 5000 && score < 10000) { Console.ForegroundColor = ConsoleColor.Magenta; Console.WriteLine("RANK: Legend     "); Console.ResetColor(); }
                        else if (score > 10000) { Console.SetCursorPosition(37, 2); Console.ForegroundColor = ConsoleColor.Red; Console.WriteLine("*-* I THINK YOU ARE GOD *-*"); Console.ResetColor(); }
                    }
                    if ((secondLine[j] == secondLine[j + 1]) && secondLine[j] != " ")
                    {
                        secondLine[j] = " "; secondLine[j + 1] = " ";
                        for (int i = 0; i < 30; i++) // print the new board
                        {
                            Console.SetCursorPosition(i + 1, 2);
                            Console.WriteLine(secondLine[i]);
                        }
                        score += 10;
                        matchCount += 2;
                        if (OperatingSystem.IsWindows()) Console.Beep(1320, 100); //beep sound for matching
                        Console.SetCursorPosition(40, 1); Console.WriteLine("Your score is" + " -> " + score);
                        Console.SetCursorPosition(40, 2);
                        if (score < 500) { Console.WriteLine("RANK: Rookie      "); }
                        else if (score > 500 && score < 1000) { Console.ForegroundColor = ConsoleColor.Yellow; Console.WriteLine("RANK: Semi-Pro    "); Console.ResetColor(); }
                        else if (score > 1000 && score < 1500) { Console.ForegroundColor = ConsoleColor.DarkYellow; Console.WriteLine("RANK: Professional"); Console.ResetColor(); }
                        else if (score > 1500 && score < 2000) { Console.ForegroundColor = ConsoleColor.Cyan; Console.WriteLine("RANK: Veteran     "); Console.ResetColor(); }
                        else if (score > 2000 && score < 2500) { Console.ForegroundColor = ConsoleColor.Green; Console.WriteLine("RANK: Expert      "); Console.ResetColor(); }
                        else if (score > 2500 && score < 5000) { Console.ForegroundColor = ConsoleColor.Blue; Console.WriteLine("RANK: Master      "); Console.ResetColor(); }
                        else if (score > 5000 && score < 10000) { Console.ForegroundColor = ConsoleColor.Magenta; Console.WriteLine("RANK: Legend     "); Console.ResetColor(); }
                        else if (score > 10000) { Console.SetCursorPosition(37, 2); Console.ForegroundColor = ConsoleColor.Red; Console.WriteLine("*-* I THINK YOU ARE GOD *-*"); Console.ResetColor(); }
                    }
                    if ((thirdLine[j] == thirdLine[j + 1]) && thirdLine[j] != " ")
                    {
                        thirdLine[j] = " "; thirdLine[j + 1] = " ";
                        for (int i = 0; i < 30; i++) // print the new board
                        {
                            Console.SetCursorPosition(i + 1, 3);
                            Console.WriteLine(thirdLine[i]);
                        }
                        score += 10;
                        matchCount += 2;
                        if (OperatingSystem.IsWindows()) Console.Beep(1320, 100); //beep sound for matching
                        Console.SetCursorPosition(40, 1); Console.WriteLine("Your score is" + " -> " + score);
                        Console.SetCursorPosition(40, 2);
                        if (score < 500) { Console.WriteLine("RANK: Rookie      "); }
                        else if (score > 500 && score < 1000) { Console.ForegroundColor = ConsoleColor.Yellow; Console.WriteLine("RANK: Semi-Pro    "); Console.ResetColor(); }
                        else if (score > 1000 && score < 1500) { Console.ForegroundColor = ConsoleColor.DarkYellow; Console.WriteLine("RANK: Professional"); Console.ResetColor(); }
                        else if (score > 1500 && score < 2000) { Console.ForegroundColor = ConsoleColor.Cyan; Console.WriteLine("RANK: Veteran     "); Console.ResetColor(); }
                        else if (score > 2000 && score < 2500) { Console.ForegroundColor = ConsoleColor.Green; Console.WriteLine("RANK: Expert      "); Console.ResetColor(); }
                        else if (score > 2500 && score < 5000) { Console.ForegroundColor = ConsoleColor.Blue; Console.WriteLine("RANK: Master      "); Console.ResetColor(); }
                        else if (score > 5000 && score < 10000) { Console.ForegroundColor = ConsoleColor.Magenta; Console.WriteLine("RANK: Legend     "); Console.ResetColor(); }
                        else if (score > 10000) { Console.SetCursorPosition(37, 2); Console.ForegroundColor = ConsoleColor.Red; Console.WriteLine("*-* I THINK YOU ARE GOD *-*"); Console.ResetColor(); }
                    }
                }
                // Generating new numbers to the board
                for (int i = 0; i < matchCount; i++)
                {
                    int numberOnTheTable;
                    Random random = new();
                    columnOnTheTable = random.Next(0, 30);
                    lineOnTheTable = random.Next(0, 3);
                    numberOnTheTable = random.Next(1, 4);
                    // for check if cell is empty
                    if (lineOnTheTable == 0 && firstLine[columnOnTheTable] == " ")
                    {
                        firstLine[columnOnTheTable] = Convert.ToString(numberOnTheTable);
                        Console.SetCursorPosition(columnOnTheTable + 1, 1); Console.WriteLine(numberOnTheTable);
                    }
                    else if (lineOnTheTable == 1 && secondLine[columnOnTheTable] == " ")
                    {
                        secondLine[columnOnTheTable] = Convert.ToString(numberOnTheTable);
                        Console.SetCursorPosition(columnOnTheTable + 1, 2); Console.WriteLine(numberOnTheTable);
                    }
                    else if (lineOnTheTable == 2 && thirdLine[columnOnTheTable] == " ")
                    {
                        thirdLine[columnOnTheTable] = Convert.ToString(numberOnTheTable);
                        Console.SetCursorPosition(columnOnTheTable + 1, 3); Console.WriteLine(numberOnTheTable);
                    }
                    else i--;
                }
                Console.SetCursorPosition(cursorx, cursory);
                matchCount = 0;
            }
        }
    }
}



