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
	//private HashMap<String,Integer> cart; //= new HashMap<String,Integer>();
   	HttpSession session;
   	
    public ShopService(@Context HttpServletRequest servletRequest) {
        session = servletRequest.getSession();
        //cart.put("itemID", 1);
        //session.setAttribute("cartMap", cart);
        System.out.println("Is not connected to session = " + session.isNew());
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
    	if(session.getAttribute("loginSession") != null)
    		
    		return session.getAttribute("loginSession").toString();
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
			session.setAttribute("loginSession", user);
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
    public String addToCart (@FormParam("id") String itemId,@FormParam("stock") int itemStock) 
    {
    	
    	
    	putInCart(itemId);    	
    	System.out.println(session.getAttribute("cart"));
    	
    	return "hej";
    }

    @POST
    @Path("adjustCloud")
    public boolean adjustCloud (@FormParam("id") String itemId,@FormParam("stock") int itemStock) 
    {
    	
    	//se hvilke items der er i session "CartMap"
    	//Skal v�re logged in 
    	//if(session is true)
    	//{
    	// User can click buy , tell user that purchase succeeded
    	//Set all inCartCell = 0;
    	//Adjust itemstock on the cloud
    	//foreach(items)
    	//	cloud.adjustItemStock(adjustment, ); for item.ID
    	//cloud.adjustItemStock(finalAmountStock);
    	//}
    	//else
    	//{
    	//Tell user that purchase failed
    	//}
    	//
    	if(session.getAttribute("loginSession") != null)
    	{
    		System.out.println("You can press buy");
    		System.out.println("User: " + session.getAttribute("loginSession").toString());
    		return true;
    		//return session.getAttribute("loginSession").toString();
    	}
    	else
    	{
    		System.out.println("You can't press buy");
    		System.out.println("User: " + session.getAttribute("loginSession").toString());
    		return false;
    		//return "NOT !!!";
    	}
    	
    	//return true;
    }       
        
    @POST
    @Path("putInCart")
    public void putInCart(String itemToAdd) 
    {       
    	HashMap<String,Integer> cart = (HashMap<String,Integer>) session.getAttribute("cartMap"); // = new HashMap();
    	    	
    	if(cart == null)
        {
         cart = new HashMap<String, Integer>();
        }
    	
    	if(cart.containsKey(itemToAdd))
    	{
    		System.out.println("itemAdd exists "+cart.get(itemToAdd));
    		cart.put(itemToAdd, cart.get(itemToAdd)+1);
    	}
    	else
    	{
    		System.out.println("item created "+cart.get(itemToAdd));
    		cart.put(itemToAdd, 1);
    	}
    	    	    	
    	session.setAttribute("cartMap", cart);
    	System.out.println("CART2:" + session.getAttribute("cartMap"));
    }
 }
