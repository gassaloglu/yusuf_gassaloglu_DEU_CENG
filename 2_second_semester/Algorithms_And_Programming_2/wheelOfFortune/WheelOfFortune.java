import java.io.File;
import java.io.FileWriter;
import java.util.Locale;
import java.util.Scanner;

public class WheelOfFortune {

	Stack countriesStack;
	Stack alphabetStack = new Stack(26);
	Stack sortedCountriesStack;
	Stack pointsStack;
	Stack namesStack;
	Stack sortedPointsStack;
	Stack sortedNamesStack;
	static int money = 0;
	
	void getCountries() throws Exception {
		try {
			File countriesFile = null;
			Scanner countryScan = null;      
	    	countriesFile = new File("countries.txt"); // opening words file
	    	countryScan = new Scanner( countriesFile );     // file scanner      
	        int countryCounter = 0;
	        // Determine stack size 
	        while (countryScan.hasNextLine()) {           	
	        	String temp = countryScan.nextLine();
	        	if (temp != "") {
	            	countryCounter++;
	        	} 
	        }
	        countryScan.close();        
	        countryScan = new Scanner( countriesFile); //reopen the file to use hasNextLine
	        countriesStack = new Stack(countryCounter); //resize countries stack
	        // push countries into  the stack      
	        while (countryScan.hasNextLine()) {
	        	//remove all non alphanumeric chars
	        	String temp = countryScan.nextLine().toUpperCase(Locale.ENGLISH).replaceAll("[\\W]+","");       	
	        	if (temp != "") {
					countriesStack.push(temp);
				}     	
	        }
	        //new sorted countries stack
	    	sortedCountriesStack = new Stack(countryCounter);
	    	//sort algorithm
	        while (!countriesStack.isEmpty()) {            	
					String topStack = (String) countriesStack.pop();
					while (!sortedCountriesStack.isEmpty() && topStack.compareTo((String) sortedCountriesStack.peek()) > 0) {
						String topTempStack = (String) sortedCountriesStack.pop();
						countriesStack.push(topTempStack);
					}
					sortedCountriesStack.push(topStack);
	            	} 
	      countryScan.close();  
		} catch (Exception e) {
			System.out.println(e);
		}		            			           
	}
	//creating alphabet stack
	void alphabet() {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 25; i >= 0; i--) {
			alphabetStack.push(alphabet.charAt(i));		
		}
	}
	//random number generator		
	int randomNumber(int min, int max) {
	    int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);  
		return random_int;
	}
	// main game loop
	void gamePlay() throws Exception {
		try {
			int randomNum = randomNumber(1, sortedCountriesStack.size()) -1; // selects random number between 1 and the stack size
			System.out.println("Randomly generated number: " + (randomNum+1));
			Stack reversedTempStack = new Stack(randomNum); 		
			int counter = 0;
			// storing countries before the selected country on temporary stack
			while (counter < randomNum) {
				String temp = (String) sortedCountriesStack.pop();
				reversedTempStack.push(temp);
				counter++;
			}
			
			String selectedCountry = (String) sortedCountriesStack.pop(); //selects random country
			// main stack retrieves remaining countries from temporary stack
			counter = 0;
			while (counter < randomNum) {
				String temp = (String) reversedTempStack.pop();
				sortedCountriesStack.push(temp);
				counter++;
			}
			
			CircularQueue questionAnswer = new CircularQueue(selectedCountry.length()); //storing country characters
			CircularQueue question = new CircularQueue(selectedCountry.length());       // for display
			// filling question stack
			for (int i = 0; i < selectedCountry.length(); i++) {
				question.enqueue('-');
				questionAnswer.enqueue(selectedCountry.charAt(i));
			}
			CircularQueue wheelQueue = new CircularQueue(8);  //wheel queue
			//filling wheel queue
			wheelQueue.enqueue("10");wheelQueue.enqueue("50");wheelQueue.enqueue("100");
			wheelQueue.enqueue("250");wheelQueue.enqueue("500");wheelQueue.enqueue("1000");
			wheelQueue.enqueue("Double Money");wheelQueue.enqueue("Bankrupt");
			// Another way to create wheel is to use switch case method
			// I used circular queue to make it feel like it's turning here
			
			Stack tempStack;
			Stack sortedAlphabet = new Stack(26);
			int generalCounter = 26, step= 1;
			//main game loop                  
			while (generalCounter >0) { // There are 26 letters in the English alphabet. So it takes a maximum of 26 rounds.
				boolean wordFounded = true;
				boolean intWheel = true;
				
				int wheelTurn = randomNumber(1, 8); // random value for wheel
				// wheel turning
				for (int i = 0; i < wheelTurn; i++) {
					String temp = (String) wheelQueue.dequeue();
					wheelQueue.enqueue(temp);
				}
				//wheel stopped
				String wheel = (String) wheelQueue.dequeue();
				wheelQueue.enqueue(wheel);
				System.out.println("Wheel: " + wheel);
				
				// if wheel bankrupt
				if (wheel.equals("Bankrupt")) {
					money = 0;
					intWheel = false;
					wordFounded = false;
				}
				System.out.println("Step: " + step);

				if (intWheel) {
					randomNum = randomNumber(1, generalCounter) -1; //random letter generator
					tempStack = new Stack(randomNum);
					counter = 0;
					//to store the popped values ​​in temporary stack.
					while (counter < randomNum) {
						char temp = (char) alphabetStack.pop();
						tempStack.push(temp);
						counter++;
					}
					char selectedChar = (char) alphabetStack.pop(); // random selected letter
					System.out.println("Guess: " + selectedChar);
					boolean doubled = false;
					for (int i = 0; i < question.size(); i++) { //Comparison of the answer characters and randomly selected character			
						char answerLetter = (char) questionAnswer.dequeue(); 
						char screenLetter = (char) question.dequeue();
						if (selectedChar == answerLetter) {
							// If wheel is Double Money
							if (wheel.equals("Double Money")) {
								doubled = true;
							}
							else {						
								money += Integer.parseInt(wheel);
							}
							screenLetter = answerLetter;
						}
						// End game control
						if (screenLetter == '-') {
							wordFounded = false;
						}
						//to store the dequeued values ​​in the same queue. (FIFO)
						questionAnswer.enqueue(answerLetter);
						question.enqueue(screenLetter);
						System.out.print(screenLetter);
					}
					//if wheel is double money
					if (doubled) {
						money *= 2;
					}
					System.out.println();
					System.out.println("Score: " + money);
					// adding all alphabet chars in tempStack into sortedAlphabet
					while (randomNum != 0 && !tempStack.isEmpty()) {
						char temp = (char) tempStack.pop();
						alphabetStack.push(temp);	
					}
					// adding all alphabet chars in alphabetStack into sortedAlphabet
					while (!alphabetStack.isEmpty()) {
						char temp = (char) alphabetStack.pop();
						sortedAlphabet.push(temp);	
					}
					
					Stack tempS = new Stack(26); // for printing to	screen		
					while (!sortedAlphabet.isEmpty()) {
						char temp = (char) sortedAlphabet.pop();
						tempS.push(temp);
						alphabetStack.push(temp);
					}
					while (!tempS.isEmpty()) {
						System.out.print(tempS.pop());
					}			
					generalCounter--;
				}
				//end game control
				if (wordFounded) {
					break;
				}
				step++;
				Thread.sleep(2000);  // program waits 2 seconds between letter guesses
				System.out.println();System.out.println();
			}
			System.out.println();System.out.println();	
			System.out.println("You win $" + money);
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	// high score table procedure
	void highScoreTable() throws Exception{
		try {
			System.out.println();
			System.out.println();
			System.out.println("HIGH SCORE TABLE");
			File highScoreFile = null;
			Scanner highScoreScan = null;
				
			highScoreFile = new File("HighScoreTable.txt"); // opening the  file
	    	highScoreScan = new Scanner( highScoreFile );     // file scanner      
	        int highScoreCounter = 0;
	        // Determine stack size 
	        while (highScoreScan.hasNextLine()) {           	
	        	String temp = highScoreScan.nextLine();
	        	if (temp != "") {
	            	highScoreCounter++;
	        	} 
	        }
	        highScoreScan.close();
	        
	        highScoreScan = new Scanner( highScoreFile); //reopen the file to use hasNextLine
	        pointsStack = new Stack(highScoreCounter + 1); // +1 for our player
	        namesStack =new Stack(highScoreCounter + 1);   // +1 for our player
	        //Push into stack      
	        while (highScoreScan.hasNextLine()) {
	        	String temp = highScoreScan.nextLine();
	        	temp = temp.strip();
	        	String[] tempArr = temp.split(" ");  // split the line according to the corresponding format
	        	pointsStack.push(Integer.parseInt(tempArr[1])); // to push the score
	        	namesStack.push(tempArr[0]);      	//to push the name
	        }
	        
	        pointsStack.push(money); // to push players score
	        namesStack.push("You");  // to push players name (default : "You")
	        
	        // new empty stacks will be used to rank by score
	        sortedPointsStack = new Stack(highScoreCounter + 1);
	        sortedNamesStack  = new Stack(highScoreCounter + 1);
	        //sort algorithm
	        while (!pointsStack.isEmpty()) {   	
				int topPointStack =  (int) pointsStack.pop();
				String topNameStack = (String) namesStack.pop();		
				while (!sortedPointsStack.isEmpty() && topPointStack < (int) sortedPointsStack.peek()) {
					int topTempPointStack = (int) sortedPointsStack.pop();
					String topTempNameStack = (String) sortedNamesStack.pop();
					pointsStack.push(topTempPointStack);
					namesStack.push(topTempNameStack);
				}
				sortedPointsStack.push(topPointStack);
				sortedNamesStack.push(topNameStack);
	        	}
	        //to write new sorted high score table
	        FileWriter writer = new FileWriter("HighScoreTable.txt", false);
	        // to store only first 10 competitors
	        if (sortedPointsStack.size() >= 10) {
				for (int i = 0; i < 10; i++) {
					String tempName = (String) sortedNamesStack.pop();
		        	int tempPoint =  (int) sortedPointsStack.pop();		  
		        	writer.write(tempName + " " + tempPoint + "\n");	       		 
					System.out.println(tempName + " ---- " + tempPoint);
				}
			}
	        else {
				while (!sortedPointsStack.isEmpty()) {
	        	String tempName = (String) sortedNamesStack.pop();
	        	int tempPoint =  (int) sortedPointsStack.pop();		  
	        	writer.write(tempName + " " + tempPoint + "\n");	       		 
				System.out.println(tempName + " ---- " + tempPoint); 
				}       
			}
	        writer.close(); 
			highScoreScan.close();
		} catch (Exception e) {
			System.out.println(e);
		}
							
	}	
	// calling functions and procedures
	WheelOfFortune() throws Exception{		
		getCountries();
		alphabet();
		gamePlay();		
		highScoreTable();
	}
}
