import java.io.*;
import java.util.Random;

public class WheelOfFortune {
	
	private SingleLinkedList SLL1;
	private SingleLinkedList SLL2;
	private SingleLinkedList SLL3;
	private SingleLinkedList SLL4;
	private SingleLinkedList SLL5;
	private SingleLinkedList Wheel;
	private Random rnd;
	public WheelOfFortune() throws Exception {
		SLL1 = new SingleLinkedList();
		SLL2 = new SingleLinkedList();
		SLL3 = new SingleLinkedList();
		SLL4 = new SingleLinkedList();
		SLL5 = new SingleLinkedList();
		Wheel = new SingleLinkedList();
		rnd = new Random();
		MainGameMode();
	}
	private void MainGameMode() throws Exception {
		//At the beginning of the game, I create the necessary structures by calling the necessary methods.
		if(UploadAnimalsTXT()) {
			FillInTheWheel();
			AddAllLettersInASLL2();
			
			int randomNumber = rnd.nextInt(SLL1.size());
			System.out.println("Randomly generated number : " + (randomNumber + 1) + "\n");
			String animal = SLL1.indexOf(randomNumber).toString();
			String wheel, guess, name = "YOU";
			//The necessary method is called to create SLL4 and SLL5
			FillInTheSLL4AndSLL5(animal);
			int step = 1, score = 0;
			
			/*these are the variables that keep the number of letters 
			*in total and the number of letters found in only one round
			*animalLength ->  A variable that I use to be able to set  the spaces properly 
			*/
			
			int howManyLettersFound, numberOfLettersFound = 0, animalLength = animal.length();
			
			while(numberOfLettersFound != animalLength) {
				
				//The game screen is redrawn with each new spin and the wheel is spinning
				
				GameScreen(step, score, animalLength);
				wheel =  Wheel.indexOf(rnd.nextInt(Wheel.size())).toString();
				System.out.println("\nWheel : " + wheel + "");
				if(wheel.equals("Bankrupt")) {
					System.out.println("YOU WENT BANKRUPT :(((");
					score = 0;
				}
				else {
					//replaceCharacter searches for the guessed letter within the word and returns how many matches
					guess = SLL2.indexOf(rnd.nextInt(SLL2.size())).toString();
					System.out.println("Guess : " + guess + "");
					howManyLettersFound = ReplaceCharacters(guess);
					if(howManyLettersFound != 0) {
						score += howManyLettersFound * Integer.valueOf(wheel);
						numberOfLettersFound += howManyLettersFound;
					}
					SLL2.delete(guess);
				}
				System.out.println("\n\n");
				step++;
			}
			/*
			 * At the end of the game, the screen is drawn one more time,
			 *  the newly formed score is added to the high score table ,
			 *  and the new ranking is made. And at the end,
			 *  the best 10 scores of all time are printed to the HighScoreTable.txt file.
			 */
			GameScreen(step, score, animalLength);
			System.out.println("\n\nYou get " + score + "$  !!!");
			if(ReadHighScoreTable()) {
				Person person = new Person(name, score);
				SLL3.addToHighScoreTable(person);
				SLL3.displayHighScoreTable();
				PrintHighScoreTableToFile();
			}
			else {
				System.out.println("\nERROR !!! HighScoreTable.txt not found");
			}
		}
		else {
			System.out.println("ERROR !!! animals.txt not found");
		}
	}
	private boolean UploadAnimalsTXT() throws IOException {
		//Uploading animal.txt file to SLL1 in this section
		File file = new File("D:\\animals.txt");
		if(!file.exists()) {
			return false;
		}
		else {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String animal;
			String temp;
			while((animal = br.readLine()) != null) {
				//I deleted the spaces in case there were spaces in the file
				temp = animal.trim();
				SLL1.addAsAlphabetic(temp);
			}
			br.close();
		}
		return true;
	}
	private boolean ReadHighScoreTable() throws IOException {
		//High score table is read from file and added to SLL3
		File file = new File("D:\\HighScoreTable.txt");
		if(!file.exists()) {
			return false;
		}
		else {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			Person person = new Person();
			int counter = 0;
			while((line = br.readLine()) != null) {
				
				if(counter % 2 == 0) {
					person.setName(line);
				}
				else {
					//This is how a check was made in case there is a space in the score sections on the file.
					line = line.trim();
					person.setScore(Integer.valueOf(line));
					SLL3.addToHighScoreTable(person);
					person = new Person();
				}
				counter++;
			}
			br.close();
			return true;
		}
	}
	private void PrintHighScoreTableToFile() throws IOException {
		//While writing to the file, the elements of the high score table
		//were called one by one and the operation was performed.
		File file = new File("D:\\HighScoreTable.txt");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		Person personInfo;
		int order = 0;
		while((personInfo = (Person)SLL3.indexOf(order)) != null) {
			bw.write(personInfo.getName());
			bw.newLine();
			
			//Since scores are integers, they are written to the file in 
			//String structure so that they are not affected by "\n".
			bw.write(String.valueOf((personInfo).getScore()));
			bw.newLine();
			order++;
		}
		bw.close();
	}
	private void AddAllLettersInASLL2() {
		//Here, letters load into SLL2 in this section
		int asciiNumber = 90;
		char asciiCharacter;
		while(asciiNumber != 64) {
			asciiCharacter = (char)asciiNumber;
			SLL2.addAsAlphabetic(asciiCharacter);
			asciiNumber--;
		}
	}
	private void FillInTheWheel() {
		//suitable elements for the wheel were placed in this section
		String[] sWheel = {"10", "20", "30", "40", "100", "200", "300", "400", "Bankrupt", "Bankrupt"};
		for(int i = 0;i < sWheel.length; i++) {
			Wheel.addToHead(sWheel[i]);
		}
	}
	private void FillInTheSLL4AndSLL5(String animal) {
		//Filling SLL4 and SLL5 with appropriate characters
		for(int i = animal.length() - 1;i >= 0 ; i--) {
			SLL4.addToHead(animal.charAt(i));
			SLL5.addToHead('_');
		}
	}
	private void GameScreen(int step, int score, int beginCursorX) {
		//A method has been written to print the basic builds of the game
		System.out.print("Word : "); 
		SLL5.display();
		LeaveSpace(beginCursorX, 25);						
		System.out.print("Step : " + step);
		beginCursorX = String.valueOf(step).length();
		LeaveSpace(beginCursorX, 8);
		System.out.print("Score : " + score);
		beginCursorX = String.valueOf(score).length();
		LeaveSpace(beginCursorX, 12);
		SLL2.display();
	}
	private int ReplaceCharacters(String chr) {
		//whether the selected letter is in the word is checked in this section
		int howManyLettersFound = 0;
		String tempChr;
		for(int i = 0;i < SLL4.size(); i++) {
			//This method returns the element of the linked list to us.
			tempChr = SLL4.indexOf(i).toString().toUpperCase();
			if(tempChr.equals(chr)) {
				//if there is a matching letter, this character is replaced in SLL5
				SLL5.ReplaceCharacter(i, chr);
				howManyLettersFound++;
			}
		}
		return howManyLettersFound;
	}
	public static void LeaveSpace(int x1, int x2) {
		//I called this method in displayHighScore method in single list so I wrote this method as static
		//Spaces were counted in order for the game screen to be regular.
		for(int i = x1;i < x2; i++) {
			System.out.print(" ");
		}
	}
}
