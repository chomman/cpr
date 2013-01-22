package sk.peterjurkovic.cpr.web.forms.admin;

import java.util.ArrayList;
import java.util.List;

import sk.peterjurkovic.cpr.entities.Authority;
import sk.peterjurkovic.cpr.entities.User;

public class UserForm {
	
	public User user;
	
	private boolean enabled;
	
	private List<GAuthority> roles = new ArrayList<GAuthority>();
	
	private String password;
	
	private String confifmPassword;
	
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
	
	
	
	public void addRole(List<Authority> authorities) {
		clearRoles();
        for (Authority a : authorities) {
            if (user.getAuthoritySet().contains(a)) {
            	getRoles().add(new GAuthority(a, Boolean.TRUE));
            } else {
            	getRoles().add(new GAuthority(a, Boolean.FALSE));
            }
        }
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
	
	
	
}
