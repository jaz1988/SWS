import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//Testing
//Stay Well System
//This system will manage all the user interactions as well as back-end operations
public class SWS {
	
	private static Clinic _clinic;
	
	
	//Constructor;
	public SWS()
	{
		_clinic = new Clinic();
	}
	
	//********************************************************************Main Menu************************************************************************//
	private static void displaySelectUser()
	{
		//Add access control logic here
		System.out.println("Stay Well System");
		System.out.println("Select user: ");
		System.out.println("1. Doctor");
		System.out.println("2. Nurse");
		
		//Get choice form user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		try 
		{
			option = Integer.parseInt(br.readLine());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		switch(option)
		{
			case 1: 
				//Doctor's menu
				displayConsDoc();
				break;
			case 2: 
				//Nurse's menu
				displayMain();
				break;
			
			default: 
				System.out.println("Choose from option 1-2");
				break;
			
		}

	}
	
	private static void displayMain()
	{
		System.out.println("Stay Well System");
		System.out.println("1. Patient");
		System.out.println("2. Consultation");
		System.out.println("3. Medicine");
		System.out.println("0. Go Back");
		
		getUserChoice();
	}
	
	private static void getUserChoice()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		try 
		{
			option = Integer.parseInt(br.readLine());
			if(option == 0)
			{
				displaySelectUser();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		switch(option)
		{
			case 1: 
				//System.out.print("\f");
				displayPatient();
				break;
			case 2: 
				displayCons();
				break;
			case 3: 
				displayMed();
				break;
			default: 
				System.out.println("Choose from option 1-3");
				break;
			
		}
	}
	
	//********************************************************************Patient Menu************************************************************************//
	private static void displayPatient()
	{
		System.out.println("Patient Records");
		System.out.println("1. Register new patient");
		System.out.println("2. Get patient record");
		System.out.println("3. Modify patient record");
		System.out.println("0. Go back");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		try 
		{
			option = Integer.parseInt(br.readLine());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		switch(option)
		{
			case 0: 
				displayMain();
				break;
			case 1: 	
				//Register new patient
				registerNewPatient();
				break;
			case 2: 
				//Get patient record
				getPatRecord();
				break;
			case 3: 
				//Modifying patient details
				modifyPatDetails();
				break;
			default: 
				System.out.println("Choose from option 1-3");
				break;
			
		}
	}
	
	//Register new patient
	private static void registerNewPatient()
	{
		System.out.println("Register new patient");
		System.out.println("Fill in the following information:");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try 
		{			
			String name, DOB, address, gender, NRIC;
			int hp = 0;
			
			//Name
			System.out.print("Name:");
			name = br.readLine();
			
			//NRIC
			System.out.print("NRIC:");
			NRIC = br.readLine(); 
			
			//DOB
			System.out.print("DOB:");
			DOB = br.readLine(); 
			
			//Gender
			System.out.print("Gender (M/F): ");
			gender = br.readLine(); 
			
			//Address
			System.out.print("Address:");
			address = br.readLine(); 
			
			//Handphone number
			System.out.print("Handphone number:");
			hp = Integer.parseInt(br.readLine());
			
			//Register the patient
			Boolean result = _clinic.registerNewPatient(name, DOB, address, hp, gender, NRIC);
			
			if(result == true)
			{
				System.out.println("Patient registered successfully!");
				
				//Go back to patient page
				displayPatient();
			}
			else
			{
				System.out.println("Registration failed, patient record already exist.");
				
				//Go back to patient page
				displayPatient();
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Retrieve patient record
	private static void getPatRecord()
	{
		System.out.println("Retrieve patient record");
		System.out.println("Please enter patient's NRIC");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try 
		{
			String NRIC;
			//Get NRIC from input
			NRIC = br.readLine();
		
			//Get patient record
			Patient pat = _clinic.getPatRecord(NRIC);
						
			//check if returned record is null
			if(pat != null)
			{
				//Display patient record
				System.out.println("Name: " + pat.getName());
				System.out.println("NRIC: " + pat.getNRIC());
				System.out.println("DOB: " + pat.getDOB());
				System.out.println("Gender: " + pat.getGender());
				System.out.println("Address: " + pat.getAddress());
				System.out.println("Handphone Number: " + pat.getHp());		
				
				//Enter 0 to go back
				System.out.println("Enter 0 to go back");
				
				if(Integer.parseInt((br.readLine())) == 0)
				{
					//Go back to patient page
					displayPatient();
				}
			}
			else
			{
				System.out.println("Patient does not exist");
				
				//Go back to patient page
				displayPatient();
			}
						
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Modify patient record
	private static void modifyPatDetails()
	{
		System.out.println("Modify patient particulars");	
		System.out.println("Please enter patient's NRIC");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try 
		{
			String NRIC;
			//Get NRIC from input
			NRIC = br.readLine();
		
			//Get patient record
			Patient pat = _clinic.getPatRecord(NRIC);
						
			//check if returned record is null
			if(pat != null)
			{
				getModifiedValues(pat);							
			}
			else
			{
				System.out.println("Patient does not exist");
				
				//Go back to patient page
				displayPatient();
			}
						
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Modify patient record - get new values from user
	private static void getModifiedValues(Patient pat)
	{
		try 
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Select the field which you wish to modify:");
			
			//Display patient record
			System.out.println("1. Name: " + pat.getName());
			System.out.println("2. NRIC: " + pat.getNRIC());
			System.out.println("3. DOB: " + pat.getDOB());
			System.out.println("4. Address: " + pat.getAddress());
			System.out.println("5. Handphone Number: " + pat.getHp());
			System.out.println("0. Go Back");
			
			//Get the option from user 
			int option = 0;
			option = Integer.parseInt(br.readLine());
			
			if(option == 0)
			{
				//Go back to patient page
				displayPatient();
			}
			
			System.out.println("Please enter the new value here: ");
			String newValue = br.readLine();
			switch(option)
			{						
				case 1: 	
					//Modify name
					pat.setName(newValue);
					System.out.println("Name modified successfully!");
					getModifiedValues(pat);
					break;
				case 2: 
					//Modify NRIC
					pat.setNRIC(newValue);
					System.out.println("NRIC modified successfully!");
					getModifiedValues(pat);
					break;
				case 3: 
					//Modify DOB
					pat.setDOB(newValue);
					System.out.println("DOB modified successfully!");
					getModifiedValues(pat);
					break;
				case 4: 
					//Modify Address
					pat.setAddress(newValue);
					System.out.println("Address modified successfully!");
					getModifiedValues(pat);
					break;
				case 5: 
					//Modify Handphone number
					pat.setHP(Integer.parseInt(newValue));
					System.out.println("Handphone number modified successfully!");
					getModifiedValues(pat);
					break;

				default: 
					System.out.println("Choose from option 0-5");
					getModifiedValues(pat);
					break;
				
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//********************************************************************Consultation Menu (Nurse)************************************************************************//
	private static void displayCons()
	{
		System.out.println("Consultation");
		System.out.println("1. Register patient for consultation");
		System.out.println("2. Cancel patient's consultation");
		System.out.println("3. Check patient's queue number");
		System.out.println("4. Check current queue number");
		System.out.println("0. Go Back");
		
		//Get choice form user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		try 
		{
			option = Integer.parseInt(br.readLine());	
			if(option == 0)
			{
				//Go back to main page
				displayMain();
			}
			switch(option)
			{
				case 1: 
					//Register patient for consultation
					//Prompt for patient's NRIC
					System.out.println("Please enter patient's NRIC:");
					String NRIC = br.readLine();
					
					//If returned value == -1, patient record does not exist, prompt to register patient first
					//Else queue number of patient will be returned
					int exist = _clinic.registerForConsultation(NRIC);
					
					if(exist == -1)
					{
						//Patient record does not exist
						System.out.println("Patient not found in patient records, please register for the patient first");
						
						//Go back to consultation page
						displayCons();
					}
					else
					{
						System.out.println("Patient registered for consultation successfully. Queue number is: " + exist);
						
						//Go back to consultation page
						displayCons();
					}
					break;
				case 2: 
					//Cancel patient's consultation
					System.out.println("Please enter patient's NRIC:");
					NRIC = br.readLine();
					
					exist = _clinic.cancelConsultation(NRIC);
					if(exist == -2)
					{
						//Patient not registered in any consultation
						System.out.println("Patient is not registered for any consultation.");
						
						//Go back to consultation page
						displayCons();
					}
					else if(exist == -1)
					{
						//Patient record does not exist
						System.out.println("Patient not found in patient records, please register for the patient first");
						
						//Go back to consultation page
						displayCons();
					}
					else
					{
						System.out.println("Patient consultation successfully cancelled.");
						
						//Go back to consultation page
						displayCons();
					}
					break;
				
				case 3:
					//Check patient's queue number
					System.out.println("Please enter patient's NRIC:");
					NRIC = br.readLine();
					
					exist = _clinic.getPatQueue(NRIC);
					if(exist == -1)
					{
						//Patient not registered in any consultation
						System.out.println("Patient is not registered for any consultation.");
						
						//Go back to consultation page
						displayCons();
					}
					else
					{
						//Display queue number for the patient
						System.out.println("Queue number for patient is: " + exist);
						
						//Go back to consultation page
						displayCons();
					}
					break;
					
				case 4:
					//Display current queue number
					exist = _clinic.getClinicQueue();
					
					System.out.println("Current Queue number: " + exist);
					
					//Go back to consultation page
					displayCons();
					break;
					
				default: 
					System.out.println("Choose from option 1-2");
					//Go back to consultation page
					displayCons();
					break;
				
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//********************************************************************Consultation Menu (Doctor)************************************************************************//
	private static void displayConsDoc()
	{
		System.out.println("1: View next patient's record");
		System.out.println("0: Go back");
		
		//Get choice from user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		try 
		{
			option = Integer.parseInt(br.readLine());	
			
			if(option == 0)
			{
				//Go back to main page
				displayMain();
			}
			
			switch(option)
			{
				case 1:
					//Display current patient in queue's record
					displayConsPat();
					break;
				default: 
					System.out.println("Choose from option 0-1");
					//Go back to consultation page
					displayConsDoc();
					break;
					
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static void displayConsPat()
	{
		//If there are no patients in Queue
		if(_clinic.getClinicQueue() == 0)
		{
			System.out.println("There are no patients currently queuing for consultation.");
			displayConsDoc();
		}
		
		System.out.println("1: View all consultation records");
		System.out.println("2: Enter illness description");
		System.out.println("3: Enter medicine prescription");
		System.out.println("0: Go back");
		
		//Get choice from user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		try 
		{
			option = Integer.parseInt(br.readLine());	
			
			if(option == 0)
			{
				//Go back to main page
				displayMain();
			}
			
			//Get the patient from Q
			Patient pat = _clinic.getPatientInQ();
			
			switch(option)
			{
				case 1:
					//View all consultation records
					List<Consultation> consList = _clinic.getConsultationRecords(pat);
					Consultation cons;
					
					//Display all the records
					for(int i = 0; i < consList.size(); i++)
					{
						cons = consList.get(i);
						System.out.println("Date: " + cons.getDate());
						System.out.println("Illness description: " + cons.getIllnessDes());
						System.out.println("Medicine prescribed: ");
						
						//List of medicine prescribed
						List<Medicine> medicineList = cons.getPrescription();
						for(int j = 0; j < medicineList.size(); j++)
						{
							System.out.println( medicineList.get(j).getName() + ", " +  medicineList.get(j).getType() );
						}						
					}
					
					break;
					
				case 2: 
					//Enter illness description
					System.out.println("Please enter patient's illness description: ");
					String desc = br.readLine();
					
					//Set the description to patient's consultation records
					_clinic.setIllnessDesc(pat, desc);
					
					break;
					
				case 3:
					//Enter medicine prescription
					System.out.println("Please choose the medicine to prescribe for the patient: ");
					
					//Display all the medicines on screen and let the doctor choose for prescription
					displayMedicine(pat, null);
					break;
					
				default: 
					System.out.println("Choose from option 0-3");
					//Go back to consultation page
					displayConsPat();
					break;
					
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void displayMedicine(Patient pat, List<Medicine> medChosen)
	{
		List<Medicine> medList;
		if(medChosen == null)
			medChosen = new ArrayList<Medicine>();
		
		System.out.print("Medicine chosen: ");
		for(int i = 0; i < medChosen.size(); i++ )
		{
			System.out.print(medChosen.get(i).getName());
			System.out.print(", ");
		}
		
		//Get all the medicines available
		medList = _clinic.getMedicineList();
		System.out.println();
		for(int i = 0; i < medList.size(); i++ )
		{
			System.out.println(Integer.toString(i+1) + ": " + medList.get(i).getName());
		}
		System.out.println("0: Go back");
		
		//Get the input from doctor
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try
		{
			int choice = Integer.parseInt(br.readLine());
			
			//If user choose to go back
			if(choice == 0)
			{
				_clinic.setMedPres(pat, medList);
				displayConsPat();
			}
			
			//Add to medicine chosen list
			medChosen.add(medList.get(choice-1));
			displayMedicine(pat, medChosen);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	//********************************************************************Medicine Menu************************************************************************//
	private static void displayMed()
	{
		System.out.println("Medicine Records");
	}
	
	//********************************************************************Main************************************************************************//
	public static void main(String args[])
	{
		SWS sws = new SWS();
		sws.displaySelectUser();	

	}
	
	
	
}


