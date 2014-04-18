<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
	</head>
	<body>
		<section>
				<div class="pj-scopes ">
						<strong class="pj-head pj -bg-light-gray "><webpage:filedVal webpage="${scopes}" fieldName="title" /></strong>
						
						<c:forEach items="${scopes.publishedChildrens}" var="i">
							<div class="pj-item">
								<div class="pj-wrapp">
									<a href="<webpage:link webpage="${i}"  />" class="pj-img" <c:if test="${empty i.avatar}">style="background-image:url(<c:url value="/resources/public/img/nothumb.png" />)"</c:if> >
										<c:if test="${not empty i.avatar}">
											<img src="<c:url value="/image/r/180/avatars/${i.avatar}" />" alt="${i.defaultName}" />
										</c:if>
									</a>
									<div>	
										<h3><webpage:a webpage="${i}" cssClass="pj-link" /></h3>
										<c:if test="${fn:length(i.titleInLang) lt 50 and not empty i.descriptionInLang}">
											<p>${fn:substring(i.descriptionInLang, 0, 120 - fn:length(i.titleInLang))}...</p>
										</c:if>
										
									</div>
									<c:if test="${not empty i.publishedChildrens}" >
										<ul class="pj-scope-nav">
											<li class="pj-subcat pj-head">
												<spring:message code="subcategories" />
											</li>
											<c:forEach items="${i.publishedChildrens}" var="j">
												<li><webpage:a webpage="${j}" /></li>
											</c:forEach>
										</ul>
									</c:if>
								</div>
							</div>
						</c:forEach>
						<div class="clear"></div>
				</div>
				
				<c:if test="${not empty publications}">
					<div class="pj-publications">
						<strong class="pj-head"><webpage:filedVal webpage="${publications}" fieldName="name" /></strong>
	
						<p>
							<webpage:filedVal webpage="${publications}" fieldName="content" />
						</p>
				
						<ul>
							<c:forEach items="${publications.publishedChildrens}" var="i">
								<li class="pj-publication pj-radius">
								<a href="<webpage:link webpage="${i}" />">
									<span class="pj-name">
										<webpage:filedVal webpage="${i}" fieldName="name" />
									</span>
									<span class="pj-price"><webpage:filedVal webpage="${i}" fieldName="description" /></span>
									<span class="pj-ico"></span>
								</a>
							</li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
			</section>
						
	</body>
</html>