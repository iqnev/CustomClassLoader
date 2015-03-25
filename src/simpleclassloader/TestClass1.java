package simpleclassloader;

public class TestClass1 {
	private static int count = 0;

    public String toString() {
        return String.valueOf("Test:" +(++count));
    }
}
