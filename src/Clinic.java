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
import java.util.StringTokenizer;
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
		//Initialization
		patient_records = new ArrayList<Patient>();
		medicine_records = new ArrayList<Medicine>();
			
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
	        	//Ignore any blanks or newline when reading from the textfile
	        	if(line == System.getProperty("line.separator") || line.equalsIgnoreCase(""))
	        		continue;
	        	
	        	//System.out.println(line);
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
	        	count++;
	        	if(count == 6)
	        	{
	        		//Reset to 0
	        		count = 0;
	        	}
	        	
	        }
	        file.close();
	        br.close();
	        		
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
	        	if(line == System.getProperty("line.separator") || line.equalsIgnoreCase(""))
	        		continue;
	        	
	        	//System.out.println(line);
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
	        	count++;
	        	if(count == 4)
	        	{
	        		//Reset to 0
	        		count = 0;
	        	}
	        	
	        }
	        file.close();
	        br.close();
	       
			//Consultation records
			file = new FileReader("consultation.txt");
			br = new BufferedReader (file);
			
			//Reading from the text file	
			String date = null, description = null;
			List<String> prescription = new ArrayList<String>();
			String rawPres = null;
			Consultation consList;
			Consultation cons;
			
	        count = 0;
	        while((line = br.readLine()) != null)
	        {
	        	if(line == System.getProperty("line.separator") || line.equalsIgnoreCase(""))
	        		continue;
	        	
	        	//System.out.println(line);
	        	if(count == 0)
	        		//Date
	        		date = line;
	        	else if(count == 1)
	        		//Illness description
	        		description = line;
	        	else if(count == 2)
	        	{
	        		//Prescription are read as Panadol,Cough Syrup
	        		//Need to tokenize to remove ','
	        		rawPres = line;
	        		StringTokenizer st = new StringTokenizer(rawPres, ",");
	        		prescription = new ArrayList<String>();
	        		while(st.hasMoreTokens())
	        		{
	        			//One medicine
	        			//Add into the prescription list	        			
	        			prescription.add(st.nextToken()); 
	        		}
	        		
	        	}
	        	else if(count == 3)
	        	{
	        		//NRIC
	        		NRIC = line;
	        		
	        		//Get patient record
	        		pat = getPatRecord(NRIC);
	        			        		
	        		//Create new consultation and add to respective patient
	        		cons = new Consultation();
	        		cons.createCR(date, description, prescription, NRIC);
	        		pat.AddConsRecords(cons);
	        		        		
	        		
	        	}	       	        		        	
	        	count++;
	        	if(count == 4)
	        	{
	        		//Reset to 0
	        		count = 0;
	        	}
	        	
	        }
			
	        file.close();
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
		appendToPatientTxt(pat);
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
		
		//update patients.txt
		updatePatientTxt();
		
		return true;
	}
	
	//Append new patient record to existing patients.txt
	private Boolean appendToPatientTxt(Patient pat)
	{
		try 
		{
			//Notice the second parameter "true"
			//This is needed to ensure the filewriter is in APPEND mode
			FileWriter out = new FileWriter("patients.txt",true);
			BufferedWriter bw = new BufferedWriter(out);
			
			//Write a newline first
			bw.newLine();
			bw.newLine();
			
			//Next, write all the information of the new patient		
			bw.write(pat.getName());
			bw.newLine();
			bw.write(pat.getDOB());
			bw.newLine();
			bw.write(pat.getAddress());
			bw.newLine();
			bw.write(Integer.toString(pat.getHp()));
			bw.newLine();
			bw.write(pat.getGender());
			bw.newLine();
			bw.write(pat.getNRIC());
			//bw.newLine();
				
			bw.close();
			
		} 
		catch (IOException e) 
		{		
			e.printStackTrace();
		}
		return true;
	}
	
	//Update patients.txt due to modifications
	private Boolean updatePatientTxt()
	{
		try 
		{
			//Notice there is no second parameter in the filewriter
			//This is needed to ensure the filewriter is in OVERWRITE mode
			FileWriter out = new FileWriter("patients.txt");
			BufferedWriter bw = new BufferedWriter(out);
			Patient pat;
			
			for(int i = 0; i < patient_records.size(); i++)
			{
				//Get patient record
				pat = patient_records.get(i);
				
				//Next, write all the information of the new patient		
				bw.write(pat.getName());
				bw.newLine();
				bw.write(pat.getDOB());
				bw.newLine();
				bw.write(pat.getAddress());
				bw.newLine();
				bw.write(Integer.toString(pat.getHp()));
				bw.newLine();
				bw.write(pat.getGender());
				bw.newLine();
				bw.write(pat.getNRIC());
				
				//last element
				if(i == patient_records.size()-1)
				{
					
				}
				else
				{
					bw.newLine();
					bw.newLine();
				}
			}
									
			bw.close();
			
		} 
		catch (IOException e) 
		{		
			e.printStackTrace();
		}
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
	public Boolean setMedPres(Patient pat, List<String> medList)
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
		Medicine med;
		String patMed;
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
			List<String> medList = cons.getPrescription();
			
			//Find the medicine from the master Medicine List at the clinic to update the stock
			for(int i = 0; i < medList.size(); i++)
			{
				patMed = medList.get(i);
				//For every medicine prescribed, look through all the records to find the medicine 
				for(int j = 0; j < medicine_records.size(); j++)
				{
					med = medicine_records.get(j);
					if(patMed.equalsIgnoreCase(med.getName()))
					{
						//Decrease the stock
						//Assume dispense of medicine decrease stock by 1 for every medicine prescribed
						med.setStock(med.getStock()-1);
						
						//Update medicine.txt
						updateMedicineTxt();
						break;
					}
				}
			}
			//Return medicine list prescribed for patient
			return pat;
		}
	}
	
	//Append new consultation record to consultation.txt
	public Boolean appendToConsultationTxt(Patient pat)
	{
		//Get latest consultation record for patient
		List<Consultation> consList = pat.getConsRecords();
		Consultation cons = consList.get(consList.size()-1);
		
		try 
		{
			//Notice the second parameter "true"
			//This is needed to ensure the filewriter is in APPEND mode
			FileWriter out = new FileWriter("consultation.txt",true);
			BufferedWriter bw = new BufferedWriter(out);
			
			//Write a newline first
			bw.newLine();
			bw.newLine();
			
			//Next, write all the information of the new patient		
			bw.write(cons.getDate());
			bw.newLine();
			bw.write(cons.getIllnessDes());
			bw.newLine();
			
			//Combine all the prescription together first
			List<String> prescList = cons.getPrescription();
			String presc = "";
			for(int i = 0; i < prescList.size(); i++)
			{
				presc += prescList.get(i);
				presc += ",";
			}			
			bw.write(presc);
			bw.newLine();
			bw.write(cons.getNRIC());
			//bw.newLine();
			
			bw.close();
			
		} 
		catch (IOException e) 
		{		
			e.printStackTrace();
		}
		return true;
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
		
		//Update medicine.txt
		appendToMedicineTxt(med);
		
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
				
				//Update medicine.txt
				updateMedicineTxt();
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
	
	//Append new consultation record to consultation.txt
	private Boolean appendToMedicineTxt(Medicine med)
	{
		
		try 
		{
			//Notice the second parameter "true"
			//This is needed to ensure the filewriter is in APPEND mode
			FileWriter out = new FileWriter("medicine.txt",true);
			BufferedWriter bw = new BufferedWriter(out);
			
			//Write a newline first
			bw.newLine();
			bw.newLine();
			
			//Next, write all the information of the new patient		
			bw.write(med.getSupplier());
			bw.newLine();
			bw.write(med.getName());
			bw.newLine();			
			bw.write(med.getType());
			bw.newLine();
			bw.write(Integer.toString(med.getStock()));
			//bw.newLine();
			
			bw.close();
			
		} 
		catch (IOException e) 
		{		
			e.printStackTrace();
		}
		return true;
	}
	
	//Update medicine.txt due to modifications
	private Boolean updateMedicineTxt()
	{
		try 
		{
			//Notice there is no second parameter in the filewriter
			//This is needed to ensure the filewriter is in OVERWRITE mode
			FileWriter out = new FileWriter("medicine.txt");
			BufferedWriter bw = new BufferedWriter(out);
			Medicine med;
			
			for(int i = 0; i < medicine_records.size(); i++)
			{
				//Get medicine record
				med = medicine_records.get(i);
				
				//Next, write all the information of the medicine
				bw.write(med.getSupplier());
				bw.newLine();
				bw.write(med.getName());
				bw.newLine();
				bw.write(med.getType());
				bw.newLine();
				bw.write(Integer.toString(med.getStock()));
				
				//last element
				if(i == medicine_records.size()-1)
				{
					
				}
				else
				{
					bw.newLine();
					bw.newLine();
				}
			}
									
			bw.close();
			
		} 
		catch (IOException e) 
		{		
			e.printStackTrace();
		}
		return true;
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
		List<String> medList;
		String med;
		
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
					sentence = med;
					
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
