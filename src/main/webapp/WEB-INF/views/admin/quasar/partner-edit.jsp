<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<title>
		<c:if test="${empty partner.id}">
			<spring:message code="partner.add" />
		</c:if>
		<c:if test="${not empty partner.id}">
			<spring:message code="partner.edit" />
		</c:if>
	</title>
	<script type="text/javascript">
		$(function(){ 
			$('input[name=manager]').remotePicker({
		    	<c:if test="${not empty partner.manager}">item: {id: ${partner.manager.id}, value: '${partner.manager.firstName} ${partner.manager.lastName}'},</c:if>    	
		    	sourceUrl : getBasePath() +"admin/quasar/auditors?adminsOnly=true",
		    	enabledOnly : true,
		    	useDefaultCallBack : true
		    });
			
		});
	</script>
	<script src="<c:url value="/resources/public/js/picker.jquery.js" />"></script>
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
				 <a:adminurl href="/quasar/admin/parnter"><spring:message code="partners" /></a:adminurl>  &raquo;
				 <span>
				 	<c:if test="${empty partner.id}">
						<spring:message code="partner.add" />
					</c:if>
					<c:if test="${not empty partner.id}">
						<spring:message code="partner.edit" />
					</c:if>
				 </span>
			</div>
			<h1>
				<c:if test="${empty partner.id}">
						<spring:message code="partner.add" />
				</c:if>
				<c:if test="${not empty partner.id}">
					<spring:message code="partner.edit" />
				</c:if>
			</h1>
	
			<div id="content">
				
				<jsp:include page="navs/partner-nav.jsp" />
				
				<form:form commandName="partner" method="post" cssClass="valid" >
							
					<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				
					<c:if test="${not empty successCreate}">
						<p class="msg ok"><spring:message code="success.create" /></p>
					</c:if>
					
					             
                       <p>
                      	<label>
                      			<strong><em class="red">*</em>
                      				<spring:message code="partner.name" />:
                      			</strong>
                       		</label>
                           <span class="field">
                           	<form:input path="name" cssClass="mw500 required"  maxlength="50"/> 
                           </span>
                       </p>
					 <p>
						<label>
							<spring:message code="partner.manager" />:
						</label>
						<span class="field">
							<form:input path="manager" cssClass="mw500" maxlength="100" />
						</span>
					</p>       
					<div>
						<p class="mini-info inline">
							Manager of this partner is authorized to create/edit Training logs of workers, who are assigned to this partner
						 </p>
					</div>                          
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