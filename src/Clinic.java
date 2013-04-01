import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//This class will management all the data in-flow and out-flow
public class Clinic {
	
	private List<Patient> patient_records; //All patient records
	private List<Medicine> medicine_records; //All medicine records
	
	private Queue<Patient> queue; //Queue for patients consultation
	private Queue<Patient> dispense_med_q; //Queue for dispensing medicince for patients

	private int current_Q; //Current Queue in clinic
	
	private int Q_size; //Total Queue size in clinic

	
	//Constructor will populate the patient and medicine records
	public Clinic()
	{
		patient_records = new ArrayList<Patient>();
		medicine_records = new ArrayList<Medicine>();
		
//		//Mock data
//		Medicine med = new Medicine();
//		med.insertNewMedicine("A", "Panadol", "pill", 5);
//		medicine_records.add(med);
//		
//		med = new Medicine();
//		med.insertNewMedicine("A", "Panadol 2", "liquid", 5);
//		medicine_records.add(med);
		
		queue = new LinkedList<Patient>();
		dispense_med_q = new LinkedList<Patient>();
		
		current_Q = 0;
		Q_size = 0;
		
		populate_data();
	}
	
	
	//Populate the patient and medicine records
	private void populate_data()
	{		
		try
		{
			String name = null, DOB = null, address = null, gender = null, NRIC = null;
			int hp = 0;
			Patient pat;
			//Load from text file
			//Patient records
			FileReader file = new FileReader("patients.txt");
			BufferedReader br = new BufferedReader (file);
			
			//Reading from the text file
	        String line;
	        int count = 0;
	        while((line = br.readLine()) != null)
	        {
	        	System.out.println(line);
	        	if(count == 0)
	        		name = line;
	        	else if(count == 1)
	        		DOB = line;
	        	else if(count == 2)
	        		address = line;
	        	else if(count == 3)
	        		hp = Integer.parseInt(line);
	        	else if(count == 4)
	        		gender = line;
	        	else if(count == 5)
	        	{
	        		NRIC = line;
	        		
	        		//Insert information into patient records
	        		pat = new Patient();
		            pat.createPat(name, DOB, address, hp, gender, NRIC);
		            
		            patient_records.add(pat);
	        	}
	        	else if(count == 6)
	        	{
	        		//Blank space
	        			        		
	        	}
	        	count++;
	        	if(count == 7)
	        	{
	        		//Reset to 0
	        		count = 0;
	        	}
	        	
	        }
	        		
			//Medicine records
			file = new FileReader("medicine.txt");
			br = new BufferedReader (file);
			
			//Reading from the text file	
			String supplier = null, type = null;
			Medicine med;
			int stock = 0;
	        count = 0;
	        while((line = br.readLine()) != null)
	        {
	        	System.out.println(line);
	        	if(count == 0)
	        		supplier = line;
	        	else if(count == 1)
	        		name = line;
	        	else if(count == 2)
	        		type = line;
	        	else if(count == 3)
	        	{
	        		stock = Integer.parseInt(line);
	        		
	        		//Insert new medicine
	        		med = new Medicine();
	        		med.insertNewMedicine(supplier, name, type, stock);
	        		medicine_records.add(med);
	        		
	        	}	       	        	
	        	else if(count == 4)
	        	{
	        		//Blank space
	        			        		
	        	}
	        	count++;
	        	if(count == 5)
	        	{
	        		//Reset to 0
	        		count = 0;
	        	}
	        	
	        }
//			
//			//Consultation records
//			file = new FileReader("consultation.txt");
//			br = new BufferedReader (file);
			
			br.close();
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
			 
			Consultation cons = new Consultation();
			cons.createCR(string_date, "", null, NRIC); //Since patient has not seen the doctor, prescription and illness description is null
			 
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
		return current_Q;
	}
	
	//Get the total Queue size on clinic
	public int getTotalQ()
	{
		return Q_size;
	}
	
	//Increase the current Q count
	//Can be modified to skip patient 
	public void addCurrentQ()
	{
		current_Q++;
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
	
	//Add the patient to the queue for dispense of medicine
	public Boolean insertMedDispense(Patient pat)
	{
		//Add the patient to the queue for dispense of medicine
		dispense_med_q.add(pat);
		return true;
	}
	
	//Dispense medicine functions for nurse
	public Patient dispenseMed()
	{
		Medicine patMed, med;
		//Check the medicine dispense queue
		if(dispense_med_q.size() == 0)
		{
			//no patient need dispense of medicine yet
			return null;
		}
		else
		{
			//Get the patient from the dispense med queue
			Patient pat = dispense_med_q.poll();
			
			//Get latest consultation record for patient
			List<Consultation> consList = pat.getConsRecords();
			Consultation cons = consList.get(consList.size()-1);
			
			//Dispense medicine for patient and update the stock of the medicine accordingly
			//Retrieve the medicine prescribed for patient
			List<Medicine> medList = cons.getPrescription();
			
			//Find the medicine from the master Medicine List at the clinic to update the stock
			for(int i = 0; i < medList.size(); i++)
			{
				patMed = medList.get(i);
				//For every medicine prescribed, look through all the records to find the medicine 
				for(int j = 0; j < medicine_records.size(); j++)
				{
					med = medicine_records.get(j);
					if(patMed.getName().equalsIgnoreCase(med.getName()))
					{
						//Decrease the stock
						//Assume dispense of medicine decrease stock by 1 for every medicine prescribed
						med.setStock(med.getStock()-1);
						break;
					}
				}
			}
			//Return medicine list prescribed for patient
			return pat;
		}
	}
	
	//********************************************************************Medicine-related functions************************************************************************//
	//Add new type of medicine 
	public Boolean addNewMedicine(String supplier, String name, String type, int stock)
	{
		//Create new medicine object
		Medicine med = new Medicine();
		
		//Insert the information
		med.insertNewMedicine(supplier, name, type, stock);
		
		//Add to the medicine_records in clinic
		medicine_records.add(med);
		
		return true;
	}
	
	//Restock medicine
	public Boolean restockMed(Medicine restockMed, int quantity)
	{
		Medicine med;
		//Find the medicine from the master Medicine List at the clinic to update the stock

		//For every medicine prescribed, look through all the records to find the medicine 
		for(int j = 0; j < medicine_records.size(); j++)
		{
			med = medicine_records.get(j);
			if(restockMed.getName().equalsIgnoreCase(med.getName()))
			{
				//Increase the stock
				med.setStock(med.getStock()+quantity);
				break;
			}
		}
		
		
		//Update medicine.txt
		
		
		return true;
	}
	
	//Check for any medicine that are low on stock
	//Assume when stock is < 10, its considered low
	public List<Medicine> checkLowStockMed()
	{
		Medicine med;
		List<Medicine> restockMedList = new ArrayList<Medicine>();
		
		//Iterate through every medicine and check its stock
		for(int j = 0; j < medicine_records.size(); j++)
		{
			med = medicine_records.get(j);
			if(med.getStock() < 10)
			{
				//Add to return medicine list
				restockMedList.add(med);
			}
		}
		
		return restockMedList;
	}
	
	//********************************************************************Search-related functions************************************************************************//
	//Search for a particular word that exist in Illness Description under patient's consultation records
	public List<Patient> searchIllnessDes(String word)
	{
		String regexp = "\\b" + word + "\\b";
		Pattern p = Pattern.compile(regexp);
		
		String sentence;
		Matcher m;
		
		//Temp list 
		List<Patient> patList = new ArrayList<Patient>();
		Patient pat;
		List<Consultation> consList;
		List<Consultation> consListNew = new ArrayList<Consultation>();
		Consultation cons;
		
		//Search through all the patient records
		for(int i = 0; i < patient_records.size(); i++)
		{
			pat = patient_records.get(i);
					
			//Get all the consultation records of this patient
			consList = pat.getConsRecords();
			
			//pat.clearConsRecords();
			
			//Get the illness description and check against every consultation
			for(int j = 0; j < consList.size(); j++)
			{
				cons = consList.get(j);
				sentence = cons.getIllnessDes();
				
				//Check against the regexp word
				m = p.matcher(sentence);
				if(m.find())
				{
					//Matched, add to temp patient list
					patList.add(pat);
					
					//Add the found consultation records in this new Patient object 
					//This will filter out the consultation records that does not contain the word in this Patient record
					//pat.AddConsRecords(cons);
					break;
					
				}
			}
		}
		
		return patList;
	}
	
	//Search for a particular word that exist in prescription under patient's consultation records
	public List<Patient> searchPrescription(String word)
	{
		Boolean check = false;
		String regexp = "\\b" + word + "\\b";
		Pattern p = Pattern.compile(regexp);
		
		String sentence;
		Matcher m;
		
		//Temp list 
		List<Patient> patList = new ArrayList<Patient>();
		Patient pat;
		List<Consultation> consList;
		Consultation cons;
		List<Medicine> medList;
		Medicine med;
		
		//Search through all the patient records
		for(int i = 0; i < patient_records.size(); i++)
		{
			pat = patient_records.get(i);
						
			//Get all the consultation records of this patient
			consList = pat.getConsRecords();
			
			//pat.clearConsRecords();
			
			//Get the illness description and check against every consultation
			for(int j = 0; j < consList.size(); j++)
			{
				cons = consList.get(j);
				
				//Get the medicine prescription
				medList = cons.getPrescription();
							
				for(int k = 0; k < medList.size(); k++)
				{
					med = medList.get(k);
					sentence = med.getName();
					
					//Check against the regexp word
					m = p.matcher(sentence);
					if(m.find())
					{
						//Matched, add to temp patient list
						patList.add(pat);
						check = true;
						//Add the found consultation records in this new Patient object 
						//This will filter out the consultation records that does not contain the word in this Patient record
						//pat.AddConsRecords(cons);
						break;
						
					}
				}
			if(check) break;
			}
		}
		
		return patList;
	}

	
	

}
