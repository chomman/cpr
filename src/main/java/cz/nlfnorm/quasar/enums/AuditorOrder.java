package cz.nlfnorm.quasar.enums;

import java.util.Arrays;
import java.util.List;

public enum AuditorOrder {

	ITC_ID(1, "ITC ID", " order by a.itcId"),
	FIRST_NAME(3, "First name", " order by a.firstName"),
	LAST_NAME(4, "Last name", " order by a.lastName");
	
	private int id;
	
	private String name;
	
	private String sql;

	
	
	private AuditorOrder(int id, String name, String sql) {
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

	public static List<AuditorOrder> getAll() {
        return Arrays.asList(values());
    }
	
	public static AuditorOrder getById(int id) {
        for (AuditorOrder i : getAll()) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }
	
	public static String getSqlById(int id) {
        for (AuditorOrder i : getAll()) {
            if (i.getId() == id) {
                return i.getSql();
            }
        }
        return ITC_ID.getSql();
    }
}
