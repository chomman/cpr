<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
<c:set var="isLoggedWebmaster" value="false"/>
<sec:authorize access="hasRole('ROLE_WEBMASTER')">	
	<c:set var="isLoggedWebmaster" value="true"/>
</sec:authorize>
<!DOCTYPE html>
<html>
<head>
	<title>
		<c:if test="${empty portalProduct.id}">
			<spring:message code="admin.service.add" />
		</c:if>
		<c:if test="${not empty portalProduct.id}">
			<spring:message code="admin.service.edit" />
		</c:if>
	</title>
	<script src="<c:url value="/resources/admin/tinymce/tinymce.min.js" />"></script>
	<script src="<c:url value="/resources/admin/js/jquery.selectTip.js" />"></script>
	<script type="text/javascript">
		 tinyMCE.init({
			 	selector: "textarea.wisiwig",
				language : "cs",
				height : 200,
				width : 630,
				forced_root_block : "",
				force_br_newlines : true,
				force_p_newlines : false,
				plugins: "image,link,table",
				convert_urls: false
		});
		 $(function() { 	
			 $(document).on('click', 'a.lang', function(){
				var $selected = $('.disabled'),
					$this = $(this),
					locale = $this.attr('data-lang'),
					$boxes = $('.switchable');
					$boxes.removeClass('hidden');
					$boxes.not('.' + locale).addClass('hidden');
					$selected.removeClass('disabled').addClass('lang');
					$this.addClass('disabled').removeClass('lang');
					return false;
			 });
			 $( ".helpTip" ).selectTip();
		 });
	</script>
</head>
<body>
	<div id="wrapper">
		<div id="left">
			<jsp:include page="../include/portal-nav.jsp" />
		</div>	
		<div id="right">
		
			<div id="breadcrumb">
				 <a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>  &raquo;
				 <a:adminurl href="/portal/products"><spring:message code="admin.portal.services" /></a:adminurl>  &raquo;
				 <span>
				 	<c:if test="${empty portalProduct.id}">
						<spring:message code="admin.service.add" />
					</c:if>
					<c:if test="${not empty portalProduct.id}">
						<spring:message code="admin.service.edit" />
					</c:if>
				 </span>
			</div>
			<h1>
				<c:if test="${empty portalProduct.id}">
					<spring:message code="admin.service.add" />
				</c:if>
				<c:if test="${not empty portalProduct.id}">
					<spring:message code="admin.service.edit" />
				</c:if>
			</h1>
	
			<div id="content">
				
				<jsp:include page="product-nav.jsp" />
				
				<form:form commandName="portalProduct" method="post" cssClass="valfid" >
							
							<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
							
							<c:if test="${not empty successCreate}">
								<p class="msg ok"><spring:message code="success.create" /></p>
							</c:if>
								<p>
		                       		<label>
		                       			<strong><em class="red">*</em>
		                        			<spring:message code="admin.service.selectType" />
		                        		</strong>  
		                        	</label>
		                            <span class="field"> 	                            	
		                            	<form:select path="portalProductType" cssClass="w100 chosenSmall">
		                            		<c:forEach items="${portalProductTypes}" var="i">
		                            			<option value="${i}" <c:if test="${i.id == portalProduct.portalProductType.id}">selected="selected"</c:if> > 
		                            				<spring:message code="${i.code}" />
		                            			</option>
		                            		 </c:forEach>
		                            	</form:select>
		                            </span>
		                        </p>
		                        <p class="prod publication">
		                       		<label>
		                       			<strong><em class="red">*</em>
		                        			<spring:message code="admin.service.onlinePublication" />
		                        		</strong>  
		                        	</label>
		                            <span class="field"> 
		                            	<form:select path="onlinePublication" cssClass="w100 chosenSmall helpTip">
		                            		<option value="">-- Vybrat --</option>
		                            		<c:forEach items="${onlinePublications}" var="i">
		                            			<option value="${i}" data-id="${i}" title="${i.url}" <c:if test="${i eq portalProduct.onlinePublication}">selected="selected"</c:if> >
		                            				${i.code}
		                            			</option>
		                            		 </c:forEach>
		                            	</form:select>
		                            </span>
		                        </p>
	                        <c:if test="${not empty portalProduct.onlinePublication}">
		                        <p>
		                       		<label>
		                       			<strong><em class="red">*</em>
		                        			Adresa publikace: 
		                        		</strong>  
		                        	</label>
		                            <span class="field"> 
		                            	<span><strong>${portalProduct.onlinePublication.code}</strong> &nbsp; | &nbsp;	                             	
			                            	<a href="${portalProduct.onlinePublication.url}" target="_blank">
			                            		${portalProduct.onlinePublication.url}
			                            	</a>
		                            	</span>
		                            </span>
		                        </p>
	                        </c:if>
	                        <p class="prod registration">
	                       		<label>
	                       			<strong><em class="red">*</em>
	                        			<spring:message code="admin.service.interval" />
	                        		</strong>  
	                        	</label>
	                            <span class="field"> 
	                            	<form:input path="intervalValue" maxlength="3" cssClass="w50 required numeric" />
	                            	<form:select path="portalProductInterval" cssClass="w100 chosenMini">
	                            		<c:forEach items="${intervalTypes}" var="i">
	                            			<option value="${i}" <c:if test="${i.id == portalProduct.portalProductInterval.id}">selected="selected"</c:if> >
	                            				<spring:message code="${i.code}" />
	                            			</option>
	                            		 </c:forEach>
	                            	</form:select>
	                            </span>
	                        </p>
 	                        
	                         <p>
	                       		<label>
	                       			<strong><em class="red">*</em>
	                        			<spring:message code="admin.service.price" /> (Kč)
	                        		</strong>  
	                        	</label>
	                            <span class="field"> 
	                            	<form:input path="priceCzk" maxlength="5" cssClass="w100 required numeric" />
	                            </span>
	                        </p>
	                         <p>
	                       		<label>
	                       			<strong><em class="red">*</em>
	                        			<spring:message code="admin.service.price" /> (EUR)
	                        		</strong>  
	                        	</label>
	                            <span class="field"> 
	                            	<form:input path="priceEur" maxlength="5" cssClass="w100 required numeric" />
	                            </span>
	                        </p>
	                         <p>
	                        	<label>
	                        			<spring:message code="admin.service.enabled" /> 
	                        	</label>
	                            <span class="field">
	                            	<form:checkbox path="enabled" />
	                            </span>
	                        </p>
	                        <p>
								<label> <strong> <spring:message
											code="webpage.locale" />:
								</strong>
		
								</label> <span class="field"> <a href="#" data-lang="cs"
									class="disabled">Česká</a> <a href="#" data-lang="en"
									class="lang">Anglická</a>
								</span>
							</p>
							<p class="switchable cs">
	                        	<label>
	                        		<strong><em class="red">*</em>
	                        			<spring:message code="admin.service.czechName" />  
	                        		</strong>
	                        	</label>
	                            <span class="field">
	                            	<form:input path="czechName" maxlength="150" cssClass="mw500 required" />
	                            </span>
	                        </p>
	                         <p class="switchable en hidden">
	                       		<label>
	                        		<spring:message code="admin.service.englishName" />  
	                        	</label>
	                            <span class="field">
	                            	<form:input path="englishName" maxlength="150" cssClass="mw500" />
	                            </span>
	                        </p>
	                         <p class="switchable cs">
	                        	<label>
	                        			<spring:message code="admin.service.description" /> (<spring:message
								code="cs" />)
	                        	</label>
	                            <span class="field">
	                            	<form:textarea path="descriptionCzech" cssClass="wisiwig" />
	                            </span>
	                        </p>
	                         <p class="switchable en hidden">
	                        	<label>
	                        			<spring:message code="admin.service.description" /> (<spring:message
								code="en" />)
	                        	</label>
	                            <span class="field">
	                            	<form:textarea path="descriptionEnglish" cssClass="wisiwig" />
	                            </span>
	                        </p>
							
							
							
		                    <form:hidden path="id" />
	                        <p class="button-box">
	                        	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
	                        </p>
						</form:form>
				<span class="note"><spring:message code="form.required" /></span>
			</div>	
		</div>
		<div class="clear"></div>	
	</div>

</body>
</html>