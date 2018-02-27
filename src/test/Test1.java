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
		//TwisterDB.addMessage(13, c.getTime(), "mesa");
		List<Integer> a = new ArrayList<Integer> ();
		a.add(12);
		a.add(13);
		List<JSONObject> json = TwisterDB.getMessage(a);
		for (JSONObject j : json) {
			try {
				System.out.println(j.getString("content"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	*/
		
		TwisterDB.addtoBDUser("vinz", "Vidal", "Vincent", "password123");
		System.out.println("success");
		if (TwisterDB.userExists("vinz")) {
			System.out.println("vinz existe");
			TwisterDB.insertConnexion("vinz", false);
			System.out.println("session success");
			if (TwisterDB.isRoot("vinz")) {
				System.out.println("Root");
			}
			if (TwisterDB.checkPassword("vinz", "password123")) {
				System.out.println("OK");
			}
		}
	}
		
}
