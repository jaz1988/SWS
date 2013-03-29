import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//This class will management all the data in-flow and out-flow
public class Clinic {
	
	private List<Patient> patient_records; //All patient records
	private List<Medicine> medicine_records; //All medicine records
	
	private Queue<Patient> queue; //Queue for patients

	private int current_Q; //Current Queue in clinic
	
	private int Q_size; //Total Queue size in clinic
	
	private String current_date; //Current date
	
	private Patient current_patient;
	
	//Constructor will populate the patient and medicine records
	public Clinic()
	{
		patient_records = new ArrayList<Patient>();
		medicine_records = new ArrayList<Medicine>();
		
		//Mock data
		Medicine med = new Medicine();
		med.insertNewMedicine("A", "Panadol", "pill", 99);
		medicine_records.add(med);
		
		med = new Medicine();
		med.insertNewMedicine("A", "Panadol 2", "liquid", 99);
		medicine_records.add(med);
		
		queue = new LinkedList<Patient>();
		current_Q = 0;
		Q_size = 0;
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
	
	//********************************************************************Patient-related functions************************************************************************//
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
	
	//********************************************************************Consultation-related functions************************************************************************//
	//Register for consultation with doctor
	//Patient must already have personal record in the clinic
	//Return type is Int: to return the Q number to the patient
	public int registerForConsultation(String NRIC)
	{
		//Check if patient has record in the clinic
		Patient pat = getPatRecord(NRIC);
		if(pat == null)
		{
			//No record
			//Prompt to register patient first.
			//Return -1 for failed registration
			return -1;
		}
		else
		{
			//Slot patient into Queue
			queue.add(pat);
			
			//Create consultation record for the patient to the current date
			//Get the current date
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			String string_date = dateFormat.format(date);
			current_date = string_date;
			 
			Consultation cons = new Consultation();
			cons.createCR(string_date, null, null, NRIC); //Since patient has not seen the doctor, prescription and illness description is null
			 
			//Add record to patient 
			pat.AddConsRecords(cons);
			
			//Set Q number to consultation records
			Q_size++;
			cons.setQNo(Q_size);
			
			//return Q size
			return Q_size;
		}
	}
	
	//Cancel patient's consultation
	//Remove patient from the Queue
	public int cancelConsultation(String NRIC)
	{
		//Check if record exist
		Patient pat = getPatRecord(NRIC);
		
		if(pat!=null)
		{
			//Remove patient from queue
			Boolean check = queue.remove(pat);
			if(check) 
			{
				Q_size--;
				return 1; //Successful removal		
			}
			else return -2;
		}
		else
		{
			//Patient do not exist
			return -1;
		}
	}
	
	//Get queue number for patient
	public int getPatQueue(String NRIC)
	{
		Patient pat;
		int i = 1;
		
		//Iterator for queue
		Iterator it=queue.iterator();
		
		//Check through the queue to find the patient
		while(it.hasNext()) 
		{
			pat = (Patient) it.next();
			if(pat.getNRIC().equalsIgnoreCase(NRIC))
			{
				//Get latest consultation record for patient
				List<Consultation> consList = pat.getConsRecords();
				Consultation cons = consList.get(consList.size()-1);
				return cons.getQNo();
			}
			else
			{
				i++;
			}
		}
		
		//patient not registered for consultation
		return -1;
		
	}
	
	//Get current queue number in clinic
	public int getClinicQueue()
	{
		return Q_size;
	}
	
	//Set current patient
	public Boolean setCurrentPatient()
	{
		this.current_patient = queue.poll();
		return true;
	}
	
	//Get patient in Queue
	public Patient getPatientInQ()
	{
		return queue.poll();
	}
	
	//Get all consultation records for the patient
	public List<Consultation> getConsultationRecords(Patient pat)
	{
		return pat.getConsRecords();
	}
	
	//Enter Illness description
	public Boolean setIllnessDesc(Patient pat, String desc)
	{
		//Get latest consultation record for patient
		List<Consultation> consList = pat.getConsRecords();
		Consultation cons = consList.get(consList.size()-1);
		
		//Set the description
		cons.setIllnessDes(desc);
		return true;
	}
	
	//Display medicine available regardless of stock
	public List<Medicine> getMedicineList()
	{
		return medicine_records;
	}
	
	//Insert the medcine prescription list to the patient's consultation records
	public Boolean setMedPres(Patient pat, List<Medicine> medList)
	{
		//Get latest consultation record for patient
		List<Consultation> consList = pat.getConsRecords();
		Consultation cons = consList.get(consList.size()-1);
		
		//Set the prescription
		cons.SetMedList(medList);
		return true;
	}

}
