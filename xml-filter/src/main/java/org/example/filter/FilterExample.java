package org.example.filter;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*")
public class FilterExample implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		if ("POST".equalsIgnoreCase(req.getMethod())) {

			MyRequestWrapper wrapper = new MyRequestWrapper((HttpServletRequest)request);
			String content = wrapper.getReader().lines().collect(Collectors.joining(System.lineSeparator())).trim();
			System.out.println("Content: \n" + content);

			if (content != null && !content.trim().isEmpty()) {

				if (content.startsWith("<?xml") && content.endsWith(">")) {
					chain.doFilter(wrapper, response);
				} else {
					((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST);;
				}

			}

			else {
				((HttpServletResponse) response).sendError(HttpServletResponse.SC_BAD_REQUEST);;
			}

		}

	}

}
