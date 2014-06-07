package cz.nlfnorm.quasar.entities;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = "quasar_field_of_education_id_seq", sequenceName = "quasar_field_of_education_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "quasar_field_of_education")
public class FieldOfEducation extends BaseEntity {

}
