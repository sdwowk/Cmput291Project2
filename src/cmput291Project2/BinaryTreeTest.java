package cmput291Project2;
import java.util.Random;

import com.sleepycat.db.*;

public class BinaryTreeTest implements FileTest {
	private static final String BINARY_TABLE = "/tmp/sdwowk_mstrong_db/Data_Table";
	private static final int RECORD_NUM = 100000;
	private static Database my_table;
	
	/*** createDB and populateTable has been modified from the sample code given by Dr. Li Yuan ***/
	public void createDB() {
		try{	
			// Create the database object.
		    // There is no environment for this simple example.
		    DatabaseConfig dbConfig = new DatabaseConfig();
		    dbConfig.setType(DatabaseType.BTREE);
		    dbConfig.setAllowCreate(true);
		    my_table = new Database(BINARY_TABLE, null, dbConfig);
		    System.out.println(BINARY_TABLE + " has been created");
	
		    /* populate the new database with NO_RECORDS records */
		    populateTable(my_table);
		    System.out.println("100 000 records inserted into" + BINARY_TABLE);
		}catch(Exception e){
			System.err.println("Error Creating Database");
			System.err.println(e.getMessage());
		}
	}

	/*
     *  To pouplate the given table with nrecs records
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
}
