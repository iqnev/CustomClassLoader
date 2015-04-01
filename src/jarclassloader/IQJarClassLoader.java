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

	private static final String DIR = System.getProperty("user.dir")
			+ System.getProperty("file.separator") + "res"
			+ System.getProperty("file.separator");
	private Hashtable classes 		= new Hashtable();
	private String jarFile;

	/**
	 * Default Constructor.
	 * @param _jarFile is the name of a jar file.
	 */
	public IQJarClassLoader(String _jarFile) {
		super(IQJarClassLoader.class.getClassLoader());
		this.jarFile = IQJarClassLoader.DIR + _jarFile;

	}
	
	public Class loadClass(String className) throws ClassNotFoundException {
		return findClass(className, true);
	}

	/**
	 * checking if class of jar file is exist or is load
	 * 
	 * @throws ClassNotFoundException
	 */
	public Class findClass(String className, boolean resolveIt)
			throws ClassNotFoundException {
		byte classByte[];
		Class result = null;

		/* Check our local cache of classes */
		result = (Class) classes.get(className);
		if (result != null) {
			return result;
		}

		try {
			JarFile jar = new JarFile(this.jarFile);
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
			while ((read = is.read(buffer)) != -1) {
				if (read > 0) {
					byteStream.write(buffer, 0, read);
				}
			}

			classByte = byteStream.toByteArray();

			result = super.defineClass(className, classByte, 0,
					classByte.length, null);
			if (result == null) {
				System.err.println(className + ".class");
			}
			
			if (resolveIt) {
				resolveClass(result);
			}
			classes.put(className, result);

			return result;
		} catch (Exception e) {
			return super.findSystemClass(className);
		}
	}

}
