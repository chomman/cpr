<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title><webpage:filedVal webpage="${webpageModel.webpage}" fieldName="title" /><decorator:title/></title>
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
		
		<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro' rel='stylesheet' type='text/css'>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script src="<c:url value="/resources/portal/js/scripts.js" />"></script>
		<decorator:head />
	</head>
	<body>
				<div class="pj-wrapp">
			<header>
				<div class="pj-above-header">
					<div class="pj-inner">
						
						<uL class="pj-langbox">
							<li><a:url href="/">www.nlfnorm.cz</a:url></li>
							<li><a href="/">Čeština</a></li>
							<li><a href="/">English</a></li>
						</ul>

						<uL class="pj-login">
							<li><a href="/">Registrovat</a></li>
							<li><a href="/">Přihlásit</a></li>
						</ul>

					</div>
				</div>
				<div class="pj-header">	
					<div class="pj-inner">
						<a href="/" id="logo" >
							<span class="blind">Zákony a normy pro plastikářský a gumárenský průmysl</span>
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
							<webpage:nav webpages="${webpageModel.mainnav}" ulCssClass="first-child" withSubnav="true" />
						</div>
				</div>
				<div class="pj-subnav pj-bg-light-gray">
				</div>
				<div class="pj-border"></div>
			</nav>

			<div id="content">
				<div class="pj-inner ">	
						<div class="pj-bc">
							<a href="">Home</a> &raquo;
							<span>Novinky</span>
						</div>
						
						<aside>
							<webpage:nav webpages="${webpageModel.subnav}" ulCssClass="pj-aside-nav" />
													
							<strong class="pj-head pj -bg-light-gray">Poslední novinky</strong>

								<div class="pj-lastnews">
									<div class="pj-item">
										<span class="pj-radius">20.04.2014</span>
										<strong><a class="pj-link" href="">Směrnice, které respkektují principy nového legislativního rámce</a></strong>
										<p>Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. </p>
									</div>

									<div class="pj-item">
										<span class="pj-radius">19.04.2014</span>
										<strong><a class="pj-link" href="">Respkektují principy nového legislativního rámce</a></strong>
										<p>Loremasdfasdfasdf Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. </p>
									</div>

									<div class="pj-item">
										<span class="pj-radius">20.04.2014</span>
										<strong><a class="pj-link" href="">Směrnice, které respkektují principy nového legislativního rámce</a></strong>
										<p>Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. </p>
									</div>

									<div class="pj-item">
										<span class="pj-radius">19.04.2014</span>
										<strong><a class="pj-link" href="">Respkektují principy nového legislativního rámce</a></strong>
										<p>Loremasdfasdfasdf Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. </p>
									</div>

								</div>
						</aside>



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
	</body>
</html>