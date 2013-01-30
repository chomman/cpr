<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="article.add" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/article-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/articles" />"><spring:message code="dashboard.news.title" /></a> &raquo;
			 <span><spring:message code="article.add" /></span>
		</div>
		<h1><spring:message code="article.add" /></h1>

		<div id="content">
		
		<p class="msg info"><spring:message code="article.add.info" /></p>
			<c:url value="/admin/article/add" var="formUrl"/>					
			<form:form commandName="article" method="post" action="${formUrl}" cssClass="valid"  >
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				<p>
                	<label>
                		<strong><em class="red">*</em>
                			<spring:message code="article.title" />:
                		</strong>
                	</label>
                    <span class="field">
                    	<form:input  htmlEscape="true" path="title" maxlength="150" cssClass="mw500 required" />
                    </span>
                </p>
                <p class="button-box">
                	 <input type="submit" class="button" value="<spring:message code="form.saveandcontinue" /> &raquo;" />
                </p>
			</form:form>
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>