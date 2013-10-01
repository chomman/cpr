package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum ExceptionLogOrder {
	CREATE_DESC(1, "Nejnovějších", " order by el.created desc"),
	CREATE_ASC(2, "Nejstarších", " order by el.created asc"),
	NAME_ASC(3, "Typu A-Z", " order by el.type asc"),
	NAME_DESC(4, "Typu Z-A", " order by el.type desc");	
	
	
	private int id;
	
	private String name;
	
	private String sql;

	
	
	private ExceptionLogOrder(int id, String name, String sql) {
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

	public static List<ExceptionLogOrder> getAll() {
        return Arrays.asList(values());
    }
	
	public static ExceptionLogOrder getById(int id) {
        for (ExceptionLogOrder i : getAll()) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }
	
	public static String getSqlById(int id) {
        for (ExceptionLogOrder i : getAll()) {
            if (i.getId() == id) {
                return i.getSql();
            }
        }
        return CREATE_DESC.getSql();
    }
}
