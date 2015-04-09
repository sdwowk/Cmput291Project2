package cmput291Project2;
import java.io.UnsupportedEncodingException;

import com.sleepycat.db.*;

public class BinaryTreeTest extends FileTest {
	private static String BINARY_TABLE;
	private static Database my_table;
	
	public BinaryTreeTest(){
		BINARY_TABLE = super.getPath();
		my_table = super.getDB();
		
	}
	
	/*** createDB and populateTable has been modified from the sample code given by Dr. Li Yuan ***/
	public void createDB() {
		try{	
			// Create the database object.
		    DatabaseConfig dbConfig = new DatabaseConfig();
		    dbConfig.setType(DatabaseType.BTREE);
		    dbConfig.setAllowCreate(true);
		    my_table = new Database(BINARY_TABLE, null, dbConfig);
		    System.out.println(BINARY_TABLE + " has been created");
	
		    
		    /* populate the new database with RECORD_NUM records, specified in  */
		    this.populateTable(my_table);
		    System.out.println("100 000 records inserted into" + BINARY_TABLE);
		    super.setDB(my_table);
		}catch(Exception e){
			System.err.println("Error Creating Database");
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void getRange(String start, String end)
    {
    	/*Similar method to getKey, add matches to ArrayList until start=end*/
    	String temp;
    	String tempK;
    	Cursor myCursor = null;
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
					recordCount++;
					tempK = new String(returnKeyByte.getData(), "UTF-8");
					temp = new String(foundData.getData(), "UTF-8");
					writeAnswers(tempK,temp);				
				}
				else
					if(amIinRange == true)
						break;
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
}
