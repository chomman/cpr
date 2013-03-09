<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.article.title}</title>
		<meta name="description" content="${model.article.title}" />
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<a title="${model.webpage.name}" href="<c:url value="${model.webpage.code}" />">${model.webpage.title}</a> &raquo; 
			<span>${model.article.title}</span>
	</div> 
	<div id="main-content">
		<article class="article-detail">
			<h1>${model.article.title}</h1>
			<span class="time"><spring:message code="published" />: 
 				<joda:format value="${article.created}" pattern="dd.MM.yyyy"/>
			</span>
			${model.article.articleContent}
	 	</article>
	 	<div id="meta">
	 		<!-- AddThis Button BEGIN -->
				<div class="addthis_toolbox addthis_default_style addthis_32x32_style">
				<a class="addthis_button_preferred_1"></a>
				<a class="addthis_button_preferred_2"></a>
				<a class="addthis_button_preferred_3"></a>
				<a class="addthis_button_preferred_4"></a>
				<a class="addthis_button_compact"></a>
				<a class="addthis_counter addthis_bubble_style"></a>
				</div>
				<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-4f7805435b56a706"></script>
				<!-- AddThis Button END -->
	 	</div>
	</div>
	</body>
</html>