import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.jdom2.Element;
import org.jdom2.JDOMException;

@ManagedBean(name="itemBean",  eager = true)
@ApplicationScoped
public class ItemData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	Item item;
	private String itemName;
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	private int adjustStock;
	

	private CloudHandler _cloud = new CloudHandler();
	private ArrayList<Item> items; //= new ArrayList<Item>();
	   
	public ArrayList<Item> getItems() throws IOException, JDOMException {
		System.out.println("GET ITEMS??");
	   	  if(items == null) // Hvis udkommenteret, så virker modifyItem, hvis ikke, så virker createItem.
	   		items = _cloud.listItems();
	   		  
	      return items;
	   }
	
	public String modifyItem(Item modifyItem) throws IOException, JDOMException {
		setItem(modifyItem);
		_cloud.modifyItem(modifyItem);
		return "MODIFY";
	}   
	public String saveModifiedItem(Item modifyItem) throws IOException, JDOMException {
		setItem(modifyItem);
		_cloud.modifyItem(modifyItem);
		
		return "ADMIN";
	}
	public String adjustStock(int adj) throws IOException, JDOMException {
		_cloud.adjustItemStock(item.getID(), adj);
		
		return "ADJUST";
	}

	public int getAdjustStock() {
		return adjustStock;
	}

	public void setAdjustStock(int adjustStock) {
		this.adjustStock = adjustStock;
	}
	
}