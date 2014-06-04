<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></title>
		<script src="<c:url value="/resources/admin/tinymce/tinymce.min.js" />"></script>
		<script src="<c:url value="/resources/admin/js/wisiwig.init.js" />"></script>
	</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/cpr-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr" />"><spring:message code="menu.cpr" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr/standards" />"><spring:message code="menu.cpr.norm" /></a> &raquo;
			 <span><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></span>
		</div>
		<h1><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></h1>

		<div id="content">
							
					
				<jsp:include page="include/standard-subnav.jsp" />
								
				<div id="tabs">
					
					
					<jsp:include page="include/cpr-standard-menu1.jsp" />
					<jsp:include page="include/cpr-standard-menu2.jsp" />
					<jsp:include page="include/cpr-standard-menu3.jsp" />
					<jsp:include page="include/cpr-standard-menu4.jsp" />
					<jsp:include page="include/cpr-standard-menu5.jsp" />
					<strong class="active-tab-head"><spring:message code="cpr.standard.tab.6" /></strong>
					
					<!-- ACTIVE TAB -->
					<div class="active-tab">
						
					<script type="text/javascript"> 
				    $(function() { 
				 
				    	var errMsg = "Nastala neočekávaná chyba, operaci zkuste zopakovat.";
				        $('form').submit(function(e) {
				        	e.preventDefault();
				            var form = $( this ),
				                url = form.attr('action'),
				                data = renameArr(form.serializeArray());
				            var mce =  tinyMCE.get('text');
				            	mce.setProgressState(1); // Show progress
								data.text = mce.getContent();
				            $.ajax({
				                url : url,
				                type : "POST",
				                traditional : true,
				                contentType : "application/json",
				                dataType : "json",
				                data : JSON.stringify(data),
				                success : function (response) {
				                	if(response.status == "SUCCESS"){
					                    showStatus({err: 0, msg: "Úspěšně aktualizováno"});
				                	}else{
				                		 showStatus({err: 1, msg: errMsg });
				                	}
				                	mce.setProgressState(0);
				                },
				                error : function(xhr, status, err) {
				                	showStatus({err: 1, msg: msg});
				                    mce.setProgressState(0);
				                }
				            });
				
				            return false;
				        });
				    });

					</script>
					
					
					<c:url value="/admin/cpr/standard/edit/${standardId}/describe" var="formUrl"/>
					<form:form commandName="standard" method="post" action="${formUrl}"  >
						
						<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
						
						<c:if test="${not empty successCreate}">
							<p class="msg ok"><spring:message code="success.create" /></p>
						</c:if>
						<p>
                        	<label>
                        		<spring:message code="cpr.standard.text" />
                        	</label>
                            <span class="field">  
                          	<form:textarea path="text"  cssClass="wisiwig bigEditorSize" />
                          </span>
                        </p>
                        <form:hidden path="standardId" />
                        <form:hidden path="czechName" />
                        <form:hidden path="id"  />
                        <p class="button-box">
                        	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                        </p>
					</form:form>
					
			
						
					</div> <!-- END ACTIVE TAB -->
					
				</div>	<!-- END TABs -->
		
			<span class="note"><spring:message code="form.required" htmlEscape="false" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>