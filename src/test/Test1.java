package test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import services.TwisterDB;

public class Test1 {
	public static void main(String [] args) throws ClassNotFoundException, SQLException {
		/*
		GregorianCalendar c = new GregorianCalendar();
		TwisterDB.addMessage(13, c.getTime(), "mes");
		TwisterDB.addMessage(14, c.getTime(), "msa");
		TwisterDB.addMessage(15, c.getTime(), "m");
		
		List<Integer> a = new ArrayList<Integer> ();
		a.add(13);
		a.add(14);
		a.add(15);
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
		List<JSONObject> json = TwisterDB.listMessageUser("amd");
		for (JSONObject j : json) {
			try {
				System.out.println(j.getString("content"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
