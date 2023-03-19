using System;

namespace DEUgame
{
    class Program
    {
        static void Main(string[] args)
        {
            string[] names = { "new player","Derya", "Elife", "Fatih", "Ali", "Azra", "Sibel", "Cem", "Nazan", "Mehmet", "Nil", "Can", "Tarkan"};
            char[] A1 = new char[15], A2 = new char[15], A3 = new char[15];
            int[] scores = { 0, 100, 100, 95, 90, 85, 80, 80, 70, 55, 50, 30, 30};
            int P1score = 120, P2score = 120, A1index = 0, A2index = 0, A3index = 0;
            bool isTie = false , addToA1 = true, addToA2 = true, addToA3 = true;
            for (int fill = 0; fill < 15; fill++)
            {
                A1[fill] = ' '; A2[fill] = ' '; A3[fill] = ' ';
            }
            for (int count = 3; count > 0; count--) // countdown to start the game
            {
                Console.WriteLine("The game will start in "+count+" seconds.");
                System.Threading.Thread.Sleep(1000);
                Console.Clear();
            }
            Console.ForegroundColor = ConsoleColor.DarkGreen;
            Console.WriteLine("A1");
            Console.WriteLine("A2");
            Console.WriteLine("A3");
            Console.ForegroundColor = ConsoleColor.White;
            // main game loop
            for (int i = 0; i < 45; i++) // the game can last up to 45 rounds so  i < 45
            {   // 32, 33, 34, 35, 36, 37 lines are random generator for printing letters to the board. 
                int charNum , rowOnTheTable;
                char letter=' ';
                Random random = new Random();
                rowOnTheTable = random.Next(0, 3);
                charNum = random.Next(0, 3);
                switch (charNum)// converting random number to letter
                {
                    case 0:
                        letter = 'D';
                        break;
                    case 1:
                        letter = 'E';
                        break;
                    case 2:
                        letter = 'U';
                        break;
                }
                // for check if cell is empty
                if (rowOnTheTable == 0 && A1[A1index] == ' ' && addToA1 == true)
                {
                    A1[A1index] = letter;
                    Console.SetCursorPosition(A1index + 3, 0); Console.WriteLine(letter);
                    if (i % 2 == 0) P1score += (-5);     // score decrease after round
                    else if (i % 2 == 1) P2score += (-5);// score decrease after round
                    A1index += 1;
                    if (A1index == 15) { addToA1 = false; A1index = 0; } // if A1 is full
                }
                else if (rowOnTheTable == 1 && A2[A2index] == ' ' && addToA2 == true)
                {
                    A2[A2index] = letter;
                    Console.SetCursorPosition(A2index + 3, 1); Console.WriteLine(letter);
                    if (i % 2 == 0) P1score += (-5);     // score decrease after round
                    else if (i % 2 == 1) P2score += (-5);// score decrease after round
                    A2index += 1;
                    if (A2index == 15) { addToA2 = false; A2index = 0; } // if A2 is full
                }
                else if (rowOnTheTable == 2 && A3[A3index] == ' ' && addToA3 == true)
                {
                    A3[A3index] = letter;
                    Console.SetCursorPosition(A3index + 3, 2); Console.WriteLine(letter);
                    if (i % 2 == 0) P1score += (-5);     // score decrease after round
                    else if (i % 2 == 1) P2score += (-5);// score decrease after round
                    A3index += 1;
                    if (A3index == 15) { addToA3 = false; A3index = 0; } // if A3 is full
                }
                else i--; // if the cell is not empty
                    Console.SetCursorPosition(40, 1);
                    Console.WriteLine("Player 1:" + " " + P1score); //score table
                    Console.SetCursorPosition(40, 2);
                    Console.WriteLine("Player 2:" + " " + P2score); //score table
                
                if (P1score < 100) {Console.SetCursorPosition(52, 1); Console.WriteLine(" "); }//delete "point 120"s 0. Console.SetCursorPosition prints score but does not deletes 120's 0 after 100 point.(eg. 100-5 = 950 without that line)
                if (P2score < 100) {Console.SetCursorPosition(52, 2); Console.WriteLine(" "); }//delete "point 120"s 0. Console.SetCursorPosition prints score but does not deletes 120's 0 after 100 point.(eg. 100-5 = 950 without that line)
                if (P1score < 10) { Console.SetCursorPosition(51, 1); Console.WriteLine(" "); }//delete "point 10"s 0. Console.SetCursorPosition prints score but does not deletes 10's 0 after 100 point.(eg. 10-5 = 50 without that line)
                if (i % 2 == 0) { Console.SetCursorPosition(55, 1); Console.WriteLine("<-- played"); Console.SetCursorPosition(55, 2); ; Console.WriteLine("          "); }//shows who played
                else if (i % 2 == 1) { Console.SetCursorPosition(55, 2); Console.WriteLine("<-- played"); Console.SetCursorPosition(55, 1); Console.WriteLine("          "); }//shows who played

                for (int j = 0; j < 13; j++) // match check algorithm
                {   // horizontal check
                    if ((A1[j] == 'D' && A1[j + 1] == 'E' && A1[j + 2] == 'U') || (A1[j] == 'U' && A1[j + 1] == 'E' && A1[j + 2] == 'D'))
                    {
                        Console.ForegroundColor = ConsoleColor.DarkYellow;
                        Console.SetCursorPosition(j + 3, 0);
                        Console.Write(A1[j] + "" + A1[j + 1] + "" + A1[j + 2]);
                        Console.ResetColor();
                        i = 46; // finishes game loop
                    }
                    else if ((A2[j] == 'D' && A2[j + 1] == 'E' && A2[j + 2] == 'U') || (A2[j] == 'U' && A2[j + 1] == 'E' && A2[j + 2] == 'D'))
                    {
                        Console.ForegroundColor = ConsoleColor.DarkYellow;
                        Console.SetCursorPosition(j + 3, 1);
                        Console.Write(A2[j] + "" + A2[j + 1] + "" + A2[j + 2]);
                        Console.ResetColor();
                        i = 46; // finishes game loop
                    }
                    else if ((A3[j] == 'D' && A3[j + 1] == 'E' && A3[j + 2] == 'U') || (A3[j] == 'U' && A3[j + 1] == 'E' && A3[j + 2] == 'D'))
                    {
                        Console.ForegroundColor = ConsoleColor.DarkYellow;
                        Console.SetCursorPosition(j + 3, 2);
                        Console.Write(A3[j] + "" + A3[j + 1] + "" + A3[j + 2]);
                        Console.ResetColor();
                        i = 46; // finishes game loop
                    }
                    for (int k = 0; k < 15; k++)
                    {   // vertical check
                        if ((A1[k] == 'D' && A2[k] == 'E' && A3[k] == 'U') || (A1[k] == 'U' && A2[k] == 'E' && A3[k] == 'D'))
                        {
                            Console.ForegroundColor = ConsoleColor.DarkYellow;
                            Console.SetCursorPosition(k + 3, 0);
                            Console.WriteLine(A1[k]);
                            Console.SetCursorPosition(k + 3, 1);
                            Console.WriteLine(A2[k]);
                            Console.SetCursorPosition(k + 3, 2);
                            Console.WriteLine(A3[k]);
                            Console.ResetColor();
                            i = 46; // finishes game loop
                        }
                    }
                    for (int l = 0; l < 13; l++)
                    {   // diagonal check
                        if ((A1[l] == 'D' && A2[l + 1] == 'E' && A3[l + 2] == 'U') || (A1[l] == 'U' && A2[l + 1] == 'E' && A3[l + 2] == 'D'))
                        {
                            Console.ForegroundColor = ConsoleColor.DarkYellow;
                            Console.SetCursorPosition(l + 3, 0);
                            Console.WriteLine(A1[l]);
                            Console.SetCursorPosition(l + 4, 1);
                            Console.WriteLine(A2[l + 1]);
                            Console.SetCursorPosition(l + 5, 2);
                            Console.WriteLine(A3[l + 2]);
                            Console.ResetColor();
                            i = 46; // finishes game loop
                        }
                        else if ((A3[l] == 'D' && A2[l + 1] == 'E' && A1[l + 2] == 'U') || (A3[l] == 'U' && A2[l + 1] == 'E' && A1[l + 2] == 'D'))
                        {
                            Console.ForegroundColor = ConsoleColor.DarkYellow;
                            Console.SetCursorPosition(l + 3, 2);
                            Console.WriteLine(A3[l]);
                            Console.SetCursorPosition(l + 4, 1);
                            Console.WriteLine(A2[l + 1]);
                            Console.SetCursorPosition(l + 5, 0);
                            Console.WriteLine(A1[l + 2]);
                            Console.ResetColor();
                            i = 46; // finishes game loop
                        }
                    }
                }
                if (i == 44) // if the game is draw
                {
                    Console.SetCursorPosition(0, 4);
                    Console.WriteLine("TIE");
                    Console.SetCursorPosition(50, 1); Console.WriteLine("...");
                    Console.SetCursorPosition(50, 2); Console.WriteLine("...");
                    isTie = true;
                }
                System.Threading.Thread.Sleep(3000); // manages game speed (3000ms per round)
            }
            Console.SetCursorPosition(0, 4);
            string winnerName = " "; int winnerScore = 0;
            int A1elements = 0, A2elements = 0, A3elements = 0;
            for (int i = 0; i < A1.Length; i++) // summing of all letters on the board
            {
                if (A1[i] != ' ') A1elements += 1;
                if (A2[i] != ' ') A2elements += 1;
                if (A3[i] != ' ') A3elements += 1;
            }
            if ((A1elements + A2elements + A3elements) % 2 == 1 && isTie == false) //algorithm that chooses the winner
            {
                Console.WriteLine("WINNER: Player 1 score:" + " " + P1score);
                winnerScore = P1score;
                winnerName = "Player 1";
            }
            else if ((A1elements + A2elements + A3elements) % 2 == 0 && isTie == false) //algorithm that chooses the winner
            {
                Console.WriteLine("WINNER: Player 2 score:" + " " + P2score);
                winnerScore = P2score;
                winnerName = "Player 2";
            }
            Console.WriteLine("");
            Console.ForegroundColor = ConsoleColor.DarkGreen; Console.WriteLine("- HIGH SCORE TABLE -"); Console.ForegroundColor = ConsoleColor.White;
            // printing and arranging high score table
            if (isTie == true)
            {
                for (int i = 0; i < 12; i++) { names[i] = names[i + 1]; scores[i] = scores[i + 1]; }
                for (int i = 0; i < 12; i++) { Console.WriteLine(i + 1 + " " + names[i] + " -> " + scores[i]); }
            }
            if (isTie == false)
            {
                for (int i = 0; i < 12; i++)
                {
                    if (scores[i + 1] >= winnerScore) { names[i] = names[i + 1]; scores[i] = scores[i + 1]; }
                    else if (winnerScore > scores[i+1]) { names[i] = winnerName; scores[i] = winnerScore; i = 13; }
                }
                if (winnerScore <= 30) { names[12] = winnerName; scores[12] = winnerScore; }
                for (int i = 0; i < 13; i++) Console.WriteLine(i + 1 + " " + names[i] + " -> " + scores[i]);
            }
            for (int count = 9; count > 0; count--) // countdown to end the game
            {
                Console.SetCursorPosition(0, 21);
                Console.WriteLine("The game will end in " + count + " seconds.");
                System.Threading.Thread.Sleep(1000);
            }
        }
    }
}
