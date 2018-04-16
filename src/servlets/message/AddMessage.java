package servlets.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class AddMessage extends HttpServlet {
	public AddMessage() {}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GregorianCalendar c = new GregorianCalendar();
		String userid = request.getParameter("userid");
		String message = request.getParameter("msg");
		
		JSONObject ret = null;
		
		try {
			ret = services.Message.addMessage(Integer.parseInt(userid), c.getTime(), message);
		} catch (Exception e) {
			ret = serviceTools.ServiceRefused.serviceRefused("Erreur lors de la création du message", 1000);
		}
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		out.print(ret.toString());
	}
}
