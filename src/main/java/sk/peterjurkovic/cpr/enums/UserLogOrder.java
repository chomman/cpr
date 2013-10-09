package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum UserLogOrder {
	
	CREATE_DESC(1, "Nejnovějších", " order by ul.loginDateAndTime desc"),
	CREATE_ASC(2, "Nejstarších", " order by ul.loginDateAndTime asc"),
	NAME_ASC(3, "Jména A-Z", " order by ul.user.firstName asc"),
	NAME_DESC(4, "Jména Z-A", " order by ul.user.firstName desc");	
	
	
	private int id;
	
	private String name;
	
	private String sql;

	
	
	private UserLogOrder(int id, String name, String sql) {
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

	public static List<UserLogOrder> getAll() {
        return Arrays.asList(values());
    }
	
	public static UserLogOrder getById(int id) {
        for (UserLogOrder i : getAll()) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }
	
	public static String getSqlById(int id) {
        for (UserLogOrder i : getAll()) {
            if (i.getId() == id) {
                return i.getSql();
            }
        }
        return CREATE_DESC.getSql();
    }
}
