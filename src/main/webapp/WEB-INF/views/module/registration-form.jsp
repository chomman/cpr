<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<script type="text/javascript" src="<c:url value="/resources/admin/js/chosen.jquery.min.js" />"></script>
<link rel="stylesheet" href="<c:url value="/resources/admin/css/chosen.css" />" />
<script type="text/javascript">
$(function(){ 
	$(".chosen").chosen({ width : "500px" });
});
</script>
<c:url value="/ajax/registration" var="url"/>
<form:form commandName="user" method="post" cssClass="valid form" action="${url}"  >
	<div id="ajax-result"></div>
	
	<strong class="form-head"><spring:message code="portalUser.head.productInfo" /></strong>
	<form:label path="">
		<span class="label"><spring:message code="portalUser.portalProduct" />: </span>
		<form:select path="portalProduct" cssClass="portalProduct chosen">
			<c:forEach items="${model.portalProducts}" var="i">
				<option value="${i.id}" data-price="${i.price}">
					${i.intervalValue} / <spring:message code="${i.portalProductInterval.code}" /> -
					<a:localizedValue object="${i}" fieldName="name" />
				</option>
			 </c:forEach>
		</form:select>
	</form:label>
	<div class="desc-wrapp">
		<c:forEach items="${model.portalProducts}" var="i">
			<div id="p${i.id}">
				<span class="descr">
					<a:localizedValue object="${i}" fieldName="description" />
				</span>
				<span class="price-wrapp no-vat">
					<span class="l"><spring:message code="portalUser.price" />: </span>
					<span class="v"><webpage:price price="${i.price}" /></span>
					
				</span>
				<span class="price-wrapp with-vat">
					<span class="l"><spring:message code="portalUser.priceWithVat" />: </span>
					<span class="v"><webpage:price price="${i.price}" useSystemVat="true" /></span>
				</span>
			</div>
		</c:forEach>
	</div>	
	<strong class="form-head"><spring:message code="portalUser.head.loginInfo" /></strong>
	
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
	
	
	<strong class="form-head"><spring:message code="portalUser.head.otherInfo" /></strong>
	 
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
	

	<form:label path="userInfo.companyName">
		<span class="label"><spring:message code="portalUser.companyName" />:</span>
		<form:input path="userInfo.companyName" cssClass="w300" maxlength="50"/>
	</form:label>
	

	<form:label path="userInfo.ico">
		<span class="label"><spring:message code="portalUser.ico" />:</span>
		<form:input path="userInfo.ico" maxlength="8" cssClass="numeric" />
	</form:label>
	
	
	<form:label path="userInfo.dic">
		<span class="label"><spring:message code="portalUser.dic" />:</span>
		<form:input path="userInfo.dic"  maxlength="12"/>
	</form:label>
	
	<span class="pj-gray">
		<span>&nbsp;</span>
		<input type="submit" value="Odeslat" class="button pj-radius6"/>
		<span class="vop">Odesláním formuláře souhlasíte s obchodními podmínkami</span>
	</span>
</form:form>
<div id="status"></div>