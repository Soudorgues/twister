package services;

public class TwisterDB {
	public TwisterDB() {}
	
	public static boolean userExists(String login) { return false; }
	public static void addtoBDUser(String login, String name, String frame, String pwd) { return; }
	public static boolean checkPassword(String user, String mdp) { return false; }
	public static String insertConnexion(String user, boolean root) { return "Clef"; }
	public static boolean isRoot(String user) { return false; }
}
