<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="article.add" /></title>
	<script src="<c:url value="/resources/admin/tiny_mce/tiny_mce.js" />"></script>
	<script src="<c:url value="/resources/admin/js/article.js" />"></script>
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
			<c:if test="${not empty article.createdBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.created" /></td>
						<td class="val">${article.createdBy.firstName} ${article.createdBy.lastName}</td>
						<td class="val"><joda:format value="${article.created}" pattern="dd.MM.yyyy / hh:mm"/></td>
					</tr>
					<c:if test="${not empty article.changedBy}">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${article.changedBy.firstName} ${article.changedBy.lastName}</td>
						<td class="val"><joda:format value="${article.changed}" pattern="dd.MM.yyyy / hh:mm"/></td>
					</tr>
					</c:if>
				</table>
			</c:if>
			
			<c:url value="/admin/article/edit/${articleId}" var="formUrl"/>					
			<form:form  commandName="article" method="post" action="${formUrl}">
				
				<div id="ajax-result"></div>
				
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				<p class="form-head"><spring:message code="article.head.setting.info" /><p>
                <p>
                	<label>
                		<strong><em class="red">*</em>
                			<spring:message code="article.released" />:
                		</strong>
                	</label>
                    <span class="field">
                    	<input type="text" class="date" id="publishedSince-date" maxlength="10" />
                    	<input type="text" class="time" id="publishedSince-time" maxlength="5" />
                    	<form:hidden path="publishedSince" />
                    </span>
                </p>
                  <p>
                	<label>
                		<strong><em class="red">*</em>
                			<spring:message code="article.released" />:
                		</strong>
                	</label>
                    <span class="field">
                    	<input type="text" class="date" id="publishedUntil-date" maxlength="10" />
                    	<input type="text" class="time" id="publishedUntil-time" maxlength="5"/>
                    	<form:hidden path="publishedUntil"/>
                    </span>
                </p>
                <p>
				    <label title="<spring:message code="publish.descr" />" class="tt">
				 		<spring:message code="publish" />
				 	</label>
				     <span class="field">  
				     	<form:checkbox path="enabled" />
				     </span>
				 </p>
				 <p class="form-head"><spring:message code="article.head.content.info" /><p>
				 <p>
                	<label>
                		<strong><em class="red">*</em>
                			<spring:message code="article.title" />:
                		</strong>
                	</label>
                    <span class="field">
                    	<form:input path="title" maxlength="150" cssClass="mw500 required" />
                    </span>
                </p>
				 <p>
				    <label>
				 		<spring:message code="article.header" />
				 	</label>
				     <span class="field counter">  
				     	<form:textarea path="header" cssClass="header" />
				     	<span id="chars"></span>
				     </span>
				 </p>
				 
				 <p>
				    <label>
				 		<spring:message code="article.content" />
				 	</label>
				     <span class="field">  								
				     	<form:textarea path="articleContent" cssClass="mceEditor " />
				     </span>
				 </p>
                <form:hidden path="id"/>
                <form:hidden path="timestamp"/>
                <p class="button-box">
                	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                </p>
			</form:form>
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>