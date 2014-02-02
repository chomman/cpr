<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<sec:authorize access="hasRole('ROLE_WEBMASTER')">	
	<c:set var="isLoggedWebmaster" value="true"/>
</sec:authorize>

<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="webpages.add" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
		<jsp:include page="include/webpages-nav.jsp" />
	</div>
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/webpages" />"><spring:message code="webpages" /></a> &raquo;
			 <span><spring:message code="webpages.add" /></span>
		</div>
		<h1><spring:message code="webpages.add" /></h1>

		<div id="content">
		

								
			<form:form  commandName="webpage" method="post" cssClass="valid">
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				<p>
                	<label>
                		<strong><em class="red">*</em>
                			<spring:message code="webpage.name.czechName" />
                		</strong>	
                		<small>(<spring:message code="webpage.name.info"/>)</small>
                	</label>
                    <span class="field">
                    	<form:input  htmlEscape="true" path="nameCzech" maxlength="60" cssClass="w300 required" />
                    </span>
                </p>
                <p class="button-box">
                	 <input type="submit" class="button default" value="<spring:message code="form.saveandcontinue" /> &raquo;" />
                	 <span class="mini-info"><spring:message code="form.moreinfo" /></span>
                </p>
			</form:form>
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>