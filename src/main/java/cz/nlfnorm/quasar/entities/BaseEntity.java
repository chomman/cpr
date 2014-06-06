package cz.nlfnorm.quasar.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import cz.nlfnorm.dao.impl.IdentifiableByLong;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class BaseEntity  implements Serializable, IdentifiableByLong{
	
	private Long id;
	private String code;
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Transient
	@Override
	public Long getId() {
		return id;
	}
	
	@Column(length = 15, name = "code")
	public String getCode() {
	       return code;
	   }

	  
    public void setCode(String code) {
       this.code = code;
    }

}
