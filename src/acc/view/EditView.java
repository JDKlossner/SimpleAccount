package acc.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import acc.controller.EditController;
import acc.model.EditModel;
import acc.model.ModelEvent;

@SuppressWarnings("serial")
public class EditView extends JFrameView {
	public static final String DEPOSIT = "Deposit";
	public static final String WITHDRAW = "Withdraw";
	public static final String DISMISS = "Dismiss";
	private static final String OPERATIONS = "Operations in ";
	private static final String AVAILABLE = "Available Funds:";
	private static final String EDITABLE = "Enter Amount in ";
	private JTextField availableField;
	private JTextField amountField;

	public EditView(EditModel model, EditController controller, String currency, int index) {
		super(model, controller);
		
		this.setTitle( ((EditController) getController()).getTitle() + "; " + OPERATIONS + currency);
		
		JPanel buttonPanel = new JPanel();
		JPanel availablePanel = new JPanel();
		JPanel amountPanel = new JPanel();
		
		Handler handler = new Handler();
		
		JLabel availableLabel = new JLabel(AVAILABLE);
		JLabel amountLabel = new JLabel(EDITABLE + currency + ":");
		
		availableField = new JTextField(20);
		availableField.setEnabled(false);
		availableField.setText( ((EditController) getController()).getAmount() );
		amountField = new JTextField(20);
		amountField.setEnabled(true);
		amountField.addActionListener(handler);
		amountField.setText("0.0");
		
		JButton depositButton = new JButton(DEPOSIT);
		depositButton.addActionListener(handler);
		JButton withdrawButton = new JButton(WITHDRAW);
		withdrawButton.addActionListener(handler);
		JButton dismissButton = new JButton(DISMISS);
		dismissButton.addActionListener(handler);
		
		buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));
		this.getContentPane().add( availablePanel, BorderLayout.NORTH);
		this.getContentPane().add( amountPanel, BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		availablePanel.add(availableLabel, 0);
		availablePanel.add(availableField, 1);
		amountPanel.add(amountLabel, 0);
		amountPanel.add(amountField, 1);
		buttonPanel.add(depositButton, null);
		buttonPanel.add(withdrawButton, null);
		buttonPanel.add(dismissButton, null);
		pack();
		
	}

	public void modelChanged(ModelEvent event) {
		String msg = event.getAmount() + "";
		availableField.setText(msg);
		amountField.setText( "0.0" );
	}

	// Inner classes for Event Handling
		class Handler implements ActionListener {
			// Event handling is handled locally
			public void actionPerformed(ActionEvent e) {
				((EditController) getController()).operation( e.getActionCommand(), amountField.getText() );
			}
		}
}
