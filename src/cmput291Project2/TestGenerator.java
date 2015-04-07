package cmput291Project2;
import java.util.HashMap;

class TestGenerator {
	
	private static HashMap<String, FileTest> types;

	public static FileTest getFileTest(String type){
		try{
			//load correct File System
			if(type.toLowerCase().trim().equals("btree")){
				return new BinaryTreeTest();
			}else if(type.toLowerCase().trim().equals("hash")){
				return new HashMapTest();
			}else if(type.toLowerCase().trim().equals("indexfile")){
				return new IndexTest();
			}
			
			return null;
		}catch(Exception e){
			return null;	
		}

		
	}

}
