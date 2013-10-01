package sk.peterjurkovic.cpr.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 * @date Aug 13, 2013
 *
 */
public enum CsnOrderBy {

	CREATE_DESC(1, "orderby.csn.created.desc", " order by csn.created desc"),
	CREATE_ASC(2, "orderby.csn.created.asc", " order by csn.created asc"),
	CSN_ID_ASC(3, "orderby.csn.csnid.asc", " order by csn.csnId asc"),
	CSN_ID_DESC(4, "orderby.csn.csnid.desc", " order by csn.csnId desc");	
	
	private int id;
	
	private String name;
	
	private String sql;
	
	
	private CsnOrderBy(int id, String name, String sql) {
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

	public static List<CsnOrderBy> getAll() {
        return Arrays.asList(values());
    }
	
	public static CsnOrderBy getById(int id) {
        for (CsnOrderBy i : getAll()) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }
	
	public static String getSqlById(int id) {
        for (CsnOrderBy i : getAll()) {
            if (i.getId() == id) {
                return i.getSql();
            }
        }
        return CREATE_DESC.getSql();
    }
}
