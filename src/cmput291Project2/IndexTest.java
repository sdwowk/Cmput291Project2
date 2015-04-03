package cmput291Project2;

public class IndexTest extends FileTest {
	
	private static final String INDEX_TABLE;
	private static Database my_table;
	
	public IndexTest(){
		INDEX_TABLE = super.getPath();
		my_table = super.getDB();
	}
	
	@Override
	public void createDB() {
		// TODO Auto-generated method stub

	}

}
