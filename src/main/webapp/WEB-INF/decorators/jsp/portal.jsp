<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ page trimDirectiveWhitespaces="true" %>
<sec:authorize access="@portalSecurity.hasValidRegistration()" var="hasValidRegistration"  /> 
<!DOCTYPE HTML>
<html>
	<head>
		<title><decorator:title/> - nlfnorm.cz</title>
		<meta charset="utf-8">
		
		<c:if test="${not empty webpageModel.webpage.descriptionInLang}">
		<meta name="description" content="${ nlf:crop(webpageModel.webpage.descriptionInLang, 200)}" />
		</c:if>
		<meta name="keywords" content="${webpageModel.webpage.jointedTags}" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0">
		<meta name="robots"   content="index,follow"/>
		
		<link rel="shortcut icon" href="<c:url value="/resources/portal/img/favico.png" />" />
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/style.css" />" />
		<link rel="stylesheet" media="print" href="<c:url value="/resources/portal/css/print.css" />" />
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/common.css" />" />
		<c:if test="${not empty isPreview and isPreview}">
			<link rel="stylesheet" href="<c:url value="/resources/portal/css/preview.css" />" />
		</c:if>
		<!--[if lt IE 10]> 
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/ie.css" />" />	
		<![endif]-->
		<!--[if lt IE 9]>
		<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/ui/jquery-ui-1.10.4.custom.css" />" />
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
		<script src="<c:url value="/resources/portal/js/jquery-ui-1.10.4.custom.min.js" />"></script>	
		<script src="<c:url value="/resources/portal/js/${commonPublic.locale}.messages.js" />"></script>
		<script src="<c:url value="/resources/portal/js/jquery.localize.js" />"></script>
		<script src="<c:url value="/resources/portal/js/scripts.js" />"></script>
		<decorator:head />
		<jsp:include page="/WEB-INF/views/include/ga.jsp" />
	</head>
	<body data-lang="${commonPublic.locale}">
				<div class="pj-wrapp">
			<header>
				<div class="pj-above-header">
					<div class="pj-inner">
						
						<uL class="pj-langbox">
							<li><a href="<a:url href="/" linkOnly="true" />">www.nlfnorm.cz</a></li>
							<li ${commonPublic.locale == 'cs' ? 'class="current"' : ''}>
								<a href="" data-lang="cs"><spring:message code="cs" /></a>
							</li>
							 <c:if test="${fn:length(webpageModel.webpage.localized) > 1 }">
								<li ${commonPublic.locale == 'en' ? 'class="current"' : ''}>
									<a href="" data-lang="en">	
										<spring:message code="en" />
									</a>
								</li>
							</c:if>
						</ul>
						
						<c:if test="${not empty webpageModel.loggedUser}">
							<c:if test="${not empty webpageModel.loggedUser.registrationValidity}">
								<c:set var="regValidity" value="${nlf:daysLeft(webpageModel.loggedUser.registrationValidity)}" />
								<c:if test="${regValidity > 0 and regValidity < 21 }">
									<p class="pj-alert pj-radius"> 
										<spring:message code="portaluser.leftDays" />: <strong>${regValidity}</strong> <spring:message code="portaluser.day" />
										
										<a href="<a:url href="${webpageModel.profileUrl}" linkOnly="true"  />/new-order?pid=554">
											<spring:message code="portaluser.extendValidity" />
										</a>
									</p> 
								</c:if>
								<c:if test="${regValidity < 0}">
									<p class="pj-alert red pj-radius">
										<spring:message code="portaluser.invalid" />:  <strong><joda:format value="${webpageModel.loggedUser.registrationValidity}" pattern="dd.MM.yyyy"/></strong>
										
										<a href="<a:url href="${webpageModel.profileUrl}" linkOnly="true"  />/new-order?pid=554">
											<spring:message code="portaluser.extendValidity" />
										</a> 
									</p> 
								</c:if>
								
							</c:if>
						
							<uL class="pj-login">
								<li><a href="<a:url href="${webpageModel.profileUrl}" linkOnly="true"  />" class="user ico">${webpageModel.loggedUser.firstName} ${webpageModel.loggedUser.lastName}<span class="ic ic-user"></span></a></li>
								<li><a href="<c:url value="/j_logout?${webpageModel.portalParam}=1" />" class="logout" ><spring:message code="portal.logout" /></a></li>
							</ul>
						</c:if>
						
						<c:if test="${empty webpageModel.loggedUser }">
							<uL class="pj-login <c:if test='${not empty loginError}'>hidden</c:if>">
								<li>
									<webpage:a webpage="${webpageModel.registrationPage}" />
								</li>
								<li><a href="#" class="show-loginbox"><spring:message code="portal.login" /></a></li>
							</ul>
							
							<div id="pj-login-box" class="<c:if test='${empty loginError}'>hidden</c:if> <c:if test='${not empty loginError}'>has-errors</c:if>" >
								 <form action="<c:url value="/j_spring_security_check?${webpageModel.portalParam}=1" />" method="post">
						              <div class="error <c:if test='${empty loginError}'>hidden</c:if>">
								           <spring:message  code="portal.login.error"/>
								       </div>  
								       
								       <div class="bx">
									       	<label for="j_username"><spring:message code="portal.login.email" /></label>
									       	<input type="text" class="filed text" name="j_username" value="<c:if test="${not empty username}">${username}</c:if>"/>
								       </div>
								       <div class="bx">
								       		<label for="j_password"><spring:message code="portal.login.pass" /></label>
								       		<input type="password" class="filed text" name="j_password" value=""/>
								       </div>
										
								       	<input type="submit" value="<spring:message code="portal.login" />" class="button pj-radius6" />
								        <a href="#" class="btn cancel hide-loginbox pj-radius-bottom-right pj-radius-bottom-left" title="<spring:message code="portal.close" />" >
								        	<spring:message code="portal.close" />
								        </a>
								        <a href="<webpage:link webpage="${webpageModel.rootwebpage}" />/zapomenute-heslo" class="pj-forgotten-pass" >
								        	<spring:message code="forgottenPassowrd" />
								        </a>
								        
										<span class="remeber">
											<label><spring:message code="rememberMe" /></label>
											<input class="checkbox" type="checkbox" name="_spring_security_remember_me" />
										</span>
							      </form>
							</div>
						</c:if>

					</div>
				</div>
				<div class="pj-header">	
					<div class="pj-inner">
						<a href="<webpage:link webpage="${webpageModel.rootwebpage}" />" id="logo" >
							<span class="blind">
								<spring:message code="portal.hidden.title" />
							</span>
						</a>

						<form enctype="application/x-www-form-urlencoded" class="pj-search pj-radius" action="<webpage:link webpage="${webpageModel.rootwebpage}" />/search">
							<input type="text" name="q" class="query" placeholder="<spring:message code="portal.search" />..." />
							<a href=""></a>
						</form>

					</div>
				</div>
			</header>
			<nav>
				<div class="pj-mainnav pj-bg-light-gray">
						<div class="pj-inner">
							<webpage:nav webpages="${webpageModel.mainnav}" 
										 ulCssClass="first-child" 
										 withSubnav="true" 
										 parentLiCssClass="pj-parent" 
										 isAuthenticated="${hasValidRegistration}" />
						</div>
				</div>
				<div class="pj-subnav pj-bg-light-gray">
				</div>
				<div class="pj-border"></div>
			</nav>

			<div id="content">
				<div class="pj-inner ">
					<c:if test="${empty profileTab}">
						<webpage:breadcrumb webpage="${webpageModel.webpage}" bcCssClass="pj-bc"/>
						<c:if test="${not webpageModel.webpage.fullWidth}">
						<aside>
						
							<webpage:nav webpages="${webpageModel.subnav}" 
										 ulCssClass="pj-aside-nav" 
										 withSubnav="true" 
										 parentLiCssClass="pj-parent" 
										 isAuthenticated="${hasValidRegistration}" />	
										 				
							<strong class="pj-head pj -bg-light-gray">
								<spring:message code="lastNews" />
							</strong>
								<div class="pj-lastnews">
									<c:forEach items="${webpageModel.news}" var="i">
										<div class="pj-item">
											<span class="pj-radius"><joda:format value="${i.published}" pattern="dd.MM.yyyy" /></span>
											<strong><webpage:a webpage="${i}" cssClass="pj-link" /></strong>
											<p>
											<c:if test="${not empty i.descriptionInLang}">
												<c:if test="${fn:length(i.descriptionInLang) gt 150}">
													${fn:substring(i.descriptionInLang, 0, 150)}...
												</c:if>
												<c:if test="${fn:length(i.descriptionInLang) lt 150}">
													${i.descriptionInLang}
												</c:if>
											</c:if>
											<c:if test="${empty i.descriptionInLang}">
												${nlf:crop(i.contentInLang, 150)} 												
											</c:if>
											</p>
										</div>
									</c:forEach>
								</div>
						</aside>
						</c:if>
					</c:if>
						<section ${webpageModel.webpage.fullWidth ? 'class="pj-fullWidth"' : ''}>
							<decorator:body/>
						</section>
						<div class="clear"></div>
				</div>
			</div>

			<footer>
				<div class="pj-inner ">	
					<p class="pj-contact">
						<strong> INSTITUT PRO TESTOVÁNÍ A CERTIFIKACI, a. s.</strong>
						<span>
							třída Tomáše Bati 299, Louky <br />
							763 02 Zlín, Česká republika
						</span>
					</p>
					
					<webpage:nav webpages="${webpageModel.footerNav}"
								 isAuthenticated="${hasValidRegistration}" />
				</div>
			</footer>
		</div>
		
		 <div id="base" class="hidden"><c:url value="/" /></div>
	</body>
</html>