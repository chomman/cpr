package cz.nlfnorm.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.jadira.usertype.dateandtime.joda.PersistentDateTime;
import org.joda.time.DateTime;

/**
 * Logovanie vynimiek
 * @author Peter Jurkovič (email@peterjurkovic.sk)
 *
 */
@Entity
@Table(name = "exception_log")
@SequenceGenerator(name = "exception_log_id_seq", sequenceName = "exception_log_id_seq", initialValue = 1, allocationSize =1)
@TypeDefs( { @TypeDef(name = "jodaDateTime", typeClass = PersistentDateTime.class) })
public class ExceptionLog {
	
	private Long id;
	
	private User user;
	
	private DateTime created;
	
	private String mehtod;
	
	private String url;
	
	private String stackTrace;
	
	private String message;
	
	private String type;
	
	private String referer;

	private String queryParams;
	private String requestParams;
	private String requestHeaders;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exception_log_id_seq")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Type(type = "jodaDateTime")
	@Column(name = "created")
	public DateTime getCreated() {
		return created;
	}

	public void setCreated(DateTime created) {
		this.created = created;
	}

	public String getMehtod() {
		return mehtod;
	}
	@Column(length = 10)
	public void setMehtod(String mehtod) {
		this.mehtod = mehtod;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Type(type = "text")
	@Column(name = "stack_trace")
	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	@Type(type = "text")
	@Column(name = "query_params")
	public String getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}
	
	@Type(type = "text")
	@Column(name = "request_params")
	public String getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(String requestParams) {
		this.requestParams = requestParams;
	}

	@Type(type = "text")
	@Column(name = "request_headers")
	public String getRequestHeaders() {
		return requestHeaders;
	}

	public void setRequestHeaders(String requestHeaders) {
		this.requestHeaders = requestHeaders;
	}
	
	
	
	
}
