package dk.cs.dwebtek;

public class Item {
	private String _itemID;
	private String _itemName;
	private String _itemPrice;
	private String _itemURL;
	private String _itemStock;
	private String _itemDescription;

	public Item(String id, String name, String price, String URL, String stock, String description) {
		this._itemID = id;
		this._itemName = name;
		this._itemURL = URL;
		this._itemPrice = price;
		this._itemStock = stock;
		this._itemDescription = description;
	}
	public void setID(String _itemID) {
		this._itemID = _itemID;
	}
	public void setName(String _itemName) {
		this._itemName = _itemName;
	}
	public void setPrice(String _itemPrice) {
		this._itemPrice = _itemPrice;
	}
	public void setURL(String _itemURL) {
		this._itemURL = _itemURL;
	}
	public void setStock(String _itemStock) {
		this._itemStock = _itemStock;
	}
	public void setDescription(String _itemDescription) {
		this._itemDescription = _itemDescription;
	}
	public String getID() {
		return _itemID;
	}
	public String getName(){
		return _itemName;
	}
	public String getPrice(){
		return _itemPrice;
	}
	public String getURL(){
		return _itemURL;
	}
	public String getStock(){
		return _itemStock;
	}	
	public String getDescription(){
		return _itemDescription;
	}
}
