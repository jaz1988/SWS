import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

//Testing
//Stay Well System
//This system will manage all the user interactions as well as back-end operations
public class ADT {
	
	private static Clinic _clinic;
	private static List<ADT_var> adt_list; //Contains all the test cases information
	private static String cur_name, cur_output; //current test case name and expected output executed
	private static Queue<Integer> cur_options_q; //All options path stored in this q
	private static Queue<String> cur_input_q; //All inputs required stored in this q
	private static String identifier; //Identifier to recognize variables if needed
	private static String cur_results; //Results to be written to text file adt_results.txt
	private static Boolean check; //Variable to check if needed
	
	
	//Constructor;
	public ADT()
	{
		check = true;
		_clinic = new Clinic();
		adt_list = new ArrayList<ADT_var>();
		
		//Read from adt.txt
		//Populate all test cases to adt_list
		FileReader file;
		try 
		{
			file = new FileReader("adt.txt");
			BufferedReader br = new BufferedReader (file);
			
			//Reading from the text file
	        String line, testName = null, options = null, input = null, output = null;
	        Queue<Integer> options_Q;
	        Queue<String> input_Q;
	        
	        ADT_var var;
	        
	        int count = 0;
	        while((line = br.readLine()) != null)
	        {
	        	//Ignore any blanks or newline when reading from the textfile
	        	if(line == System.getProperty("line.separator") || line.equalsIgnoreCase(""))
	        		continue;
	        	
	        	//System.out.println(line);
	        	if(count == 0)
	        		testName = line;
	        	else if(count == 1)
	        		options = line;
	        	else if(count == 2)
	        		input = line;		        	
	        	else if(count == 3)
	        	{
	        		output = line;
	        		
	        		var = new ADT_var();
	        		var.name = testName;
	        		System.out.println("Name: " + var.name);
	        		
	        		//Tokenize options
	        		options_Q = new LinkedList<Integer>();
	        		int option_int;
	        		StringTokenizer st = new StringTokenizer(options, ";");	
	        		System.out.print("Options: ");
	        		while(st.hasMoreTokens())
	        		{        	
	        			//add to options queue
	        			option_int = Integer.parseInt(st.nextToken());
	        			System.out.print(option_int + "->");
	        			options_Q.add(option_int); 
	        			
	        		}
	        		System.out.println();
	        		
	        		//Tokenize input
	        		input_Q = new LinkedList<String>();
	        		String input_string;
	        		st = new StringTokenizer(input, ";");	
	        		System.out.print("Input: ");
	        		while(st.hasMoreTokens())
	        		{        	
	        			//add to input queue
	        			input_string = st.nextToken();
	        			System.out.print(input_string + "->");
	        			input_Q.add(input_string); 
	        		}
	        		System.out.println();
	        		        		
	        		var.options_Q = options_Q;
	        		var.input_Q = input_Q;
	        		var.output = output;
	        		System.out.println("Output: " + var.output);
	        		
	        		adt_list.add(var);
	        		
	        		
	        		
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
	        
	        //Execute adt test cases according to adt_list
	        for(int i = 0; i < adt_list.size(); i++)
	        {
	        	var = adt_list.get(i);
	        	cur_name = var.name;
	        	cur_options_q = var.options_Q;
	        	cur_input_q = var.input_Q;
	        	cur_output = var.output;
	        	
	        	displaySelectUser();
	        }
		} 
		catch (FileNotFoundException e) 
		{			
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//Check if a string is an integer
	private static boolean isInteger(String s) 
	{
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	private static void clrScreen()
	{
		for(int i = 0; i < 100; i++) {
		    System.out.println();
		  }
	}
	
	
	//********************************************************************Main Menu************************************************************************//
	//Main menu to select type of user
	private static void displaySelectUser()
	{
		//clrScreen();
		
		//Add access control logic here
		System.out.println("Stay Well System");
		System.out.println();
		System.out.println("Select user: ");
		System.out.println("1. Doctor");
		System.out.println("2. Nurse");
		
		//Get choice form user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		try 
		{
			//option = Integer.parseInt(br.readLine());	
			
			//option are always taken from head of cur_options_q
			option = cur_options_q.poll();
		} 
		catch (NumberFormatException ne)
		{
			System.err.println("Choose from option 1-2");
			displaySelectUser();
		}
		
		switch(option)
		{
			case 1: 
				//Doctor's menu
				displayMainDoc();
				break;
			case 2: 
				//Nurse's menu
				displayMainNurse();
				break;			
			default: 
				System.out.println("Choose from option 1-2");
				displaySelectUser();
				break;
			
		}

	}
	
	//Nurse MAIN menu
	private static void displayMainNurse()
	{
		//clrScreen();
		System.out.println();
		System.out.println("Nurse main menu");
		System.out.println();
		System.out.println("1. Patient");
		System.out.println("2. Consultation");
		System.out.println("3. Medicine");
		System.out.println("4. Search");
		System.out.println("0. Go Back");
		
		getUserChoice();
	}
	
	private static void getUserChoice()
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		try 
		{
			//option = Integer.parseInt(br.readLine());
			
			//option are always taken from head of cur_options_q
			option = cur_options_q.poll();
			if(option == 0)
			{
				displaySelectUser();
			}
		} 
		catch (NumberFormatException ne)
		{
			System.err.println("Choose from option 0-4");
			displayMainNurse();
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
			case 4:
				searchMenu();
				break;
			default: 
				System.out.println("Choose from option 0-4");
				break;
			
		}
	}
	
	//********************************************************************Patient Menu************************************************************************//
	private static void displayPatient()
	{
		//clrScreen();
		System.out.println();
		System.out.println("Patient Records");
		System.out.println();
		System.out.println("1. Register new patient");
		System.out.println("2. Get patient record");
		System.out.println("3. Modify patient record");
		System.out.println("0. Go back");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		try 
		{
			//option = Integer.parseInt(br.readLine());
			option = cur_options_q.poll();
		} 
		catch (NumberFormatException ne)
		{
			System.err.println("Choose from option 0-3");
			displayPatient();
		}
		
		switch(option)
		{
			case 0: 
				displayMainNurse();
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
				System.out.println("Choose from option 0-3");
				break;
			
		}
	}
	
	//Register new patient
	private static void registerNewPatient()
	{
		//clrScreen();
		System.out.println();
		System.out.println("Register new patient");
		System.out.println();
		System.out.println("Fill in the following information:");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String name, DOB, address, gender, NRIC;
		int hp = 0;
		
		//Name
		System.out.print("Name:");
		//name = br.readLine();
		name = cur_input_q.poll();
		
		//NRIC
		System.out.print("NRIC:");
		//NRIC = br.readLine();
		NRIC = cur_input_q.poll();
		
		//DOB
		System.out.print("DOB:");
		//DOB = br.readLine(); 
		DOB = cur_input_q.poll();
		
		//Gender
		System.out.print("Gender (M/F): ");
		//gender = br.readLine(); 
		gender = cur_input_q.poll();
		
		//Address
		System.out.print("Address:");
		//address = br.readLine(); 
		address = cur_input_q.poll();
		
		//Handphone number
		String hpString = null;
		do
		{
			System.out.print("Handphone number:");
			//hpString = br.readLine();
			hpString = cur_input_q.poll();
			if(isInteger(hpString))
			{
				hp = Integer.parseInt(hpString);
				break;
			}
			else
			{
				System.err.println("Please enter only numbers");
			}
		} while(true);
		
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
			
			cur_results = "Fail, patient record already exist";
			appendFile();
			
			//Go back to patient page
			//displayPatient();
		}
	}
	
	//Retrieve patient record
	private static void getPatRecord()
	{
		//clrScreen();
		System.out.println();
		System.out.println("Retrieve patient record");
		System.out.println();
		System.out.println("Please enter patient's NRIC");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String NRIC;
		//Get NRIC from input
		//NRIC = br.readLine();
		NRIC = cur_input_q.poll();

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
			
			if(cur_name.equalsIgnoreCase("Test case 3-1"))
			{
				//Check if DOB is modified
				if(pat.getDOB().equalsIgnoreCase(cur_output))
				{
					cur_results = "Pass";
					appendFile();
				}
				else
				{
					cur_results = "Fail, DOB modified wrongly";
					appendFile();
				}
			}
			else
			{
				cur_results = "Pass";
				appendFile();
			}
			
			//Enter 0 to go back
//				System.out.println("Enter 0 to go back");
			
//				if(Integer.parseInt((br.readLine())) == 0)
//				{
//					//Go back to patient page
//					displayPatient();
//				}
		}
		else
		{
			System.out.println("Patient does not exist");
			
			cur_results = "Fail, patient does not exist in database";
			appendFile();
			//Go back to patient page
			//displayPatient();
		}
	}
	
	//Modify patient record
	private static void modifyPatDetails()
	{
		//clrScreen();
		System.out.println();
		System.out.println("Modify patient particulars");	
		System.out.println();
		System.out.println("Please enter patient's NRIC");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String NRIC;
		//Get NRIC from input
		//NRIC = br.readLine();
		NRIC = cur_input_q.poll();

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
	
	//Modify patient record - get new values from user
	private static void getModifiedValues(Patient pat)
	{
		try 
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Select the field which you wish to modify:");
			System.out.println();
			
			//Display patient record
			System.out.println("1. Name: " + pat.getName());
			System.out.println("2. NRIC: " + pat.getNRIC());
			System.out.println("3. DOB: " + pat.getDOB());
			System.out.println("4. Address: " + pat.getAddress());
			System.out.println("5. Handphone Number: " + pat.getHp());
			System.out.println("0. Go Back");
			
			//Get the option from user 
			int option = 0;
			//option = Integer.parseInt(br.readLine());
			option = cur_options_q.poll();
			
			if(option == 0)
			{
				//Go back to patient page
				displayPatient();
			}
			
			System.out.println("Please enter the new value here: ");
			//String newValue = br.readLine();
			String newValue = cur_input_q.poll();
			switch(option)
			{						
				case 1: 	
					//Modify name
					pat.setName(newValue);
					System.out.println("Name modified successfully!");
					_clinic.modifyPatRecords(pat, pat.getName(), pat.getDOB(), pat.getAddress(), pat.getHp(), pat.getGender(), pat.getNRIC());
					getModifiedValues(pat);
					break;
				case 2: 
					//Modify NRIC
					pat.setNRIC(newValue);
					System.out.println("NRIC modified successfully!");
					_clinic.modifyPatRecords(pat, pat.getName(), pat.getDOB(), pat.getAddress(), pat.getHp(), pat.getGender(), pat.getNRIC());
					getModifiedValues(pat);
					break;
				case 3: 
					//Modify DOB
					pat.setDOB(newValue);
					System.out.println("DOB modified successfully!");
					_clinic.modifyPatRecords(pat, pat.getName(), pat.getDOB(), pat.getAddress(), pat.getHp(), pat.getGender(), pat.getNRIC());
					displayPatient();
					//getModifiedValues(pat);
					break;
				case 4: 
					//Modify Address
					pat.setAddress(newValue);
					System.out.println("Address modified successfully!");
					_clinic.modifyPatRecords(pat, pat.getName(), pat.getDOB(), pat.getAddress(), pat.getHp(), pat.getGender(), pat.getNRIC());
					getModifiedValues(pat);
					break;
				case 5: 
					//Modify Handphone number
					//Check if input is integer
					//Handphone number
					String hpString = null;
					do
					{
						System.out.print("Handphone number:");
						hpString = br.readLine();
						if(isInteger(hpString))
						{
							pat.setHP(Integer.parseInt(hpString));
							System.out.println("Handphone number modified successfully!");
							_clinic.modifyPatRecords(pat, pat.getName(), pat.getDOB(), pat.getAddress(), pat.getHp(), pat.getGender(), pat.getNRIC());
							getModifiedValues(pat);
							break;
						}
						else
						{
							System.err.println("Please enter only numbers");
						}
					} while(true);
										
					break;

				default: 
					System.out.println("Choose from option 0-5");
					getModifiedValues(pat);
					break;
				
			}
		}
		catch (NumberFormatException ne)
		{
			System.err.println("Choose from option 0-5");
			getModifiedValues(pat);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//********************************************************************Consultation Menu (Nurse)************************************************************************//
	private static void displayCons()
	{
		//clrScreen();
		System.out.println();
		System.out.println("Consultation");
		System.out.println();		
		System.out.println("1. Register patient for consultation");
		System.out.println("2. Cancel patient's consultation");
		System.out.println("3. View patient's consultation records");
		System.out.println("4. Check patient's queue number");
		System.out.println("5. Check current queue number");
		System.out.println("6. Dispense medicine");
		System.out.println("0. Go Back");
		
		//Get choice form user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		try 
		{
			//option = Integer.parseInt(br.readLine());
			option = cur_options_q.poll();
			if(option == 0)
			{
				//Go back to main page
				displayMainNurse();
			}
			switch(option)
			{
				case 1: 
					//Register patient for consultation
					//Prompt for patient's NRIC
					System.out.println("Please enter patient's NRIC:");
					//String NRIC = br.readLine();
					String NRIC = cur_input_q.poll();
					
					//If returned value == -1, patient record does not exist, prompt to register patient first
					//Else queue number of patient will be returned
					int exist = _clinic.registerForConsultation(NRIC);
					
					if(exist == -1)
					{
						//Patient record does not exist
						System.out.println("Patient not found in patient records, please register for the patient first");
						
						//Go back to consultation page
						//displayCons();
						displaySelectUser();
					}
					else
					{
						System.out.println("Patient registered for consultation successfully. Queue number is: " + exist);
						
						//Go back to consultation page
						//displayCons();
						displaySelectUser();
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
					//View patient consultation records
					System.out.println("Please enter patient's NRIC:");
					NRIC = br.readLine();
					
					//Get patient record
					Patient pat = _clinic.getPatRecord(NRIC);
					
					if(pat == null)
					{
						//Patient do not exist in record
						//Patient record does not exist
						System.out.println("Patient not found in patient records.");
						
						//Go back to consultation page
						displayCons();
					}
					
					System.out.println("All consultation records");
					System.out.println();
					
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
						List<String> medicineList = cons.getPrescription();
						if(medicineList!=null)
						{
							for(int j = 0; j < medicineList.size(); j++)
							{
								System.out.println(Integer.toString(j + 1) + ". " +  medicineList.get(j));
							}					
						}
						System.out.println();	
					}
					
					//Go back to consultation page
					displayCons();
					break;
					
				case 4:
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
					
				case 5:
					//Display current queue number
					exist = _clinic.getClinicQueue();
					
					System.out.println("Current Queue number: " + exist);
					
					//Go back to consultation page
					displayCons();
					break;
					
				case 6:
					//Dispense medicine for patient							
					//Medicine list prescribed for patient is returned
					pat = _clinic.dispenseMed();
					
					if(pat == null)
					{
						//no patient for dispense medicine
						cur_results = "Fail, no patient are in Q for medicine dispense";
						appendFile();
						return;
					}
					
					//Get latest consultation record for patient
					consList = pat.getConsRecords();
					cons = consList.get(consList.size()-1);
					
					//Retrieve the medicine prescribed for patient
					List<String> medList = cons.getPrescription();
					
					System.out.println("Medicine dispensed for patient " + pat.getName() + ", " + pat.getNRIC());
					for(int i = 0; i < medList.size(); i++)
					{
						System.out.println(Integer.toString(i+1) + ". " + medList.get(i));
						if(medList.get(i).equalsIgnoreCase(cur_output))
						{
							cur_results = "Pass";
							appendFile();
							return;
						}
					}
					
					//Go back to consultation page
					displayCons();
					break;
					
				default: 
					System.out.println("Choose from option 0-6");
					//Go back to consultation page
					displayCons();
					break;
				
			}
		} 
		catch (NumberFormatException ne)
		{
			System.err.println("Choose from option 0-6");
			displayCons();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//********************************************************************Consultation Menu (Doctor)************************************************************************//
	//This is the menu shown to the doctor
	private static void displayMainDoc()
	{
		//clrScreen();
		System.out.println();
		System.out.println("Doctor's main menu");
		System.out.println();
		System.out.println("1. View next patient's record"); //Once the doctor select this option, the next patient
															 //in queue for consultation will have his records fetched
		//System.out.println("2: Open consultation record");
		System.out.println("2. Search");
		System.out.println("0. Go back");
		
		//Get choice from user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		try 
		{
			//option = Integer.parseInt(br.readLine());	
			option = cur_options_q.poll();
			
			if(option == 0)
			{
				//Go back to main page
				displaySelectUser();
			}
			
			switch(option)
			{
				case 1:
					//If there are no patients in Queue
					if(_clinic.getTotalQ() == 0)
					{
						System.out.println("There are no patients currently queuing for consultation.");
						//displayMainDoc();
						cur_results = "Fail, patient is not registered for consultation";
						appendFile();
						return;
					}
					
					//Get the patient from Q
					Patient pat = _clinic.getPatientInQ();
					
					//If not in Q (Not registered for consultation)
					if(pat == null)
					{
						cur_results = "Fail, patient is not registered for consultation";
						appendFile();
						return;
					}
					else
					{
						//Display current patient in queue's record
						
						cur_results = "Pass";
						appendFile();
						
						displayConsPat(pat);
						break;
					}
					
//				case 2:
//					//opening consultation records manually
//					System.out.println("Enter patient's NRIC: ");
//					
//					String NRIC = br.readLine();
//					
//					//Get patient's record
//					pat = _clinic.getPatRecord(NRIC);
//					
//					//Open consultation record
//					displayConsPat(pat);
//					break;
				case 2:
					searchMenu();
					break;
					
				default: 
					System.out.println("Choose from option 0-2");
					//Go back to consultation page
					displayMainDoc();
					break;
					
			}
			
		}
		catch (NumberFormatException ne)
		{
			System.err.println("Choose from option 0-2");
			displayMainDoc();
		}
		
		
		
	}
	//This function lets the doctor access the consultation record of the patient
	//He will modify the information here by selecting the various options available
	private static void displayConsPat(Patient pat)
	{		
		//clrScreen();
		
		//Increment the current Q to show that the doctor is currently attending to this patient
		_clinic.addCurrentQ();
		
		//Show particulars of patient
		System.out.println("Patient Name: " + pat.getName());
		System.out.println("NRIC: " + pat.getNRIC());		
		
		List<Consultation> consList = pat.getConsRecords();
		if(consList.size()-1 > 0)
		{
			Consultation cons = consList.get(consList.size()-2);
			System.out.println("Last consultation date: " + cons.getDate());
		}	
		else
			System.out.println("Last consultation date: NIL");
		
		//Options
		System.out.println();
		System.out.println("1: View all consultation records");
		System.out.println("2: Enter illness description");
		System.out.println("3: Enter medicine prescription");
		System.out.println("0: Go back");
		
		//Get choice from user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		try 
		{
			//option = Integer.parseInt(br.readLine());	
			option = cur_options_q.poll();
			
			if(option == 0)
			{
				//Update the consultation.txt
				_clinic.appendToConsultationTxt(pat);
				
				//Go back to main page
				displayMainDoc();
			}
						
			switch(option)
			{
				case 1:
					System.out.println("All consultation records");
					System.out.println();
					
					//View all consultation records
					consList = _clinic.getConsultationRecords(pat);					
					Consultation cons;
					
					//Display all the records
					for(int i = 0; i < consList.size(); i++)
					{
						cons = consList.get(i);
						System.out.println("Date: " + cons.getDate());
						System.out.println("Illness description: " + cons.getIllnessDes());
						System.out.println("Medicine prescribed: ");
						
						//List of medicine prescribed
						List<String> medicineList = cons.getPrescription();
						if(medicineList!=null)
						{
							for(int j = 0; j < medicineList.size(); j++)
							{
								System.out.println(Integer.toString(j + 1) + ". " +  medicineList.get(j));
							}					
						}
						System.out.println();					
					}
					displayConsPat(pat);
					break;
					
				case 2: 
					//Enter illness description
					System.out.println("Please enter patient's illness description: ");
					String desc = br.readLine();
					
					//Set the description to patient's consultation records
					_clinic.setIllnessDesc(pat, desc);
					displayConsPat(pat);
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
					displayConsPat(pat);
					break;
					
			}
			
		}
		catch (NumberFormatException ne)
		{
			System.err.println("Choose from option 0-3");
			displayConsPat(pat);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void displayMedicine(Patient pat, List<String> medChosen)
	{
		List<Medicine> medList;
		if(medChosen == null)
			medChosen = new ArrayList<String>();
		
		System.out.print("Medicine chosen: ");
		for(int i = 0; i < medChosen.size(); i++ )
		{
			System.out.print(medChosen.get(i));
			System.out.print(", ");
		}
		
		//Get all the medicines available
		medList = _clinic.getMedicineList();
		System.out.println();
		int choice = 0;
		String input = cur_input_q.poll();
		for(int i = 0; i < medList.size(); i++ )
		{
			System.out.println(Integer.toString(i+1) + ": " + medList.get(i).getName());
			if(medList.get(i).getName().equalsIgnoreCase(input))
			{
				System.out.println("Cough syrup matched");
				choice = i+1;
				break;
			}
			else if(input.equalsIgnoreCase("back"))
			{
				System.out.println("back matched");
				choice = 0;
				break;
			}
		}
		System.out.println("0: Go back");
		
		//Get the input from doctor
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//If user choose to go back
		if(choice == 0)
		{
			//Set the medicine prescription in the consultation records of the patient
			_clinic.setMedPres(pat, medChosen);
			
			//Add to dispense medicine queue so the nurse can prepare the medicine
			_clinic.insertMedDispense(pat);
			
			//Return to select next patient for consultation
			//displayConsPat(pat);
			//Update the consultation.txt
			_clinic.appendToConsultationTxt(pat);			
			return;
		}
		
		//Add to medicine chosen list
		medChosen.add(medList.get(choice-1).getName());
		displayMedicine(pat, medChosen);
		
	}
	
	//********************************************************************Medicine Menu************************************************************************//
	private static void displayMed()
	{
		System.out.println();
		System.out.println("Medicine Records");
		System.out.println();
		System.out.println("1. Add new medicine");
		System.out.println("2. View all medicine");
		System.out.println("3. Restock medicine");
		System.out.println("0. Go back");
		
		//Get choice from user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		try 
		{
			//option = Integer.parseInt(br.readLine());	
			option = cur_options_q.poll();
			
			if(option == 0)
			{
				//Go back to nurse main page
				displayMainNurse();
			}
						
			switch(option)
			{
				case 1:
					//Add new medicine
					//Ask user for the following information to add new medicine
					System.out.println("Medicine name: ");
					//String name = br.readLine();
					String name = cur_input_q.poll();
					
					System.out.println("type (pill/liquid): ");
					//String type = br.readLine();
					String type = cur_input_q.poll();
					
					System.out.println("supplier: ");
					//String supplier = br.readLine();
					String supplier = cur_input_q.poll();
					
					
					//check if input is integer
					int stock;
					String stockString = null;
					do
					{
						System.out.print("stock:");
						//stockString = br.readLine();
						stockString = cur_input_q.poll();
						if(isInteger(stockString))
						{
							stock = Integer.parseInt(stockString);
							
							//Add into medicine records
							_clinic.addNewMedicine(supplier, name, type, stock);
							System.out.println("Medicine: " + name + "successfully added!");
							displayMed();
							break;
						}
						else
						{
							System.err.println("Please enter only numbers");
						}
					} while(true);
					
					
					break;
					
				case 2: 
					//View all medicine
					System.out.println("Medicine List");
							
					//Retrieve medicine list 
					List<Medicine> medList = _clinic.getMedicineList();
					Medicine med;
					
					//Display on the screen
					for(int i = 0; i < medList.size(); i++)
					{
						med = medList.get(i);
						System.out.println("Name: " + med.getName());
						System.out.println("Type: " + med.getType());
						System.out.println("Supplier: " + med.getSupplier());
						System.out.println("Stock: " + med.getStock());
						System.out.println();
						
						//Test case 8-1 
						//Check if medicine is successfully added to database
						if(cur_name.equalsIgnoreCase("Test case 8-1"))
						{
							//Check for the just added medicine
							if(med.getName().equalsIgnoreCase(cur_output))
							{
								//Matched, successful
								cur_results = "Pass";
								appendFile();
								return;
							}
						}
						
						//Check for the med
						//test case 1-1
						//Check if medicine stock is successfully restocked
						if(med.getName().equalsIgnoreCase(identifier))
						{
							//Check for the stock 
							int output_stock = Integer.parseInt(cur_output);
							if(med.getStock() == output_stock && check)
							{
								System.out.println("Pass");
								cur_results = "Pass";
								appendFile();
								//return;
							}
							else if(med.getStock() != output_stock && check)
							{
								System.out.println("FAIL");
								cur_results = "Fail,stock does not match expected stock output";
								appendFile();
								//return;
							}
						}
					}
					
					
					//displayMed();
					break;
									
				case 3:
					//Restock medicine
					restockMenu();
					
					if(cur_name.equalsIgnoreCase("Test case 7-1"))
					{
						return;
					}
					
					displayMed();
					
					//Go back to Medicine menu page
					//displayMed();
					break;
					
				default: 
					System.out.println("Choose from option 0-3");
					//Go back to Medicine menu page
					displayMed();
					break;
					
			}
			
		}
		catch (NumberFormatException ne)
		{
			System.err.println("Choose from option 0-3");
			displayMed();
		}
	}
	
	private static void restockMenu()
	{
		System.out.println();
		System.out.println("Restock menu");
		System.out.println();
		//Assume only need to restock medicine that are low on stock
		//Display all medicine that are low on stock only
		List<Medicine> medList = _clinic.checkLowStockMed();
		Medicine med;
		int option = 0;
		
		if(cur_name.equalsIgnoreCase("Test case 7-1"))
		{
			//Daily report test, if total med to be restocked is 5, success, else fail
			if(medList.size() == Integer.parseInt(cur_output))
			{
				cur_results = "Pass";
				appendFile();
				return;
			}
			else
			{
				cur_results = "Fail, number of medicine to restock > 5";
				appendFile();
				return;
			}
		}
		
		identifier = cur_input_q.poll();
		//Display on the screen
		for(int i = 0; i < medList.size(); i++)
		{			
			med = medList.get(i);
			
			//Find the medicine according to the input medicine and set the appropriate option
			if(med.getName().equalsIgnoreCase(identifier))
			{
				option = i+1;
			}
			
			System.out.println(Integer.toString(i + 1)+ ". Name: " + med.getName());
			System.out.println("   Type: " + med.getType());
			System.out.println("   Supplier: " + med.getSupplier());
			System.out.println("   Stock: " + med.getStock());
			System.out.println();
		}
		
		//if option still remains to be 0, it means that the medicine is not found in the database
		//or no restock is available
		if(option == 0)
		{
			cur_results = "Fail, " + identifier + " is not found on database or restock not needed";
			appendFile();
			//Set check variable to fail
			check = false;
			return;
		}
		
		System.out.println("0: Go back");
				
		//Check if user want to restock
		System.out.println("Please enter the medicine you wish to restock");
		//Get choice from user
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try 
		{
			//option = Integer.parseInt(br.readLine());	
			//option = cur_options_q.poll();
			
//			if(option == 0)
//			{
//				//Go back to medicine main page
//				displayMed();
//			}
						
			//Get the medicine user has selected
			if(medList.size() == 0)
			{
				//If there are no stocks to restock
				restockMenu();
			}
			Medicine restockMed = medList.get(option - 1);
			
			
			
			//Prompt user quantity to restock
			System.out.println("Medicine: " + restockMed.getName());
			System.out.println("Enter quantity you wish to add: ");
			
			int quantity;
			//check if input is integer
			String stockString = null;
			do
			{
				System.out.print("stock:");
				//identifier = cur_input_q.poll();
				
				//stockString = br.readLine();
				stockString = cur_input_q.poll();
				if(isInteger(stockString))
				{
					quantity = Integer.parseInt(stockString);
					System.out.print(quantity);
					//Restock
					_clinic.restockMed(restockMed, quantity);
					System.out.println("Medicine: " + restockMed.getName() + " successfully restocked!");
					//restockMenu();
					//break;
					//displayMed();
					return;
				}
				else
				{
					System.err.println("Please enter only numbers");
				}
			} while(true);	
			
		}
		catch (NumberFormatException ne)
		{
			System.err.println("Choose from option 0-" + medList.size());
			restockMenu();
		}
		
	}
	
	//********************************************************************Search Menu************************************************************************//
	private static void searchMenu()
	{
		System.out.println();
		System.out.println("Search menu");
		System.out.println();
		System.out.println("1. Search for phrase in illness description field in consultation records");
		System.out.println("2. Search for medication prescribed to patient in consultation records");
		System.out.println("0. Go back");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int option = 0;
		
		try 
		{
			//option = Integer.parseInt(br.readLine());	
			option = cur_options_q.poll();
			
			if(option == 0)
			{
				//Go back to nurse main page
				displayMainNurse();
			}
			
			switch(option)
			{
				case 1:
					//Search for phrase in illness description field in consultation records
					//Get word that user wants to search
					System.out.println("Enter word you wish to search under illness description of patients' consultation records: ");
					
					//String word = br.readLine();
					String word = cur_input_q.poll();
					identifier = word;
					Patient pat;
					
					List<Consultation> consList;
					Consultation cons;
					
					//Search it
					List<Patient> patList = _clinic.searchIllnessDes(word);
					
					//Nothing found
					if(patList.size() == 0)
					{
						System.out.println("No match found");
						cur_results = "Fail, no match found";
						appendFile();
						return;
						//searchMenu();
						
					}
					
					for(int i = 0; i < patList.size(); i++)
					{
						pat = patList.get(i);
						
						//Display information to user
						System.out.println("Patient name: " + pat.getName());
						System.out.println("NRIC: " + pat.getNRIC());
						
						System.out.println();
						
						//Get consultation records
						consList = pat.getConsRecords();
						
						//Display consultation records that consist the word that user finds
						for(int j = 0; j < consList.size(); j++)
						{
							cons = consList.get(j);
							System.out.println("Consultation date: " + cons.getDate());
							System.out.println("Illness description: " + cons.getIllnessDes());
							if(cons.getNRIC().equalsIgnoreCase(cur_output) && cons.getIllnessDes().equalsIgnoreCase(identifier))
							{
								cur_results = "Pass";
								appendFile();
								break;
							}
							System.out.println("Medicine prescribed: ");
							
							//List of medicine prescribed
							List<String> medicineList = cons.getPrescription();
							if(medicineList!=null)
							{
								for(int k = 0; k < medicineList.size(); k++)
								{
									System.out.println(Integer.toString(k + 1) + ". " +  medicineList.get(k));
								}					
							}
							
							System.out.println();
						}
					}
					//searchMenu();
					break;
				case 2:
					//Search for medication prescribed to patient in consultation records
					//Get word that user wants to search
					System.out.println("Enter medication you wish to search under medication prescribed to patient in consultation records: ");
					
					word = br.readLine();
					
					//Search it
					patList = _clinic.searchPrescription(word);
					
					//Nothing found
					if(patList.size() == 0)
					{
						System.out.println("No match found");
						searchMenu();
					}
					
					for(int i = 0; i < patList.size(); i++)
					{
						pat = patList.get(i);
						
						//Display information to user
						System.out.println("Patient name: " + pat.getName());
						System.out.println("NRIC: " + pat.getNRIC());
						
						System.out.println();
						
						//Get consultation records
						consList = pat.getConsRecords();
						
						//Display consultation records that consist the word that user finds
						for(int j = 0; j < consList.size(); j++)
						{
							cons = consList.get(j);
							System.out.println("Consultation date: " + cons.getDate());
							System.out.println("Illness description: " + cons.getIllnessDes());
							
							System.out.println("Medicine prescribed: ");
							
							//List of medicine prescribed
							List<String> medicineList = cons.getPrescription();
							if(medicineList!=null)
							{
								for(int k = 0; k < medicineList.size(); k++)
								{
									System.out.println(Integer.toString(k + 1) + ". " +  medicineList.get(k));
								}					
							}
							
							System.out.println();
						}
					}
					searchMenu();
					break;
				default:
					System.err.println("Choose from option 0-2");
					searchMenu();
			}
						
			
		}
		catch (NumberFormatException ne)
		{
			System.err.println("Choose from option 0-2");
			searchMenu();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Append new patient record to existing patients.txt
	private static void appendFile()
	{
		try 
		{
			//Notice the second parameter "true"
			//This is needed to ensure the filewriter is in APPEND mode
			FileWriter out = new FileWriter("adt_results.txt",true);
			BufferedWriter bw = new BufferedWriter(out);
			
			//Write a newline first
			//bw.newLine();
			//bw.newLine();
			
			//Next, write all the information of the new patient		
			bw.write(cur_name);
			bw.newLine();
			bw.write(cur_results);
			bw.newLine();
			
				
			bw.close();
			
		} 
		catch (IOException e) 
		{		
			e.printStackTrace();
		}
		
	}
	
	//********************************************************************Main************************************************************************//
	public static void main(String args[])
	{
		ADT adt = new ADT();
		//adt.displaySelectUser();	

	}
	
	
	
}


