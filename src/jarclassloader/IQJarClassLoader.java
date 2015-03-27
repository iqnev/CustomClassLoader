package jarclassloader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class IQJarClassLoader extends ClassLoader {
	private String jarFile = "/Users/iqnev/Downloads/workspace/jarclassloader/json-simple-1.1.1.jar"; //Path to the jar file
    private Hashtable classes = new Hashtable(); 

    /**
     * This constructor is used to set the parent ClassLoader
     */
    public IQJarClassLoader() {
        super(IQJarClassLoader.class.getClassLoader()); 
    }

    public Class loadClass(String className) throws ClassNotFoundException {
        return findClass(className);
    }
    
    /**
     * checking if class of jar file is exist or is load
     * @throws ClassNotFoundException 
     */
    public Class findClass(String className) throws ClassNotFoundException {
        byte classByte[];
        Class result = null;

        result = (Class) classes.get(className); 
        if (result != null) {
            return result;
        }
        try {  
            return findSystemClass(className.replace('/', '.'));  
        } catch (Exception e) {  
        	 System.err.println("        >>>>>> Not a system class.");
        } 
        try {
        	
            JarFile jar = new JarFile(jarFile);
     
            JarEntry entry = jar.getJarEntry(className + ".class");
            if (entry == null) {
            	 System.err.println(className + ".class");
            }
            byte[] buffer = new byte[4096];
            InputStream is = (InputStream) jar.getInputStream(entry);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextValue = is.read();
            
            int read = 0; 
            while ( (read = is.read(buffer)) != -1 ) {
            	byteStream.write(buffer, 0, read);
            }
         
            classByte = byteStream.toByteArray();
            System.out.println(classByte.length);
            result = defineClass(className, classByte, 0, classByte.length);
            if(result == null) {
            	 System.err.println(className + ".class");
            }
            classes.put(className, result);
            return result; 
        } catch (Exception e) {
        	throw new ClassNotFoundException(className, e);
        }
		
    }

}
