package dk.cs.dwebtek;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.jdom2.JDOMException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("shop")
public class ShopService {
    /**
     * Out Servlet session. We will need this for the shopping basket
     */
    HttpSession session;
    public ShopService(@Context HttpServletRequest servletRequest) {
        session = servletRequest.getSession();
    }
    
    private static int priceChange = 0;
    @GET
    @Path("items")
    public String getItems() {
        //You should get the items from the cloud server.
        //In the template we just construct some simple data as an array of objects
        JSONArray array = new JSONArray();

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("id", 1);
        jsonObject1.put("name", "Stetson hat");
        jsonObject1.put("price", 200 + priceChange);
        array.put(jsonObject1);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("id", 2);
        jsonObject2.put("name", "Rifle");
        jsonObject2.put("price", 500 + priceChange);
        array.put(jsonObject2);

        priceChange++;

        //You can create a MessageBodyWriter so you don't have to call toString() every time
        return array.toString();
    }
  
    CloudHandler cloud = new CloudHandler();
    @GET
    @Path("cloudItems")
    public String getItemsFromCloud() throws IOException, JDOMException
    {
    	ArrayList<Item> items = cloud.listItems();
    	JSONArray mJSONArray = new JSONArray(items);
    	
    	return mJSONArray.toString();
    }
    @GET
    @Path("loggedIn")
    public String loggedIn()
    {	
    	if(session.getAttribute("userAttr") != null)
    		
    		return session.getAttribute("userAttr").toString();
    	else
    		return "NOT !!!";
    }
    String r; 
    @POST
    @Path("login")
    public Response login(@FormParam("user") String user, @FormParam("password") String password) throws IOException, JDOMException { 

		r = cloud.login(user, password);
		
		if (cloud.responseCode == 200)	{
			System.out.println("LOGIN SUCCESS!!!!!!!!");
			session.setAttribute("userAttr", user);
		}
		else {
			System.out.println("UNVALID USER?? RESPONSECODE: " + cloud.responseCode);
		}	
    		//String username = "perle";
    		/*if (session.getAttribute(user) != null) {
    			username = (String) session.getAttribute("username");
    			}*/
    	return Response.status(cloud.responseCode)
    			.entity("Logget ind som: " + r)
    			.build();
    			
    }
    
    int counter = 0;
    @POST
    @Path("cart")
    public String addToCart (@FormParam("id") String itemId,@FormParam("stock") int itemStock) {
    
    	HashMap<String,Integer> cart =	(HashMap<String,Integer>) session.getAttribute("cart"); // = new HashMap();
    	
    	if(cart == null)
    	{
    		cart = new HashMap<String, Integer>();
    	}
  
    	if (cart.containsKey(itemId)) {
    		cart.put(itemId, cart.get(itemId)+1);
    	}
    	else {
    		cart.put(itemId, 1);
    	}
//    	
//    	if(itemStock > 0)
//    		
//    	counter++;
//    	
    	
    	session.setAttribute("cartAttr", cart);
    	return "http://services.brics.dk/java4/cloud/listItems?shopID=530";
    }
 }
