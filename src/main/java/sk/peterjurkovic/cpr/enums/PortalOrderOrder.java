package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

public enum PortalOrderOrder {
	
	CREATE_DESC(1, "Nejnovějších", " order by o.created desc "),
	CREATE_ASC(2, "Nejstarších", " order by o.created asc ");
	
	private int id;
	private String name;
	private String sql;
	
	private PortalOrderOrder(int id, String name, String sql){
		this.id = id;
		this.name = name;
		this.sql = sql;
	}

	public static List<PortalOrderOrder> getAll() {
        return Arrays.asList(values());
    }
	
	public static PortalOrderOrder getById(int id) {
        for (PortalOrderOrder i : getAll()) {
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

	public String getSql() {
		return sql;
	}
	
	
}
