package boss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
//this is where all the methods will be
public class Store {
	
	Vector <Products> item; // Vector to Hold Products
	Vector <Users> account; //Vector to Hold All Users
	Vector <Orders> invoice; //Vector to Hold Order Information
	int numUupdates;
	int loggedinuser = -1;
	Scanner Keyboard;
	
	int test = 0;
	//Database objects
		 Connection connection;
		 Statement statement;
		 ResultSet resultSet;
	/*******************************************************CONSTRUCTOR*****************************************************/

	Store()
	{
		item = new Vector<Products>();
		account = new Vector<Users>();
		invoice = new Vector<Orders>();
		Keyboard = new Scanner (System.in);
		
	}
	/*******************************************************ENDOFCONSTRUCTOR*****************************************************/

	/*******************************************************LOAD METHODS
	 * @throws SQLException ******************************************************/
	//this method calls the load methods for each text files
	
	void loadData() throws IOException, SQLException
	{
		
	
	loadUsers();
	loadProducts();
	
	}
	
	/*******************************************************LOAD USER
	 * @throws SQLException ******************************/
	
	void loadUsers() throws IOException, SQLException {
		 String i = "";
		 String en = "";
		 String psw = "";
		 String pn = "";
		 String em = "";
		 String n = "";
		 Boolean isEm = false;
		 int totalrows = 0, index = 0;
		 resultSet = statement.executeQuery("SELECT * FROM Users");
		 while (resultSet.next()) //tests for the eof
			{   totalrows = resultSet.getRow();
		
				i = resultSet.getString("UserID"); // or 	i = resultSet.getString(1);
				en = resultSet.getString("UserName");
				psw = resultSet.getString("Password");
				pn = resultSet.getString("Phone");
				em =  resultSet.getString("Email");
				n = resultSet.getString("FullName");
				isEm = resultSet.getBoolean("isEmployee");
				account.add(new Users(i,en,psw,pn,em,n,isEm));
				index++;
			}
		System.out.println("Employees Loaded");
	 } 
/*******************************************************END OF LOAD USER*****************************/
	
/*******************************************************LOAD PRODUCTS*****************************/

	//Need to Check this Load Products - Not sure if logic is right
	
	void loadProducts() throws IOException
    {
	    //create a File for reading from your data file 
	    FileReader fr=new FileReader("products.dat");
	    BufferedReader br = new BufferedReader(fr);
	    //create the variables for each field in the file
	    String id = ""; //productID
	    String pn = ""; //productName
	    double pc = 0.00; //productPrice
	    String dc = "";
	  
	    
	    //Create a string to read each line and a tokenizer to separate at the field at the comma
	    String eachLine = "";
	    StringTokenizer st;
	    eachLine = br.readLine(); //read the first line
	    while (eachLine != null) //tests for the eof
	    { st = new StringTokenizer(eachLine, ","); //read each line
	    
	    	while (st.hasMoreTokens()) //read each field
		    { //remember the order of the text file
			    id = st.nextToken();
			    pn = st.nextToken();
			    pc = Double.parseDouble(st.nextToken());
			    dc = st.nextToken();
			    item.add (new Products(id, pn, pc, dc)); //add the product to the Vector
			    eachLine = br.readLine(); //read the next line
		    }//end of reading one line
	    }//end of reading the file
    
    br.close(); //close the file
    System.out.println("Bakery Loaded");
    } //end of loadProducts() method

	/*******************************************************END OF LOAD PRODUCTS******************************/

	
	/*******************************************************CHECK PRODUCTS******************************/

	
	void checkProducts() {
		
	}
	
	
	
	
	
	/*******************************************************END OF CHECK PRODUCTS******************************/

	
	
/*******************************************************ADD NEW USER******************************/

		void addNewUser() {
			//DISPLAY MENU HEADER
			System.out.println("\n\n");
			System.out.println("-------------------------------------------");
			System.out.println("            CREATE NEW USER               ");
			System.out.println("-------------------------------------------");	

				Scanner input = new Scanner(System.in);
				
				System.out.print("Enter User ID: ");
				String userID = input.nextLine();
				
				System.out.print("Enter New UserName: ");
				String username = input.nextLine();
				
				System.out.print("Enter New Password ");
				String password = input.nextLine();
				
				System.out.print("Enter Phone: ");
				String phone = input.nextLine();
				
				System.out.print("Enter Email: ");
				String email = input.nextLine();
				
				System.out.print("Enter Name: ");
				String name= input.nextLine();
				
				System.out.print("Is User an Employee(True/False): ");
				Boolean employee = input.nextBoolean();
				
				Users nUser = new Users (userID,username, password, phone, email,name,
						employee);
				
				
				account.add(nUser);
				
			
				numUupdates++;	//update the User flag
				
				
			}
		
/*******************************************************ENDOFADDUSER******************************/

/*******************************************************VIEW PRODUCTS******************************/

	

		
/*******************************************************END OF VIEW PRODUCTS******************************/

		
/*******************************************************LOGIN METHOD * @throws IOException ***************/

		void login() throws IOException {
			String username ="", password ="";
			
			boolean isValid = false;
			int counter = 0;
			System.out.println("************************LOGIN SCREEN**************************");
			
			do {
				counter++;
				System.out.print("Enter UserName: ");
				username = Keyboard.next();
				System.out.print("Enter Password: ");
				password = Keyboard.next();
				
				for(int i = 0; i < account.size(); i++) {
					if(username.equals(account.get(i).getUsername()) && password.equals(account.get(i).getPassword())) {
						isValid = true;
						
						loggedinuser = i;
				
					}
				}
				System.out.println("Invalid Username or Password!");
			} while(counter < 3 && !isValid);
			
			if(!isValid) {
				System.out.println("Max Attempts Exceeded, Shutting Down System");
				System.exit(0);
				
			}
			//Welcome the Logged In User
			
			System.out.println("Welcome "+ account.get(loggedinuser).getUsername() + " Employee: " + account.get(loggedinuser).isEmployee());
			if(account.get(loggedinuser).isEmployee()) {
				
				//showMenu();
				
			}else{
				//showEmployeeMenu();
			}
			
		}
		
		/*******************************************************END OF LOGIN METHOD***************************************************/
		
		/*******************************************************WELCOME METHOD******************************************************/
		
		//Welcome Menu
		void welcome()
		
		{
			System.out.println("\n\n");
			System.out.println("-------------------------------------------");
			System.out.println("               WELCOME MENU                ");
			System.out.println("-------------------------------------------");	
			
			System.out.println("*********************************************");	
			System.out.println("Welcome to BOSS System!");
			System.out.println("**********************************************");	
		}
		
		/**************************************************END OF WELCOME METHOD******************************************************/
		
		/*******************************************************SHOW USER MENU METHOD * @throws IOException *********/
		
		//arrays = 0 (index - 1)
		
		 //Display Menu
		void showMenu() throws IOException
		{	
			int option = -1;

			do {
				
				System.out.println("\n\n");
				System.out.println("-------------------------------------------");
				System.out.println("             BOSS USER MENU            ");
				System.out.println("-------------------------------------------");	
				System.out.println("1. View Bakery");
				System.out.println("2. Place Order");
				System.out.println("3. Check Order Status");
				System.out.println("4. Cancel Order");
				System.out.println("5. Display Invoice");
				System.out.println("6. Register New User");
				System.out.println("7. Exit");
				System.out.print("Choose a system process (1-7): ");
				option = Keyboard.nextInt();
				switch (option)
				{
					case (1):
					{	 checkProducts();
						break;
					}
					case (2):
					{	//placeOrder();
						break;
					}
					case (3):
					{	 //chkStatus();
						break;
					}
					case (4):
					{	//cnclOrder();
						break;
					}
					case (5):
					{	//showInvoice();
						break;
					}
					case (6):
					{	addNewUser();
						break;
					}
					case (7):
					{	 //updateRecords();
						System.out.println("\n\n");
						System.out.println("****************************************************************************");	
						System.out.println("Thank you for using BOSS - Program Terminated!");
						System.out.println("****************************************************************************");	
						System.exit(0);
					}
					default:
					{	System.out.println("Invalid choice, please choose between 1-7"); }
			
				} //end of switch
				}while (option != 7);
				
			
		}
		
		/************************************************END OF SHOW USER MENU METHOD
		 * @throws IOException ******************************************************/
		
		
		/************************************************SHOW EMPLOYEE MENU METHOD
		 * @throws IOException ******************************************************/
		 //Display Menu
			void showEmployeeMenu() throws IOException
			{	
				int option = -1;

				do {
					//DISPLAY MENU HEADER
					System.out.println("\n\n");
					System.out.println("-------------------------------------------");
					System.out.println("                BOSS EMPLOYEE MENU               ");
					System.out.println("-------------------------------------------");	
					System.out.println("1. View Bakery");
					System.out.println("2. Update Order Status");
					System.out.println("3. Contact Customer");
					System.out.println("4. Exit");
					System.out.print("Choose a system process (1-4): ");
					option = Keyboard.nextInt();
					switch (option)
					{
						case (1):
						{	 checkProducts();
							break;
						}
						case (2):
						{	 //updateStatus();
							break;
						}
						case (3):
						{	 //contactUser();
							break;
						}
						case (4):
						{	// updateRecords();
							System.out.println("Thank you for using BOSS System, - Program Terminated!");
							System.out.println("****************************************************************************\n\n");	
							System.exit(0);
						}
						default:
						{	System.out.println("Invalid choice, please choose between 1-7"); }
				
					} //end of switch
					}while (option != 4);
					
				
			}
			
			/************************************************END OF SHOW MENU METHOD******************************************************/
		
			
	/********************************************************UPDATE RECORDS* @throws IOException ***/
			 void updateRecords() throws IOException
			 {
				 //update Employee records
				 if (numUupdates >0)
				 {
					 FileWriter fw=new FileWriter("users2.dat", true);
					 BufferedWriter bw = new BufferedWriter(fw);
					 String em = "false";
				
						for (int i = 0; i < account.size(); i++)
						{	 bw.write(account.get(i).getUserID() + "," +
							 account.get(i).getUsername() + "," + 
							 account.get(i).getPassword() + "," +
							 account.get(i).getPhone()+ "," +
							 account.get(i).getEmail() + "," +
							 account.get(i).getName() + "," );
								
							if (account.get(i).isEmployee()) em = "true";
								bw.write(em +  "\n");
								em = "false"; //reset manager
						} //write each line separated by the enter key
						
						 System.out.println("User File Update Successfully");
						bw.close();
				 
			
				 }
				 
		}
			 void connectDB() throws ClassNotFoundException, SQLException
				{
				 // Step 1: Loading or registering JDBC driver class 
			
					Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
				
				 // Step 2: Opening database connection
				 String msAccDB = "SIMS.accdb";
				 String dbURL = "jdbc:ucanaccess://" + msAccDB; 
				 
				 // Step 3: Create and get connection using DriverManager class
				 connection = DriverManager.getConnection(dbURL); 
				 
				 // Step 4: Creating JDBC Statement 
				 // It is scrollable so we can use next() and last() & It is updatable so we can enter new records
				 statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				 ResultSet.CONCUR_UPDATABLE);
				 
				 System.out.println("Database Connected!");
				}
	/************************************************ END OFUPDATE RECORDS******************************************************/

}