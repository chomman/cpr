package cz.nlfnorm.dto;

public class StandardGroupDto {
	
	private long id;
	private String name;
	
	
	public StandardGroupDto(){}
	
	public StandardGroupDto(long id, String code, String name){
		this.id = id;
		this.name = code + " - " + name;
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
