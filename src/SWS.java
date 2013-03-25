import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
	private static void displayMain()
	{
		System.out.println("Stay Well System");
		System.out.println("1. Patient Records");
		System.out.println("2. Consultation Records");
		System.out.println("3. Medicine Records");
		
		getUserChoice();
	}
	
	private static void getUserChoice()
	{
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
	
	//********************************************************************Consultation Menu************************************************************************//
	private static void displayCons()
	{
		System.out.println("Consultation Records");
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
		sws.displayMain();		

	}
	
	
	
}


