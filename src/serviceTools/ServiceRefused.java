
package serviceTools;

import org.json.JSONException;
import org.json.JSONObject;


public class ServiceRefused {
	public ServiceRefused () {}
	
	public static JSONObject serviceRefused(String m, int idError) {
		JSONObject response = new JSONObject();

		try {
			response.put("status", "KO");
			response.put("message", m);
			response.put("idError", idError);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
}
