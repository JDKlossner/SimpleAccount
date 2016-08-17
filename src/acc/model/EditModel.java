package acc.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import acc.model.ModelEvent;

public class EditModel extends AbstractModel {
	
	UserList list;
	private String currency;
	private int index;
	private static final Double EURO = 0.88;
	private static final Double YUAN = 6.47;
	private String[] dismiss = { "Dismiss" };
	
	public EditModel ( String currency, int index, UserList list ){
		this.currency = currency;
		this.index = index;
		this.list = list;
	}

	public void deposit( String amountS ) {
		Double amount;
		Double balance = list.getAmountOfIndex(index);
		try {
			amount = Double.parseDouble( amountS );   //check amount input is valid
		}catch( NumberFormatException e ){
			JOptionPane.showOptionDialog( new JDialog(), "Amount must only contain numbers!\n",
					"Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
			ModelEvent invalid = new ModelEvent(this, index, "", balance);
			notifyChanged(invalid);
			return;
		}
		
		if( amount < 1 ) {   //check amount input greater than 1
			JOptionPane.showOptionDialog( new JDialog(), "Amount must be greater than 1!\n",
					"Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
			ModelEvent invalid = new ModelEvent(this, index, "", balance);
			notifyChanged(invalid);
			return;
		}
		
		switch( this.currency ) {
		case "USD":
			balance += amount;
			balance = round(balance, 2);
			list.updateAmountOfIndex(index, balance);
			ModelEvent usd = new ModelEvent(this, index, "", balance);
			notifyChanged(usd);
			break;
		case "Euro":
			amount = amount / EURO;
			balance += amount;
			balance = round(balance, 2);
			list.updateAmountOfIndex(index, balance);
			balance = balance * EURO;
			balance = round(balance, 2);
			ModelEvent euro = new ModelEvent(this, index, "", balance);
			notifyChanged(euro);
			break;
		case "Yuan":
			amount = amount / YUAN;
			balance += amount;
			balance = round(balance, 2);
			list.updateAmountOfIndex(index, balance);
			balance = balance * YUAN;
			balance = round(balance, 2);
			ModelEvent yuan = new ModelEvent(this, index, "", balance);
			notifyChanged(yuan);
			break;
		}
	}

	public void withdraw( String amountS ) {
		Double amount;
		Double balance = list.getAmountOfIndex(index);
		try {
			amount = Double.parseDouble( amountS );   //check amount input is valid
		}catch( NumberFormatException e ){
			JOptionPane.showOptionDialog( new JDialog(), "Amount must only contain numbers!\n",
					"Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
			ModelEvent invalid = new ModelEvent(this, index, "", balance);
			notifyChanged(invalid);
			return;
		}
		
		if( amount < 1 ) {   //check amount input greater than 1
			JOptionPane.showOptionDialog( new JDialog(), "Amount must be greater than 1!\n",
					"Input Format Error", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, dismiss, dismiss );
			ModelEvent invalid = new ModelEvent(this, index, "", balance);
			notifyChanged(invalid);
			return;
		}
		
		switch( this.currency ) {
		case "USD":
			balance -= amount;
			balance = round(balance, 2);
			list.updateAmountOfIndex(index, balance);
			ModelEvent usd = new ModelEvent(this, index, "", balance);
			notifyChanged(usd);
			break;
		case "Euro":
			amount = amount / EURO;
			balance -= amount;
			balance = round(balance, 2);
			list.updateAmountOfIndex(index, balance);
			balance = balance * EURO;
			balance = round(balance, 2);
			ModelEvent euro = new ModelEvent(this, index, "", balance);
			notifyChanged(euro);
			break;
		case "Yuan":
			amount = amount / YUAN;
			balance -= amount;
			balance = round(balance, 2);
			list.updateAmountOfIndex(index, balance);
			balance *= YUAN;
			balance = round(balance, 2);
			ModelEvent yuan = new ModelEvent(this, index, "", balance);
			notifyChanged(yuan);
			break;
		}
	}
	
	public String getTitle() {
		return ( list.getListEntry(index) );
	}

	public String getAmount() {
		Double temp = list.getAmountOfIndex(index);
		if( currency.equals("Euro") ) {
			temp *= EURO;
			temp = round(temp, 2);
		}
		else if( currency.equals("Yuan")) {
			temp *= YUAN;
			temp = round(temp, 2);
		}
		return ( temp.toString() );
	}
	
	public static double round(double value, int places) {
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
}
