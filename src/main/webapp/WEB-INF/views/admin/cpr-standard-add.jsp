<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.standard.add" /></title>
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
			 <span><spring:message code="cpr.standard.add" /></span>
		</div>
		<h1><spring:message code="cpr.standard.add" /></h1>

		<div id="content">
							
					
					<ul class="sub-nav">
						<li><a href="<c:url value="/admin/cpr/standards"  />"><spring:message code="cpr.standard.view" /></a></li>
						<li><a class="active" href="<c:url value="/admin/cpr/standard/add"  />"><spring:message code="cpr.standard.add" /></a></li>
					</ul>
				
					<c:url value="/admin/cpr/standard/edit/0" var="formUrl"/>
					
					<form:form commandName="standard" method="post" action="${formUrl}"  >
						
						<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
						
						<c:if test="${not empty successCreate}">
							<p class="msg ok"><spring:message code="success.create" /></p>
						</c:if>
						<p class="form-head">Základní informace<p>
						<p>
                        	<label>
                        		<strong><em class="red">*</em>
                        			<spring:message code="cpr.standard.id" />
                        		</strong>
                        	</label>
                            <span class="field">
                            	<form:input path="standardId"  cssClass="w200" maxlength="45" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<spring:message code="cpr.standard.replacedStandardId" />
                        	</label>
                            <span class="field">
                            	<form:input path="replacedStandardId" cssClass="w200" maxlength="45" />
                            </span>
                        </p>
                        <p>
                        	<label>
                        		<em class="red">*</em>
                        		<spring:message code="cpr.standard.name" />
                        	</label>
                            <span class="field">
                            	<form:input path="standardName" cssClass="mw500" maxlength="255" />
                            </span>
                        </p>
                        
                        <p>
                        	<label>
                        		<spring:message code="cpr.standard.validity.from" />
                        	</label>
                            <span class="field">  
                            	<form:input path="startValidity" maxlength="25" cssClass="date"  />
                            	<span class="sublabel">do</span>
                            	<form:input path="stopValidity" maxlength="25" cssClass="date"  />
                            </span>
                        </p>
                        
                        
                        <p>
                        	<label>
                        		<spring:message code="cpr.standard.concurrentvalidity.form" />
                        	</label>
                            <span class="field">  
                            	<form:input path="startConcurrentValidity" maxlength="25" cssClass="date"  />
                            	<span class="sublabel">do</span>
                            	<form:input path="stopConcurrentValidity" maxlength="25" cssClass="date"  />
                            </span>
                        </p>
                        
                        <p>
                        	<label>
                        		<spring:message code="cpr.standard.group" />
                        	</label>
                            <span class="field">  
                            	<form:select path="standardGroup" cssClass="mw500">
			                      <option value="-"><spring:message code="form.select"/></option>
			                      <form:options items="${model.groups}" itemValue="id" itemLabel="groupName" />
			                 	</form:select>
                            </span>
                        </p>
                        
                        <p>
                        	<label title="<spring:message code="publish.descr" />" class="tt">
                        		<spring:message code="publish" />
                        	</label>
                            <span class="field">  
                            	<form:checkbox path="enabled" />
                            </span>
                            <div class="clear"></div>
                        </p>

                        <form:hidden path="id"  />
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