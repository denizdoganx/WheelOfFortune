
public class Person {
	//The objects of this class were used while creating the high score table.
	private String name;
	private int score;
	public Person() {
		
	}
	public Person(String name, int score) {
		this.name = name;
		this.score = score;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}
