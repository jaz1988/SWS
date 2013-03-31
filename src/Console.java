/*
 * Usage: Console.clr()
 */

public class Console {

	/*
	 * Load External DLL for clr Function
	 */
	static {
		System.loadLibrary("ConsoleClear");
	}

	public static native void clr();
}
