package servlets.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.*;
import serviceTools.*;

import org.json.JSONException;
import org.json.JSONObject;
// foo
public class Login extends HttpServlet{
	public Login() {}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login"); 
		String pwd = request.getParameter("pwd");
		
		JSONObject ret = null;
		
		try {
			ret = services.User.login(login, pwd);
		} catch (Exception e) {
			ret = serviceTools.ServiceRefused.serviceRefused("Erreur de login", 1000);
		}
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		out.print(ret.toString());
	}
}
