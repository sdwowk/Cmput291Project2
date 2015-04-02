package cmput291Project2;

import java.io.Console;

public class Main {
	static Console console;
	public static void main(String[] args){
		FileTest fileTest = null;
		
		console = System.console();
		
		fileTest = TestGenerator.getFileTest(args[0]);
		
		String input = null;
		while(fileTest == null){
			
			input = console.readLine("Error in input please enter a proper file structure: ");
			fileTest = TestGenerator.getFileTest(input);
			
		}
		
		fileTest.print();
		
	}
}
