package cz.nlfnorm.web.forms.admin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.nlfnorm.entities.Authority;
import cz.nlfnorm.entities.User;

public class UserForm {
	
	public static class GAuthority {

        private Boolean selected = Boolean.FALSE;
        private Authority authority;

        public GAuthority(Authority a, Boolean selected) {
            this.authority = a;
            this.selected = selected;
        }

        public Boolean getSelected() {
            return selected;
        }

        public void setSelected(Boolean selected) {
            this.selected = selected;
        }

        public Authority getAuthority() {
            return authority;
        }

        public void setAuthority(Authority authority) {
            this.authority = authority;
        }
    }
	
	
	public User user;
	
	private boolean enabled;
	
	private List<GAuthority> roles = new ArrayList<GAuthority>();
	
	private String password;
	
	private String confifmPassword;	
	
	private Boolean sendEmail;
	
	
	public void addRoles(List<Authority> authorities) {
		clearRoles();
        for (Authority a : authorities) {
            if (user.getAuthoritySet().contains(a)) {
            	getRoles().add(new GAuthority(a, Boolean.TRUE));
            } else {
            	getRoles().add(new GAuthority(a, Boolean.FALSE));
            }
        }
    }
	
	
	public Set<Authority> getSelectedAuthorities(){
		Set<Authority> newRoles = new HashSet<Authority>();
		for (GAuthority a : getRoles()) {
            if (a.getSelected().booleanValue()) {
            	newRoles.add(a.getAuthority());
            }
        }
        return newRoles;
	}
	
	
	
	
	public void clearRoles() {
        this.roles.clear();
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<GAuthority> getRoles() {
		return roles;
	}

	public void setRoles(List<GAuthority> roles) {
		this.roles = roles;
	}

	public String getConfifmPassword() {
		return confifmPassword;
	}

	public void setConfifmPassword(String confifmPassword) {
		this.confifmPassword = confifmPassword;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}


	public Boolean getSendEmail() {
		return sendEmail;
	}


	public void setSendEmail(Boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	
	

	
	
	
}
