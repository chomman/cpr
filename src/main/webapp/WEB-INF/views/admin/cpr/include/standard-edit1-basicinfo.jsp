<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
	<c:url value="/admin/cpr/standard/edit/${standardId}" var="formUrl"/>
	<jsp:include page="cpr-standard-form1.jsp" />	
					
					
	<!--  STANDARD GROUPS WRAPPER  -->
	<div  class="tab-wrapp">
		<p class="form-head"><spring:message code="cpr.standard.attachedGroups" /></p>
		
		
		<!-- assigned STANDARD GROUPS -->
		<c:if test="${not empty standard.standardGroups}">
			<table class="data">
				<c:forEach items="${standard.standardGroups}" var="i">
					<tr>
						<td class="w40 c b"><strong>${i.code}</strong></td>
						<td>${i.czechName}</td>
						<td class="delete big">
							<a class="confirmUnassignment"  href="${formUrl}/standard-group/delete/${i.id}">
				 				<spring:message code="cpr.group.unassigment" />
				 			</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${empty standard.standardGroups}">
			<p class="msg alert">
				<spring:message code="cpr.standard.nogroup" />
			</p>
		</c:if>
		
		<!--  STANDARD GROUP ADDING FORM  -->	
		<form:form cssClass="inline-form valid" action="${formUrl}/standard-group/add" commandName="standardForm"  method="post"  >
		 	<div class="inline-field">
		 		<form:select path="standardGroup" cssClass="chosen required">
					<option value="" ><spring:message code="form.select" /></option>
					<c:forEach items="${model.standardGroups}" var="group">
                              <option value="${group.id}"> ${group.code} - ${fn:substring(group.czechName, 0, 90)}...</option>
                      </c:forEach>
				</form:select>
		 	</div>
		 	<input type="submit" class="lang mandate-add-btn" value="<spring:message code="cpr.standard.group" />" />
		 </form:form>
	
	</div>
	
	
	<!--  STANDARD CHANGES WRAPPER  -->
	<div class="tab-wrapp">
		<p class="form-head"><spring:message code="cpr.standard.changes" /></p>
		<c:if test="${empty standard.standardChanges}">
			<p class="msg alert">
				<spring:message code="cpr.standard.changes.empty" />
			</p>
		</c:if>
		<c:if test="${not empty standard.standardChanges}">
			<table class="data">
				<c:forEach items="${standard.standardChanges}" var="i">
					<tr>
						<td class="b">
							<a title="Editovat" class="tt b" href="${formUrl}/standard-change/${i.id}">${i.changeCode}</a>
						</td>
						<td class="last-edit"><joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/></td>
						<td class="delete big">
							<a class="confirm"  href="${formUrl}/standard-change/delete/${i.id}">
				 				<spring:message code="form.delete" />
				 			</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		
		<div class="inline-form r">
			<a class="lang mandate-add-btn" href="${formUrl}/standard-change/0">
				<spring:message code="cpr.standard.changes.add" />
			</a>
		</div>
	</div>