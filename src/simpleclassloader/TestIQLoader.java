package simpleclassloader;

import java.io.File;

public class TestIQLoader {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		String qPackage = "CustomClassLoader.";
        String baseDir = ".." + File.separator + "CustomClassLoader" + File.separator;
        System.out.println(qPackage);
        System.out.println(baseDir);
        ClassLoader cl = new IQLoader(qPackage, baseDir);
        
        Object test1 = cl.loadClass("CustomClassLoader.TestClass1").newInstance();
        
        System.out.println("test1: " + test1.toString());
	}

}
