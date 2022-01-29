using System;
using System.Threading;
using System.Timers;
using static System.Console;
namespace chiners
{
    class Program
    {
        // This variables are used for changing piece style
        static string X_piece = "X";
        static string O_piece = "O";
        
        // This function is the complete game without menu
        static void gameBot(int jumpPossibility, int gameMode)
        {
            // Bot function that returns which move to do
            static (((int, int), (int, int), int), bool) CengersBot(string[,] table, int turn, int jumpStreak, (int, int) chosen, int jumpPossibility, bool exception)
            {
                // (int, int)[] arrays are used for storing coordinates
                (int, int)[] coordinates_X = new (int, int)[9], coordinates_O = new (int, int)[9];
                // ((int, int), (int, int))[,] arrays are used to store motion containing part and target coordinates
                ((int, int), (int, int))[,] nextMove_O = new ((int, int), (int, int))[9, 8], nextMove_1 = new ((int, int), (int, int))[9, 2], nextMove_2 = new ((int, int), (int, int))[9, 2],
                nextMove_n1 = new ((int, int), (int, int))[9, 2], nextMove_n2 = new ((int, int), (int, int))[9, 2];
                // ((int, int), (int, int), int) variable is used for returning the move (2 coordinates) and also an instruction int tells the game how to act
                ((int, int), (int, int), int) chosenMove = ((0, 0), (0, 0), 0);

                //Listing O pieces
                int count = 0;
                bool noMove = false, negative_move;
                for (int i = 2; i < 10; i++)
                {
                    for (int j = 2; j < 10; j++)
                    {
                        if (table[i, j] == O_piece)
                        {
                            coordinates_O[count] = (j, i);
                            count++;
                        }
                    }
                }
                //Listing X pieces
                count = 0;
                for (int i = 2; i < 10; i++)
                {
                    for (int j = 2; j < 10; j++)
                    {
                        if (table[i, j] == X_piece)
                        {
                            coordinates_X[count] = (j, i);
                            count++;
                        }
                    }
                }
                // O's turn
                if (turn % 2 == 1)
                {
                    int count1 = 0; int count2 = 0; int countn1 = 0; int countn2 = 0; // n stands for negative
                                                                                      //Finding potential moves
                    for (int i = 0; i < 9; i++)
                    { //If target index in range  and        there is no jumpstreak    and            target cell is blank
                        if (coordinates_O[i].Item2 < 9 && !(jumpStreak > 0 && turn % 2 == 1) && table[coordinates_O[i].Item2 + 1, coordinates_O[i].Item1] == ".")
                        {//Downward step +1
                            nextMove_1[i, count1].Item1 = (coordinates_O[i].Item1, coordinates_O[i].Item2);
                            nextMove_1[i, count1].Item2 = (coordinates_O[i].Item1, coordinates_O[i].Item2 + 1);
                            count1++;
                        }
                        if (coordinates_O[i].Item2 < 8 && table[coordinates_O[i].Item2 + 1, coordinates_O[i].Item1] != "." && table[coordinates_O[i].Item2 + 2, coordinates_O[i].Item1] == ".")
                        {//Downward jump +2
                            nextMove_2[i, count2].Item1 = (coordinates_O[i].Item1, coordinates_O[i].Item2);
                            nextMove_2[i, count2].Item2 = (coordinates_O[i].Item1, coordinates_O[i].Item2 + 2);
                            count2++;
                        }

                        if (coordinates_O[i].Item2 > 0 && !(jumpStreak > 0 && turn % 2 == 1) && table[coordinates_O[i].Item2 - 1, coordinates_O[i].Item1] == ".")
                        {//Upward step -1
                            nextMove_n1[i, countn1].Item1 = (coordinates_O[i].Item1, coordinates_O[i].Item2);
                            nextMove_n1[i, countn1].Item2 = (coordinates_O[i].Item1, coordinates_O[i].Item2 - 1);
                            countn1++;
                        }
                        if (coordinates_O[i].Item2 > 1 && !(table[coordinates_O[i].Item2 - 1, coordinates_O[i].Item1] == ".") && table[coordinates_O[i].Item2 - 2, coordinates_O[i].Item1] == ".")
                        {//Upward jump -2
                            nextMove_n2[i, countn2].Item1 = (coordinates_O[i].Item1, coordinates_O[i].Item2);
                            nextMove_n2[i, countn2].Item2 = (coordinates_O[i].Item1, coordinates_O[i].Item2 - 2);
                            countn2++;
                        }

                        if (coordinates_O[i].Item1 < 9 && !(jumpStreak > 0 && turn % 2 == 1) && table[coordinates_O[i].Item2, coordinates_O[i].Item1 + 1] == ".")
                        {//Right step +1
                            nextMove_1[i, count1].Item1 = (coordinates_O[i].Item1, coordinates_O[i].Item2);
                            nextMove_1[i, count1].Item2 = (coordinates_O[i].Item1 + 1, coordinates_O[i].Item2);
                        }
                        if (coordinates_O[i].Item1 < 8 && !(table[coordinates_O[i].Item2, coordinates_O[i].Item1 + 1] == ".") && table[coordinates_O[i].Item2, coordinates_O[i].Item1 + 2] == ".")
                        {//Right jump +2
                            nextMove_2[i, count2].Item1 = (coordinates_O[i].Item1, coordinates_O[i].Item2);
                            nextMove_2[i, count2].Item2 = (coordinates_O[i].Item1 + 2, coordinates_O[i].Item2);
                        }

                        if (coordinates_O[i].Item1 > 0 && !(jumpStreak > 0 && turn % 2 == 1) && table[coordinates_O[i].Item2, coordinates_O[i].Item1 - 1] == ".")
                        {//Left step -1
                            nextMove_n1[i, countn1].Item1 = (coordinates_O[i].Item1, coordinates_O[i].Item2);
                            nextMove_n1[i, countn1].Item2 = (coordinates_O[i].Item1 - 1, coordinates_O[i].Item2);
                        }
                        if (coordinates_O[i].Item1 > 1 && !(table[coordinates_O[i].Item2, coordinates_O[i].Item1 - 1] == ".") && table[coordinates_O[i].Item2, coordinates_O[i].Item1 - 2] == ".")
                        {//Left jump -2
                            nextMove_n2[i, countn2].Item1 = (coordinates_O[i].Item1, coordinates_O[i].Item2);
                            nextMove_n2[i, countn2].Item2 = (coordinates_O[i].Item1 - 2, coordinates_O[i].Item2);
                        }
                        count1 = 0; count2 = 0; countn1 = 0; countn2 = 0;

                    }
                    bool jumpMove = false;
                    //Checking if there is a Jump move
                    for (int i = 0; i < 9; i++)
                    {
                        for (int j = 0; j < 2; j++)
                        {
                            if (nextMove_2[i, j].Item1.Item1 != 0)
                                jumpMove = true;
                        }
                    }
                    bool stepMove = false;
                    //Checking if there is a Step move
                    for (int i = 0; i < 9; i++)
                    {
                        for (int j = 0; j < 2; j++)
                        {
                            if (nextMove_1[i, j].Item1.Item1 != 0)
                                stepMove = true;
                        }
                    }
                    Random random = new Random();

                    //If there is an exception, bot will make one of the movements belove instead of the automated moves (neceessary for endgame)
                    if (exception)
                    {
                        exception = false;
                        if (table[6, 9] == O_piece && table[7, 9] == ".")
                        {
                            chosenMove = ((9, 6), (9, 7), -1);
                        }
                        else if (table[9, 6] == O_piece && table[9, 7] == ".")
                        {
                            chosenMove = ((6, 9), (7, 9), -1);
                        }
                        else
                        {
                            noMove = true;
                        }
                    }
                    else if (jumpStreak > 0)
                    {
                        if (chosen.Item2 < 8 && table[chosen.Item2 + 1, chosen.Item1] != "." && table[chosen.Item2 + 2, chosen.Item1] == ".")
                        {//Downward jump +2
                            chosenMove.Item1 = (chosen.Item1, chosen.Item2);
                            chosenMove.Item2 = (chosen.Item1, chosen.Item2 + 2);
                            chosenMove.Item3 = 2;
                        }
                        else if (chosen.Item1 < 8 && !(table[chosen.Item2, chosen.Item1 + 1] == ".") && table[chosen.Item2, chosen.Item1 + 2] == ".")
                        {//Right jump +2
                            chosenMove.Item1 = (chosen.Item1, chosen.Item2);
                            chosenMove.Item2 = (chosen.Item1 + 2, chosen.Item2);
                            chosenMove.Item3 = 2;
                        }
                        else
                        {
                            chosenMove = ((0, 0), (0, 0), 0); //end turn
                        }
                    }
                    else if (jumpMove && stepMove)
                    {
                        // jumpPossibility variable is a parameter that increases the chance of a Jump instead of a Step as it increases
                        int percentage = random.Next(1, 101);
                        if (jumpPossibility >= percentage)
                        {
                            while (true)
                            {
                                int i = random.Next(0, 9);
                                int j = random.Next(0, 2);
                                if ((nextMove_2[i, j].Item1.Item1 != 0))
                                {
                                    chosenMove.Item1 = nextMove_2[i, j].Item1; chosenMove.Item2 = nextMove_2[i, j].Item2; chosenMove.Item3 = 2;
                                    break;
                                }
                            }
                        }
                        else
                        {
                            while (true)
                            {
                                int i = random.Next(0, 9);
                                int j = random.Next(0, 2);
                                if ((nextMove_1[i, j].Item1.Item1 != 0))
                                {
                                    chosenMove.Item1 = nextMove_1[i, j].Item1; chosenMove.Item2 = nextMove_1[i, j].Item2; chosenMove.Item3 = 1;
                                    break;
                                }
                            }
                        }

                    }
                    else if (jumpMove)
                    {
                        while (true)
                        {
                            int i = random.Next(0, 9);
                            int j = random.Next(0, 2);
                            if ((nextMove_2[i, j].Item1.Item1 != 0))
                            {
                                chosenMove.Item1 = nextMove_2[i, j].Item1; chosenMove.Item2 = nextMove_2[i, j].Item2; chosenMove.Item3 = 2;
                                break;
                            }
                        }
                    }
                    else if (stepMove)
                    {
                        while (true)
                        {
                            int i = random.Next(0, 9);
                            int j = random.Next(0, 2);
                            if ((nextMove_1[i, j].Item1.Item1 != 0))
                            {
                                chosenMove.Item1 = nextMove_1[i, j].Item1; chosenMove.Item2 = nextMove_1[i, j].Item2; chosenMove.Item3 = 1;
                                break;
                            }
                        }
                    }
                    else
                    {
                        noMove = true;
                    }
                    // If there is no move to the directions we want, this means bot is stuck so bot tries to get rid of it
                    negative_move = false;
                    if (noMove)
                    {
                        if (table[7, 7] == ".")
                        {
                            if (table[7, 9] == O_piece && table[6, 9] == O_piece && table[7, 8] == O_piece)
                            {
                                chosenMove = ((9, 7), (7, 7), -1);
                                exception = true;
                            }
                            else if (table[7, 9] == O_piece && table[6, 9] == O_piece && table[7, 8] == ".")
                            {
                                chosenMove = ((9, 7), (8, 7), -1);
                                exception = true;
                            }
                            else if (table[9, 7] == O_piece && table[9, 6] == O_piece && table[8, 7] == O_piece)
                            {
                                chosenMove = ((7, 9), (7, 7), -1);
                                exception = true;
                            }
                            else if (table[9, 7] == O_piece && table[9, 6] == O_piece && table[8, 7] == ".")
                            {
                                chosenMove = ((7, 9), (7, 8), -1);
                                exception = true;
                            }
                            else
                            {
                                negative_move = true;
                            }
                        }
                        else
                        {
                            negative_move = true;
                        }
                    }
                    // If there is no movement to do other than above ones, bot makes a negative Step (-1 length move)
                    if (negative_move)
                    {
                        while (true)
                        {
                            int i = random.Next(0, 9);
                            int j = random.Next(0, 2);
                            if ((nextMove_n1[i, j].Item1.Item1 != 0))
                            {
                                chosenMove.Item1 = nextMove_n1[i, j].Item1; chosenMove.Item2 = nextMove_n1[i, j].Item2; chosenMove.Item3 = -1;
                                break;
                            }
                        }
                    }
                }
                // X's Turn
                else
                {
                    int count1 = 0; int count2 = 0; int countn1 = 0; int countn2 = 0; // n stands for negative
                                                                                      //Finding potential moves
                    for (int i = 0; i < 9; i++)
                    { //If target index in range  and        there is no jumpstreak    and            target cell is blank
                        if (coordinates_X[i].Item2 < 9 && !(jumpStreak > 0 && turn % 2 == 1) && table[coordinates_X[i].Item2 + 1, coordinates_X[i].Item1] == ".")
                        {//Downward step +1 --> -1
                            nextMove_n1[i, countn1].Item1 = (coordinates_X[i].Item1, coordinates_X[i].Item2);
                            nextMove_n1[i, countn1].Item2 = (coordinates_X[i].Item1, coordinates_X[i].Item2 + 1);
                            countn1++;
                        }
                        if (coordinates_X[i].Item2 < 8 && table[coordinates_X[i].Item2 + 1, coordinates_X[i].Item1] != "." && table[coordinates_X[i].Item2 + 2, coordinates_X[i].Item1] == ".")
                        {//Downward jump +2 --> -2
                            nextMove_n2[i, countn2].Item1 = (coordinates_X[i].Item1, coordinates_X[i].Item2);
                            nextMove_n2[i, countn2].Item2 = (coordinates_X[i].Item1, coordinates_X[i].Item2 + 2);
                            countn2++;
                        }

                        if (coordinates_X[i].Item2 > 0 && !(jumpStreak > 0 && turn % 2 == 1) && table[coordinates_X[i].Item2 - 1, coordinates_X[i].Item1] == ".")
                        {//Upward step -1 --> +1
                            nextMove_1[i, count1].Item1 = (coordinates_X[i].Item1, coordinates_X[i].Item2);
                            nextMove_1[i, count1].Item2 = (coordinates_X[i].Item1, coordinates_X[i].Item2 - 1);
                            count1++;
                        }
                        if (coordinates_X[i].Item2 > 1 && !(table[coordinates_X[i].Item2 - 1, coordinates_X[i].Item1] == ".") && table[coordinates_X[i].Item2 - 2, coordinates_X[i].Item1] == ".")
                        {//Upward jump -2 --> +2
                            nextMove_2[i, count2].Item1 = (coordinates_X[i].Item1, coordinates_X[i].Item2);
                            nextMove_2[i, count2].Item2 = (coordinates_X[i].Item1, coordinates_X[i].Item2 - 2);
                            count2++;
                        }

                        if (coordinates_X[i].Item1 < 9 && !(jumpStreak > 0 && turn % 2 == 1) && table[coordinates_X[i].Item2, coordinates_X[i].Item1 + 1] == ".")
                        {//Right step +1 --> -1
                            nextMove_n1[i, countn1].Item1 = (coordinates_X[i].Item1, coordinates_X[i].Item2);
                            nextMove_n1[i, countn1].Item2 = (coordinates_X[i].Item1 + 1, coordinates_X[i].Item2);
                        }
                        if (coordinates_X[i].Item1 < 8 && !(table[coordinates_X[i].Item2, coordinates_X[i].Item1 + 1] == ".") && table[coordinates_X[i].Item2, coordinates_X[i].Item1 + 2] == ".")
                        {//Right jump +2 --> -2
                            nextMove_n2[i, countn2].Item1 = (coordinates_X[i].Item1, coordinates_X[i].Item2);
                            nextMove_n2[i, countn2].Item2 = (coordinates_X[i].Item1 + 2, coordinates_X[i].Item2);
                        }

                        if (coordinates_X[i].Item1 > 0 && !(jumpStreak > 0 && turn % 2 == 1) && table[coordinates_X[i].Item2, coordinates_X[i].Item1 - 1] == ".")
                        {//Left step -1 --> +1
                            nextMove_1[i, count1].Item1 = (coordinates_X[i].Item1, coordinates_X[i].Item2);
                            nextMove_1[i, count1].Item2 = (coordinates_X[i].Item1 - 1, coordinates_X[i].Item2);
                        }
                        if (coordinates_X[i].Item1 > 1 && !(table[coordinates_X[i].Item2, coordinates_X[i].Item1 - 1] == ".") && table[coordinates_X[i].Item2, coordinates_X[i].Item1 - 2] == ".")
                        {//Left jump -2 --> +2
                            nextMove_2[i, count2].Item1 = (coordinates_X[i].Item1, coordinates_X[i].Item2);
                            nextMove_2[i, count2].Item2 = (coordinates_X[i].Item1 - 2, coordinates_X[i].Item2);
                        }
                        count1 = 0; count2 = 0; countn1 = 0; countn2 = 0;

                    }
                    bool jumpMove = false;
                    //Checking if there is a Jump move
                    for (int i = 0; i < 9; i++)
                    {
                        for (int j = 0; j < 2; j++)
                        {
                            if (nextMove_2[i, j].Item1.Item1 != 0)
                                jumpMove = true;
                        }
                    }
                    bool stepMove = false;
                    //Checking if there is a Step move
                    for (int i = 0; i < 9; i++)
                    {
                        for (int j = 0; j < 2; j++)
                        {
                            if (nextMove_1[i, j].Item1.Item1 != 0)
                                stepMove = true;
                        }
                    }
                    Random random = new Random();

                    //If there is an exception, bot will make one of the movements belove instead of the automated moves (neceessary for endgame)
                    if (exception)
                    {
                        exception = false;
                        if (table[2, 5] == X_piece && table[2, 4] == ".")
                        {
                            chosenMove = ((5, 2), (4, 2), -1);
                        }
                        else if (table[5, 2] == X_piece && table[4, 2] == ".")
                        {
                            chosenMove = ((2, 5), (2, 4), -1);
                        }
                        else
                        {
                            noMove = true;
                        }
                    }
                    else if (jumpStreak > 0)
                    {
                        if (chosen.Item2 > 1 && !(table[chosen.Item2 - 1, chosen.Item1] == ".") && table[chosen.Item2 - 2, chosen.Item1] == ".")
                        {//Upward jump -2 --> +2
                            chosenMove.Item1 = (chosen.Item1, chosen.Item2);
                            chosenMove.Item2 = (chosen.Item1, chosen.Item2 - 2);
                            chosenMove.Item3 = 2;
                        }
                        else if (chosen.Item1 > 1 && !(table[chosen.Item2, chosen.Item1 - 1] == ".") && table[chosen.Item2, chosen.Item1 - 2] == ".")
                        {//Left jump -2 --> +2
                            chosenMove.Item1 = (chosen.Item1, chosen.Item2);
                            chosenMove.Item2 = (chosen.Item1 - 2, chosen.Item2);
                            chosenMove.Item3 = 2;
                        }
                        else
                        {
                            chosenMove = ((0, 0), (0, 0), 0); //end turn
                        }
                    }
                    else if (jumpMove && stepMove)
                    {
                        // jumpPossibility variable is a parameter that increases the chance of a Jump instead of a Step as it increases
                        int percentage = random.Next(0, 101);
                        if (jumpPossibility >= percentage)
                        {
                            while (true)
                            {
                                int i = random.Next(0, 9);
                                int j = random.Next(0, 2);
                                if ((nextMove_2[i, j].Item1.Item1 != 0))
                                {
                                    chosenMove.Item1 = nextMove_2[i, j].Item1; chosenMove.Item2 = nextMove_2[i, j].Item2; chosenMove.Item3 = 2;
                                    break;
                                }
                            }
                        }
                        else
                        {
                            while (true)
                            {
                                int i = random.Next(0, 9);
                                int j = random.Next(0, 2);
                                if ((nextMove_1[i, j].Item1.Item1 != 0))
                                {
                                    chosenMove.Item1 = nextMove_1[i, j].Item1; chosenMove.Item2 = nextMove_1[i, j].Item2; chosenMove.Item3 = 1;
                                    break;
                                }
                            }
                        }
                    }
                    else if (jumpMove)
                    {
                        while (true)
                        {
                            int i = random.Next(0, 9);
                            int j = random.Next(0, 2);
                            if ((nextMove_2[i, j].Item1.Item1 != 0))
                            {
                                chosenMove.Item1 = nextMove_2[i, j].Item1; chosenMove.Item2 = nextMove_2[i, j].Item2; chosenMove.Item3 = 2;
                                break;
                            }
                        }
                    }
                    else if (stepMove)
                    {
                        while (true)
                        {
                            int i = random.Next(0, 9);
                            int j = random.Next(0, 2);
                            if ((nextMove_1[i, j].Item1.Item1 != 0))
                            {
                                chosenMove.Item1 = nextMove_1[i, j].Item1; chosenMove.Item2 = nextMove_1[i, j].Item2; chosenMove.Item3 = 1;
                                break;
                            }
                        }
                    }
                    else
                    {
                        noMove = true;
                    }
                    // If there is no move to the directions we want, this means bot is stuck so bot tries to get rid of it
                    negative_move = false;
                    if (noMove)
                    {
                        if (table[4, 4] == ".")
                        {
                            if (table[4, 2] == X_piece && table[5, 2] == X_piece && table[4, 3] == X_piece)
                            {
                                chosenMove = ((2, 4), (4, 4), -1);
                                exception = true;
                            }
                            else if (table[4, 2] == X_piece && table[5, 2] == X_piece && table[4, 3] == ".")
                            {
                                chosenMove = ((2, 4), (3, 4), -1);
                                exception = true;
                            }
                            else if (table[2, 4] == X_piece && table[2, 5] == X_piece && table[3, 4] == X_piece)
                            {
                                chosenMove = ((4, 2), (4, 4), -1);
                                exception = true;
                            }
                            else if (table[2, 4] == X_piece && table[2, 5] == X_piece && table[3, 4] == ".")
                            {
                                chosenMove = ((4, 2), (4, 3), -1);
                                exception = true;
                            }
                            else
                            {
                                negative_move = true;
                            }
                        }
                        else
                        {
                            negative_move = true;
                        }
                    }
                    // If there is no movement to do other than above ones, bot makes a negative Step (-1 length move)
                    if (negative_move)
                    {
                        while (true)
                        {
                            int i = random.Next(0, 9);
                            int j = random.Next(0, 2);
                            if ((nextMove_n1[i, j].Item1.Item1 != 0))
                            {
                                chosenMove.Item1 = nextMove_n1[i, j].Item1; chosenMove.Item2 = nextMove_n1[i, j].Item2; chosenMove.Item3 = -1;
                                break;
                            }
                        }
                    }
                }
                return (chosenMove, exception);
            }

            //Creating table
            string[,] table = new string[11, 11];
            table[0, 0] = " "; table[0, 1] = " "; table[1, 0] = " "; table[10, 0] = " ";
            table[1, 1] = "╔"; table[1, 10] = "╗"; table[10, 1] = "╚"; table[10, 10] = "╝";
            for (int i = 2; i < 10; i++)
            {
                table[i, 0] = Convert.ToString(i - 1);
            }

            for (int i = 2; i < 10; i++)
            {
                table[0, i] = Convert.ToString(i - 1);
            }

            for (int i = 2; i < 10; i++)
            {
                table[i, 1] = "║";
            }

            for (int i = 2; i < 10; i++)
            {
                table[i, 10] = "║";
            }

            for (int i = 2; i < 10; i++)
            {
                table[1, i] = "═";
            }

            for (int i = 2; i < 10; i++)
            {
                table[10, i] = "═";
            }

            for (int i = 2; i < table.GetLength(0) - 1; i++)
            {
                for (int j = 2; j < table.GetLength(1) - 1; j++)
                {
                    table[i, j] = ".";
                }
            }

            for (int i = 2; i < 5; i++)
            {
                for (int j = 2; j < 5; j++)
                {
                    table[i, j] = O_piece;
                }
            }

            for (int i = 7; i < 10; i++)
            {
                for (int j = 7; j < 10; j++)
                {
                    table[i, j] = X_piece;
                }
            }

            //Printing table
            for (int i = 0; i < table.GetLength(0); i++)
            {
                for (int j = 0; j < table.GetLength(1); j++)
                {
                    if (j > 1 && j < 11)
                    {
                        if (i == 1 || i == 10)
                        {
                            Console.ForegroundColor = ConsoleColor.Blue;
                            Console.Write('═');
                            Console.ForegroundColor = ConsoleColor.White;
                        }
                        else
                            Console.Write(' ');
                    }
                    if (table[i, j] == X_piece) // Color
                    {
                        Console.ForegroundColor = ConsoleColor.Blue;
                    }
                    else if (table[i, j] == O_piece)
                    {
                        Console.ForegroundColor = ConsoleColor.Red;
                    }
                    else if (table[i, j] == "╔" || table[i, j] == "╗" || table[i, j] == "╚" || table[i, j] == "╝" || table[i, j] == "║" || table[i, j] == "═")
                    {
                        Console.ForegroundColor = ConsoleColor.Blue;
                    }
                    Console.Write(table[i, j]);
                    Console.ForegroundColor = ConsoleColor.White;
                }
                Console.WriteLine();
            }
            // Assigning variables
            ConsoleKeyInfo arrowKey;
            int x = 9, y = 9, chosen_x = 1, chosen_y = 1, turn = 0, x_count, o_count, jumpStreak = 0, cursor_x = 17, cursor_y = 9;
            bool choosed = false, quit = false, endGameException_O = false, endGameException_X = false; string player_X, player_O;
            // Selecting players Human or Computer
            if (gameMode == 1)
            {
                player_X = "h"; player_O = "c";
            }
            else if (gameMode == 2)
            {
                player_X = "h"; player_O = "h";
            }
            else if (gameMode == 3)
            {
                player_X = "c"; player_O = "c";
            }
            else
            {
                player_X = "h"; player_O = "c";
            }
            // Clearing some parts of the console
            for (int i = 2; i < 11; i++)
            {
                Console.SetCursorPosition(30, i);
                for (int j = 0; j < 50; j++)
                {
                    Console.Write(" ");
                }
            }
            Random random = new Random();
            // Human move function
            void humanMove()
            {
                arrowKey = Console.ReadKey(true);
                switch (arrowKey.Key)
                {
                    //Arrow moves
                    case ConsoleKey.UpArrow:
                        cursor_y--; y--;
                        if (y == 1) { y = 9; }
                        if (cursor_y == 1) { cursor_y = 9; }
                        Console.SetCursorPosition(cursor_x, cursor_y);
                        break;

                    case ConsoleKey.DownArrow:
                        cursor_y++; y++;
                        if (y == 10) { y = 2; }
                        if (cursor_y == 10) { cursor_y = 2; }
                        Console.SetCursorPosition(cursor_x, cursor_y);
                        break;

                    case ConsoleKey.RightArrow:
                        cursor_x += 2; x++;
                        if (x == 10) { x = 2; }
                        if (cursor_x == 19) { cursor_x = 3; }
                        Console.SetCursorPosition(cursor_x, cursor_y);
                        break;

                    case ConsoleKey.LeftArrow:
                        cursor_x -= 2; x--;
                        if (x == 1) { x = 9; }
                        if (cursor_x == 1) { cursor_x = 17; }
                        Console.SetCursorPosition(cursor_x, cursor_y);
                        break;

                    case ConsoleKey.Escape:
                        quit = true;
                        break;

                    //Choosing (Keypress Z)
                    case ConsoleKey.Z:
                        if (table[y, x] == "." || (turn % 2 == 0 && table[y, x] == O_piece) || jumpStreak > 0) { break; }
                        else if (table[y, x] == "." || (turn % 2 == 1 && table[y, x] == X_piece) || jumpStreak > 0) { break; }
                        chosen_x = x; chosen_y = y; choosed = true;
                        break;
                    //Moving (Keypress X)
                    case ConsoleKey.X:
                        //Break IF target is not empty OR it is X's turn and chosen is not X OR it is O's turn and chosen is not O
                        if ((table[y, x] != ".") || (turn % 2 == 0 && table[chosen_y, chosen_x] != X_piece) || (turn % 2 == 1 && table[chosen_y, chosen_x] != O_piece)) { break; }
                        /*Step*/
                        //Down
                        if (chosen_x == x && chosen_y == y - 1 && table[y - 1, x] != "." && jumpStreak == 0)                       
                        {
                            table[y, x] = table[y - 1, x];
                            table[y - 1, x] = ".";
                            turn++;
                        }
                        //Up
                        if (chosen_x == x && chosen_y == y + 1 && table[y + 1, x] != "." && jumpStreak == 0)                       
                        {
                            table[y, x] = table[y + 1, x];
                            table[y + 1, x] = ".";
                            turn++;
                        }
                        //Right
                        if (chosen_x == x - 1 && chosen_y == y && table[y, x - 1] != "." && jumpStreak == 0)
                        {
                            table[y, x] = table[y, x - 1];
                            table[y, x - 1] = ".";
                            turn++;
                        }
                        //Left
                        if (chosen_x == x + 1 && chosen_y == y && table[y, x + 1] != "." && jumpStreak == 0)                     
                        {
                            table[y, x] = table[y, x + 1];
                            table[y, x + 1] = ".";
                            turn++;
                        }
                        /*Jump*/
                        //Down
                        if (chosen_x == x && chosen_y == y - 2 && table[y - 1, x] != "." && table[y - 2, x] != ".")                      
                        {
                            table[y, x] = table[y - 2, x];
                            table[y - 2, x] = ".";
                            chosen_x = x; chosen_y = y;
                            jumpStreak++;
                        }
                        //Up
                        if (chosen_x == x && chosen_y == y + 2 && table[y + 1, x] != "." && table[y + 2, x] != ".")                       
                        {
                            table[y, x] = table[y + 2, x];
                            table[y + 2, x] = ".";
                            chosen_x = x; chosen_y = y;
                            jumpStreak++;
                        }
                        //Right
                        if (chosen_x == x - 2 && chosen_y == y && table[y, x - 1] != "." && table[y, x - 2] != ".")                       
                        {
                            table[y, x] = table[y, x - 2];
                            table[y, x - 2] = ".";
                            chosen_x = x; chosen_y = y;
                            jumpStreak++;
                        }
                        //Left
                        if (chosen_x == x + 2 && chosen_y == y && table[y, x + 1] != "." && table[y, x + 2] != ".")                       
                        {
                            table[y, x] = table[y, x + 2];
                            table[y, x + 2] = ".";
                            chosen_x = x; chosen_y = y;
                            jumpStreak++;
                        }
                        if (jumpStreak == 0)
                        {
                            choosed = false; //Controls if there is a chosen square
                        }
                        break;
                    //Ending turn (Keypress C)
                    case ConsoleKey.C:
                        {
                            if (jumpStreak == 0)
                            {
                                break;
                            }
                            turn++;
                            choosed = false;
                            jumpStreak = 0;
                        }
                        break;
                }
            }
            // This function controls the bot movement using the returned values from the bot function
            void botMove(bool exception, int jumpPossibility)
            {
                (((int, int), (int, int), int), bool) returned = CengersBot(table, turn, jumpStreak, (chosen_x, chosen_y), jumpPossibility, exception);
                (int, int) piece = returned.Item1.Item1, target = returned.Item1.Item2; int displacement = returned.Item1.Item3;
                if (turn % 2 == 0)
                {
                    endGameException_X = returned.Item2;
                }
                else
                {
                    endGameException_O = returned.Item2;
                }
                Thread.Sleep(100); //To see bot's movement
                // IF there is a move to do, then it performs it
                if (displacement != 0)
                {
                    chosen_x = target.Item1; chosen_y = target.Item2;
                    table[target.Item2, target.Item1] = table[piece.Item2, piece.Item1];
                    table[piece.Item2, piece.Item1] = ".";
                }
                choosed = true;
                if (displacement == 2) // Jump
                {
                    jumpStreak++;
                }
                else if (displacement == 1 || displacement == -1) // Step
                {
                    turn++;
                    choosed = false;
                }
                else // There is a jumpstreak but there are not any moves to do so bot ends its turn
                {
                    turn++;
                    choosed = false;
                    jumpStreak = 0;
                }
            }
            // Main game loop
            while (true)
            {
                //Printing additional info
                Console.SetCursorPosition(25, 0);
                Console.Write("X:" + (x - 1) + " Y:" + (y - 1));
                if (turn % 2 == 0)
                {
                    Console.Write("  Turn: " + X_piece + "   ");
                }
                else
                {
                    Console.Write("  Turn: " + O_piece + "   ");
                }
                Console.Write("(" + (turn + 1) + "th turn)    ");
                if (choosed && ((turn % 2 == 0 && player_X == "h") || (turn % 2 == 1 && player_O == "h")))
                {
                    Console.Write(" Chosen X:" + (chosen_x - 1) + " Chosen Y:" + (chosen_y - 1));
                }

                Console.SetCursorPosition(25, 2);
                Console.Write("Press 'Z' to choose a piece");
                Console.SetCursorPosition(25, 4);
                Console.Write("Press 'X' to choose the target square");

                if (jumpStreak > 0 && ((turn % 2 == 0 && player_X == "h") || (turn % 2 == 1 && player_O == "h")))
                {
                    Console.SetCursorPosition(25, 6);
                    Console.Write("Press 'C' to end your turn");
                    Console.SetCursorPosition(25, 8);
                    Console.Write("Jump streak:" + jumpStreak);
                }
                else
                {
                    Console.SetCursorPosition(25, 6);
                    for (int i = 0; i < 30; i++)
                    {
                        Console.Write(' ');
                    }
                    Console.SetCursorPosition(25, 8);
                    for (int i = 0; i < 30; i++)
                    {
                        Console.Write(' ');
                    }
                }

                Console.SetCursorPosition(cursor_x, cursor_y);
                // Cursor only shows up if it is human's turn for aesthetic purposes
                if ((turn % 2 == 0 && player_X == "h") || (turn % 2 == 1 && player_O == "h"))
                    Console.CursorVisible = true;
                if (turn % 2 == 0) // X's turn
                {
                    if (player_X == "c")
                    {
                        botMove(endGameException_X, jumpPossibility);
                    }
                    else
                    {
                        humanMove();
                    }


                }
                else // O's turn
                {
                    if (player_O == "c")
                    {
                        botMove(endGameException_O, jumpPossibility);
                    }
                    else
                    {
                        humanMove();
                    }
                }

                Console.CursorVisible = false;
                //Clearing some parts of the console
                Console.SetCursorPosition(40, 0);
                for (int i = 0; i < 50; i++)
                {
                    Console.Write(' ');
                }
                Console.SetCursorPosition(43, 10);
                for (int j = 0; j < 30; j++)
                {
                    Console.Write(' ');
                }
                Console.SetCursorPosition(0, 0);
                //Printing table
                for (int i = 0; i < table.GetLength(0); i++)
                {
                    for (int j = 0; j < table.GetLength(1); j++)
                    {
                        if (j < 11 && j > 1)
                        {
                            if (i == 1 || i == 10)
                            {
                                if (turn % 2 == 0) // Turn based color change
                                {
                                    Console.ForegroundColor = ConsoleColor.Blue;
                                }
                                else
                                {
                                    Console.ForegroundColor = ConsoleColor.Red;
                                }
                                Console.Write('═');
                                Console.ForegroundColor = ConsoleColor.White;
                            }
                            else
                                Console.Write(' ');
                        }
                        if (table[i, j] == X_piece) // Color
                        {
                            Console.ForegroundColor = ConsoleColor.Blue;
                        }
                        else if (table[i, j] == O_piece)
                        {
                            Console.ForegroundColor = ConsoleColor.Red;
                        }
                        else if ((table[i, j] == "╔" || table[i, j] == "╗" || table[i, j] == "╚" || table[i, j] == "╝" || table[i, j] == "║" || table[i, j] == "═") && turn % 2 == 0)
                        {
                            Console.ForegroundColor = ConsoleColor.Blue;
                        }
                        else if ((table[i, j] == "╔" || table[i, j] == "╗" || table[i, j] == "╚" || table[i, j] == "╝" || table[i, j] == "║" || table[i, j] == "═") && turn % 2 == 1)
                        {
                            Console.ForegroundColor = ConsoleColor.Red;
                        }
                        Console.Write(table[i, j]);
                        Console.ForegroundColor = ConsoleColor.White;
                    }
                    Console.WriteLine();
                }
                // Console Clear is not used to improve display, instead we deleted part by part
                Console.SetCursorPosition(0, 0);
                Console.Write(' ');
                Console.SetCursorPosition(0, 12);
                for (int j = 0; j < 50; j++)
                {
                    Console.Write(' ');
                }
                Console.SetCursorPosition(0, 14);
                for (int j = 0; j < 50; j++)
                {
                    Console.Write(' ');
                }
                //To control if there is a winner
                x_count = 0;
                for (int i = 2; i < 5; i++)
                {
                    for (int j = 2; j < 5; j++)
                    {
                        if (table[i, j] == X_piece)
                            x_count++;
                    }
                }
                o_count = 0;
                for (int i = 7; i < 10; i++)
                {
                    for (int j = 7; j < 10; j++)
                    {
                        if (table[i, j] == O_piece)
                            o_count++;
                    }
                }
                // Endgame messages
                Console.SetCursorPosition(25, 10);
                if (x_count == 9 || (quit == true && x_count > o_count))
                {
                    if (player_X == "c")
                    {
                        Console.Write("Winner: Computer (" + X_piece + ")        Press any key to exit...");
                    }
                    else
                    {
                        Console.Write("Winner: Player (" + X_piece + ")        Press any key to exit...");
                    }
                    break;
                }
                else if (o_count == 9 || (quit == true && o_count > x_count))
                {
                    if (player_O == "c")
                    {
                        Console.Write("Winner: Computer (" + O_piece + ")       Press any key to exit...");
                    }
                    else
                    {
                        Console.Write("Winner: Player (" + O_piece + ")       Press any key to exit...");
                    }

                    break;
                }
                else if (quit == true && o_count == x_count)
                {
                    Console.Write("It is a draw.        Press any key to exit...");
                    break;
                }
                Console.SetCursorPosition(cursor_x, cursor_y);
            }
            Console.ReadKey(true);
        }

        static void Main(string[] args)
        {
            X_piece = "X"; O_piece = "O"; // Default piece style
            int menu_Y = 1, gameDiffLevel = 3, gameModeSelection = 1, firstPieceSelection, secondPieceSelection;
            bool startGame = false, gameMode = false, diffLevel = false, exit = false, menu = true, pieceStyle = false, howToPlay = false;
            Console.CursorVisible = false;
            ConsoleKeyInfo cki;
            // Menu
            while (true)
            {
                Console.SetCursorPosition(0, 0); Console.ForegroundColor = ConsoleColor.Blue; Console.Write("CHIN");
                Console.ForegroundColor = ConsoleColor.Magenta; Console.Write("E"); Console.ForegroundColor = ConsoleColor.Red; Console.WriteLine("RS");
                Console.SetCursorPosition(0, 1); Console.ForegroundColor = ConsoleColor.Green; Console.WriteLine("Start Game");
                Console.SetCursorPosition(0, 2); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Game Mode");
                Console.SetCursorPosition(0, 3); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Difficulty Level");
                Console.SetCursorPosition(0, 4); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Piece Style");
                Console.SetCursorPosition(0, 5); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("How To Play");
                Console.SetCursorPosition(0, 6); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Exit");
                while (menu)
                {
                    if (Console.KeyAvailable)
                    {   //read key and take action
                        cki = Console.ReadKey(true);
                        // To set menu boundaries
                        if (cki.Key == ConsoleKey.UpArrow && menu_Y > 1) { menu_Y--; Console.SetCursorPosition(0, menu_Y); if (OperatingSystem.IsWindows()) { Console.Beep(880, 50); } }
                        else if (cki.Key == ConsoleKey.DownArrow && menu_Y < 6) { menu_Y++; Console.SetCursorPosition(0, menu_Y); if (OperatingSystem.IsWindows()) { Console.Beep(1318, 50); } }
                        else if (cki.Key == ConsoleKey.Enter) //if enter pressed
                        {
                            // actions
                            if (menu_Y == 1) { startGame = true; menu = false; }
                            else if (menu_Y == 2) { gameMode = true; menu = false; }
                            else if (menu_Y == 3) { diffLevel = true; menu = false; }
                            else if (menu_Y == 4) { pieceStyle = true; menu = false; }
                            else if (menu_Y == 5) { howToPlay = true; menu = false; }
                            else if (menu_Y == 6) { exit = true; menu = false; }
                            if (OperatingSystem.IsWindows()) { Console.Beep(1557, 50); }
                        }    
                        if (menu_Y == 1)// if cursor on start game
                        {
                            Console.SetCursorPosition(0, 1); Console.ForegroundColor = ConsoleColor.Green; Console.WriteLine("Start Game");
                            Console.SetCursorPosition(0, 2); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Game Mode");
                            Console.SetCursorPosition(0, 3); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Difficulty Level");
                            Console.SetCursorPosition(0, 4); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Piece Style");
                            Console.SetCursorPosition(0, 5); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("How To Play");
                            Console.SetCursorPosition(0, 6); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Exit");
                            Console.ResetColor();
                        }
                        else if (menu_Y == 2)// if cursor on game mode
                        {
                            Console.SetCursorPosition(0, 1); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Start Game");
                            Console.SetCursorPosition(0, 2); Console.ForegroundColor = ConsoleColor.Green; Console.WriteLine("Game Mode");
                            Console.SetCursorPosition(0, 3); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Difficulty Level");
                            Console.SetCursorPosition(0, 4); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Piece Style");
                            Console.SetCursorPosition(0, 5); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("How To Play");
                            Console.SetCursorPosition(0, 6); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Exit");
                            Console.ResetColor();
                        }
                        else if (menu_Y == 3)// if cursor on difficulty level
                        {
                            Console.SetCursorPosition(0, 1); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Start Game");
                            Console.SetCursorPosition(0, 2); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Game Mode");
                            Console.SetCursorPosition(0, 3); Console.ForegroundColor = ConsoleColor.Green; Console.WriteLine("Difficulty Level");
                            Console.SetCursorPosition(0, 4); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Piece Style");
                            Console.SetCursorPosition(0, 5); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("How To Play");
                            Console.SetCursorPosition(0, 6); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Exit");
                            Console.ResetColor();
                        }
                        else if (menu_Y == 4)// if cursor on piece style
                        {
                            Console.SetCursorPosition(0, 1); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Start Game");
                            Console.SetCursorPosition(0, 2); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Game Mode");
                            Console.SetCursorPosition(0, 3); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Difficulty Level");
                            Console.SetCursorPosition(0, 4); Console.ForegroundColor = ConsoleColor.Green; Console.WriteLine("Piece Style");
                            Console.SetCursorPosition(0, 5); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("How To Play");
                            Console.SetCursorPosition(0, 6); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Exit");
                            Console.ResetColor();
                        }
                        else if (menu_Y == 5)// if cursor on how to play
                        {
                            Console.SetCursorPosition(0, 1); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Start Game");
                            Console.SetCursorPosition(0, 2); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Game Mode");
                            Console.SetCursorPosition(0, 3); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Difficulty Level");
                            Console.SetCursorPosition(0, 4); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Piece Style");
                            Console.SetCursorPosition(0, 5); Console.ForegroundColor = ConsoleColor.Green; Console.WriteLine("How To Play");
                            Console.SetCursorPosition(0, 6); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Exit");
                            Console.ResetColor();
                        }
                        else if (menu_Y == 6)// if cursor on exit
                        {
                            Console.SetCursorPosition(0, 1); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Start Game");
                            Console.SetCursorPosition(0, 2); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Game Mode");
                            Console.SetCursorPosition(0, 3); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Difficulty Level");
                            Console.SetCursorPosition(0, 4); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("Piece Style");
                            Console.SetCursorPosition(0, 5); Console.ForegroundColor = ConsoleColor.White; Console.WriteLine("How To Play");
                            Console.SetCursorPosition(0, 6); Console.ForegroundColor = ConsoleColor.Green; Console.WriteLine("Exit");
                            Console.ResetColor();
                        }
                    }
                }
                // IF game started
                if (startGame)
                {
                    Console.Clear();
                    if (gameDiffLevel == 1) // Rookie
                    {
                        gameBot(20, gameModeSelection);
                        startGame = false;
                        menu = true;
                        menu_Y = 1;
                        Console.Clear();
                    }
                    else if (gameDiffLevel == 2) // Pro
                    {
                        gameBot(60, gameModeSelection);
                        startGame = false;
                        menu = true;
                        menu_Y = 1;
                        Console.Clear();
                    }
                    else if (gameDiffLevel == 3) // Master
                    {
                        gameBot(100, gameModeSelection);
                        startGame = false;
                        menu = true;
                        menu_Y = 1;
                        Console.Clear();
                    }
                }
                else if (gameMode) // Game mode selection
                {
                    Console.Clear();
                    Console.WriteLine("Please select a game mode.");
                    Console.WriteLine("1- Human vs Computer \n2- Human vs Human \n3- Computer vs Computer");
                    do
                    {
                        if (Int32.TryParse(Console.ReadLine(), out gameModeSelection) && gameModeSelection >= 1 && gameModeSelection <= 3)
                        {
                            break;
                        }
                        Console.WriteLine("Invalid game mode selection");
                    }
                    while (true);
                    Console.WriteLine("Press enter to go to main menu.");
                    menu = true;
                    gameMode = false;
                    Console.ReadLine();
                    menu_Y = 1;
                    Console.Clear();
                }
                else if (diffLevel) // Difficulty level selection
                {
                    Console.Clear();
                    Console.WriteLine("Please select a difficulty level.");
                    Console.WriteLine("1- Rookie \n2- Pro \n3- Master");
                    do
                    {
                        if (Int32.TryParse(Console.ReadLine(), out gameDiffLevel) && gameDiffLevel >= 1 && gameDiffLevel <= 3)
                        {
                            break;
                        }
                        Console.WriteLine("Invalid game diff level");
                    }
                    while (true);
                    Console.WriteLine("Press enter to go to main menu.");
                    menu = true;
                    diffLevel = false;
                    Console.ReadLine();
                    menu_Y = 1;
                    Console.Clear();
                }
                else if (pieceStyle) // Piece style selection
                {
                    Console.Clear();
                    Console.WriteLine("Please select a piece style for the first player.");
                    Console.WriteLine("1- ♥ \n2- ♦ \n3- ♣ \n4- ♠ \n5- ▼ \n6- ■ \n7- ☻ \n8- ☺ ");
                    do
                    {
                        if (Int32.TryParse(Console.ReadLine(), out firstPieceSelection) && firstPieceSelection >= 1 && firstPieceSelection <= 8)
                        {
                            break;
                        }
                        Console.WriteLine("Invalid piece selection.");
                    }
                    while (true);
                    switch (firstPieceSelection)
                    {
                        case 1: X_piece = "♥"; break;                                                     
                        case 2: X_piece = "♦"; break;                                                      
                        case 3: X_piece = "♣"; break;                                                      
                        case 4: X_piece = "♠"; break;                                                    
                        case 5: X_piece = "▼"; break;                                                    
                        case 6: X_piece = "■"; break;                          
                        case 7: X_piece = "☻"; break;                                                    
                        case 8: X_piece = "☺"; break;                         
                        default: break;
                           
                    }
                    Console.WriteLine();
                    Console.WriteLine("Please select a piece style for the second player.");
                    Console.WriteLine("1- ♥ \n2- ♦ \n3- ♣ \n4- ♠ \n5- ▼ \n6- ■ \n7- ☻ \n8- ☺ ");
                    do
                    {
                        if (Int32.TryParse(Console.ReadLine(), out secondPieceSelection) && secondPieceSelection >= 1 && secondPieceSelection <= 8 && secondPieceSelection != firstPieceSelection)
                        {
                            break;
                        }
                        Console.WriteLine("Invalid piece selection.");
                        if (firstPieceSelection == secondPieceSelection) { Console.WriteLine("Selected pieces must be different."); }
                    }
                    while (true);
                    switch (secondPieceSelection)
                    {
                        case 1: O_piece = "♥"; break;                                                     
                        case 2: O_piece = "♦"; break;                                                      
                        case 3: O_piece = "♣"; break;                                                      
                        case 4: O_piece = "♠"; break;                                                    
                        case 5: O_piece = "▼"; break;                                                    
                        case 6: O_piece = "■"; break;                          
                        case 7: O_piece = "☻"; break;                                                    
                        case 8: O_piece = "☺"; break;                         
                        default: break;
                           
                    }
                    Console.WriteLine("Press enter to go to main menu.");
                    Console.ReadLine();
                    menu = true;
                    menu_Y = 1;
                    pieceStyle = false;
                    Console.Clear();
                }
                else if (howToPlay) // Player guide
                {
                    Console.Clear();
                    Console.WriteLine("1-) The game is played on a 8*8 board. Players of the game are human (x) and computer (o). Human player \n" +
                        "starts the game. The game is turn based. The goal of the game is to be the first player to move all 9 pieces \n" +
                        "across the board and into their own home area. Each player's home area is the opposing 3*3 area. ");
                    Console.WriteLine("\n2-) All the moves are in 4 directions, diagonal moves cannot be used.\n" +
                        "-Step: If adjacent square of a piece in any direction (left, right, up or down) is empty, that piece can step into the empty square.\n" +
                        "-Jump: A piece can jump over only a single adjacent piece (his/her or opponent's). Jumping over 2 or more pieces or distant pieces is not allowed.\n\n" +
                        "Jumping operations can be continued with successive jumps (called jump chain) if possible, in the same turn. On the contrary, \n" +
                        "step operation is a single one. There is no capturing in this game, so all pieces remain active during the game.\n ");
                    Console.WriteLine("- Move cursor to the location of the piece.\n" +
                        "- Choose the piece by pressing key Z.\n" +
                        "- Choose target square by pressing key X.\n" +
                        "- If there is no successive jumps, end the move by pressing key C.\n" +
                        "- You can end the game by pressing ESC.\n");
                    Console.WriteLine("Press enter to go to main menu.");
                    Console.ReadLine();
                    menu = true;
                    menu_Y = 1;
                    howToPlay = false;
                    Console.Clear();
                }
                else if (exit) // Quit the game
                {
                    break;
                }
            }
        }
    }
}


