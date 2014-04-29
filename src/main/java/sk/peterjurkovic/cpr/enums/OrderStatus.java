package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

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
	
	public static List<OrderStatus> getAll() {
        return Arrays.asList(values());
    }

	public int getId() {
		return id;
	}

	public String getCode() {
		return code;
	}
	
	
	
}
