package cmput291Project2;

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
		    FileTest.populateTable(my_table);
		    System.out.println("100 000 records inserted into" + BINARY_TABLE);
		    super.setDB(my_table);
		}catch(Exception e){
			System.err.println("Error Creating Database");
			System.err.println(e.getMessage());
		}
	}
	
}
