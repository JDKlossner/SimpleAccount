package acc.model;

public class UserAcc {

	private String name;
	private Integer id;
	private Double amount;
	
	UserAcc ( String name, Integer id, Double amount ) {
		this.name = name;
		this.id = id;
		this.amount = amount;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName( String name ) {
		this.name = name;
	}
	
	public Integer getId() {
		return this.id;
	}
	public void setId( Integer id ) {
		this.id = id;
	}
	
	public Double getAmount() {
		return this.amount;
	}
	public void setAmount( Double amount ) {
		this.amount = amount;
	}
	
	public String getListEntry() {
		return ( this.id.toString() + " " + this.name );
	}
}
