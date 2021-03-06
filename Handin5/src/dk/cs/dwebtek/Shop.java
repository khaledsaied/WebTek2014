package dk.cs.dwebtek;
public class Shop 
{
	// Private member variables
	private int _shopID;

	private String _shopURL;
	private String _shopName;
	
	// Constructor
	public Shop(int shopID, String shopURL, String shopName)
	{
		this._shopID = shopID;
		this._shopURL = shopURL;
		this._shopName = shopName;
	}
	
	// Getters
	public int getShopID()
	{
		return _shopID;
	}
	public void setShopID(int _shopID) {
		this._shopID = _shopID;
	}
	public String getShopURL()
	{
		return _shopURL;
	}
	
	public String getShopName()
	{
		return _shopName;
	}
	
}
