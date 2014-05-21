<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ page trimDirectiveWhitespaces="true" %>
<sec:authorize access="@portalSecurity.hasOnlinePublicatoin('${i.onlinePublication.code}')" var="hasPublication" />									
<c:if test="${not hasPublication}">
	<li class="pj-publication pj-radius">
		<a href="<a:url href="/${i.publicationUrl}"  linkOnly="true" />" >
			<span class="pj-name">
				<a:localizedValue object="${i}" fieldName="name" />
			</span>
			<span class="pj-price"><webpage:price price="${i.priceCzk}" /></span>
			<span class="pj-ico"></span>
		</a>
	</li>
</c:if>
<c:if test="${hasPublication}">
	<li class="pj-publication pj-radius pj-brought">
		<a href="${i.onlinePublication.url}" target="_blank">
			<span class="pj-name">
				<a:localizedValue object="${i}" fieldName="name" />
			</span>
			<span class="pj-ico"></span>
		</a>
	</li>
</c:if>