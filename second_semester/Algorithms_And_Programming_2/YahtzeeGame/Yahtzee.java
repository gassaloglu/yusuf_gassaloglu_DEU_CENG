import java.io.File;
import java.util.Scanner;

public class Yahtzee {
	SingleLinkedList players = new SingleLinkedList();
	
	Yahtzee(){
		YahtzeeGamePlay();	
	}
	// random number generator
	int randomNumber(int min, int max) {
	    int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);  
		return random_int;
	}
	
	// dice
	int dice() {
		return randomNumber(1, 6);
	}
	
	//game
	void YahtzeeGamePlay() {
		SingleLinkedList p1 = new SingleLinkedList(); // first players numbers
		SingleLinkedList p2 = new SingleLinkedList(); // second players numbers
		int p1Score = 0 , p2Score = 0;
		// game loop
		for (int i = 0; i < 10; i++) {
			System.out.println("- Tour " + (i+1) + " -");
			// roll dice
			for (int j = 0; j < 6; j++) {
				if (j < 3) {
					p1.add(dice());
				}
				else {
					p2.add(dice());
				}
			}
			
			System.out.print("Player 1: "); p1.display();
			System.out.print("Player 2: "); p2.display();
			System.out.println();
			System.out.println("P1 score: " + p1Score);
			System.out.println("P2 score: " + p2Score);
			System.out.println();
			
			//Player 1
			//if there are 4 identical number increase the score
			p1Score += searchSameNumbers(p1);
			p2Score += searchSameNumbers(p2);
			
			boolean isScoreIncreased = false;
			// if both consecutive and identical numbers are exist remove the elements
			if (searchConsecutiveNumbers(p1) && searchSameNumbers(p1) > 0) {
				p1Score += 30;
				for (int j = 1; j <= 6 ; j++) {
					p1.pop(j , 1);
				}
				removeSameElements(p1 , 3);
				isScoreIncreased = true;
			}
			// if consecutive numbers are exist remove the elements
			else if (searchConsecutiveNumbers(p1)) {
				p1Score += 30;
				for (int j = 1; j <= 6 ; j++) {
					p1.pop(j , 1);
					isScoreIncreased = true;
				}
			}
			//if there are 4 identical number remove the elements
			else if (searchSameNumbers(p1) > 0) {
				removeSameElements(p1, 4);
				isScoreIncreased = true;
			}
			
			//Player 2
			// if both consecutive and identical numbers are exist remove the elements
			if (searchConsecutiveNumbers(p2) && searchSameNumbers(p2) > 0) {
				p2Score += 30;
				for (int j = 1; j <= 6 ; j++) {
					p2.pop(j , 1);
				}
				removeSameElements(p2 , 3);
				isScoreIncreased = true;
			}
			// if consecutive numbers are exist remove the elements
			else if (searchConsecutiveNumbers(p2)) {
				p2Score += 30;
				for (int j = 1; j <= 6 ; j++) {
					p2.pop(j , 1);
					isScoreIncreased = true;
				}
			}
			//if there are 4 identical number remove the elements
			else if (searchSameNumbers(p2) > 0) {
				removeSameElements(p2, 4);
				isScoreIncreased = true;
			}	
			
			// if any player gain score
			if (isScoreIncreased) {
				System.out.print("Player 1: "); p1.display();
				System.out.print("Player 2: "); p2.display();
				System.out.println();
				System.out.println("P1 score: " + p1Score);
				System.out.println("P2 score: " + p2Score);
				System.out.println();
			}
						
		}
		System.out.println("-- Final Status --");
		System.out.print("Player 1: "); p1.display();
		System.out.print("Player 2: "); p2.display();
		System.out.println();
		System.out.println("P1 score: " + p1Score);
		System.out.println("P2 score: " + p2Score);
		System.out.println();
		highScoreTable("HighScoreTable.txt"); // Open high score file
		System.out.println("------------");
		Player winner = new Player(null , 0); //Winner player object
		// if player 2 is the winner
		if (p2Score > p1Score) {
			System.out.println("Winner Player 2. Score: " + p2Score);
			System.out.print("Please enter a name: ");
			Scanner scanner = new Scanner(System.in);
			String winnerName = scanner.nextLine();
			// redefine the object
			winner = new Player(winnerName, p2Score);
			players.add(winner); // add the object to players single linked list
			scanner.close();
		}
		// if the game is a draw
		else if (p2Score == p1Score) {
			System.out.println("Draw Score: " + p1Score);
			System.out.print("Please enter a name: ");
			Scanner scanner = new Scanner(System.in);
			String winnerName = scanner.nextLine();
			// redefine the object
			winner = new Player(winnerName, p1Score);
			players.add(winner); // add the object to players single linked list
			scanner.close();
		}
		// if player 1 is the winner
		else {
			System.out.println("Winner Player 1. Score: " + p1Score);
			System.out.print("Please enter a name: ");
			Scanner scanner = new Scanner(System.in);
			String winnerName = scanner.nextLine();
			// redefine the object
			winner = new Player(winnerName, p1Score);
			players.add(winner); // add the object to players single linked list
			scanner.close();
		}
		
		players.sortList(); // sort the list
		System.out.println("\n-- High Score Table --");
		players.displayAndSavePlayers("HighScoreTable.txt");	
	}
	
	//function that searches consecutive numbers
	boolean searchConsecutiveNumbers(SingleLinkedList sll) {
		if (sll.search(1) && sll.search(2) && sll.search(3) && 
			sll.search(4) && sll.search(5) && sll.search(6)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//function that finds 4 same numbers and adds points
	int searchSameNumbers(SingleLinkedList sll) {
		int point = 0;
		for (int i = 1; i <= 6; i++) {
			if (sll.counter(i) >= 4) {
				point += 10;
			}
		}
		return point;
	}
	
	//function that removes same numbers
	void removeSameElements(SingleLinkedList sll, int count) {
		for (int i = 1; i <= 6; i++) {
			if (sll.counter(i) >= count) {
				sll.pop(i , count);
			}
		}
	}
	
	// gets players from the high score table and writes them to the single linked list
	void highScoreTable(String highScoreTableName) {
		try {
			File hsFile = new File(highScoreTableName);
			Scanner scanner = new Scanner(hsFile);
			int counter = 1 , score = 0;;
			Player tempPlayer;
			String name = null;
			while (scanner.hasNextLine()) {	
				/*
				  file format:
				  name              John
				  score             80
				 */	 
				if (counter % 2 == 1) {
					name = scanner.nextLine();
					counter++;
				}
				else {
					score = Integer.parseInt(scanner.nextLine());
					tempPlayer = new Player(name, score);
					players.add(tempPlayer);
					counter++;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			System.exit(0);
		}
				
	}
}
