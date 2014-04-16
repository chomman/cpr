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
											<c:forEach items="${i.publishedChildrens}">
												<li><webpage:a webpage="${i}" /></li>
											</c:forEach>
										</ul>
									</c:if>
								</div>
							</div>
						</c:forEach>
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
						
						<li class="pj-publication pj-radius">
							<a href="">
								<span class="pj-name">
									Normy pro systemy managmentu a posuzdování shody
								</span>
								<span class="pj-price">Cena: 3490 Kč</span>
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
						
	</body>
</html>