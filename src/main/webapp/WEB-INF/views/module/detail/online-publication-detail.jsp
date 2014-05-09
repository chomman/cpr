<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>
			<a:localizedValue object="${webpageModel.portalProduct}" fieldName="name" />
		</title>
		<link rel="stylesheet" href="<c:url value="/resources/admin/css/jquery.fancybox.css" />" />
		<script	src="<c:url value="/resources/admin/js/jquery.fancybox.pack.js" />"></script>
	</head>
	<script>
	$(function() {
		$(".online-pub-preview").fancybox({
	 		maxWidth	: 1000,
	 		maxHeight	: 1000,
	 		fitToView	: false,
	 		width		: '80%',
	 		height		: '80%',
	 		autoSize	: false,
	 		closeClick	: false,
	 		openEffect	: 'none',
	 		closeEffect	: 'none',
	 		type: "iframe"
	 	});
	 });	
	</script>
	<body>
 		<article>
			<h1 class="pj-head"><a:localizedValue object="${webpageModel.portalProduct}" fieldName="name" /></h1>
			<div id="article">
		
				<a:localizedValue object="${webpageModel.portalProduct}" fieldName="description" />
				
				<div class="pub-nav">
					<a href="${webpageModel.portalProduct.onlinePublication.previewUrl}" class="online-pub-preview pj-radius">
						<spring:message  code="onlniePublication.preview" />
					</a>
					
					<a href="${webpageModel.portalProduct.onlinePublication.url}" target="_blank" class="online-pub-enter pj-radius">
						<spring:message  code="onlniePublication.enter" />
					</a>
				</div>
			</div> 
		</article>
		
		
		<c:if test="${not empty webpageModel.publications}">
			<div class="pj-publications" style="margin-top:30px">
				<strong class="pj-head"><spring:message code="onlniePublication.other" /></strong>
					<ul>
						<c:forEach items="${webpageModel.publications}" var="i">
						 <c:if test="${webpageModel.portalProduct.onlinePublication.code != i.code}">	
								<li class="pj-publication pj-radius">
									<a href="<a:url href="/${i.publicationUrl}"  linkOnly="true" />" >
										<span class="pj-name">
											<a:localizedValue object="${i}" fieldName="name" />
										</span>
										<span class="pj-price"><webpage:price price="${i.priceCzk}" /></span>
										<span class="pj-ico"></span>
									</a>
								</li>
						</c:if>
						</c:forEach>
					</ul>
				
			</div>
		</c:if>
						
	</body>
</html>