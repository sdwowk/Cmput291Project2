package cmput291Project2;
import java.util.Random;

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
	
	

}
