package sk.peterjurkovic.cpr.enums;

public enum WebpageType {
	
	ARTICLE(1, "webpageType.article"),
	CATEGORY(2, "webpageType.category"),
	REDIRECT(3, "webpageType.redirect"),
	COMPONENT(4, "webpageType.component"),;
	
	
	private int id;
	private String code;
	
	private WebpageType(int id, String code){
		this.id = id;
		this.code = code;
	}

	
	
	public int getId() {
		return id;
	}

	public String getCode() {
		return code;
	}
	
	
	

}
