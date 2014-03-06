<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/multi-select.css" />" />
	<script src="<c:url value="/resources/admin/js/jquery.multi-select.js" />"></script>
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
					
					<jsp:include page="include/cpr-standard-menu1.jsp" />
					<jsp:include page="include/cpr-standard-menu2.jsp" />
					<jsp:include page="include/cpr-standard-menu3.jsp" />					

					<strong class="active-tab-head"><spring:message code="cpr.standard.tab.4" /></strong>
					
					<!-- ACTIVE TAB -->
					<div class="active-tab">
					<script>
					$(document).ready(function(){
						
						   
					});
					</script>
					<c:url value="/admin/cpr/standard/edit/${standardId}/notifiedbodies" var="formUrl"/>
					
					<!--  STANDARD CHANGES WRAPPER  -->
					<div class="tab-wrapp">
						<p class="form-head"><spring:message code="standard.noao" /></p>
						<c:if test="${empty model.standard.notifiedBodies}">
							<p class="msg alert">
								<spring:message code="cpr.standard.changes.empty" />
							</p>
						</c:if>
						<c:if test="${not empty model.standard.notifiedBodies}">
							<table class="data">
									<tr>
										<th>Kód</th>
										<th>Název</th>
										<th>Od data</th>
										<th>Zrušit</th>
									</tr>
								<c:forEach items="${model.standard.notifiedBodies}" var="i">
									<tr>
										<td>
											<a:adminurl href="/cpr/notifiedbodies/edit/${i.notifiedBody.id}" >
											${i.notifiedBody.noCode} 
												<c:if test="${not empty i.notifiedBody.aoCode }">
											(${i.notifiedBody.aoCode})
											</c:if>
											</a:adminurl>
										</td>
										<td class="b">
											<a:adminurl href="/cpr/notifiedbodies/edit/${i.notifiedBody.id}" >
												${i.notifiedBody.name}
											</a:adminurl>
										</td>
										<td class="last-edit">
											<c:if test="${not empty i.assignmentDate}">
												<joda:format value="${i.assignmentDate}" pattern="dd.MM.yyyy"/>
											</c:if>
											<c:if test="${empty i.assignmentDate}">
												-
											</c:if>
										</td>
										<td class="delete big">
											<a class="confirmUnassignment"  href="?id=${i.id}">
								 				<spring:message code="cpr.group.unassigment" />
								 			</a>
										</td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						
						<div class="inline-form ">
							<form:form commandName="standardNotifiedBody" cssClass="nb-form valid">
								<span class="rel wrapp">
								<label for="notifiedBody">Zvolte z možností:</label>
								<form:select path="notifiedBody" cssClass="chosen required">
									<option value="" ><spring:message code="form.select" /></option>
									<c:forEach items="${model.notifiedBodies}" var="i">
				                              <option value="${i.id}">  
				                              <c:if test="${empty i.aoCode}">${i.noCode}</c:if>
				                              <c:if test="${not empty i.aoCode}">${i.aoCode}</c:if>
				                              - ${fn:substring(i.name, 0, 90)}...</option>
				                      </c:forEach>
								</form:select>
								</span>
								<span class="rel wrapp">
									<label for="notifiedBody">Od data:</label>
									<form:input path="assignmentDate" maxlength="25" cssClass="date-month"  />
								</span>
								<input type="submit" class="lang mandate-add-btn" value='<spring:message code="cpr.standard.group" />' />
								<div class="clear"></div>
							</form:form>
						</div>
					</div>
					
					
						
					</div> <!-- END ACTIVE TAB -->
					
					<jsp:include page="include/cpr-standard-menu5.jsp" />
					<jsp:include page="include/cpr-standard-menu6.jsp" />
					
				</div>	<!-- END TABs -->
		
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>