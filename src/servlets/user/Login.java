package servlets.user;

import java.sql.SQLException;

import services.*;
import serviceTools.*;

import org.json.JSONException;
import org.json.JSONObject;
// foo
public class Login {
	public Login() {}
	
	public static JSONObject login(String user, String mdp) throws ClassNotFoundException, SQLException {
		JSONObject ret = null;
		boolean root = TwisterDB.isRoot(user);
		String key = null;
		
		if (! TwisterDB.userExists(user)) {
			ret = ServiceRefused.serviceRefused("user doesn't exists", 1000);
			return ret;
		}
		if (! TwisterDB.checkPassword(user, mdp)) {
			ret = ServiceRefused.serviceRefused("wrong password", 1000);
			return ret;
		}
		key = TwisterDB.insertConnexion(user, root);
		try {
			ret = new JSONObject();
			ret.put("status", "OK");
			ret.put("key", key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}
}
