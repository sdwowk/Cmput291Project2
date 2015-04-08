package cmput291Project2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import com.sleepycat.db.*;


public abstract class FileTest {
	private static final String TABLE = "/tmp/sdwowk_mstrong_db/Data_Table";
	private static final int RECORD_NUM = 100000;
	private static Database my_table;
	private static Cursor myCursor = null;
	
	void createDB() {
	}
	
	/*
     *  To populate the given table with RECORD_NUM records
     */
    static void populateTable(Database my_table) {
	int range;
        DatabaseEntry keydb, datadb;
	String s;

	/*  
	 *  generate a random string with the length between 64 and 127,
	 *  inclusive.
	 *
	 *  Seed the random number once and once only.
	 */
	Random random = new Random(1000000);

        try {
            for (int i = 0; i < RECORD_NUM; i++) {

		/* to generate a key string */
		range = 64 + random.nextInt( 64 );
		s = "";
		for ( int j = 0; j < range; j++ ) 
		  s+=(new Character((char)(97+random.nextInt(26)))).toString();

		/* to create a DBT for key */
		keydb = new DatabaseEntry(s.getBytes());
		keydb.setSize(s.length()); 
		if(i == 100){
			System.out.println("Key: " + s);
		}
		if(i == 300){
			System.out.println("Key: " +s);
		}

		/* to generate a data string */
		range = 64 + random.nextInt( 64 );
		s = "";
		for ( int j = 0; j < range; j++ ) 
		  s+=(new Character((char)(97+random.nextInt(26)))).toString();
  
		
		/* to create a DBT for data */
		datadb = new DatabaseEntry(s.getBytes());
		datadb.setSize(s.length()); 
		if(i == 100){
			System.out.println("Data: " + s);
		}
		/* to insert the key/data pair into the database */
                my_table.putNoOverwrite(null, keydb, datadb);
            }
        }
        catch (DatabaseException dbe) {
            System.err.println("Populate the table: "+dbe.toString());
        }
    }
    
    public Database getDB(){
    	return my_table;
    }
    
    public String getPath(){
    	return TABLE;
    }
    
    public void closeDB(){
    	try {
			my_table.close();
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println("Error closing Database!");
		}
    }
    
    public ArrayList<String> getData(String in_key){
    	/*Performs basic search for given key*/
    	ArrayList<String> returns = new ArrayList<String>();
    	DatabaseEntry searchKey = new DatabaseEntry(in_key.getBytes());
    	DatabaseEntry returnDataByte = new DatabaseEntry();
    	try{
    		
    		if(my_table.get(null, searchKey, returnDataByte, LockMode.DEFAULT)==OperationStatus.SUCCESS)
    		{
    			String returnData = new String(returnDataByte.getData(), "UTF-8");
    			System.out.println("One key/data pair retrieved.");
    			returns.add(returnData);
    			myCursor = my_table.openCursor(null, null);
    			while(myCursor.getNext(searchKey, returnDataByte, LockMode.DEFAULT) == OperationStatus.SUCCESS){
    				returnData = new String(returnDataByte.getData(), "UTF-8");
    				returns.add(returnData);
    			}
    			myCursor.close();
    		}
    		else{
    			//System.out.println("Zero key/data pairs retrieved.");
    		}
    		
    		return returns;
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	System.out.println("Exception occurred, unable to search for data");
    	return null;
    }
    
    public void getKey(String in_data){
    	String returnKey = "Exception occurred, unable to search for key";
    	int recordCount = 0;
    	try {
    		
    		DatabaseEntry searchData = new DatabaseEntry(in_data.getBytes());
    		DatabaseEntry foundData = new DatabaseEntry();
    		DatabaseEntry returnKeyByte = new DatabaseEntry();
			myCursor = my_table.openCursor(null, null);
			
			/*This feels dirty but I can't think of a better way to search by data*/
			while(myCursor.getNext(returnKeyByte, foundData, LockMode.DEFAULT)==OperationStatus.SUCCESS)
			{
				if (searchData.equals(foundData)){
					returnKey = new String(returnKeyByte.getData(), "UTF-8");
					writeAnswers(returnKey, in_data);
					recordCount++;
				}
			}
			System.out.println("There were " + recordCount + " keys retrieved");
			
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally{
			if (myCursor!=null)
			{
				try {
					myCursor.close();
				} catch (DatabaseException e) {
					e.printStackTrace();
				}
			}
		}
    }
    
    public boolean inRange(String start, String end, byte[] foundKeyByte)
    {
    	String foundKey = "";
    	/*take first few characters of foundKeyByte and turn into a String*/
    	for ( int j = 0; j < start.length(); j++ ) 
  		  foundKey+=(new Character((char)(foundKeyByte[j]))).toString();
    	if (end.compareTo(foundKey) >= 0)
    		if(start.compareTo(foundKey) <= 0)
    			return true;
    	return false;
    }
    
    public void getRange(String start, String end)
    {
    	/*Similar method to getKey, add matches to ArrayList until start=end*/
    	String temp;
    	String tempK;
    	String[] returnListArray = new String[2];
    	int recordCount = 0;
    	boolean amIinRange = false;
    	try {
    		DatabaseEntry foundData = new DatabaseEntry();
    		DatabaseEntry returnKeyByte = new DatabaseEntry();
			myCursor = my_table.openCursor(null, null);
			
			
			while(myCursor.getNext(returnKeyByte, foundData, LockMode.DEFAULT)==OperationStatus.SUCCESS)
			{

				if(inRange(start, end, returnKeyByte.getData()))
				{
					amIinRange = true;
					/*we should probably just be writing the key/data pair to answers, 
					 * and not even bother with returning the data at all
					 */
					recordCount++;
					tempK = new String(returnKeyByte.getData(), "UTF-8");
					temp = new String(foundData.getData(), "UTF-8");
					returnListArray[0] = tempK;
					returnListArray[1] = temp;
					
					writeAnswers(returnListArray[0],returnListArray[1]);
					
					//returnList.add(returnListArray);
				
				}
				//else
					//if(amIinRange == true)
			}
			System.out.println("There were " + recordCount + " key/value pairs retrieved.");
	
		} catch (DatabaseException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally{
			if (myCursor!=null)
			{
				try {
					myCursor.close();
				} catch (DatabaseException e) {
					e.printStackTrace();
				}
			}
		}
    }

	public void setDB(Database my_table2) {
		my_table = my_table2;		
	}
	
	public void writeAnswers(String key, String data)
	{
		//from http://alvinalexander.com/java/edu/qanda/pjqa00009.shtml
		BufferedWriter bufwrit = null;
		try{
			bufwrit = new BufferedWriter(new FileWriter("answers.txt",true));
			bufwrit.write("Key: " + key);
			bufwrit.newLine();
			bufwrit.write("Data: " + data);
			bufwrit.newLine();
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
