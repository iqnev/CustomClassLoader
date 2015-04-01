package jarclassloader;

import java.net.URL;

import jarclassloader.MyLoader;

public class TestJarLoader {
	
	/**
	 * 
	 * @param args
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {

		ClassLoader cl = new IQJarClassLoader("TestJar.jar");
		MyLoader obj;
		obj = (MyLoader) cl.loadClass("jarclassloader.MyTestClass")
				.newInstance();

		obj.start();

	}

}
