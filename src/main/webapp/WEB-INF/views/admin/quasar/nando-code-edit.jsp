<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<title>
		<c:if test="${empty nandoCode.id}">
			<spring:message code="nandoCode.add" />
		</c:if>
		<c:if test="${not empty nandoCode.id}">
			<spring:message code="nandoCode.edit" />
		</c:if>
	</title>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/quasar-nav.jsp" />
		</div>	
		<div id="right">
		
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				 <a:adminurl href="/quasar/dashboard"><spring:message code="quasar.long" /></a:adminurl>  &raquo;
				 <a:adminurl href="/quasar/admin/nando-codes"><spring:message code="nandoCodes" /></a:adminurl>  &raquo;
				 <span>
				 	<c:if test="${empty nandoCode.id}">
						<spring:message code="nandoCode.add" />
					</c:if>
					<c:if test="${not empty nandoCode.id}">
						<spring:message code="nandoCode.edit" />
					</c:if>
				 </span>
			</div>
			<h1>
				<c:if test="${empty nandoCode.id}">
						<spring:message code="nandoCode.add" />
				</c:if>
				<c:if test="${not empty nandoCode.id}">
					<spring:message code="nandoCode.edit" />
				</c:if>
			</h1>
	
			<div id="content">
				
				<jsp:include page="navs/nando-code-nav.jsp" />
				
				<form:form commandName="nandoCode" method="post" cssClass="valid" >
							
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				
					<c:if test="${not empty successCreate}">
						<p class="msg ok"><spring:message code="success.create" /></p>
					</c:if>
					
					<p class="form-head"><spring:message code="baseInformations" /></p>
					<p>
                      		<label>
                      			<strong><em class="red">*</em>
                       			<spring:message code="nandoCode.code" />:
                       		</strong>  
                       		<small>Pattern: SSSS NNNN</small>
                       	</label>
                           <span class="field">
                           	<form:input path="code" maxlength="8" cssClass="w100 required" />
                           </span>
                       </p>
                         <p>
                      		<label>
                      			<spring:message code="isActivated" />:
                       	</label>
                           <span class="field"> 
                           	<form:checkbox path="enabled"/>
                           </span>
                       </p>
                       
                       <p>
                      		<label>
                      			<strong><em class="red">*</em>
                       			<spring:message code="nandoCode.specification" />:
                       		</strong>  
                       	</label>
                           <span class="field">
                           	<form:textarea path="specification" cssClass="required mw500" />
                           </span>
                       </p>
                       
                        <p>
                      		<label>
                       		<spring:message code="nandoCode.parent" />:
                       		<small>(if any)</small>
                       	</label>
                           <span class="field">
                           	<form:select path="parent">
                           		<option value="">--- <spring:message code="nandoCode.parent" /> ---</option>
                           		<c:forEach items="${model.firstLevelCodes}" var="i">
                           			<option value="${i.id}" ${i.id == nandoCode.id ? 'selected="selected"' : ''}>
                           				${i.code} - ${nlf:crop(i.specification, 150)}
                           			</option>
                           		</c:forEach>
                           	</form:select>
                           </span>
                       </p>
                       
                       <p class="form-head"><spring:message code="nandoCode.scope" /></p>
                       <p>
                      		<label>
                      			<spring:message code="nandoCode.productAssessorAScope" />:
                       	</label>
                           <span class="field"> 
                           	<form:checkbox path="forProductAssesorA"/>
                           </span>
                       </p>
                         <p>
                      		<label>
                      			<spring:message code="nandoCode.productAssessorRScope" />:
                       	</label>
                           <span class="field"> 
                           	<form:checkbox path="forProductAssesorR"/>
                           </span>
                       </p>
                         <p>
                      		<label>
                      			<spring:message code="nandoCode.productSpecialist" />:
                       	</label>
                           <span class="field"> 
                           	<form:checkbox path="forProductSpecialist"/>
                           </span>
                       </p>
                    <form:hidden path="id" />
                       <p class="button-box">
                       	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                       </p>
				</form:form>
				<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>
			</div>	
		</div>
		<div class="clear"></div>	
	</div>

</body>
</html>