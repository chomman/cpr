<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.webpage.title}</title>
		<script src="<c:url value="/resources/public/js/terminology.autocomplete.js" />"></script>
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<span>${model.webpage.name}</span>
	</div> 

		<div id="main-content">
			<article>
				${model.webpage.topText}
			</article>
			
			
			<!-- SEARCH FORM -->
			 <div id="sarchTerminology">
			 	<form class="extendedSearch">
			 		<div>
			 			<label for="csnId">
			 				<spring:message code="csn.terminology.csnId" />:
			 			</label>
			 			<input type="text" maxlength="40" name="csnId" class="field csnId" value="${model.params.csnId}" />
			 		</div>
			 		<div>
			 			<label for="csnCategory">
			 				<spring:message code="csn.terminology.classSymbol" />:
			 			</label>
			 			<input type="text" maxlength="40" name="csnCategory"  class="field csnCategory" value="${model.params.csnCategory}" />
			 		</div>
			 		<div>
			 			<label for="name" ><spring:message code="csn.terminology.name" />:</label>
			 			<input type="text" maxlength="40" name="name"  class="field w500" value="${model.params.name}"  />
			 		</div>
			 		<div>
				 		<label for="query">
							<spring:message code="csn.terminology.search" />:
						</label>	
						<input type="text" class="query field w500" name="query" value="${model.params.query}"  />
			 			<input type="submit" value="<spring:message code="csn.terminology.search" />" class="btn" />
			 			<div class="clear"></div>
			 		</div>
						
			 	</form>
			 </div>
			 
			
			 
			 <!-- ALERT -->
			 <c:if test="${empty model.page and not empty model.params.query}">
					<p class="msg alert">
						<spring:message code="alert.empty" />
					</p>
			</c:if>
			
			
			
			<!-- RESULT TABLE -->		  
			<c:if test="${not empty model.page}">
				<table class="group-detail">
					<thead>
						<tr>
							<th class="section" ><spring:message code="csn.table.secion" /></th>
							<th><spring:message code="csn.table.terminology" /></th>
							<th><spring:message code="csn.form.name" /></th>
							<th><spring:message code="csn.table.name" /></th>
						</tr>
					</thead>
					<tbody>
						 <c:forEach items="${model.page}" var="i">
						 	<tr>
						 		<td class="c section">${i.section}</td>
						 		<td class="title">
						 		   <a class="b tt" href="<c:url value="${model.webpage.code}/${i.id}" />" 
						 		   	title='<spring:message code="csn.terminology.show" arguments="${i.title}" />' >
						 				${i.title}
						 			</a>	
						 		</td>
						 		<td class="norm c">
							 		<c:if test="${not empty i.csn.csnOnlineId}">
							 			<a class="file pdf" target="_blank" href="${fn:replace(commonPublic.settings.csnOnlineUrl, '{0}', i.csn.csnOnlineId)}">
											${i.csn.csnId}
										</a>	
							 		</c:if>
							 		<c:if test="${empty i.csn.csnOnlineId}">
											${i.csn.csnId}
							 		</c:if>
						 		</td>
						 		<td class="c name">${i.csn.czechName}</td>
						 	</tr>
						 </c:forEach>
					</tbody>
				</table>
			</c:if>
			
				
				
			 <article>
					${model.webpage.bottomText}
			 </article>
				
			 
		</div>
	</body>
</html>