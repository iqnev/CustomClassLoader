package jarclassloader;

import simpleclassloader.IQLoader;

public class TestJarLoader {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		 ClassLoader cl = new IQJarClassLoader();
		 Object parser= cl.loadClass("JSONParser").newInstance();
		 
		 String s="[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
		 Object obj=parser.parse(s);

	}

}
