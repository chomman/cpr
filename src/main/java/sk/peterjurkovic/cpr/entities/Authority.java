package sk.peterjurkovic.cpr.entities;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;



@Entity
@Table(name = "authority")
public class Authority extends AbstractEntity implements GrantedAuthority {

	private static final long serialVersionUID = 3198554L;
	
	public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    
    private String name;
    private String description;

    
    @Override
    @Id
    @GeneratedValue
    public Long getId() {
        return super.getId();
    }

    /**
     * Metoda vracia hodnotu name objektu.
     */
    @Transient
    public String getAuthority() {
        return super.getCode();
    }

    
    @Column(length = 50)
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
     * Metoda compareTo pre porovnavanie objektu na zaklade nazvu
     */
    @Transient
    public int compareTo(GrantedAuthority object) {
        Authority a = (Authority)object;
        return getCode().compareTo(a.getCode());
    }

    @Override
    @Transient
    public int hashCode() {
        final int prime = 31;
        int result = prime * ((getCode() == null) ? 0 : getCode().hashCode());
        return result;
    }

    /**
     * equals na zaklade kodu authority
     */
    @Override
    @Transient
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Authority other = (Authority)obj;
        if (getCode() == null) {
            if (other.getCode() != null) {
                return false;
            }
        } else if (!getCode().equals(other.getCode())) {
            return false;
        }
        return true;
    }

    /**
     * Vrati instanciu authority pre porovnavanie
     *
     * @param kod
     *            role
     * @return
     */
    public static Authority getInstance(String code) {
        Authority authority = new Authority();
        authority.setCode(code);
        return authority;
    }

    /**
     * Vrati vsechny role
     */
    public static List<String> getRoles() {
        List<String> roles = new ArrayList<String>();
        roles.add(ROLE_ADMIN);
        roles.add(ROLE_SUPERADMIN);
        roles.add(ROLE_USER);
        return roles;
    }
}
