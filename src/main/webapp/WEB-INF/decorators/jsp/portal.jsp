<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="@portalSecurity.hasValidRegistration()" var="hasValidRegistration"  /> 
<!DOCTYPE HTML>
<html>
	<head>
		<title><decorator:title/></title>
		<meta charset="utf-8">
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0">
		<meta name="robots"   content="index,follow"/>
		
		<link rel="shortcut icon" href="/img/icon.png" />
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/style.css" />" />
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/common.css" />" />
		
		<!--[if lt IE 9]>
			<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script src="<c:url value="/resources/portal/js/scripts.js" />"></script>
		<decorator:head />
		<script>
		  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
		  ga('create', 'UA-40352149-1', 'nlfnorm.cz');
		  ga('send', 'pageview');
		</script>
	</head>
	<body data-lang="${commonPublic.locale}">
				<div class="pj-wrapp">
			<header>
				<div class="pj-above-header">
					<div class="pj-inner">
						
						<uL class="pj-langbox">
							<li><a:url href="/">www.nlfnorm.cz</a:url></li>
							<li><a href="/">Čeština</a></li>
							<li><a href="/">English</a></li>
						</ul>
						
						<c:if test="${not empty webpageModel.loggedUser}">
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
								        <a href="#" class="btn cancel hide-loginbox" title="<spring:message code="portal.cancel" />" >
								        	<spring:message code="portal.cancel" />
								        </a>
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

						<div class="pj-search pj-radius">
							<input type="text" name="q" class="query" placeholder="Vyhledat..." />
							<a href=""></a>
						</div>

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
						<aside>
						
							<webpage:nav webpages="${webpageModel.subnav}" 
										 ulCssClass="pj-aside-nav" 
										 withSubnav="true" 
										 parentLiCssClass="pj-parent" 
										 isAuthenticated="${hasValidRegistration}" />	
										 				
							<strong class="pj-head pj -bg-light-gray">Poslední novinky</strong>
								<div class="pj-lastnews">
									<c:forEach items="${webpageModel.news}" var="i">
										<div class="pj-item">
											<span class="pj-radius"><joda:format value="${i.published}" pattern="dd.MM.yyyy" /></span>
											<strong><webpage:a webpage="${i}" cssClass="pj-link" /></strong>
											<c:if test="${fn:length(i.descriptionInLang) gt 150}">
												<p>${fn:substring(i.descriptionInLang, 0, 150)}...</p>
											</c:if>
											<c:if test="${fn:length(i.descriptionInLang) lt 151}">
												<p>${i.descriptionInLang}</p>
											</c:if>
										</div>
									</c:forEach>
								</div>
						</aside>
					</c:if>
						<section>
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

					<ul>
						<li><a href="">Kontakty</a></li>
						<li><a href="">Obchodní podmínky</a></li>
					</ul>
				</div>
			</footer>
		</div>
		
		 <div id="base" class="hidden"><c:url value="/" /></div>
	</body>
</html>