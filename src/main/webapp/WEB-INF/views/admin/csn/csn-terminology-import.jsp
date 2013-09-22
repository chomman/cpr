<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="csn.terminology.import" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
		<jsp:include page="csn-nav.jsp" />
	</div>
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/csn" />"><spring:message code="menu.csn" /></a> &raquo;
			 <span><spring:message code="csn.terminology.import" /></span>
		</div>
		<h1><spring:message code="csn.terminology.import" /></h1>

		<div id="content">
			
		
		
			<c:url value="/admin/csn/terminology/import" var="formUrl"/>
			<form:form  modelAttribute="uploadForm" method="post" action="${formUrl}"  cssClass="csnFileUpload" enctype="multipart/form-data">	


				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				 
				<p>
					<label>
						<spring:message code="csn.terminology.import.chooseFile" />:
					</label>
					<span class="field">
						<input type="file" name="fileData" />
						<input type="submit" class="button radius" value="<spring:message code="form.file.upload"  /> &raquo;" />
					</span>	
              	</p>
			
			</form:form>
			
			<h4>Informace k importu</h4>
			
			<p class="msg info">
				V dokumentu musí být vložen oddělovač "<strong>####</strong>", který identifikuje začátek a konec terminologie. Pomocí tohoto oddělovače se odstraní nepotřebná část dokumentu a výrazně se tak redukuje potřebný paměťový prostor.
			</p>
			
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>