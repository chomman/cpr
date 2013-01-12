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
							
					
					<ul class="sub-nav">
						<li><a href="<c:url value="/admin/cpr/standards"  />"><spring:message code="cpr.standard.view" /></a></li>
						<li><a href="<c:url value="/admin/cpr/standard/add"  />"><spring:message code="cpr.standard.add" /></a></li>
					</ul>
								
				<div id="tabs">
					<a class="tab tt"  
						href="<c:url value="/admin/cpr/standard/edit/${standardId}" />" >
						<span>1</span> - <spring:message code="cpr.standard.tab.1" />
					</a>
					<strong class="active-tab-head"><span>2</span> - <spring:message code="cpr.standard.tab.2" /></strong>
					
					<!-- ACTIVE TAB -->
					<div class="active-tab">
					
					<div id="req-nav">
						<a class="view" href="<c:url value="/admin/cpr/standard/edit/${standardId}/requirements?country=1" />">
							&laquo; <spring:message code="cpr.requirement.view" />
						</a>
					</div>
					
					
				
					<!--  FORM  -->
					<c:url value="/admin/cpr/standard/edit/${standardId}/req/${requirementId}" var="formUrl"/>
					<form:form commandName="requirement" method="post" action="${formUrl}"  >
				
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
	                    	<form:input path="name" maxlength="100" cssClass="mw500" />
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
	                    	<form:input path="section" maxlength="20" cssClass="w100" />
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

					<a class="tab tt" title="<spring:message code="cpr.standard.tab.3.title" />" 
						href="<c:url value="/admin/cpr/standard/edit/${standardId}/notifiedbodies" />" >
						<span>3</span> - <spring:message code="cpr.standard.tab.3" />
					</a>
					<a class="tab tt" title="<spring:message code="cpr.standard.tab.4.title" />" 
							href="<c:url value="/admin/cpr/standard/edit/${standardId}/other" />" >
							<span>4</span> - <spring:message code="cpr.standard.tab.4" />
					</a>
					<a class="tab tt" title="<spring:message code="cpr.standard.tab.5.title" />" 
						href="<c:url value="/admin/cpr/standard/edit/${standardId}/describe" />" >
						<span>5</span> - <spring:message code="cpr.standard.tab.5" />
					</a>
				</div>	<!-- END TABs -->
		
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>