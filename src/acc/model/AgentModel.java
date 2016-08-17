package acc.model;

import acc.model.AbstractModel;
import javax.swing.*;

public class AgentModel extends AbstractModel {
	private int index = 0;
	private UserList list;
	private ModelEvent start = new ModelEvent(this, index, "Start Agent", null); //start event
	private ModelEvent update = new ModelEvent(this, index, "Update", null);     //update event
	private ModelEvent stop = new ModelEvent(this, index, "Stop Agent", null);   //stop event
	private AccThread accThread;
	private Thread thread;
	private String[] dismiss = { "Dismiss" };
	private Integer agentID;
	private InitialModel model;
	

	public AgentModel ( int index, UserList list ){
		this.index = index;
		this.list = list;
	}
	
	public void deposit( double amount ) {  //method to be called by thread
		list.deposit(index, amount);
		notifyChanged(update);
	}
	
	public void withdraw( double amount ) throws InterruptedException {  //method to be called by thread
		list.withdraw(index, amount, this);                              //throws Interrupted to catch on the stop Interrupt
		notifyChanged(update);                                           //in order to stop it completely
	}
	
	//Checks values input into the fields for validity
	public boolean checkStartValues( InitialModel model, String id, String amount, String operations ) {
		
		Integer agentID;
		try {
			agentID = Integer.parseInt(id);    //check Agent ID input is valid
		}catch( NumberFormatException e ){
			JOptionPane.showOptionDialog( new JDialog(), "Agent ID must only contain numbers!\n",
					"Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
			return false;
		}
		
		if( model.checkAgentID(agentID)) {
			JOptionPane.showOptionDialog( new JDialog(), "Agent ID must be unique!\n",
		                         "Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
			return false;
		} 
		
		Double temp;
		
	    try {
			temp = Double.parseDouble( amount );   //check amount input is valid
		}catch( NumberFormatException e ){
			JOptionPane.showOptionDialog( new JDialog(), "Amount must only contain numbers!\n",
					"Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
			return false;
		}
	    
	    if( temp < 1 ) { 
	    	JOptionPane.showOptionDialog( new JDialog(), "Amount must be greater than 1!\n",
				"Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
	    	return false;
	    }
	    
	    try {
			temp = Double.parseDouble( operations );   //check operations per second input is valid
		}catch( NumberFormatException e ){
			JOptionPane.showOptionDialog( new JDialog(), "Operations per second must only contain numbers!\n",
					"Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
			return false;
		}
	    
	    if( temp <= 0 ) { 
	    	JOptionPane.showOptionDialog( new JDialog(), "Operations per second must be greater than 0!\n",
				"Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
	    	return false;
	    }
	    
	    return true;  //Passed all checks
	}

	//Method to Start the Agent Thread
	public void start( InitialModel model, String id, String type, String amount, String operations ) {
		agentID = Integer.parseInt(id);
		this.model = model;
		model.addAgentID( agentID );  //Adds Agent ID to list held in the InitialModel for validity purposes
		notifyChanged(start);
		accThread = new AccThread(this, type, amount, operations);
		thread = new Thread( accThread );
		thread.start();
	}

	//Method to Stop the Agent Thread
	public void stop() {
		accThread.stop();
		thread.interrupt();
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		model.removeAgentID( agentID );  //Remove AgentID from list
		notifyChanged(stop);
	}

}