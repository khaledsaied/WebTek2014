package dk.cs.dwebtek;
public class Customer {
	private int _customerID;
	private String _customerName;
	
	public Customer(int ID, String name){
		this._customerID = ID;
		this._customerName = name;
	}
	
	public int getID(){
		return _customerID;
	}	
	public String getName(){
		return _customerName;
	}
}