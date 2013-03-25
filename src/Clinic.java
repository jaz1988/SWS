import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

//This class will management all the data in-flow and out-flow
public class Clinic {
	
	private List<Patient> patient_records; //All patient records
	private List<Medicine> medicine_records; //All medicine records

	
	//Constructor will populate the patient and medicine records
	public Clinic()
	{
		patient_records = new ArrayList<Patient>();
		medicine_records = new ArrayList<Medicine>();
	}
	
	
	//Populate the patient and medicine records
	private void populate_data()
	{
		try
		{
			//Load from text file
			//Patient records
			FileReader file = new FileReader("patients.txt");
			BufferedReader br = new BufferedReader (file);
			
			//Reading from the text file
			String line = br.readLine();
	        while (line != null) {
	            line = br.readLine();
	            //Do something
	        }
			
			//Medicine records
			file = new FileReader("medicines.txt");
			br = new BufferedReader (file);
			
			//Consultation records
			file = new FileReader("consultations.txt");
			br = new BufferedReader (file);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	
	}
	
	//Registering a new patient
	public Boolean registerNewPatient(String name, String DOB, String address, int hp, String gender, String NRIC)
	{
		if(getPatRecord(NRIC)!=null)
		{
			//Patient record already exist, abort registering new patient
			return false;
		}
		
		//Create a new patient object
		Patient pat = new Patient();
		
		//Input the details and create the patient record
		pat.createPat(name, DOB, address, hp, gender, NRIC);
		
		//Add to the patient record list
		patient_records.add(pat);	
		
		//Update the patient.txt
		return true;
	}
	
	//Get patient record
	public Patient getPatRecord(String NRIC)
	{
		//Iterate through the patient record list
		for(int i = 0; i < patient_records.size(); i++)
		{
			if(patient_records.get(i).getNRIC().equalsIgnoreCase(NRIC))
			{
				//NRIC matched
				return patient_records.get(i);
			}
		}
		return null;
	}
	
	//Modify patient records
	public Boolean modifyPatRecords(Patient pat, String name, String DOB, String address, int hp, String gender, String NRIC)
	{
		//Changing the values accordingly
		pat.setAddress(address);
		pat.setDOB(DOB);
		pat.setName(name);
		pat.setHP(hp);
		pat.setNRIC(NRIC);
		return true;
	}
	
	
	
	
	

}
