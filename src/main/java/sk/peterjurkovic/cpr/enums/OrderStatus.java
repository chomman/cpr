package sk.peterjurkovic.cpr.enums;

public enum OrderStatus {
	
	PENDING(1, "orderStatus.pending"),
	PAYED(2, "orderStatus.payed"),
	CANCELED(3, "orderStatus.canceled");
	
	private int id;
	private String code;
	
	
	private OrderStatus(int id, String code){
		this.id = id;
		this.code = code;
	}
	

	public int getId() {
		return id;
	}

	public String getCode() {
		return code;
	}
	
	
	
}
