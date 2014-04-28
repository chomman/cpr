<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>	
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
	<script type="text/javascript">
		 tinyMCE.init({
			 	selector: "textarea.wisiwig",
				language : "cs",
				height : 170,
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
	                        			<spring:message code="admin.service.price" />
	                        		</strong>  
	                        	</label>
	                            <span class="field"> 
	                            	<form:input path="price" maxlength="5" cssClass="w100 required numeric" />
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