package acc.model;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.PrintWriter;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class UserList {
	private ArrayList<UserAcc> list = new ArrayList<UserAcc>();
	private String inputFile;
	private boolean modified = false;
	private Integer agentCount =0;
	private ModelEvent blocked = new ModelEvent(this, 0, "Blocked", null);
	private ModelEvent running = new ModelEvent(this, 0, "Running", null);
	
	public String fillList( String file ) throws FileNotFoundException {
		String line;
		UserAcc temp;
		
		this.inputFile = file;  //save for later use in save()
		
		File input = new File( file );
		
		if( input.exists() ) {
			Scanner inFile = new Scanner(input);
			
			boolean exist = false;
			String[] dismiss = { "Dismiss" };
		    
			//skip first two lines, the column name lines. Also check if file is empty.
			if( inFile.hasNextLine() ) inFile.nextLine();
			else {
				JOptionPane.showOptionDialog( new JDialog(), "Input File Empty!", "Input Format Error",
    				    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
				System.exit(0);
			}
			if( inFile.hasNext() ) inFile.nextLine();
			else {
				JOptionPane.showOptionDialog( new JDialog(), "Input File missing proper formatting, refer to readme.txt.", 
						"Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
				System.exit(0);
			}
			
			int lineCount = 3; //starts on 3rd line

			if( !(inFile.hasNext()) ) {
				JOptionPane.showOptionDialog( new JDialog(), "Input File has no data, or is missing proper fromatting, refer to readme.txt."
					    , "Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
				System.exit(0);
			}
			
			while ( inFile.hasNext() )
	        {
	            line = inFile.nextLine();   // read the next line
	            
	            //JOptionPane.showMessageDialog( new JDialog(), line); //Debugging line to output current ingoing entry.
	            
	            String[] info = line.split( "[|]" );
	            info[0] = info[0].substring( 0, ( info[0].length() - 1 ));
	            info[1] = info[1].substring( 1, ( info[1].length() - 1 ));
	            info[2] = info[2].substring(2);
	            
	            if( info[1].matches("[0-9]+") ) { //check if id is only numeric
	            	
	            	exist = idExists( Integer.parseInt( info[1] ) ); //check if id is unique
		            
		            if ( !exist) {
		            	if( !( Pattern.matches( "[a-zA-Z ]+", info[0]) ) ) { //check if name has only letters and a space
		            		JOptionPane.showOptionDialog( new JDialog(), "Name must only contain letters!\n" + "Error on line " + lineCount + " of input file.\n"
			            			+ "This entry will be skipped.", "Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
		            	}
		            	else {
		            		if( info[2].matches("[0-9.]+") ) { //check that balance is only numeric, might contain a decimal
		            			temp = new UserAcc( info[0], Integer.parseInt( info[1] ), Double.parseDouble( info[2] ) );
			            		list.add( temp );
		            		}
		            		else {
		            			JOptionPane.showOptionDialog( new JDialog(), "Balance should start with a $ and must only contain numbers and a decimal place! Ex. $45.76\n" + "Error on line " + lineCount + " of input file.\n"
				            			+ "This entry will be skipped.", "Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
		            		}
		            	}
		            }
		            
		            else {
		            	JOptionPane.showOptionDialog( new JDialog(), "ID must be unique!\n" + "Error on line " + lineCount + " of input file.\n"
		            			+ "This entry will be skipped.", "Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
		            }
	            }
	            else {
	            	JOptionPane.showOptionDialog( new JDialog(), "ID must only contain numbers!\n" + "Error on line " + lineCount + " of input file.\n"
	            			+ "This entry will be skipped.", "Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
	            }
	            ++lineCount;
	        }
			JOptionPane.showOptionDialog( new JDialog(), "If missing or incorrect data values, check formatting of input file in readme.txt.",
					"Informative Dialog", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, dismiss, dismiss );
			inFile.close();
			return ("Successful");
		}

		else return ("Not Found");
	}
	
	private boolean idExists(int id) {
		for( int i = 0; i < list.size(); ++i ) {
			if( id == list.get(i).getId() ) return true;
		}
		return false;
	}
	

	public void sort() {
		boolean change = true;
		
		while( change ) {
			change = false;

			for ( int i = 0; i < list.size() - 1; ++i ) {
				if( list.get(i).getId() > list.get(i + 1).getId() ) {
				    UserAcc temp = list.get(i);
				    list.set( i, list.get(i + 1));
				    list.set( i + 1, temp);
				    change = true;
				}
			}
		}
	}
	
	public int getSize() {
		return ( list.size() );
	}
	
	public String getListEntry( int index ) {
		return (list.get(index).getListEntry());
	}
	
	public Double getAmountOfIndex( int index ) {
		return (list.get(index).getAmount());
	}
	
	public void updateAmountOfIndex( int index, Double amount ) {
		list.get(index).setAmount(amount);
		modified = true;
	}
	
	public synchronized void deposit( int index, double amount ) {
		modified = true;
		double balance = list.get(index).getAmount();
		balance += amount;
		list.get(index).setAmount(balance);
		notifyAll();
	}
	
	public synchronized void withdraw( int index, double amount, AgentModel model ) throws InterruptedException {
		modified = true;
		while( getAmountOfIndex(index) < amount ) {
				model.notifyChanged(blocked);
				wait(0);
				model.notifyChanged(running);
		}
		double balance = list.get(index).getAmount();
		balance -= amount;
		list.get(index).setAmount(balance);
	}
	
	public void save() throws FileNotFoundException {
		
		if( !modified ) return;
		File output = new File(inputFile);
		String name, new_line;
		int id;
		Double amount;
		
		if( output.exists() ) {
			PrintWriter outFile = new PrintWriter(output);
		    
			//fill first two lines
			outFile.println("name          | id     | amount");
			outFile.println("-------------------------------------------");
			
			for ( int i = 0; i < list.size(); ++i )
	        {
	            name = list.get(i).getName();
	            id = list.get(i).getId();
	            amount = list.get(i).getAmount();
	            
	           new_line = name + " | " + Integer.toString( id ) + " | $" + Double.toString( amount );
	           
	           outFile.println( new_line );
	        }
			outFile.close();
			modified = false;
		}
	}
	
	public int getNextAgentID() {
		++agentCount;
		return agentCount;
	}
}
