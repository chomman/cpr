package cz.nlfnorm.enums;

import java.util.Arrays;
import java.util.List;

public enum ArticleOrder {
	
	
	CREATE_DESC(1, "Přidání - nejnovějších", " order by a.created desc"),
	CREATE_ASC(2, "Přidání - nejstarších", " order by a.created asc"),
	TITLE_ASC(3, "Titulku A-Z", " order by a.title asc"),
	TITLE_DESC(4, "Titulku Z-A", " order by a.title desc");	
	
	
	private int id;
	
	private String name;
	
	private String sql;

	
	
	private ArticleOrder(int id, String name, String sql) {
		this.id = id;
		this.name = name;
		this.sql = sql;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public static List<ArticleOrder> getAll() {
        return Arrays.asList(values());
    }
	
	public static ArticleOrder getById(int id) {
        for (ArticleOrder i : getAll()) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }
	
	public static String getSqlById(int id) {
        for (ArticleOrder i : getAll()) {
            if (i.getId() == id) {
                return i.getSql();
            }
        }
        return CREATE_DESC.getSql();
    }
	
	
}
