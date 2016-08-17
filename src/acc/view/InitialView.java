package acc.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;

import acc.controller.InitialController;
import acc.model.InitialModel;
import acc.model.ModelEvent;

@SuppressWarnings("serial")
public class InitialView extends JFrameView {
	public static final String USD = "Edit in USD";
	public static final String EURO = "Edit in Euros";
	public static final String YUAN = "Edit in Yuan";
	public static final String SAVE = "Save";
	public static final String EXIT = "Exit";
	public static final String AGENTD = "Create deposit agent.";
	public static final String AGENTW = "Create withdraw agent.";
	private JComboBox<String> users;
	private JButton usdButton;
	private JButton euroButton;
	private JButton yuanButton;
	private JButton agentDeposit;
	private JButton agentWithdraw;

	public InitialView(InitialModel model, InitialController controller) {
		super(model, controller);
		this.setTitle("SimpleAcc By Jordan Klossner");
		JPanel usersPanel = new JPanel();
		JPanel buttonPanel1 = new JPanel();
		JPanel buttonPanel2 = new JPanel();
		Handler handler = new Handler();
		String[] combo_list = ((InitialController) getController()).createComboList();
		users = new JComboBox<String>(combo_list);
		users.addActionListener(handler);
		usdButton = new JButton(USD);
		usdButton.addActionListener(handler);
		euroButton = new JButton(EURO);
		euroButton.addActionListener(handler);
		yuanButton = new JButton(YUAN);
		yuanButton.addActionListener(handler);
		agentDeposit = new JButton(AGENTD);
		agentDeposit.addActionListener(handler);
		agentWithdraw = new JButton(AGENTW);
		agentWithdraw.addActionListener(handler);
		JButton saveButton = new JButton(SAVE);
		saveButton.addActionListener(handler);
		JButton exitButton = new JButton(EXIT);
		exitButton.addActionListener(handler);
		buttonPanel1.setLayout(new GridLayout(4, 4, 5, 5));
		buttonPanel2.setLayout(new GridLayout(4, 4, 5, 5));
		this.getContentPane().add( usersPanel, BorderLayout.NORTH);
		this.getContentPane().add(buttonPanel1, BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel2, BorderLayout.SOUTH);
		usersPanel.add(users, null);
		buttonPanel1.add(usdButton, null);
		buttonPanel1.add(euroButton, null);
		buttonPanel1.add(yuanButton, null);
		buttonPanel2.add(agentDeposit, null);
		buttonPanel2.add(agentWithdraw, null);
		buttonPanel2.add(saveButton, null);
		buttonPanel2.add(exitButton, null);
		pack();

	}

	// Now implement the necessary event handling code
	public void modelChanged(ModelEvent event) {
		
	}

	// Inner classes for Event Handling
	class Handler implements ActionListener {
		// Event handling is handled locally
		public void actionPerformed(ActionEvent e) {
			if( e.getSource() == users ) {
				((InitialController) getController()).updateSelection( users.getSelectedIndex() );
			}
			else {
			    try {
					((InitialController) getController()).operation(e.getActionCommand() );
				} catch (FileNotFoundException e1) {
					//Should never throw this exception during save() function
				}
			}
		}
	}

	public static void main(String[] args) {
		try {
			new InitialController( args[0] );
		} catch (FileNotFoundException e) {
			String[] options = { "Dismiss" };
			JOptionPane.showOptionDialog( new JDialog(), "File not found!", "File Exception Error",
				    JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0] );
			System.exit(0);
		}
	}

}