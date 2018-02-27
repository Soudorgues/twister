package services;

import java.sql.SQLException;

import serviceTools.ServiceRefused;
import services.*;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	public User() {}
	
	public static JSONObject createUser(String login, String name, String frame, String pwd) throws ClassNotFoundException, SQLException {
		JSONObject ret = new JSONObject();
		
		if (services.TwisterDB.userExists(login)) {
			ret = ServiceRefused.serviceRefused("user exists", 1000);
			return ret;
		}
		else {
			try {
				ret.put("status", "OK");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			services.TwisterDB.addtoBDUser(login, name, frame, pwd);
			return ret;
		}
	}
	public static JSONObject login(String user, String mdp) throws ClassNotFoundException, SQLException {
		JSONObject ret = null;
		boolean root = TwisterDB.isRoot(user);
		String key = null;
		
		if (! TwisterDB.userExists(user)) {
			ret = ServiceRefused.serviceRefused("user doesn't exists", 1000);
			return ret;
		}
		/*
		if (! TwisterDB.checkPassword(user, mdp)) {
			ret = ServiceRefused.serviceRefused("wrong password", 1000);
			return ret;
		}
		*/
		key = TwisterDB.insertConnexion(user, root);
		try {
			ret = new JSONObject();
			ret.put("login", user);
			ret.put("key", key);
			ret.put("status", "OK");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
}
