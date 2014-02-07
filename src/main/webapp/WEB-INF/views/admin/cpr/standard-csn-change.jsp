<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>${csn.csnName}</title>
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
			
			
			<c:if test="${standardCsnChange.id == 0}">
			 	<span><spring:message code="cpr.csn.change.add" arguments="${csn.csnName}" /></span>
			</c:if>
			<c:if test="${standardCsnChange.id != 0}">
				<span><spring:message code="cpr.csn.change.edit"/></span>
			</c:if>
		</div>
		
		
		<h1>	
			<c:if test="${standardCsnChange.id == 0}">
			 	<spring:message code="cpr.csn.change.add" arguments="${csn.csnName}" />
			</c:if>
			<c:if test="${standardCsnChange.id != 0}">
				${csn.csnName} - <spring:message code="cpr.csn.change.edit"/>
			</c:if>
		</h1>

		<div id="content">
			
			<div id="req-nav">							
				
				<a href="<c:url value="/admin/cpr/standard-csn/${csn.id}/change/0"  />">
					 <spring:message code="cpr.csn.change.add"  arguments="${csn.csnName}"  />
				</a>
				
			</div>			
								
			<form:form commandName="standardCsnChange" method="post" cssClass="valid" >
				<p class="form-head">${csn.csnName}</p>
		
				<c:if test="${not empty successCreate}">
					<p class="msg ok"><spring:message code="success.create" /></p>
				</c:if>
		
				<form:errors path="*" delimiter="<br/>" element="p" cssClass="msg error"  />
				
				<p>
				 	<label>
				 		<strong><em class="red">*</em>
				 			<spring:message code="cpr.csn.change.code" />
				 		</strong>
				 	</label>
				     <span class="field">
				     	<form:input path="changeCode"  cssClass="w200 required" />
				     </span>
				 </p>
				
				 <p>
                	<label>
               			<spring:message code="cpr.csn.onlineid" />:
                	</label>
                    <span class="field">
                    	<form:input path="csnOnlineId" maxlength="10" cssClass="w100" />
                    	<span class="norminfo" >http://www.sgpstandard.cz/editor/files/on_line/csn-redirect.php?k=<strong class="red">90588</strong></span>
                    </span>
                 </p>
				 <p>
				 	<label>
				 		<spring:message code="cpr.csn.change.date" />
				 	</label>
				     <span class="field">  
				     	<form:input path="date" maxlength="25" cssClass="date"  />
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
				
				 <form:hidden path="id"  />
				 <p class="button-box">
				 	 <input type="submit" class="button" value="<spring:message code="form.save" />" />
				 </p>
		 </form:form>
				
		<c:if test="${not empty csn.standardCsnChanges}">					
			<div>
				<p class="form-head"><spring:message code="cpr.csn.change.other" arguments="${csn.csnName}" /></p>
				 <table class="data">
					 <c:forEach items="${csn.standardCsnChanges}" var="j">
			 		 	<c:if test="${j.id != standardCsnChange.id}">
				 		 	<tr class="standard-csn-change">
				 		 		<td class="standarardId">${j.changeCode}</td>
				 		 		<td class="last-edit">
						 			<c:if test="${empty j.date}">-</c:if>
						 			<c:if test="${not empty j.date}">
						 				<span class="tt" 
						 					  title="<spring:message code="cpr.csn.change.date" />">
						 				<joda:format value="${j.date}" pattern="dd.MM.yyyy"/>
						 				</span>
						 			</c:if>
						 		</td>
						 		<td class="edit">												
						 			<a class="tt" title="Upraviť položku?" href="<c:url value="/admin/cpr/standard-csn/${csn.id}/change/${j.id}"  />">
						 				<spring:message code="form.edit" />
						 			</a>
						 		</td>
						 		<td class="delete">
						 			<a class="confirm" href="<c:url value="/admin/cpr/standard-csn/${csn.id}/change/${j.id}/delete"  />">
						 				<spring:message code="form.delete" />
						 			</a>
						 		</td>
				 		 	</tr>
				 		 </c:if>
			 		 </c:forEach>
		 		 </table>
			</div>					
		</c:if>
		
							
			<span class="note"><spring:message code="form.required" /></span>
		</div>	
	</div>
	
	
	<div class="clear"></div>	
</div>

</body>
</html>