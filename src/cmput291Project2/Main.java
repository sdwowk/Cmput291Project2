package cmput291Project2;

import java.util.Scanner;
import java.io.File;

public class Main {
	static Scanner in;
	private static FileTest fileTest;
	
	public static void main(String[] args) {
		//fileTest = null;
		
		in = new Scanner(System.in);
		
		fileTest = TestGenerator.getFileTest(args[0]);
		if(fileTest == (null)){
			System.err.println("BOO");
		}
		File newFile = new File(fileTest.getPath());
		newFile.getParentFile().mkdirs();
		/*
		String input = null;
		while(fileTest == null){
			System.out.println("Error in input please enter a proper file structure: ");
			input = in.nextLine();
			fileTest = TestGenerator.getFileTest(input);
			
		}
		*/
		
		int choice;
		while(true){
			
			displayOptions();
			try{
				choice = in.nextInt();

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
					fileTest.closeDB();
					if(fileTest instanceof IndexTest){
						File idb = new File("/tmp/sdwowk_mstrong_db/Index_Table");
						File db = new File("/tmp/sdwowk_mstrong_db/Data_Table");
						try{
							db.delete();
							idb.delete();
						}
						catch(Exception e){
							System.out.println("Could not delete database");
						}
					}else{

						File db = new File("/tmp/sdwowk_mstrong_db/Data_Table");
						try{
							db.delete();
						}
						catch(Exception e){
							System.out.println("Could not delete database");
						}
					}
					break;
				case 6:
					System.exit(0);
					break;

				default:
					System.out.println("Improper input");
				}
			}
			catch(Exception e){
				System.out.println("Improper input, quitting everything!");
				System.exit(0);
			}
				
		}
	}
		
	
	
	private static void dataMenu() {
		System.out.println("Please enter the data you would like to query with: ");
		String query = in.next();
		
		long start = System.nanoTime();
		fileTest.getKey(query);
		long end = System.nanoTime();
		System.out.println("Time taken to execute query (us): " + getMicros(start, end));
		//for(String results : result){
		//	writeAnswers(result, query);
		//}
		//writeAnswers(result);
		/*** Need to store the data results to a file called answers ***/
		// Not sure how we are doing the next part yet
		//ArrayList<String> dataQueryList = fileTest.dataQuery(query);
		//print(dataQueryList);
		
	}
	private static void keyMenu() {
		System.out.println("Please enter the key you would like to query with: ");
		String query = in.next();
	
		long start = System.nanoTime();
		fileTest.getData(query);
		long end = System.nanoTime();
		System.out.println("Time taken to execute query (us): " + getMicros(start, end));
		
		//(if result == null){
		//	System.out.println("Nothing found");
		//}else{
		//	for(String results : result){
		// 		writeAnswers(query, result);
		//	}
		/*** Need to store the data results to a file called answers ***/
		// Not sure how we are doing the next part yet
		// ArrayList<String> keyQueryList = fileTest.keyQuery(query);
	}
	
	private static long getMicros(long start, long end) {
		//Convert nano time to Micro seconds
		return ((end-start)/1000);
	}

	private static void rangeMenu(){
		System.out.println("Please enter the range you would like to query with: (startRange-endRange)");
		String query = in.next();
		
		/*** Need to store the data results to a file called answers ***/
		String numOne = query.split("-")[0].trim();
		String numTwo = query.split("-")[1].trim();
		
		long start = System.nanoTime();
		fileTest.getRange(numOne, numTwo);
		long end = System.nanoTime();
		System.out.println("Time taken to execute query (us): " + getMicros(start, end));
		
		/*
		for(String[] results : result){
			writeAnswers(results[0], results[1]);
		}
		*/

	}
	
	private static void displayOptions(){
		//Display menu
		System.out.println("1. Create and populate database");
		System.out.println("2. Retrieve records with provided key");
		System.out.println("3. Retrieve records with given data");
		System.out.println("4. Retrieve records with a given range");
		System.out.println("5. Destroy the database");
		System.out.println("6. Quit");
		System.out.print("Please enter your choice: ");
	}
}
