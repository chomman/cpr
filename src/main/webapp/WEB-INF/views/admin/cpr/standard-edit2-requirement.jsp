<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></title>
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
			 <a href="<c:url value="/admin/cpr/standards" />"><spring:message code="menu.cpr.norm" /></a> &raquo;
			 <span><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></span>
		</div>
		<h1><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></h1>

		<div id="content">
							
					
				<jsp:include page="include/standard-subnav.jsp" />
								
				<div id="tabs">
					
					<jsp:include page="include/cpr-standard-menu1.jsp" />

					<strong class="active-tab-head"><spring:message code="cpr.standard.tab.2" /></strong>
					
					<!-- ACTIVE TAB -->
					<div class="active-tab">
					
					<div id="req-nav">
						<a class="view" href="<c:url value="/admin/cpr/standard/edit/${standardId}/requirements?country=1" />">
							&laquo; <spring:message code="cpr.requirement.view" />
						</a>
					</div>
					
					
				
					<!--  FORM  -->
					<c:url value="/admin/cpr/standard/edit/${standardId}/req/${requirementId}" var="formUrl"/>
					<form:form commandName="requirement" method="post" action="${formUrl}" cssClass="valid"  >
				
						<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
						<c:if test="${not empty successCreate}">
							<p class="msg ok"><spring:message code="success.create" /></p>
						</c:if>
				
					<p>
	                	<label>
	                		<strong><em class="red">*</em>
	                			<spring:message code="cpr.requirement.name" />
	                		</strong>
	                	</label>
	                    <span class="field">
	                    	<form:input path="name" maxlength="100" cssClass="mw500 required" />
	                    </span>
                    </p>
                    <p>
	                	<label class="tt" title="např. reakce na ohneň A1 až F">
                			<spring:message code="cpr.requirement.level" />
	                	</label>
	                    <span class="field">
	                    	<form:input path="levels" maxlength="50" cssClass="w300" />
	                    </span>
                    </p>
                    <p>
	                	<label>
                			<spring:message code="cpr.requirement.note" />
	                	</label>
	                    <span class="field">
	                    	<form:input path="note" maxlength="150" cssClass="mw500" />
	                    </span>
                    </p>
                    <p>
	                	<label class="tt" title="Odsek (např. 4.1.2, 5.1 ...)" >
                			<spring:message code="cpr.requirement.section" />
	                	</label>
	                    <span class="field">
	                    	<form:input path="section" maxlength="50" cssClass="w300" />
	                    </span>
                    </p>
     				<p>
	                	<label>
                			<spring:message code="cpr.requirement.npd" />
	                	</label>
	                    <span class="field">
	                    	<form:checkbox path="npd" />
	                    </span>
                    </p>
                      
                   <p>
						<label>
							<spring:message code="cpr.requirement.country" />
							</label>
							<span class="field">  
							<form:select path="country" cssClass="mw300">
							<form:options items="${model.countries}" itemValue="id" itemLabel="countryName" />
							</form:select>
						</span>
                   </p>
                   <form:hidden path="id" />
                   <p class="button-box">
                   	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                   </p>
			</form:form>
					<!-- END FORM -->	
					</div> <!-- END ACTIVE TAB -->

					<jsp:include page="include/cpr-standard-menu3.jsp" />
					<jsp:include page="include/cpr-standard-menu4.jsp" />
					<jsp:include page="include/cpr-standard-menu5.jsp" />
					<jsp:include page="include/cpr-standard-menu6.jsp" />
				</div>	<!-- END TABs -->
		
			<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>