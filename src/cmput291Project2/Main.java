package cmput291Project2;

//import java.util.Scanner;
import java.io.Console;

public class Main {
	static Console console;
	public static void main(String[] args){
		FileTest fileTest = null;
		
		console = System.console();
		//Scanner in = new Scanner(System.in);
		
		fileTest = TestGenerator.getFileTest(args[0]);
		
		String input = null;
		while(fileTest == null){
			
			input = console.readLine("Error in input please enter a proper file structure: ");
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
		//int choice = in.nextInt();
		//or alternatively if we don't want to use scanner
		int choice = Integer.parseInt(System.console().readLine());
		
		
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
				//get records with key
				break;
			case 3:
				//get data with key
				break;
			case 4:
				//get records in range
				break;
			case 5:
				//destroy database
				break;
			case 6:
				System.exit(0);
				break;
		}
	}
}
