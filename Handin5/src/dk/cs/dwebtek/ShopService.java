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
		System.out.println("Is not connected to session = " + session.isNew());
	}

	CloudHandler cloud = new CloudHandler();

	@GET
	@Path("cloudItems")
	public String getItemsFromCloud() throws IOException,
			JDOMException {
		ArrayList<Item> items = cloud.listItems();
		JSONArray mJSONArray = new JSONArray(items);

		return mJSONArray.toString();
	}

	@GET
	@Path("shops")
	public String getShopsFromCloud() throws IOException, JDOMException {
		ArrayList<Shop> shops = cloud.listShops();
		JSONArray mJSONArray = new JSONArray(shops);

		return mJSONArray.toString();
	}

	@GET
	@Path("loggedIn")
	public String loggedIn() {
		if (session.getAttribute("loginSession") != null)
			return session.getAttribute("loginSession").toString();
		else
			return "User is not signed in";
	}

	@GET
	@Path("loggedInBool")
	public boolean loggedInBool() {
		if (session.getAttribute("loginSession") != null) {
			System.out.println("LoggedInBool: true");
			return true;
		} else {
			System.out.println("LoggedInBool: false");
			return false;
		}
	}

	String r;
	@POST
	@Path("login")
	public String login(@FormParam("user") String user,
			@FormParam("password") String password) {
		try {
			r = cloud.login(user, password);
		} catch (IOException | JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}

		if (cloud.responseCode == 200) {
			System.out.println("LOGIN SUCCESS!!!!!!!!");
			session.setAttribute("loginSession", user);
			return "success";
		} else {
			System.out.println("UNVALID USER?? RESPONSECODE: "
					+ cloud.responseCode);

			return "fail";
		}
	}

	@POST
	@Path("logout")
	public void logout() {
		session.setAttribute("loginSession", null); // Remove Session
	}

	@POST
	@Path("cart")
	public String addToCart(@FormParam("id") String itemId,
			@FormParam("stock") int itemStock) {
		putInCart(itemId);
		System.out.println(session.getAttribute("cart"));

		return "hej";
	}

	@POST
	@Path("adjustCloud")
	public boolean adjustCloud(@FormParam("id") String itemId,
			@FormParam("stock") int itemStock) {
		// Get "CartMap" attribute in Session

		// Sell items on cloud based upon the amount of each itemID:
		// cloud.adjustItemStock(finalAmountStock);

		// Set all cells in table to 0 with the same itemID as the ones in
		// HahMap, CartMap.

		// Set innerHTML in <span id="totalPrise"> to 0

		// if(session is true)
		// {
		// User can click buy , tell user that purchase succeeded
		// Set all inCartCell = 0;
		// Adjust itemstock on the cloud
		// foreach(items)
		// cloud.adjustItemStock(adjustment, ); for item.ID
		// cloud.adjustItemStock(finalAmountStock);
		// }
		// else
		// {
		// Tell user that purchase failed
		// }
		//
		if (session.getAttribute("loginSession") != null) {

			System.out.println("User: "
					+ session.getAttribute("loginSession").toString());
			return true;
			// return session.getAttribute("loginSession").toString();
		} else {

			System.out.println("User: "
					+ session.getAttribute("loginSession").toString());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("putInCart")
	public void putInCart(String itemToAdd) {
		HashMap<String, Integer> cart = (HashMap<String, Integer>) session
				.getAttribute("cartMap");

		if (cart == null) {
			cart = new HashMap<String, Integer>();
		}

		if (cart.containsKey(itemToAdd)) {
			System.out.println("itemToAdd exists and is: "
					+ cart.get(itemToAdd));
			cart.put(itemToAdd, cart.get(itemToAdd) + 1);
		} else {
			System.out.println("itemToAdd is created and is: "
					+ cart.get(itemToAdd));
			cart.put(itemToAdd, 1);
		}

		session.setAttribute("cartMap", cart);
	}
}
