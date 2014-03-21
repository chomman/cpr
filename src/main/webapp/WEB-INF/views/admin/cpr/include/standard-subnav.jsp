<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<ul class="sub-nav">
	<li><a href="<c:url value="/admin/cpr/standards"  />"><spring:message code="cpr.standard.view" /></a></li>
	<li><a href="<c:url value="/admin/cpr/standard/add"  />"><spring:message code="cpr.standard.add" /></a></li>
	<li><a data-fancybox-type="ajax" class="standard-preview" href="<c:url value="/preview/standard/${standardId}"  />"><spring:message code="form.preview" /></a></li>
	<li><a:url extraAttr="data-fancybox-type;iframe" cssClass="fancyboxiframe"  href="${model.webpage.code}"><a:localizedValue object="${model.webpage}" fieldName="name" /></a:url></li>	
</ul>