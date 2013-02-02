<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<title><decorator:title/></title>
		<meta charset="utf-8" />
		<link rel="stylesheet" href="<c:url value="/resources/public/css/screen.css" />" />
		<script src="<c:url value="/resources/public/js/jquery-1.9.0.min.js" />"></script>
		<decorator:head/>
	</head>
	<body>
		<div id="wrapper">
			<!-- HEADER -->
			<div class="border-top"></div>
			<header class="page-width">
				<a href="" id="logo"></a>
					<strong>
						<span class="is">Informační systém</span>
						<span class="is-name">Uvádění <em class="blue-color">stavebních výrobků</em> na trh</span>
						<span class="itc-name">ITC - Institut pro testování a certifikace</span>
					</strong>
			</header>


			<!-- NAVIGATION -->
			<nav>
				<ul class="page-width">
					<c:forEach items="${commonPublic.mainMenu}" var="webpage">
						<li><a href="<c:url value="${webpage.code}" />">${webpage.name}</a></li>
					</c:forEach>
					<!-- <li><a href="">O portálu</a></li>
					<li><a href="">Aktuality</a></li>
					<li><a href="">Uvadeni vyrobku</a></li>
					<li><a href="">Prehled subjektu</a></li>
					<li><a href="">Vygenerovat prohlaseni</a></li>
					<li><a href="">Kontakty</a></li> -->
				</ul>
			</nav>
			<!-- CONTENT -->
			<div id="content">
				
				<decorator:body />
				
			</div>
			 <div class="push"></div>	
		</div>

		<!-- FOOTER -->
		<footer>
			<div id="footer" class="page-width">
				<a href="http://www.itczlin.cz/cz/" title="ITC - Institut pro testování a certifikace" class="itc-logo"></a>
				<p class="itc-name">
					Iformační systém CPR<br />
					<a href="http://www.itczlin.cz/cz/" title="ITC - Institut pro testování a certifikace">ITC - Institut pro testování a certifikace</a>
				</p>
				<a class="admin" href="<c:url value="/admin/login" />" title="Přihlášení do administrace systému" >Administrace systému</a>
			</div>
		</footer>

	</body>
</html>