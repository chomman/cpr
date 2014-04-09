package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum WebpageModule {
	
	CPR_EHN_LIST(1 , "CPR list", "/m/harmonized-standards", "harmonized-standards.jsp"),
	CPR_GROUP_LIST(2 , "CPR group list", "/m/cpr-groups", "cpr-groups.jsp"),
	NOAO_LIST(2 , "NOAO list", "/m/notifiedbodies", "notifiedbodies.jsp");
	
	private int id;
	private String name;
	private String fourwardUrl;
	private String jspPage;
	
	private WebpageModule(int id, String name, String fourwardUrl, String jspPage){
		this.id = id;
		this.name = name;
		this.fourwardUrl = fourwardUrl;
		this.jspPage = jspPage;
	}
	
	public static List<WebpageModule> getAll(){
		 return Arrays.asList(values());
	}
	
	public static WebpageModule getById(Long id) {
       for (WebpageModule i : getAll()) {
           if (i.getId() == id) {
               return i;
           }
       }
       return null;
   }
	

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getFourwardUrl() {
		return fourwardUrl;
	}

	public String getJspPage() {
		return jspPage;
	}
	
	
}
