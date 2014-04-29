<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="admin.portal.order.title" />: ${model.order.id}</title>
	<script src="<c:url value="/resources/admin/js/jquery.selectTip.js" />"></script>
	<script>
		$(function() {
			$( ".helpTip" ).selectTip();
		});
	</script>
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
				 <span><spring:message code="admin.portal.order.title" />: ${model.order.id}</span>
			</div>
			<h1>
				<spring:message code="admin.portal.order.title" />: <strong>${model.order.id}</strong>
			</h1>
	
			<div id="content">
				
				<jsp:include page="order-nav.jsp" />
				
				
				<table class="info mw600" style="width:600px;">
					
						<tr>
						<c:if test="${not empty model.order.id}">
							<td class="key"><spring:message code="admin.portal.order.no" /></td>
							<td class="val">${model.order.id}</td>
							<td class="key">Zákazník</td>
							<td class="val">
								<a:adminurl href="/portal/user/${portalOrder.user.id}">
									${portalOrder.user.firstName} ${portalOrder.user.lastName}
								</a:adminurl>
							</td>
						</c:if>
						</tr>
					
					<tr>
						<td class="key"><spring:message code="admin.portal.order.created" /></td>
						<td class="val"><joda:format value="${model.order.created}" pattern="${common.dateTimeFormat}"/></td>
						<c:if test="${not empty model.order.ipAddress}">
						<td class="key"><spring:message code="admin.portal.order.idAddres" /></td>
						<td class="val">${model.order.ipAddress}</td>
						</c:if>
					</tr>
					
						<tr>
							<c:if test="${not empty model.order.dateOfActivation}">
								<td class="key"><spring:message code="admin.portal.order.dateOfActivation" /></td>
								<td class="val"><joda:format value="${model.order.dateOfActivation}" pattern="dd.MM.yyyy"/></td>
							</c:if>
							<c:if test="${not empty model.order.user.registrationValidity}">
								<td class="key">Platnost registrace do:</td>
								<td class="val"><joda:format value="${model.order.user.registrationValidity}" pattern="dd.MM.yyyy"/></td>
							</c:if>
						</tr>
					
				</table>
				
				
				<form:form commandName="portalOrder" method="post" cssClass="valfid" >
							
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
	                            	<form:select path="portalProduct">
	                            		<c:forEach items="${model.portalProducts}" var="i">
	                            			<option value="${i.id}" >${i.czechName}</option>
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
	                            	<span>s (${model.order.formatedVat}) DPH: 
	                            	<strong>${model.order.priceWithVat}</strong> </span>
	                            </span>
	                        </p>
	                         <p class="orderStatus">
	                       		<label>
	                       			<strong><em class="red">*</em>
	                        			<spring:message code="admin.portal.order.stav" />:
	                        		</strong>  
	                        	</label>
	                            <span class="field">
	                            	<form:select path="orderStatus" cssClass="chosenSmall helpTip">
	                            		<c:forEach items="${model.orderStatuses}" var="i">
	                            			<option value="${i}" 
	                            				data-id="${i.id}"
	                            		  		<c:if test="${empty portalOrder.dateOfActivation }">title="<spring:message code="admin.${i.code}" />"</c:if>
	                            				<c:if test="${portalOrder.orderStatus eq i}">selected="selected"</c:if> >
	                            				<spring:message code="${i.code}" />
	                            			</option>
	                            		</c:forEach>
	                            	</form:select>
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
	                            	<form:input path="firstName" cssClass="mw500 required" />
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
	                            	<form:input path="zip" cssClass="w50" maxlength="6"/>
	                            </span>
	                        </p>
	                        <p class="form-head"><spring:message code="admin.portal.order.head.company" /></p>
							<p>
	                        	<label>
	                        		<spring:message code="admin.portal.companyName" />  
	                        	</label>
	                            <span class="field">
	                            	<form:input path="companyName" cssClass="mw300" />
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