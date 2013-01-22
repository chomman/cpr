<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="user.add" /></title>
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
			 <span><spring:message code="user.add" /></span>
		</div>
		<h1><spring:message code="user.add" /></h1>

		<div id="content">
			
			
			<c:url value="/admin/cpr/mandates/edit/${userId}" var="formUrl"/>
			<form:form commandName="userForm" method="post" action="${formUrl}" cssClass="valid" >
						
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
                            	<form:input path="user.email" maxlength="50" cssClass="required email" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="user.pass" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:input path="user.password" cssClass="w300" maxlength="60" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="user.pass.repeat" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:input path="confifmPassword" cssClass="w300" maxlength="60" />
                            </span>
                        </p>
                        
                       <p class="form-head"><spring:message code="user.cred.info" /></p>
                       <p>
                        	<label>
                        			<spring:message code="user.firstname" />
                        	</label>
                            <span class="field">
                            	<form:input path="user.firstName" cssClass="w300" maxlength="50" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<spring:message code="user.lastname" />
                        	</label>
                            <span class="field">
                            	<form:input path="user.lastName" cssClass="w300" maxlength="50" />
                            </span>
                        </p>
                         <p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="user.enabled" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:checkbox path="user.enabled" />
                            </span>
                        </p>
						<p class="form-head"><spring:message code="user.roles" /></p>
                       	<table>
							<c:forEach items="${userForm.roles}" var="item" varStatus="i">	
							<tr>
								<td><form:checkbox path="roles[${i.index}].selected" /></td>
								<td><c:out value="${item.authority.name}" /></td>
								<td><c:out value="${item.authority.description}" /></td>
							</tr>
							</c:forEach>
						</table>
                        <form:hidden path="user.id" />
                        <p class="button-box">
                        	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                        </p>
					</form:form>
		
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>