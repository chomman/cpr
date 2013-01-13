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
					
					
					<strong class="active-tab-head"><spring:message code="cpr.standard.tab.5" /></strong>
					
					<!-- ACTIVE TAB -->
					<div class="active-tab">
					<script>
					$(document).ready(function(){
						
						   $("#mandates").multiSelect({
							   selectableHeader: "<div class='custom-header'>Vyberte z mandátů</div><input type='text' id='search' autocomplete='off' placeholder='Vyhledat ...'>",
							   selectionHeader: "<div class='custom-header'>Vybrané mandáty</div>"
						   });
						   
						   $("#assessmentSystems").multiSelect({
							   selectableHeader: "<div class='custom-header'>Vyberte ze systémů PS</div>",
							   selectionHeader: "<div class='custom-header'>Vybrané systémy</div>"
						   });
						   
						   $('#search').quicksearch($('.ms-elem-selectable', '#ms-mandates' )).on('keydown', function(e){
						   		console.log('searching ' + e);   
							    if (e.keyCode == 40){
								   $(this).trigger('focusout');
								   $('#mandates').focus();
								   return false;
								}
							}); 
						   
						   
						});
					</script>
					<c:url value="/admin/cpr/standard/edit/${standardId}/other" var="formUrl"/>

					<form:form commandName="standard" method="post" action="${formUrl}" cssClass="form-multiple"  >
						
						<p class="msg info">
							<spring:message code="form.multiselect.info" />
						</p>
						
						<c:if test="${not empty successCreate}">
							<p class="msg ok"><spring:message code="success.create" /></p>
						</c:if>
						
						<!-- Mandaty -->
						<div class="hbox">
							<h2><spring:message code="cpr.mandates.title" /></h2>
						</div>
						
						 <form:select path="mandates" cssClass="mw500 multiple" multiple="true">
							 <c:forEach items="${model.mandates}" var="m" >
					 				<option value="${m.id}" 
						 				<c:forEach items="${standard.mandates}" var="sm">
						 					<c:if test="${sm.id ==  m.id}"> selected="selected" </c:if>
						 				</c:forEach> 
					 				>
					 				${m.mandateName}
					 				</option>			 			
							</c:forEach>
						 </form:select>
						 
						 
						 <!-- Systemy -->
						 <div class="hbox">
							<h2><spring:message code="cpr.as.title" /></h2>
						 </div>
						 
						 
						 <form:select path="assessmentSystems" cssClass="mw500 multiple" multiple="true">
							 <c:forEach items="${model.assessmentSystem}" var="as" >
					 				<option value="${as.id}" 
						 				<c:forEach items="${standard.assessmentSystems}" var="sas">
						 					<c:if test="${sas.id ==  as.id}"> selected="selected" </c:if>
						 				</c:forEach> 
					 				>
					 				${as.name}
					 				</option>			 			
							</c:forEach>
						 </form:select>
						 
						 
						 
						 <form:hidden path="id"  />
						 <p class="margin-top-30">
						 <input type="submit" class="button" value="<spring:message code="form.save" />" />
						 </p>
					</form:form>
					
					
						
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