<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:url value="/admin/portal/user/${user.id}/validity" var="formUrl" /> 
<form:form cssClass="valid" action="${formUrl}" method="get" >
	
	<c:if test="${not empty validityChanged}">
		<p class="msg ok"><spring:message code="success.create" /></p>
	</c:if>
									
	<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
	
	<p class="form-head"><spring:message code="admin.portalUser.validity" /></p>
	   <p>
       	<label>
       		<strong><em class="red">*</em>
       			<spring:message code="admin.portalUser.validity" /> do:  
       		</strong>
       	</label>
           <span class="field">
           	<input name="date" type="text" class="required date" value='<joda:format value="${ model.user.registrationValidity}" pattern="dd.MM.yyyy"/>' />
           <span class="mini-info inline">
           		<spring:message code="admin.portalUser.validity.info" />Ulož
           	</span>
           </span>
       </p>
     <input type="hidden" name="id" value="0" />
      <p class="button-box">
      	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
      </p>
      
</form:form>	

<div class="hbox">
	<h2> <spring:message code="portalProductType.publication" /></h2>
</div>	
	<c:if test="${not empty model.user.onlinePublications}">
		<ul class="pj-online-pub">
		<c:forEach items="${ model.user.onlinePublications}" var="i" varStatus="s">
			<li class="${s.index % 2 == 0 ? 'even' : 'odd'}">
				<span class="pj-pub-code">${i.portalProduct.onlinePublication.code}</span>
				<span class="pj-pub-name">${i.portalProduct.czechName}</span>
				<span class="pj-pub-val">
					<form  action="${formUrl}" method="get" class="valid"> 
						<input name="date" type="text" class="required date" value='<joda:format value="${i.validity}" pattern="dd.MM.yyyy"/>' />
						<input type="submit" class="button" value="<spring:message code="form.save" />" />
						<input type="hidden" name="id" value="${i.id}" />
					</form>
				</span>
			</li>
		</c:forEach>
		</ul>
	</c:if>
	<c:if test="${empty model.user.onlinePublications}"> 
		<p class="msg alert"><spring:message code="admin.portalUser.noOnlinePublications" arguments="${model.user.firstName};${model.user.lastName}" argumentSeparator=";" /></p>
	</c:if>
