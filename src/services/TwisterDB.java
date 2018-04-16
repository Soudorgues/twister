package services;

import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;

import com.mongodb.*;
import com.mongodb.util.JSON;

import bd.DBStatic.*;

public class TwisterDB {
	public TwisterDB() {}
	
	public static Connection getMySQLConnection() throws ClassNotFoundException {
		Connection c = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection("jdbc:mysql://"+bd.DBStatic.mysql_host+"/"+bd.DBStatic.mysql_db, bd.DBStatic.mysql_username, bd.DBStatic.mysql_password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.print("Exception : ");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return c;
	}
	
	public static boolean userExists(String login) throws ClassNotFoundException, SQLException {
		Connection c = getMySQLConnection();
		Statement s  = c.createStatement();
		String query = "SELECT login FROM login WHERE login='" + login + "'";
		
		ResultSet res = s.executeQuery(query);
		boolean exist = res.next();
		res.close();
		s.close();
		c.close();
		return exist;
	}
	public static boolean idExists(int id) throws ClassNotFoundException, SQLException {
		Connection c = getMySQLConnection();
		Statement s  = c.createStatement();
		String query = "SELECT login FROM login WHERE id=" + id;
		
		ResultSet res = s.executeQuery(query);
		boolean exist = res.next();
		res.close();
		s.close();
		c.close();
		return exist;
	}
	
	public static void deleteUser(String login) throws ClassNotFoundException, SQLException{
		Connection c = getMySQLConnection();
		String query = "DELETE FROM login where login='"+login+"'";
		
		Statement st = c.createStatement();
		st.executeUpdate(query);
		st.close();
		c.close();
	}
		
	public static void addtoBDUser(String login, String name, String fname, String pwd) throws ClassNotFoundException, SQLException {
		Connection c = getMySQLConnection();
		if (userExists(login)) {
			System.out.println("User already exists");
		} else {
			String query = 
				"INSERT INTO login(login,pwd,nom,prenom) VALUES ('" + login + "',"
										+ "PASSWORD('"  + pwd   + "'),'" +
														 name  + "','" +
														 fname + "')";
			Statement s = c.createStatement();
			System.out.println(query);
			s.executeUpdate(query);
			s.close();
			c.close();
		}
	}
	
	public static void addFriend(String login1, String login2) throws ClassNotFoundException, SQLException {
		Connection c = getMySQLConnection();
		if (!userExists(login)) {
			System.out.println("User does not exist");
		} else {
			/* Réccuperer dernière session non expirée de l'utilisateur actif */
			/* SELECT id FROM login WHERE login=login1 */
			/* SELECT id FROM login WHERE login=login1 */
			
			String query = "INSERT INTO friend(userId, friendId, time) VALUES (userid, friendId, NOW());";
			Statement s = c.createStatement();
			s.executeUpdate(query);
			s.close();
			c.close();
		}
	}
	
  	public static boolean checkPassword(String login, String mdp) throws SQLException, ClassNotFoundException {
		Connection c = getMySQLConnection();
		Statement lecture = c.createStatement();
		String query = "SELECT * FROM login WHERE login='" + login + "'";
		ResultSet cursor = lecture.executeQuery(query);
		
		boolean retour;
		if (!cursor.next())
			retour=false;
		else
			retour=cursor.getString("pwd").equals(mdp);
		
		lecture.close();
		c.close();
		return retour;
	}
	
	public static String insertConnexion(String user, boolean root) throws ClassNotFoundException, SQLException {
		Connection c = getMySQLConnection();
		Statement s  = c.createStatement();
		if (!userExists(user)) {
			System.out.println("User does not exist");
			s.close();
			c.close();
			return null;
		}
		int id_user = 0;
		String query = "SELECT id FROM login WHERE login='"+ user + "'";
		ResultSet res = s.executeQuery(query);
		while (res.next()) {
			id_user = res.getInt("id");
		}
		String clef = generate_key();
		query = "INSERT INTO tw_session(idUser, time, clef, isRoot, expired) VALUES (" + id_user + "," + "NOW(),'" + clef + "'," + bool2string(root) +", 0)";
		s.executeUpdate(query);
		s.close();
		c.close();
		return clef;
	}
	
	public static boolean logout(String key) throws ClassNotFoundException, SQLException {
		Connection c = getMySQLConnection();
		Statement s  = c.createStatement();
		String query = "UPDATE tw_session SET expired=1 WHERE clef='"+ key +"'";
		s.executeUpdate(query);
		s.close();
		c.close();
		return true;
	}
	
	public static boolean isRoot(String user) throws ClassNotFoundException, SQLException {
		Connection c = getMySQLConnection();
		Statement s  = c.createStatement();
		int id_user = 0;
		String query = "SELECT id FROM login WHERE login='"+ user + "'";
		ResultSet res = s.executeQuery(query);
		while (res.next()) {
			id_user = res.getInt("id");
		}
		query = "SELECT isRoot FROM tw_session WHERE idUser=" + id_user;
		res = s.executeQuery(query);
		boolean ret = false;
		while (res.next()) {
			ret = res.getBoolean("isRoot") == true;
		}
		s.close();
		c.close();
		return ret;
	}
	
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
		dbo.put("idmessage", generate_key());
		message.insert(dbo);
	}
	
	public static void deleteMessage(int userid, String messageid) {
		DBCollection message = getCollection("message");
		message.remove(new BasicDBObject().append("idmessage", messageid).append("user_id", userid));
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
	
	public static String generate_key() {
		String key = "";
		char c;
		for(int i=0; i<32; i++) {
			Random r = new Random();
			if(Math.random() < 0.5) 
				c = (char)(r.nextInt(26) + 'A');
			else
				c = (char)(r.nextInt(26) + 'a');
			key += c;			
		}
		return key;		
	}
	
	public static List<JSONObject> listMessageUser(String login) throws ClassNotFoundException, SQLException {
		Connection c = getMySQLConnection();
		Statement s  = c.createStatement();
		List<Integer> users = new ArrayList<> ();
		String query = "SELECT id FROM login WHERE login='" + login + "'";
		
		ResultSet res = s.executeQuery(query);
		while (res.next()) {
			users.add(res.getInt("id"));
		}
		s.close();
		c.close();
		return getMessage(users);
	}
	
	private static String bool2string(boolean b) {
		return (b ? "true" : "false");
	}
}


