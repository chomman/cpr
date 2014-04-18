package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum WebpageModule {
	
	CPR_EHN_LIST(1 , "webpageModule.cprStandardList", "/m/harmonized-standards", "harmonized-standards.jsp"),
	CPR_GROUP_LIST(2 , "webpageModule.cprStandardGroupList", "/m/cpr-groups", "cpr-groups.jsp"),
	NOAO_LIST(3 , "webpageModule.notifiedBodyList", "/m/notifiedbodies", "notifiedbodies.jsp"),
	REPORT_LIST(4 , "webpageModule.reportList", "/m/reports", "reports.jsp"),
	TERMINOLOGY(5 , "webpageModule.terminologySearch", "/m/terminology", "terminology-search.jsp"),
	CPR_AS_LIST(6 , "webpageModule.assessmetsSystemList", "/m/asessments-systems", "assessmentsystems.jsp");
	
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
