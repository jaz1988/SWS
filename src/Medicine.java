
public class Medicine {

	private String supplier;
	private String name; //Name of the medicine
	private String type;
	private int stock;
	
	public Boolean insertNewMedicine(String supplier, String name, String type, int stock)
	{
		this.supplier = supplier;
		this.name = name;
		this.type = type;
		this.stock = stock;
		return true;
	}
	
	public String getSupplier()
	{
		return this.supplier;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public int getStock()
	{
		return this.stock;
	}
	
	public void setSupplier(String supplier)
	{
		this.supplier = supplier;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public void setStock(int stock)
	{
		this.stock = stock;
	}
	
}
