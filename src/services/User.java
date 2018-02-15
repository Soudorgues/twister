package services;

import serviceTools.ServiceRefused;
import services.*;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	public User() {}
	
	public static JSONObject createUser(String login, String name, String frame, String pwd) {
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
}
