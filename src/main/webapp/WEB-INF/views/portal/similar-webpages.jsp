<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:if test="${not empty webpageModel.similarWebpages}">
	<sec:authorize access="@portalSecurity.hasValidRegistration()" var="hasValidRegistration"  />
	<div class="pj-news-similar">
		<h5><spring:message code="webpage.similar" />:</h5>
		<div class="pj-news-similar-wrapp">
			<c:forEach items="${webpageModel.similarWebpages}" var="i">
				<c:set var="isDenied"  value="${not hasValidRegistration and nlf:isOnlyForRegistrated(i)}" />
				<div class="pj-item">
					<strong>
						<a class="pj-link ${isDenied ? 'pj-locked' : ''}" href="<webpage:link webpage="${i}" />">
							<webpage:filedVal webpage="${i}" fieldName="title" />
						</a>
					</strong>
					<p>
					<c:if test="${not empty i.descriptionInLang}">
						<c:if test="${fn:length(i.descriptionInLang) gt 150}">
							${fn:substring(i.descriptionInLang, 0, 150)}...
						</c:if>
						<c:if test="${fn:length(i.descriptionInLang) lt 150}">
							${i.descriptionInLang}
						</c:if>
					</c:if>
					<c:if test="${ empty i.descriptionInLang}">
						${nlf:crop(i.contentInLang, 150)} 												
					</c:if>
					</p>
				</div>			
			</c:forEach>
		</div>
	</div>
</c:if>