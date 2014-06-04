<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>
		<c:if test="${id == 0}">
			<spring:message code="csn.add" />
		</c:if>
		<c:if test="${id != 0}">
			<spring:message code="csn.edit" arguments="${csn.csnId}" />
		</c:if>
	</title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
		<jsp:include page="csn-nav.jsp" />
	</div>
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/csn" />"><spring:message code="menu.csn" /></a> &raquo;
			 <span><spring:message code="csn.add" /></span>
		</div>
		<h1>
			<c:if test="${id == 0}">
				<spring:message code="csn.add" />
			</c:if>
			<c:if test="${id != 0}">
				<spring:message code="csn.edit" arguments="${csn.csnId}" />
			</c:if>
		</h1>

		<div id="content">
			<ul class="sub-nav">
				<li><a href="<c:url value="/admin/csn"  />"><spring:message code="csn.list" /></a></li>
				<li><a<c:if test="${id == 0}"> class="active"</c:if> href="<c:url value="/admin/csn/edit/0"  />"><spring:message code="csn.add" /></a></li>
				<li><a  href="<c:url value="/admin/csn/csvimport"  />"><spring:message code="csn.csvimport" /></a></li>
			</ul>
		
			<c:if test="${not empty csn.createdBy}">
				<table class="info">
					<tr>
						<td class="key"><spring:message code="meta.created" /></td>
						<td class="val">${csn.createdBy.firstName} ${csn.createdBy.lastName}</td>
						<td class="val"><joda:format value="${csn.created}" pattern="${common.dateTimeFormat}"/></td>
					</tr>
					<c:if test="${not empty csn.changedBy}">
					<tr>
						<td class="key"><spring:message code="meta.edited" /></td>
						<td class="val">${csn.changedBy.firstName} ${csn.changedBy.lastName}</td>
						<td class="val"><joda:format value="${csn.changed}" pattern="${common.dateTimeFormat}"/></td>
					</tr>
					</c:if>
				</table>
			</c:if>
			
			<c:url value="/admin/csn/edit/${id}" var="formUrl"/>					
			<form:form  modelAttribute="csn" method="post" cssStyle="valid"  action="${formUrl}">
				
				<div id="ajax-result"></div>
				
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
				<c:if test="${not empty successUpdate}">
					<p class="msg ok"><spring:message code="success.update" /></p>
				</c:if>
				
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				
				<p class="form-head"><spring:message code="csn.basic.info" /><p>
                <p>
                	<label>
                		<strong>
                			<em class="red">*</em>
                			<spring:message code="csn.form.name" />:
                		</strong>
                	</label>
                   <span class="field">
                    	<form:input path="csnId" maxlength="50" cssClass="required" />
                    </span>
                </p>
                <p>
                	<label title="V případě, že není přesný den vydání známý, zvolte jakýkoliv den v daném měsíci." class="tt">
                		<spring:message code="csn.form.published" />:
                	</label>
                    <span class="field">
                    	<form:input path="published"  cssClass="date" />
                    </span>
                </p>
                
                <p>
                	<label>
                		<spring:message code="cpr.csn.onlineid" />:
                	</label>
                    <span class="field">
                    	<form:input path="catalogId" cssClass="csnOnlineReplace" />
                    </span>
                </p>
                
                <p>
				    <label >
				    	<strong>
                			<em class="red">*</em>
				 		<spring:message code="csn.form.czechName" />:
				 		</strong>
				 	</label>
				     <span class="field">  
				     	<form:input  htmlEscape="true" path="czechName"  cssClass="mw500 required" maxlength="255" />
				     </span>
				 </p>
				 <p>
				    <label >
				 		<spring:message code="csn.form.englishName" />:
				 	</label>
				     <span class="field">  
				     	<form:input  htmlEscape="true" path="englishName"  cssClass="mw500" maxlength="255"  />
				     </span>
				 </p>

				<p>
				    <label >
				 		<spring:message code="csn.form.category" />:
				 	</label>
				     <span class="field">  
				     	<select name="csnCategory" class="maxw600 chosen" data-placeholder="<spring:message code="csn.form.select.choose" />" >
				     		<option value=""></option>
				     		<c:forEach items="${model.csnCategories}" var="i">
				     			<optgroup label="${i.searchCode} - ${i.name}">
				     				<c:forEach items="${i.children}" var="c">
				     					<option <c:if test="${csn.csnCategory.id ==  c.id}">selected="selected"</c:if> 
				     					value="${c.id}" >${c.searchCode} - ${c.name}</option>
				     				</c:forEach>
				     			</optgroup> 
				     		</c:forEach>
				     	</select>
				     </span>
				 </p>
				
				  <p>
                	<label>
                		<spring:message code="csn.form.symbol" />:
                	</label>
                   <span class="field">
                    	<form:input  htmlEscape="true" path="classificationSymbol" maxlength="10"  />
                    </span>
                </p>
				 <p>
                	<label>
                		<spring:message code="csn.form.isc" />:
                	</label>
                   <span class="field">
                    	<form:input  htmlEscape="true" path="ics" class="mw500"  maxlength="255" />
                    </span>
                </p>
               
				
                <form:hidden path="id"/>
                <p class="button-box">
                	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
                </p>
			</form:form>
			

			<span class="note"><spring:message code="form.required" htmlEscape="false" /></span> 
			
			<c:if test="${not empty id  and id != 0}">
			
				<!-- IMPORT -->
			
				<c:url value="/admin/csn/import/${id}" var="formUrl"/>					
				<form:form  modelAttribute="uploadForm" method="post" action="${formUrl}" cssClass="csnFileUpload" enctype="multipart/form-data">
					<p class="form-head"><spring:message code="csn.terminology.upload" arguments="${csn.csnId}" /><p>
					 
					 <jsp:include page="csn-terminology-import-status.jsp" />
					 <c:if test="${not empty importFaild}">
					 	<p class="msg error">Nastala chyba, import selhal.</p>
					 </c:if>
					 
					 <p>
						<label>
							<spring:message code="csn.terminology.upload.selectfile" />:
						</label>
						<span class="field">
							<input type="file" name="fileData" />
							<input type="submit" class="button radius" value="<spring:message code="form.file.upload"  /> &raquo;" />
						</span>	
               		</p>
			
				</form:form>

				<!-- CSN TERMINOLOGY -->
				
				<div id="terminology">			
					<p class="form-head"><spring:message code="csn.terminology" arguments="${csn.csnId}" htmlEscape="false" /><p>
					<div class="csn-edit-nav" >
						<a class="add radius" title="<spring:message code="csn.terminology.add" />" href="<c:url value="/admin/csn/${id}/terminology/edit/0" />">
							<spring:message code="csn.terminology.add" />
						</a>
						
						<a data-message="<spring:message code="csn.terminology.delete.confirm" arguments="${csn.csnId}" />" class="delete radius confirmMessage" title="<spring:message code="csn.terminology.delete" />" href="<c:url value="/admin/csn/${id}/terminology/delete/all" />">
							<spring:message code="csn.terminology.delete" />
						</a>
						
					</div>
					<div class="search-box" >
						
						<c:if test="${model.lang == 'CZ' }">
							<span class="disabled">
								<spring:message code="csn.terminology.lang.cz" />
							</span>
							<a class="lang en <c:if test="${model.lang == 'EN' }">disabled</c:if>" href="<c:url value="/admin/csn/edit/${id}?lang=EN" />">
								<spring:message code="csn.terminology.lang.en" />
							</a>
						</c:if>
						
						<c:if test="${model.lang == 'EN' }">
							<a class="lang cz " href="<c:url value="/admin/csn/edit/${id}?lang=CZ" />">
								<spring:message code="csn.terminology.lang.cz" />
							</a>
							<span class="disabled">
								<spring:message code="csn.terminology.lang.en" />
							</span>
						</c:if>
						
						
						
						
						<span title="<spring:message code="form.quicksearch.title" />" class="tt"><spring:message code="form.quicksearch" />:</span>
						<input id="quick-search" type="text" />
					</div>
						<table class="data csn">
						<thead>
							<tr>
								<tH><spring:message code="cns.terminology.section" /></th>
								<tH><spring:message code="csn.terminology.title" /></th>
								<th><spring:message code="csn.terminology.lang" /></th>
								<th><spring:message code="changed" /></th>
								<th><spring:message code="form.edit" /></th>
								<th><spring:message code="form.delete" /></th>
							</tr>
						</thead>
						<tbody>
								 <c:forEach items="${model.terminologies}" var="i">
								 	<tr>
								 		<td>${i.section}<td>
								 			<a href="<c:url value="/admin/csn/${id}/terminology/edit/${i.id}"  />">
							 				${i.title} 
							 				</a>
								 		</td>
								 		<td>
								 			<spring:message code="${i.language.name}" /> 
								 		</td>
								 		<td class="last-edit">
								 			<c:if test="${empty i.changedBy}">
								 				<joda:format value="${i.created}" pattern="${common.dateTimeFormat}"/>
								 			</c:if>
								 			<c:if test="${not empty i.changedBy}">
								 				<joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/>
								 			</c:if>
								 		</td>
								 		<td class="edit">
								 			<a class="tt" title="Zobrazit a upraviť položku?" href="<c:url value="/admin/csn/${id}/terminology/edit/${i.id}"  />">
								 				<spring:message code="form.edit" />
								 			</a>
								 		</td>
								 		<td class="delete">
								 			<a class="confirm"  href="<c:url value="/admin/csn/${id}/terminology/delete/${i.id}"  />">
								 				<spring:message code="form.delete" />
								 			</a>
								 		</td>
								 	</tr>
								 </c:forEach>
						</tbody>
					</table>
				</div>
			
			</c:if>
			
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>