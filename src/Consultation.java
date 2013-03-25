//Consultation_Records class contains all information of a consultation session
public class Consultation {
	
	private String date;
	private String illnessDescription;
	private String prescription;
	private String NRIC;
	
	//Create consultation record for 1 session
	public Boolean createCR(String date, String description, String prescription)
	{
		this.date = date;
		this.illnessDescription = description;
		this.prescription = prescription;
		return true;
	}

}
