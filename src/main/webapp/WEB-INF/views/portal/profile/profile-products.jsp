<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<table class="data">
	<thead>
		<tr>
			<tH><spring:message code="portaluser.profile.order.product" /></th>
			<th><spring:message code="portaluser.profile.registrationValidity" /></th>
			<th><spring:message code="portaluser.leftDays" /></th>
			<th><spring:message code="portaluser.extendValidity" /></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td  class="l">
				<spring:message code="portaluser.productRegistration" />
			</td>
			<td>
				<joda:format value="${webpageModel.user.registrationValidity}" pattern="dd.MM.yyyy"/>
			</td>
			<td>
				${nlf:daysLeft(webpageModel.user.registrationValidity)}
				<spring:message code="portaluser.day" />
			</td>
			<td>
				<a href="<a:url href="/${webpageModel.profileUrl}/new-order" linkOnly="true"  />">
					<spring:message code="portaluser.extendValidity" />
				</a>
		</tr>
	
		<c:forEach items="${webpageModel.user.onlinePublications}" var="i" varStatus="s" >
		 	<tr class="${s.index % 2 == 0 ? 'odd' : 'even'}">
			<td class="l">
				<a:localizedValue object="${i.portalProduct}" fieldName="name" />
			</td>
			<td>
				<joda:format value="${i.validity}" pattern="dd.MM.yyyy"/>
			</td>
			<td>
				${nlf:daysLeft(i.validity)}
				<spring:message code="portaluser.day" />
			</td>
			<td>
				<a href="<a:url href="/${webpageModel.profileUrl}/new-order" linkOnly="true"  />">
					<spring:message code="portaluser.extendValidity" />
				</a>
		</tr>
		 	
		 </c:forEach>
	</tbody>
</table>


