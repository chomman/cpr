<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>nlfnorm.cz</title>
		<meta charset="utf-8">
		<meta name="robots"   content="noindex,follow"/>
		
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/style.css" />" />
		
		<!--[if lt IE 9]>
			<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/ui/jquery-ui-1.10.4.custom.css" />" />
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script src="<c:url value="/resources/portal/js/jquery-ui-1.10.4.custom.min.js" />"></script>
		<script src="<c:url value="/resources/portal/js/scripts.js" />"></script>
		<script>
		  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		  ga('create', 'UA-40352149-1', 'nlfnorm.cz');ga('send', 'pageview');
		</script>
	</head>
	<body data-lang="${commonPublic.locale}">
			<jsp:include page="../module/registration-form.jsp" />	
		 <div id="base" class="hidden"><c:url value="/" /></div>
	</body>
</html>