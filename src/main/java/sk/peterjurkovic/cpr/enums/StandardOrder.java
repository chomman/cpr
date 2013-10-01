package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum StandardOrder {
	
	
	
	CREATE_DESC(6, "Přidání - nejnovějších", " order by s.created desc"),
	CREATE_ASC(5, "Přidání - nejstarších", " order by s.created asc"),
	VALIDITY_DESC(2, "Platnosti nejnovší", " order by s.stopValidity desc"),
	VALIDITY_ASC(1, "Platnosti nejstarší", " order by s.stopValidity asc"),
	STANDARD_ID_ASC(3, "Označení eHN A-Z", " order by s.standardId asc"),
	STANDARD_ID_DESC(4, "Označení eHN Z-A", " order by s.standardId desc");
	;	
	
	private int id;
	
	private String name;
	
	private String sql;

	
	
	private StandardOrder(int id, String name, String sql) {
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

	public static List<StandardOrder> getAll() {
        return Arrays.asList(values());
    }
	
	public static StandardOrder getById(int id) {
        for (StandardOrder i : getAll()) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }
	
	public static String getSqlById(int id) {
        for (StandardOrder i : getAll()) {
            if (i.getId() == id) {
                return i.getSql();
            }
        }
        return CREATE_DESC.getSql();
    }
	
}
