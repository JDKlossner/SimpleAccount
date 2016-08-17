package acc.model;

import acc.model.AbstractModel;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.JOptionPane;

import acc.controller.AgentController;
import acc.controller.EditController;

public class InitialModel extends AbstractModel {
	private int current = 0;
	UserList list = new UserList();
	ArrayList<Integer> agents = new ArrayList<Integer>(5);
	
	public void fillList( String file ) throws FileNotFoundException {
		String status = list.fillList(file);
		if( status.equals( "Successful" )) {
			list.sort();
		}
		else  if ( status.equals( "Not Found" ) ) {
			JOptionPane.showOptionDialog( new JDialog(), "File not found!", "File Exception Error",
				    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, null, "Dismiss" );
			System.exit(0);
		}
		
	}
	
	public String[] createComboList() {
		String[] combo_list = new String[list.getSize()];
		
		for( int i = 0; i < list.getSize(); ++i ) {
			combo_list[i] = list.getListEntry(i);
		}
		return combo_list;
	}
	
	public void edit( int selection ) {
		switch(selection) {
			case 1: //USD
				new EditController( "USD", current, list );
				break;
			case 2: //Euro
				new EditController( "Euro", current, list );
				break;
			case 3: //Yuan
				new EditController( "Yuan", current, list );
				break;
		}
	}
	
	public void save() throws FileNotFoundException {
		list.save();
	}

	public void updateSelection(int index) {
		this.current = index;
	}

	public void agent(String type) {
		new AgentController( this, type, current, list);
		
	}
	
	public void addAgentID( Integer id ) {
		agents.add(id);
	}

	public boolean checkAgentID( Integer id ) {
		return (agents.contains(id));
	}
	
	public void removeAgentID( Integer id ) {
		agents.remove(id);
	}
	
}