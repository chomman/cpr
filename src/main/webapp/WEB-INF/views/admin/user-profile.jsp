<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="user.profile" /></title>
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
			 <span><spring:message code="user.profile" /></span>
		</div>
		<h1><spring:message code="user.profile" /></h1>

		<div id="content">
			
			
			<c:url value="/admin/user/profile" var="formUrl"/>
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
                            	<form:input path="user.email" maxlength="50" cssClass="w300 required email" />
                            </span>
                        </p>
                        <p>
                        	<label class="tt" title="<spring:message code="user.change.pass.notice" />">
                        		<spring:message code="user.pass" />
                        		<small><spring:message code="user.change.pass" /></small>
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
                        <p class="button-box">
                        	<form:hidden path="user.id" />
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