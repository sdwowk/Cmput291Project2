package cmput291Project2;

import java.io.UnsupportedEncodingException;

import com.sleepycat.db.*;

public class HashMapTest extends FileTest {
	//I'm not 100% sure we should be giving our btree_table and hash_table separate names
	//inside of /tmp/sdwowk_mstrong_db/ - if key retrieval / database destruction uses 
	//the same commands, keeping the table name the same across db types may
	//make our work simpler in the long run
	private static String HASH_TABLE;
	private static Database my_table;
	
	public HashMapTest(){
		HASH_TABLE = super.getPath();
		my_table = super.getDB();
	}
	
	public void createDB() {
		try{	
			// Create the database object.
		    DatabaseConfig dbConfig = new DatabaseConfig();
		    dbConfig.setType(DatabaseType.HASH);
		    dbConfig.setAllowCreate(true);
		    my_table = new Database(HASH_TABLE, null, dbConfig);
		    System.out.println(HASH_TABLE + " has been created");
	
		    /* populate the new database RECORD_NUM records */
		    //I think we can reuse populateTable, key/data insertion seems to be the same..
		    this.populateTable(my_table);
		    System.out.println("100 000 records inserted into" + HASH_TABLE);
		    
		    super.setDB(my_table);
		}catch(Exception e){
			System.err.println("Error Creating Database");
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void getRange(String start, String end)
    {
    	String temp;
    	String tempK;
    	Cursor myCursor = null;
    	int recordCount = 0;
    	try {
    		DatabaseEntry foundData = new DatabaseEntry();
    		DatabaseEntry returnKeyByte = new DatabaseEntry();
			myCursor = my_table.openCursor(null, null);
			
			
			while(myCursor.getNext(returnKeyByte, foundData, LockMode.DEFAULT)==OperationStatus.SUCCESS)
			{

				if(inRange(start, end, returnKeyByte.getData()))
				{
					recordCount++;
					tempK = new String(returnKeyByte.getData(), "UTF-8");
					temp = new String(foundData.getData(), "UTF-8");
					writeAnswers(tempK,temp);				
				}

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
	
	@Override
    public void getData(String in_key){
    	/*Performs basic search for given key*/
    	int recordCount = 0;
    	DatabaseEntry searchKey = new DatabaseEntry(in_key.getBytes());
    	DatabaseEntry returnDataByte = new DatabaseEntry();
    	Cursor myCursor = null;
    	try{
    		
    		if(my_table.get(null, searchKey, returnDataByte, LockMode.DEFAULT)==OperationStatus.SUCCESS)
    		{
    			String returnData = new String(returnDataByte.getData(), "UTF-8");
    			recordCount++;
    			writeAnswers(in_key, returnData);
    			myCursor = my_table.openCursor(null, null);
    			while(myCursor.getNext(searchKey, returnDataByte, LockMode.DEFAULT) == OperationStatus.SUCCESS){
    				if(searchKey.equals(in_key)){
    					returnData = new String(returnDataByte.getData(), "UTF-8");
    					recordCount++;
    					writeAnswers(in_key, returnData);
    				}
    			}
    			myCursor.close();
    		}
    		System.out.println("There were " + recordCount + " data entries retrieved");
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    }

}
