<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="admin.portal.orders" /></title>
	<script type="text/javascript">  
	$(document).ready(function() {  
		
	});
	</script>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/portal-nav.jsp" />
		</div>	
		<div id="right">
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				 <span><spring:message code="admin.portal.orders" /></span>
			</div>
			<h1><spring:message code="admin.portal.orders" /></h1>
	
			<div id="content">
				
				
								
			<form class="filter" method="get">
				<div >
					<span class="filter-label long"><spring:message code="form.orderby" />:</span>
					<select name="orderBy" class="chosenSmall">
						<c:forEach items="${model.orders}" var="i">
							<option value="${i.id}" <c:if test="${i.id == model.params.orderBy}" >selected="selected"</c:if> >${i.name}</option>
						</c:forEach>
					</select>
					&nbsp;<span class="filter-label">Stav objednávky: </span>
					<select name="orderStatus" class="chosenSmall">
							<option value="">Nezáleží</option>
						<c:forEach items="${model.orderStatuses}" var="i">
							<option value="${i}" <c:if test="${i == model.params.orderStatus}" >selected="selected"</c:if> >
								<spring:message code="${i.code}" />
							</option>
						</c:forEach>
					</select>
										
				</div>
				<div>
					<span class="filter-label long">Datum přijetí:</span>
					<input type="text" class="date"  name="createdFrom" value="<joda:format value="${model.params.createdFrom}" pattern="dd.MM.yyyy"/>" />
					<span class="filter-label">do:</span>
					<input type="text" class="date" name="createdTo"  value="<joda:format value="${model.params.createdTo}" pattern="dd.MM.yyyy"/>" />
					&nbsp;<span class="filter-label"><spring:message code="portalOrderSource" /></span>
					<select name="orderSource" class="chosenSmall">
						<option value="">Nezáleží</option>
						<c:forEach items="${model.sources}" var="i">
							<option value="${i}" <c:if test="${i == model.params.orderSource}" >selected="selected"</c:if> >
								<spring:message code="${i.code}" />
							</option>
						</c:forEach>
					</select>
				</div>
				<div>
					<span class="filter-label long">Název zákazníka:</span>
					<input type="text" class="query " name="query"   value="${model.params.query}" />
					<input type="submit" value="Filtrovat" class="btn filter-btn-standard" />
				</div>
			</form>				
								
				<c:if test="${not empty successDelete}">
					<p class="msg ok"><spring:message code="success.delete" /></p>
				</c:if>
				
				<jsp:include page="order-list-table.jsp" />			
				
			</div>	
		</div>
		<div class="clear"></div>	
	</div>

</body>
</html>