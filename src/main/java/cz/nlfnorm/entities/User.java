package cz.nlfnorm.entities;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;

import org.apache.commons.lang.Validate;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.jadira.usertype.dateandtime.joda.PersistentDateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cz.nlfnorm.enums.OnlinePublication;

/**
 * Entita reprezentujuca opravneneho uzivatela informacneho systemu
 * @author peto
 *
 */
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
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
	private Set<UserOnlinePublication> onlinePublications = new HashSet<UserOnlinePublication>();
	
	private UserInfo userInfo;
	private LocalDate registrationValidity;
	private LocalDateTime changePasswordRequestDate;
    private String changePasswordRequestToken;
    private String sgpPassword;
	
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
	
	@Valid
	@PrimaryKeyJoinColumn
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	@OrderBy(clause = "id")
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
	public Set<UserOnlinePublication> getOnlinePublications() {
		return onlinePublications;
	}

	public void setOnlinePublications(Set<UserOnlinePublication> onlinePublications) {
		this.onlinePublications = onlinePublications;
	}

	@Transient
    public List<GrantedAuthority> getAuthorities() {
        return new ArrayList<GrantedAuthority>(getAuthoritySet());
    }
	
	
	@Transient
    public boolean isWebmaster() {
        return hasRole(Authority.ROLE_WEBMASTER);
    }
	
	@Transient
    public boolean isAdministrator() {
		return isSuperAdministrator() || hasRole(Authority.ROLE_ADMIN);
    }
	
	@Transient
    public boolean isSuperAdministrator() {
		return isWebmaster() || hasRole(Authority.ROLE_SUPERADMIN);
    }
	
	@Transient
    public boolean isQuasarAdmin() {
		return hasRole(Authority.ROLE_QUASAR_ADMIN);
    }
	
	@Transient
    public boolean isAuditor() {
		return hasRole(Authority.ROLE_AUDITOR);
    }
	
	@Transient
    public boolean isPortalAdmin() {
		return isSuperAdministrator() || hasRole(Authority.ROLE_PORTAL_ADMIN);
    }
	
	@Transient
    public boolean isPortalUser() {
		return isPortalAdmin() || hasRole(Authority.ROLE_PORTAL_USER);
    }
	
	private boolean hasRole(final String code){
		Authority authority = Authority.getInstance(code);
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
	
	@Transient
	public UserOnlinePublication getUserOnlinePublication(final PortalProduct portalProduct){
		Validate.notNull(portalProduct);
		return getUserOnlinePublication(portalProduct.getId());
	}
	
	@Transient
	public UserOnlinePublication getUserOnlinePublication(final Long portalProductId){
		Validate.notNull(portalProductId);
		for(UserOnlinePublication uop : onlinePublications ){
			if(uop.getPortalProduct().getId().equals(portalProductId)){
				return uop;
			}
		}
		return null;
	}
	
	@Transient
	public boolean hasValidOnlinePublication(final OnlinePublication onlinePublication){
		if(onlinePublication != null){
			for(UserOnlinePublication uop : onlinePublications ){
				if(uop.getPortalProduct().getOnlinePublication().equals(onlinePublication)){
					return isValid(uop);
				}
			}
		}
		return false;
	}
	
	
	
	@Transient
	public boolean hasValidOnlinePublication(final PortalProduct portalProduct){
		UserOnlinePublication uop = getUserOnlinePublication(portalProduct);
		if(uop == null){
			return false;
		}
		return isValid(uop);
	}
	
	@Transient
	private boolean isValid(final UserOnlinePublication uop){
		LocalDate today = new LocalDate();
		if(today.isBefore(uop.getValidity()) || today.isEqual(uop.getValidity())){
			return true;
		}
		return false;
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
    
    
     
    
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @Column( name = "registration_validity")
	public LocalDate getRegistrationValidity() {
		return registrationValidity;
	}

	public void setRegistrationValidity(LocalDate registrationValidity) {
		this.registrationValidity = registrationValidity;
	}

	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column( name = "change_pass_req_date")
	public LocalDateTime getChangePasswordRequestDate() {
		return changePasswordRequestDate;
	}
	
	public void setChangePasswordRequestDate(LocalDateTime changePasswordRequestDate) {
		this.changePasswordRequestDate = changePasswordRequestDate;
	}
	
	@Column( name = "change_pass_req_token", length = 60)
	public String getChangePasswordRequestToken() {
		return changePasswordRequestToken;
	}

	public void setChangePasswordRequestToken(String changePasswordRequestToken) {
		this.changePasswordRequestToken = changePasswordRequestToken;
	}

	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}

	@Transient
	public boolean getHasActiveRegistration(){
		if(!getEnabled() || getRegistrationValidity() == null){
			return false;
		}
		LocalDate today = new LocalDate();
		return !getRegistrationValidity().isBefore(today);
	}
	
	@Transient
	public int getCountOfActivePublications(){
		return getAllActiveUserOnlinePublications().size();
	}
	
	
	@Transient
	public List<UserOnlinePublication> getAllActiveUserOnlinePublications(){
		List<UserOnlinePublication> list = new ArrayList<UserOnlinePublication>();
		LocalDate today = new LocalDate();
		for(UserOnlinePublication pub : onlinePublications){
			if(!pub.getValidity().isBefore(today)){
				list.add(pub);
			}
		}
		return list;
	}
	
	@Transient
	public boolean isPortalAuthorized(){
		return getHasActiveRegistration() || isAdministrator();
	}
    
	@Transient
	public UserOnlinePublication getUserOnlineUplication(Long id){
		Validate.notNull(id);
		for(UserOnlinePublication pub : onlinePublications){
			if(pub.getId().equals(id)){
				return pub;
			}
		}
		return null;
	}

	@Column(name = "sgp_password", length = 48)
	public String getSgpPassword() {
		return sgpPassword;
	}

	public void setSgpPassword(String sgpPassword) {
		this.sgpPassword = sgpPassword;
	}
		
	@Transient
	public String getName(){
		return (getFirstName() != null ? getFirstName() : "") + " " +
			   (getLastName() != null ? getLastName() : "");
	}	
	
}
