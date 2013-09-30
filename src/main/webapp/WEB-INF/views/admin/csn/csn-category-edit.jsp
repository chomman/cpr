<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="csn.category.edit" /></title>
	<script src="<c:url value="/resources/admin/tiny_mce/tiny_mce.js" />"></script>
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
			 <span><spring:message code="csn.category.edit" /></span>
		</div>
		<h1><spring:message code="csn.category.edit" /></h1>

		<div id="content">
			
			<ul class="sub-nav">
				<li><a href="<c:url value="/admin/csn/categories"  />"><spring:message code="csn.category.list" /></a></li>
				<li><a <c:if test="${id == 0}">class="active" </c:if> href="<c:url value="/admin/csn/category/edit/0"  />"><spring:message code="csn.category.new" /></a></li>
				<li><a href="<c:url value="/admin/csn/category/import"  />"><spring:message code="csn.category.import" /></a></li>
			</ul>
		
			<c:if test="${not empty csnCategory.createdBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.created" /></td>
						<td class="val">${csnCategory.createdBy.firstName} ${csnCategory.createdBy.lastName}</td>
						<td class="val"><joda:format value="${csnCategory.created}" pattern="${common.dateTimeFormat}"/></td>
					</tr>
					<c:if test="${not empty csn.changedBy}">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${csnCateogry.changedBy.firstName} ${csnCateogry.changedBy.lastName}</td>
						<td class="val"><joda:format value="${csnCateogry.changed}" pattern="${common.dateTimeFormat}"/></td>
					</tr>
					</c:if>
				</table>
			</c:if>
			
			<c:url value="/admin/csn/category/edit/${id}" var="formUrl"/>					
			<form:form  modelAttribute="csnCategory" method="post" cssStyle="valid"  action="${formUrl}" >
								
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />

                <p>
                	<label>
                		<strong>
                			<em class="red">*</em>
                			<spring:message code="csn.category.form.name" />:
                		</strong>
                	</label>
                   <span class="field">
                    	<form:input path="name" maxlength="255" cssClass="mw500 required" />
                    </span>
                </p>
                  				
                <form:hidden path="id"/>
                <p class="button-box">
                	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                </p>
			</form:form>
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>