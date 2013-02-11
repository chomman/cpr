<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>${model.webpage.title}</title>
		<meta name="description" content="${model.webpage.description}" />
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a title="<spring:message code="homepage" />" href="<c:url value="/" />"><spring:message code="homepage" /></a> &raquo; 
			<span>${model.webpage.name}</span>
	</div> 

		<div id="main-content">
			<article>
				${model.webpage.topText}
			</article>
			<c:if test="${model.webpage.webpageContent.id == 8}">
				
				<c:url value="/dop/form" var="formUrl"/>	
				<form:form commandName="declarationOfPerformance" id="dop" method="post" action="${formUrl}" >
					<div class="fitem">
						<label><spring:message code="dop.numberofdeclaration" /></label>
						<form:input cssClass="w300 required" path="numberOfDeclaration" />
						<div class="clear"></div>
					</div>
					
					
					<div class="fitem">
						<label><spring:message code="dop.productid" /></label>
						<form:input cssClass="w300 required" path="productId" />
						<div class="clear"></div>
						<p><spring:message code="dop.productid.info" /></p>
					</div>
					
					
					<div class="fitem">
						<label><spring:message code="dop.serialid" /></label>
						<form:input cssClass="w300 required" path="serialId" />
						<div class="clear"></div>
						<p><spring:message code="dop.serialid.info" /></p>
					</div>
					
					
					<div class="fitem">
						<label><spring:message code="dop.intendedUse" /></label>
						<form:textarea cssClass="w600 h80 required" path="intendedUse" rows="3" />
						<div class="clear"></div>
						<p><spring:message code="dop.intendedUse.info" /></p>
					</div>
					
					
					<div class="fitem">
						<label><spring:message code="dop.authorisedRepresentative" /></label>
						<form:textarea cssClass="w600 h80 required" path="authorisedRepresentative" rows="3" />
						<div class="clear"></div>
					</div>
					
					<div class="fitem">
						<label><spring:message code="dop.assessmentSystem" /></label>
						<form:select  path="assessmentSystem" cssClass="w300 required">
							<form:options items="${model.assessmentSystems}" itemValue="id" itemLabel="name" />
						</form:select>
						<div class="clear"></div>
					</div>
					
					<div class="fitem">
						<label><spring:message code="dop.notifiedBody" /></label>
						<form:select path="notifiedBody" cssClass="w600" >
						
							 <c:forEach items="${model.notifiedBodies}" var="nb" varStatus="s" >
					 			<c:if test="${prev != nb.country.id }">
					 				<optgroup label="${nb.country.countryName}"></optgroup>
					 				<c:set value="${nb.country.id}" var="prev" />
					 			</c:if>
					 				<option value="${nb.id}" 
						 				<c:forEach items="${model.standardnotifiedBodies}" var="i">
						 					<c:if test="${i.id ==  nb.id}"> selected="selected" </c:if>
						 				</c:forEach> 
					 				>
					 				${nb.notifiedBodyCode} - ${nb.name}
					 				</option>			 			
							</c:forEach>
						 </form:select>
						<div class="clear"></div>
					</div>
					
					
				</form:form>
			
			
			</c:if>
			 <article>
					${model.webpage.bottomText}
			 </article>
				
			 
		</div>
	</body>
</html>