package cmput291Project2;

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

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
		
		while(true){
			switch(choice){
				case 1:
					fileTest.createDB();
					
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
					//close database
					//my_table.close();
					File db = new File("/tmp/sdwowk_mstrong_db/Data_Table");
					db.delete();
					break;
				case 6:
					System.exit(0);
					break;
			}
		}
	}
	
	private static void dataMenu() {
		System.out.println("Please enter the data you would like to query with: ");
		String query = in.nextLine();
		
		/*** Need to store the data results to a file called answers ***/
		// Not sure how we are doing the next part yet
		//ArrayList<String> dataQueryList = fileTest.dataQuery(query);
		//print(dataQueryList);
		
	}
	private static void keyMenu() {
		System.out.println("Please enter the key you would like to query with: ");
		String query = in.nextLine();
		
		/*** Need to store the data results to a file called answers ***/
		// Not sure how we are doing the next part yet
		// ArrayList<String> keyQueryList = fileTest.keyQuery(query);
	}
	
	private static void rangeMenu(){
		System.out.println("Please enter the range you would like to query with: (Two numbers separated by a hyphen)");
		String query = in.nextLine();
		
		/*** Need to store the data results to a file called answers ***/
		String numOne = query.split("-")[0].trim();
		String numTwo = query.split("-")[1].trim();
		
		/* Unsure of the execution but something like this
		
			ArrayList<String> rangeQueryList = fileTest.rangeQuery(numOne, numTwo);
			
		}
		*/

	}
	
	private static void writeAnswers(String input)
	{
		//from http://alvinalexander.com/java/edu/qanda/pjqa00009.shtml
		BufferedWriter bufwrit = null;
		try{
			bufwrit = new BufferedWriter(new FileWriter("answers",true));
			bufwrit.write(input);
			bufwrit.newLine();
			bufwrit.flush();
		}
		catch(IOException e){
			System.out.println("Something went wrong writing to answers.");
			e.printStackTrace();
		}
		finally{
			if (bufwrit == null)
			{
				try {
					bufwrit.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		}
	}
}
