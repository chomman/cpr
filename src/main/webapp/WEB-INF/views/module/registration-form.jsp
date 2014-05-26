<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ page trimDirectiveWhitespaces="true" %>
<link rel="stylesheet" href="<c:url value="/resources/admin/css/chosen.css" />" />
<script type="text/javascript" src="<c:url value="/resources/admin/js/chosen.jquery.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/portal/js/registration-form.js" />"></script>
<c:if test="${webpageModel.isRegistration}">
<c:url value="/ajax/registration" var="url"/>
</c:if>
<c:if test="${not webpageModel.isRegistration}">
<c:url value="/ajax/order" var="url"/>
</c:if>
<form:form commandName="user" method="post" cssClass="valid form" action="${url}"  >
	
	<!-- SELECT PRODUCTS SECTION  -->	
	<strong class="form-head"><spring:message code="portaluser.selectProducts" /></strong>
	<!-- REGISTRATIONS -->
	<table class="pubs">
		<tr >
			<th><spring:message code="portalProductType.registration" /></th>
			<th><spring:message code="portaluser.th.price" /></th>
			<th>&nbsp;</th>
		</tr>
		<c:set var="products" value="${webpageModel.portalRegistrations}" scope="request" />
		<jsp:include page="registration-form-item.jsp" />
	</table>
	
	<!-- PUBLICATIONS -->
	<table class="pubs">
		<tr >
			<th><spring:message code="portaluser.th.onlinePublication" /></th>
			<th><spring:message code="portaluser.th.price" /></th>
			<th>&nbsp;</th>
		</tr>
		<c:set var="products" value="${webpageModel.portalOnlinePublications}" scope="request" />
		<jsp:include page="registration-form-item.jsp" />
	</table>
	
	
	<!-- TOTALS inforations  -->
	<strong class="form-head"><spring:message code="portalUser.head.price" /></strong>
	<div class="price-sum-wrapp">
		<div><spring:message code="price.sum" />: <span id="price" class="price-bx"></span></div> 
		<div><spring:message code="vat" />&nbsp;(<span id="vat-val"></span>) : <span id="price-vat" class="price-bx"></span></div>
		<div class="bigger"><spring:message code="price.sumWithDph" />: <span id="price-with-dph" class="price-bx"></span></div>
	</div>
	
	<!-- CURRENCY inforations  -->
	<strong class="form-head"><spring:message code="payment.method" /></strong>
	<div class="payment">
		<div class="rf-left">
			<strong><spring:message code="payment.method.bank" /></strong>
		</div>
		<div class="rf-right">
			<span><spring:message code="portaluser.currency" />: </span>
			<ul>
				<c:forEach items="${webpageModel.portalCurrencies}" var="i">
					<li><a href="" class="currency pj-radius ${user.portalCurrency eq i ? 'active' : ''}">${i}</a></li>	
				</c:forEach>
			</ul>
		</div>
		<div class="clear"></div>
	</div>
	
	
	<c:if test="${webpageModel.isRegistration}">
		<!-- LOGIN INFO -->
		<strong class="form-head"><spring:message code="portalUser.head.loginInfo" /></strong>
		<div id="ajax-result"></div>
		<form:label path="email" cssClass="with-info">
			<span class="label"><spring:message code="portalUser.email" />: </span>
			<form:input path="email" cssClass="required email w300" maxlength="50"/>
			<span class="miniinfo"><spring:message code="portal.miniinfo.email" /></span>
		</form:label>
		<form:label path="password"  cssClass="with-info">
			<span class="label"><spring:message code="portalUser.password" />:</span>
			<form:password path="password" cssClass="required w300 more6" maxlength="50"/>
			<span class="miniinfo"><spring:message code="portal.miniinfo.password" /></span>
		</form:label>
		<form:label path="confirmPassword">
			<span class="label"><spring:message code="portalUser.confirmPassword" />:</span>
			<form:password path="confirmPassword" cssClass="required w300 more6" maxlength="50"/>
		</form:label>
	</c:if>
	
	<!-- INVOICE INFO -->
	<strong class="form-head"><spring:message code="portaluser.profile.order.fa" /></strong>
	<c:if test="${not webpageModel.isRegistration}"><div id="ajax-result"></div></c:if>
	<form:label path="userInfo.portalCountry">
		<span class="label"><spring:message code="portalUser.country" />:</span>
		<form:select path="userInfo.portalCountry" cssClass="chosen" id="portalCountry">
			<c:forEach items="${webpageModel.portalCountries}" var="i">
				<option ${i == user.userInfo.portalCountry ? 'selected="selected" ' : ''}
				value="${i}"><spring:message code="${i.code}" /></option>
			</c:forEach>
		</form:select>
	</form:label>
	<form:label path="firstName">
		<span class="label"><spring:message code="portalUser.firstName" />:</span>
		<form:input path="firstName" cssClass="required w300" maxlength="50"/>
	</form:label>
	<form:label path="lastName">
		<span class="label"><spring:message code="portalUser.lastName" />:</span>
		<form:input path="lastName" cssClass="required w300" maxlength="50"/>
	</form:label>
	<form:label path="userInfo.phone">
		<span class="label"><spring:message code="portalUser.phone" />:</span>
		<form:input path="userInfo.phone" cssClass="required w300 phone" maxlength="25"/>
	</form:label>
	<c:if test="${not webpageModel.isRegistration}">
	<form:label path="email" cssClass="with-info">
		<span class="label"><spring:message code="portalUser.email" />: </span>
		<form:input path="email" cssClass="required email w300" maxlength="50"/>
	</form:label>
	</c:if>
	<form:label path="userInfo.city">
		<span class="label"><spring:message code="portalUser.city" />:</span>
		<form:input path="userInfo.city" cssClass="required w300" maxlength="50"/>
	</form:label>
	<form:label path="userInfo.street">
		<span class="label"><spring:message code="portalUser.street" />:</span>
		<form:input path="userInfo.street" cssClass="required w300" maxlength="50"/>
	</form:label>
	<form:label path="userInfo.zip">
		<span class="label"><spring:message code="portalUser.zip" />:</span>
		<form:input path="userInfo.zip" cssClass="required zip w80" maxlength="50"/>
	</form:label>
	
	<form:label path="userInfo.companyName" cssClass="pj-company ${empty user.userInfo.companyName ? 'hidden' : ''}">
		<span class="label"><spring:message code="portalUser.companyName" />:</span>
		<form:input path="userInfo.companyName" cssClass="w300" maxlength="50"/>
	</form:label>
	<form:label path="userInfo.ico" cssClass="pj-company ${empty user.userInfo.companyName ? 'hidden' : ''}">
		<span class="label"><spring:message code="portalUser.ico" />:</span>
		<form:input path="userInfo.ico" maxlength="8" cssClass="numeric" />
	</form:label>
	<form:label path="userInfo.dic" cssClass="pj-company ${empty user.userInfo.companyName ? 'hidden' : ''}">
		<span class="label"><spring:message code="portalUser.dic" />:</span>
		<form:input path="userInfo.dic"  maxlength="12"/>
	</form:label>
	<c:if test="${empty user.userInfo.companyName}">
		<div class="is-company">
			<spring:message code="portaluser.companyRegistration" />
		</div>
	</c:if>
	
	
	<span class="pj-gray">
		<span>&nbsp;</span>
		<input type="submit" value="<spring:message code="form.submit" />" class="button pj-radius6"/>
		<span class="vop">Odesláním formuláře souhlasíte s <a target="_blank" href="<webpage:link webpage="${webpageModel.termsOfConditions}" />" 
															class="pj-link">obchodními podmínkami</a></span>
	</span>
	<form:hidden path="portalCurrency" />
	<form:hidden path="portalOrderSource" />
</form:form>


<div id="status"></div>
<div id="vat">${webpageModel.vat}</div>
<div id="loader"></div>