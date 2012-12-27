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
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.joda.time.DateTime;
import org.joda.time.contrib.hibernate.PersistentDateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
@TypeDefs( { @TypeDef(name = "jodaDateTime", typeClass = PersistentDateTime.class) })
public class User extends AbstractEntity implements UserDetails{
	

	//private static final long serialVersionUID = 72541L;
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
    private DateTime changePasswordRequestDate;
    private String changePasswordRequestToken;
	private Set<Authority> authoritySet = new HashSet<Authority>();
	
	
	@Id
	@GeneratedValue
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
	
	@Column(name = "change_password_request_date")
    @Type(type = "jodaDateTime")
    public DateTime getChangePasswordRequestDate() {
        return changePasswordRequestDate;
    }

    public void setChangePasswordRequestDate(DateTime changePasswordRequestDate) {
        this.changePasswordRequestDate = changePasswordRequestDate;
    }
    
    @Column(name = "change_password_request_token", length = 60)
    public String getChangePasswordRequestToken() {
        return changePasswordRequestToken;
    }


    public void setChangePasswordRequestToken(String changePasswordRequestToken) {
        this.changePasswordRequestToken = changePasswordRequestToken;
    }
    
	
	@ManyToMany(targetEntity = Authority.class, fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
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
	
	
	@Transient
    public List<GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>(getAuthoritySet());
    }
	
	
	
	@Transient
    public boolean isAdminUser() {
        Authority authority = Authority.getInstance(Authority.ROLE_ADMIN);
        if (getAuthoritySet().contains(authority)) {
            return true;
        }
        return false;
    }
	
	
	@Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof User) {
            return email.equals(((User)obj).getEmail());
        }
        return false;
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

	

		
}
