package cz.nlfnorm.quasar.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;

public abstract class PersistentEnumUserType<T extends PersistentEnum> implements UserType {
	 
    @Override
    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return cached;
    }
 
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }
 
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable)value;
    }
 
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y;
    }
 
    @Override
    public int hashCode(Object x) throws HibernateException {
        return x == null ? 0 : x.hashCode();
    }
 
    @Override
    public boolean isMutable() {
        return false;
    }
 
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names,	SessionImplementor session, Object owner)throws HibernateException, SQLException {
          if(rs.wasNull()) {
              return null;
          }
          final int id = rs.getInt(names[0]);
          for(PersistentEnum value : returnedClass().getEnumConstants()) {
              if(id == value.getId()) {
                  return value;
              }
          }
    	return null;
    }
    
   @Override
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
		   if (value == null) {
	           st.setNull(index, Types.SMALLINT);
	       } else {
	           st.setInt(index, ((PersistentEnum)value).getId());
	       }
	}
 
    
 
    @Override
    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return original;
    }
 
    @Override
    public abstract Class<T> returnedClass();
 
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.SMALLINT};
    }
 
}