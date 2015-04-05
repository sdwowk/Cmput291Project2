package cmput291Project2;

import java.util.Random;

public class IndexTest extends FileTest {
	
	private static final String INDEX_TABLE;
	private static final String DATA_TABLE;
	private static Database my_table;
	private static Database index;
	private static int RECORD_NUM = 100000;
	
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
		}catch(Exception e){
			System.err.println("Error Creating Database");
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	static void populateTable(Database index){
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
		
				/* to insert the key/data pair into the database, index file swaps keys and data from the database */
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

}
