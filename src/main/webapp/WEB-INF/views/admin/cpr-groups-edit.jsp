<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="cpr.groups.title" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/cpr-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr" />"><spring:message code="menu.cpr" /></a> &raquo;
			 <span><spring:message code="cpr.groups.title" /></span>
		</div>
		<h1><spring:message code="cpr.groups.title" /></h1>

		<div id="content">
			
			<c:if test="${not empty notFoundError}">
				<p class="msg error"><spring:message code="error.notFoundError" /></p>
			</c:if>
			
			<c:if test="${not empty success}">
				<p class="msg ok"><spring:message code="succes" /></p>
			</c:if>
			
			<c:if test="${empty notFoundError}">
					<c:url value="/admin/cpr/groups/edit/${standardGroupId}" var="formUrl"/>
					
					<form:form commandName="standardGroup" method="post" action="${formUrl}" >
						<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"/>
						
						<p>
                        	<label>
                        		<spring:message code="form.name" /> <spring:message code="cpr.groups" />
                        	</label>
                            <span class="field">
                            	<form:input path="groupName" maxlength="255" cssClass="mw500" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<spring:message code="form.code" /> <spring:message code="cpr.groups" />
                        	</label>
                            <span class="field">
                            	<form:input path="code" maxlength="255" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<spring:message code="cpr.groups.fileUrl" />
                        	</label>
                            <span class="field">
                            	<form:input path="commissionDecisionFileUrl" maxlength="255"  cssClass="mw500" />
                            </span>
                        </p>
                        <form:hidden path="timestamp"/>
                        <form:hidden path="id"/>
                        <p class="button-box">
                        	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                        </p>
					</form:form>
			</c:if>

		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>