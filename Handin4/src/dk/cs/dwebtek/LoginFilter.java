package dk.cs.dwebtek;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {

	ServletContext context;
	int counter;
	
    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		Object loggedIn = req.getSession().getAttribute("loggedIn");
			
		if(loggedIn == null) {
			System.out.println("NOT LOGGED IN");
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect("index.html");
		}
		else if ((boolean)loggedIn) {
			chain.doFilter(request, response);
		} 
		else {
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect("products.html");
			System.out.println("NOT VALID USER - NOT ADMIN");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		context = fConfig.getServletContext();
	}

}
