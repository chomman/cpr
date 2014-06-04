<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<link rel="stylesheet" href="<c:url value="/resources/portal/css/invoice.css" />" />


<c:if test="${webpageModel.portalOrder.orderStatus.id == 1}">
	<c:if test="${empty webpageModel.hideAlert}">
		<p class="status alert"><spring:message code="portaluser.profile.order.alert" /></p>
	</c:if>
	<c:if test="${not empty webpageModel.hideAlert}">
			<strong class="pj-bank-head">
				<spring:message code="bank.payment" />
			</strong>
	</c:if>
	<table class="payment-info">
		<tr>
			<td class="key"><spring:message code="bank.accountNo" />:</td>
			<td class="val">${webpageModel.portalOrder.isInEuro ?  webpageModel.settings.euAccountNumber :  webpageModel.settings.czAccountNumber} </td>
		</tr>
		<tr>
			<td class="key"><spring:message code="bank.iban" />:</td>
			<td class="val">${webpageModel.portalOrder.isInEuro ?  webpageModel.settings.euIban :  webpageModel.settings.czIban} </td>
		</tr>
		<tr>
			<td class="key"><spring:message code="bank.swift" />:</td>
			<td class="val">${webpageModel.portalOrder.isInEuro ?  webpageModel.settings.euSwift :  webpageModel.settings.czSwift} </td>
		</tr>
		<tr>
			<td class="key"><spring:message code="bank.vs" />:</td>
			<td class="val">${webpageModel.portalOrder.id}</td>
		</tr>
		<tr>
			<td class="key"><spring:message code="price.sumWithDph" />:</td>
			<td class="val">
				<webpage:price price="${webpageModel.portalOrder.totalPriceWithVat}" isEuro="${webpageModel.portalOrder.isInEuro}" />
			</td>
		</tr>
		
	</table>
</c:if>

<c:if test="${empty webpageModel.hideAlert and webpageModel.portalOrder.orderStatus.id == 1 or webpageModel.portalOrder.orderStatus.id == 2}">
	<div class="action-nav">
		<span>
			<spring:message code="options" />:
		</span>
		<a href="<c:url value="/auth/order/pdf/${webpageModel.portalOrder.code}?type=1" />" class="file pdf">
			<spring:message code="invoice.proforma" />
		</a>
		<a href="<c:url value="/auth/order/print/${webpageModel.portalOrder.code}?type=1" />" target="_blank"  class="file print">
			<spring:message code="print" />
		</a>
	</div>
</c:if>

<div id="invoice" class="new iv">
	<div class="this-is">
		<strong>
			<spring:message code="portaluser.profile.order" /> ${webpageModel.portalOrder.id}
		</strong>
	</div><!-- invoice headline -->

	<div id="parties">

		<div class="invoice-to">
			<h2>
				<spring:message code="portaluser.profile.order.customer" />:
			</h2>
			<div id="hcard-Hiram-Roth" class="vcard">
				<a class="url fn" href="http://memory-alpha.org">
					<c:out value="${webpageModel.portalOrder.firstName} ${webpageModel.portalOrder.lastName}" />
				</a>
				<div class="tel">${webpageModel.portalOrder.phone}</div>
				<a class="email" href="mailto:${webpageModel.portalOrder.email}">${webpageModel.portalOrder.email}</a>
				
				<div class="adr">
					
					<div class="street-address"><c:out value="${webpageModel.portalOrder.street}" /></div>
					<span class="locality"><c:out value="${webpageModel.portalOrder.zip}" /></span>
					<span class="country-name"><c:out value="${webpageModel.portalOrder.city}" /></span>
					<div>
						<spring:message code="${webpageModel.portalOrder.portalCountry.code}" />
					</div>
				</div>
			<c:if test="${not empty webpageModel.portalOrder.companyName}">
				<div class="adr">
						<div class="street-address"><c:out value="${webpageModel.portalOrder.companyName}" /></div>
						<div class="ic">
							<spring:message code="admin.portal.ico" />:
							<c:out value="${webpageModel.portalOrder.companyName}" />
						</div> 
						<div class="dic">
							<spring:message code="admin.portal.dic" />:
							<c:out value="${webpageModel.portalOrder.dic}" />
						</div>
				</div>
			</c:if>	
			</div><!-- e: vcard -->
		</div><!-- e invoice-to -->


		<div class="invoice-from">
				<h2>
				<spring:message code="portaluser.profile.order.contractor" />:
			</h2>
			<div id="hcard-Admiral-Valdore" class="vcard">
				<a class="url fn" href="http://www.itczlin.cz" target="_blank">
					${webpageModel.settings.companyName}
				</a>
			
				<div class="adr">
					<div class="street-address">${webpageModel.settings.street}</div>
					<span class="locality">${webpageModel.settings.zip}</span>
					<span class="country-name">${webpageModel.settings.city}</span>
					<div>Česká republika</div>
				</div>
				
				
				<a class="email" href="mailto:${webpageModel.settings.invoiceEmai}">${webpageModel.settings.invoiceEmai}</a>
				<div class="tel">${webpageModel.settings.phone}</div>
				
				<div class="adr">
						<div class="ic">
							<spring:message code="admin.portal.ico" />:
							${webpageModel.settings.ico}
						</div> 
						<div class="dic">
							<spring:message code="admin.portal.dic" />:
							${webpageModel.settings.dic}
						</div>
				</div>
				
			</div><!-- e: vcard -->
		</div><!-- e invoice-from -->
	
		<div class="invoice-status">
				<h2>
					<spring:message code="portaluser.profile.order.info" />
				</h2>
				<div id="hcard-Admiral-Valdore" class="vcard">
					<dl class="invoice-meta">
						<dt class="invoice-number">
							<spring:message code="portaluser.profile.order.info.no"  />
						</dt>
						<dd>${webpageModel.portalOrder.id}</dd>
						<dt class="invoice-date"><spring:message code="portaluser.profile.order.info.date"  /></dt>
						<dd><joda:format value="${webpageModel.portalOrder.created}" pattern="dd.MM.yyyy"/></dd>
						<dt class="invoice-date"><spring:message code="portaluser.profile.order.info.activated"  /></dt>
						<dd>
							<c:if test="${not empty webpageModel.portalOrder.dateOfActivation}">
								<joda:format value="${webpageModel.portalOrder.dateOfActivation}" pattern="dd.MM.yyyy"/>
							</c:if>
							<c:if test="${empty webpageModel.portalOrder.dateOfActivation}">
								-
							</c:if>
						</dd>
						<dt class="invoice-date"><spring:message code="payment.method"  /></dt>
						<dd><spring:message code="payment.method.bank"  /></dd>
						<dt class="invoice-date"><spring:message code="portaluser.profile.order.info.state"  /></dt>
						<dd><spring:message code="${webpageModel.portalOrder.orderStatus.code}" /></dd>
					</dl>
				</div><!-- e: vcard -->
		</div>

		

	</div><!-- e: invoice partis -->


	<div class="invoice-financials">

		<div class="invoice-items">
			<table>
				<caption>
					<spring:message code="portaluser.profile.order.items" />
				</caption>
				<thead>
					<tr>
						<th><spring:message code="portaluser.profile.order.product" /></th>
						<th><spring:message code="portaluser.profile.order.price" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${webpageModel.portalOrder.orderItems}" var="i">
						<tr>
							<td class="l">
								<a:localizedValue object="${i.portalProduct}" fieldName="name" />
							</td>
							<td>
								<webpage:price price="${i.price}" isEuro="${webpageModel.portalOrder.isInEuro}" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div><!-- e: invoice items -->


		<div class="invoice-totals">
			<table>
				<caption><spring:message code="portaluser.profile.order.total" /> :</caption>
				<tbody>
					<tr>
						<th><spring:message code="price.sum" />:</th>
						<td><webpage:price price="${webpageModel.portalOrder.totalPrice}" isEuro="${webpageModel.portalOrder.isInEuro}" /></td>
					</tr>
					<tr>
						<th><spring:message code="vat"/> (${webpageModel.portalOrder.percentageVat}):</th>
						<td><webpage:price price="${webpageModel.portalOrder.vatPriceValue}" isEuro="${webpageModel.portalOrder.isInEuro}" /></td>
					</tr>
					<tr>
						<th><spring:message code="price.sumWithDph" />:</th>
						<td><webpage:price price="${webpageModel.portalOrder.totalPriceWithVat}" isEuro="${webpageModel.portalOrder.isInEuro}" /></td>
					</tr>
				</tbody>
			</table>
		</div><!-- e: invoice totals -->



	</div><!-- e: invoice financials -->
	<div class="clear"></div>
</div>