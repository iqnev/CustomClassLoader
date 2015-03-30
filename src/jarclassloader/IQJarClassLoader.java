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
	//private URL jarFile = getClass().getClassLoader().getResource("json-simple-1.1.1.jar");
	private String jarFile = "/Users/iqnev/Downloads/workspace/jarclassloader/json-simple-1.1.1.jar";
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
        	
            //JarFile jar = new JarFile(this.jarFile.getPath().toString());
        	JarFile jar = new JarFile(this.jarFile);
            Enumeration<JarEntry> jarIterator = jar.entries();
            JarEntry entry;
            
            // search in jar
            entry = jar.getJarEntry(className.replace('.', '/') + ".class");
            if (entry == null) {          	
            	throw new ClassNotFoundException(className + ".class");
            }
            byte[] buffer = new byte[4096];
            InputStream is = (InputStream) jar.getInputStream(entry);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            
            int read = 0; 
            while ( (read = is.read(buffer)) != -1 ) {
            	if(read > 0) {
            		byteStream.write(buffer, 0, read);
            	}
            }
         
            classByte = byteStream.toByteArray();
           
            result =  super.defineClass(className, classByte, 0, classByte.length, null);
            if(result == null) { 
            	 System.err.println(className + ".class");
            } 
            
            if(resolveIt) {
            	resolveClass(result);
            }
            
            classes.put(className, result); 
            
            return result; 
        } catch (Exception e) {
        	
        	return super.findSystemClass(className);
   
        }
		
    }

}
