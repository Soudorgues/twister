package services;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.*;
import com.mongodb.util.JSON;

public class TwisterDB {
	public TwisterDB() {}
	
	public static boolean userExists(String login) { return false; }
	public static void addtoBDUser(String login, String name, String frame, String pwd) { return; }
	public static boolean checkPassword(String user, String mdp) { return false; }
	public static String insertConnexion(String user, boolean root) { return "Clef"; }
	public static boolean isRoot(String user) { return false; }
	
	public static DBCollection getCollection(String nom_collection) {
		DBCollection message = null;
		try {
			Mongo m = new Mongo("localhost");
			DB db = m.getDB("twistermg");
			message = db.getCollection(nom_collection);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}
	public static void addMessage(int userid, Date date, String msg) {
		DBCollection message = getCollection("message");
		BasicDBObject dbo = new BasicDBObject();
		dbo.put("user_id", userid);
		dbo.put("date", date);
		dbo.put("content", msg);
		message.insert(dbo);
	}
	
	public static List<JSONObject> getMessage(List<Integer> authors) {
		List<JSONObject> result = new ArrayList<> ();
		DBCollection message 	= getCollection("message");
		BasicDBObject query 	= new BasicDBObject();
		query.put("user_id", new BasicDBObject("$in", authors));
		DBCursor c = message.find(query);
		while (c.hasNext()) {
			DBObject obj = c.next();
			try {
				result.add(new JSONObject(JSON.serialize(obj)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}




