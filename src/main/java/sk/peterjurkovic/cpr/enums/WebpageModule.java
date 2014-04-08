package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum WebpageModule {
	
	CPR_EHN_LIST(1 , "CPR eHN list", ""),
	CPR_GROUP_LIST(1 , "CPR group list", "");
	
	private int id;
	private String name;
	private String fourwardUrl;
	
	private WebpageModule(int id, String name, String fourwardUrl){
		this.id = id;
		this.name = name;
		this.fourwardUrl = fourwardUrl;
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
	
	
}
