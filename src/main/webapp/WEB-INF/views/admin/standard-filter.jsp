<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<form class="filter" method="get">
	<script>

	</script>
	<div class="filter-advanced">
		<span class="filter-label long"><spring:message code="form.orderby" />:</span>
		<select name="orderBy" class="chosenSmall">
			<c:forEach items="${model.orders}" var="i">
				<option value="${i.id}" <c:if test="${i.id == model.params.orderBy}" >selected="selected"</c:if> >${i.name}</option>
			</c:forEach>
		</select>
		<span class="filter-label"><spring:message code="cpr.standard.added" /></span>
		<input type="text" class="date"  name="createdFrom" value="<joda:format value="${model.params.createdFrom}" pattern="dd.MM.yyyy"/>" />
		<span class="filter-label">do:</span>
		<input type="text" class="date" name="createdTo"  value="<joda:format value="${model.params.createdTo}" pattern="dd.MM.yyyy"/>" />
		
	</div>
	
	<div class="filter-advanced">
		<span class="filter-label long"><spring:message code="filter.standard.standardCategory" />:</span>
		<select name="scId" class="async" data-items="standardCategories">
			<option value=""><spring:message code="cpr.standard.filter.default" /></option>
		</select>
	</div>
	
	<div class="filter-advanced">
		
		<span class="filter-label long"><spring:message code="cpr.standard.filter.status" />: &nbsp;</span>
		<select name="s" class="chosenSmall">
			<option value=""><spring:message code="cpr.standard.filter.default" /></option>
			<c:forEach items="${model.standardStatuses}" var="i">
                     <option value="${i}" <c:if test="${i.code == model.params.s}">selected="selected"</c:if> >
                     		<spring:message code="${i.name}" />
                     </option>
              </c:forEach>
		</select>
	
		<span class="filter-label "><spring:message code="filter.standard.regulation" />:</span>
		<select name="rId" class="async async chosenCustom"  style="width:300px;">
			<option value=""><spring:message code="cpr.standard.filter.default" /></option>
		</select>
	</div>
	<div class="cpr-filter hidden">
		<span class="head">Rozšířený filtr pro kategorii stavebních výrobků (CPR)</span>
		<div class="filter-advanced">
			<span class="filter-label long"><spring:message code="form.groups" />:</span>
			<select name="sgId" class="groups async" data-items="standardGroups">
				<option value=""><spring:message code="cpr.standard.filter.default" /></option>
			</select>
		</div>
		
		<div class="filter-advanced">
			<span class="filter-label long"><spring:message code="cpr.commisiondecision.name" />:</span>
			<select name="cdId" class="async chosenSmall" data-items="commissionDecisions">
				<option value=""><spring:message code="cpr.standard.filter.default" /></option>
			</select>
			<span class="filter-label"> &nbsp; &nbsp; &nbsp; &nbsp; <spring:message code="cpr.standard.filter.mandate" />:</span>
			<select name="mId" class="async chosenSmall" data-items="mandates">
				<option value=""><spring:message code="cpr.standard.filter.default" /></option>
			</select>
		</div>
		
		<div class="filter-advanced">
			<span class="filter-label long"><spring:message code="cpr.standard.filter.as" />:</span>
			<select name="asId" class="async chosenSmall " data-items="assessmentSystems">
				<option value=""><spring:message code="cpr.standard.filter.default" /></option>
			</select>
		</div>
		
		<div class="filter-advanced">
			<span class="filter-label long"><spring:message code="cpr.nb.filter" />:</span>
			<input type="text" class="query-aono mw500" name="notifiedBody" />
		</div>
	</div>
	<div>
		<span class="filter-label long"><spring:message code="form.name" />/Označení</span>
		<input type="text" class="query " name="query"   value="${model.params.query}" />
		
		<input type="submit" value="Filtrovat" class="btn filter-btn-standard" />
		<a href="#" class="filter-advanced-btn">
			<spring:message code="cpr.standard.filter.advanced" />
		</a>
	</div>
</form>