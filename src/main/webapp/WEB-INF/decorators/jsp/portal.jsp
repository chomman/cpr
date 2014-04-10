<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>Informacni portal</title>
		<meta charset="utf-8">
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0">
		<meta name="robots"   content="index,follow"/>
		
		<link rel="shortcut icon" href="/img/icon.png" />
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/style.css" />" />
		<link rel="stylesheet" href="<c:url value="/resources/portal/css/common.css" />" />

		<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro' rel='stylesheet' type='text/css'>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script src="<c:url value="/resources/portal/js/scripts.js" />"></script>
	</head>
	<body>
		<div class="pj-wrapp">
			<header>
				<div class="pj-above-header">
					<div class="pj-inner">
						
						<uL class="pj-langbox">
							<li><a href="/">www.nlfnorm.cz</a></li>
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
				</div>
				<div class="pj-subnav pj-bg-light-gray">
				</div>
				<div class="pj-border"></div>
			</nav>

			<div id="content">
			</div>


			<footer>

			</footer>
		</div>
	</body>
</html>