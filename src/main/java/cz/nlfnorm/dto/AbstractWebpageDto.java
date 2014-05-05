package cz.nlfnorm.dto;

import javax.validation.constraints.NotNull;

import cz.nlfnorm.entities.Webpage;

public abstract class AbstractWebpageDto {
	
	@NotNull
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public abstract void setWebpage(Webpage webpage);
	
}
