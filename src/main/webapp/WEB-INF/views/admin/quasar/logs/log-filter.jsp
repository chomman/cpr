<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<form class="filter user" method="get">
	<div>
		<span class="long filter-label"><spring:message code="logStatus" />:</span>
		<select name="status" class="chosenMini">
			<c:forEach items="${model.statuses}" var="i">
				<option value="${i.id}" <c:if test="${i.id == model.params.status}" >selected="selected"</c:if> >
					<spring:message code="${i.code}" />
				</option>
			</c:forEach>
		</select>
		<span class="filter-label">Created from:</span>
		<input type="text" class="date"  name="dateFrom" value="<joda:format value="${model.params.dateFrom}" pattern="dd.MM.yyyy"/>" />
		<span class="filter-label">to:</span>
		<input type="text" class="date" name="dateTo"  value="<joda:format value="${model.params.dateTo}" pattern="dd.MM.yyyy"/>" />
	</div>
	<c:if test="${model.isQuasarAdmin}">
		<div>
			<span class="long filter-label"><spring:message code="auditor.partner" />:</span>
			<select name="partner" class="chosenSmall">
				<option value=""><spring:message code="notmatter" /></option>
				<c:forEach items="${model.partners}" var="i">
					<option value="${i.id}" ${model.params.partner eq i.id ? 'selected="selected"' : ''}>${i.name}</option>
				</c:forEach>
			</select>
			<input type="submit" value="Filter" class="btn" />
		</div>
	</c:if>
</form>		