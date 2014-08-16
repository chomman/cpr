package cz.nlfnorm.entities;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Abstraktna entita, obsahujuca vseky spolocne atributy.
 */
@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractEntity  extends IdentifiableEntity{
	
	/**
    * poskytuje logovaciu funkcionalitu všetkým potomkom
    */
   protected final Log logger = LogFactory.getLog(getClass());
   
      
   /**
    * Datum vytvorenia
    */
   @JsonIgnore
   private LocalDateTime created = new LocalDateTime();
   
   
   /**
    * Datum poslednej zmeny
    */
   @JsonIgnore
   private LocalDateTime changed;
   
   
   /**
    * Vytvoril
    */
   @JsonIgnore
   private User createdBy;
   
   
   /**
    * Zmenil
    */
   @JsonIgnore
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
   @ManyToOne(fetch = FetchType.LAZY)
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
   @ManyToOne(fetch = FetchType.LAZY)
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
   
   @Transient
   public boolean isEnabled(){
	   if(getEnabled() == null || !getEnabled()){
		   return false;
	   }
	   return true;
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
 
}
