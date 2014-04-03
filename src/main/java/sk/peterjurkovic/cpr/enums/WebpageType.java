package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum WebpageType {
	
	
	ARTICLE(1, "webpageType.article", "webpageType.article.descr", true),
	NEWS(2, "webpageType.news", "webpageType.news.descr", true),
	CATEGORY(3, "webpageType.category", "webpageType.category.descr", true),
	REDIRECT(4, "webpageType.redirect", "webpageType.redirect.descr", true);
	
	
	private int id;
	private String code;
	private String description;
	private boolean isVisible;
	
	private WebpageType(int id, String code, String description, boolean isVisible){
		this.id = id;
		this.code = code;
		this.description = description;
		this.isVisible = isVisible;
	}

	public static List<WebpageType> getAll(){
		 return Arrays.asList(values());
	}
	
	public static WebpageType getById(Long id) {
        for (WebpageType i : getAll()) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }
	
	public int getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public boolean isVisible() {
		return isVisible;
	}
	
	
	
}
