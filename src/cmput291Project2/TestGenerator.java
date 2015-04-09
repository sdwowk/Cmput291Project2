package cmput291Project2;

class TestGenerator {

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
