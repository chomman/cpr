<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="cpr.report.add" /></title>
</head>
<body>
	<div id="wrapper">
		<div id="left">
		
			<jsp:include page="include/cpr-nav.jsp" />
			
		</div>	
		<div id="right">
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /> </a:adminurl>  &raquo;
				 <a:adminurl href="/cpr/reports"><spring:message code="cpr.report.list" /> </a:adminurl>  &raquo;
				 <span><spring:message code="cpr.report.add" /></span>
			</div>
			<h1><spring:message code="cpr.report.add" /></h1>
	
			<div id="content">
						
				<ul class="sub-nav">
					<li>
						<a:adminurl href="/cpr/reports" >
							<spring:message code="cpr.report.list" />
						</a:adminurl>
					</li>
					<li>
						<a:adminurl href="/cpr/report/add" cssClass="active">
							<spring:message code="cpr.report.add" />
						</a:adminurl>
					</li>
				</ul>
	
	
				<form:form  commandName="report" method="post"  cssClass="valid" >					
					<p class="form-head"><spring:message code="cpr.report.add.period" /></p>
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
					<p>
					 	<label>
					 		<spring:message code="cpr.report.dateFrom" />:
					 	</label>
					     <span class="field">  
					     	<form:input path="dateFrom" maxlength="25" cssClass="date required" />
					     	<span>
					     		<spring:message code="cpr.report.dateTo" />:
					     	</span>
					     	<form:input path="dateTo" maxlength="25" cssClass="date required" />
					     </span>
					</p>
					 <p class="button-box">
	                	 <input type="submit" class="button default" value="<spring:message code="form.save" />" />
	                	 <span class="mini-info"><spring:message code="form.moreinfo" /></span>
	                </p>
				</form:form>
	
			</div>	
		</div>
		<div class="clear"></div>	
	</div>

</body>
</html>