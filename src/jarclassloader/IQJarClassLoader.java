package jarclassloader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class IQJarClassLoader extends ClassLoader {
	private URL jarFile = getClass().getClassLoader().getResource("json-simple-1.1.1.jar");
    private Hashtable classes = new Hashtable(); 

    /**
     * This constructor is used to set the parent ClassLoader
     */
    public IQJarClassLoader() {
        super(IQJarClassLoader.class.getClassLoader()); 
    }

    public Class loadClass(String className) throws ClassNotFoundException {
        return findClass(className, true);
    }
    
    /**
     * checking if class of jar file is exist or is load
     * @throws ClassNotFoundException 
     */
    public Class findClass(String className, boolean resolveIt) throws ClassNotFoundException {
        byte classByte[];
        Class result = null;
        
        /* Check our local cache of classes */
        result = (Class) classes.get(className); 
        if (result != null) {
            return result;
        }

        try {
        	
            JarFile jar = new JarFile(this.jarFile.getPath().toString());
            Enumeration<JarEntry> jarIterator = jar.entries();
            JarEntry entry;
            // search in jar
         /*   while (jarIterator.hasMoreElements()) {

             entry = jarIterator.nextElement();
             System.out.println(entry.getName());
            
            } */
          
            entry = jar.getJarEntry(className + ".class");
            if (entry == null) {          	
            	throw new ClassNotFoundException(className + ".class");
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
            
            result = defineClass(className, classByte, 0, classByte.length);
            if(result == null) { 
            	 System.err.println(className + ".class");
            } 
            
            if(resolveIt) {
            	resolveClass(result);
            }
            
            classes.put(className, result); 
            return result; 
        } catch (Exception e) {
        	throw new ClassNotFoundException(className, e);
        }
		
    }

}
