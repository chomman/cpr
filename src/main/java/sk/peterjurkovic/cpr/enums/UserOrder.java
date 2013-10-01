package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum UserOrder {
	
	CREATE_DESC(1, "Vytvoření - nejnovějších", " order by u.created desc"),
	CREATE_ASC(2, "Vytvoření - nejstarších", " order by u.created asc"),
	FIRST_NAME(3, "Jména", " order by u.firstName asc"),
	LAST_NAME(4, "Příjmení", " order by a.lastName desc");	
	
	private int id;
	
	private String name;
	
	private String sql;

	
	
	private UserOrder(int id, String name, String sql) {
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

	public static List<UserOrder> getAll() {
        return Arrays.asList(values());
    }
	
	public static UserOrder getById(int id) {
        for (UserOrder i : getAll()) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }
	
	public static String getSqlById(int id) {
        for (UserOrder i : getAll()) {
            if (i.getId() == id) {
                return i.getSql();
            }
        }
        return CREATE_DESC.getSql();
    }
}
