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
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/jquery.tagit.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/tagit.ui-zendesk.css" />" />
	<script src="<c:url value="/resources/admin/tinymce/tinymce.min.js" />"></script>
	<script src="<c:url value="/resources/admin/js/webpage.js" />"></script>
	<script src="<c:url value="/resources/public/js/picker.jquery.js" />"></script>
	<script src="<c:url value="/resources/admin/js/jquery.selectTip.js" />"></script>
	<script src="<c:url value="/resources/admin/js/tag-it.min.js" />"></script>
	
	
	
	 <script>
	$(function() {
		var tags = [
			<c:forEach items="${webpageContent.tags}" var="i" varStatus="loop">'${i.name}'<c:if test="${!loop.last}">,</c:if></c:forEach>                  
		];
		$( "#tabs" ).tabs();
		$( ".helpTip" ).selectTip();
		$('.lightbox').fancybox();
		$('#webpageModule').chosen({ width : "330px" });
		$('.picker').remotePicker({
			<c:if test="${not empty webpageContent.redirectWebpage}">
			item : { value : '${webpageContent.redirectWebpage.defaultName}', id: ${webpageContent.redirectWebpage.id} },
			</c:if>
	    	sourceUrl : getBasePath()  +"ajax/autocomplete/webpages",
	    	inputNames : { hidden : "redirectWebpage", text : "redirectUrl" },
	    	useDefaultCallBack : true,
	    	excludeId : ${webpageContent.id}
		});
		$('#tags').tagit({
			availableTags: tags,  
			allowSpaces: true,
			beforeTagAdded: function(event, ui) {
					var s = $.trim(ui.tag.text());			
					if(s.length > 25){
						showStatus({err : 1, msg : 'Max. délka je 25 znakú'});
						return false;
					}
			    }
		});
		
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
			<c:if test="${not empty model.webpage.parent}">
				<a:adminurl href="/webpage/add/${model.webpage.parent.id}" cssClass="btn-webpage radius link-ico" >
					Přidat sesterskou sekci <span class="ico plus"></span>
				</a:adminurl>
			</c:if>
			</div>
			
			<div id="ajax-result"></div>
			
			
			<div id="tabs">
				<ul>
					<li><a href="#content2">Obsah</a></li>
					<li><a href="#settings">Publikování a nastavení </a></li>
					<li><a href="#images">Obrázky</a></li>
				</ul>
			<div id="content2">
					<form:form commandName="webpageContent" method="post" cssClass="valid" name="webpageContent">
						<p class="pj-redirect-type  pj-type">
							 <label>
			                 	<strong>
			                 		<spring:message code="webpage.redirect" />:
			                 	</strong>	
			                 </label>
			                     <span class="field">
			                     	<form:input path="redirectUrl" cssClass="picker mw500"/>
			                     </span>
						</p>
						
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
		                     <form:input htmlEscape="true" path="webpageContent.name" id="pj-name" maxlength="200" cssClass="mw500 required"
		                     data-err-msg="Hodnota: Název sekce, musí být vyplněna" />
		                    </span>
			            </p>
						<p>
	                 		<label>
	                 			<spring:message code="webpage.webpageContent.title" />
							</label>
		                    <span class="field">
		                     <form:input htmlEscape="true" path="webpageContent.title" id="pj-title" maxlength="250" cssClass="mw500"
		                     data-err-msg="Hodnota: Titulek sekce, musí být vyplněna" />
		                    </span>
                		</p>
                		<c:if test="${isLoggedWebmaster}">
	                		<p>
		                 		<label>
		                 			<spring:message code="webpage.url" />
								</label>
			                    <span class="field">
			                     <form:input htmlEscape="true" path="code" id="pj-code" maxlength="250" cssClass="mw500" />
			                    </span>
	                		</p>
                		</c:if>
                		<p class="pj-content-type  pj-type">
	                 		<label class="tt" title="Slovo potvrďte stiskem klávesy ENTER">
	                 			<spring:message code="webpage.webpageContent.tags" />
							</label>
		                    <span class="field tags-wrapp">
		                    	<input type="text" id="tags" placeholder="Vepište slovo a stiskněte ENTER"/>
		                    </span>
                		</p>
                		
						<p>
							<label>
								<spring:message code="webpage.webpageContent.description" />
						   </label>
							<span class="field">
								<form:textarea path="webpageContent.description" id="pj-description" cssClass="mw500 mh100" />
							</span>
						</p>
						<p class="pj-content-type  pj-type">
							
							<span class="field full-width">
								<form:textarea path="webpageContent.content" cssClass="wisiwig" />
							</span>
						</p>
						<p>
							<span class="field full-width">
								<input type="submit" class="button default saveContent" value="<spring:message code="form.save" />" />
								
								<a href="<webpage:link webpage="${model.webpage}" />?isPreview=true" class="button preview">
								<spring:message code="form.preview" />
								</a>
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
							<c:url value="/admin/webpage/lang/${webpageContent.id}" var="actionUrl"  />
							<form method="post" action="${actionUrl}">
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
				<form:form modelAttribute="webpageSettings" name="webpageSettings">
							<p>
								<label>
									Typ webové sekce:
								</label>
								<span class="field">
									 <form:select path="webpageType" cssClass="chosenSmall required helpTip">
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
									K sekci připojit modul:
								</label>
								<span class="field">
									 <form:select path="webpageModule" cssClass="helpTip" >
					                	<option value=""><spring:message code="form.select" /></option>
					                	<c:forEach items="${model.webpageModules}" var="i">
					                		<option <c:if test="${i eq  webpageSettings.webpageModule}">selected="selected"</c:if>
					                				value="${i}" data-id="${i.id}" 
					                				title="<spring:message code="${i.name}.descr" />">
					                			<spring:message code="${i.name}" />
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
									 <form:checkbox path="enabled" id="enabled" />
								</span>
							</p>
							
							<p>
								<label>
									<spring:message code="webpage.showThumnail" />:
								</label>
								<span class="field">
									 <form:checkbox path="showThumbnail" id="showThumbnail" />
									 <span class="mini-info inline">Při seznamu podstránek se bude zobrazovat avatar (Náhledový obrázek), pokud existuje.</span>
								</span>
							</p>
							
							<p  class="pj-article-type pj-type">
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
							<p>
								<label>
									Pouze pro registrované:
								</label>
								<span class="field">
									 <form:checkbox path="isOnlyForRegistrated" id="isOnlyForRegistrated" />
									 <span class="mini-info inline">Sekce bude dostupná pouze pro registrované uživatele</span>
								</span>
							</p>
							<p>
								<label>
									Zobrazit na celou šířku:
								</label>
								<span class="field">
									 <form:checkbox path="fullWidth" id="fullWidth" />
									 <span class="mini-info inline">Obsah webové sekce bude zobrazen na celou šířku. Nebude se zobrazovat levý boční panel (navigace a novinky)</span>
								</span>
							</p>
							
							<c:if test="${isLoggedWebmaster}">
								<p>
									<label>
										Zamknutá URL adresa:
									</label>
									<span class="field">
										 <form:checkbox path="lockedCode" id="lockedCode" />
									</span>
								</p>
								<p>
									<label>
										Zamčené odstraňování:
									</label>
									<span class="field">
										 <form:checkbox path="lockedRemove" id="lockedRemove" />
									</span>
								</p>
							</c:if>
							<p class="button-box">
								<input type="submit" class="button default" value="<spring:message code="form.save" />" /> 
							</p>
				</form:form>
			</div>
			<div id="images">
				<c:url value="/admin/webpage/${webpageContent.id}/avatar" var="actionUrl" />
				<form method="post" action="${actionUrl}" enctype="multipart/form-data" name="avatar" class="valid <c:if test="${not empty model.webpage.avatar}">hidden</c:if>"> 
				  	<p>
						<label>
							<strong>
								<em class="red">*</em>
								<spring:message code="form.file.image.upload" />
							</strong>
						</label>
						<span class="field">
							<input name="file" id="file" type="file" class="required" />
						</span>
					</p>
				  	<p class="button-box">
				  		<input type="submit" class="button default" value="<spring:message code="form.file.upload" />" />		
				  	</p>
				  
				</form>
				
				<div class="pj-fotobox <c:if test="${empty model.webpage.avatar}">hidden</c:if>">
					<h4><spring:message code="webpage.avatar" /></h4>
					<span>
						<c:if test="${not empty model.webpage.avatar}">
							<a href="<c:url value="/image/n/avatars/${model.webpage.avatar}" />" class="lightbox">
								<img src="<c:url value="/image/s/150/avatars/${model.webpage.avatar}" />" alt="avatar" />
							</a>
						</c:if>
					</span>
					<a href="#" class="confirm delete">
						<spring:message code="form.delete" />
					</a>
				</div>
			</div>
		</div>
			
		</div>	
	</div>
	<div id="loader" class="webpage"></div>
</body>
</html>