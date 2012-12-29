package sk.peterjurkovic.cpr.dao.impl;

import org.springframework.stereotype.Repository;

import sk.peterjurkovic.cpr.dao.UserLogDao;
import sk.peterjurkovic.cpr.entities.UserLog;


@Repository("userLog")
public class UserLogDaoImpl extends BaseDaoImpl<UserLog, Long> implements UserLogDao{

	public UserLogDaoImpl() {
		super(UserLog.class);
	}

	/**
	 * Metoda vrati zalogovany zaznam podla zadanej session ID.
	 * 
	 * @param String sessionID
	 * @return UserLog zalogovany zaznam Uzivatela
	 */
	@Override
	public UserLog getBySessionId(String sessionId) {
		 StringBuffer hql = new StringBuffer();
         hql.append("  FROM UserLog l ");
         hql.append("  WHERE l.sessionId = :sessionId");
         return (UserLog) sessionFactory.getCurrentSession()
         				.createQuery(hql.toString())
         				.setString("sessionId", sessionId)
         				.setMaxResults(1)
         				.uniqueResult();
	}

	
}
