package cz.nlfnorm.web.json.dto;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cz.nlfnorm.web.json.serializers.SgpportalOnlinePublicationSerializer;

@JsonSerialize(using = SgpportalOnlinePublicationSerializer.class)
public class SgpportalOnlinePublication {
	
	public final static String SGP_PORTAL_ACCESS = "SGP_portal";
	
	private long userId;
	private String code;
	private LocalDate validity;
	private LocalDateTime changed;
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public LocalDate getValidity() {
		return validity;
	}
	public void setValidity(LocalDate validity) {
		this.validity = validity;
	}
	public LocalDateTime getChanged() {
		return changed;
	}
	public void setChanged(LocalDateTime changed) {
		this.changed = changed;
	}
	
	
	
}
