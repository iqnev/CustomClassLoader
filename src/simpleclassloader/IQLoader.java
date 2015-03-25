package simpleclassloader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;

public class IQLoader extends ClassLoader {
	 private Hashtable classes = new Hashtable();
	 private String iPackage;
	 private String iBase;
	 
	/**
	 * This constructor is used to set iBase and iPackage
	 * @param iPackage
	 * @param iBase
	 */
	public IQLoader(String iPackage, String iBase) {
       this.iBase		= iBase;
       this.iPackage	= iPackage;
    }
	
	
	
	/**
	 * Every request for a class passes through this method
	 *  @param resolveIt Any referenced class should be loaded as well
	 *  @param className Full class name
	 */
	@SuppressWarnings("deprecation")
	public synchronized Class loadClass(String className, boolean resolveIt) throws ClassNotFoundException {
		Class result;
		byte classData[];
		
		/* Check our local cache of classes */ 
		result = (Class) classes.get(className);
		if(result != null) {
			return result;
		}
		
		if(!className.startsWith(iPackage)) {
			try {
				result = super.findSystemClass(className);
				return result;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		classData = getClassFromDataBase(className);
		if(classData == null) {
			throw new ClassNotFoundException(); 
		}
		
		result = defineClass(classData, 0, classData.length);
		if(result == null) {
			throw new ClassFormatError();
		}
		
		if(resolveIt) {
			resolveClass(result);
		}
		
		 classes.put(className, result);
		 
		 return result;
	}
	
	private byte[] getClassFromDataBase(String className) {
		byte result[];
		
		//convert package seperators  to directory seperators
		StringBuffer name = new StringBuffer(className);
        for (int i=0, n= name.length(); i < n; i++) {
            if (name.charAt(i) == '.') {
                name.setCharAt(i, File.separatorChar);
            }
        }
		
        className = name.append(".class").toString();
        System.out.println(className);
        try {
            FileInputStream fil = new FileInputStream("/Users/iqnev/Downloads/workspace/JavaExamples/src/CustomClassLoader/TestClass1.class");
            result = new byte[fil.available()];
            fil.read(result);
            return result;
        } catch (Exception e) {
            return null;
        }
		
	}
	
}
