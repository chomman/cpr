package cz.nlfnorm.entities;

import java.util.Locale;

import javax.persistence.Entity;

@Entity
public class LocalizedReport {
	
	private Report report;
	
	private String content;
	
	private Locale locale;

	
	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
	
	
	
}
