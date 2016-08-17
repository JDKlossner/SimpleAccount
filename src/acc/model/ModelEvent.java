package acc.model;
import java.awt.event.ActionEvent;

public class ModelEvent extends ActionEvent {
	private Double amount;
	public ModelEvent(Object obj, int id, String message, Double balance){
		super(obj, id, message);
		this.amount = balance;
	}
	public Double getAmount(){return amount;}
}
