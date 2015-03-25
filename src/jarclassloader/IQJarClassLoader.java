package jarclassloader;

import java.io.ByteArrayOutputStream;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.omg.CORBA.portable.InputStream;

public class IQJarClassLoader extends ClassLoader {
	private String jarFile = "/Users/iqnev/Downloads/workspace/JavaExamples/src/jarclassloader/json-simple-1.1.1.jar "; //Path to the jar file
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
     */
    public Class findClass(String className) {
        byte classByte[];
        Class result = null;

        result = (Class) classes.get(className); 
        if (result != null) {
            return result;
        }

        try {
            return findSystemClass(className);
        } catch (Exception e) {
        	e.printStackTrace();
        }

        try {
            JarFile jar = new JarFile(jarFile);
            JarEntry entry = jar.getJarEntry(className + ".class");
            InputStream is = (InputStream) jar.getInputStream(entry);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextValue = is.read();
            byte[] buffer = new byte[4096];
            int read = 0;
            while ( (read = is.read(buffer)) != -1 ) {
            	byteStream.write(buffer, 0, read);
            }
         
            classByte = byteStream.toByteArray();
            result = defineClass(className, classByte, 0, classByte.length, null);
            classes.put(className, result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
