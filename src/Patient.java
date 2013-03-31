import java.util.ArrayList;
import java.util.List;

//Patient class to contain information of a patient
public class Patient {

	private String name; //Name of patient
	private String DOB; //Date of birth
	private String address; 
	private int hp; //Handphone no.
	private String gender;
	private String NRIC; //IC of patient
	private Patient instance;
	private List<Consultation> cons_records; //Consultation records of this patient
	
	//Constructor
	public Patient()
	{
		cons_records = new ArrayList<Consultation>();
	}

	//Create new patient record
	public Boolean createPat(String name, String DOB, String address, int hp, String gender, String NRIC)
	{
		this.name = name;
		this.DOB = DOB;
		this.address = address;
		this.hp = hp;
		this.gender = gender;
		this.NRIC = NRIC;		
		return true;
	}
	
	//Getting patient consultation records
	public List<Consultation> getConsRecords()
	{
		return cons_records;
	}
	
	public void AddConsRecords(Consultation cons_records)
	{
		this.cons_records.add(cons_records);
	}
	
	public void clearConsRecords()
	{
		this.cons_records.clear();	
	}
	
	//Accessors to get patients' particulars.
	public String getName()
	{
		return this.name;
	}
	
	public String getDOB()
	{
		return this.DOB;
	}
	
	public String getAddress()
	{
		return this.address;
	}
	
	public int getHp()
	{
		return this.hp;
	}
	
	public String getGender()
	{
		return this.gender;
	}
	
	public String getNRIC()
	{
		return this.NRIC;
	}
	
	//Mutators to edit patients' particulars
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setDOB(String DOB)
	{
		this.DOB = DOB;
	}
	
	public void setNRIC(String NRIC)
	{
		this.NRIC = NRIC;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public void setHP(int HP)
	{
		this.hp = HP;
	}
	
	
	
}
