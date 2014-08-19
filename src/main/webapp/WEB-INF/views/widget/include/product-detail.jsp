<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<article class="pj-widget-artice">
	<h3 class="pj-widget"><a:localizedValue object="${webpageModel.portalProduct}" fieldName="name" /></h3>
	<a:localizedValue object="${webpageModel.portalProduct}" fieldName="description" />						
	<c:if test="${webpageModel.portalProduct.portalProductType.id == 2}">
		<div class="pub-nav">
			<a href="${webpageModel.portalProduct.onlinePublication.previewUrl}" target="_blank" class="online-pub-preview pj-radius">
				<spring:message  code="onlniePublication.preview" />
			</a>
			
			<a href="<a:url href="/widget/registrace" linkOnly="true" />${webpageModel.params}&amp;pid=${webpageModel.portalProduct.id}" class="online-pub-extend pj-radius">
				<spring:message  code="onlinePublication.orderProduct" />
			</a>
		</div>
	</c:if>
</article> 