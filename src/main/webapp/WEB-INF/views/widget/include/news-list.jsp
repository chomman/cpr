<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<div class="pj-widget-news">
	<c:forEach items="${webpageModel.news}" var="i">
		<div class="pj-item">
			<span class="pj-radius pj-date"><joda:format value="${i.published}" pattern="dd.MM.yyyy" /></span>
			<strong>
			<c:if test="${webpageModel.showInWindow}">
				<a:url href="/widget/aktualita/${i.id}${webpageModel.params}">
					<webpage:filedVal webpage="${i}" fieldName="title" />
				</a:url>
			</c:if>
			<c:if test="${not webpageModel.showInWindow}">
				<webpage:a webpage="${i}" target="_blank" />
			</c:if>
			</strong>
			<p>
			<c:if test="${not empty i.descriptionInLang}">
				<c:if test="${fn:length(i.descriptionInLang) gt webpageModel.descrLength}">
					${fn:substring(i.descriptionInLang, 0, webpageModel.descrLength)}...
				</c:if>
				<c:if test="${fn:length(i.descriptionInLang) lt webpageModel.descrLength}">
					${i.descriptionInLang}
				</c:if>
			</c:if>
			<c:if test="${ empty i.descriptionInLang}">
				${nlf:crop(i.contentInLang, webpageModel.descrLength)} 												
			</c:if>
			</p>
		</div>			
	</c:forEach>
</div>