<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="cpr.report.edit" /></title>
	<script src="<c:url value="/resources/admin/tinymce/tinymce.min.js" />"></script>
	<script src="<c:url value="/resources/admin/js/cpr/report.js" />"></script>
	<link rel="stylesheet" href="<c:url value="/resources/public/css/common.css" />" />
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
				 <span><spring:message code="cpr.report.edit" /></span>
			</div>
			<h1><spring:message code="cpr.report.edit" /></h1>
	
			<div id="content">
						
				<ul class="sub-nav">
					<li>
						<a:adminurl href="/cpr/reports" >
							<spring:message code="cpr.report.list" />
						</a:adminurl>
					</li>
					<li>
						<a:adminurl href="/cpr/report/add">
							<spring:message code="cpr.report.add" />
						</a:adminurl>
					</li>
				</ul>
	
	
				<form:form  commandName="report" method="post"  cssClass="valid" >					
					<p class="form-head"><spring:message code="cpr.report.edit" /></p>
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
					<c:if test="${not empty successCreate}">
						<p class="msg ok"><spring:message code="success.create" /></p>
					</c:if>
					<p>
					 	<label>
					 		<spring:message code="cpr.report.dateFrom" />:
					 	</label>
					     <span class="field">  
					     	<form:input path="dateFrom" maxlength="25" cssClass="date required"  />
					     	<span>
					     		<spring:message code="cpr.report.dateTo" />:
					     	</span>
					     	<form:input path="dateTo" maxlength="25" cssClass="date required"  />
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
					 <p>
	                	<label>
	                		<strong>
	                			<spring:message code="webpage.locale" />:
	                		</strong>	
	                		
	                	</label>
	                     <span class="field">
	                    	<a href="#" data-lang="cs" class="disabled">Česká</a>
	                    	<a href="#" data-lang="en" class="lang">Anglická</a>
	                    </span>
	                </p> 
	                <p class="switchable cs">
					    <label>
					 		<spring:message code="cpr.report.content" />
					 		(<spring:message code="cs" />)
					 	</label>
					     <span class="field">  								
					     	<form:textarea path="contentCzech" cssClass="wisiwig" />
					     </span>
					 </p>
					 <p class="switchable en hidden">
					    <label>
					 		<spring:message code="cpr.report.content" />
					 		(<spring:message code="en" />)
					 	</label>
					     <span class="field">  								
					     	<form:textarea path="contentEnglish" cssClass="wisiwig" />
					     </span>
					 </p>
					 <p class="button-box">
	                	 <input type="submit" class="button default" value="<spring:message code="form.save" />" />
	                </p>
				</form:form>
				
				<div class="report-wrapp">
					<h3><spring:message code="cpr.report.standards" /> </h3>
					<jsp:include page="../../public/cpr/include/standard-table.jsp" />
				</div>
				
				<div class="report-wrapp">
					<h3><spring:message code="csn" /> </h3>
					<c:if test="${not empty model.standardCsns}">
						<table class="csn">
							<c:forEach items="${model.standardCsns}" var="csn">
								<tr>
									<td class="name key">
										<a:standardCsnUrl object="${csn}" editable="${editable}" />
									</td>
									<td>
									<c:if test="${not empty csn.standardStatus and csn.standardStatus.id != 1}">
										(<spring:message code="${csn.standardStatus.name}" />)
									</c:if>
									${csn.note}
									</td>
								</tr>
						 		<c:forEach items="${csn.standardCsnChanges}" var="j">
							 		<tr class="standard-change csn-change">
							 			<td class="name key">
							 				<a:standardCsnUrl object="${j}" editable="${editable}" />
							 			</td>					
							 			<td >${csn.note}</td>
							 		</tr>
						 		</c:forEach>
							</c:forEach>
						</table>
					</c:if>
				</div>
			
			</div>	
		</div>
		<div class="clear"></div>	
	</div>

</body>
</html>