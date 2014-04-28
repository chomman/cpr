<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="admin.portal.order.title" />: ${portalOrder.id}</title>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/portal-nav.jsp" />
		</div>	
		<div id="right">
		
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  u&raquo;
				 <a:adminurl href="/portal/orders"><spring:message code="admin.portal.orders" /></a:adminurl>  &raquo;
				 <span><spring:message code="admin.portal.order.title" />: ${portalOrder.id}</span>
			</div>
			<h1>
				<spring:message code="admin.portal.order.title" />: <strong>${portalOrder.id}</strong>
			</h1>
	
			<div id="content">
				
				<jsp:include page="order-nav.jsp" />
				
				
				<table class="info">
					
						<tr>
						<c:if test="${not empty portalOrder.id}">
							<td class="key"><spring:message code="admin.portal.order.no" /></td>
							<td class="val"><strong>${portalOrder.id}</strong></td>
						</c:if>
						</tr>
					
					<tr>
						<td class="key"><spring:message code="admin.portal.order.created" /></td>
						<td class="val"><joda:format value="${system.created}" pattern="${common.dateTimeFormat}"/></td>
						<c:if test="${not empty portalOrder.ipAddress}">
						<td class="key"><spring:message code="admin.portal.order.idAddres" /></td>
						<td class="val">${portalOrder.ipAddress}</td>
						</c:if>
					</tr>
					<c:if test="${not empty portalOrder.dateOfActivation}">
						<tr>
							<td class="key"><spring:message code="admin.portal.order.dateOfActivation" /></td>
							<td class="val"><strong><joda:format value="${system.dateOfActivation}" pattern="dd.MM.yyyy"/></strong> </td>
						</tr>
					</c:if>
				</table>
				
				
				<form:form commandName="service" method="post" cssClass="valfid" >
							
							<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
							
							<c:if test="${not empty successCreate}">
								<p class="msg ok"><spring:message code="success.create" /></p>
							</c:if>
							
							<p class="form-head"><spring:message code="admin.portal.order.head.service" /></p>
							<p>
	                        	<label>
	                        		<strong><em class="red">*</em>
	                        			<spring:message code="admin.portal.order" />  
	                        		</strong>
	                        	</label>
	                            <span class="field">
	                            	<form:select path="portalService">
	                            		<c:forEach items="${model.services}" var="i">
	                            			<option value="${id}" >${i.czechName}</option>
	                            		</c:forEach>
	                            	</form:select>
	                            </span>
	                        </p>
	                         <p>
	                       		<label>
	                       			<strong><em class="red">*</em>
	                        			<spring:message code="admin.service.price" />
	                        		</strong>  
	                        	</label>
	                            <span class="field"> 
	                            	<form:input path="price" maxlength="5" cssClass="w100 required numeric" />
	                            	<span>DPH: ${service.vat - 1}%</span>
	                            </span>
	                        </p>
	                        <p class="form-head"><spring:message code="admin.portal.order.head.customer" /></p>
	                        <p>
	                        	<label>
	                        		<strong><em class="red">*</em>
	                        			<spring:message code="admin.portal.order.firstName" />  
	                        		</strong>
	                        	</label>
	                            <span class="field">
	                            	<form:input path="firtsName" cssClass="mw500 required" />
	                            </span>
	                        </p>
	                        <p>
	                        	<label>
	                        		<strong><em class="red">*</em>
	                        			<spring:message code="admin.portal.order.lastName" />  
	                        		</strong>
	                        	</label>
	                            <span class="field">
	                            	<form:input path="lastName" cssClass="mw500 required" />
	                            </span>
	                        </p>
	                         <p>
	                        	<label>
	                        		   <spring:message code="admin.portal.order.email" />  
	                        	</label>
	                            <span class="field">
	                            	<form:input path="email" cssClass="mw300 email required" />
	                            </span>
	                        </p>
	                         <p>
	                        	<label>
	                        		   <spring:message code="admin.portal.order.phone" />  
	                        	</label>
	                            <span class="field">
	                            	<form:input path="phone" cssClass="mw300" />
	                            </span>
	                        </p>
	                        <p class="form-head"><spring:message code="admin.portal.order.head.address" /></p>
	                        <p>
	                        	<label>
	                        		<spring:message code="admin.portal.city" />  
	                        	</label>
	                            <span class="field">
	                            	<form:input path="city" cssClass="mw300" />
	                            </span>
	                        </p>
	                        <p>
	                        	<label>
	                        		<spring:message code="admin.portal.street" />:
	                        	</label>
	                            <span class="field">
	                            	<form:input path="street" cssClass="mw300" />
	                            	<span><spring:message code="admin.portal.zip" />:</span>
	                            	<form:input path="zip" cssClass="mw100" maxlength="6"/>
	                            </span>
	                        </p>
	                        <p class="form-head"><spring:message code="admin.portal.order.head.company" /></p>
							<p>
	                        	<label>
	                        		<spring:message code="admin.portal.companyName" />  
	                        	</label>
	                            <span class="field">
	                            	<form:input path="city" cssClass="mw300" />
	                            </span>
	                        </p>
	                        <p>
	                        	<label>
	                        		<spring:message code="admin.portal.ico" />  
	                        	</label>
	                            <span class="field">
	                            	<form:input path="ico" cssClass="mw300" maxlength="8" />
	                            </span>
	                        </p>
	                        <p>
	                        	<label>
	                        		<spring:message code="admin.portal.dic" />  
	                        	</label>
	                            <span class="field">
	                            	<form:input path="dic" cssClass="mw300" maxlength="12" />
	                            </span>
	                        </p>
	                        
							
							
		                    <form:hidden path="id" />
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