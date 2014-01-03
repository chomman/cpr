package sk.peterjurkovic.cpr.dto;

public class CommissionDecisionDto {

	private long id;
	
	private String name;

	public CommissionDecisionDto(long id, String czechName, String englishName) {
		this.id = id;
		this.name = czechName + " - " + englishName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	
}
