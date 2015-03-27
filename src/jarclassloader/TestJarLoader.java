package jarclassloader;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class TestJarLoader {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, ParseException {
		 ClassLoader cl = new IQJarClassLoader();
		 JSONParser parser= (JSONParser) cl.loadClass("org/json/simple/parser/JSONParser").newInstance();
		 
		 String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
		 Object obj=parser.parse(s);
		  JSONArray array=(JSONArray)obj;
		  System.out.println("======the 2nd element of array======");
		  System.out.println(array.get(1));
		  System.out.println(); 

	}

}
