<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>nlfnorm.cz</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="robots"   content="noindex,follow" />
		<meta name="viewport" content="width=device-width" />
		
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/common.css" />" />
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/style.css" />" />
		
		<!--[if lt IE 9]>
			<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/ui/jquery-ui-1.10.4.custom.css" />" />
		<c:if test="${not empty webpageModel.css }">
		<link rel="stylesheet" href="${webpageModel.css}" />
		</c:if>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
		<script src="<c:url value="/resources/portal/js/jquery.messaging.js" />"></script>
		<script src="<c:url value="/resources/portal/js/jquery-ui-1.10.4.custom.min.js" />"></script>
		<script src="<c:url value="/resources/portal/js/scripts.js" />"></script>
		<jsp:include page="/WEB-INF/views/include/ga.jsp" />
	</head>
	<body>
		
				<ul class="w-nav">
					<li ${webpageModel.type == 1 ? 'class="active"'  : '' }>
						<a href="">
							<webpage:filedVal webpage="${webpageModel.registration}" fieldName="name" />
						</a>
					</li>
					<c:forEach items="${webpageModel.nav}" var="i">
					<li ${webpageModel.type == 2 and webpageModel.webpage.id eq i.id ? 'class="active"'  : '' }>
						<a:url href="/widget/${i.id}">
							<webpage:filedVal webpage="${i}" fieldName="name" />
						</a:url>
					</li>
					</c:forEach>
				</ul>
			
			<c:if test="${webpageModel.type == 1}">
				<jsp:include page="../module/registration-form.jsp" />
			</c:if>
			
			<c:if test="${webpageModel.type == 2}">
				<h1 class="pj-head"><webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" /></h1>
				<div id="article">
					<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="content" />
				</div> 
			</c:if>	
			
			
		 <div id="base" class="hidden"><c:url value="/" /></div>
		 <script src="<c:url value="/resources/widget/iframeResizer.contentWindow.min.js" />"></script>
	</body>
</html>