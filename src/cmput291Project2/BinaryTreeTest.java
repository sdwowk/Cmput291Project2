package cmput291Project2;
import com.sleepycat.db.*;

public class BinaryTreeTest implements FileTest {
	private static final String BINARY_TABLE = "/tmp/sdwowk_db/Binary_Table";
	private static final int RECORD_NUM = 10000;
	/* This is modified from the sample code written by Dr. Li Yuan*/
	public void createDB() {
		
		// Create the database object.
	    // There is no environment for this simple example.
	    DatabaseConfig dbConfig = new DatabaseConfig();
	    dbConfig.setType(DatabaseType.BTREE);
	    dbConfig.setAllowCreate(true);
	    Database my_table = new Database(BINARY_TABLE, null, dbConfig);
	    System.out.println(BINARY_TABLE + " has been created");

	    /* populate the new database with NO_RECORDS records */
	    populateTable(my_table,RECORD_NUM);
	    System.out.println("1000 records inserted into" + BINARY_TABLE);


	    
	}

}
