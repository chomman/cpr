<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title>
			<a:localizedValue object="${model.webpage}" fieldName="title" />
		</title>
		<meta name="description" content="<a:localizedValue object="${model.webpage}" fieldName="description" />" />
	</head>
	<body>
		
	<div id="bc">
		<span class="bc-info"><spring:message code="location" />:</span>  
			<a:url href="/"><spring:message code="homepage" /></a:url> &raquo;
			<span><a:localizedValue object="${model.webpage}" fieldName="name" /></span>
	</div> 

		<div id="main-content">
			 
			<article>
			<a:localizedValue object="${model.webpage}" fieldName="topText" />
			</article>	
				
				
				
				<div class="cpr-items">
						<c:forEach items="${model.submenu}" var="item">
							<div>
								<h2>
									<a:url href="${item.code}" cssClass="blue-color" >
										<a:localizedValue object="${item}" fieldName="name" />
									</a:url>
								</h2>
								<p><a:localizedValue object="${item}" fieldName="description" /></p>
							</div>
						</c:forEach>
				</div> 
				
				
			 <article>
				<a:localizedValue object="${model.webpage}" fieldName="topText" />
			 </article>
		</div>
	</body>
</html>