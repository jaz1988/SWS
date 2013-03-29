import java.util.ArrayList;
import java.util.List;

//Consultation_Records class contains all information of a consultation session
public class Consultation {
	
	private String date; //Date of the date of consultation session
	private String illnessDescription; //Description of patient's illness
	private List<Medicine> prescription; //Prescription will contain more than 1 type of medicine, hence a List would store all the medicine types.
	private String NRIC; //NRIC is needed when backing up consultation records for the current patient.
	private int Q_No; //Queue number of patient for this consultation session
	
	//Constructor
	public Consultation()
	{
		prescription = new ArrayList<Medicine>();
	}
	
	//Create consultation record for 1 session
	public Boolean createCR(String date, String description, List<Medicine> prescription, String NRIC)
	{
		this.date = date;
		this.illnessDescription = description;
		this.prescription = prescription;
		this.NRIC = NRIC;
		return true;
	}
	
	public String getDate()
	{
		return this.date;
	}
	
	public String getIllnessDes()
	{
		return this.illnessDescription;
	}
	
	public List<Medicine> getPrescription()
	{
		return this.prescription;
	}
	
	public String getNRIC()
	{
		return this.NRIC;
	}
	
	public int getQNo()
	{
		return this.Q_No;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}
	
	public void setIllnessDes(String des)
	{
		this.illnessDescription = des;
	}
	
	public void setQNo(int Q_No)
	{
		this.Q_No = Q_No;
	}
	
	public void addPrescription(Medicine medicine)
	{
		this.prescription.add(medicine);
	}
	
	public void SetMedList(List<Medicine> medList)
	{
		this.prescription = medList;
	}
	
}
