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
						<div class="pj-inner">
							<ul class="first-child">
								<li>
									<a class="pj-current"  href="">O portálu</a>
									<ul>
										<li>
											<a href="">Proč se registrovat</a>
										</li><li>
											<a href="">Pro koho je určený</a>
										</li><li>
											<a href="">Obchodní podmínky</a>
										</li><li>
											<a href="">Kontakty</a>
										</li><li>
											<a href="">Obecná bezpečnosť</a>
										</li>
									</ul>
								</li><li>
									<a href="">Uvádení výrobků</a>
									<ul>
										<li>
											<a href="">Základní informace</a>
										</li><li>
											<a href="">Stavení výrobky</a>
										</li>
									</ul>
								</li><li>
									<a href="">Stanovené výrobky</a>
									<ul>
										<li>
											<a href="">informace</a>
										</li><li>
											<a href="">Stanovené výrobky</a>
										</li>
										<li>
											<a href="">informace</a>
										</li>
									</ul>
								</li><li>
									<a href="">Označení CE</a>
								</li><li>
									<a href="">Obecná bezpečnosť</a>
								</li><li>
									<a href="">Ochrana</a>
								</li><li>
									<a href="">Dozor nad trhem</a>
								</li><li>
									<a href="">Terminologie</a>
								</li>
									
							</ul>
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
							<ul class="pj-aside-nav">
								<li><a href="">Novinky</a></li>
								<li><a href="">České technické normy</a></li>
								<li><a href="">Harmonizované normy</a></li>
								<li><a href="">Určené normy</a></li>
								<li><a href="">Technické normalizační informace</a></li>
								<li><a href="">Evropské technické normy</a></li>
								<li><a href="">Medzinárodní normy</a></li>
								<li><a href="">Slovenské technické normy (STN)</a></li>
								<li><a href="">Americk0 normy (ASTM)</a></li>
								<li><a href="">Nemecké normy (DIN)</a></li>
								<li><a href="">České právní predpisy</a></li>
								<li><a href="">Evropské právní predpisy</a></li>
								<li><a href="">Slovenské právní predpisy</a></li>
							</ul>
						
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
							<div class="pj-scopes ">
									<strong class="pj-head pj -bg-light-gray">Oblasti</strong>

									<div class="pj-item">
										<a href="" class="pj-img">
											<img src="img/p/toys.jpg" />
										</a>
										<div>
											<h3><a class="pj-link" href="">Hračky</a></h3>
											<p>Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. Lorem Ipsum je štandardným výplňovým textom už od 16.</p>
										</div>
									</div>

									<div class="pj-item">
										<a href="" class="pj-img">
											<img src="img/p/elec.jpg" />
										</a>
										<div>
											<h3><a class="pj-link" href="">Elektrotechnický prumysl</a></h3>
											<p>Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. Lorem Ipsum je štandardným v.</p>
										</div>
									</div>

									<div class="pj-item">
										<a href="" class="pj-img">
											<img src="img/p/obal.jpg" />
										</a>
										<div>
											<h3><a class="pj-link"href="">Obaly prumysl</a></h3>
											<p>Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. Lorem Ipsum je štandardným v.</p>
										</div>
									</div>
									<div class="pj-item">
										<a href="" class="pj-img">
											<img src="img/p/toys.jpg" />
										</a>
										<div>
											<h3><a class="pj-link" href="">Hračky</a></h3>
											<p>Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. Lorem Ipsum je štandardným výplňovým textom už od 16.</p>
										</div>
									</div>

									<div class="pj-item">
										<a href="" class="pj-img">
											<img src="img/p/elec.jpg" />
										</a>
										<div>
											<h3><a class="pj-link" href="">Elektrotechnický prumysl</a></h3>
											<p>Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. Lorem Ipsum je štandardným v.</p>
										</div>
									</div>

									<div class="pj-item">
										<a href="" class="pj-img">
											<img src="img/p/obal.jpg" />
										</a>
										<div>
											<h3><a class="pj-link" href="">Obaly prumysl</a></h3>
											<p>Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. Lorem Ipsum je štandardným v.</p>
										</div>
									</div>
									<div class="pj-item">
										<a href="" class="pj-img">
											<img src="img/p/toys.jpg" />
										</a>
										<div>
											<h3><a class="pj-link" href="">Hračky</a></h3>
											<p>Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. Lorem Ipsum je štandardným výplňovým textom už od 16.</p>
										</div>
									</div>

									<div class="pj-item">
										<a href="" class="pj-img">
											<img src="img/p/elec.jpg" />
										</a>
										<div>
											<h3><a class="pj-link" href="">Elektrotechnický prumysl</a></h3>
											<p>Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. Lorem Ipsum je štandardným v.</p>
										</div>
									</div>

									<div class="pj-item">
										<a href="" class="pj-img">
											<img src="img/p/obal.jpg" />
										</a>
										<div>
											<h3><a class="pj-link" href="">Obaly prumysl</a></h3>
											<p>Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. Lorem Ipsum je štandardným v.</p>
										</div>
									</div>
									<div class="pj-item">
										<a href="" class="pj-img">
											<img src="img/p/toys.jpg" />
										</a>
										<div>
											<h3><a class="pj-link" href="">Hračky</a></h3>
											<p>Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. Lorem Ipsum je štandardným výplňovým textom už od 16.</p>
										</div>
									</div>

									<div class="pj-item">
										<a href="" class="pj-img">
											<img src="img/p/elec.jpg" />
										</a>
										<div>
											<h3><a class="pj-link" href="">Elektrotechnický prumysl</a></h3>
											<p>Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. Lorem Ipsum je štandardným v.</p>
										</div>
									</div>

									<div class="pj-item">
										<a href="" class="pj-img">
											<img src="img/p/obal.jpg" />
										</a>
										<div>
											<h3><a href="">Obaly prumysl</a></h3>
											<p>Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. Lorem Ipsum je štandardným v.</p>
										</div>
									</div>

									<div class="clear"></div>
							</div>
							<div class="pj-publications">
								<strong class="pj-head">Prodej on-line publikací</strong>

								<p>
									Lorem Ipsum je fiktívny text, používaný pri návrhu tlačovín a typografie. Lorem Ipsum je štandardným výplňovým textom už od 16. storočia, keď neznámy tlačiar zobral sadzobnicu plnú tlačových znakov a pomiešal ich, aby tak vytvoril vzorkovú knihu. Prežil nielen päť storočí, ale aj skok do elektronickej sadzby, a pritom zostal v podstate nezmenený.
								</p>

								<ul>
									<li class="pj-publication pj-radius">

										<a href="">
											<span class="pj-name">
												Normy pro systemy managmentu a posuzdování shody
											</span>
											<span class="pj-price">Cena: 3500 Kč</span>
											<span class="pj-ico"></span>
										</a>
										
									</li>

									<li class="pj-publication pj-radius">

										<a href="">
											<span class="pj-name">
												Technické normy a plasty
											</span>
											<span class="pj-price">Cena: 3490 Kč</span>
											<span class="pj-ico"></span>
										</a>
										
									</li>
								</ul>
							</div>
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