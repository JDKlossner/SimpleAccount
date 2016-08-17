package acc.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AccThread implements Runnable{
	
	private AgentModel model;
	private String type;
	private Double amount;
	private long operations;
	private volatile boolean flag = false;
	
	public AccThread( AgentModel model, String type, String amount, String operations) {
		Double temp;
		String toLong;
		this.model = model;
		this.type = type;
		this.amount = Double.parseDouble(amount);
		temp = Double.parseDouble(operations);
		temp = 1 / temp;
		temp = temp * 1000;
		BigDecimal bd = new BigDecimal(temp);
	    bd = bd.setScale(0, RoundingMode.HALF_UP);
	    toLong = bd.toString();
		this.operations = Long.parseLong(toLong);
		
	}
	
	public void stop() {
		flag = true;
	}

	@Override
	public void run() {
		if( type.equals("Deposit") ) {
			while(true) {
				if(flag) return;
				model.deposit(amount);
				try {
					Thread.sleep((operations));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else if ( type.equals("Withdraw") ) {
			while(true) {
				if(flag) return;
				try {
					model.withdraw(amount);
				} catch (InterruptedException e1) {
					if(flag) return;
				}
				try {
					Thread.sleep((operations));
				} catch (InterruptedException e) {
					if(flag) return;
				}
			}
		}
		
	}

}
