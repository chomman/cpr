<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<sec:authorize access="hasRole('ROLE_WEBMASTER')">	
	<c:set var="isLoggedWebmaster" value="true"/>
</sec:authorize>

<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="webpages.edit" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/webpages.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/jquery.treetable.theme.custom.css" />" />
	<script src="<c:url value="/resources/admin/tinymce/tinymce.min.js" />"></script>
	<script src="<c:url value="/resources/admin/js/webpage.js" />"></script>
	 <script>
	$(function() {
		$( "#tabs" ).tabs();
	});
	</script>
</head>
<body>
	<div id="wrapper">
		<div id="content">
			<div id="breadcrumb">
				<a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				<a:adminurl href="/webpages"><spring:message code="webpages" /></a:adminurl>  &raquo;
				 <span>
				 <spring:message code="webpages.edit" />
				 </span>
			</div>
			<div class="pj-nav margin-bottom">
				<span class="pj-nav-label"><spring:message code="webpages.edit" /></span>
				<span class="pj-nav-label2"><spring:message code="options" />:</span>
				<a:adminurl href="/webpages" cssClass="btn-webpage radius link-ico" >
				<spring:message code="webpages.view" /> <span class="ico set"></span>
			</a:adminurl>
				<a:adminurl href="/webpage/add/0" cssClass="btn-webpage tt radius link-ico" title="Do hlavního menu">
					<spring:message code="webpages.add" /> <span class="ico plus"></span>
				</a:adminurl>
			</div>
			
			
			<div id="tabs">
				<ul>
					<li><a href="#content">Obsah</a></li>
					<li><a href="#settings">Publikování a nastavení </a></li>
					<li><a href="#images">Obrázky</a></li>
				</ul>
			<div id="content">
					<form:form commandName="webpageContent" method="post" cssClass="valid">
							<p <c:if test="${fn:length(model.usedLocales) eq 1}">class="pj-locale-box hidden"</c:if>>
			                 <label>
			                 	<strong>
			                 		<spring:message code="webpage.locale" />:
			                 	</strong>	
			                 </label>
			                     <span class="field">
			                     	<c:forEach items="${model.locales}" var="l">
			                     		<a href="#" data-lang="${l}" class="lang <c:if test="${l eq  webpageContent.locale}">disabled</c:if>">
			                     			<spring:message code="l${l}" />
			                     		</a>
			                     	</c:forEach>
			                    </span>
			                </p> 
		                
						<p>
			                 <label>
				                 <strong>
				                 	<em class="red">*</em><spring:message code="webpage.webpageContent.name" />
				                 </strong>	
				                 <small><spring:message code="webpage.webpageContent.name.descr" /></small>
					         </label>
		                    <span class="field">
		                     <form:input htmlEscape="true" path="webpageContent.name" maxlength="200" cssClass="mw500 required"
		                     data-err-msg="Hodnota: Název sekce, musí být vyplněna" />
		                    </span>
			            </p>
						<p>
	                 		<label>
	                 			<spring:message code="webpage.webpageContent.title" />
							</label>
		                    <span class="field">
		                     <form:input htmlEscape="true" path="webpageContent.title" maxlength="250" cssClass="mw500"
		                     data-err-msg="Hodnota: Titulek sekce, musí být vyplněna" />
		                    </span>
                		</p>
                		<c:if test="${isLoggedWebmaster}">
	                		<p>
		                 		<label>
		                 			<spring:message code="webpage.url" />
								</label>
			                    <span class="field">
			                     <form:input htmlEscape="true" path="webpageContent.url" maxlength="250" cssClass="mw500" />
			                    </span>
	                		</p>
                		</c:if>
                		
						<p>
							<label>
								<spring:message code="webpage.webpageContent.description" />
						   </label>
							<span class="field">
								<form:textarea path="webpageContent.description" cssClass="mw500 mh100" />
							</span>
						</p>
						<p>
							
							<span class="field full-width">
								<form:textarea path="webpageContent.content" cssClass="wisiwig" />
							</span>
						</p>

						
						<form:hidden path="locale" />
						<form:hidden path="id" />				
					</form:form>

			</div>
			<div id="settings">
				<table>
					
					<!--  JAZYKY  -->
					<tr>
						<td class="t-label">
							Sekce je v jazykových mutacích:	
						</td>
						<td class="t-val">
							<c:forEach items="${model.usedLocales}" var="lang" varStatus="loop">
								<spring:message code="l${lang}" />
								<c:if test="${!loop.last}">, </c:if>
							</c:forEach>
						</td>
						<td class="t-label">Přidat jazykovou mutaci:</td>
						<td class="t-val">
							<c:if test="${not empty model.notUsedLocales}">
							<c:url value="/admin/webpage/add-lang/${webpageContent.id}" var="actionUrl"  />
							<form method="get" action="${actionUrl}">
							<select name="${model.langCodeParam}" class="chosenMini">
								<c:forEach items="${model.notUsedLocales}" var="i" >
										<option value="${i}" >
											<spring:message code="l${i}" />
										</option>
								</c:forEach>
							</select>
								<a class="btn-webpage btn-submit radius link-ico">Pridať<span class="ico plus"></span></a>
							</form>
							</c:if>
							<c:if test="${empty model.notUsedLocales}">
								<em>Další jazykové mutace nejsou dostupné</em>
							</c:if>
						</td>
					</tr>
					
					<!--  JAZYKY  -->
				</table>
				<form:form modelAttribute="webpageSettings">
							<p>
								<label>
									Typ webové sekce:
								</label>
								<span class="field">
									 <form:select path="webpageType" cssClass="chosenSmall  required">
					                	<c:forEach items="${model.webpageTypes}" var="i">
					                		<option <c:if test="${i eq  webpageSettings.webpageType}">selected="selected"</c:if>
					                				value="${i}" data-id="${i.id}" 
					                				title="<spring:message code="${i.description}" />">
					                			<spring:message code="${i.code}" />
					                		</option>
					                	</c:forEach>
					                </form:select>
								</span>
							</p>
							
							<p>
								<label>
									<spring:message code="publish" />:
								</label>
								<span class="field">
									 <form:checkbox path="enabled" />
								</span>
							</p>
							
							<p>
								<label>
									<spring:message code="webpage.publishedSince" />:
                					<small><spring:message code="webpage.publishedSince.alert" /></small>
								</label>
								<span class="field">
			                    	<span><spring:message code="date" />:</span>
			                    	<input type="text" class="date" id="publishedSince-date" maxlength="10" />
			                    	<span><spring:message code="time" />:</span>
			                    	<input type="text" class="time" id="publishedSince-time" maxlength="5" />
			                    	<form:hidden  htmlEscape="true" path="publishedSince" /><em>hh:mm</em>
			                    </span>
							</p>
							
							<c:if test="${isLoggedWebmaster}">
								<p>
									<label>
										Zamknutá:
									</label>
									<span class="field">
										 <form:checkbox path="locked" />
									</span>
								</p>
								
							</c:if>

				</form:form>
			</div>
			<div id="images">
			<p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
			<p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>
			</div>
		</div>
			
		</div>	
	</div>
<div id="fileDir" class="hidden">webpage-${webpageId}</div>
<div id="loader" class="webpage"></div>
</body>
</html>