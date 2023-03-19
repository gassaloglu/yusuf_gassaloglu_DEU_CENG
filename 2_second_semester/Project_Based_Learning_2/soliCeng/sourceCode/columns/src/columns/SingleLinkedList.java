package columns;


public class SingleLinkedList {
	private NodeSll head;
	
	public void add(Object data) {
		if(head == null) {
			NodeSll newNode = new NodeSll(data);
			head = newNode;
		}
		else {
			NodeSll temp = head;
			while (temp.getLink() != null) {
				temp = temp.getLink();
			}
			NodeSll newNode = new NodeSll(data);
			temp.setLink(newNode);
		}
	}
	public NodeSll search2(int selected) {
		NodeSll temp=head;
		int count=0;
		while(count<selected) {
			temp=temp.getLink();
			count++;
		}
		
		return(temp);
	}
	public int size() {
		if (head == null) {
			return 0;
		}
		else {
			int count = 0;
			NodeSll temp = head;
			
			while (temp != null) {
				temp = temp.getLink();
				count++;
			}
			
			return count;
		}
	}
	
	//prints SLL in desired location
	public void displayCards(int x, int y) {
		if (head == null) {
			System.out.println("List is empty!");
		}
		else {
			NodeSll temp = head;
			int counter = y;
			while (temp != null) {
				Columns.console.getTextWindow().setCursorPosition(x, counter);
				System.out.println(temp.getData() + " ");
				temp = temp.getLink();
				counter++;
			}
			System.out.println();
		}
	}

	public void display() {
		if (head == null) {
			System.out.println("List is empty!");
		}
		else {
			NodeSll temp = head;
			while (temp != null) {
				System.out.print(temp.getData() + " ");
				temp = temp.getLink();
			}
			System.out.println();
		}
	}
	
	public int counter(Object data) {
		if (head == null) {
			return 0;
		}
		else {
			int data_counter = 0;
			NodeSll temp = head;
			
			while (temp != null) {
				if ((Integer)temp.getData() == (Integer)data) {
					data_counter++;
					}
				temp = temp.getLink();
			}
			return data_counter;
		}	
	}
	
	//returns true if object found
	public boolean search(Object data) {
		if (head == null) {
			System.out.println("List is empty!");
			return false;
		}
		else {
			NodeSll temp = head;
			while (temp != null) {
					if ((Integer)temp.getData() == (Integer)data) {
					return true;
					}
					temp = temp.getLink();
				}
			return false;
			}
		}
	
	// remove all the data
	public void remove(Object dataToRemove) {
		if (head == null) {
			System.out.println("List is empty!");
		}
		else {
			while (head != null && (Integer)head.getData() == (Integer)dataToRemove) {
				head = head.getLink();
			}
			
			NodeSll temp = head;
			NodeSll previous = null;
			while (temp != null) {
				if ((Integer)temp.getData() == (Integer)dataToRemove) {
					previous.setLink(temp.getLink());
					temp = previous;
												
				}
				previous = temp;
				temp = temp.getLink();
			}
			
		}
	}
	
	// pop the data count times
	public void pop(Object dataToPop, int count) {
		if (head == null) {
			System.out.println("List is empty!");
		}
		else {
			int counter = 0;
			while (head != null && (Integer)head.getData() == (Integer)dataToPop && counter < count) {
				head = head.getLink();
				counter++;
			}
			
			NodeSll temp = head;
			NodeSll previous = null;
			while (temp != null) {
				if ((Integer)temp.getData() == (Integer)dataToPop && counter < count) {
					previous.setLink(temp.getLink());
					temp = previous;
					counter++;
				}					
				previous = temp;
				temp = temp.getLink();
			}
			
		}
	}
	
	//remove node and return it from head
	public Object popTheHead() {
		if (head == null) {
			System.out.println("XX");
			return "Box is empty !" ;
		}
		else {
			int temp = (int) head.getData();
			head = head.getLink();
			return temp;
		}
		
	}
	
}
