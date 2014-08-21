<%@ page session="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>
		<c:if test="${empty standardCategory.id}">
			<spring:message code="standardCategory.add" />
		</c:if>
		<c:if test="${not empty standardCategory.id}">
			<spring:message code="standardCategory.edit" />: ${standardCategory.code}
		</c:if>
	</title>
	<script src="<c:url value="/resources/admin/tinymce/tinymce.min.js" />"></script>
	<script type="text/javascript">
		$(function(){
			tinyMCE.init({
				selector: "textarea.wisiwig",
				height : 120,
				width : '100%',
				forced_root_block : "",
				force_br_newlines : false,
				force_p_newlines : false,
				content_css : getBasePath() + 'resources/admin/css/tinymce.css',
				plugins: "link",
				entity_encoding : 'raw',
				toolbar: "undo redo | bold italic | link",
				statusbar : false
			});
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
			<jsp:include page="include/cpr-nav.jsp" />
		</div>
		<div id="right">
			<div id="breadcrumb">
				<a:adminurl href="/">
					<spring:message code="menu.home" />
				</a:adminurl>
				&raquo;
				<a:adminurl href="/cpr/regulations">
					<spring:message code="regulations" />
				</a:adminurl>
				&raquo; 
				<span>
				<c:if test="${empty standardCategory.id}">
					<spring:message code="standardCategory.add" />
				</c:if>
				<c:if test="${not empty standardCategory.id}">
					<spring:message code="standardCategory.edit" />: ${standardCategory.code}
				</c:if>
				</span>
			</div>
			<h1>
				<c:if test="${empty standardCategory.id}">
					<spring:message code="standardCategory.add" />
				</c:if>
				<c:if test="${not empty standardCategory.id}">
					<spring:message code="standardCategory.edit" />: ${standardCategory.code}
				</c:if>
			</h1>

			<div id="content">
				<ul class="sub-nav">
					<li>
						<a:adminurl href="/cpr/standard-categories">
							<spring:message code="standardCategories" />
						</a:adminurl>
					</li>
					<c:if test="${not empty standardCategory.id}">
					<li>
						<a:adminurl href="/cpr/standard-category/0">
							<spring:message code="standardCategory.add" />
						</a:adminurl>
					</li>
					</c:if>
				</ul>


			<form:form commandName="standardCategory" method="post" cssClass="valid">
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error" />
				
				<c:if test="${not empty successCreate}">
					<p class="msg ok">
						<spring:message code="success.create" />
					</p>
				</c:if>
				
				<div class="input-wrapp smaller">
					<label> 
						<spring:message code="standardCategory.code" />:
					</label>
					<div class="field">
						<form:input path="code" cssClass="w100" maxlength="10" />
					</div>
				</div>
				
				<div class="input-wrapp smaller">
					<label> 
						<spring:message code="standardCategory.noao" />:
					</label>
					<div class="field">
						<form:input path="noaoUrl" cssClass="mw500" maxlength="250" />
					</div>
				</div>
				<p class="form-head">Editace lokalizovaného obsahu</p>
				<p>
					<label>  
						Jazyková mutace:
					</label>
					<span class="field"> <a href="#" data-lang="cs"
						class="disabled">Česká</a> <a href="#" data-lang="en"
						class="lang">Anglická</a>
					</span>
				</p>
				
				
				<!-- CESTINA -->
			   <div class="input-wrapp smaller switchable cs">
	               	<label>
	               		<strong><em class="red">*</em>
	               			<spring:message code="standardCategory.name" />  (v češtine)
	               		</strong>
	               	</label>
	                <span class="field">
						<form:input path="nameCzech" maxlength="120" cssClass="mw500 required" />
	                </span>
               </div>
               <div class="input-wrapp smaller switchable cs">
	               	<label>
	               		<spring:message code="standardCategory.specialiyation" />  (v češtine)
	               	</label>
	                <span class="field">
						<form:input path="specializationCzech" maxlength="120" cssClass="mw500" />
	                </span>
               </div>
               <div class="input-wrapp smaller switchable cs">
	               	<label>
	               		<spring:message code="standardCategory.ojeu" />  (v češtine)
	               	</label>
	                <span class="field">
	                	<form:textarea path="ojeuPublicationCzech" cssClass="wisiwig"/>
	                </span>
               </div>
               
               <!-- ANGLICTINA -->
               <div class="input-wrapp smaller switchable en hidden">
	               	<label>
	               		<strong><em class="red">*</em>
	               			<spring:message code="standardCategory.name" />  (v angličtine)
	               		</strong>
	               	</label>
	                <span class="field">
						<form:input path="nameEnglish" maxlength="120" cssClass="mw500 required" />
	                </span>
               </div>
               <div class="input-wrapp smaller switchable en hidden">
	               	<label>
	               		<spring:message code="standardCategory.specialiyation" />  (v angličtine)
	               	</label>
	                <span class="field">
						<form:input path="specializationEnglish" maxlength="120" cssClass="mw500" />
	                </span>
               </div>
               <div class="input-wrapp smaller switchable en hidden">
	               	<label>
	               		<spring:message code="standardCategory.ojeu" />  (v angličtine)
	               	</label>
	                <span class="field">
	                	<form:textarea path="ojeuPublicationEnglish" cssClass="wisiwig"/>
	                </span>
               </div>
				
				<form:hidden path="id"/> 
				<p class="button-box">
					<input type="submit" class="button default" value="<spring:message code="form.save" />" /> 
				</p>
			</form:form>
					
				<c:if test="${not empty standardCategory.id}">					
					<jsp:include page="include/standard-category-regulations.jsp" />
					<div class="qs-bx-wrapp" style="margin-bottom:150px;">
						<c:if test="${not empty model.regulations}">
							<form class="inline-form">
								<div class="inline-field">
									<span class="label">
										Přiřadit předpis ke kategorii:
									</span>
									<select name="iid" class="chosenSmall">
										<c:forEach items="${model.regulations}" var="i">
											<option value="${i.id}">${i.code}</option>
										</c:forEach>
									</select>
								</div>
								<input type="submit" class="lang mandate-add-btn" value="Priradiť" />
								<input type="hidden" value="assign" name="action" />
							</form>
						</c:if>
					</div>
				</c:if>
				
								
			</div>
		</div>
		<div class="clear"></div>
	</div>

</body>
</html>