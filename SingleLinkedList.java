
public class SingleLinkedList {
	private Node head;
	public void addToHead(Object data) {
		//We put the object we added directly at the beginning of the Linked List.
		if(head == null) {
			Node node = new Node(data);
			head = node;
		}
		else {
			Node node = new Node(data);
			node.setLink(head);
			head = node;
		}
	}
	public void addToHighScoreTable(Person data) {
		//in order to create a high score table, an addition method has been
		//written that adds separate scores from higher to lower.
		if(head == null) {
			Node node = new Node(data);
			head = node;
		}
		else if(data.getScore() >= ((Person)head.getData()).getScore()) {
			Node node = new Node(data);
			node.setLink(head);
			head = node;
		}
		else {
			Node temp = head;
			Node previous = null;
			while((temp != null) && data.getScore() < ((Person)temp.getData()).getScore()) {
				previous = temp;
				temp = temp.getLink();
			}
			if(temp == null) {
				Node node = new Node(data);
				previous.setLink(node);
			}
			else {
				Node node = new Node(data);
				node.setLink(temp);
				previous.setLink(node);
			}
		}
		if(size() == 11) {
			/*Since the high score table has 10 elements
			  this section has been added to delete the last element.
			*/
			Node temp = head;
			for(int i = 0;i < 9; i++) {
				temp = temp.getLink();
			}
			temp.setLink(null);
		}
	}
	public void addAsAlphabetic(Object data) {
		//This addition method has been written to sort the animals.txt alphabetically.
		if(head == null) {
			Node node = new Node(data);
			head = node;
		}
		//For alphabetical sorting, sorting was made based on the  ASCII character number of each character.
		else if(data.toString().charAt(0) < head.getData().toString().charAt(0)) {
			Node node = new Node(data);
			node.setLink(head);
			head = node;
		}
		else {
			Node temp = head;
			Node previous = null;
			while((temp != null) && data.toString().charAt(0) >= temp.getData().toString().charAt(0)) {
				if(whichOneIsPriority(data, temp.getData())) {
					break;
				}
				previous = temp;
				temp = temp.getLink();
			}
			if(temp == null) {
				Node node = new Node(data);
				previous.setLink(node);
			}
			else {				
				Node node = new Node(data);
				node.setLink(temp);
				previous.setLink(node);
			}
		}
	}
	private boolean whichOneIsPriority(Object data1,  Object data2) {
		//if the initials of the animals are the same,
		//the order for the remaining parts is made in this boolean function.
		String sData = data1.toString();
		String tempData = data2.toString();
		if(sData.charAt(0) != tempData.charAt(0)) {
			return false;
		}
		else {
			boolean isFindIt = false;
			int length1 = sData.length();
			int lenght2 = tempData.length();
			int minLength = Math.min(length1, lenght2);
			int counter = 0;
			for(int i = 0;i < minLength; i++) {
				if(sData.charAt(i) < tempData.charAt(i)) {
					isFindIt = true;
					break;
				}
				else if(sData.charAt(i) == tempData.charAt(i)) {
					counter++;
				}
			}
			if(isFindIt || counter == minLength) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	public void ReplaceCharacter(int order, String chr) {
		//The method that replaces the element in the given order with the incoming character as a parameter
		if(head == null) {
			System.out.println("LinkedList is empty.");
		}
		else {
			Node temp = head;
			for(int i = 0;i < order; i++) {
				temp = temp.getLink();
			}
			temp.setData(chr);
		}
	}
	public void delete(Object data) {
		//Delete method was used for just SLL2 so it was designed according SLL2
		if(head == null) {
			System.out.println("LinkedList is empty.");
		}
		else {
			while(head != null && head.getData().toString().equals(data.toString())) {
				head = head.getLink();
			}
			Node temp = head;
			Node previous = null;
			while(temp != null) {
				if(temp != null && temp.getData().toString().equals(data.toString())) {
					previous.setLink(temp.getLink());
					temp = previous;
				}
				previous = temp;
				temp = temp.getLink();
			}
		}
	}
	public void display() {
		if(head != null) {
			Node temp = head;
			while(temp != null) {
				System.out.print(temp.getData() + " ");
				temp = temp.getLink();
			}
		}
		else {
			//I put this section in the comment line in case the word cannot be found until the last letter.
			//System.out.println("LinkedList is empty.");
		}
	}
	public void displayHighScoreTable() {
		//Since SLL3, which contains the high score, has an object in Person structure,
		//the display method was written separately.
		System.out.println("\nHIGH SCORE TABLE\n");
		if(head == null) {
			System.out.println("LinkedList is empty.");
		}
		else {
			Node temp = head;
			Person p = new Person();
			
			while(temp != null) {
				p = (Person)temp.getData();
				System.out.print(p.getName());
				//For a nice output, the statically available spacing method in the WheelOfFortune class is called
				WheelOfFortune.LeaveSpace(p.getName().length(), 10);
				System.out.println(p.getScore());
				temp = temp.getLink();
			}
		}
	}
	public boolean search(Object item) {
		if(head == null) {
			System.out.println("LinkedList is empty.");
			return false;
		}
		else {
			boolean flag = false;
			Node temp = head;
			while(temp != null) {
				if((int)item == (int)temp.getData()) {
					flag = true;
				}
				temp = temp.getLink();
			}
			return flag;
		}
	}
	public int size() {
		int counter = 0;
		if(head == null) {
			System.out.println("LinkedList is empty.");
		}
		else {
			Node temp = head;
			while(temp != null) {
				temp = temp.getLink();
				counter++;
			}
		}
		return counter;
	}
	public Object indexOf(int order) {
		//The function that finds the order element that comes as a parameter
		if(head == null) {
			System.out.println("LinkedList is empty.");
			return null;
		}
		else {
			Node temp = head;
			int index = 0;
			while(temp != null && index != order) {
				temp = temp.getLink();
				index++;
			}
			if(temp == null) {
				return null;
			}
			else {
				return temp.getData();
			}
			
		}
	}
}
