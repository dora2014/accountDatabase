package turnPage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class FileFunc {

	File newfile;
	char[] a = new char[2048];
	String pathDefault;
	
	public FileFunc() {
		//constructor
		newfile = new File("C:\\Users\\Waising\\Desktop\\accountDatabase.txt");
		pathDefault = "C:\\Users\\Waising\\Desktop";
		
	//	if (newfile.exists())
	//		System.out.println("File exists!");
	//	else {
			try { //try to create a new file
				newfile.createNewFile();
				
			}
			catch(Exception e) { //print exception 
				e.printStackTrace();
				
			}
			
			try{//now write to the new file
				FileWriter filew = new FileWriter(newfile);
			    BufferedWriter buffw = new BufferedWriter(filew);
			    
			  //  buffw.write("This is the first line\nThis is the second line\n");
			    buffw.write("This is the first line");
			    buffw.newLine();  //separate the line
			    buffw.write("This is the second");
			    buffw.close();
			    System.out.println("Successful on contructing FileInOut object\n a java text file is written");
				
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("Written exception occured!");
			}
			
	//	} //end else
		
	}//end of constructor
	
	//This method will read from a text file
	public void readFile() {
		
		try {
				FileReader filer = new FileReader("C:\\Users\\Waising\\Desktop\\menus rate.txt");
				BufferedReader buffr = new BufferedReader(filer);
		
				//char[] a = new char[512];
				buffr.read(a);
				//System.out.println(a.length);
				
			//	for(char c: a)
			//		System.out.print(c);
				buffr.close();
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		
	}//end of readFile
	
	//This method will write to a file
	public void writeFile(String fname) {
		
			//newfile = new File("C:\\Users\\Waising\\Desktop\\write.txt");
		
	//		try { //try to create a new file
				/*	newfile.createNewFile(); */
					
		//		}
		//	catch(Exception e) { //print exception 
			//	e.printStackTrace();
					
			//	}
			String fileLoc;
			
			//fileLoc = pathDefault + "\\" + fname;
			fileLoc = pathDefault + "\\" + fname;
			
			System.out.println("File passed in: " + fname);
			System.out.printf("File to write: %s\n", fileLoc);
			
			try{//now write to the new file
					FileWriter filew = new FileWriter(fileLoc);
				    BufferedWriter buffw = new BufferedWriter(filew);
				    buffw.write(a);
				    buffw.close();
				    System.out.println(fname + " file is written successfully!");
					
				}
				catch(Exception e) {
					e.printStackTrace();
					System.out.println("Written exception occured!");
				}
		
		}//end of writeFile method
	
	
	
	
}
