package turnPage;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javax.swing.event.*;
import java.util.List;

public class FirstPage {
	
	private JFrame one;
	private JFrame two;
	private JButton next;
	private Point region;
	
	// class variables
	private JTextField fn; //first name
	private JTextField mn; //middle name
	private JTextField ln; //last name
	
	private JTextField userName; //user name
	private JPasswordField psw; //password
	
	//labels for each box
	private JLabel fnLab;
	private JLabel mnLab;
	private JLabel lnLab;
	private JLabel userLab;
	private JLabel pswLab;
	
	private static Hashtable<Integer, String> ClientIndex = new Hashtable<Integer, String>(); //client index and name
	private static int index=0;
	
	private static String[] accountType = {"checking", "savings"};
	
	private static Hashtable<String, List<String>> ClientInfo = new Hashtable<String, List<String>>(); //client name and account type
	private List<String> userInfo;
	
	// new file
	File newfile;
	char[] a = new char[2048];
	
	Enumeration indexKey;
	Enumeration indexValue;
	
	public FirstPage() { //constructor
		
		one = new JFrame("Page #1");
		one.setLayout(new FlowLayout());
		one.setLocation(400,100); 
		one.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		one.setSize(350,200);
		
		//add components to the frame
		fnLab = new JLabel("First Name");
		fn = new JTextField(20);
		one.add(fnLab);
		one.add(fn);
				
		mnLab = new JLabel("Middle Name");
		mn = new JTextField(20);
		one.add(mnLab);
		one.add(mn);
				
		fnLab = new JLabel("Last Name");
		ln = new JTextField(20);
		one.add(fnLab);
		one.add(ln);
		
		userLab = new JLabel("User Name");
		userName = new JTextField(20);
		one.add(userLab);
		one.add(userName);
				
		pswLab = new JLabel("Password");
		psw = new JPasswordField("", 10);
		psw.setEchoChar((char) 0);
		one.add(pswLab);
		one.add(psw);
		
		//add next page button
		next = new JButton("Next Page");
		next.addActionListener(			
				new ActionListener () {
					public void actionPerformed(ActionEvent e) {
						
						if (e.getSource() == next) {	//store new client information
						
						region = one.getLocation();
					//	one.setVisible(false);
						goNextPage(keepName(), keepUserName(), keepPsw()); //go to next page by instantiate second frame
					}
				  }	//end of actionPerformed
				} //end of actionListener class
			); //end of addActionListener
		
		one.add(next);
		
		one.setVisible(true);
		
	} //end of constructor First Page
	
	public void goNextPage(String name, String usr, char[] code) { //instantiate next page object
		
		SecondPage sp = new SecondPage(name, usr, code);

	}
	
	public String keepName() {  //record client information
		
		String fullName="";
		
		fullName = fn.getText();
		fullName = fullName.concat(" ");
		
		if(mn.getText().length() >0) {
			fullName = fullName.concat(mn.getText());	
			fullName = fullName.concat(" ");
		}

		fullName = fullName.concat(ln.getText());
		

		return fullName;
		
	}
	
	public String keepUserName() {
		
		 String user="";
		 
		 user = userName.getText();
		 
		 return user;
	
	}
	
	public char[] keepPsw() {
		
		char[] passCode= new char[10];
		
		passCode = psw.getPassword();
		 
		return passCode;
	
	}
	
	public boolean checkDatabase(String currentName, List<String> perClientInfo) {
		
		boolean skip=false, usrExist=false, anotherClient=false;
		
		System.out.printf("Current Client Information Entered For Checking: \n");
		System.out.printf("Client Name: %s \n", currentName);
		System.out.printf("User Name: %s \n", perClientInfo.get(0));
		System.out.printf("Password: %s \n", perClientInfo.get(1));
		System.out.printf("Account Type: %s \n", perClientInfo.get(2));
		
		if(index == 0) { //always store in the database if this is the first client
			
			System.out.println("Store info in the database");
			data_store(index, currentName, perClientInfo); //store client information	
			index++;
			anotherClient = true;
			
		}
		else {  // starting from the 2nd entry
		
		anotherClient = false;
		//compare new name to the list in the hashtable
		Enumeration i = ClientIndex.keys();
		//int num_elements = i.hasMoreElements().;
		//System.out.printf("Database contains: %d elements", (int) i.hasMoreElements() );
		
		while(i.hasMoreElements()) { //if there are more client in the database
			
			//compare fullname
			int id = (int)i.nextElement();
			int k = currentName.compareTo(ClientIndex.get(id));
			System.out.printf("compare result: %d", k);
			
			if(k ==0) { //if found match, stop loop through
				
				JOptionPane.showMessageDialog(null, "Name already exist!");
				//System.out.println("This client name already in the Database!");
				skip=true;
				break;
			} //end of if
			
		} //end of while
		
		if(skip==false) { // if a new client name
			
		//	index++;     // increase the client index
			System.out.printf("This is a new name, client# %d \n", index);
			
			/*********check to see user name already exist in the database? ********/
			Enumeration j = ClientInfo.keys();
			
			while(j.hasMoreElements()) {  //loop through name in database, check if duplicant user name
				
				String nxt = (String) j.nextElement();
				String UsrNdataBase = ClientInfo.get(nxt).get(0); //UserName in the database
				String currentUsr = perClientInfo.get(0); //current user try to register
				
				System.out.printf("current user name(entered): %s \t", currentUsr);
				System.out.printf("current user named(database): %s \t", UsrNdataBase);
				
				int k = currentUsr.compareTo(UsrNdataBase);  //compare 
				System.out.println("User name compare result: " + k);
				
				if (k == 0) { //if matches, don't store 					
							  //ask user to enter a new user name
					usrExist = true;
					break;					
				}

			} //end of while loop
			
			if (usrExist == true) {
				JOptionPane.showMessageDialog(null, "User Name already exist! Please enter a new User Name");
			}
			else { // no duplicant user name
				
				System.out.println("Store info in the database");
				data_store(index, currentName, perClientInfo); //store client information	
				index++;     // increase the client index
			}
		} //end of if skip==false

		if((skip == false) && (usrExist == false)) {
			//if new client, or new username
			anotherClient = true;
		}
	  } //end of else 
		
	  return anotherClient;
	
	} //end of checkdDtabase method
	
	//save new client information(name, User name, password, account type)
	public void data_store(int id, String clientName, List<String> pci) {
		
		ClientIndex.put(id, clientName);
		ClientInfo.put(clientName, pci);
		
		System.out.printf("Store Data() - Client Index: %d \n", ClientIndex.keys().nextElement());
						
	}//end of data store method

	
	//write to a file
	public void write_to_file() {
		
		String outBuffer="";
		int c_index;
		
		//newfile = new File("C:\\Users\\Waising\\Desktop\\accountDatabase.csv");
		newfile = new File("C:\\Users\\Waising\\Desktop\\accountDatabase.txt");
		
		try { //try to create a new file
			newfile.createNewFile();
			
		}
		catch(Exception e) { //print exception 
			e.printStackTrace();
			
		}
		
		try{//now write to the new file
			FileWriter filew = new FileWriter(newfile);
		    BufferedWriter buffw = new BufferedWriter(filew);
		    
		    Enumeration a = ClientIndex.keys();
		    
		    while(a.hasMoreElements()) {
	
		    	c_index = (int) a.nextElement();
		    	outBuffer = Integer.toString(c_index);
		    	
			    buffw.write("Client Index:" + outBuffer );
			    buffw.newLine();  //separate the line
			    
			    buffw.write("Client Name:" + ClientIndex.get(c_index));
			    buffw.newLine();  //separate the line
			    
		    }
		    
		    buffw.close();
		    System.out.println("Java output file written successfully");
			
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Written exception occured!");
		}
		
	}
	
	//nested class = page#2
	public class SecondPage{
				
		private JButton back;
		private JButton exit;
		private JButton save;
		
		private JList list;
		private JLabel label;
		private JRadioButton rButt;
		
		Enumeration listName; 
		
		public SecondPage(String name, String usr, char[] code) {  //constructor
			
			one.setVisible(false);
			two = new JFrame("Page #2");
			two.setLocation(region.x, region.y);
			
			two.setLayout(new FlowLayout());
			two.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			two.setSize(350,200);
			
			label = new JLabel("Account Type");
			two.add(label);
			list = new JList(accountType);
			list.setVisibleRowCount(2);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			two.add(new JScrollPane(list));
			
			save = new JButton("Save");
			save.addActionListener(  //take action once save button is clicked
					
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							//save account type
							if (e.getSource() == save) {
								
								//System.out.printf("Name passed in: %s \n", name);
								  System.out.printf("user name passed in: %s \n", usr);
								
								//save to the temp list array						
								List<String> tempArray = new ArrayList();
								
								tempArray.add(usr);
								tempArray.add(String.valueOf(code));
								tempArray.add(accountType[list.getSelectedIndex()]);
								
								if(checkDatabase(name, tempArray) == true) {
									
									int k = JOptionPane.showConfirmDialog(null, String.format("Would you like to Enter another name?"),"Confirmation", JOptionPane.YES_NO_OPTION);
									
									if(k==0) { //0=YES =>instantiate a new frame
										
										newClient();
									}
									else {  //1 = NO =>exit
										
										printData();									
										write_to_file();
										
									//	FileFunc filewrite = new FileFunc();
										//FileInOut fileWrite = new FileInOut();									
										System.exit(0);
									}							
								} // end if checkDatabase()
								
							} //end of if click save button
							
						}//end of actionPerformed method
									
					} //end of actionListenerclass
				); //end of addActionListener method
			
			two.add(save);
			
			back = new JButton("Prev Page"); //back button	
			back.addActionListener(			
					new ActionListener () {
						public void actionPerformed(ActionEvent e) {
							goPrevPage(); //go to next page by instantiate second frame
						}
					}	
				); //end of addActionListener
	
			two.add(back);
			
			exit = new JButton("Exit");
			exit.addActionListener( 
					
					new ActionListener () {
						public void actionPerformed(ActionEvent e) {
							System.exit(0); //exit the system
						}
					}
				); //end of exit action listener method
			
			two.add(exit); 
			
			two.setVisible(true);
										
		} //end of constructor		
	
		public void goPrevPage() { //back to previous frame
			
			region = two.getLocation();	
			one.setLocation(region.x, region.y);
			one.setVisible(true);
			two.setVisible(false);

		}	// end of goPrevPage method
		
		public void newClient() {  //instantiate another frame when user wants another entry
			two.setVisible(false);
			FirstPage newPerson = new FirstPage();
		}
		
		public void printData() {
			
			System.out.printf("************** Printing the database ******************** \n");
			Enumeration iter1 = ClientIndex.keys();
			Enumeration iter2 = ClientInfo.keys();
			
			while(iter1.hasMoreElements()) {
				int count = (int) iter1.nextElement();
				System.out.printf("Client Index: %d, Client Name: %s \n", count, 
						ClientIndex.get(count));
			}
			
			System.out.printf("-------------------------------------------------------- \n");
			while(iter2.hasMoreElements()) {
				String countString = (String) iter2.nextElement();
				System.out.printf("Client Name: %s \n", countString);
				System.out.printf("User Name: %s \n", ClientInfo.get(countString).get(0));
				System.out.printf("Password: %s \n", ClientInfo.get(countString).get(1));
				System.out.printf("Account Type: %s \n", ClientInfo.get(countString).get(2));
			}

		} //end of print data method		
		
	} //end of page2 class
	
} //end of first page class - outer class
