import java.io.FileWriter;

public class SingleLinkedList {
	Node head;
	
	//to add data to list
	public void add(Object data) {
		//if list is empty
		if(head == null) {
			Node newNode = new Node(data);
			head = newNode;
		}
		//if list is not empty
		else {
			Node temp = head;
			// to add data to end of the list 
			while (temp.getLink() != null) {
				temp = temp.getLink();
			}
			Node newNode = new Node(data);
			temp.setLink(newNode);
		}
	}
	
	//to print and save top 10 players 
	public void displayAndSavePlayers(String highScoreTableFileName) {
		if (head == null) {
			System.out.println("List is empty!");
		}
		else {
			Node temp = head;
			FileWriter myWriter = null;
			try {
				myWriter = new FileWriter(highScoreTableFileName); // open file writer
			} catch (Exception e) {
				System.out.println(e);
			}
			int count = 0;
			while (temp != null) {
				// to save and print top 10
				if (count < 10) {
					try {
						count++;
						myWriter.write(((Player) temp.getData()).getName() + "\n");  //save player name
						myWriter.write(((Player) temp.getData()).getScore() + "\n"); // save player score
					} catch (Exception e) {
						System.out.println(e);
					}		      
					System.out.println("     " +((Player) temp.getData()).getName() + " -> " + ((Player) temp.getData()).getScore()); // print high score table to screen
				}
				temp = temp.getLink(); // go to next data
			}
			System.out.println();
		    try {
				myWriter.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	// to print the list
	public void display() {
		if (head == null) {
			System.out.println("List is empty!");
		}
		else {
			Node temp = head;
			// print from head to the tail of the list
			while (temp != null) {
				System.out.print(temp.getData() + " ");
				temp = temp.getLink();
			}
			System.out.println();
		}
	}
	
	// to count the data in the list
	public int counter(Object data) {
		if (head == null) {
			return 0;
		}
		else {
			int data_counter = 0;
			Node temp = head;
			
			while (temp != null) {
				if ((Integer)temp.getData() == (Integer)data) {
					data_counter++;
					}
				temp = temp.getLink();
			}
			return data_counter;
		}	
	}
	
	// to search if the list contains the data
	public boolean search(Object data) {
		if (head == null) {
			System.out.println("List is empty!");
			return false;
		}
		else {
			Node temp = head;
			while (temp != null) {
					if ((Integer)temp.getData() == (Integer)data) {
					return true;
					}
					temp = temp.getLink();
				}
			return false;
			}
		}
		
	// pop the data 'count' times
	public void pop(Object dataToPop, int count) {
		if (head == null) {
			System.out.println("List is empty!");
		}
		else {
			int counter = 0;
			// if the head data is the same data as data to be removed
			while (head != null && (Integer)head.getData() == (Integer)dataToPop && counter < count) {
				// remove the data and declare the next data as head data
				head = head.getLink();
				counter++;
			}
			
			Node temp = head;
			Node previous = null;
			while (temp != null) {
				// if the data is the same data as data to be removed
				if ((Integer)temp.getData() == (Integer)dataToPop && counter < count) {
					// remove the data and connect previous and next data
					previous.setLink(temp.getLink());
					temp = previous;
					counter++;
				}					
				previous = temp;
				temp = temp.getLink();
			}	
		}
	}
	
	//to sort the list
	public void sortList() {  
        //Node current will point to head  
        Node current = head, index = null;  
        int tempScore;  
        String tempName;
        
        if(head == null) {  
            return;  
        }  
        else {  
            while(current != null) {  
                //Node index will point to node next to current  
                index = current.getLink();  
                  
                while(index != null) {  
                    //If current node's data is greater than index's node data, swap the data between them  
                    if(((Player) (current.getData())).getScore() < ((Player) (index.getData())).getScore()) {  
                        tempScore = ((Player) (current.getData())).getScore(); 
                        tempName =  ((Player) (current.getData())).getName();
                        
                        ((Player) (current.getData())).setScore(((Player) (index.getData())).getScore()); 
                        ((Player) (current.getData())).setName(((Player) (index.getData())).getName());  

                        ((Player) (index.getData())).setScore(tempScore);
                        ((Player) (index.getData())).setName(tempName);
      
                    }  
                    index = index.getLink();  
                }  
                current = current.getLink();  
            }      
        }  
	}  
}
