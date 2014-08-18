<%@ page session="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>
		<c:if test="${empty regulation.id}">
			<spring:message code="regulation.add" />
		</c:if>
		<c:if test="${not empty regulation.id}">
			<spring:message code="regulation.edit" />: ${regulation.code}
		</c:if>
	</title>
	<script src="<c:url value="/resources/admin/tinymce/tinymce.min.js" />"></script>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="include/cpr-nav.jsp" />
		</div>
		<div id="right">
			<div id="breadcrumb">
				<a:adminurl href="/">
					<spring:message code="menu.home" />
				</a:adminurl>
				&raquo;
				<a:adminurl href="/cpr/regulations">
					<spring:message code="regulations" />
				</a:adminurl>
				&raquo; 
				<span>
				<c:if test="${empty regulation.id}">
					<spring:message code="regulation.add" />
				</c:if>
				<c:if test="${not empty regulation.id}">
					<spring:message code="regulation.edit" />: ${regulation.code}
				</c:if>
				</span>
			</div>
			<h1>
				<c:if test="${empty regulation.id}">
					<spring:message code="regulation.add" />
				</c:if>
				<c:if test="${not empty regulation.id}">
					<spring:message code="regulation.edit" />: ${regulation.code}
				</c:if>
			</h1>

			<div id="content">
				<ul class="sub-nav">
					<li>
						<a:adminurl href="/cpr/regulations" activeTab="true">
							<spring:message code="regulation.view" />
						</a:adminurl>
					</li>
					<c:if test="${empty regulation.id}">
					<li>
						<a:adminurl href="/cpr/regulation/0">
							<spring:message code="regulation.add" />
						</a:adminurl>
					</li>
					</c:if>
				</ul>


			<form:form commandName="regulation" method="post" cssClass="valid">
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error" />
				
				<c:if test="${not empty successCreate}">
					<p class="msg ok">
						<spring:message code="success.create" />
					</p>
				</c:if>
				<div class="input-wrapp smaller">
					<label> 
						<strong><em class="red">*</em>
							<spring:message code="regulation.code" />:
						</strong>
					</label>
					<div class="field">
						<form:input path="code" cssClass="w100 required" maxlength="20" />
					</div>
				</div>
				
				<c:if test="${not empty regulation.id}">
					<div class="input-wrapp smaller">
						<label> 
								<spring:message code="regulation.eu" />:
						</label>
						<div class="field">
							<c:if test="${regulation.euRegulation}">
								<strong>ANO</strong> &nbsp; |  &nbsp; <a class="tt delete confirm" title="Odstraniť harmonizační předpis ES/EU předpis?" href="?type=eu&amp;action=0">odstranit</a>
							</c:if>
							<c:if test="${not regulation.euRegulation}">
								<strong>NE</strong> &nbsp; |  &nbsp; <a class="tt lang mandate-add-btn" title="Přidat harmonizační předpis ES/EU předpis?" href="?type=eu&amp;action=1">přidat</a>
							</c:if>
						</div>
					</div>
				
				</c:if>
				
				<form:hidden path="id"/> 
				<p class="button-box">
					<input type="submit" class="button default" value="<spring:message code="form.save" />" /> 
				</p>
			</form:form>

								
			</div>
		</div>
		<div class="clear"></div>
	</div>

</body>
</html>