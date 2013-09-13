package sk.peterjurkovic.cpr.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import sk.peterjurkovic.cpr.dao.impl.IdentifiableByLong;


/**
 * Abstraktna entita, obsahujuca vseky spolocne atributy.
 * @author peto
 *
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractEntity  implements Serializable, IdentifiableByLong{
	
	/**
    * poskytuje logovaciu funkcionalitu všetkým potomkom
    */
   protected final Log logger = LogFactory.getLog(getClass());
   
   
   
   /**
    * Jednoznačný identifikátor všetkých záznamov
    */
   private Long id;
   
   
   /**
    * Datum vytvorenia
    */
   private LocalDateTime created = new LocalDateTime();
   
   
   /**
    * Datum poslednej zmeny
    */
   private LocalDateTime changed;
   
   
   /**
    * Vytvoril
    */
   private User createdBy;
   
   
   /**
    * Zmenil
    */
   private User changedBy;
   
   
   /**
    * Aktivny zaznam
    */
   private Boolean enabled = Boolean.TRUE;
   
   
   /**
    *  Kod objektu
    */
   private String code;
   
   
   
   
   /**
    * Metod deleguje na potomkov a vracia ich ID pre potreby metod equals, hashcode a toString
    * 
    * @return id
    */
   @Transient
   @Override
   public Long getId() {
       return id;
   }

   /**
    * Metoda nastavuje id objektu.
    */
   public void setId(Long id) {
       this.id = id;
   }
   
   
   
   /**
    * Metoda vracia datum vytvorenia.
    * 
    * @return datum
    */
   @Column(name = "created")
   @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   public LocalDateTime getCreated() {
       return created;
   }

   /**
    * Metoda nastavuje datum vytvorenia.
    * 
    * @param Date
    *            : datum
    */
   public void setCreated(LocalDateTime created) {
       this.created = created;
   }

   /**
    * Metoda vracia datum poslednych zmien dat v objektu.
    * 
    * @return datum
    */
   @Column(name = "changed")
   @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
   public LocalDateTime getChanged() {
       return changed;
   }


   /**
    * Metoda nastavuje datum poslednej zmeny objektu
    * 
    * @param Date
    *            : datum
    */
   public void setChanged(LocalDateTime changed) {
       this.changed = changed;
   }
   
   
   
   /**
    * Metoda vracia autora objektu
    * 
    * @return zakladatel
    */
   @ManyToOne
   @JoinColumn(name = "id_user_created_by")
   public User getCreatedBy() {
       return createdBy;
   }

   /**
    * Metoda nastavuje autora.
    * 
    * @param User
    *            : zakladatel
    */
   public void setCreatedBy(User createdBy) {
       this.createdBy = createdBy;
   }

   /**
    * Metoda vracia autora poslednej zmeny
    * 
    * @return autor poslednej zmeny
    */
   @ManyToOne
   @JoinColumn(name = "id_user_changed_by")
   public User getChangedBy() {
       return changedBy;
   }

   /**
    * Metoda nastavuje autora poslednej zmeny.
    * 
    * @param changedBy
    *            User: autor poslednej zmeny
    */
   public void setChangedBy(User changedBy) {
       this.changedBy = changedBy;
   }

   /**
    * Metoda idetifikujuca aktivitu objektu.
    * 
    * @return true alebo false podla toho ci je aktivny
    */
   @Column(name = "enabled")
   public Boolean getEnabled() {
       return enabled;
   }


   /**
    * Metoda nastavuje promennou enabled
    * 
    * @param enabled
    *            Boolean: hodnota
    */
   public void setEnabled(Boolean enabled) {
       this.enabled = enabled;
   }
   
   
   /**
    * Metoda nastavuje kod objektu
    * 
    * @return
    */
   public String getCode() {
       return code;
   }

   /**
    * Metoda vriacia code
    * 
    * @param String
    *            : code
    */
   public void setCode(String code) {
       this.code = code;
   }

   
   
   
   /**
    * Implementiacia hascode pracujicich na idckach.
    * @return hash
    */
   @Override
   public int hashCode() {
       final int prime = 31;
       int result = 1;
       result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
       return result;
   }


   /**
    * Implementácia metody equals porovnavajuca idcka .
    * @param Object
    *            : porovnavany objekt
    * 
    * @return true alebo false podla toho ci plati rovnost
    */
   @Override
   public boolean equals(Object obj) {
       if (this == obj)
           return true;
       if (obj == null)
           return false;
       if (getClass() != obj.getClass())
           return false;
       final AbstractEntity other = (AbstractEntity)obj;
       if (getId() == null) {
           if (other.getId() != null)
               return false;
       } else if (!getId().equals(other.getId()))
           return false;
       return true;
   }
}
