<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>						
<h4>Editace obsahu pro: <spring:message code="regulation.${lang}" /> ${regulation.code}</h4>
<div>
	<div class="input-wrapp smaller">
		<label> 
			<strong><em class="red">*</em>
			<spring:message code="regulation.content.name" /> v ČEŠTINE alebo Slovenštině:
			</strong>
		</label>
		<div class="field">
			<form:input path="localized['${lang}'].nameCzech" cssClass="mw500" maxlength="100" />
		</div>
	</div>
	<div class="input-wrapp smaller">
		<label> 
			<spring:message code="regulation.content.name" /> v ANGLIČTINE:
		</label>
		<div class="field">
			<form:input path="localized['${lang}'].nameEnglish" cssClass="mw500" maxlength="100" />
		</div>
	</div>
	<div class="input-wrapp smaller">
		<label> 
			<spring:message code="regulation.content.pdf" /> v ČEŠTINE alebo Slovenštině:
		</label>
		<div class="field">
			<form:input path="localized['${lang}'].pdfCzech" cssClass="mw500" maxlength="150" />
		</div>
	</div>
	<div class="input-wrapp smaller">
		<label> 
			<spring:message code="regulation.content.pdf" /> v ANGLIČTINE:
		</label>
		<div class="field">
			<form:input path="localized['${lang}'].pdfEnglish" cssClass="mw500" maxlength="150" />
		</div>
	</div>
	<div class="input-wrapp">
		<label> 
			<spring:message code="regulation.content.note" />
		</label>
		<div class="field mh150">
			<form:textarea path="localized['${lang}'].description" cssClass="wisiwig" />
		</div>
		<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
					