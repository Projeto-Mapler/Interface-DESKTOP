package resources.css;

public class CssMaster {
		
	public static String menuBar() {
		return CssMaster.class.getResource("menubar.css").toExternalForm();
	}
}
