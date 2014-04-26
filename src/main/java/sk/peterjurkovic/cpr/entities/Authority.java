package sk.peterjurkovic.cpr.entities;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;


/**
 * Uzivatelska rola
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Entity
@Table(name = "authority")
@SequenceGenerator(name = "authority_id_seq", sequenceName = "authority_id_seq", initialValue = 1, allocationSize =1)
public class Authority extends AbstractEntity implements GrantedAuthority {

	private static final long serialVersionUID = 3198554L;
	
	public static final String ROLE_WEBMASTER = "ROLE_WEBMASTER";
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_PORTAL_USER = "ROLE_PORTAL_USER";
    public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN";
    
    private String name;
    private String shortDescription;
    private String longDescription;
    
    @Override
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authority_id_seq")
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

    
    @Column(length = 20)
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "short_description")
	public String getShortDescription() {
		return shortDescription;
	}
	
	
	public void setShortDescription(String description) {
		this.shortDescription = description;
	}
	
	@Column(name = "long_description")
	@Type(type = "text")
	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
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
        roles.add(ROLE_WEBMASTER);
        roles.add(ROLE_PORTAL_USER);
        roles.add(ROLE_SUPERADMIN);
        return roles;
    }
}
