<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<c:forEach items="${products}" var="i" varStatus="s">
	<tr class="${s.index % 2 == 0 ? 'even' : 'odd'} ${i.id == webpageModel.pid ? ' selected' : ''}" data-id="${i.id}" data-eurprice="${i.priceEur}" data-czkprice="${i.priceCzk}">
		<td class="pro-name">
			<c:if test="${empty webpageModel.portalProductDetailUrl}">
				<a href="<c:url value="/${i.publicationUrl}" />" target="_blank" class="product-url">
			</c:if>
			<c:if test="${not empty webpageModel.portalProductDetailUrl}">
				<a href="<c:url value="/${webpageModel.portalProductDetailUrl}${i.id}" />${webpageModel.params}" class="product-url">
			</c:if>
				<a:localizedValue object="${i}" fieldName="name" />
			</a>
		</td>
		<td class="price-wrapp"></td>
		<td class="btn-wrapp">
			<a href="#" class="pub-btn add pj-radius6" title="<spring:message code="portaluser.add.title" />"> 
				<spring:message code="portaluser.add" />
			</a>
			<a href="#" class="pub-btn remove pj-radius6" title="<spring:message code="portaluser.remove.title" />"> 
				<spring:message code="portaluser.remove" />
			</a>
		</td>
	</tr>
</c:forEach>