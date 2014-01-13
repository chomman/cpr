<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<c:if test="${not empty model.webpage.title}" >
	<h1>${model.webpage.title}</h1>
</c:if>

<c:if test="${not empty model.webpage.topText}" >
	<h1>${model.webpage.topText}</h1>
</c:if>

<form id="search-form" method="get">
		<label for="query">
			<spring:message code="standard.public.search" />
		</label>	
		<input type="text" class="query" name="query" value="${model.params.query}"  />
		<input type="submit" value="Vyhledat" class="btn" />
</form>


<c:if test="${empty model.standards}">
	<p class="msg alert">
		<spring:message code="standard.public.search.empty"  arguments="${model.params.query}" />
	</p>
</c:if>

<c:if test="${not empty model.webpage.bottomText}" >
	<h1>${model.webpage.bottomText}</h1>
</c:if>

