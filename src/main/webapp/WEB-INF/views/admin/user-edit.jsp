<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<sec:authorize access="hasRole('ROLE_WEBMASTER')">	
	<c:set var="isLoggedWebmaster" value="true"/>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ADMIN')">	
	<c:set var="isLoggedAdmin" value="true"/>
</sec:authorize>							
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="user.edit" >${userForm.user.email}</spring:message></title>
	<script src="<c:url value="/resources/admin/js/users.js" />"></script>
</head>
<body>
	<div id="wrapper">
	<div id="left">
		<jsp:include page="include/user-nav.jsp" />
	</div>	
	<div id="right">
		
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/users" />"><spring:message code="menu.users" /></a> &raquo;
			 <span><spring:message code="user.edit" />&nbsp; ${userForm.user.email}</span>
		</div>
		<h1><spring:message code="user.edit" />&nbsp; ${userForm.user.email}</h1>

		<div id="content">
			
			
			<c:url value="/admin/user/edit/${userId}" var="formUrl"/>
			<form:form  htmlEscape="true" commandName="userForm" method="post" action="${formUrl}" cssClass="valid" >
						
						<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
						
						<c:if test="${not empty successCreate}">
							<p class="msg ok"><spring:message code="success.create" /></p>
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
                        	<label class="tt" title="<spring:message code="user.change.pass.notice" />">
                        		<spring:message code="user.pass" />
                        		<small><spring:message code="password.info" /></small> 
                        	</label>
                            <span class="field">
                            	<form:password path="password" cssClass="w300" maxlength="60" />
                            </span>
                        </p>
                        <p>
                        	<label class="tt" title="<spring:message code="user.change.pass.notice" />">
                        		<spring:message code="user.pass.repeat" />
                        		
                        	</label>
                            <span class="field">
                            	<form:password path="confifmPassword" cssClass="w300" maxlength="60" />
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
						<p class="form-head"><spring:message code="user.roles" /></p>
						
						
						<c:if test="${not sameUser or isLoggedWebmaster}">
							<p class="msg info"><spring:message code="user.role.notice" /></p>
	                       	<table class="roles">
								
								<c:if test="${isLoggedWebmaster}">
									<c:forEach items="${userForm.roles}" var="item" varStatus="i">
											<tr id="${item.authority.code}">
												<td class="check"><form:checkbox path="roles[${i.index}].selected" data-role="${item.authority.code}" /></td>
												<td class="name"><c:out value="${item.authority.name}" /></td>
												<td class="descr"><c:out value="${item.authority.shortDescription}" /></td>
											</tr>
									</c:forEach>
								</c:if>
								
								<c:if test="${isLoggedAdmin and not isLoggedWebmaster}">
									<c:forEach items="${userForm.roles}" var="item" varStatus="i">
											<c:if test="${not item.authority.code == 'ROLE_WEBMASTER'}">
												<tr id="${item.authority.code}">
													<td class="check"><form:checkbox path="roles[${i.index}].selected" data-role="${item.authority.code}" /></td>
													<td class="name"><c:out value="${item.authority.name}" /></td>
													<td class="descr"><c:out value="${item.authority.shortDescription}" /></td>
												</tr>
											</c:if>
									</c:forEach>
								</c:if>			
							</table>
	                       <form:hidden path="user.id" />
	                        <p class="button-box">
	                        	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
	                        </p>
						</c:if>
						<c:if test="${sameUser and not isLoggedWebmaster}">
							<p class="msg alert"><spring:message code="user.rights.own" /></p>
						</c:if>
					</form:form>
			<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>