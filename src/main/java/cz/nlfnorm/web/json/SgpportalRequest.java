package cz.nlfnorm.web.json;

import java.util.List;

public class SgpportalRequest {
	
	private User user;
	
	private List<Publication> publications;
	
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Publication> getPublications() {
		return publications;
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

	public static class User{
		private Long userId;
		private String login;
		private String pass;
		
		public Long getUserId() {
			return userId;
		}
		public String getLogin() {
			return login;
		}
		public String getPass() {
			return pass;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public void setLogin(String login) {
			this.login = login;
		}
		public void setPass(String pass) {
			this.pass = pass;
		}
		
	}
	
	public static class Publication{
		private Long userId;
		private String code;
		
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
	}
}
