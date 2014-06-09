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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

    
    
}
