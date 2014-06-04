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
								<a:adminurl href="/portal/user/${model.order.user.id}">
									<c:out value="${model.order.user.firstName} ${model.order.user.lastName}" />
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
					
					<tr>
						<td class="key"><spring:message code="portalOrderSource" /></td>
						<td class="val"><spring:message code="${model.order.portalOrderSource.code}" /></td>
						<td class="key"><spring:message code="admin.portal.userAgent" /></td>
						<td class="val">${model.order.browser}</td>
					</tr>
					
				</table>
				
				<div class="action-nav">
					<span>
						<spring:message code="options" />:
					</span>
					<a href="<c:url value="/auth/order/pdf/${model.order.code}?type=1" />" class="file pdf">
						<spring:message code="invoice.proforma" />
					</a>
					
					<a href="<c:url value="/auth/order/pdf/${model.order.code}?type=2" />" class="file pdf">
						<spring:message code="invoice.command" />
					</a>
					<a title="<spring:message code="invoice.proforma" />" href="<c:url value="/auth/order/print/${model.order.code}?type=1" />" target="_blank"  class="file print">
						ZFA
					</a>
					<a title="<spring:message code="invoice.command" />" href="<c:url value="/auth/order/print/${model.order.code}?type=2" />" target="_blank"  class="file print">
						PKFA
					</a>
				</div>
				
				<form:form commandName="portalOrder" method="post" cssClass="valfid" >
							
						<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
						<c:if test="${not empty successCreate}">
							<p class="msg ok"><spring:message code="success.create" /></p>
						</c:if>
							
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
	                         <p>
	                        	<label class="tt" title="Datum uskutečnění zdanitelného plnění">
	                        			DUZP:
	                        	</label>
	                            <span class="field">
	                            	<form:input path="duzp" cssClass="date" maxlength="10" />
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
	                            	<form:input path="firstName" cssClass="mw300 required" />
	                            </span>
	                        </p>
	                        <p>
	                        	<label>
	                        		<strong><em class="red">*</em>
	                        			<spring:message code="admin.portal.order.lastName" />  
	                        		</strong>
	                        	</label>
	                            <span class="field">
	                            	<form:input path="lastName" cssClass="mw300 required" />
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
	                         <p>
	                        	<label>
	                        		<spring:message code="portalCountry" />:
	                        	</label>
	                            <span class="field">
	                            	<form:select path="portalCountry" cssClass="chosenSmall" >
										<c:forEach items="${model.portalCountries}" var="i">
											<option ${i == portalOrder.portalCountry ? 'selected="selected" ' : ''}
											value="${i}"><spring:message code="${i.code}" /></option>
										</c:forEach>
									</form:select>
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
	                            	<form:input path="ico" cssClass="w100" maxlength="8" />
	                            	<span><spring:message code="admin.portal.dic" /> </span>
	                            	<form:input path="dic" cssClass="w200" maxlength="12" /> 
	                            </span>
	                        </p>	
		                    <form:hidden path="id" />
	                        <p class="button-box">
	                        	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
	                        </p>
						</form:form>
								
				<div class="hbox" >
					<h2>Položky obejdnávky</h2>
				</div>
				<c:if test="${empty model.order.orderItems}">
					<p class="msg alert">
						<spring:message code="alert.empty" />
					</p>
				</c:if>
				<c:if test="${not empty model.order.orderItems}">	
					<table class="data">
							<thead>
								<tr>
									<th><spring:message code="admin.service.name" /></th>
									<th><spring:message code="admin.service.price" /> </th>
									<th><spring:message code="form.delete" /></th>
								</tr>
							</thead>
							<tbody>
									<c:forEach items="${model.order.orderItems}" var="i">
										<tr>
											<td>
												${i.portalProduct.czechName}
											</td>
											<td class="c">
												${i.price} ${portalOrder.currency.symbol}
											</td>
											<td class="delete">
									 			<a:adminurl href="/portal/order/${portalOrder.id}/delete/${i.id}" cssClass="confirm">
									 				<spring:message code="form.delete" />
									 			</a:adminurl>
								 			</td>
										</tr>
									</c:forEach>
							</tbody>
					</table>
					
					<div class="total-price">
							<div><spring:message code="price.sum" />: <span>${model.order.totalPrice} ${model.order.currency.symbol}</span></div>
							<div><spring:message code="vat"/> (${model.order.percentageVat}): <span>${model.order.vatPriceValue} ${model.order.currency.symbol}</span></div>
							<div class="bigger"><spring:message code="price.sumWithDph" />: <span>${model.order.totalPriceWithVat} ${model.order.currency.symbol}</span></div>
					</div>
					 
				</c:if>
			</div>
			<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>	
		</div>
		<div class="clear"></div>	
	</div>

</body>
</html>