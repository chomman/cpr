<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="cpr.standard.edit" arguments="${model.standardName}" /></title>
<script src="<c:url value="/resources/admin/js/standard-csn.picker.js" />"></script>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/cpr-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr" />"><spring:message code="menu.cpr" /></a> &raquo;
			 <a href="<c:url value="/admin/cpr/standards" />"><spring:message code="menu.cpr.norm" /></a> &raquo;
			 <span><spring:message code="cpr.standard.edit" arguments="${model.standardName}" /></span>
		</div>
		<h1><spring:message code="cpr.standard.edit" arguments="${model.standardName}" /></h1>
		<div id="content">
							
					
					<ul class="sub-nav">
						<li><a href="<c:url value="/admin/cpr/standards"  />"><spring:message code="cpr.standard.view" /></a></li>
						<li><a href="<c:url value="/admin/cpr/standard/add"  />"><spring:message code="cpr.standard.add" /></a></li>
					</ul>
								
				<div id="tabs">
					
					<jsp:include page="include/cpr-standard-menu1.jsp" />
					<jsp:include page="include/cpr-standard-menu2.jsp" />

					<strong class="active-tab-head"><spring:message code="cpr.standard.tab.3" /></strong>
					
					<!-- ACTIVE TAB -->
					<div class="active-tab">
					
					<div id="req-nav">
						<a class="view" href="<c:url value="/admin/cpr/standard/edit/${standardId}/csn" />">
							&laquo; <spring:message code="cpr.csn.view" />
						</a>
						<c:if test="${csn.id != 0}">
							<a href="<c:url value="/admin/cpr/standard-csn/${csn.id}/change/0"  />">
								 <spring:message code="cpr.csn.change.add"  arguments="${csn.csnName}"  /> +
							</a>
						</c:if>
					</div>
					
					
				
					<!--  FORM  -->
					<c:url value="/admin/cpr/standard/edit/${standardId}/csn-edit/${csn.id}" var="formUrl"/>
					<form:form  commandName="csn" method="post" action="${formUrl}" cssClass="valid" >
					
							<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
							<c:if test="${not empty successCreate}">
								<p class="msg ok"><spring:message code="success.create" /></p>
							</c:if>
					
						<p>
		                	<label>
		                		<strong><em class="red">*</em>
		                			<spring:message code="cpr.csn.name" />:
		                		</strong>
		                	</label>
		                    <span class="field">
		                    	<form:input path="csnName" maxlength="45" cssClass="w300 required" />
		                    </span>
	                    </p>
	                    <p>
		                	<label title="ČSN online ID je číselný identifikátor PDF dokumentu obsahující normu" class="tt">
	                			<spring:message code="cpr.csn.onlineid" />:
	                			<small>
	                			Příklad ČSN online ID je znázorněn červenou barvou.<br />
	                			</small>
		                	</label>
		                    <span class="field">
		                    	<form:input path="csnOnlineId" maxlength="10" cssClass="w100" />
		                    	<span class="norminfo" >http://www.sgpstandard.cz/editor/files/on_line/csn-redirect.php?k=<strong class="red">90588</strong></span>
		                    </span>
	                    </p>
	                    <p>
		                	<label>
		                		<spring:message code="csn.table.classification" />:
		                	</label>
		                    <span class="field">
		                    	<form:input path="classificationSymbol" maxlength="10"  cssClass="w100" />
		                    </span>
	                    </p>    
	                    <c:if test="${not empty csn.replaceStandardCsn}">
						 	<script type="text/javascript">
								$(document).ready(function() {
									selectStandard('${csn.replaceStandardCsn.csnName}', ${csn.replaceStandardCsn.id});
								});
							</script>
					  
						 </c:if>
						 
	                    <p>
						 	<label>
						 		<spring:message code="cpr.standard.status" />:
						 	</label>
						     <span class="field standard-picker">  
						     	<form:select path="standardStatus" cssClass="chosenSmall">
						     		<c:forEach items="${model.standardStatuses}" var="i">
					                       <option value="${i}" <c:if test="${i ==  csn.standardStatus}">selected="selected"</c:if> >
					                       		<spring:message code="${i.name}" />
					                       </option>
					               </c:forEach>
						     	</form:select>
						     	<span id="standard-replaced-label" class="resetmargin"></span>	
						     	<input type="text" id="standardPicker" />	
						     	<form:hidden path="replaceStandardCsn" id="pickerVal" />
						     </span>
						 </p>
						 <p id="statusDateWrapp" <c:if test="${empty csn.statusDate}">class="hidden"</c:if> >
						 	<label>
						 		<spring:message code="standardStatus.date" />:
						 	</label>
						     <span class="field">  
						     	<form:input path="statusDate" maxlength="25" cssClass="date"  />
						     </span>
						 </p>      
	                    <p>
		                	<label>
		                		<spring:message code="cpr.csn.note" />
		                	</label>
		                    <span class="field">
		                    	<form:input path="note" maxlength="255" cssClass="mw500" />
		                    </span>
	                    </p>
	                   <form:hidden path="id" />
	                   <p class="button-box">
	                   	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
	                   </p>
				</form:form>
					<!-- END FORM -->
					
					<!--  CSN CHANGES WRAPPER  -->
					<div class="tab-wrapp">
						<p class="form-head"><spring:message code="cpr.csn.changes" /></p>
						<c:if test="${empty csn.standardCsnChanges}">
							<p class="msg alert">
								<spring:message code="cpr.standard.changes.empty" />
							</p>
						</c:if>
						<c:if test="${not empty csn.standardCsnChanges}">
							<table class="data">
								<c:forEach items="${csn.standardCsnChanges}" var="i">
									<tr>
										<td class="b">
											<a title="Editovat" class="tt b" href="${formUrl}/standard-csn-change/${i.id}">${i.changeCode}</a>
										</td>
										<td class="last-edit"><joda:format value="${i.changed}" pattern="${common.dateTimeFormat}"/></td>
										<td class="delete big">
											<a class="confirm"  href="${formUrl}/standard-change/delete/${i.id}">
								 				<spring:message code="form.delete" />
								 			</a>
										</td>
									</tr>
								</c:forEach>
							</table>
						</c:if>
						
						<div class="inline-form r">
							<a class="lang mandate-add-btn" href="<c:url value="/admin/cpr/standard-csn/${csn.id}/change/0"  />">
								<spring:message code="cpr.standard.changes.add" />
							</a>
						</div>
					</div>
						
					</div> <!-- END ACTIVE TAB -->

					<jsp:include page="include/cpr-standard-menu4.jsp" />
					<jsp:include page="include/cpr-standard-menu5.jsp" />
					<jsp:include page="include/cpr-standard-menu6.jsp" />
				</div>	<!-- END TABs -->
		
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>