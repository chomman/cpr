<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>


<h1>
	<a:localizedValue object="${model.webpage}" fieldName="title" />
</h1>

<c:if test="<a:localizedValue object="${model.webpage}" fieldName="topText" />">
	<article class="full-width">
		<a:localizedValue object="${model.webpage}" fieldName="topText" />
	</article>
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


<article class="full-width">
	<a:localizedValue object="${model.webpage}" fieldName="bottomText" />
</article>

