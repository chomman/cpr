<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>Google Analytics statistiky</title>
	<script>
		$(function() {
			showLoader();
			setTimeout(function(){
				$( "iframe" ).attr("src", "//nlfnorm.peterjurkovic.sk/");
				$( "iframe" ).load(function(){
			        $('#loader').remove();
			    });
			},100);
		});
	</script>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/portal-nav.jsp" />
		</div>	
		<div id="right">
		
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				 <span>Google Analytics statistiky</span> 
			</div>
			<h1>Google Analytics statistiky</h1>
	
			<div id="content">
				<iframe width="100%" height="1400px" frameBorder="0" ></iframe>
				<div id="loader" class="webpage"></div>
			</div>	
			
		</div>
		<div class="clear"></div>	
	</div>

</body>
</html>