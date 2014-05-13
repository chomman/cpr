<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<link rel="stylesheet" href="<c:url value="/resources/admin/css/chosen.css" />" />
<script type="text/javascript" src="<c:url value="/resources/admin/js/chosen.jquery.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/portal/js/registration-form.js" />"></script>
<c:url value="/ajax/registration" var="url"/>
<form:form commandName="user" method="post" cssClass="valid form" action="${url}"  >
	
	
	<strong class="form-head"><spring:message code="portalUser.head.productInfo" /></strong>
	<form:label path="">
		<span class="label"><spring:message code="portalUser.portalProduct" />: </span>
		<select id="portalProduct" class="portalProduct chosen">
			<c:forEach items="${model.portalRegistrations}" var="i">
				<option value="${i.id}" data-price="${model.useEuro ? i.priceEur : i.priceCzk}">
					<a:localizedValue object="${i}" fieldName="name" />
				</option> 
			 </c:forEach>
		</select>
	</form:label>
	<div class="desc-wrapp">
		<c:forEach items="${model.portalRegistrations}" var="i"> 
			<div id="p${i.id}" class="product-info hidden">
				<span class="descr">
					<a:localizedValue object="${i}" fieldName="description" />
				</span>
				 <span class="price-wrapp no-vat pj-radius">
					<span class="l"><spring:message code="portalUser.price" />: </span>
					<span class="v">
						<webpage:price price="${model.useEuro ? i.priceEur : i.priceCzk}" isEuro="${model.useEuro}" />
					</span>
				</span>
				<!-- 
				<span class="price-wrapp with-vat pj-radius">
					<span class="l"><spring:message code="portalUser.priceWithVat" />: </span>
					<span class="v">
						<webpage:price price="${model.useEuro ? i.priceEur : i.priceCzk}" isEuro="${model.useEuro}" useSystemVat="true" />
					</span>
				</span>
				 --> 
			</div>
		</c:forEach>
	</div>	
	<strong class="form-head"><spring:message code="portalUser.head.otherProducts" /></strong>
		<table class="pubs">
			<tr >
				<th><spring:message code="portaluser.th.onlinePublication" /></th>
				<th><spring:message code="portaluser.th.price" /></th>
				<th>&nbsp;</th>
			</tr>
			
			<c:forEach items="${model.portalOnlinePublications}" var="i" varStatus="s">
			<tr class="${s.index % 2 == 0 ? 'even' : 'odd'} ${i.id == model.pid ? ' selected' : ''}" data-id="${i.id}" data-price="${model.useEuro ? i.priceEur : i.priceCzk}">
				<td>
					<a href="<c:url value="/${i.publicationUrl}" />" target="_blank" class="product-url">
						<a:localizedValue object="${i}" fieldName="name" />
					</a>
				</td>
				<td class="price-wrapp"> 
					<webpage:price price="${model.useEuro ? i.priceEur : i.priceCzk}" isEuro="${model.useEuro}" /> 
				</td>
				<td class="btn-wrapp">
					<a href="#" class="pub-btn add pj-radius6" title="<spring:message code="portaluser.add.title" />"> 
						<spring:message code="portaluser.add" />
					</a>
					<a href="#" class="pub-btn remove pj-radius6" title="<spring:message code="portaluser.remove.title" />"> 
						<spring:message code="portaluser.remove" />
					</a>
				</td>
			</tr>
			</c:forEach>
		</table>
	
	<strong class="form-head"><spring:message code="portalUser.head.price" /></strong>
	<div class="price-sum-wrapp">
		<div><spring:message code="price.sum" />: <span id="price"></span></div> 
		<div><spring:message code="price.dph" arguments="${(model.vat - 1 ) * 100}" />: <span id="price-vat"></span></div>
		<div class="bigger"><spring:message code="price.sumWithDph" />: <span id="price-with-dph"></span></div>
	</div>
	
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
	
	
	<strong class="form-head"><spring:message code="portaluser.profile.order.fa" /></strong>
	 
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
	

	<form:label path="userInfo.companyName" cssClass="pj-company hidden">
		<span class="label"><spring:message code="portalUser.companyName" />:</span>
		<form:input path="userInfo.companyName" cssClass="w300" maxlength="50"/>
	</form:label>
	

	<form:label path="userInfo.ico" cssClass="pj-company hidden">
		<span class="label"><spring:message code="portalUser.ico" />:</span>
		<form:input path="userInfo.ico" maxlength="8" cssClass="numeric" />
	</form:label>
	
	
	<form:label path="userInfo.dic" cssClass="pj-company hidden">
		<span class="label"><spring:message code="portalUser.dic" />:</span>
		<form:input path="userInfo.dic"  maxlength="12"/>
	</form:label>
	<div class="is-company">
		<spring:message code="portaluser.companyRegistration" />
	</div>
	<span class="pj-gray">
		<span>&nbsp;</span>
		<input type="submit" value="<spring:message code="form.submit" />" class="button pj-radius6"/>
		<span class="vop">Odesláním formuláře souhlasíte s <a href="" class="pj-link">obchodními podmínkami</a></span>
	</span>
	<form:hidden path="portalCurrency" />
	<form:hidden path="portalOrderSource" />
</form:form>
<div id="status"></div>
<div id="vat">${model.vat}</div>
<div id="loader"></div>