package dk.cs.dwebtek;
import java.io.IOException;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jdom2.JDOMException;

@ManagedBean(name="loginBean")
@ViewScoped
public class LoginServlet {
	private String _userName;
	private String _password;
	private String _adminName = "perle";
	private String _adminPass = "body";
	private boolean _IamAdmin = false;
	boolean isUsernameValid;
	boolean isPasswordValid;
	boolean validationComplete;
	private String r;
	private ArrayList<Customer> _customers;	
	private CloudHandler _cloud = new CloudHandler();
	
	public LoginServlet() throws IOException, JDOMException {
		_customers = _cloud.listCustomers();
		//_cloud.createCustomer("perle","body");
	}
	
	public String checkLogin() throws IOException, JDOMException {		
		for (Customer customer : _customers) {		
			System.out.println("in forech...");
			//if(customer.getName().equals(_adminName))
			//{			
				r = _cloud.login(_userName, _password);
				if (_cloud.responseCode == 200)
				{
					if(customer.getName().equals(_adminName))	{
						System.out.println("VALID USER? " + r);
						// SESSION OG SÆT ATTRIBUTE 
						FacesContext context = FacesContext.getCurrentInstance();  
						HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();  
						HttpSession httpSession = request.getSession(false);  
						httpSession.setAttribute("loggedIn", true);
						
						return "SUCCESSFUL ADMIN LOGIN";
					}
				}
			//}
			else {
				System.out.println("UNVALID USER?? RESPONSECODE: " + _cloud.responseCode);
				return "FAIL LOGIN";
			}
		}
		
		return "FAIL LOGIN";
	}
	

	public boolean getAdmin(){
		return _IamAdmin;
	}
	public String getuserName() {
		return _userName;
	}
	public void setuserName(String _userName) {
		this._userName = _userName;
	}
	public String getPassword() {
		return _password;
	}
	public void setPassword(String _password) {
		this._password = _password;
	}
}
