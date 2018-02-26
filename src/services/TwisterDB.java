package services;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.*;
import com.mongodb.util.JSON;

import bd.DBStatic.*;
import sun.rmi.transport.Connection;

public class TwisterDB {
	public TwisterDB() {}
	
	public static Connection getMySQLConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("Exception : ");
			System.err.println(e.getMessage());
		}
		Connection c = Driver.getConnection(bd.DBStatic.mysql_host,  bd.DBStatic.mysql_username, bd.DBStatic.mysql_password);
		return c;
	}
	
	public static boolean userExists(String login) {
		Connection c = getMySQLConnection();
		Statement s  = c.createStatement();
		String query = "SELECT login FROM login";
		
		ResultSet res = s.executeQuery(query);
		boolean exist = res.hasNext();
		res.close();
		s.close();
		c.close();
		return exist;
	}
	public static void addtoBDUser(String login, String name, String fname, String pwd) {
		Connection c = getMySQLConnection();
		if (userExists(login)) {
			System.out.println("User already exists");
		} else {
			String query = "INSERT INTO login VALUES (" + login + ","
										+ "PASSWORD("  + pwd   + ")," +
														 name  + "," +
														 fname + ")";
			Statement s = c.createStatement();
			s.executeUpdate(query);
			s.close();
			c.close();
		}
	}
	public static boolean checkPassword(String user, String mdp) { return false; }
	
	public static String insertConnexion(String user, boolean root) {
		GregorianCalendar gc = new GregorianCalendar();
		Connection c = getMySQLConnection();
		Statement s  = c.createStatement();
		if (!userExists(user)) {
			System.out.println("User doesn't exist");
			return null;
		}
		String id_user = null;
		String query = "SELECT id FROM login WHERE login='"+ user + "'";
		ResultSet res = s.executeQuery(query);
		while (s.next()) {
			id_user = res.getString("id");
		}
		query = "INSERT INTO tw_session VALUES (" + id_user + ",", gc.getTime() + ","
		
	}
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
	
	public static void deleteMessage(int messageid) {
		DBCollection message = getCollection("message");
		message.remove(new BasicDBObject().append("idmessage", messageid));
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
	
	public static List<JSONObject> listMessageUser(String login) {
		Connection c = getMySQLConnection();
		Statement s  = c.createStatement();
		List<Integer> users = new ArrayList<> ();
		String query = "SELECT id FROM login";
		
		ResultSet res = s.executeQuery(query);
		while (res.next()) {
			users.add(res.getInt("id"));
		}
		return getMessage(users);
	}
}




