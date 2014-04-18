package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum WebpageType {
	
	
	ARTICLE(1, "webpageType.article", "webpageType.article.descr", "article"),
	NEWS(2, "webpageType.news", "webpageType.news.descr", "news"),
	CATEGORY(3, "webpageType.category", "webpageType.category.descr", "category"),
	REDIRECT(4, "webpageType.redirect", "webpageType.redirect.descr", null),
	NEWS_CATEGORY(5, "webpageType.newsCategory", "webpageType.newsCategory.descr", "news-category");
	
	
	private int id;
	private String code;
	private String description;
	private String viewName;
	
	private WebpageType(int id, String code, String description, String viewName){
		this.id = id;
		this.code = code;
		this.description = description;
		this.viewName = viewName;
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

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	
	
}
