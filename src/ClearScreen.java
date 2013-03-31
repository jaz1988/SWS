import java.io.IOException;


public class ClearScreen {

	public static void clearscreen()
	{
		try {

			  if( System.getProperty( "os.name" ).startsWith( "Window" ) ) {
			     Runtime.getRuntime().exec("cls");
			  } else {
			     Runtime.getRuntime().exec("clear");
			  }


			} catch (IOException e) {

			  for(int i = 0; i < 1000; i++) {
			    System.out.println();
			  }

			}
	}
}
