<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="menu.cpr.norm" /></title>
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
			 <span><spring:message code="menu.cpr.norm" /></span>
		</div>
		<h1><spring:message code="cpr.groups.title" /></h1>

		<div id="content">
			
<script type="text/javascript">  
       
$(document).ready(function() {    
      $("input.query").autocomplete({
			 source: function(request, response){  
			 	 $.getJSON( $("#base").text() +"admin/cpr/standard/autocomplete", request, function(data) {  
                 	 response( $.map( data, function( item ) {
                 			 var shortText = jQuery.trim(item[1] + " " +  item[2]).substring(0, 65).split(" ").slice(0, -1).join(" ") + " ...";
							return {label: shortText, value: item[1]};
						}));
            	});  
			 },
			minLength: 2,
			select: function( event, ui ) {
				ui.item.value;
			}
	});
});
</script>
			
			<ul class="sub-nav">
				<li><a class="active" href="<c:url value="/admin/cpr/standards"  />"><spring:message code="cpr.standard.view" /></a></li>
				<li><a href="<c:url value="/admin/cpr/standard/add"  />"><spring:message code="cpr.standard.add" /></a></li>
			</ul>
			
			<form class="filter" action="<c:url value="/admin/cpr/standards" />" method="get">
				<div>
					<span class="long">Seřadit podle:</span>
					<select name="orderBy">
						<c:forEach items="${model.orders}" var="i">
							<option value="${i.id}">${i.name}</option>
						</c:forEach>
					</select>
					<span>Platnost od:</span>
					<input type="text" class="date"  name="startValidity" />
					<span>do:</span>
					<input type="text" class="date" name="stopValidity" />
					
				</div>
				<div>
					<span class="long">Název:</span>
					<input type="text" class="query" name="query" />
					
					<input type="submit" value="Filtrovat" class="btn" />
				</div>
			</form>
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
						
			<c:if test="${not empty model.standards}">
				
				<!-- STRANKOVANIE -->
				<c:if test="${not empty model.paginationLinks}" >
					<div class="pagination">
					<c:forEach items="${model.paginationLinks}" var="i">
						<c:if test="${not empty i.url}">
							<a title="Stánka č. ${i.anchor}"  class="tt"  href="<c:url value="${i.url}"  />">${i.anchor}</a>
						</c:if>
						<c:if test="${empty i.url}">
							<span>${i.anchor}</span>
						</c:if>
					</c:forEach>
					</div>
				</c:if>
				
				<table class="data">
					<thead>
						<tr>
							<tH><spring:message code="cpr.standard.id" /></th>
							<th><spring:message code="cpr.standard.name" /></th>
							<th><spring:message code="published" /></th>
							<th><spring:message code="form.lastEdit" /></th>
							<th><spring:message code="form.edit" /></th>
							<th><spring:message code="form.delete" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.standards}" var="i">
						 	<tr>
						 		<td>${i.standardId}</td>
						 		<td>${i.standardName}</td>
						 		<td class="w100">
						 			<c:if test="${i.enabled}">
						 				<span class="published yes tt" title="<spring:message code="published.yes.title" />" >
						 					<spring:message code="yes" />
						 				</span>
						 			</c:if>
						 			<c:if test="${not i.enabled}">
						 				<span class="published no tt" title="<spring:message code="published.no.title" />" >
						 					<spring:message code="no" />
						 				</span>
						 			</c:if>
						 		</td>
						 		<td class="last-edit">
						 			<c:if test="${empty i.changedBy}">
						 				<joda:format value="${i.created}" pattern="dd.MM.yyyy / hh:mm"/>
						 			</c:if>
						 			<c:if test="${not empty i.changedBy}">
						 				<joda:format value="${i.changed}" pattern="dd.MM.yyyy / hh:mm"/>
						 			</c:if>
						 		</td>
						 		<td class="edit">
						 			<a class="tt" title="Zobrazit a upraviť položku?" href="<c:url value="/admin/cpr/standard/edit/${i.id}"  />">
						 				<spring:message code="form.edit" />
						 			</a>
						 		</td>
						 		<td class="delete">
						 			<a class="confirm"  href="<c:url value="/admin/cpr/standard/delete/${i.id}"  />">
						 				<spring:message code="form.delete" />
						 			</a>
						 		</td>
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<c:if test="${empty model.standards}">
				<p class="msg alert">
					<spring:message code="alert.empty" />
				</p>
			</c:if>

		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>