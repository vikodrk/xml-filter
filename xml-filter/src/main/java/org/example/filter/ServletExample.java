package org.example.filter;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/test")
public class ServletExample extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Here is supose to be some logic");
		String content = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		System.out.println("Content: \n"+content);
		System.out.println("EUREKA!!");
	}

}
