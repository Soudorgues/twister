package services;

import java.sql.SQLException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import serviceTools.ServiceRefused;

public class Message {
	public Message() {}
	
	public static JSONObject addMessage(int id, Date time, String message) throws ClassNotFoundException, SQLException {
		JSONObject ret = new JSONObject();
		
		if (!services.TwisterDB.idExists(id)) {
			ret = ServiceRefused.serviceRefused("user doesn't exist", 1000);
			return ret;
		}
		else {
			try {
				ret.put("status", "OK");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			services.TwisterDB.addMessage(id, time, message);
			return ret;
		}
	}
}
