package pbl2;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import enigma.console.TextAttributes;
import enigma.core.Enigma;

public class StarTrek {
	public static enigma.console.Console cn = Enigma.getConsole("Star Trek Warp Wars", 100,24,20);
	Number[] number;
	Device[] device;
	Computer[] computer;
	char[][] maze;
	Stack backpack;
	Player player;
	Coordinate pCoordinate, nCoordinate, dCoordinate, cCoordinate;
	Object[][] objectArray;
	CircularQueue pathFinder; 
	CircularQueue elements; 
	int numberArrayCounter = 0, deviceArrayCounter = 0, computerArrayCounter= 0, computerScore = 0;
	TextAttributes blackColor=new TextAttributes(Color.black,Color.white);
	TextAttributes greenColor=new TextAttributes(Color.green,Color.black);
	TextAttributes redColor=new TextAttributes(Color.red,Color.black);
	TextAttributes yellowColor=new TextAttributes(Color.yellow,Color.black);
	TextAttributes mapAtt = new TextAttributes(Color.gray , Color.gray);
	TextAttributes mapAtt2 = new TextAttributes(Color.yellow , Color.yellow);
	TextAttributes reset = new TextAttributes(Color.white , Color.BLACK);
	TextAttributes cyanColor = new TextAttributes(Color.cyan , Color.BLACK);
		
	StarTrek() throws Exception {
		number = new Number[1265]; // Number array stores numbers
		device = new Device[1265]; // Device array stores devices
		computer = new Computer[1265]; // Computer array stores computers	
		pathFinder = new CircularQueue(100); // to store previous movements
		elements = new CircularQueue(15); // elements those will place into maze		
		pCoordinate = new Coordinate(1, 1);
		nCoordinate = new Coordinate(0, 0);
		dCoordinate = new Coordinate(0, 0);
		cCoordinate = new Coordinate(0, 0);
		objectArray = new Object[23][55]; // to store objects
		backpack = new Stack(8); //creating backpack
		player = new Player(pCoordinate,0,5,50,backpack); // creating player
		maze = new char[23][55]; // creating maze char array to easy access	 
		menu();	
		
	} 
	// random number generator
	int randomNumber(int min, int max) {
	    int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);  
		return random_int;
	}
	//to fill queue
	void fillQueue() {
		// filling queue 
		while(!(elements.isFull()))
	    {
	        Random rnd = new Random();
	        int a = (rnd.nextInt(40) + 1);
	        if(a<13) {elements.enqueue('1');}	         	        
	        else if(a<21) {elements.enqueue('2');}	            	        
	        else if(a<27) {elements.enqueue('3');}	            	        
	        else if(a<32) {elements.enqueue('4');}	            	        
	        else if(a<36) {elements.enqueue('5');}	            	        
	        else if(a<38) {elements.enqueue('=');}	            	        
	        else if(a<39) {elements.enqueue('*');}	            	        
	        else if(a<41) {elements.enqueue('C');}	            	                	      
    	}
	}
	// the function that puts the numbers into the maze
	void placeTheNumber(int numberValue, char chrNumValue, boolean isMoving, int numberScore) {
		int xPosition,yPosition;
		while (true) {
			// selects random empty cell
        	xPosition = randomNumber(1,54);
            yPosition = randomNumber(1,22);
           if (maze[yPosition][xPosition] == ' '  && objectArray[yPosition][xPosition] == null) {
        	   nCoordinate = new Coordinate(xPosition, yPosition);
        	   break;
           }
		}
        number[numberArrayCounter] = new Number(nCoordinate,numberValue,numberScore,isMoving); //adding the element into number array
        objectArray[nCoordinate.getY()][nCoordinate.getX()] = number[numberArrayCounter]; // filling object array
        cn.getTextWindow().output(nCoordinate.getX(),nCoordinate.getY(),chrNumValue); //printing the element into maze
		maze[nCoordinate.getY()][nCoordinate.getX()] = chrNumValue; //filling the maze
		numberArrayCounter++; 
	}
	// the function that puts the devices into the maze
	void placeTheDevice(char chrNumValue) {
		int xPosition,yPosition;
		while (true) { 
			// selects random empty cell
			xPosition = randomNumber(1,54);
			yPosition = randomNumber(1,22);
			if (maze[yPosition][xPosition] == ' '  && objectArray[yPosition][xPosition] == null) {
    	   dCoordinate = new Coordinate(xPosition, yPosition);
    	   break;
       		}
		}
   	   device[deviceArrayCounter] = new Device(dCoordinate,chrNumValue,300,25); //adding the device into device array
       objectArray[dCoordinate.getY()][dCoordinate.getX()] = device[deviceArrayCounter]; // filling object array
       cn.getTextWindow().output(dCoordinate.getX(),dCoordinate.getY(),chrNumValue, redColor); //printing the device into maze
	   maze[dCoordinate.getY()][dCoordinate.getX()] = chrNumValue; //filling the maze
	   deviceArrayCounter++; 
	}
	
	//to get element from queue
	void elementsQueue(){
		cn.getTextWindow().setCursorPosition(60, 0);
		System.out.println("Queue");    
		fillQueue();
	    char selectedElement = (char) elements.dequeue(); //selectedElement in process
	    fillQueue();
	    //printing queue
	    cn.getTextWindow().setCursorPosition(60,1);
	    cn.getTextWindow().output("<<<<<<<<<<<<<<<", blackColor);
	    for (int i = 0; i < 15; i++) {
			char tempChar = (char) elements.dequeue();
			cn.getTextWindow().setCursorPosition(60 + i, 2);
			System.out.println(tempChar);
			elements.enqueue(tempChar);
		  }  
	    cn.getTextWindow().setCursorPosition(60,3);
	    cn.getTextWindow().output("<<<<<<<<<<<<<<<", blackColor); 
	    int xPosition,yPosition;
	    // to identify the element
	    if(selectedElement == '1') {
	    	placeTheNumber(1, selectedElement, false, 1);   
        }
        else if(selectedElement == '2') {
	    	placeTheNumber(2, selectedElement, false, 5);
        }
        else if(selectedElement == '3') {
	    	placeTheNumber(3, selectedElement, false, 15);   
        }
        else if(selectedElement == '4') {
	    	placeTheNumber(4, selectedElement, true, 50);   
        }
        else if(selectedElement == '5') {
	    	placeTheNumber(5, selectedElement, true, 150);   
        }
        else if(selectedElement == '=') {
        	placeTheDevice(selectedElement);	         
        }
        else if(selectedElement == '*') {
        	placeTheDevice(selectedElement);
        }
	    // to put the computers into the maze
        else if(selectedElement == 'C' ) { 
        	// selects random empty cell
        	while (true) {
            	xPosition = randomNumber(1,54);
                yPosition = randomNumber(1,22);
               if (maze[yPosition][xPosition] == ' '  && objectArray[yPosition][xPosition] == null) {
            	   cCoordinate = new Coordinate(xPosition, yPosition);
            	  break;
               }
        	}
               computer[computerArrayCounter] = new Computer(cCoordinate, maze, true); // adding the computer into computer array         
               objectArray[cCoordinate.getY()][cCoordinate.getX()] = computer[computerArrayCounter]; // filling object array
               cn.getTextWindow().output(cCoordinate.getX(),cCoordinate.getY(),'C', redColor); // printing the device into maze
               maze[cCoordinate.getY()][cCoordinate.getX()] = 'C'; //filling the maze
               computerArrayCounter++;			
        }
  	}
	
	void screen() throws Exception {
		cn.getTextWindow().setCursorPosition(0, 0);
		File map = new File("map.txt");
		FileReader fReader = new FileReader(map);
		String line;		
		BufferedReader bReader = new BufferedReader(fReader);		
		int linecounter = 0;	
		// to declare maze array
		while((line = bReader.readLine()) != null) {
			for (int i = 0; i < 55; i++) {
				maze[linecounter][i] = ' ';
				if(line.charAt(i) == '#')  {	
					objectArray[linecounter][i] = '#';
					maze[linecounter][i] = '#';
				}
			}
			linecounter++;
		}
		bReader.close();
		// to print maze
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if (maze[i][j] == '#') {
					cn.setTextAttributes(mapAtt);
				}
				System.out.print(maze[i][j]);
				cn.setTextAttributes(reset);
			}
			System.out.println();
		}	
	}
	
	// game play function
	void gamePlay() throws Exception {	
		// to declare how many computers will spawn at the beginning
		Scanner scanner = new Scanner(System.in);
		cn.getTextWindow().setCursorPosition(27, 12);
		System.out.println("How many enemies do you want the game to start with?");
		int numberOfComputersAtTheBeginning;
            do {
            	try {
            		cn.getTextWindow().setCursorPosition(20, 12);
            		System.out.println("At least how many enemies do you want the game to start with?") ;
            		cn.getTextWindow().setCursorPosition(27, 13);
            		System.out.println("  (1-3) Beginner || (4-6) Semi-Pro || (7-8)  Pro");
            		cn.getTextWindow().setCursorPosition(50, 14);
            		numberOfComputersAtTheBeginning = scanner.nextInt();
            		if (numberOfComputersAtTheBeginning <= 8 && numberOfComputersAtTheBeginning > 0) {
    					break;
    				}
    				else {
    					consoleClear();
    					cn.getTextWindow().setCursorPosition(37, 16);
        				System.out.println("Please enter a valid value.");
    				}
    			} catch (InputMismatchException e) {
    				consoleClear();
    				cn.getTextWindow().setCursorPosition(37, 16);
    				System.out.println("Please enter a valid value.");
    				scanner.next();
    			    
    			}
            }while(true);   
            
		for (int i = 0; i < numberOfComputersAtTheBeginning; i++) {
			elements.enqueue('C');
		}
		consoleClear();
		//count down
		for (int i = 5; i > 0; i--) {
    		cn.getTextWindow().setCursorPosition(33, 12);
			System.out.println("The game will start in "+ i + " seconds...");
			Thread.sleep(1000);
		}
		consoleClear();
		screen();		
		Movement m = new Movement();		 
		int px=1,py=1;
	 	cn.getTextWindow().output(px,py,'P', yellowColor);
	 	maze[px][py] = 'P';					//Defining the player into array.
	    long time = 0; //delta time
	    long time1 = System.nanoTime();
	    int queueTimer = 3000; // new element will dequeue every 3 seconds
	    int movingNumberAndComputerTimer = 500; // moving numbers speed
	    int deviceTimer = 1000;
	    int energyTimer = 500; //energy timer will decrease 1 by 1 second
	    int playerSpeedbyTimer = 500;
	    int playerSpeedbyTimerAdder = 0;
	    //filling the map for the beginning of the game
	    for (int i = 0; i < 20; i++) {
			 elementsQueue();
		}	
	    
	    cn.getTextWindow().setCursorPosition(70, 10);
	    System.out.println("Press P to exit the game");
	    while(true) {
		    	long time2 = System.nanoTime();
		    	time = (time2 - time1) / 1000000; //delta time
		    	//queue timer	    	
		    	if (time > queueTimer) {
					elementsQueue();
					queueTimer +=3000;
				}
		    	//set player coordinate
		    	pCoordinate.setX(px);
		    	pCoordinate.setY(py);
		    	player.setpCoordinate(pCoordinate);
		    	//time display
		    	cn.getTextWindow().setCursorPosition(60, 22);
	            System.out.println("Time: " + time/1000);
	            // backpack string display
	            cn.setTextAttributes(mapAtt2);
	            cn.getTextWindow().setCursorPosition(62, 13);
		    	System.out.println("+---+");
		    	for (int i = 0; i < 8; i++) {
		    		cn.getTextWindow().setCursorPosition(62, 12-i);
		    		System.out.println("|");
		    		cn.getTextWindow().setCursorPosition(66, 12-i);
		    		System.out.println("|");
		    	}	
		    	cn.setTextAttributes(reset);
		    	cn.getTextWindow().setCursorPosition(60, 14);
		    	System.out.println("P.Backpack");
		    	// adjusting player speed according to energy		    	
		    	if (player.getpEnergy() > 0) {
		    		playerSpeedbyTimerAdder = 250;	 
		    	}
		    	else {
		    		 playerSpeedbyTimerAdder = 500;
				}		    	
		    	if (time > playerSpeedbyTimer) {
		    		
		    		if(m.keypr==1) {
				    	char chr;			
				    	//to clear backpack text
				    	for (int i = 0; i < 8; i++) {
				    		cn.getTextWindow().setCursorPosition(64, 5+i);
					    	System.out.println(" ");
						}
				    	// printing backpack elements
				    	Stack tempBackPackStack = new Stack(8);			    	
				    	while (!player.getBackpack().isEmpty()) {
							char temp = (char) player.getBackpack().pop();
							tempBackPackStack.push(temp);					
						}
				    	int backpackPlacePrinter = 12; // Declaring start and end point for print backpack
				        while (!tempBackPackStack.isEmpty()) {
				        	char temp = (char) tempBackPackStack.pop();
				        	cn.getTextWindow().setCursorPosition(64, backpackPlacePrinter);
							System.out.println(temp);
				        	player.getBackpack().push(temp);
				        	backpackPlacePrinter--;
						}
				        backpackPlacePrinter = 12;
			
				    	cn.getTextWindow().output(px,py,' ');
				    	if(m.rkey==KeyEvent.VK_LEFT && maze[py][px - 1] != '#' 
				        		&& maze[py][px - 1] != '=' && maze[py][px - 1] != '*' && maze[py][px - 1] != 'C') {		//Looking for the walls and other things.
				    		maze[py][px] = ' ';
				    		px--;
				        	findValue(px, py);
				        	maze[py][px] = 'P';		//Updating the player in array.
				        }
				        if(m.rkey==KeyEvent.VK_RIGHT && maze[py][px + 1] != '#'
				        		&& maze[py][px + 1] != '=' && maze[py][px + 1] != '*' && maze[py][px + 1] != 'C') {
				        	maze[py][px] = ' ';
				        	px++;
				        	findValue(px, py);
				        	maze[py][px] = 'P';
				        }
				        if(m.rkey==KeyEvent.VK_UP && maze[py - 1][px] != '#'
				        		&& maze[py - 1][px] != '=' && maze[py - 1][px] != '*' && maze[py - 1][px] != 'C') {
				        	maze[py][px] = ' ';
				        	py--;
				        	findValue(px, py);
				        	maze[py][px] = 'P';
				        }
			            if(m.rkey==KeyEvent.VK_DOWN && maze[py + 1][px] != '#'
			            		&& maze[py + 1][px] != '=' && maze[py + 1][px] != '*' && maze[py + 1][px] != 'C') {
			            	maze[py][px] = ' ';
			            	py++;
			            	findValue(px, py);
			            	maze[py][px] = 'P';
			            }
			            if(m.rkey ==KeyEvent.VK_W && !(player.getBackpack().isEmpty())) {
			            	if(maze[py - 1][px] != '#') {       			
			            		chr = (char) player.getBackpack().pop();
			            		if (chr == '=' || chr == '*') {
									maze[py - 1][px] = chr;	
									//to create a device
									dCoordinate = new Coordinate(px, py - 1);
									device[deviceArrayCounter] = new Device(dCoordinate,chr,300,25);
									objectArray[py - 1][px] = device[deviceArrayCounter];
									deviceArrayCounter++;
								 	cn.getTextWindow().output(px,py-1,chr, redColor); // if selected cell is empty prints following char
								}
			            	}
			            }
			            if(m.rkey ==KeyEvent.VK_A && !(player.getBackpack().isEmpty())) {
			            	if(maze[py][px - 1] != '#') {       			
			            		chr = (char) player.getBackpack().pop();
			            		if (chr == '=' || chr == '*') {
									maze[py][px - 1] = chr;	
									//to create a device
									dCoordinate = new Coordinate(px - 1, py);
									device[deviceArrayCounter] = new Device(dCoordinate,chr,300,25);
									objectArray[py][px - 1] = device[deviceArrayCounter];
									deviceArrayCounter++;
								 	cn.getTextWindow().output(px-1,py,chr, redColor);	
								}
			            	}
			            }
			            if(m.rkey ==KeyEvent.VK_S && !(player.getBackpack().isEmpty())) {
			            	if(maze[py + 1][px] != '#') {       			
			            		chr = (char) player.getBackpack().pop();
			            		if(chr == '=' || chr == '*') {
			            			maze[py + 1][px] = chr;	
			            			//to create a device
			            			dCoordinate = new Coordinate(px, py + 1);
									device[deviceArrayCounter] = new Device(dCoordinate,chr,300,25);
									objectArray[py + 1][px] = device[deviceArrayCounter];
									deviceArrayCounter++;
								 	cn.getTextWindow().output(px,py+1,chr, redColor);
			            		}
			            	}
			            }
			            if(m.rkey ==KeyEvent.VK_D && !(player.getBackpack().isEmpty())) {
			            	if(maze[py][px + 1] != '#') {        			
			            		chr = (char) player.getBackpack().pop(); 
			            		if(chr == '=' || chr == '*') {
			            			maze[py][px + 1] = chr;	
			            			//to create a device
			            			dCoordinate = new Coordinate(px + 1, py);
									device[deviceArrayCounter] = new Device(dCoordinate,chr,300,25);
									objectArray[py][px + 1] = device[deviceArrayCounter];
									deviceArrayCounter++;
								 	cn.getTextWindow().output(px+1,py,chr, redColor);	
			            		}	
			            	}
			            }
			            //to finish game
			            if(m.rkey==KeyEvent.VK_P) {
			            	break;
			            }
			            cn.getTextWindow().output(px,py,'P', yellowColor);
			            m.keypr=0;	            		            
				    	}
		    		
		    		playerSpeedbyTimer += playerSpeedbyTimerAdder;
				}
		    	
		    	// moving numbers and computers timer
		    	if (time> movingNumberAndComputerTimer) {
		    		//1265 is max array size in the code
		    		for (int i = 0; i < 1265; i++) {
		    			// if the number is not null and is it moving
		    			int stuckCounter = 0;
		    			// to control numbers
						if(number[i] != null && number[i].isMovingNumber()) {						
							while (true) {					
								stuckCounter++;
								// 1: right | 2: left | 3: down | 4: up 
								int randomWay = randomNumber(1, 4);
								// if randomWay is right and right cell is empty
								if (randomWay == 1 && maze[number[i].getNumberCoordinate().getY()]
													      [number[i].getNumberCoordinate().getX() + 1] == ' ') {
									// updating coordinate								
									nCoordinate = new Coordinate(number[i].getNumberCoordinate().getX() + 1,
											                     number[i].getNumberCoordinate().getY());
									// updating object
									number[i] = new Number(nCoordinate, number[i].getnValue(),number[i].getnScore(),true);
									// updating object array
									objectArray[nCoordinate.getY()][nCoordinate.getX()] = number[i]; 
									// printing
									cn.getTextWindow().output(nCoordinate.getX(),nCoordinate.getY(),(char) (number[i].getnValue()+ '0'), greenColor);
									//updating maze
									maze[nCoordinate.getY()][nCoordinate.getX()] = (char) (number[i].getnValue()+ '0');
									// emptying the old maze cell
									maze[nCoordinate.getY()][nCoordinate.getX() - 1] = ' ';
									//emptying the old object cell
									objectArray[nCoordinate.getY()][nCoordinate.getX()  - 1] = null;
									// clearing the old cell
									cn.getTextWindow().output(nCoordinate.getX() - 1,nCoordinate.getY(),' ');
									break;
					               }
								else if (randomWay == 2 && maze[number[i].getNumberCoordinate().getY()]
															   [number[i].getNumberCoordinate().getX() - 1] == ' ') {
									
									nCoordinate = new Coordinate(number[i].getNumberCoordinate().getX() - 1,
											                     number[i].getNumberCoordinate().getY());
									number[i] = new Number(nCoordinate, number[i].getnValue(),number[i].getnScore(),true);
									objectArray[nCoordinate.getY()][nCoordinate.getX()] = number[i]; 
									cn.getTextWindow().output(nCoordinate.getX(),nCoordinate.getY(),(char) (number[i].getnValue()+ '0'), greenColor);
									maze[nCoordinate.getY()][nCoordinate.getX()] = (char) (number[i].getnValue()+ '0');
									
									maze[nCoordinate.getY()][nCoordinate.getX() + 1] = ' ';
									objectArray[nCoordinate.getY()][nCoordinate.getX() + 1] = null;
									cn.getTextWindow().output(nCoordinate.getX() + 1,nCoordinate.getY(),' ');
									break;
									}
								
								else if (randomWay == 3 && maze[number[i].getNumberCoordinate().getY() + 1]
															   [number[i].getNumberCoordinate().getX()] == ' ') {
											
									nCoordinate = new Coordinate(number[i].getNumberCoordinate().getX(),
											                     number[i].getNumberCoordinate().getY() + 1);
									number[i] = new Number(nCoordinate, number[i].getnValue(),number[i].getnScore(),true);
									objectArray[nCoordinate.getY()][nCoordinate.getX()] = number[i]; 
									cn.getTextWindow().output(nCoordinate.getX(),nCoordinate.getY(),(char) (number[i].getnValue()+ '0'), greenColor);
									maze[nCoordinate.getY()][nCoordinate.getX()] = (char) (number[i].getnValue()+ '0');
									
									maze[nCoordinate.getY() -1][nCoordinate.getX()] = ' ';
									objectArray[nCoordinate.getY() -1][nCoordinate.getX()] = null;
									cn.getTextWindow().output(nCoordinate.getX(),nCoordinate.getY() - 1,' ');
									break;
									}
								else if (randomWay == 4 && maze[number[i].getNumberCoordinate().getY() -1]
															   [number[i].getNumberCoordinate().getX()] == ' ') {
																		
									nCoordinate = new Coordinate(number[i].getNumberCoordinate().getX(),
											                     number[i].getNumberCoordinate().getY() - 1);
									number[i] = new Number(nCoordinate, number[i].getnValue(),number[i].getnScore(),true);
									objectArray[nCoordinate.getY()][nCoordinate.getX()] = number[i]; 
									cn.getTextWindow().output(nCoordinate.getX(),nCoordinate.getY(),(char) (number[i].getnValue()+ '0'), greenColor);
									maze[nCoordinate.getY()][nCoordinate.getX()] = (char) (number[i].getnValue()+ '0');
									
									maze[nCoordinate.getY() + 1][nCoordinate.getX()] = ' ';
									objectArray[nCoordinate.getY() + 1][nCoordinate.getX()] = null;
									cn.getTextWindow().output(nCoordinate.getX(),nCoordinate.getY() + 1,' ');
									break;
									}
									// if the element stuck
								  if (stuckCounter == 1000) {
									break;
								  } 
								}					        	
							}
							stuckCounter = 0;
							// to control computers
							if(computer[i] != null && computer[i].isMoving()) {
								computer[i].setMaze(maze);
								pathFinder(computer[i]);
							}	
						}
	    			movingNumberAndComputerTimer+=500;
				}
		    	// device timer
		    	if (time> deviceTimer) {
		    		//1265 is max array size in the code
		    		for (int i = 0; i < 1265; i++) {
		    			if(device[i] != null) {
							while(true) {
								int deviceX = device[i].getdCoordinate().getX();
								int deviceY = device[i].getdCoordinate().getY();
								if(device[i] != null && device[i].getDtype() == '=') {
									if(device[i].getActivationTime() != 0) {
										//to decrease activation time
										device[i].setActivationTime(device[i].getActivationTime() - 1); 
										//to check 8 neighbor squares
										if(maze[deviceY - 1][deviceX - 1] != ' ' && maze[deviceY - 1][deviceX] != 'P') {
											//to activate Trap device
											activateTrapDevice(deviceX - 1, deviceY - 1, false);												
										}
										if(maze[deviceY - 1][deviceX] != ' ' && maze[deviceY - 1][deviceX] != 'P') {
											activateTrapDevice(deviceX, deviceY - 1, false);
										}
										if(maze[deviceY + 1][deviceX] != ' ' && maze[deviceY + 1][deviceX] != 'P') {
											activateTrapDevice(deviceX, deviceY + 1, false);
										}
										if(maze[deviceY][deviceX - 1] != ' ' && maze[deviceY][deviceX - 1] != 'P') {
											activateTrapDevice(deviceX - 1, deviceY, false);
										}
										if(maze[deviceY + 1][deviceX - 1] != ' ' && maze[deviceY + 1][deviceX - 1] != 'P') {
											activateTrapDevice(deviceX - 1, deviceY + 1, false);
										}
										if(maze[deviceY + 1][deviceX + 1] != ' ' && maze[deviceY + 1][deviceX + 1] != 'P') {
											activateTrapDevice(deviceX + 1, deviceY + 1, false);
										}
										if(maze[deviceY - 1][deviceX + 1] != ' ' && maze[deviceY - 1][deviceX + 1] != 'P') {
											activateTrapDevice(deviceX + 1, deviceY - 1, false);
										}
										if(maze[deviceY][deviceX + 1] != ' ' && maze[deviceY][deviceX + 1] != 'P') {
											activateTrapDevice(deviceX + 1, deviceY, false);
										}
									}
									else { //if activation time equals 0
										if(maze[deviceY - 1][deviceX - 1] != ' ' && maze[deviceY - 1][deviceX] != 'P') {
											//to deactivate device
											activateTrapDevice(deviceX - 1, deviceY - 1, true);
										}
										if(maze[deviceY - 1][deviceX] != ' ' && maze[deviceY - 1][deviceX] != 'P') {
											activateTrapDevice(deviceX, deviceY - 1, true);
										}
										if(maze[deviceY + 1][deviceX] != ' ' && maze[deviceY + 1][deviceX] != 'P') {
											activateTrapDevice(deviceX, deviceY + 1, true);
										}
										if(maze[deviceY][deviceX - 1] != ' ' && maze[deviceY][deviceX - 1] != 'P') {
											activateTrapDevice(deviceX - 1, deviceY, true);
										}
										if(maze[deviceY + 1][deviceX - 1] != ' ' && maze[deviceY + 1][deviceX - 1] != 'P') {
											activateTrapDevice(deviceX - 1, deviceY + 1, true);
										}
										if(maze[deviceY + 1][deviceX + 1] != ' ' && maze[deviceY + 1][deviceX + 1] != 'P') {
											activateTrapDevice(deviceX + 1, deviceY + 1, true);
										}
										if(maze[deviceY - 1][deviceX + 1] != ' ' && maze[deviceY - 1][deviceX + 1] != 'P') {
											activateTrapDevice(deviceX + 1, deviceY - 1, true);
										}
										if(maze[deviceY][deviceX + 1] != ' ' && maze[deviceY][deviceX + 1] != 'P') {
											activateTrapDevice(deviceX + 1, deviceY, true);
										}
										//for deleting device
										device[i] = null;
										maze[deviceY][deviceX] = ' ';
										objectArray[deviceY][deviceX] = null;
										cn.getTextWindow().output(deviceX,deviceY,' ');	
									}
									
								}
								if(device[i] != null && device[i].getDtype() == '*') {
									if(device[i].getActivationTime() != 0) {
										//to decrease activation time
										device[i].setActivationTime(device[i].getActivationTime() - 1);
										//to check 8 neighbor squares
										if(maze[deviceY - 1][deviceX - 1] != ' ' && maze[deviceY - 1][deviceX] != 'P' 
												&& maze[deviceY - 1][deviceX] != '#') {
											// to activate Warp device
											activateWarpDevice(deviceX - 1, deviceY - 1);
										}
										if(maze[deviceY - 1][deviceX] != ' ' && maze[deviceY - 1][deviceX] != 'P'
												&& maze[deviceY - 1][deviceX] != '#') {
											activateWarpDevice(deviceX, deviceY - 1);
										}
										if(maze[deviceY + 1][deviceX] != ' ' && maze[deviceY + 1][deviceX] != 'P'
												&& maze[deviceY - 1][deviceX] != '#') {
											activateWarpDevice(deviceX, deviceY + 1);
										}
										if(maze[deviceY][deviceX - 1] != ' ' && maze[deviceY][deviceX - 1] != 'P'
												&& maze[deviceY - 1][deviceX] != '#') {
											activateWarpDevice(deviceX - 1, deviceY);
										}
										if(maze[deviceY + 1][deviceX - 1] != ' ' && maze[deviceY + 1][deviceX - 1] != 'P'
												&& maze[deviceY - 1][deviceX] != '#') {
											activateWarpDevice(deviceX - 1, deviceY + 1);
										}
										if(maze[deviceY + 1][deviceX + 1] != ' ' && maze[deviceY + 1][deviceX + 1] != 'P'
												&& maze[deviceY - 1][deviceX] != '#') {
											activateWarpDevice(deviceX + 1, deviceY + 1);
										}
										if(maze[deviceY - 1][deviceX + 1] != ' ' && maze[deviceY - 1][deviceX + 1] != 'P'
												&& maze[deviceY - 1][deviceX] != '#') {
											activateWarpDevice(deviceX + 1, deviceY - 1);
										}
										if(maze[deviceY][deviceX + 1] != ' ' && maze[deviceY][deviceX + 1] != 'P'
												&& maze[deviceY - 1][deviceX] != '#') {
											activateWarpDevice(deviceX + 1, deviceY);
										}
									}
									else { //if activation time equals 0
										//for deleting device
										device[i] = null;
										maze[deviceY][deviceX] = ' ';
										objectArray[deviceY][deviceX] = null;
										cn.getTextWindow().output(deviceX,deviceY,' ');																																																																			
									}										
								}									
								break;
							}																																
						}	
		    		}
		    		deviceTimer+=1000;
		    	}
				// necessary informations
		    	cn.getTextWindow().setCursorPosition(60, 17); System.out.println("P.Score: " + player.getpScore());		    	
		    	cn.getTextWindow().setCursorPosition(60, 18); System.out.println("P.Life: " + player.getpLife());		    	
		    	cn.getTextWindow().setCursorPosition(60, 20); System.out.println("C.Score: " + computerScore);
		    	//energy timer
		    	if (time > energyTimer) {
		    		if (player.getpEnergy() > 0) {	
			    		player.setpEnergy(player.getpEnergy() - 1);		    		
			    		cn.getTextWindow().setCursorPosition(60, 16); System.out.println("                           ");			    		
			    		cn.getTextWindow().setCursorPosition(60, 16); System.out.println("Energy Duration: " + player.getpEnergy());		    				    				    		
					}
		    		energyTimer += 1000;
				}
		    	 if (player.getpLife() == 0) {
		 			break;
		   }
		}
	    //to play related sound
	    try {
			File wavFile = new File("endGame.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(wavFile));
			clip.start();
		} catch (Exception e) {
			System.out.println(e);
		}
	    //end game screen
	    consoleClear();
		int finalScore = player.getpScore() - computerScore;
		if(finalScore < 0) {
			finalScore = 0;
		}
		cn.getTextWindow().setCursorPosition(25, 8);
		cn.getTextWindow().output("   _________    __  _________   ____ _    ____________ ", yellowColor);
		cn.getTextWindow().setCursorPosition(25, 9);
		cn.getTextWindow().output("  / ____/   |  /  |/  / ____/  / __ \\ |  / / ____/ __ \\", yellowColor);
		cn.getTextWindow().setCursorPosition(25, 10);
		cn.getTextWindow().output(" / / __/ /| | / /|_/ / __/    / / / / | / / __/ / /_/ /", yellowColor);
		cn.getTextWindow().setCursorPosition(25, 11);
		cn.getTextWindow().output("/ /_/ / ___ |/ /  / / /___   / /_/ /| |/ / /___/ _, _/ ", yellowColor);
		cn.getTextWindow().setCursorPosition(25, 12);
		cn.getTextWindow().output("\\____/_/  |_/_/  /_/_____/   \\____/ |___/_____/_/ |_|  ", yellowColor);
		cn.getTextWindow().setCursorPosition(45, 15);
		cn.getTextWindow().output("SCORE: " + finalScore , yellowColor);
		Thread.sleep(5000);
		consoleClear();
		
	}
	// path finder
	void pathFinder(Computer computer) {
		// to duplicate current maze
		char[][] computerMaze = new char[23][55];
		for (int i = 0; i < computer.getMaze().length; i++) {
			for (int j = 0; j < computer.getMaze()[i].length; j++) {
				computerMaze[i][j] = computer.getMaze()[i][j];
			}
		}
		boolean founded = false;
		char theWayTheComputerWillGo = ' ';
		String path = "";
		int stuckCounter = 0;
		while (true) {
			// to get current position of computer
			int cX = computer.getComputerCoordinate().getX();
			int cY = computer.getComputerCoordinate().getY();
			computerMaze[cY][cX] = 'X';	//remark computers coordinates
			// if pathFinder queue is not empty
			if (!pathFinder.isEmpty()) {
				String nextStep = (String) pathFinder.dequeue(); // dequeue queue
				path = nextStep; //store old path by path
				if (nextStep != "") {
					// follow path and remark coordinate
					for (int i = 0; i < nextStep.length(); i++) {
						
						if (nextStep.charAt(i) == 'R') {
							cX++;
						}
						else if (nextStep.charAt(i) == 'U') {
							cY--;
						}
						else if (nextStep.charAt(i) == 'L') {
							cX--;
						}
						else if (nextStep.charAt(i) == 'D') {
							cY++;
						}						
					}
				}			
			}
			
			// if cX and cY on the number
			if (computerMaze[cY][cX] == '1' || computerMaze[cY][cX] == '2' ||
				computerMaze[cY][cX] == '3' || computerMaze[cY][cX] == '4' ||
				computerMaze[cY][cX] == '5' || computerMaze[cY][cX] == 'P') {
				theWayTheComputerWillGo = computerMaze[cY][cX];
				founded = true;
			}
			
			// to check the last position. Control order: right up left down
			// right
			if ((computerMaze[cY][cX + 1] == '1' || computerMaze[cY][cX + 1] == '2' ||
				computerMaze[cY][cX + 1] == '3' || computerMaze[cY][cX + 1] == '4' ||
				computerMaze[cY][cX + 1] == '5' || computerMaze[cY][cX + 1] == ' ' ||
				computerMaze[cY][cX + 1] == 'P') && !founded){
				//if the cell is blank 
				if (computerMaze[cY][cX + 1] == ' ') {
					pathFinder.enqueue(path + "R"); // add R (right) to path and enqueue it
					computerMaze[cY][cX + 1] = 'X'; // remark the maze
				}
				// if cell is not blank
				else {					
					founded = true;	
					// if the element is not neighbor
					if (path != "") {
						theWayTheComputerWillGo = path.toCharArray()[0]; // move the paths first way
					}
					// if the element is neighbor
					else {
						theWayTheComputerWillGo = 'R';
					}				
				}
			}
			// up
			if ((computerMaze[cY - 1][cX] == '1' || computerMaze[cY - 1][cX] == '2' ||
				computerMaze[cY - 1][cX] == '3' || computerMaze[cY - 1][cX] == '4' ||
				computerMaze[cY - 1][cX] == '5' || computerMaze[cY - 1][cX] == ' ' ||
				computerMaze[cY - 1][cX] == 'P')&& !founded) {
					//if the cell is blank 
					if (computerMaze[cY - 1][cX] == ' ') {
						pathFinder.enqueue(path + "U"); // add U (up) to path and enqueue it
						computerMaze[cY - 1][cX] = 'X'; // remark the maze
					}
					else {
						founded = true;
						// if the element is not neighbor
						if (path != "") {
							theWayTheComputerWillGo = path.toCharArray()[0];
						}
						// if the element is neighbor
						else {
							theWayTheComputerWillGo = 'U';
						}	
					}
				}
			// left
			if ((computerMaze[cY][cX - 1] == '1' || computerMaze[cY][cX - 1] == '2' ||
				computerMaze[cY][cX - 1] == '3' || computerMaze[cY][cX - 1] == '4' ||
				computerMaze[cY][cX - 1] == '5' || computerMaze[cY][cX - 1] == ' ' ||
				computerMaze[cY][cX - 1] == 'P' )&& !founded) {
				//if the cell is blank 
				if (computerMaze[cY][cX - 1] == ' ') {
					pathFinder.enqueue(path + "L"); // add L (left) to path and enqueue it
					computerMaze[cY][cX - 1] = 'X'; // remark the maze
				}
				else {					
					founded = true;
					// if the element is not neighbor
					if (path != "") {
						theWayTheComputerWillGo = path.toCharArray()[0];
					}
					// if the element is neighbor
					else {
						theWayTheComputerWillGo = 'L';
					}
				}
			}
			// down
			if ((computerMaze[cY + 1][cX] == '1' || computerMaze[cY + 1][cX] == '2' ||
				computerMaze[cY + 1][cX] == '3' || computerMaze[cY + 1][cX] == '4' ||
				computerMaze[cY + 1][cX] == '5' || computerMaze[cY + 1][cX] == ' ' ||
				computerMaze[cY + 1][cX] == 'P')&& !founded){
					//if the cell is blank 
					if (computerMaze[cY + 1][cX] == ' ') {
						pathFinder.enqueue(path + "D"); // add D (down) to path and enqueue it
						computerMaze[cY + 1][cX] = 'X'; // remark the maze
					}
					else {						
						founded = true;
						// if the element is not neighbor
						if (path != "") {
							theWayTheComputerWillGo = path.toCharArray()[0];
						}
						// if the element is neighbor
						else {
							theWayTheComputerWillGo = 'D';
						}
					}
				}
			// if the element founded
			if (founded) {
				// if the element is on the right side
				if (theWayTheComputerWillGo == 'R') {
					int tempX = computer.getComputerCoordinate().getX() + 1;
					int tempY = computer.getComputerCoordinate().getY();
					if (computerMaze[tempY][tempX] == '1' || computerMaze[tempY][tempX] == '2'||
						computerMaze[tempY][tempX] == '3' ||computerMaze[tempY][tempX] == '4' ||
						computerMaze[tempY][tempX] == '5' || computerMaze[tempY][tempX] == 'P')	{
						
						maze[tempY][tempX] = ' '; // emptying the maze cell
						objectArray[tempY][tempX] = null; // emptying the objectArray cell
						for (int k = 0; k < 1265; k++) {
							if(number[k] != null &&
									number[k].getNumberCoordinate().getX() == tempX &&
									number[k].getNumberCoordinate().getY() == tempY) {
								computerScore += (number[k].getnScore())*2;
								number[k] = null; //converting number to null
								break;
							}
						}
						if (computerMaze[tempY][tempX] == 'P') {
							player.setpLife(player.getpLife() -1); // to decrease life if computer beats player
							//to play related sound
							try {
								File wavFile = new File("dead.wav");
								Clip clip = AudioSystem.getClip();
								clip.open(AudioSystem.getAudioInputStream(wavFile));
								clip.start();
							} catch (Exception e) {
								System.out.println(e);
							}
							// pop 2 elements from backpack
							for (int i = 0; i < 2; i++) {
								if (!player.getBackpack().isEmpty()) {
									char theElementPoppedByC = (char) player.getBackpack().pop();
									addPoppedElementsScoreToC(theElementPoppedByC);
								}
							}
						}						
					}
					// pop 2 elements from backpack
					if (computerMaze[tempY][tempX + 1] == 'P'){
						for (int i = 0; i < 2; i++) {
							if (!player.getBackpack().isEmpty()) {
								char theElementPoppedByC = (char) player.getBackpack().pop();
								addPoppedElementsScoreToC(theElementPoppedByC);
							}
						}
					}
					
					// updating coordinate								
					cCoordinate = new Coordinate(computer.getComputerCoordinate().getX() + 1,
							                     computer.getComputerCoordinate().getY());
					computer.setComputerCoordinate(cCoordinate);
					// updating object array
					objectArray[cCoordinate.getY()][cCoordinate.getX()] = computer; 
					// printing
					cn.getTextWindow().output(cCoordinate.getX(),cCoordinate.getY(),(char) ('C'), redColor);
					//updating maze
					maze[cCoordinate.getY()][cCoordinate.getX()] = (char) ('C');
					// emptying the old maze cell
					maze[cCoordinate.getY()][cCoordinate.getX() - 1] = ' ';
					//emptying the old object cell
					objectArray[cCoordinate.getY()][cCoordinate.getX() - 1] = null;
					// clearing the old cell
					cn.getTextWindow().output(cCoordinate.getX() - 1,cCoordinate.getY(),' ');
					computer.setMaze(maze);
					
					break;
				}
				// if the element is above
				else if (theWayTheComputerWillGo == 'U') {
					
					int tempX = computer.getComputerCoordinate().getX();
					int tempY = computer.getComputerCoordinate().getY() - 1;
					if (computerMaze[tempY][tempX] == '1' || computerMaze[tempY][tempX] == '2'||
						computerMaze[tempY][tempX] == '3' ||computerMaze[tempY][tempX] == '4' ||
						computerMaze[tempY][tempX] == '5' || computerMaze[tempY][tempX] == 'P')	{
						
						maze[tempY][tempX] = ' '; // emptying the maze cell
						objectArray[tempY][tempX] = null; // emptying the objectArray cell
						for (int k = 0; k < 1265; k++) {
							if(number[k] != null &&
									number[k].getNumberCoordinate().getX() == tempX &&
									number[k].getNumberCoordinate().getY() == tempY) {
								computerScore += (number[k].getnScore())*2;
								number[k] = null; //converting number to null
								break;
							}
						}
						if (computerMaze[tempY][tempX] == 'P') {
							player.setpLife(player.getpLife() -1);
							//to play related sound
							try {
								File wavFile = new File("dead.wav");
								Clip clip = AudioSystem.getClip();
								clip.open(AudioSystem.getAudioInputStream(wavFile));
								clip.start();
							} catch (Exception e) {
								System.out.println(e);
							}
							for (int i = 0; i < 2; i++) {
								if (!player.getBackpack().isEmpty()) {
									char theElementPoppedByC = (char) player.getBackpack().pop();
									addPoppedElementsScoreToC(theElementPoppedByC);
								}
							}
						}
					}
					
					if (computerMaze[tempY - 1][tempX] == 'P'){
						for (int i = 0; i < 2; i++) {
							if (!player.getBackpack().isEmpty()) {
								char theElementPoppedByC = (char) player.getBackpack().pop();
								addPoppedElementsScoreToC(theElementPoppedByC);
							}
						}
					}
					
					// updating coordinate								
					cCoordinate = new Coordinate(computer.getComputerCoordinate().getX(),
							                     computer.getComputerCoordinate().getY() - 1);					
					computer.setComputerCoordinate(cCoordinate);
					// updating object array
					objectArray[cCoordinate.getY()][cCoordinate.getX()] = computer; 
					// printing
					cn.getTextWindow().output(cCoordinate.getX(),cCoordinate.getY(),(char) ('C'), redColor);
					//updating maze
					maze[cCoordinate.getY()][cCoordinate.getX()] = (char) ('C');
					// emptying the old maze cell
					maze[cCoordinate.getY() + 1][cCoordinate.getX()] = ' ';
					//emptying the old object cell
					objectArray[cCoordinate.getY() + 1][cCoordinate.getX()] = null;
					// clearing the old cell
					cn.getTextWindow().output(cCoordinate.getX(),cCoordinate.getY() + 1,' ');
					computer.setMaze(maze);
					
					
					break;
				}
				// if the element is on the left side
				else if (theWayTheComputerWillGo == 'L') {

					int tempX = computer.getComputerCoordinate().getX() - 1;
					int tempY = computer.getComputerCoordinate().getY();
					if (computerMaze[tempY][tempX] == '1' || computerMaze[tempY][tempX] == '2'||
						computerMaze[tempY][tempX] == '3' ||computerMaze[tempY][tempX] == '4' ||
						computerMaze[tempY][tempX] == '5' || computerMaze[tempY][tempX] == 'P')	{
						
						maze[tempY][tempX] = ' '; // emptying the maze cell
						objectArray[tempY][tempX] = null; // emptying the objectArray cell
						for (int k = 0; k < 1265; k++) {
							if(number[k] != null &&
									number[k].getNumberCoordinate().getX() == tempX &&
									number[k].getNumberCoordinate().getY() == tempY) {
								computerScore += (number[k].getnScore())*2;
								number[k] = null; //converting number to null
								break;
							}
						}
						if (computerMaze[tempY][tempX] == 'P') {
							player.setpLife(player.getpLife() -1);
							//to play related sound
							try {
								File wavFile = new File("dead.wav");
								Clip clip = AudioSystem.getClip();
								clip.open(AudioSystem.getAudioInputStream(wavFile));
								clip.start();
							} catch (Exception e) {
								System.out.println(e);
							}
							for (int i = 0; i < 2; i++) {
								if (!player.getBackpack().isEmpty()) {
									char theElementPoppedByC = (char) player.getBackpack().pop();
									addPoppedElementsScoreToC(theElementPoppedByC);
								}
							}
						}
					}
					
					if (computerMaze[tempY][tempX - 1] == 'P'){
						for (int i = 0; i < 2; i++) {
							if (!player.getBackpack().isEmpty()) {
							char theElementPoppedByC = (char) player.getBackpack().pop();
							addPoppedElementsScoreToC(theElementPoppedByC);
							}
						}
					}
					
					// updating coordinate								
					cCoordinate = new Coordinate(computer.getComputerCoordinate().getX() - 1,
							                     computer.getComputerCoordinate().getY());
					
					computer.setComputerCoordinate(cCoordinate);
					// updating object array
					objectArray[cCoordinate.getY()][cCoordinate.getX()] = computer; 
					// printing
					cn.getTextWindow().output(cCoordinate.getX(),cCoordinate.getY(),('C'), redColor);
					//updating maze
					maze[cCoordinate.getY()][cCoordinate.getX()] = ('C');
					// emptying the old maze cell
					maze[cCoordinate.getY()][cCoordinate.getX() + 1] = ' ';
					//emptying the old object cell
					objectArray[cCoordinate.getY()][cCoordinate.getX() + 1] = null;
					// clearing the old cell
					cn.getTextWindow().output(cCoordinate.getX() + 1,cCoordinate.getY(),' ');
					computer.setMaze(maze);					
					break;				
				}
				// if the element is below
				else if (theWayTheComputerWillGo == 'D') {					
					int tempX = computer.getComputerCoordinate().getX();
					int tempY = computer.getComputerCoordinate().getY() + 1;
					if (computerMaze[tempY][tempX] == '1' || computerMaze[tempY][tempX] == '2'||
						computerMaze[tempY][tempX] == '3' ||computerMaze[tempY][tempX] == '4' ||
						computerMaze[tempY][tempX] == '5' || computerMaze[tempY][tempX] == 'P')	{
						
						maze[tempY][tempX] = ' '; // emptying the maze cell
						objectArray[tempY][tempX] = null; // emptying the objectArray cell
						for (int k = 0; k < 1265; k++) {
							if(number[k] != null &&
									number[k].getNumberCoordinate().getX() == tempX &&
									number[k].getNumberCoordinate().getY() == tempY) {
								computerScore += (number[k].getnScore())*2;
								number[k] = null; //converting number to null
								break;
							}
						}						
						if (computerMaze[tempY][tempX] == 'P') {
							player.setpLife(player.getpLife() -1);
							try {
								File wavFile = new File("dead.wav");
								Clip clip = AudioSystem.getClip();
								clip.open(AudioSystem.getAudioInputStream(wavFile));
								clip.start();
							} catch (Exception e) {
								System.out.println(e);
							}
							for (int i = 0; i < 2; i++) {
								if (!player.getBackpack().isEmpty()) {
									char theElementPoppedByC = (char) player.getBackpack().pop();
									addPoppedElementsScoreToC(theElementPoppedByC);
								}
							}
						}	
					}					
					if (computerMaze[tempY + 1][tempX] == 'P'){
						for (int i = 0; i < 2; i++) {
							if (!player.getBackpack().isEmpty()) {
								char theElementPoppedByC = (char) player.getBackpack().pop();
								addPoppedElementsScoreToC(theElementPoppedByC);
							}
						}
					}					
					// updating coordinate								
					cCoordinate = new Coordinate(computer.getComputerCoordinate().getX(),
							                     computer.getComputerCoordinate().getY() + 1);
					
					computer.setComputerCoordinate(cCoordinate);
					// updating object array
					objectArray[cCoordinate.getY()][cCoordinate.getX()] = computer; 
					// printing
					cn.getTextWindow().output(cCoordinate.getX(),cCoordinate.getY(),(char) ('C'), redColor);
					//updating maze
					maze[cCoordinate.getY()][cCoordinate.getX()] = (char) ('C');
					// emptying the old maze cell
					maze[cCoordinate.getY() - 1][cCoordinate.getX()] = ' ';
					//emptying the old object cell
					objectArray[cCoordinate.getY()  - 1][cCoordinate.getX()] = null;
					// clearing the old cell
					cn.getTextWindow().output(cCoordinate.getX(),cCoordinate.getY() - 1,' ');
					computer.setMaze(maze);					
					break;
				}
			}
			stuckCounter++;
			if (stuckCounter > 1000) {
				break;
			}
		}
		// emptying path finder queue for next move
		while (!pathFinder.isEmpty()) {
			pathFinder.dequeue();		
		}
	}
	// if the computer is the player's neighbor or beats the player
	void addPoppedElementsScoreToC(char theElementPoppedByC) {
		if (theElementPoppedByC == '2') {
			computerScore += 10;
		}
		else if (theElementPoppedByC == '3') {
			computerScore += 30;
		}
		else if (theElementPoppedByC == '4') {
			computerScore += 100;
		}
		else if (theElementPoppedByC == '5') {
			computerScore += 300;
		}
		else if (theElementPoppedByC == '*') {
			computerScore += 300;
		}
		else if (theElementPoppedByC == '=') {
			computerScore += 300;
		}
	}
	
	void findValue(int px, int py) {  //finds the value we want in this coordinate or not
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if(maze[py][px] != ' ' && objectArray[py][px] != null 
						&& maze[py][px] != '=' && maze[py][px] != '*') {			
					backPack_Identical_Numbers((Number) objectArray[py][px]);  
					maze[py][px] = ' '; // emptying the maze cell
					objectArray[py][px] = null; // emptying the objectArray cell
					for (int k = 0; k < 1265; k++) {
						if(number[k] != null &&
								number[k].getNumberCoordinate().getX() == px &&
								number[k].getNumberCoordinate().getY() == py) {
							number[k] = null; //converting number to null
							break;
						}
					}
				}
			}
		}	
	}
	
	void backPack_Identical_Numbers(Number num) {
		int energy = player.getpEnergy(); // get player energy from player object
		int score = player.getpScore();  // get player score from player object
		//get number value
		char number = (char) (num.getnValue() + '0');
		// if top element in the stack device
		if((!(player.getBackpack().isEmpty())) && (!player.getBackpack().isFull()) 
				&& (player.getBackpack().peek().equals('=') || player.getBackpack().peek().equals('*'))) {
			if(number == '1') {
				player.setpScore(score + 1);
			}
			else {
				player.setpScore(score + num.getnScore());
				player.getBackpack().push(number);
			}
		}
		else if(player.getBackpack().isFull() && (player.getBackpack().peek().equals('=')   //if backpack isFull and top of backpack
				|| player.getBackpack().peek().equals('*'))) {                              //one of them, the computer's not getting into else control.			
		}
		else {  
			switch(number) {                                                                             
			// if the element is 1
			case '1': {
				player.setpScore(score + 1);
				break;
			}
			//if the element is 2 
			case '2': {
				player.setpScore(score + 5);
				//if the top element is 2
				if(!(player.getBackpack().isEmpty()) && 
						(number == (char)player.getBackpack().peek())) {
					player.setpEnergy(energy + 30);
					player.getBackpack().pop();
					//to play related sound
					try {
						File wavFile = new File("match.wav");
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(wavFile));
						clip.start();
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				// if the numbers are not matched
				else if(!(player.getBackpack().isEmpty())) {
					player.getBackpack().pop();
				}
				// if the stack is empty
				else if(!player.getBackpack().isFull()) {
					player.getBackpack().push(number);
				}
				break;
			}
			//if the element is 3
			case '3':{
				player.setpScore(score + 15);
				//if the top element is 3
				if(!(player.getBackpack().isEmpty()) && 
						(number == (char)player.getBackpack().peek())) {
					player.getBackpack().pop();
					player.getBackpack().push('=');
					try {
						File wavFile = new File("match.wav");
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(wavFile));
						clip.start();
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				// if the numbers are not matched
				else if(!player.getBackpack().isEmpty()) {
					player.getBackpack().pop();
				}
				// if the stack is empty
				else if(!player.getBackpack().isFull()) {
					player.getBackpack().push(number);
				}
				break;
			}
			//if the element is 4
			case '4':{
				player.setpScore(score + 50);
				//if the top element is 4
				if(!(player.getBackpack().isEmpty()) && 
						(number == (char)player.getBackpack().peek())) {
					player.setpEnergy(energy + 240);
					player.getBackpack().pop();
					//to play related sound
					try {
						File wavFile = new File("match.wav");
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(wavFile));
						clip.start();
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				// if the numbers are not matched
				else if(!(player.getBackpack().isEmpty())) {
					player.getBackpack().pop();
				}
				// if the stack is empty
				else if(!player.getBackpack().isFull()) {
					player.getBackpack().push(number);
				}
				break;
			}
			//if the element is 5
			case '5':{
				player.setpScore(score + 150);
				//if the top element is 5
				if(!(player.getBackpack().isEmpty()) && 
						(number == (char)player.getBackpack().peek())) {
					player.getBackpack().pop();
					player.getBackpack().push('*');  
					//to play related sound
					try {
						File wavFile = new File("match.wav");
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(wavFile));
						clip.start();
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				// if the numbers are not matched
				else if(!(player.getBackpack().isEmpty())) {
					player.getBackpack().pop();
				}
				// if the stack is empty
				else if(!player.getBackpack().isFull()) {
					player.getBackpack().push(number);
					}
					break;
				}
			}
		}	
	}
	
	void activateTrapDevice(int deviceX, int deviceY, boolean flag) {
		if(maze[deviceY][deviceX] == 'C') {
			//to find out where is the computer in the array
			for (int i = 0; i < computerArrayCounter; i++) {
				if(computer[i] == null) continue;
				if(computer[i].getComputerCoordinate().getX() == deviceX 
						&& computer[i].getComputerCoordinate().getY() == deviceY) {
					//to prevent computer's movement
					computer[i].setMoving(flag); 
					break;
				}
			}
		}
		else {
			//to find out where is the number in the array
			for (int i = 0; i < numberArrayCounter; i++) {
				if(number[i] == null) continue;
				if(number[i].getNumberCoordinate().getX() == deviceX 
						&& number[i].getNumberCoordinate().getY() == deviceY) {
					if(number[i].getnValue() == 4 || number[i].getnValue() == 5) {
						//to prevent moving number's movement
						number[i].setMovingNumber(flag);
					}
					break;
					
				}
			}
		}
	}
	
	void activateWarpDevice(int deviceX, int deviceY) {
		if(maze[deviceY][deviceX] == 'C') {
			//to find out where is the computer in the array
			for (int i = 0; i < computerArrayCounter; i++) {
				if(computer[i] == null) continue;
				if(computer[i].getComputerCoordinate().getX() == deviceX 
						&& computer[i].getComputerCoordinate().getY() == deviceY) {
					int playerscore = player.getpScore();
					//to increase player's score
					player.setpScore(playerscore + 300);
					//to delete computer
					computer[i] = null;
					maze[deviceY][deviceX] = ' ';
					objectArray[deviceY][deviceX] = null;
					cn.getTextWindow().output(deviceX,deviceY,' ');
				}
			}		
		}
		else {
			//to find out where is the number in the array
			for (int i = 0; i < numberArrayCounter; i++) {
				if(number[i] == null) continue;
				if(number[i].getNumberCoordinate().getX() == deviceX 
						&& number[i].getNumberCoordinate().getY() == deviceY) {
					int playerscore = player.getpScore();
					//to increase player's score
					player.setpScore(playerscore + number[i].getnScore());
					//to delete number
					number[i] = null;
					maze[deviceY][deviceX] = ' ';
					objectArray[deviceY][deviceX] = null;
					cn.getTextWindow().output(deviceX,deviceY,' ');	
				}
			}
		}
	}
	
	void consoleClear() {
		cn.getTextWindow().setCursorPosition(0, 0);
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 24; j++) {
				cn.getTextWindow().output(i,j, ' ');
			}
		}
	}
		
	void menu() throws Exception {
		cn.getTextWindow().setCursorPosition(0, 0);
		int chosen= 1;
		Movement move = new Movement();
		//to play related sound
		try {
			File wavFile = new File("soundtrack.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(wavFile));
			clip.start();
		} catch (Exception e) {
			System.out.println(e);
		}
		while (true) {			
			printMenu(chosen);			
			 if (move.keypr==1) {
				//to play related sound
				 try {
						File wavFile = new File("menu.wav");
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(wavFile));
						clip.start();
					} catch (Exception e) {
						System.out.println(e);
					}
				 move.keypr=0;
				 switch (move.rkey) {
				case KeyEvent.VK_UP:
					if (chosen == 1 )
						chosen =4;
					else
						chosen--;
					break;
				case KeyEvent.VK_DOWN:
					if (chosen == 4 )
						chosen =1;
					else
						chosen++;
					break;
				case KeyEvent.VK_ENTER:
					switch (chosen) {
					case 1:
						consoleClear();
						pCoordinate = new Coordinate(1, 1);
						nCoordinate = new Coordinate(0, 0);
						dCoordinate = new Coordinate(0, 0);
						cCoordinate = new Coordinate(0, 0);
						objectArray = new Object[23][55];
						backpack = new Stack(8); //creating backpack
						player = new Player(pCoordinate,0,5,50,backpack); // creating player
						maze = new char[23][55]; // creating maze char array to easy access	 
						number = new Number[1265];
						device = new Device[1265];
						computer = new Computer[1265];
						pathFinder = new CircularQueue(100); //temporary size
						elements = new CircularQueue(15); // elements those will place into maze
						numberArrayCounter = 0; deviceArrayCounter = 0; computerArrayCounter= 0; computerScore = 0;
						gamePlay();
						break;
					case 2:
						consoleClear();
						Movement movemt = new Movement();
						cn.getTextWindow().setCursorPosition(25, 0);
						cn.getTextWindow().output("  _  _  _____      __  _____ ___    ___ _      ___   __", greenColor);
						cn.getTextWindow().setCursorPosition(25, 1);
						cn.getTextWindow().output(" | || |/ _ \\ \\    / / |_   _/ _ \\  | _ \\ |    /_\\ \\ / /", greenColor);
						cn.getTextWindow().setCursorPosition(25, 2);
						cn.getTextWindow().output(" | __ | (_) \\ \\/\\/ /    | || (_) | |  _/ |__ / _ \\ V / ", greenColor);
						cn.getTextWindow().setCursorPosition(25, 3);
						cn.getTextWindow().output(" |_||_|\\___/ \\_/\\_/     |_| \\___/  |_| |____/_/ \\_\\_|  ", greenColor);
						cn.getTextWindow().setCursorPosition(4, 6);
				   	    cn.getTextWindow().output("> Player uses the cursor keys (↑↓→←) to move and uses WASD keys to drop a device.");
					    cn.getTextWindow().setCursorPosition(4, 8);
					    cn.getTextWindow().output("      Numbers                   Player Points      Computer Points");
					    cn.getTextWindow().setCursorPosition(4, 9);
					    cn.getTextWindow().output("     1 (Static)                         1                   2");
					    cn.getTextWindow().setCursorPosition(4, 10);
					    cn.getTextWindow().output("     2 (Static)                         5                  10");
					    cn.getTextWindow().setCursorPosition(4, 11);
					    cn.getTextWindow().output("     3 (Static)                        15                  30");
					    cn.getTextWindow().setCursorPosition(4, 12);
					    cn.getTextWindow().output("     4 (Moving)                        50                 100");
					    cn.getTextWindow().setCursorPosition(4, 13);
					    cn.getTextWindow().output("     5 (Moving)                       150                 300");
					    cn.getTextWindow().setCursorPosition(4, 14);
					    cn.getTextWindow().output("     = (Trap Device)                   -                  300");
					    cn.getTextWindow().setCursorPosition(4, 15);
					    cn.getTextWindow().output("     = (Warp Device)                   -                  300");
					    cn.getTextWindow().setCursorPosition(4, 17);
				        cn.getTextWindow().output("   Two Idenical Numbers            Bonus   ");
					    cn.getTextWindow().setCursorPosition(4, 18);
					    cn.getTextWindow().output("      2(Static)              Energy For 30 Second");
					    cn.getTextWindow().setCursorPosition(4, 19);
					    cn.getTextWindow().output("      3(Static)                 Trap Device");
					    cn.getTextWindow().setCursorPosition(4, 20);
					    cn.getTextWindow().output("      4(Static)              Energy For 240 Second");
					    cn.getTextWindow().setCursorPosition(4, 21);
					    cn.getTextWindow().output("      5(Static)                 Warp Device");
						cn.getTextWindow().setCursorPosition(93, 0);
				   	    cn.getTextWindow().output("← Back ");
						while(movemt.rkey!=KeyEvent.VK_BACK_SPACE) {	
						}
						consoleClear();
						break;
					case 3:
						consoleClear();
						Movement movem = new Movement();
						cn.getTextWindow().setCursorPosition(29, 0);
						cn.getTextWindow().output("  ____  _   _ _     _____ ____  ", greenColor);
						cn.getTextWindow().setCursorPosition(29, 1);
						cn.getTextWindow().output(" |  _ \\| | | | |   | ____/ ___| ", greenColor);
						cn.getTextWindow().setCursorPosition(29, 2);
						cn.getTextWindow().output(" | |_) | | | | |   |  _| \\___ \\ ", greenColor);
						cn.getTextWindow().setCursorPosition(29, 3);
						cn.getTextWindow().output(" |  _ <| |_| | |___| |___ ___) |", greenColor);
						cn.getTextWindow().setCursorPosition(29, 4);
						cn.getTextWindow().output(" |_| \\_\\\\___/|_____|_____|____/ ", greenColor);
						cn.getTextWindow().setCursorPosition(4, 7);
				   	    cn.getTextWindow().output("> Star Trek is a single-player game.");
				   	    cn.getTextWindow().setCursorPosition(4, 8);
					    cn.getTextWindow().output("> The aim of the game is to collect the highest score without dying. ");
					    cn.getTextWindow().setCursorPosition(4, 9);
					    cn.getTextWindow().output("> The player has energy , and slows down when the energy runs out. ");
					    cn.getTextWindow().setCursorPosition(4, 10);
					    cn.getTextWindow().output("> The player earns points by collecting numbers.");
					    cn.getTextWindow().setCursorPosition(4, 11);
					    cn.getTextWindow().output("> If two identical numbers are collected in the player's backpack,");
					    cn.getTextWindow().setCursorPosition(4, 12);
					    cn.getTextWindow().output("  the player gets a bonus like warp or trap devices.");
					    cn.getTextWindow().setCursorPosition(4, 13);
					    cn.getTextWindow().output("> Trap device (=) stops the numbers and robots in the neighboring squares for 25 seconds.");
					    cn.getTextWindow().setCursorPosition(4, 14);
					    cn.getTextWindow().output("> Warp device (*) warps the numbers and robots in the neighboring squares for 25 seconds.");
					    cn.getTextWindow().setCursorPosition(4, 15);
					    cn.getTextWindow().output("> If different numbers are collected, these numbers are deleted from the backpack.");
					    cn.getTextWindow().setCursorPosition(4, 16);
				        cn.getTextWindow().output("> The player has 5 lives and if the robots catches the player, 1 life is lost.");
					    cn.getTextWindow().setCursorPosition(4, 17);
					    cn.getTextWindow().output("> If the player loses all 5 lives, game ends.");
					    cn.getTextWindow().setCursorPosition(4, 18);
					    cn.getTextWindow().output("> Robots also can steal 2 elements of player’s backpack by becoming ");
					    cn.getTextWindow().setCursorPosition(4, 19);
					    cn.getTextWindow().output("  neighbor square of the player.");
					    cn.getTextWindow().setCursorPosition(4, 20);
					    cn.getTextWindow().output(" ---Have a good time!---", cyanColor);
						cn.getTextWindow().setCursorPosition(93, 0);
				   	    cn.getTextWindow().output("← Back ");
						while(movem.rkey!=KeyEvent.VK_BACK_SPACE) {	
						}
						consoleClear();
						break;
					case 4:
						System.exit(0);
						break;		
					}
					break;
				}
			}		 			 			 
		}
	}

	void printMenu(int index) {
		cn.setTextAttributes(reset);
		cn.getTextWindow().setCursorPosition(25, 5);
		cn.setTextAttributes(yellowColor);
		System.out.print(" \r\n"
				+"                       "+ "____________              ________           ______  \r\n"
				+"                       "+ "__  ___/_  /______ __________  __/______________  /__\r\n"
				+"                       "+ "_____ \\_  __/  __ `/_  ___/_  /  __  ___/  _ \\_  //_/\r\n"
				+"                       "+ "____/ // /_ / /_/ /_  /   _  /   _  /   /  __/  ,<   \r\n"
				+"                       "+ "/____/ \\__/ \\__,_/ /_/    /_/    /_/    \\___//_/|_|  \r\n"
				+"                       "+ "                                                     \r\n"
				+"                       "+ "");
		
		if (index == 1)
			cn.setTextAttributes(greenColor);
		else
			cn.setTextAttributes(reset);
		cn.getTextWindow().setCursorPosition(45, 13);
		System.out.println("START GAME");
		cn.getTextWindow().setCursorPosition(45, 14);
		
		if (index == 2)
			cn.setTextAttributes(greenColor);
		else
			cn.setTextAttributes(reset);
		System.out.println("HOW TO PLAY");
		cn.getTextWindow().setCursorPosition(45, 15);
		
		if (index == 3)
			cn.setTextAttributes(greenColor);
		else
			cn.setTextAttributes(reset);
		System.out.println("RULES");
		cn.getTextWindow().setCursorPosition(45, 16);
		
		if (index == 4) 
			cn.setTextAttributes(greenColor);
		else
			cn.setTextAttributes(reset);
		System.out.println("EXIT");
		cn.setTextAttributes(reset);
	}
}

