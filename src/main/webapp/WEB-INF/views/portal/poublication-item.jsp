<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ page trimDirectiveWhitespaces="true" %>
<sec:authorize access="@portalSecurity.hasOnlinePublicatoin('${i.onlinePublication.code}')" var="hasPublication" />									
<c:set var="validity" value="${nlf:getPortalProductValidityInDays(i.id)}" />
<c:if test="${not hasPublication}">
	<li class="pj-publication pj-radius">
		<a href="<a:url href="/${i.publicationUrl}"  linkOnly="true" />" >
			<span class="pj-name">
				<a:localizedValue object="${i}" fieldName="name" />
			</span>
			<span class="pj-price"><webpage:price price="${i.priceCzk}" /></span>
			<span class="pj-ico"></span>
		</a>
		
		<c:if test="${not empty validity}">
			<div class="pj-valid-alert">
					<p class="pj-alert pj-radius"> 
						<spring:message code="onlinePublication.invalid" />
						<a href="<a:url href="${webpageModel.profileUrl}" linkOnly="true"  />/new-order?pid=${i.id}">
							<spring:message code="portaluser.extendValidity" />
						</a>
					</p> 
			</div>
		</c:if>
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
		<c:if test="${not empty validity and validity < 21}">
			<div class="pj-valid-alert">
				<c:if test="${validity > 0 }">
					<p class="pj-alert pj-radius"> 
						<spring:message code="portaluser.leftDays" />: <strong>${validity}</strong> <spring:message code="portaluser.day" />
						<a href="<a:url href="${webpageModel.profileUrl}" linkOnly="true"  />/new-order?pid=${i.id}">
							<spring:message code="portaluser.extendValidity" />
						</a>
					</p> 
				</c:if>
			</div>
		</c:if>
	</li>
</c:if>