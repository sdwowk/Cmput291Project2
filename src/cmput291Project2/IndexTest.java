package cmput291Project2;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import com.sleepycat.db.Cursor;
import com.sleepycat.db.Database;
import com.sleepycat.db.DatabaseConfig;
import com.sleepycat.db.DatabaseEntry;
import com.sleepycat.db.DatabaseException;
import com.sleepycat.db.DatabaseType;
import com.sleepycat.db.LockMode;
import com.sleepycat.db.OperationStatus;

public class IndexTest extends FileTest {
	/***
	 * IndexTest creates two databases that have their key's and data is swapped. THis should make data search much more efficient 
	 */
	
	private static String INDEX_TABLE;
	private static String DATA_TABLE;
	private static Database my_table;
	private static Database index;
	private static int RECORD_NUM = 100000;
	private static Cursor myCursor = null;
	
	public IndexTest(){
		INDEX_TABLE = "/tmp/sdwowk_mstrong_db/Index_Table";
		DATA_TABLE = super.getPath();
	}
	
	@Override
	public void createDB() {
		try{	
			// Create the database object.
		    DatabaseConfig dbConfig = new DatabaseConfig();
		    dbConfig.setType(DatabaseType.BTREE);
		    dbConfig.setAllowCreate(true);
		    my_table = new Database(DATA_TABLE, null, dbConfig);
		    index = new Database(INDEX_TABLE, null, dbConfig);
		    
		    System.out.println(INDEX_TABLE + " has been created");
		    
		    populateTable(index);
		    System.out.println("100 000 records inserted into" + INDEX_TABLE);
		    super.setDB(my_table);
		}catch(Exception e){
			System.err.println("Error Creating Database");
			System.err.println(e.getMessage());
		}
	}
	
	static void populateTable(Database index) {
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

				/* To insert the key/data pair into the database, index file swaps keys and data from the database, 
				 * Don't want to have extra key's holding same data however so if/else to keep it neat. */
				if(my_table.putNoOverwrite(null, keydb, datadb) == OperationStatus.KEYEXIST){

				}else{
					index.put(null, datadb, keydb);
				}
			}
		}
		catch (DatabaseException dbe) {
			System.err.println("Populate the table: "+dbe.toString());
		}
	}
	
	
	@Override
    public void getKey(String in_data){
    	String returnKey = "Exception occurred, unable to search for key";
    	int recordCount = 0;
    	try {
    		
    		DatabaseEntry searchData = new DatabaseEntry(in_data.getBytes());
    		DatabaseEntry foundData = new DatabaseEntry();
    		DatabaseEntry returnKeyByte = new DatabaseEntry();
    		boolean amIinRange = false;
			myCursor = my_table.openCursor(null, null);
			
			while(myCursor.getNext(returnKeyByte, foundData, LockMode.DEFAULT)==OperationStatus.SUCCESS)
			{
				if (searchData.equals(foundData)){
					amIinRange = true;
					returnKey = new String(returnKeyByte.getData(), "UTF-8");
					writeAnswers(returnKey, in_data);
					recordCount++;
				}
				else
					if(amIinRange == true)
						break;
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
//    public void getKey(String in_data){
//    	/*Performs basic search for given key*/
//    	DatabaseEntry searchKey = new DatabaseEntry(in_data.getBytes());
//    	DatabaseEntry returnDataByte = new DatabaseEntry();
//    	Cursor myCursor = null;
//    	int recordsCount = 0;
//    	try{
//    		if(index.get(null, searchKey, returnDataByte, LockMode.DEFAULT)==OperationStatus.SUCCESS)
//    		{
//    			String returnData = new String(returnDataByte.getData(), "UTF-8");
//    			System.out.println("One key/data pair retrieved.");
//    			recordsCount++;
//    			
//    			writeAnswers(returnData, in_data);
//    			/*Add multiple values*/
//    			myCursor = index.openCursor(null, null);
//    			while(myCursor.getNextDup(searchKey, returnDataByte, LockMode.DEFAULT) == OperationStatus.SUCCESS){
//    				returnData = new String(returnDataByte.getData(), "UTF-8");
//    				writeAnswers(returnData, in_data);
//    				recordsCount++;
//    			}
//    			myCursor.close();
//    			System.out.println("There were " + String.valueOf(recordsCount) + "key/value pairs found.");
//    		}
//    		else{
//    			System.out.println("Zero key/data pairs retrieved.");
//    		}
//    	}
//    	catch(Exception e){
//    		e.printStackTrace();
//    	}finally{
//    		if (myCursor!=null)
//			{
//				try {
//					myCursor.close();
//				} catch (DatabaseException e) {
//					e.printStackTrace();
//				}
//			}
//    	}
//    }

	@Override
    public void getRange(String start, String end)
    {
		/*Similar method to getKey, add matches to ArrayList until start=end*/
    	String temp;
    	String tempK;
    	int recordCount = 0;
    	try {
    		DatabaseEntry foundData = new DatabaseEntry();
    		DatabaseEntry returnKeyByte = new DatabaseEntry();
			myCursor = my_table.openCursor(null, null);
			
			
			while(myCursor.getNext(returnKeyByte, foundData, LockMode.DEFAULT)==OperationStatus.SUCCESS)
			{
				if(super.inRange(start, end, returnKeyByte.getData()))
				{
					recordCount++;
					tempK = new String(returnKeyByte.getData(), "UTF-8");
					temp = new String(foundData.getData(), "UTF-8");
				
					super.writeAnswers(tempK, temp);
				}
				
			}
			
			System.out.println("There were " + recordCount + "key/value pairs found.");

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
	
	@Override
    public void closeDB(){
    	try {
			my_table.close();
			index.close();
		} catch (DatabaseException e) {
			e.printStackTrace();
			System.out.println("Error closing Database!");
		}
    }
}
