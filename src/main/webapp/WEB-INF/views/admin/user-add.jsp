<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="hasRole('ROLE_WEBMASTER')">	
	<c:set var="isLoggedWebmaster" value="true"/>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_SUPERADMIN')">	
	<c:set var="isSuperAdmin" value="true"/>
</sec:authorize>	
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="user.add" /></title>
	<script src="<c:url value="/resources/admin/js/users.js" />"></script>
</head>
<body>
	<div id="wrapper">
	<div id="left">
		<jsp:include page="include/user-nav.jsp" />
	</div>	
	<div id="right">
		
		<c:if test="${isSuperAdmin}">
		
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/users" />"><spring:message code="menu.users" /></a> &raquo;
			 <span><spring:message code="user.add" /></span>
		</div>
		<h1><spring:message code="user.add" /></h1>

		<div id="content">
			
			
			<c:url value="/admin/user/add" var="formUrl"/>
			<form:form  htmlEscape="true" commandName="userForm" method="post" action="${formUrl}" cssClass="valid" >
						
						<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
						
						<c:if test="${not empty successUserCreate}">
							<p class="msg ok"><spring:message code="success.user.create" /></p>
						</c:if>
						
						
						<p class="form-head"><spring:message code="user.login.info" /></p>
						<p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="user.username" />  
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:input path="user.email" maxlength="50" cssClass="w300 required email" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="user.pass" />
                        		</strong>
                        	</label>
                        	<small><spring:message code="password.info" /></small>
                            <span class="field">
                            	<form:password path="password" cssClass="w300 required" maxlength="60" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="user.pass.repeat" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:password path="confifmPassword" cssClass="w300 required" maxlength="60" />
                            </span>
                        </p>
                        
                       <p class="form-head"><spring:message code="user.cred.info" /></p>
                       <p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="user.firstname" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:input path="user.firstName" cssClass="w300 required" maxlength="50" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="user.lastname" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:input path="user.lastName" cssClass="w300 required" maxlength="50" />
                            </span>
                        </p>
                         <p>
                        	<label class="tt" title="<spring:message code="user.enabled.notice" />">
                        			<spring:message code="user.enabled" />
                        	</label>
                            <span class="field">
                            	<form:checkbox path="user.enabled" />
                            </span>
                        </p>
                        <p>
                        	<label class="tt" title="<spring:message code="user.sendemail.info" />">
                        			<spring:message code="user.sendemail" />
                        	</label>
                            <span class="field">
                            	<form:checkbox path="sendEmail" />
                            </span>
                        </p>
                        
						<c:if test="${isSuperAdmin}">
						<p class="form-head"><spring:message code="user.roles" /></p>
						<p class="msg info"><spring:message code="user.role.notice" /></p>
                       	<table class="roles">
							<c:forEach items="${userForm.roles}" var="item" varStatus="i">
								<c:if test="${isLoggedWebmaster and item.authority.code == 'ROLE_WEBMASTER'}">
										<tr id="${item.authority.code}">
											<td class="check"><form:checkbox path="roles[${i.index}].selected" data-role="${item.authority.code}" /></td>
											<td class="name"><c:out value="${item.authority.name}" /></td>
											<td class="descr"><c:out value="${item.authority.shortDescription}" /></td>
										</tr>
								</c:if>
								<c:if test="${item.authority.code != 'ROLE_WEBMASTER'}">
										<tr id="${item.authority.code}">
											<td class="check"><form:checkbox path="roles[${i.index}].selected" data-role="${item.authority.code}" /></td>
											<td class="name"><c:out value="${item.authority.name}" /></td>
											<td class="descr"><c:out value="${item.authority.shortDescription}" /></td>
										</tr>
								</c:if>
							</c:forEach>
						</table>
					</c:if>		
                        <form:hidden path="user.id" />
                        <p class="button-box">
                        	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                        </p>
					</form:form>
			<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>
		</div>	
		
		</c:if>
		<c:if test="${not isSuperAdmin}">
			<p class="msg error"><spring:message code="error.unauthorized" /></p>
		</c:if>
		
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>