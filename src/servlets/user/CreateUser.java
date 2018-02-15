package servlets.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class CreateUser extends HttpServlet {
	public CreateUser () { super(); }
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login"); 
		String name = request.getParameter("name");
		String frame = request.getParameter("frame");
		String pwd = request.getParameter("pwd");
		
		JSONObject ret = null;
		
		try {
			ret = services.User.createUser(login, name, frame, pwd);
		} catch (Exception e) {
			
		}
		
		PrintWriter out = response.getWriter();
		response.setContentType("plain/text");
		out.print(ret.toString());
	}
}
