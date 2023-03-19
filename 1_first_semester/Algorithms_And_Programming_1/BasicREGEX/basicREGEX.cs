using System;

namespace basicREGEX
{
    class Program
    {
        static void Main(string[] args)
        {
            string text = "", pattern = "", editedText = "", editedPatternText = "", patternsText = "",givenPattern = "";
            bool textValidation = true, patternValidation = true, noSignInPattern = true, starPattern = true, startsWithStar = true, endsWithStar = true, uniqPattern = true;
            string[] textSplit = text.Split(), editedTextSplit = editedText.Split(), patternsList = patternsText.Split();
            char[] letterControl = pattern.ToCharArray(), editedPattern = editedPatternText.ToCharArray(), patterns = editedPattern;
            int countStar = 0, countScore = 0;

            while (textValidation) // text input and text validation
            {
                Console.Write("Please enter a text: "); text = Console.ReadLine(); // getting text from user
                textSplit = text.Split(); // getting words from text
                for (int i = 0; i < textSplit.Length; i++)
                {
                    letterControl = textSplit[i].ToCharArray(); // converting to char array for control char validation
                    for (int j = 0; j < letterControl.Length; j++)
                    {
                        if ((letterControl[j] == 'a' || letterControl[j] == 'b' || letterControl[j] == 'c' || letterControl[j] == 'd' || letterControl[j] == 'e' || letterControl[j] == 'f' || letterControl[j] == 'g' || letterControl[j] == 'h' || letterControl[j] == 'i' || letterControl[j] == 'j' ||
                            letterControl[j] == 'k' || letterControl[j] == 'l' || letterControl[j] == 'm' || letterControl[j] == 'n' || letterControl[j] == 'o' || letterControl[j] == 'p' || letterControl[j] == 'q' || letterControl[j] == 'r' || letterControl[j] == 's' || letterControl[j] == 't' ||
                            letterControl[j] == 'u' || letterControl[j] == 'v' || letterControl[j] == 'w' || letterControl[j] == 'x' || letterControl[j] == 'y' || letterControl[j] == 'z' ||
                            letterControl[j] == 'A' || letterControl[j] == 'B' || letterControl[j] == 'C' || letterControl[j] == 'D' || letterControl[j] == 'E' || letterControl[j] == 'F' || letterControl[j] == 'G' || letterControl[j] == 'H' || letterControl[j] == 'I' || letterControl[j] == 'J' ||
                            letterControl[j] == 'K' || letterControl[j] == 'L' || letterControl[j] == 'M' || letterControl[j] == 'N' || letterControl[j] == 'O' || letterControl[j] == 'P' || letterControl[j] == 'Q' || letterControl[j] == 'R' || letterControl[j] == 'S' || letterControl[j] == 'T' ||
                            letterControl[j] == 'U' || letterControl[j] == 'V' || letterControl[j] == 'W' || letterControl[j] == 'X' || letterControl[j] == 'Y' || letterControl[j] == 'Z' || letterControl[j] == ',' || letterControl[j] == '.'))
                        {
                            textValidation = false; 
                            if (letterControl[j] == ',' || letterControl[j] == '.') { letterControl[j] = ' '; string commaAndDotreplacer = new string(letterControl);textSplit[i] = commaAndDotreplacer;} // comma and dot replace
                        }
                        else { textValidation = true; Console.WriteLine("Invalid char: " + letterControl[j]); j = letterControl.Length; i = textSplit.Length;} //if char is invalid
                    }
                }
            }    
            
            for (int i = 0; i < textSplit.Length; i++) { editedText = editedText +" "+ textSplit[i]; } //creating new sperated text
            editedTextSplit = editedText.Split(); //creating new words array
            for (int i = 0; i < editedTextSplit.Length; i++)
            {
                for (int j = 0; j < i; j++) { if (editedTextSplit[i] == editedTextSplit[j]) { editedTextSplit[j] = " "; } }// removing duplicates
            }
            editedText = "";
            for (int i = 0; i < editedTextSplit.Length; i++) { if (editedTextSplit[i] != " ") { editedText = editedText + " " + editedTextSplit[i]; } } //creating new seperaeted and text (removed duplicates)
            editedTextSplit = editedText.Split(); //creating new words array
            
            while (patternValidation) //pattern input and pattern validation
            {
                Console.Write("Please enter a pattern: "); pattern = Console.ReadLine(); givenPattern = pattern;
                pattern = pattern.Trim().ToLower(); // triming form left and right
                letterControl = pattern.ToCharArray(); editedPattern = pattern.ToCharArray();
                for (int j = 0; j < letterControl.Length; j++) //pattern char control
                {
                    if ((letterControl[j] == 'a' || letterControl[j] == 'b' || letterControl[j] == 'c' || letterControl[j] == 'd' || letterControl[j] == 'e' || letterControl[j] == 'f' || letterControl[j] == 'g' || letterControl[j] == 'h' || letterControl[j] == 'i' || letterControl[j] == 'j' ||
                        letterControl[j] == 'k' || letterControl[j] == 'l' || letterControl[j] == 'm' || letterControl[j] == 'n' || letterControl[j] == 'o' || letterControl[j] == 'p' || letterControl[j] == 'q' || letterControl[j] == 'r' || letterControl[j] == 's' || letterControl[j] == 't' ||
                        letterControl[j] == 'u' || letterControl[j] == 'v' || letterControl[j] == 'w' || letterControl[j] == 'x' || letterControl[j] == 'y' || letterControl[j] == 'z' ||
                        letterControl[j] == 'A' || letterControl[j] == 'B' || letterControl[j] == 'C' || letterControl[j] == 'D' || letterControl[j] == 'E' || letterControl[j] == 'F' || letterControl[j] == 'G' || letterControl[j] == 'H' || letterControl[j] == 'I' || letterControl[j] == 'J' ||
                        letterControl[j] == 'K' || letterControl[j] == 'L' || letterControl[j] == 'M' || letterControl[j] == 'N' || letterControl[j] == 'O' || letterControl[j] == 'P' || letterControl[j] == 'Q' || letterControl[j] == 'R' || letterControl[j] == 'S' || letterControl[j] == 'T' ||
                        letterControl[j] == 'U' || letterControl[j] == 'V' || letterControl[j] == 'W' || letterControl[j] == 'X' || letterControl[j] == 'Y' || letterControl[j] == 'Z' || letterControl[j] == '-' || letterControl[j] == '*'))
                    {
                        patternValidation = false;
                        if (letterControl[j] == '*') countStar++;
                        else if (letterControl[j] == '-') countScore++;
                        if (countStar > 0 && countScore > 0) { patternValidation = true; j = letterControl.Length; Console.WriteLine("Wrong Pattern"); countScore = 0; countStar = 0; } //pattern sign validation
                    }
                    else { patternValidation = true; j = letterControl.Length; Console.WriteLine("Invalid char"); countScore = 0; countStar = 0; }
                }
                bool flag = true;
                for (int i = 0; i < letterControl.Length; i++) //pattern simplification
                {
                    if (letterControl[i] == '*' && flag == true) { editedPattern[i] = letterControl[i]; flag = false; }
                    else if (letterControl[i] != '*' && letterControl[i] != ' ')
                    {
                        letterControl[i] = editedPattern[i];
                        if (i != letterControl.Length-1 && letterControl[i+1] == '*' && letterControl[i+1] != ' ') {flag = true; }
                    }
                    else { editedPattern[i] = ' ';}
                }
                for (int i = 0; i < editedPattern.Length; i++) { if (editedPattern[i] != ' ') { editedPatternText = editedPatternText += editedPattern[i]; } }// removing blank spaces
                editedPattern = editedPatternText.ToCharArray(); patterns = editedPattern;
                for (int j = 0; j < editedPattern.Length; j++) // controlling patterns first and last elements
                {
                    if (j == 0 && editedPattern[j] != '*') { startsWithStar = false; }
                    else if (j == editedPattern.Length - 1 && editedPattern[j] != '*') { endsWithStar = false; }
                    if (editedPattern[j] != '*') { patternsText += patterns[j]; }
                    else if (editedPattern[j] == '*' && (j != 0 || j != editedPattern.Length - 1)) { patternsText += ' '; }
                }
                patternsText = patternsText.Trim();
                patternsList = patternsText.Split(' ');
            }

            Console.ForegroundColor = ConsoleColor.Cyan;Console.WriteLine();Console.WriteLine("--\t-   *   -\t--");Console.ResetColor();
            for (int i = 0; i < letterControl.Length; i++)//if no sign pattern
            {
                if (letterControl[i] == '-') { noSignInPattern = false; starPattern = false; } 
                else if (letterControl[i] == '*') { noSignInPattern = false; }

            }
            if (noSignInPattern == true) // if no sign in pattern
            {
                for (int i = 0; i < textSplit.Length; i++) { if (textSplit[i].ToLower() == pattern) { Console.WriteLine(textSplit[i]); } }
            }

            else if (starPattern == true) // if pattern is a star pattern
            {
                for (int i = 0; i < editedTextSplit.Length; i++)
                {
                    string lowerWord = editedTextSplit[i]; lowerWord = lowerWord.ToLower(); //word reducing for correct control
                    int count = 0;
                    for (int j = 0; j < patternsList.Length; j++) { if (lowerWord.Contains(patternsList[j])) count++; } //first control
                    if (count == patternsList.Length)
                    {
                        int temp1 = 0, temp2 = 0;
                        bool flag = true, control = true;
                        string pat1 = "", pat2 = "";
                        for (int k = 0; k < patternsList.Length; k++)
                        {
                            pat2 = pat1; pat1 = patternsList[k];
                            temp2 = temp1; temp1 = lowerWord.IndexOf(patternsList[k]);
                            if (temp1 < temp2) { flag = false; }    // if there is a letter that breaks pattern order                    
                            if (pat1 == pat2 && control == true && pat1 != "") { uniqPattern = false; } //unique pattern control (e.g a*a*a is not uniq. but *a*b*a is uniq.)
                            else if (pat1 != pat2 && pat2 != "") { uniqPattern = true; control = false; }
                            if (uniqPattern) k = temp1;
                        }
                        if (startsWithStar == false && lowerWord.IndexOf(patternsList[0]) != 0) { flag = false; } // if pattern does not starts with a star
                        if (endsWithStar == false && lowerWord.IndexOf(patternsList[patternsList.Length - 1]) != (lowerWord.Length - patternsList[patternsList.Length - 1].Length)) { flag = false; }// if pattern does not ends with a star
                        if (lowerWord.Substring(lowerWord.Length - patternsList[patternsList.Length - 1].Length) == patternsList[patternsList.Length - 1]) { flag = true; }
                        if (uniqPattern == false)// if pattern is a uniq. pattern
                        {
                            count = 0;
                            for (int k = 0; k < lowerWord.Length; k++) { if (lowerWord[k].ToString() == patternsList[0]) { count++; } }
                            if (count < patternsList.Length) { flag = false; }
                        }
                        if (flag) { Console.WriteLine(editedTextSplit[i]); } // printing word
                    }
                }
            }

            else if (starPattern == false) // if pattern is a score pattern
            {
                for (int i = 0; i < editedTextSplit.Length; i++)
                {
                    string lowerWord = editedTextSplit[i]; lowerWord = lowerWord.ToLower();
                    char[] textLenght = lowerWord.ToCharArray();
                    if (textLenght.Length == pattern.Length)
                    {
                        int signCounter = 0;
                        for (int j = 0; j < letterControl.Length; j++)
                        {
                            if (letterControl[j] == '-') { signCounter++; }
                            if (letterControl[j] != '-' && textLenght[j] != letterControl[j]) { break; }
                            if (signCounter == countScore && j == letterControl.Length - 1) { Console.WriteLine(editedTextSplit[i]); }
                        }
                    }
                }
            }
            Console.WriteLine(); Console.ForegroundColor = ConsoleColor.Cyan; Console.Write("Given text: "); Console.ResetColor(); Console.WriteLine(text);
            Console.ForegroundColor = ConsoleColor.Cyan; Console.Write("Given pattern: "); Console.ResetColor(); Console.WriteLine(givenPattern);
            Console.WriteLine("Please press enter to exit.");Console.ReadLine();           
        }
    }
}
