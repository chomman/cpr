<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>
		<c:if test="${id == 0}">
			<spring:message code="csn.add" />
		</c:if>
		<c:if test="${id != 0}">
			<spring:message code="csn.edit" arguments="${csn.csnId}" />
		</c:if>
	</title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
		<jsp:include page="csn-nav.jsp" />
	</div>
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/csn" />"><spring:message code="menu.csn" /></a> &raquo;
			 <span><spring:message code="csn.add" /></span>
		</div>
		<h1>
			<c:if test="${id == 0}">
				<spring:message code="csn.add" />
			</c:if>
			<c:if test="${id != 0}">
				<spring:message code="csn.edit" arguments="${csn.csnId}" />
			</c:if>
		</h1>

		<div id="content">
			<ul class="sub-nav">
				<li><a href="<c:url value="/admin/csn"  />"><spring:message code="csn.list" /></a></li>
				<li><a<c:if test="${id == 0}"> class="active"</c:if> href="<c:url value="/admin/csn/edit/0"  />"><spring:message code="csn.add" /></a></li>
			</ul>
		
			<c:if test="${not empty csn.createdBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.created" /></td>
						<td class="val">${csn.createdBy.firstName} ${csn.createdBy.lastName}</td>
						<td class="val"><joda:format value="${csn.created}" pattern="${dateTimeFormat}"/></td>
					</tr>
					<c:if test="${not empty csn.changedBy}">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${csn.changedBy.firstName} ${csn.changedBy.lastName}</td>
						<td class="val"><joda:format value="${csn.changed}" pattern="${dateTimeFormat}"/></td>
					</tr>
					</c:if>
				</table>
			</c:if>
			
			<c:url value="/admin/csn/edit/${id}" var="formUrl"/>					
			<form:form  modelAttribute="csn" method="post" cssStyle="valid"  action="${formUrl}">
				
				<div id="ajax-result"></div>
				
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				
				<p class="form-head"><spring:message code="csn.basic.info" /><p>
                <p>
                	<label>
                		<strong>
                			<em class="red">*</em>
                			<spring:message code="csn.form.name" />:
                		</strong>
                	</label>
                   <span class="field">
                    	<form:input path="csnId" maxlength="50" cssClass="required" />
                    </span>
                </p>
                <p>
                	<label>
                		<spring:message code="csn.form.published" />:
                	</label>
                    <span class="field">
                    	<form:input path="published" maxlength="4" />
                    </span>
                </p>
                
                <p>
                	<label>
                		<spring:message code="cpr.csn.onlineid" />:
                	</label>
                    <span class="field">
                    	<form:input path="csnOnlineId" cssClass="csnOnlineReplace" />
                    </span>
                </p>
                
                <p>
				    <label >
				    	<strong>
                			<em class="red">*</em>
				 		<spring:message code="csn.form.czechName" />:
				 		</strong>
				 	</label>
				     <span class="field">  
				     	<form:input  htmlEscape="true" path="czechName"  cssClass="mw500 required" maxlength="255" />
				     </span>
				 </p>
				 <p>
				    <label >
				 		<spring:message code="csn.form.englishName" />:
				 	</label>
				     <span class="field">  
				     	<form:input  htmlEscape="true" path="englishName"  maxlength="255"  />
				     </span>
				 </p>

				<p>
				    <label >
				 		<spring:message code="csn.form.category" />:
				 	</label>
				     <span class="field">  
				     	<form:select path="csnCategory" cssClass="mw500" >
				     		<form:option value="0"><spring:message code="form.select" /></form:option>
				     		<form:options items="${model.csnCategories}" itemValue="id" itemLabel="name" /> 
				     	</form:select>
				     </span>
				 </p>
				
				  <p>
                	<label>
                		<spring:message code="csn.form.symbol" />:
                	</label>
                   <span class="field">
                    	<form:input  htmlEscape="true" path="classificationSymbol" maxlength="10"  />
                    </span>
                </p>
				 <p>
                	<label>
                		<spring:message code="csn.form.isc" />:
                	</label>
                   <span class="field">
                    	<form:input  htmlEscape="true" path="ics" class="mw500"  maxlength="255" />
                    </span>
                </p>
               
				
                <form:hidden path="id"/>
                <p class="button-box">
                	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                </p>
			</form:form>
			<span class="note"><spring:message code="form.required" /></span>


				<c:if test="${not empty id  and id != 0}">
				<div id="terminology">			
					<p class="form-head"><spring:message code="csn.terminology" arguments="${csn.csnId}" /><p>
					
					<div class="search-box" >
						
						<a class="add radius" title="<spring:message code="csn.terminology.add" />" href="<c:url value="/admin/csn/${id}/terminology/edit/0" />">
							<spring:message code="csn.terminology.add" />
						</a>
						<span title="<spring:message code="form.quicksearch.title" />" class="tt"><spring:message code="form.quicksearch" />:</span>
						<input id="quick-search" type="text" />
					</div>
						<table class="data csn">
						<thead>
							<tr>
								<tH><spring:message code="csn.terminology.title" /></th>
								<th><spring:message code="csn.terminology.lang" /></th>
								<th><spring:message code="changed" /></th>
								<th><spring:message code="form.edit" /></th>
								<th><spring:message code="form.delete" /></th>
							</tr>
						</thead>
						<tbody>
								 <c:forEach items="${csn.terminologies}" var="i">
								 	<tr>
								 		<td>${i.title}</td>
								 		<td><spring:message code="${i.language.name}" /> </td>
								 		<td class="last-edit">
								 			<c:if test="${empty i.changedBy}">
								 				<joda:format value="${i.created}" pattern="${dateTimeFormat}"/>
								 			</c:if>
								 			<c:if test="${not empty i.changedBy}">
								 				<joda:format value="${i.changed}" pattern="${dateTimeFormat}"/>
								 			</c:if>
								 		</td>
								 		<td class="edit">
								 			<a class="tt" title="Zobrazit a upraviť položku?" href="<c:url value="/admin/csn/${id}/terminology/edit/${i.id}"  />">
								 				<spring:message code="form.edit" />
								 			</a>
								 		</td>
								 		<td class="delete">
								 			<a class="confirm"  href="<c:url value="/admin/csn/${id}/terminology/delete/${i.id}"  />">
								 				<spring:message code="form.delete" />
								 			</a>
								 		</td>
								 	</tr>
								 </c:forEach>
						</tbody>
					</table>
				</div>
			
			</c:if>
			
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>