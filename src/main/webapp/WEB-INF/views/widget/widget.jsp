<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Widget - nlfnorm.cz</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta name="robots"   content="noindex,follow" />
		<meta name="viewport" content="width=device-width" />
		
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/common.css" />" />
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/style.css" />" />
		<link rel="stylesheet" href="<c:url value="/resources/widget/widget.css" />" />
		
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
	<body class="pj-widget">
			
			<c:if test="${webpageModel.type == 1 or webpageModel.type == 2 or webpageModel.type == 4}">
				<ul class="w-nav">
					<li ${webpageModel.type == 1 ? 'class="active"'  : '' }>
						<a href="<a:url href="/widget/registrace" linkOnly="true" />${webpageModel.params}">
							<webpage:filedVal webpage="${webpageModel.registration}" fieldName="name" />
						</a>
					</li>
					<c:forEach items="${webpageModel.nav}" var="i">
					<li ${webpageModel.type == 2 and webpageModel.webpage.id eq i.id ? 'class="active"'  : '' }>
						<a:url href="/widget/${i.id}${webpageModel.params}">
							<webpage:filedVal webpage="${i}" fieldName="name" />
						</a:url>
					</li>
					</c:forEach>
				</ul>
				
				<c:if test="${webpageModel.type == 1}">
					<jsp:include page="../module/registration-form.jsp" />
				</c:if>
				
				
				<c:if test="${webpageModel.type == 2}">
					<article class="pj-widget-artice">
						<h3 class="pj-widget"><webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" /></h3>
						<webpage:filedVal webpage="${webpageModel.webpage}" fieldName="content" />
					</article> 
				</c:if>	
				
				<c:if test="${webpageModel.type == 4}">
					<article class="pj-widget-artice">
						<h3 class="pj-widget"><a:localizedValue object="${webpageModel.portalProduct}" fieldName="name" /></h3>
						<a:localizedValue object="${webpageModel.portalProduct}" fieldName="description" />						
						<c:if test="${webpageModel.portalProduct.portalProductType.id == 2}">
							<div class="pub-nav">
								<a href="${webpageModel.portalProduct.onlinePublication.previewUrl}" target="_blank" class="online-pub-preview pj-radius">
									<spring:message  code="onlniePublication.preview" />
								</a>
								
								<a href="<a:url href="/widget/registrace" linkOnly="true" />${webpageModel.params}&amp;pid=${webpageModel.portalProduct.id}" class="online-pub-extend pj-radius">
									<spring:message  code="onlinePublication.orderProduct" />
								</a>
							</div>
						</c:if>
					</article> 
				</c:if>	
			</c:if>
			
			<c:if test="${webpageModel.type == 3}">
					<div class="pj-widget-news">
						<c:forEach items="${webpageModel.news}" var="i">
							<div class="pj-item">
								<span class="pj-radius pj-date"><joda:format value="${i.published}" pattern="dd.MM.yyyy" /></span>
								<strong><webpage:a webpage="${i}" target="_blank" /></strong>
								<p>
								<c:if test="${not empty i.descriptionInLang}">
									<c:if test="${fn:length(i.descriptionInLang) gt webpageModel.descrLength}">
										${fn:substring(i.descriptionInLang, 0, webpageModel.descrLength)}...
									</c:if>
									<c:if test="${fn:length(i.descriptionInLang) lt webpageModel.descrLength}">
										${i.descriptionInLang}
									</c:if>
								</c:if>
								<c:if test="${ empty i.descriptionInLang}">
									${nlf:crop(i.contentInLang, webpageModel.descrLength)} 												
								</c:if>
								</p>
							</div>			
						</c:forEach>
					</div>							
			</c:if>	
			
		 <div id="base" class="hidden"><c:url value="/" /></div>
		 <script src="<c:url value="/resources/widget/iframeResizer.contentWindow.min.js" />"></script>
	</body>
</html>