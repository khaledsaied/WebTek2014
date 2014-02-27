package dk.cs.dwebtek;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class CloudHandler {
	int responseCode;
	private static String _shopKey = "CF5576E136AC07922AF7F568";
	private static String _shopID = "530";
	private static String baseUrl = "http://services.brics.dk/java4/cloud/";
	private static Namespace ns = Namespace.getNamespace("http://www.cs.au.dk/dWebTek/2014");
	private static HttpURLConnection cloudCon;
	//String validatorPath = "Validator"+File.separator+"cloud.xsd";
	String validatorPath = "/Users/khaledsaied/Dropbox/IKT [6. SEMESTER]/WebTek/MacEclipse/Handin3/WebContent/WEB-INF/Validator/cloud.xsd";
	//private static String _customerID = null;
	private static String _itemID = null;
	//private static String _loginResponse = null;
	//private static String _createCustomerResponse = null;
	
	public void createItem(String name) throws IOException, JDOMException {		
		// lav createItem xml med navnet
		Element root = new Element("createItem", ns);
		Element e1 = new Element("shopKey", ns);
		e1.setText(_shopKey);
		root.addContent(e1);
		Element e2 = new Element("itemName", ns);
		e2.setText(name);
		root.addContent(e2);
		
		Document doc = new Document(root);
		try
		{
			Validator.validateXML(doc, Paths.get(validatorPath));
			// lav request til server med dokumentet
			Document requestDoc = postToCloud(doc,"createItem");
			
			// laes response og tag itemId ud
			_itemID = requestDoc.getRootElement().getText();
			System.out.println("My current itemID: " + _itemID + "\n");
		}
		catch(Exception e)
		{
			System.out.println("The XML document is not valid...");
			return;
		}
	}
	public void modifyItem(Item item) throws IOException, JDOMException {		
		Element root = new Element("modifyItem", ns);
		Element e1 = new Element("shopKey", ns);
		e1.setText(_shopKey);
		root.addContent(e1);
		Element e2 = new Element("itemID", ns);
		e2.setText(Integer.toString(item.getID()));
		root.addContent(e2);
		Element e3 = new Element("itemName", ns);
		e3.setText(item.getName());
		root.addContent(e3);
		Element e4 = new Element("itemPrice", ns);
		e4.setText(Integer.toString(item.getPrice()));
		root.addContent(e4);
		Element e5 = new Element("itemURL", ns);
		e5.setText(item.getURL());
		root.addContent(e5);
		Element e6 = new Element("itemDescription", ns);
		e6.addContent(generateItemDescriptionHTML(new Element("document",ns).setText(item.getDescription())));
		root.addContent(e6);
	
		Document doc = new Document(root);
		try
		{
			Validator.validateXML(doc, Paths.get(validatorPath));
			// brug itemId til at lave modifyItem xml
			postToCloud(doc, "modifyItem");
		}
		catch(Exception e)
		{
			System.out.println("The XML document is not valid in modifyItem...");
			return;
		}
	}
	public void createCustomer(String customerName, String customerPass) throws IOException, JDOMException {
		// Opret dokument
		Element root = new Element("createCustomer", ns);
		Element e1 = new Element("shopKey", ns);
		e1.setText(_shopKey);
		root.addContent(e1);
		Element e2 = new Element("customerName", ns);
		e2.setText(customerName);
		root.addContent(e2);
		Element e3 = new Element("customerPass", ns);
		e3.setText(customerPass);
		root.addContent(e3);
		
		Document doc = new Document(root);
		
		try
		{
			Validator.validateXML(doc, Paths.get(validatorPath));
			// lav request til server med dokumentet
			Document requestDoc = postToCloud(doc,"createCustomer");
			// laes response og tag itemId ud
			String createCustomerResponse = requestDoc.getRootElement().getText();
			System.out.println("My current Customer Response: " + createCustomerResponse + "\n");
		}
		catch(Exception e)
		{
			System.out.println("The XML document is not valid...");
			return;
		}
	}
	
	public void sellItems(int itemID, int customerID, int saleAmount) throws IOException, JDOMException {
		// Opret dokument
		Document doc = new Document();
		Element root = new Element("sellItems", ns);
		Element e1 = new Element("shopKey", ns);
		e1.setText(_shopKey);
		root.addContent(e1);
		Element e2 = new Element("itemID", ns);
		e2.setText(Integer.toString(itemID));
		root.addContent(e2);
		Element e3 = new Element("customerID",ns);
		e3.setText(Integer.toString(customerID));
		root.addContent(e3);
		Element e4 = new Element("saleAmount",ns);
		e4.setText(Integer.toString(saleAmount));
		root.addContent(e4);
		doc.setRootElement(root);
		
		try
		{
			Validator.validateXML(doc, Paths.get(validatorPath));
			// lav request til server med dokumentet
			Document requestDoc = postToCloud(doc,"sellItems");
			
			// laes respons
			String saleResponse = requestDoc.getRootElement().getText();
			System.out.println("My login response: " + saleResponse + "\n");
		}
		catch(Exception e)
		{
			System.out.println("The XML document is not valid...");
			return;
		}
	}
	public void adjustItemStock(int itemID, int adjustment) throws IOException, JDOMException {
		Element root = new Element("adjustItemStock", ns);
		Element e1 = new Element("shopKey", ns);
		e1.setText(_shopKey);
		root.addContent(e1);
		Element e2 = new Element("itemID", ns);
		e2.setText(Integer.toString(itemID));
		root.addContent(e2);
		Element e3 = new Element("adjustment", ns);
		e3.setText(Integer.toString(adjustment));
		root.addContent(e3);
	
		Document doc = new Document(root);
		
		try
		{
			Validator.validateXML(doc, Paths.get(validatorPath));
			// brug itemId til at lave modifyItem xml
			postToCloud(doc, "adjustItemStock");
		}
		catch(Exception e)
		{
			System.out.println("The XML document is not valid...");
			return;
		}
	}
	public String login(String user, String password) throws IOException, JDOMException {
		// Opret dokument
		Element root = new Element("login", ns);
		Element e1 = new Element("customerName", ns);
		e1.setText(user);
		root.addContent(e1);
		Element e2 = new Element("customerPass", ns);
		e2.setText(password);
		root.addContent(e2);
		Document doc = new Document(root);
		
		try
		{
			Validator.validateXML(doc, Paths.get(validatorPath));
			
			// lav request til server med dokumentet
			Document requestDoc = postToCloud(doc,"login");
			try
			{				
				Validator.validateXML(requestDoc, Paths.get(validatorPath));
				
				// laes respons
				String loginResponse = requestDoc.getRootElement().getChildText("customerName",ns);
				System.out.println("My login response: " + loginResponse + "\n");	
				return loginResponse;
			}
			catch(Exception e)
			{
				System.out.println("The XML document is not valid...");
				return "";
			}
		}
		catch(Exception e)
		{
			System.out.println("The XML document is not valid...");
			return "";
		}
	}
	public ArrayList<Item> listItems() throws IOException, JDOMException {
		Document doc = getFromCloud("listItems?shopID="+_shopID);
		ArrayList<Item> items = new ArrayList<Item>();
		
		Iterator<?> i = doc.getDescendants(new ElementFilter("item",ns));
		while(i.hasNext()) {
			Element element = (Element)i.next();
			int itemID = Integer.parseInt(element.getChildText("itemID",ns));
			String itemName = element.getChildText("itemName",ns);
			String itemURL = element.getChildText("itemURL",ns);
			int itemPrice = Integer.parseInt(element.getChildText("itemPrice",ns));
			int itemStock = Integer.parseInt(element.getChildText("itemStock",ns));
			String itemDescription = element.getChild("itemDescription", ns).getChildText("document",ns);
			
			items.add(new Item(itemID,itemName,itemPrice,itemURL,itemStock,itemDescription));
		}
		
		return items;
	}
	
	public ArrayList<Customer> listCustomers() throws IOException, JDOMException {
		Document doc = getFromCloud("listCustomers");
		ArrayList<Customer> customers = new ArrayList<Customer>();
		
		Iterator<?> i = doc.getDescendants(new ElementFilter("customer",ns));
		while(i.hasNext()) {
			Element element = (Element)i.next();
			int customerID = Integer.parseInt(element.getChildText("customerID",ns));
			String customerName = element.getChildText("customerName",ns);
			customers.add(new Customer(customerID,customerName));
		}
		return customers;
	}

	public Document getFromCloud(String request) throws IOException, JDOMException {
		URL cloudUrl = null;
		try {
			cloudUrl = new URL(baseUrl+request);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		cloudCon = (HttpURLConnection) cloudUrl.openConnection();
		cloudCon. setRequestMethod("GET"); //Could also be "GET", "PUT", "DELETE" ...
		cloudCon.setDoOutput(true);
		cloudCon.setDoInput(true);
		cloudCon.connect();
		
		responseCode = cloudCon.getResponseCode();
		System.out.println("\nMy response code in the method getFromCloud: " + responseCode);
		if(responseCode == 200)
		{
			InputStream response = cloudCon.getInputStream();
			
			return new SAXBuilder().build(response);
		}
		else
		{
			InputStream error = cloudCon.getErrorStream();
			System.out.println( "\n\n" +"RESPONSCODE: " + responseCode + "\n\n" + "ERRORMESSAGE: "+ error + "\n");
		}
        cloudCon.disconnect();
        
        return null;
	}
	public Document postToCloud(Document document, String request) throws IOException, JDOMException {	
		URL cloudUrl = null;
		try {
			cloudUrl = new URL(baseUrl+request);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		
		cloudCon = (HttpURLConnection) cloudUrl.openConnection();
		cloudCon. setRequestMethod("POST"); //Could also be "GET", "PUT", "DELETE" ...
		cloudCon.setDoOutput(true);
		cloudCon.setDoInput(true);
		cloudCon.connect();
		
		XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
		xmlOutput.output(document, cloudCon.getOutputStream());
		xmlOutput.output(document, System.out);
		
		responseCode = cloudCon.getResponseCode();
		System.out.println("\nMy response code in the method postToCloud: " + responseCode);
		if(responseCode == 200)
		{
			InputStream response = cloudCon.getInputStream();
			SAXBuilder builder = new SAXBuilder();
			Document doc = new Document();
			doc = builder.build(response);
			// INDSÆT VALIDATOREN HER SÅ MAN IKKE BEHØVER AT TESTE I DEN ENKELTE VALIDATOR KLASSE
			
			return doc;
		}
		else
		{
			InputStream error = cloudCon.getErrorStream();
			System.out.println( "\n\n" +"RESPONSCODE: " + responseCode + "\n\n" + "ERRORMESSAGE: "+ error + "\n");
		}
        cloudCon.disconnect();
        
        return null;
	}
	public Document readXml(String path) {		
		SAXBuilder builder = new SAXBuilder();
		Document doc = new Document();
		try {
			doc = builder.build(new File(path));
		} catch (JDOMException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return doc;
	}
public String generateItemDescriptionHTML(Element root) {
		String result = "";
		result += generateHTMLFromElement(root, true);
		result += root.getText();
		List<Element> children = root.getChildren();
		for (Element child : children) {
			result += generateItemDescriptionHTML(child);
		}
		result += generateHTMLFromElement(root, false);
		return result;
	}

private String generateHTMLFromElement(Element element, Boolean start) {
	String result = "<";
	String tag = element.getName();
	if (!start) {
		result += "/";
	}
	if (tag == "document") {
		result += "div>";
	} else if (tag == "italics") {
		result += "i>";
	} else if (tag == "bold") {
		result += "b>";
	} else if (tag == "list") {
		result += "ul>";
	} else if (tag == "item") {
		result += "li>";
	} else {
		return "";
	}
	return result;
}
	
}