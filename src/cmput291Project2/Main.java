package cmput291Project2;

import java.util.Scanner;
import java.io.Console;

public class Main {
	static Scanner in;
	private static FileTest fileTest;
	
	public static void main(String[] args){
		fileTest = null;
		
		in = new Scanner(System.in);
		
		fileTest = TestGenerator.getFileTest(args[0]);
		
		String input = null;
		while(fileTest == null){
			System.out.println("Error in input please enter a proper file structure: ");
			input = in.nextLine();
			fileTest = TestGenerator.getFileTest(input);
			
		}
	
		//Display menu
		System.out.println("1. Create and populate database");
		System.out.println("2. Retrieve records with provided key");
		System.out.println("3. Retrieve records with given data");
		System.out.println("4. Retrieve records with a given range");
		System.out.println("5. Destroy the database");
		System.out.println("6. Quit");
		System.out.print("Please enter your choice: ");
		
		int choice = in.nextInt();
		//or alternatively if we don't want to use scanner
		//int choice = Integer.parseInt(System.console().readLine());
		
		
		switch(choice){
			case 1:
				if (args[0].toLowerCase().equals("db_btree")){
					//do btree stuff
				}
				else if (args[0].toLowerCase().equals("db_hash")){
					//do hash tree stuff
				}
				else{
					System.out.println("Invalid argument entered, exiting");
					System.exit(0);
				}
				break;
			case 2:
				keyMenu();
				break;
			case 3:
				//get data with data
				dataMenu();
				break;
			case 4:
				//get records in range
				rangeMenu();
				break;
			case 5:
				//destroy database
				break;
			case 6:
				System.exit(0);
				break;
		}
	}
	private static void dataMenu() {
		System.out.println("Please enter the data you would like to query with: ");
		String query = in.nextLine();
		
		// Not sure how we are doing the next part yet
		// fileTest.dataQuery(query);
		
	}
	private static void keyMenu() {
		System.out.println("Please enter the key you would like to query with: ");
		String query = in.nextLine();
		// Not sure how we are doing the next part yet
		// fileTest.keyQuery(query);
	}
	
	private static void rangeMenu(){
		System.out.println("Please enter the range you would like to query with: (Two numbers separated by a hyphen)");
		String query = in.nextLine();
		
		/*** Doubles or Ints? ***/
		Double numOne = Double.parseDouble(query.split("-")[0].trim());
		Double numTwo = Double.parseDouble(query.split("-")[1].trim());
		
		/* Unsure of the execution but something like this
		if(numOne > numTwo){
			fileTest.rangeQuery(numTwo, numOne);
		}else{
			fileTest.rangeQuery(numOne, numTwo);
		}
		*/

	}
}
