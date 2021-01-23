package mapler;

public class IMGMaster {
	
	public static String getLink() {
		return IMGMaster.class.getResource("menubar.css").toExternalForm();
	}
	
}
