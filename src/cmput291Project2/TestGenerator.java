package cmput291Project2;
import java.util.HashMap;

class TestGenerator {
	
	private static HashMap<String, FileTest> types;

	public static FileTest getFileTest(String type){
		try{
			load();
			
			if(types.containsKey(type)){
				return types.get(type);
			}
			
			return null;
		}catch(Exception e){
			return null;	
		}

		
	}

	private static void load() {
		
		types.put("btree", new BinaryTreeTest());
		types.put("hash", new HashMapTest());
		types.put("index", new IndexTest());
	}
}
