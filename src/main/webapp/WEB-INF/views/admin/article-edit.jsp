<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="article.add" /></title>
	<script src="<c:url value="/resources/admin/tiny_mce/tiny_mce.js" />"></script>
</head>
<body>
	<div id="wrapper">
	<div id="left">
		<jsp:include page="include/article-nav.jsp" />
	</div>
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/articles" />"><spring:message code="dashboard.news.title" /></a> &raquo;
			 <span><spring:message code="article.add" /></span>
		</div>
		<h1><spring:message code="article.add" /></h1>

		<div id="content">
			<c:if test="${not empty article.createdBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.created" /></td>
						<td class="val">${article.createdBy.firstName} ${article.createdBy.lastName}</td>
						<td class="val"><joda:format value="${article.created}" pattern="dd.MM.yyyy / hh:mm"/></td>
					</tr>
					<c:if test="${not empty article.changedBy}">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${article.changedBy.firstName} ${article.changedBy.lastName}</td>
						<td class="val"><joda:format value="${article.changed}" pattern="dd.MM.yyyy / hh:mm"/></td>
					</tr>
					</c:if>
				</table>
			</c:if>
			
			<script type="text/javascript"> 
				$(function() { 
					initWISIWIG("610", "450"); 
					var elem = $("#chars");
					$("#header").limiter(255, elem);
					$('form').submit(function(e) {
			        	e.preventDefault();
			            var form = $( this ),
			                url = form.attr('action'),
			                data = renameArr(form.serializeArray());
			            console.log(data);
			            var mce =  tinyMCE.get('articleContent');
			            	mce.setProgressState(1); // Show progress
							data.text = mce.getContent();
						console.log(data);
			            $.ajax({
			                url : url,
			                type : "POST",
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
			                	showStatus({err: 1, msg: "Nastala neočekávaná chyba, operaci zkuste zopakovat."});
			                    mce.setProgressState(0);
			                }
			            });
			            return false;
			        });
				});
			</script>
			
			
			<c:url value="/admin/article/edit/${articleId}" var="formUrl"/>					
			<form:form commandName="article" method="post" action="${formUrl}" cssClass="valid"  >
				
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				
				 <p class="form-head"><spring:message code="article.head.setting.info" /><p>
                <p>
                	<label>
                		<strong><em class="red">*</em>
                			<spring:message code="article.released" />:
                		</strong>
                	</label>
                    <span class="field">
                    	<form:input path="released"  cssClass="date required" />
                    </span>
                </p>
                <p>
				    <label title="<spring:message code="publish.descr" />" class="tt">
				 		<spring:message code="publish" />
				 	</label>
				     <span class="field">  
				     	<form:checkbox path="enabled" />
				     </span>
				 </p>
				 <p class="form-head"><spring:message code="article.head.content.info" /><p>
				 <p>
                	<label>
                		<strong><em class="red">*</em>
                			<spring:message code="article.title" />:
                		</strong>
                	</label>
                    <span class="field">
                    	<form:input path="title" maxlength="150" cssClass="mw500 required" />
                    </span>
                </p>
				 <p>
				    <label>
				 		<spring:message code="article.header" />
				 	</label>
				     <span class="field counter">  
				     	<form:textarea path="header" cssClass="header" />
				     	<span id="chars"></span>
				     </span>
				 </p>
				 
				 <p>
				    <label>
				 		<spring:message code="article.content" />
				 	</label>
				     <span class="field">  								
				     	<form:textarea path="articleContent" cssClass="mceEditor " />
				     </span>
				 </p>
                
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