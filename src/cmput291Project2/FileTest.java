package cmput291Project2;

import java.util.ArrayList;
import java.util.Random;

import com.sleepycat.db.*;

public abstract class FileTest {
	private static final String TABLE = "/tmp/sdwowk_mstrong_db/Data_Table";
	private static final int RECORD_NUM = 100000;
	private static final Database my_table;
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


		/* to generate a data string */
		range = 64 + random.nextInt( 64 );
		s = "";
		for ( int j = 0; j < range; j++ ) 
		  s+=(new Character((char)(97+random.nextInt(26)))).toString();
  
		
		/* to create a DBT for data */
		datadb = new DatabaseEntry(s.getBytes());
		datadb.setSize(s.length()); 

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
    
    public String getData(String in_key){
    	/*Performs basic search for given key*/
    	DatabaseEntry searchKey = new DatabaseEntry(in_key.getBytes());
    	DatabaseEntry returnDataByte = new DatabaseEntry();
    	try{
    		if(my_table.get(null, searchKey, returnDataByte, LockMode.DEFAULT)==OperationStatus.SUCCESS)
    		{
    			String returnData = new String(returnDataByte.getData(), "UTF-8");
    			System.out.println("One key/data pair retrieved.");
    			return returnData;
    		}
    		else{
    			System.out.println("Zero key/data pairs retrieved.");
    			return "No results found.";
    		}
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	return "Exception occurred, unable to search for data";
    }
    
    public ArrayList<String> getKey(String in_data){
    	String returnKey = "Exception occurred, unable to search for key";
    	ArrayList<String> returnList = new ArrayList<String>();
    	try {
    		
    		DatabaseEntry searchData = new DatabaseEntry(in_data.getBytes());
    		DatabaseEntry foundData = new DatabaseEntry();
    		DatabaseEntry returnKeyByte = new DatabaseEntry();
			myCursor = my_table.openCursor(null, null);
			
			/*This feels dirty but I can't think of a better way to search by data*/
			while(myCursor.getNext(returnKeyByte, foundData, LockMode.DEFAULT)==OperationStatus.SUCCESS)
			{
				if (searchData.equals(foundData.getData())){
					System.out.println("One record retrieved");
					/*unsure of why it says this type of encoding is unsupported*/
					returnKey = new String(returnKeyByte.getData(), "UTF-8");
					returnList.add(returnKey);
				}
			}
			System.out.println("Zero key/data pairs retrieved.");
			return "Key not found";
		} catch (DatabaseException e) {
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
    	return returnKey;
    }
    
    public boolean inRange(String start, String end, byte[] foundKeyByte)
    {
    	String foundKey = "";
    	/*take first few characters of foundKeyByte and turn into a String*/
    	for ( int j = 0; j < start.length(); j++ ) 
  		  foundKey+=(new Character((char)(foundKeyByte[j]))).toString();
    	if (start.compareTo(foundKey) >= 0)
    		if(start.compareTo(foundKey) <= 0)
    			return true;
    	return false;
    }
    
    public ArrayList<String[]> getRange(String start, String end)
    {
    	/*Similar method to getKey, add matches to ArrayList until start=end*/
    	ArrayList<String[]> returnList = new ArrayList<String[]>();
    	String temp;
    	String tempK;
    	String[] returnListArray = new String[2];
    	boolean amIinRange = false;
    	try {
    		DatabaseEntry foundData = new DatabaseEntry();
    		DatabaseEntry returnKeyByte = new DatabaseEntry();
			myCursor = my_table.openCursor(null, null);
			
			
			while(myCursor.getNext(returnKeyByte, foundData, LockMode.DEFAULT)==OperationStatus.SUCCESS)
			{
				if(inRange(start, end, foundData.getData()))
				{
					amIinRange = true;
					/*we should probably just be writing the key/data pair to answers, 
					 * and not even bother with returning the data at all
					 */
					
					tempK = new String(returnKeyByte, "UTF-8");
					temp = new String(foundData.getData(), "UTF-8");
					returnListArray[0] = tempK;
					returnListArray[1] = temp;
							
					returnList.add(returnListArray);
				
				}
				else
					if(amIinRange == true)
						break;
				
			}
			
	System.out.println("There were " + returnList.size() + "key/value pairs found.");

		} catch (DatabaseException e) {
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
    	return returnList;
    }
}
