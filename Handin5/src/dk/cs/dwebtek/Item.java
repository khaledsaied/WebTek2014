package dk.cs.dwebtek;

public class Item {
	private int _itemID;
	private String _itemName;
	private int _itemPrice;
	private String _itemURL;
	private int _itemStock;
	private String _itemDescription;
	private String[][] _itemCoordinates;

	public Item(int id, String name, int price, String URL, int stock, String description) {
		this._itemID = id;
		this._itemName = name;
		this._itemURL = URL;
		this._itemPrice = price;
		this._itemStock = stock;
		this._itemDescription = description;
	}
	public void setID(int _itemID) {
		this._itemID = _itemID;
	}
	public void setName(String _itemName) {
		this._itemName = _itemName;
	}
	public void setPrice(int _itemPrice) {
		this._itemPrice = _itemPrice;
	}
	public void setURL(String _itemURL) {
		this._itemURL = _itemURL;
	}
	public void setStock(int _itemStock) {
		this._itemStock = _itemStock;
	}
	public void setDescription(String _itemDescription) {
		this._itemDescription = _itemDescription;
	}
	public void setCoordinates(String[][] _itemCoordinates) {
		this._itemCoordinates = _itemCoordinates;
	}
	public int getID() {
		return _itemID;
	}
	public String getName(){
		return _itemName;
	}
	public int getPrice(){
		return _itemPrice;
	}
	public String getURL(){
		return _itemURL;
	}
	public int getStock(){
		return _itemStock;
	}	
	public String getDescription(){
		return _itemDescription;
	}
	public String[][] getCoordinates(){
		return _itemCoordinates;
	}
}
