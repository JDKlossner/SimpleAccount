package acc.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import acc.controller.AgentController;
import acc.model.AgentModel;
import acc.model.ModelEvent;

@SuppressWarnings("serial")
public class AgentView extends JFrameView {
	public static final String START = "Start Agent";
	public static final String STOP = "Stop Agent";
	public static final String DISMISS = "Dismiss";
	public static final String RUNNING = "Running";
	public static final String BLOCKED = "Blocked";
	public static final String STOPPED = "Stopped";
	public static final String UPDATE = "Update";
	private String type, agentID, amount, operations;
	private JButton actionButton;
	private JButton startButton;
	private JButton stopButton;
	private JButton dismissButton;
	private JPanel textPanel;
	private JPanel buttonPanel;
	private Handler handler;
	private JTextField idField, amountField, operationsField, stateField, transferField, completedField;
	private boolean started = false;
	private int index, completed = 0;

	public AgentView(AgentModel model, AgentController controller, String type, int index) {
		super(model, controller);
		this.type = type;
		this.index = index;
		
		this.setTitle("Start " + type + " Agent for Account: " + (index + 1));
		
		textPanel = new JPanel();
		buttonPanel = new JPanel();
		handler = new Handler();
		
		JLabel idLabel = new JLabel("Agent ID:");
		JLabel amountLabel = new JLabel("Amount in $:");
		JLabel operationsLabel = new JLabel("Operations per second:");
		
		idField = new JTextField(20);    //Field for Agent ID:
		idField.setEnabled(true);
		idField.addActionListener(handler);
		
		amountField = new JTextField(20);  //Field for Amount in $:
		amountField.setEnabled(true);
		amountField.addActionListener(handler);
		
		operationsField = new JTextField(20);  //Field for Operations per second:
		operationsField.setEnabled(true);
		operationsField.addActionListener(handler);
		operationsField.setText("0.0");
		
		startButton = new JButton(START);      //Start Agent button
		startButton.addActionListener(handler);
		
		actionButton = startButton;
		
		dismissButton = new JButton(DISMISS);  //Dismiss button
		dismissButton.addActionListener(handler);
		
		textPanel.setLayout(new GridLayout(4, 4, 5, 5));
		buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));
		
		this.getContentPane().add(textPanel, BorderLayout.NORTH);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		textPanel.add(idLabel, 0);
		textPanel.add(idField, 1);
		
		textPanel.add(amountLabel, 2);
		textPanel.add(amountField, 3);
		
		textPanel.add(operationsLabel, 4);
		textPanel.add(operationsField, 5);
		
		
		buttonPanel.add(actionButton, null);
		buttonPanel.add(dismissButton, null);
		pack();

	}

	// Now implement the necessary event handling code
	public void modelChanged(ModelEvent event) {
		if( (event.getActionCommand()).equals(START)) { //Modify view to a started Agent
			started = true;
			
			this.getContentPane().removeAll();
			textPanel.removeAll();
			buttonPanel.removeAll();
			
			this.setTitle( type + " Agent " + agentID + " for Account: " + (index + 1));
			
			JLabel amountLabel = new JLabel("Amount in $:");
			JLabel operationsLabel = new JLabel("Operations per second:");
			JLabel stateLabel = new JLabel("State:");
			JLabel transferLabel = new JLabel("Amount transfered in $:");
			JLabel completedLabel = new JLabel("Operations completed::");
			
			amountField = new JTextField(20); //Amount in $: Field
			amountField.setEnabled(false);
			amountField.addActionListener(handler);
			amountField.setText(amount);
			
			operationsField = new JTextField(20);  //Operations per Second Field
			operationsField.setEnabled(false);
			operationsField.addActionListener(handler);
			operationsField.setText(operations);

			stateField = new JTextField(20);       //Agent State Field
			stateField.setEnabled(false);
			stateField.addActionListener(handler);
			stateField.setText(RUNNING);
			
			transferField = new JTextField(20);    //Amount Transfered Field
			transferField.setEnabled(false);
			transferField.addActionListener(handler);
			transferField.setText("0.00");
			
			completedField = new JTextField(20);  //Operations Completed Field
			completedField.setEnabled(false);
			completedField.addActionListener(handler);
			completedField.setText("0");
			
			stopButton = new JButton(STOP);      //Stop Agent button
			stopButton.addActionListener(handler);
			
			actionButton = stopButton;      //Set the correct button
			
			textPanel.setLayout(new GridLayout(6, 2, 5, 5));
			buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));
			
			this.getContentPane().add(textPanel, BorderLayout.NORTH);
			this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
			
			textPanel.add(amountLabel, 0);
			textPanel.add(amountField, 1);
			
			textPanel.add(operationsLabel, 2);
			textPanel.add(operationsField, 3);
			
			textPanel.add(stateLabel, 4);
			textPanel.add(stateField, 5);
			
			textPanel.add(transferLabel, 6);
			textPanel.add(transferField, 7);
			
			textPanel.add(completedLabel, 8);
			textPanel.add(completedField, 9);
			
			buttonPanel.add(actionButton, 0);
			buttonPanel.add(dismissButton, 1);
			
			dismissButton.setEnabled(false);  //Disable Dismiss button since Agent is running
			pack();
		}
		else if( (event.getActionCommand()).equals(STOP)) { //Agent stopped change status and button functions
			buttonPanel.remove(actionButton);
			actionButton = startButton;       //Change button to allow Agent to restart
			buttonPanel.add(actionButton, 0);
			pack();
			stateField.setText(STOPPED);
			dismissButton.setEnabled(true);
		}
		else if( (event.getActionCommand()).equals(UPDATE)) { //One operation completed update all info
			++completed;
			String msg = completed + "";   //Update Operations Completed
			completedField.setText(msg);
			msg = (completed * Double.parseDouble(amount)) + "";   //Update Amount Transfered
			transferField.setText(msg);
		}
		else if( (event.getActionCommand()).equals(BLOCKED)) { //Agent blocked due to insufficient funds update status
			stateField.setText(BLOCKED);
		}
		else if( (event.getActionCommand()).equals(RUNNING)) { //Agent unblocked and running update status
			stateField.setText(RUNNING);
		}
	}

	// Inner classes for Event Handling
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			if( !started) {
				agentID = idField.getText();    //Save the field inputs to create new view if they pass checks
				amount = amountField.getText();
				operations = operationsField.getText();
			}
			((AgentController) getController()).operation(e.getActionCommand(), agentID, amount, operations);
		}
	}	

}