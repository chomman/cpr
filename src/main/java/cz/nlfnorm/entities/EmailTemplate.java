package cz.nlfnorm.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;

import cz.nlfnorm.utils.ValidationsUtils;


@SuppressWarnings("serial")
@Entity
@SequenceGenerator(name = "email_template_id_seq", sequenceName = "email_template_id_seq", initialValue = 1, allocationSize =1)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "email_template")
public class EmailTemplate extends AbstractEntity {
	
	/* PORTAL TEMPLATES */
	public final static String PORTAL_ORDER_CREATE = "PORTAL_ORDER_CREATE";
	public final static String PORTAL_ORDER_ACTIVATION = "PORTAL_ORDER_ACTIVATION";
	public final static String PORTAL_ORDER_CANCELATION = "PORTAL_ORDER_CANCELATION";
	
	public final static String PORTAL_MEDIATOR_INFO_ACTIVATION = "PORTAL_MEDIATOR_INFO_ACTIVATION";
	public final static String PORTAL_MEDIATOR_INFO_CANCELATION = "PORTAL_MEDIATOR_INFO_CANCELATION";
	public final static String PORTAL_MEDIATOR_INFO_CREATE_ORDER = "PORTAL_MEDIATOR_INFO_CREATE_ORDER";
	
	/* OTHER TEMPLATES */
	public final static String USER_CHANGE_PASSWORD_REQUEST = "USER_CHANGE_PASSWORD_REQUEST";
	public final static String USER_CREATE = "USER_CREATE";
	
	/* QUASAR TEMPLATES */
	public final static String QUASAR_APPROVAL_REQUEST = "QUASAR_APPROVAL_REQUEST";
	public final static String QUASAR_STATUS_CHANGED = "QUASAR_STATUS_CHANGED";
	
	private String name;
	private String subject;
	private String body;
	private String bcc;
	private String variables;
	private String variablesDescription;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_template_id_seq")
	public Long getId() {
		return super.getId();
	}
	
	@Column(name = "name", length = 100)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "subject", length = 150)
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@Column(name = "bcc_forwarding")		
	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	@Type(type = "text")
	@Column(name = "body")
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	@Column(name = "variables", length = 300)
	public String getVariables() {
		return variables;
	}
	public void setVariables(String variables) {
		this.variables = variables;
	}
	
	@Type(type = "text")
	@Column(name = "variable_description")
	public String getVariablesDescription() {
		return variablesDescription;
	}

	public void setVariablesDescription(String variablesDescription) {
		this.variablesDescription = variablesDescription;
	}
	
	@Transient
	public String[] getAvaiableVariables(){
		if(StringUtils.isNotBlank(variables)){
			String vars = variables.replace(" ", "");
			return vars.split(",");
		}
		return null;
	}
	
	@Transient
	public List<String> getBccEmails(){
		List<String> bccEmails = new ArrayList<String>();
		if(StringUtils.isNotBlank(bcc)){
			String[] emails = bcc.split(",");
			for(String email : emails){
				email = StringUtils.trim(email);
				if(ValidationsUtils.isEmailValid(email)){
					bccEmails.add(email);
				}
			}
		}
		return bccEmails;
	}
	
}
