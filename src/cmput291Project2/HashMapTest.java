package cmput291Project2;
import java.util.Random;

import com.sleepycat.db.*;

public class HashMapTest implements FileTest {
	//I'm not 100% sure we should be giving our btree_table and hash_table separate names
	//inside of /tmp/sdwowk_mstrong_db/ - if key retrieval / database destruction uses 
	//the same commands, keeping the table name the same across db types may
	//make our work simpler in the long run
	private static final String HASH_TABLE = "/tmp/sdwowk_mstrong_db/Data_Table";

	public void createDB() {
		try{	
			// Create the database object.
		    // There is no environment for this simple example.
		    DatabaseConfig dbConfig = new DatabaseConfig();
		    dbConfig.setType(DatabaseType.HASH);
		    dbConfig.setAllowCreate(true);
		    Database my_table = new Database(HASH_TABLE, null, dbConfig);
		    System.out.println(HASH_TABLE + " has been created");
	
		    /* populate the new database with NO_RECORDS records */
		    //I think we can reuse populateTable, key/data insertion seems to be the same..
		    BinaryTreeTest.populateTable(my_table);
		    System.out.println("100 000 records inserted into" + HASH_TABLE);
		}catch(Exception e){
			System.err.println("Error Creating Database");
			System.err.println(e.getMessage());
		}
	}

}
