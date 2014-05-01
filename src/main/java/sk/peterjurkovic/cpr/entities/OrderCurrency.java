package sk.peterjurkovic.cpr.entities;

public enum OrderCurrency {
	
	CZK("Kč"),
	EUR("EUR");
	
	private String symbol;
	
	private OrderCurrency(String symbol){
		this.symbol = symbol;
	}

	
	public String getSymbol() {
		return symbol;
	}

	
}
