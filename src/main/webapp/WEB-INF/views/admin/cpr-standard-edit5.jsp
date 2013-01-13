<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.standard.edit" arguments="${standard.standardId}" /></title>
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
							
					
					<ul class="sub-nav">
						<li><a href="<c:url value="/admin/cpr/standards"  />"><spring:message code="cpr.standard.view" /></a></li>
						<li><a href="<c:url value="/admin/cpr/standard/add"  />"><spring:message code="cpr.standard.add" /></a></li>
					</ul>
								
				<div id="tabs">
					<a class="tab tt"  
						href="<c:url value="/admin/cpr/standard/edit/${standardId}" />" >
						<span>1</span> - <spring:message code="cpr.standard.tab.1" />
					</a>
					<a class="tab tt" title="<spring:message code="cpr.standard.tab.2.title" />" 
							href='<c:url value="/admin/cpr/standard/edit/${standardId}/requirements?country=1" />' >
							<span>2</span> - <spring:message code="cpr.standard.tab.2" />
						</a>
					<a class="tab tt" title="<spring:message code="cpr.standard.tab.3.title" />" 
						href="<c:url value="/admin/cpr/standard/edit/${standardId}/notifiedbodies" />" >
						<span>3</span> - <spring:message code="cpr.standard.tab.3" />
					</a>
					<a class="tab tt" title="<spring:message code="cpr.standard.tab.4.title" />" 
							href="<c:url value="/admin/cpr/standard/edit/${standardId}/other" />" >
							<span>4</span> - <spring:message code="cpr.standard.tab.4" />
					</a>
					
					<strong class="active-tab-head"><span>5</span> - <spring:message code="cpr.standard.tab.5" /></strong>
					
					<!-- ACTIVE TAB -->
					<div class="active-tab">
					
					<script type="text/javascript"> 
				    $(function() { 
				        $('form').submit(function(e) {
				        	e.preventDefault();
				            var form = $( this ),
				                url = form.attr('action'),
				                data = renameArr(form.serializeArray());
				            var mce =  tinyMCE.get('text');
				            	mce.setProgressState(1); // Show progress
								data.text = mce.getContent();
				            console.log(data);
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
				                		 showStatus({err: 1, msg: "Nastala neočekávaná chyba, operaci zkuste zopakovat."});
				                	}
				                	mce.setProgressState(0);
				                },
				                error : function(xhr, status, err) {
				                	howStatus({err: 1, msg: ERROR_MESSAGE});
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
                        		<spring:message code="cpr.standard.tab.5" />
                        	</label>
                            <span class="field">  
                          	<form:textarea path="text"  cssClass="mceEditor bigEditorSize" />
                          </span>
                        </p>
                        <form:hidden path="standardId" />
                        <form:hidden path="standardName" />
                        <form:hidden path="id"  />
                        <p class="button-box">
                        	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                        </p>
					</form:form>
					
			
						
					</div> <!-- END ACTIVE TAB -->
				</div>	<!-- END TABs -->
		
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>