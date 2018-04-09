package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import serviceTools.ServiceRefused;

public class Friend {
	public Friend() {}
	
	public static JSONObject createFriend(String login, int friendId) throws ClassNotFoundException, SQLException {
		JSONObject ret = new JSONObject();
		
		if (!services.TwisterDB.userExists(login)) {
			ret = ServiceRefused.serviceRefused("user does not exist", 1000);
			return ret;
		}
		else {
			try {
				ret.put("status", "OK");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			services.TwisterDB.addFriend(login, friendId);
			return ret;
		}
	}
}
