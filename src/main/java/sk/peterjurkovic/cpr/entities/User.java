package sk.peterjurkovic.cpr.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.jadira.usertype.dateandtime.joda.PersistentDateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Entita reprezentujuca opravneneho uzivatela informacneho systemu
 * @author peto
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", initialValue = 1, allocationSize =1)
@TypeDefs( { @TypeDef(name = "jodaDateTime", typeClass = PersistentDateTime.class) })
public class User extends AbstractEntity implements UserDetails{
	

	
	private static final long serialVersionUID = 198524L;
	
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Set<Authority> authoritySet = new HashSet<Authority>();
	
	private UserInfo userInfo;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(name = "first_name", length = 50)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "last_name", length = 50)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Email(message = "Neplatné uživatelské jméno")
	@Length(min=1,max=50,message="Uživatelské jméno musí být vyplněno")
	@Column(name = "email", length = 50, unique=true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "password", length = 60)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}   
	
	@ManyToMany(targetEntity = Authority.class, fetch = FetchType.EAGER, cascade = { CascadeType.ALL } )
    @JoinTable(name = "user_has_authority", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "authority_id"))
    public Set<Authority> getAuthoritySet() {
        return authoritySet;
    }
	
	public void setAuthoritySet(Set<Authority> authorities) {
        this.authoritySet = authorities;
    }
	
	public void addAuthority(Authority authority) {
		authoritySet.add(authority);
    }
	
	public boolean removeAuthority(Authority authority) {
		return authoritySet.remove(authority);
	}
	
	@PrimaryKeyJoinColumn
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Transient
    public List<GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>(getAuthoritySet());
    }
	
	
	@Transient
    public boolean isWebmaster() {
        Authority authority = Authority.getInstance(Authority.ROLE_WEBMASTER);
        if (getAuthoritySet().contains(authority)) {
            return true;
        }
        return false;
    }
	
	
	@Transient
    public boolean isAdministrator() {
		if(isWebmaster()){
			return true;
		}
        Authority authority = Authority.getInstance(Authority.ROLE_ADMIN);
        if (getAuthoritySet().contains(authority)) {
            return true;
        }
        return false;
    }
	
	
	@Transient
    public boolean isPortalUser() {
		if(isAdministrator() || isWebmaster()){
			return true;
		}
        Authority authority = Authority.getInstance(Authority.ROLE_PORTAL_USER);
        if (getAuthoritySet().contains(authority)) {
            return true;
        }
        return false;
    }
	
	public void clearAuthorities(){
		authoritySet.clear();
	}

	@Transient
	public String getUsername() {
		return getEmail();
	}
	
	public void setUsername(String username) {
	        setEmail(username);
	}

	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}


	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@Transient
	public boolean isEnabled() {
		if (getEnabled() == null) {
            return false;
        }
        return getEnabled().booleanValue();
	}

    @Override
    public int hashCode() {
        return email.hashCode();
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + ", password=" + password
				+ " authoritySize: " + getAuthoritySet().size() + "]";
	}

	
    
		
}
