
package jarclassloader;

/**
 * @author iqnev
 *
 */
public class MyTestClassold implements MyLoader {

	@Override
	public void start() {
		System.out.println("MyTesClass!!");	
	}
	
	public void testMethod() {
		System.out.println("It's working!!");	
	}
}
