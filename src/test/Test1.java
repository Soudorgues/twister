package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.json.JSONException;
import org.json.JSONObject;

import services.TwisterDB;

public class Test1 {
	public static void main(String [] args) throws ClassNotFoundException, SQLException {
		GregorianCalendar c = new GregorianCalendar();
		
		/* Ajout de message */
		/* yrdy */
		//TwisterDB.addMessage(2, c.getTime(), "message1");
		//TwisterDB.addMessage(2, c.getTime(), "message2");
		//TwisterDB.addMessage(3, c.getTime(), "message3");
		
		//TwisterDB.deleteMessage(2, "waRmCXGEfMuSkozmvZGvicNBIFcEmimq");

		/* Connexion */
		//TwisterDB.insertConnexion("vinc", false);
		//TwisterDB.logout("wOldwiGBcoNmAojcnxGAgorlCcZVxfHB");
		//TwisterDB.deleteUser("tete");
/*
		List<Integer> a = new ArrayList<Integer> ();
		a.add(2);
		a.add(2);
		a.add(3);
		List<JSONObject> json = TwisterDB.getMessage(a);
		for (JSONObject j : json) {
			try {
				System.out.println(j.getString("content"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
*/
		/* Message User */
		
		List<JSONObject> json = TwisterDB.listMessageUser("vinc");
		for (JSONObject j : json) {
			try {
				System.out.println(j.getString("content"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//TwisterDB.addtoBDUser("vinc", "Vidal", "Vincent", "pwd123");
		//TwisterDB.insertConnexion("amd", true);
	}

		/*
		TwisterDB.addtoBDUser("amd", "Bah", "auezg", "pas123");
		System.out.println("success");
		if (TwisterDB.userExists("amd")) {
			System.out.println("amd existe");
			TwisterDB.insertConnexion("amd", "false");
			System.out.println("session success");
			if (TwisterDB.isRoot("amd")) {
				System.out.println("Root");
			}
			if (TwisterDB.checkPassword("amd", "pas123")) {
				System.out.println("OK");
			}
		}
	}
*/
}
