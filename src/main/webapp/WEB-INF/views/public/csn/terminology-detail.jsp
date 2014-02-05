<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.terminology.csn.csnId} - ${model.terminology.title}</title>
		<script src="<c:url value="/resources/public/js/terminology.autocomplete.js" />"></script>
	</head>
	<script>
	$(function() {
		var $nav = $('#terminologyNav'),
			$content = $('#terminologyContent'),
			collClass = "collapse",
			sessionKey = "status";
		
		if(sessionStorageSupported()){
			if(typeof sessionStorage.getItem(sessionKey) !== 'undefined' && sessionStorage.getItem(sessionKey) === '1'){
				hideNav($(".tbtn"));
				console.log('hidinging nav..');
			}
		}
				
		$(document).on("click", ".tbtn", function(){
			var o = $(this);
			if(o.hasClass(collClass)){
				console.log('showing nav..');
				showNav(o);
			}else{
				hideNav(o);
				console.log('hidinging nav..');
			}
			return false;
		});
		
		function hideNav(btn){
			$nav.addClass('hidden');
			$content.addClass('fullWidth');
			if(sessionStorageSupported()){
				 sessionStorage.setItem(sessionKey, 1);
			}
			btn.html('&rsaquo;').addClass(collClass);
		}
		function showNav(btn){
			$nav.removeClass('hidden');
			$content.removeClass('fullWidth');
			if(sessionStorageSupported()){
				 sessionStorage.setItem(sessionKey, 0);
			}
			btn.html('&lsaquo;').removeClass(collClass);
		}
		
		
		function sessionStorageSupported(){
			return !(typeof(sessionStorage) == 'undefined');
		}
		
		$(document).on("click", ".showMore", function(){
			$(this).hide().next().removeClass("hidden");
			return false;
		});
	 });	
	</script>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a:url href="/"><spring:message code="homepage" /></a:url> &raquo; 
			<a:url href="${model.webpage.code}">
				<a:localizedValue object="${model.webpage}" fieldName="name" />
			</a:url> &raquo;
			<span>${model.terminology.csn.csnId} - ${model.terminology.title}</span>
	</div> 

		<div id="main-content" class="fullheight">
			<h1>${model.terminology.csn.csnId} - ${model.terminology.csn.czechName}</h1>
			
			<table class="meta">
			<c:if test="${not empty model.terminology.csn.catalogId}">
				<tr>
					<td class="label"><spring:message code="csn.csnonline" />:</td>
					<td>
			 			<a class="file pdf tt" title='<spring:message code="csn.csnonline.info" />' target="_blank" href="${fn:replace(commonPublic.settings.csnOnlineUrl, '{0}', model.terminology.csn.catalogId)}">
							${model.terminology.csn.csnId}
						</a>
						<a class="showMore" href="#"><spring:message code="csn.csnonline.info.show" /></a>
						<span class="info hidden">
							<spring:message code="csnonline.info" />
						</span>	
					</td>
				</tr>
				</c:if>
				<c:if test="${not empty model.terminology.csn.published}">
					<tr>
						<td class="label"><spring:message code="csn.table.published" />:</td>
						<td>${model.terminology.csn.published}</td>
					</tr>
				</c:if>
				<c:if test="${not empty model.terminology.csn.ics}">
					<tr>
						<td class="label"><spring:message code="csn.table.ics" />:</td>
						<td>${model.terminology.csn.ics}</td>
					</tr>
				</c:if>
				<c:if test="${not empty model.terminology.csn.classificationSymbol}">
					<tr>
						<td class="label"><spring:message code="csn.table.classification" />:</td>
						<td>${model.terminology.csn.classificationSymbol}</td>
					</tr>
				</c:if>
				<c:if test="${not empty model.terminology.csn.csnCategory}">
					<tr>
						<td class="label"><spring:message code="csn.table.category" />:</td>
						<td>${model.terminology.csn.csnCategory.name}</td>
					</tr>
				</c:if>
				
				
			</table>
		
			
			
			<div class="thead">
				
			</div>
			<div id="terminologyNav" class="left">
				<ul>
				<c:forEach items="${model.terminologies}" var="i">
					<li <c:if test="${model.terminology.id == i.id}"> class="active"</c:if>  >
						<a href="<c:url value="${model.webpage.code}/${i.id}" />">${i.section} ${i.title}</a>
					</li>
				</c:forEach>
				</ul>
				<span class="head-terminology"><spring:message code="csn.terminology.nav.head" /></span>
			</div>	
			<div class="border">
				<div class="tbtn">&lsaquo;</div>
			</div>
			<div id="terminologyContent" class="right">
				
				<div class="terminology">
					<h2>${model.terminology.section} ${model.terminology.title}</h2>
					${model.terminology.content}
				</div>
				
				<c:if test="${not empty model.terminology2}">
					<div class="terminology second">
						<h2>${model.terminology2.section} ${model.terminology2.title}</h2>
						${model.terminology2.content}
					</div>
				</c:if>
			</div>
			<div class="clear"></div>
			 
		</div>
	</body>
</html>