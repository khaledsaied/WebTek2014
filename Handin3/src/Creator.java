import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.jdom2.JDOMException;

@ManagedBean(name="creatorBean",  eager = true)
@ViewScoped
public class Creator implements Serializable {
	
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

	private CloudHandler _cloud = new CloudHandler();
	
	public String createItem() throws IOException, JDOMException {
		_cloud.createItem(this.itemName);
		System.out.println("I AM INSIDE CREATE");
		
		return "CREATE";
	}
}