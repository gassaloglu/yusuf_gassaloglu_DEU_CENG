package pbl;

import java.io.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import enigma.core.Enigma;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class Main {
	public static enigma.console.Console cn = Enigma.getConsole("Keyboard");
	static Scanner scanner = new Scanner(System.in);	
	static Question[] question;
    static Participant[] participant;          
    static String[] dictionary ;
    static WordCloud[] keywords;
    static Statistics[] question_history;
    static boolean is_choice_1_done = false, is_choice_2_done = false, is_choice_3_done = false;
    static int question_number=1;
    static String [][] categoryStatistics;
	static String categoryString="";
    static int counter_statistics=0;
    
    // question count function
	static int getQuestionsLength() {
		return question.length;
	}
	
	// manual console clear function
	public static void consoleClear() {
		cn.getTextWindow().setCursorPosition(0, 0);
		for (int k = 0; k < 100; k++) {
			for (int k2 = 0; k2 < 100; k2++) {
				System.out.print(" ");
			}
			System.out.println(" ");
		}
		cn.getTextWindow().setCursorPosition(0, 0);		
	}
	
	
	// the procedure that gets questions file
	public static void getQuestionsFile() {
		File questionsFile = null;
		String[] questions = null;
		Scanner questionScan = null; 
		System.out.print( "Please enter the questions filename: " );
        String questionsFileName = scanner.nextLine(); // question files name scanner
        
        try {
            questionsFile = new File(questionsFileName+".txt"); // opening file
            questionScan = new Scanner( questionsFile );     // file scanner      
            String text = ""; // to store text in the program
            // writing file data to text
            while (questionScan.hasNextLine()) {
            	String[] temp = questionScan.nextLine().strip().split("#");
            	if (temp.length == 8) {
					for (int i = 0; i < temp.length; i++) {
						text += temp[i] + "#";
					}
					text += "\n";
				}
            	else {
            		String tempStr = "";
            		for (int i = 0; i < temp.length; i++) {
            			tempStr += temp[i] + "#";
					}
					System.out.println("The format of question is wrong -> "+ tempStr);
				}
            	
            }          
            questions = text.split("\n");        // to  separate questions    
            question = new Question[questions.length];   // resize the question array
        }
        catch (Exception e) {
			System.out.println(e);
			is_choice_1_done = false;
		}
        try {  		       
            // splits and stores question information question by question into object
            for (int i = 0; i < questions.length; i++) {
				String[] temp = questions[i].split("#"); 
				
					question[i] = new Question(temp[0], temp[1],temp[2],temp[3],temp[4],temp[5],temp[6],temp[7]); //store into the object
				}
            }
            catch (Exception e) {
            	System.out.println(e);
            }
	        if (questionScan != null) {
				questionScan.close(); // close scanner
			}
        } 
       
	// the procedure that gets participants file
	public static void getParticipants() {
		System.out.print( "Please enter the participants file name: " );
        String participantsFileName = scanner.nextLine(); // file name scanner
        
        try {

            File participantsFile = new File(participantsFileName +".txt"); //opening file
            Scanner participantScan = new Scanner( participantsFile );      // reading file     
            String text = ""; // to store text in the program
            // writing file data to text
            while ( participantScan.hasNextLine()) {
            	String[] temp = participantScan.nextLine().strip().split("#");
            	if (temp.length == 4) {
					for (int i = 0; i < temp.length; i++) {
						text += temp[i] + "#";
					}
					text += "\n";
            	}
            	else {
            		String tempStr = "";
            		for (int i = 0; i < temp.length; i++) {
            			tempStr += temp[i] + "#";
					}
					System.out.println("The format of participant is wrong -> " + tempStr);
				}
            }
            
            String[] participants = text.split("\n");       	// to  separate participants    
            participant = new Participant[participants.length];   // resize the question array      
            // splits and stores participant information participant by participant into object
            for (int i = 0; i < participants.length; i++) {
				String[] temp = participants[i].split("#");
				String[] temp_date = temp[1].split("\\.");
				String[] temp_address = temp[3].split(";");
				Date temp_date_obj = new Date(Integer.parseInt(temp_date[0]),Integer.parseInt(temp_date[1]), Integer.parseInt(temp_date[2]));
				Address temp_address_obj = new Address(temp_address[0],temp_address[1],temp_address[2],temp_address[3],temp_address[4]);
				//error for string date
				
				participant[i] = new Participant(temp[0], temp_date_obj ,temp[2],temp_address_obj,false); //store into the object
            }
	        participantScan.close();
        }        
        catch (FileNotFoundException e){
            System.out.println(e);
            is_choice_2_done = false;
        }
	}
    	
	// the procedure that prints category screen
	public static void categoryScreen() {

	     try {
	    	 System.out.println("CATEGORY \t\t THE NUMBER OF QUESTIONS");
	    	 boolean visited[] = new boolean[question.length];
	    	 boolean noEnglishCategory = true, noCengCategory=true; // data for compulsory categories
	    	    Arrays.fill(visited, false);
	    	    // counting categories
	    	    for (int i = 0; i < question.length; i++) {
	    	        if (visited[i] == true)
	    	            continue;
	    	    int count = 1;
	    	    for (int j = i + 1; j < question.length; j++) {
	            	
	    	        if (question[i].getCategory().equals(question[j].getCategory())) { //if it matches
	    	            visited[j] = true;
	    	            count++;
	    	        }
	    	    }	    
	    	    System.out.format("%-15s", question[i].getCategory());
	    	    System.out.format("%15s%n", count);
	    	    categoryString = categoryString.concat(question[i].getCategory()+";"+count+";");
	    	    if (question[i].getCategory().equals("English")) { // to control English category
	    			noEnglishCategory = false;
	    		} 	    
	    	    if (question[i].getCategory().equals("Computer")) { // to control Computer category
	    			noCengCategory = false;
	    		}
	    	}
	    	    if (noEnglishCategory || noCengCategory) {
	    	    	System.out.println("English and Computer Engineering are compulsory categories. Please check your file.");
	    			
	    		} 
		} catch (Exception e) {
			System.out.println(e);
			}
	    }
   
    // the procedure that shows difficulty statistics.
    public static void difficultyScreen() {
        try {
        	System.out.println("DIFFICULTY LEVEL \t THE NUMBER OF QUESTIONS");	
          	 boolean visited[] = new boolean[question.length];
          	    Arrays.fill(visited, false);
          	    for(int difficulty_level = 1; difficulty_level < 6; difficulty_level++) { // to sort in ascending order
          	    	int count = 0; //question counter
       	   	    for (int i = 0; i < question.length; i++) {
       	   	    	// counter
       	   	        if (visited[i] == false && Integer.valueOf(question[i].getDifficulty()) == difficulty_level) { 
       	   	        	count ++;  		   	     
       	   	        	}    	   	             	   	 
       	   	        }
       	   	        System.out.println("\t\t" + difficulty_level + "\t\t\t\t\t\t" + count);
       	   	    }
		} catch (Exception e) {
			System.out.println(e);
			}
    }
    
    // spell check algorithm
    public static void spellCheck() {
    	try {
    		boolean exit = false;
    		for (int i = 0; i < question.length; i++) {
        		String temp = "";
        		temp = question[i].getQuestion().replaceAll("\\p{Punct}", ""); //removing punctuation marks
        		String[] tempArr = temp.split(" ");   		 // Separator		
        		for (int j = 0; j < tempArr.length; j++) {
        			boolean needSpellCheck = true;          // if it needs spell check
        			// to control if word in the dictionary
        			for(String word : dictionary) {
        	    		if(tempArr[j].strip().equalsIgnoreCase(word)) {
        	    			needSpellCheck = false;   	    			
        	    			break;
        	    		}    	    		
            		}
        			if (needSpellCheck && tempArr[j].length() != 0) {		
        			// to control suggestions
    				for(String corrector : dictionary) {
    					boolean skip = false;
    					// first control is word length control 
    					if (corrector.length() == tempArr[j].length()) {
    						int tempCounter = 0; // counter for not matched letters
    						char[] tempLetters = new char[4];		// we need that array in case of reversed letters				
    						for (int k = 0; k < tempArr[j].length(); k++) {							
    							if(tempArr[j].toLowerCase().toCharArray()[k] != corrector.toLowerCase().toCharArray()[k]) {
    								tempCounter++;
    								//storing not matched letters
    								if(tempCounter == 1) { 
    									tempLetters[0] = tempArr[j].toLowerCase().toCharArray()[k];
    									tempLetters[1] =  corrector.toLowerCase().toCharArray()[k];			
    								}
    								else if(tempCounter == 2) {
    									tempLetters[2] = tempArr[j].toLowerCase().toCharArray()[k];
    									tempLetters[3] =  corrector.toLowerCase().toCharArray()[k];			
    								}		
    								// if there is more than 2 not matched letters
    								if(tempCounter > 2) {
    									break;
    									}
    								}
    							}
    						//if there is only 1 letter not matched
    						if (tempCounter == 1) {
    							System.out.println(question[i].getQuestion());
    							System.out.println(tempArr[j] +" --> "+ corrector); // suggestion screen
    							int choiceInput = -1;
    							boolean invalid = true;
    							// loop to get valid input
    					        do{
    					            System.out.print("Do you want to replace the word with the suggested word? (1-YES 2-NO 3-EXIT 4-SKIP WORD): ");
    					            if (scanner.hasNextInt()) {
    					                choiceInput = scanner.nextInt();
    					                scanner.nextLine();
    					                boolean flag = true;
    					                if (choiceInput > 0  && choiceInput < 5){
    					                    invalid = false;
    					                    flag = false;
    					                }
    					                if (flag){
    					                    System.out.println("Invalid input. Please enter only 1, 2, 3 and 4");
    					                }

    					            } else {
    					                System.out.println("Invalid input. Please enter only 1, 2, 3 and 4.");
    					                scanner.nextLine();
    					            }
    					        }while(invalid);
    							// if input is 1
    					        if(choiceInput == 1) {
    							question[i].setQuestion(question[i].getQuestion().replaceAll(tempArr[j], corrector)); //corrector
    					        }
    					        else if (choiceInput == 3) {
									exit = true;
								}
    					        
    					        else if (choiceInput == 4) {
									skip = true;
								}
    							System.out.println(question[i].getQuestion());	// to show new question	
    							System.out.println("------------------------");
    						}
    						// if there is reversed letters
    						else if(tempCounter == 2 && tempLetters[3] == tempLetters[0] && tempLetters[1] == tempLetters[2]) {
    							System.out.println(question[i].getQuestion());
    							System.out.println(tempArr[j] +" --> "+ corrector); // suggestion screen
    							int choiceInput = -1;
    							boolean invalid = true;
    							// loop to get valid input
    							 do{
     					            System.out.print("Do you want to replace the word with the suggested word? (1-YES 2-NO 3-EXIT 4-SKIP WORD): ");
     					            if (scanner.hasNextInt()) {
     					                choiceInput = scanner.nextInt();
     					                scanner.nextLine();
     					                boolean flag = true;
     					                if (choiceInput > 0  && choiceInput < 5){
     					                    invalid = false;
     					                    flag = false;
     					                }
     					                if (flag){
     					                    System.out.println("Invalid input. Please enter only 1, 2, 3 and 4");
     					                }

     					            } else {
     					                System.out.println("Invalid input. Please enter only 1, 2, 3 and 4.");
     					                scanner.nextLine();
     					            }
     					        }while(invalid);
    							 
    					        if(choiceInput == 1) {
    							question[i].setQuestion(question[i].getQuestion().replaceAll(tempArr[j], corrector));
    					        }
    					        else if (choiceInput == 3) {
									exit = true;
								}
    					        else if (choiceInput == 4) {
									skip = true;
								}
    							System.out.println(question[i].getQuestion());	
    							System.out.println("------------------------");
    							}					
    						}	
	    					if (exit || skip) {
	    						break;
	    					}
    					}
    	    		}
        			if (exit) {
						break;
					}
    	    	}
        		if (exit) {
					break;
        		}
	    		if (exit) {
					break;
	    		}
    		}
		} 
    	catch (Exception e) {
			System.out.println(e);
		}	   
	}
    
    // word cloud procedure
    public static void wordCloudFunc() {
    	try {
    		keywords = new WordCloud[question.length]; // resize array
    		// to get a key from every questions
        	for (int i = 0; i < question.length; i++) {
        		WordCloud key = new WordCloud(question[i].getQuestion());    		
        				keywords[i] = new WordCloud(key.wc(),Integer.parseInt(question[i].getDifficulty()), true); 	// the function that gets key from question	      		
    			}
		} catch (Exception e) {
			System.out.println(e);
		}	
    	
    }
		   
    // procedure that gets dictionary
    public static void getDictionary() {    	
    	try {  	        
        File dictionaryFile = new File("dictionary.txt"); // to open file       
        BufferedReader br = new BufferedReader(new FileReader(dictionaryFile));       // file reader
        dictionary = br.lines().toArray(String[]::new); //storing dictionary
		br.close();	
		} catch (IOException e) {
			System.out.println(e);
			}	                    			  
		}

    public static void competition() throws Exception { 
    	counter_statistics=0;
		question_history = new Statistics[question.length];
    	int money = 0, ddCount = 1;
    	boolean fiftyPerc = true, doubleDip = true, ddJoker = true;
    	String selectedChoice = "";
    	int question_counter = 1;
    	// to select participant
    	for (int i = 0; i < participant.length; i++) {
			participant[i].setIsPlayed(true);
    		consoleClear();
			System.out.println("Contestant :" + participant[i].getName());
			System.out.println("----------------------------------------");
			int count = 1, temp_question_number= question_number;
			boolean is_word_cloud_showed = false;
			boolean cycle = false;
			boolean up=false, down = false;
			
			while(!is_word_cloud_showed){				
				for (int j = 0; j < keywords.length; j++) {
					if(Integer.valueOf(question[j].getDifficulty()) == temp_question_number && keywords[j].notAsked()) {
						is_word_cloud_showed = true;
						if (count % 4 == 0) {
							System.out.println(keywords[j].getKey());
							count++;
						}
						else if (count % 4 != 0) {
							System.out.print(keywords[j].getKey() + "\t");
							count++;
						}
					}
					if(j == keywords.length-1 && !is_word_cloud_showed) {
						if(question_number==5) {
							temp_question_number--;
							if(temp_question_number==0) {
								cycle=true;
							}
						}
						else {
							if(!up) {
								temp_question_number++;
								if(temp_question_number==6) {
									up=true;
									temp_question_number=question_number;
								}
							}
							else if(up) {
								temp_question_number--;
								if(temp_question_number==0) {
									down=true;
								}
							}
							if(up && down) {
								cycle=true;
							}
						}
					}
					if(cycle) {
						is_word_cloud_showed=true;
					}
				}
			}
			if(cycle) {
				System.out.println("No more questions to ask. Redirecting to menu...");
        		Thread.sleep(2000);
				break;
			}
			System.out.println();
			
			System.out.print("Please enter a keyword: ");
			String keywordSelection = scanner.nextLine();
			Boolean flag = true;
			int time = 20;
			boolean keywordNotFound = true, timeIsUp = false;
			// matching keyword
			for (int j = 0; j < keywords.length; j++) { 
				
				if (keywordSelection.equalsIgnoreCase(keywords[j].getKey()) && keywords[j].notAsked() && keywords[j].getDifficultyLevel() == temp_question_number) {  
					keywordNotFound = false;						
					consoleClear();
				    // question display
					System.out.println("Q"+ question_counter + ")" + " " +question[j].getQuestion());
					// choices display
					System.out.println("A) " + question[j].getChoice1());
					System.out.println("B) " + question[j].getChoice2());
					System.out.println("C) " + question[j].getChoice3());
					System.out.println("D) " + question[j].getChoice4());
					// Input display		
					System.out.println("Enter your choice (E for exit): ");
					// info display
					cn.getTextWindow().setCursorPosition(50, 6);
					System.out.println("$ "+ money);					
					if (fiftyPerc) {
						cn.getTextWindow().setCursorPosition(50, 3);
						System.out.println("F : 50%");
					}
					else {
						cn.getTextWindow().setCursorPosition(50, 3);
						System.out.println("                     ");
					}
					if (doubleDip) {
						cn.getTextWindow().setCursorPosition(50, 4);
						System.out.println("G : DOUBLE DIP");
					}
					else {
						cn.getTextWindow().setCursorPosition(50, 4);
						System.out.println("                     ");
					}
					 cn.getTextWindow().setCursorPosition(35, 8);System.out.println("----------------------------------------");			
					 cn.getTextWindow().setCursorPosition(35, 9);System.out.println(" Question 1 -     $20,000	 tier-one");
					cn.getTextWindow().setCursorPosition(35, 10);System.out.println(" Question 2 -    $100,000	");
					cn.getTextWindow().setCursorPosition(35, 11);System.out.println("----------------------------------------");
					cn.getTextWindow().setCursorPosition(35, 12);System.out.println(" Question 3 -    $250,000	 tier-two");
					cn.getTextWindow().setCursorPosition(35, 13);System.out.println(" Question 4 -    $500,000	");
					cn.getTextWindow().setCursorPosition(35, 14);System.out.println("----------------------------------------");
					cn.getTextWindow().setCursorPosition(35, 15);System.out.println(" Question 5 -  $1,000,000	 tier-three");
					cn.getTextWindow().setCursorPosition(35, 16);System.out.println("----------------------------------------");
					cn.getTextWindow().setCursorPosition(0, 9);
					
					// counter	
					Countdown c = new Countdown();		
					while(true) {				
					    if (time >= 10) {
		            		cn.getTextWindow().setCursorPosition(50, 5);
		            		System.out.println("Remaining Time: " + time);
		            		Thread.sleep(1000);
		                    time--;
		                    cn.getTextWindow().setCursorPosition(0, 9);
						}
		            	else {
		            		cn.getTextWindow().setCursorPosition(50, 5);
		            		System.out.println("Remaining Time:  " +time);
		            		Thread.sleep(1000);
		            		time--;
		            		cn.getTextWindow().setCursorPosition(0, 9);
						} 					    
					    if (time < 0) {
		                	timeIsUp = true;
		    				cn.getTextWindow().setCursorPosition(50, 5);
		                    System.out.println("Time is Over!            ");
		                    cn.getTextWindow().setCursorPosition(0, 9);
		                    break;
		                }
				         if(c.keypr==1) {    // if keyboard button pressed
				            if(c.rkey==KeyEvent.VK_A) { selectedChoice = "A"; break; }
				            if(c.rkey==KeyEvent.VK_B) { selectedChoice = "B"; break; }
				            if(c.rkey==KeyEvent.VK_C) { selectedChoice = "C"; break; }
				            if(c.rkey==KeyEvent.VK_D) { selectedChoice = "D"; break; }
				            if(c.rkey==KeyEvent.VK_G && doubleDip) { selectedChoice = "G"; break; }
				            if(c.rkey==KeyEvent.VK_E) { selectedChoice = "E"; break; }
				            if(c.rkey==KeyEvent.VK_F && fiftyPerc) { selectedChoice = "F"; break;
				            }
				            c.keypr=0;    // last action  
				         }
							Thread.sleep(20);
				      }
					//if time is up				
					if (timeIsUp) {
						question_history[counter_statistics] = new Statistics(j,i, "Not answered: Time is up");
						counter_statistics++;
						System.out.println("Time Is Up");
						System.out.println("The correct answer is " + question[j].getAnswer());
						// money counter
						if (question_counter <= 2) {
							System.out.println(participant[i].getName() + " You won $ 0 ");
						}
						else if (question_counter <= 4) {
							System.out.println(participant[i].getName() + " You won $$ 100.000 ");
						}
						else {
							System.out.println(participant[i].getName() + " You won $$ 500.000 ");
						}

						// to play sfx
						try {
							File wavFile = new File("false.wav");
							Clip clip = AudioSystem.getClip();
							clip.open(AudioSystem.getAudioInputStream(wavFile));
							clip.start();
							Thread.sleep(1000);
						} catch (Exception e) {
							System.out.println(e);
						}
						System.out.println("Next Contestant Y/N?");
						String nextContestantChoise = scanner.nextLine();
						if (nextContestantChoise.equalsIgnoreCase("N")) {
							flag = false;
						}
						// revalue important values
						keywords[j].setAsked(false);
						keywords[j].setKey("     ");
						money = 0;
						keywordSelection = "";
						fiftyPerc = true;
						doubleDip = true;
						ddJoker = true;
						ddCount = 1;
						selectedChoice = "";
						question_counter = 1;
						question_number = 1;
					}
					// if the answer is correct answer
					if (selectedChoice.strip().equalsIgnoreCase(question[j].getAnswer())) {
						System.out.println(selectedChoice);
						System.out.println("CORRECT ANSWER");
						question_history[counter_statistics] = new Statistics(j,i, "True");
						counter_statistics++;
						participant[i].addOneToQuestionCount();
						
						i--; // to stay same participant
						// money counter
						switch (question_counter) {
						case 1:
							money = 20000;
							break;
						case 2:
							money = 100000;
							break;
						case 3:
							money = 250000;
							break;
						case 4:
							money = 500000;
							break;
						case 5:
							money = 1000000;
							break;
						}
						//revalue important values
						question_counter++;
						question_number++;
						keywords[j].setAsked(false);
						keywords[j].setKey("     ");
						keywordSelection = "";
						if (!doubleDip) {
							ddCount = 0;
						}
						//to play sfx
						try {
							File wavFile = new File("true.wav");
							Clip clip = AudioSystem.getClip();
							clip.open(AudioSystem.getAudioInputStream(wavFile));
							clip.start();
							Thread.sleep(4000);
						} catch (Exception e) {
							System.out.println(e);
						}					
					}					
					// if participant wants to exit
					else if (selectedChoice.strip().equalsIgnoreCase("E")) {
						System.out.println(selectedChoice);
						System.out.println(participant[i].getName() + " You won $" +money);
						question_history[counter_statistics] = new Statistics(j,i, "Not answered: Exited");
						counter_statistics++;
						
						System.out.println("Next Contestant Y/N?");
						String nextContestantChoise = scanner.nextLine();
						if (nextContestantChoise.equalsIgnoreCase("N")) {
							flag = false;
						}
						// revalue important values
						money = 0;
						keywords[j].setAsked(false);
						keywords[j].setKey("     ");
						keywordSelection = "";
						fiftyPerc = true;
						doubleDip = true;
						ddJoker = true;
						ddCount = 1;
						question_counter = 1;
						question_number = 1;
						
					}
					// if participant uses %50 joker
					else if (selectedChoice.strip().equalsIgnoreCase("F") && fiftyPerc) {
						fiftyPerc = false;
						// to play sfx
						try {
							File wavFile = new File("joker.wav");
							Clip clip = AudioSystem.getClip();
							clip.open(AudioSystem.getAudioInputStream(wavFile));
							clip.start();
						} catch (Exception e) {
							System.out.println(e);
						}
						// storing choices
						String[] choices = new String[4];
						choices[0] = question[j].getChoice1();
						choices[1] = question[j].getChoice2();
						choices[2] = question[j].getChoice3();
						choices[3] = question[j].getChoice4();
						// to remove random two wrong choices
						for (int k = 0; k < 2; k++) {
							int rnd = new Random().nextInt(choices.length);
							
							if (choices[rnd] != " - ") {
								if (!question[j].getAnswer().equalsIgnoreCase("A") && choices[rnd].equalsIgnoreCase(question[j].getChoice1()) && choices[rnd] == question[j].getChoice1()) {
									question[j].setChoice1(" - ");
									choices[rnd] = " - ";
								}
								else if (!question[j].getAnswer().equalsIgnoreCase("B") && choices[rnd].equalsIgnoreCase(question[j].getChoice2()) && choices[rnd] == question[j].getChoice2()) {
									question[j].setChoice2(" - ");
									choices[rnd] = " - ";
								}
								else if (!question[j].getAnswer().equalsIgnoreCase("C") && choices[rnd].equalsIgnoreCase(question[j].getChoice3()) &&  choices[rnd] == question[j].getChoice3()) {
									question[j].setChoice3(" - ");
									choices[rnd] = " - ";
								}
								else if (!question[j].getAnswer().equalsIgnoreCase("D") && choices[rnd].equalsIgnoreCase(question[j].getChoice4())&& choices[rnd] == question[j].getChoice4()) {
									question[j].setChoice4(" - ");
									choices[rnd] = " - ";
								}
								else {
									k--;
								}							
							}
							else {
								k--;
							}						
						}					
						
						j = -1;
					}
					// if participant uses double dip joker
					else if (selectedChoice.strip().equalsIgnoreCase("G") && doubleDip) {
						doubleDip = false;							
						ddJoker = false;
						j = -1;	
						// to play sfx
						try {
							File wavFile = new File("joker.wav");
							Clip clip = AudioSystem.getClip();
							clip.open(AudioSystem.getAudioInputStream(wavFile));
							clip.start();
						} catch (Exception e) {
							System.out.println(e);
						}
					}
					// if the answer is wrong answer					
					else {
						// double dip joker control (if participant used the joker)
						if (!ddJoker && ddCount > 0) {
							System.out.println(selectedChoice);
							if (selectedChoice.equalsIgnoreCase("A")) {
								question[j].setChoice1(" - ");
							}
							else if (selectedChoice.equalsIgnoreCase("B")) {
								question[j].setChoice2(" - ");
							}
							else if (selectedChoice.equalsIgnoreCase("C")) {
								question[j].setChoice3(" - ");
							}
							else if (selectedChoice.equalsIgnoreCase("D")) {
								question[j].setChoice4(" - ");
							}
							ddCount--;
							j=-1;
						}
						// if the answer wrong
						else {
							if (!timeIsUp) {
								question_history[counter_statistics] = new Statistics(j,i, "False");
								counter_statistics++;
								
								System.out.println(selectedChoice);
								System.out.println("WRONG ANSWER");
								System.out.println("The correct answer is " + question[j].getAnswer());
								// money counter
								if (question_counter <= 2) {
									System.out.println(participant[i].getName() + " You won $ 0 ");
								}
								else if (question_counter <= 4) {
									System.out.println(participant[i].getName() + " You won $$ 100.000 ");
								}
								else {
									System.out.println(participant[i].getName() + " You won $$ 500.000 ");
								}
								//to play sfx
								try {
									File wavFile = new File("false.wav");
									Clip clip = AudioSystem.getClip();
									clip.open(AudioSystem.getAudioInputStream(wavFile));
									clip.start();
									Thread.sleep(4000);
								} catch (Exception e) {
									System.out.println(e);
								}
								
								System.out.println("Next Contestant Y/N?");
								String nextContestantChoise = scanner.nextLine();
								if (nextContestantChoise.equalsIgnoreCase("N")) {
									flag = false;
								}
								// revalue important values
								keywords[j].setAsked(false);
								keywords[j].setKey("     ");
								keywordSelection = "";
								money = 0;
								fiftyPerc = true;
								doubleDip = true;
								ddJoker = true;
								ddCount = 1;
								question_counter = 1;
								question_number = 1;							
							}						
						}						
					}					
					// if participant wins the game
					if(question_counter == 6) {
						i++;
						System.out.println("Ding, ding, ding, we have a winner!");
						System.out.println(participant[i].getName() + " You won $$$ 1.000.000 ");
						System.out.println("Next Contestant Y/N?");
						String nextContestantChoise = scanner.nextLine();
						if (nextContestantChoise.equalsIgnoreCase("N")) {
							flag = false;
						money = 0;
					}
						// revalue important values
						keywordSelection = "";
						fiftyPerc = true;
						doubleDip = true;
						ddJoker = true;
						ddCount = 1;
						question_counter = 1;
						question_number = 1;
						money = 0;		
						}
					if (!flag) {
						break;
					}					
				}	
			if (!flag) {
					break;
			}
		}
		if (!flag) {
			consoleClear();
			break;
		}
		if (keywordNotFound) {
			i--;				
		}		
    }
}    

    public static void categoryCalculations() throws IOException {
    	int max_answered=0;
    	int contestant_id = 0;
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
    	LocalDateTime now = LocalDateTime.now(); 
    	int year= now.getYear();//systen year

    	// most successfull contestant 
    	for(int i = 0; i< participant.length;i++){
    		if(participant[i].getQuestionCount()>max_answered) {
    			max_answered = 	participant[i].getQuestionCount();	
    			contestant_id = i;
    		}
    	}
    	System.out.println("The most successfull contestant: "+participant[contestant_id].getName());

    	//2nd and 3rd line
    	String [] temp5;
    	temp5 = categoryString.split(";");
    	String[][] categoryStatistics = new String[(temp5.length)/2][4];
    	int a = 0;
    	for(int i = 0; i < temp5.length-1; i+=2) {//filling the array 
    		
    			categoryStatistics[a][0] = temp5[i]; //category
    			categoryStatistics[a][1] = temp5[i+1];//how many questions
    			categoryStatistics[a][2] = "0";//answered true
    			categoryStatistics[a][3] = "0";//the rate
    			a++;  		   	
    	}
    	
    	//stores the true answer's count
    	for(int i = 0; i<categoryStatistics.length;i++) {
    		for(int j = 0; j<counter_statistics;j++){
    			if((question[question_history[j].getQuestion_id()].getCategory()).equalsIgnoreCase(categoryStatistics[i][0])){
    				if(question_history[j].getAnswer().equalsIgnoreCase("True")) {
    					int tempNum = Integer.parseInt(categoryStatistics[i][2])+1;
    					categoryStatistics[i][2] = String.valueOf(tempNum);
    				}
    			}
    		}
    	}
    	//stores the rate
    	for(int i = 0; i<(temp5.length)/2;i++) {
    		double perc = Double.valueOf(categoryStatistics[i][2]) / Double.valueOf(categoryStatistics[i][1])*100;
    		categoryStatistics[i][3] = String.valueOf(perc);
    	}
    	
    	//most correctly and badly answered categories
    	double max2=0;
    	int temp_index2=0;
    	for(int i =0; i<(temp5.length)/2;i++) {
    		if(Double.valueOf(categoryStatistics[i][3])>max2) {
    			max2 = Double.valueOf(categoryStatistics[i][3]);
    			temp_index2= i;
    		}
    	}
    	double min = Double.valueOf(categoryStatistics[0][3]);
    	int temp_index3 = 0;
    	for(int i =0; i<(temp5.length)/2;i++) {
    		if(Double.valueOf(categoryStatistics[i][3])<min) {
    			min = Double.valueOf(categoryStatistics[i][3]);
    			temp_index3= i;
    		}
    	}
    	System.out.println("The category with the most correctly answered: "+categoryStatistics[temp_index2][0]);
    	System.out.println("The category with the most badly answered: "+categoryStatistics[temp_index3][0]);
    	
    	//year calculations
        DecimalFormat f = new DecimalFormat("##.00");//to round the result
    	double ans = 0;//how many questions answered true
    	double tot = 0;//how many people
    	double ans1 = 0;
    	double tot1 = 0;
    	double ans2 = 0;
    	double tot2 = 0;

    	for(int i = 0; i<participant.length; i++) {
    		if(participant[i].getIsPlayed()) {
    			int age = year - participant[i].getBirthDate().getY();//calculates the age
    			if(age<=30) {
    				ans = ans+ participant[i].getQuestionCount();// adds the number of true answers
    				tot++;//adds one to the people count
    			}
    			else if(age>30 && age<=50) {
    				ans1 = ans1+ participant[i].getQuestionCount();
    				tot1++;
    			}
    			else{
    				ans2 = ans2+ participant[i].getQuestionCount();
    				tot2++;
    			}
    		}
    		
    	}
    	if(tot == 0){// to avoid getting a NaN on the screen
    		System.out.print("Age<=30 0/5 \t");
    	}
    	else {
    		double result = ans/tot;
    		System.out.print("Age<=30 "+ f.format(result)+"/5"+"\t");
    	}
    	if(tot1==0) {
    		System.out.print("30<Age<=50 0/5 \t");
    	}
    	else{
    		double result = ans1/tot1;
    		System.out.print("30<Age<=50 "+ f.format(result)+"/5"+"\t");
    	}
    	if(tot2==0) {
    		System.out.println("Age>50 0/5 \t");
    	}
    	else {
    		double result = ans2/tot2;
    		System.out.println("Age>50 "+ f.format(result)+"/5"+"\t");
    	}

    	//cities
    	String [][] city_numbers = new String [participant.length][2];
    	for(int i = 0; i<participant.length;i++) {//to fill array
    		city_numbers[i][0]="city name";
    		city_numbers[i][1]="0";
    	}
    	for(int i = 0; i<participant.length; i++) {
    		String city = participant[i].getAddress().getProvince();
    		for(int j = 0; j<participant.length;j++) {
    			if(city_numbers[j][0].equalsIgnoreCase(city)) {//if city exists in the array adds one
    				int temp_number = Integer.valueOf(city_numbers[j][1])+1;
    				city_numbers[j][1] = String.valueOf(temp_number);
    				break;
    			}
    			if(city_numbers[j][0].equalsIgnoreCase("city name")) {//if city does not exist adds it to the array and makes its number 1 
    				city_numbers[j][0]= city;
    				city_numbers[j][1]="1";
    				break;
    			}
    		}
    	
    	}

    	//highest number of participants from which city
    	int max =0;
    	int temp_index=0;
    	for(int i = 0;i<participant.length;i++) {
    		if(Integer.valueOf(city_numbers[i][1])>max) {
    			max = Integer.valueOf(city_numbers[i][1]);
    			temp_index = i;
    		
    		}
    	}
    	System.out.println("The city with the highest number of participants: "+city_numbers[temp_index][0]);

    	//output file
    	File file = new File("answers_history.txt");
    	FileWriter fw = new FileWriter(file);
    	PrintWriter pw = new PrintWriter(fw);
    	for(int i =0; i<counter_statistics;i++) {
    		pw.print("("+question_history[i].getQuestion_id()+", "+question_history[i].getContestant_id()+", ");
    		pw.println(question_history[i].getAnswer()+")");
    	}
    	pw.close();
    	
    }    
    // variables for menu
    static int choiceInput= -1;
    static boolean invalid = true;    
    // menu procedure
    public static void Menu() throws Exception {
    	
    	do {
    		// to print screen
    		System.out.println("*      MENU      *");
            System.out.println("1- Load Questions");
            System.out.println("2- Load Participants");
            System.out.println("3- Start Competition");
            System.out.println("4- Show Statistics");
            System.out.println("5- Exit");
            boolean flag=false; // to get correct input
            do {
            	try {
                    System.out.print("Please enter Your Choice: ");
                    choiceInput = scanner.nextInt();
                    scanner.nextLine();
            		if (choiceInput<6 && choiceInput>0) {
    					flag = true;
    				}
    				else {
    					System.out.println("Please enter only 1, 2, 3, 4, 5");					
    				}
    			} catch (InputMismatchException e) {
    				System.out.println("Please enter a valid value.");
    				scanner.next();
    			}
            }while(!flag);   
            // load questions choice
            if (choiceInput==1) {
				is_choice_1_done = true;
				// calling procedures and functions
				getQuestionsFile();
        		System.out.println();
        		spellCheck();
        		System.out.println();
        		categoryScreen();
        		System.out.println();
        		difficultyScreen();
        		System.out.println();
        		wordCloudFunc();  
        		}
            // load participants choice
			else if (choiceInput==2) {
				is_choice_2_done=true; 
				getParticipants(); 
				consoleClear();
			}
            // start competition choice
			else if (choiceInput==3 ) {
				if(is_choice_1_done && is_choice_2_done) {
					try {
			    	    File wavFile = new File("comp.wav");
			    	    Clip clip = AudioSystem.getClip();
			    	    clip.open(AudioSystem.getAudioInputStream(wavFile));
			    	    clip.start();
			    	} catch (Exception e) {
			    	    System.out.println(e);
			    	}
            		competition();
            		consoleClear();
            		is_choice_3_done = true;
				}
				else {
					System.out.println("Option 1 and 2 must be done before option 3.");
				}
			}
            // show statistics choice
			else if (choiceInput==4) {
				if(is_choice_3_done) {
					System.out.println("------------");
					System.out.println();
					categoryCalculations();
					System.out.println();
					System.out.println("------------");
				}
				else {
					System.out.println("Option 3 must be done before option 4.");
				}					
			}
		} while (choiceInput!=5);       
    }
             
    public static void main(String[] args) throws Exception {   	     	     	
    	
    	getDictionary(); 
		cn.getTextWindow().setCursorPosition(5, 3);
		// welcome screen
    	System.out.println("| WELCOME |");
    	//to play sfx
    	try {
    	    File wavFile = new File("menu.wav");
    	    Clip clip = AudioSystem.getClip();
    	    clip.open(AudioSystem.getAudioInputStream(wavFile));
    	    clip.start();
    	} catch (Exception e) {
    	    System.out.println(e);
    	}
    	Thread.sleep(4000);
    	consoleClear();
    	Menu();
    	//to play sfx
    	try {
    	    File wavFile = new File("exit.wav");
    	    Clip clip = AudioSystem.getClip();
    	    clip.open(AudioSystem.getAudioInputStream(wavFile));
    	    clip.start();
    	} catch (Exception e) {
    	    System.out.println(e);
    	}
    	Thread.sleep(2000);
    	
        scanner.close();
    }
}

