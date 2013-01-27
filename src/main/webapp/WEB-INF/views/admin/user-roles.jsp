<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="user.roles.detail" /></title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/user-nav.jsp" />
		
	</div>	
	<div id="right">
		
		<div id="breadcrumb">
			 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
			 <a href="<c:url value="/admin/users" />"><spring:message code="menu.users" /></a> &raquo;
			 <span><spring:message code="user.roles.detail" /></span>
		</div>
		<h1><spring:message code="user.roles.detail" /></h1>

		<div id="content">
			
			<!-- 
			<div class="role">
				<div class="hbox">
					<h3>Administrátor</h3>
				</div>
				<div class="role-lable">
					 	<p>Administrátor má právo na všechny operace v systému.</p>
					 	<ul>
					 		<li>uživatel s rolí administrátor má možnost registrovat nových uživatelů systému, upravovat již existující účty, nebo jejich odstranit</li>
					 		<li>Může kontrolovat uživatelské přístupy</li>
					 		<li>Má možnost manipulovat s nastavením systému</li>
					 	</ul> 
				
				</div>
			</div>
			<div class="role">
				<div class="hbox">
					<h3>Editor</h3>
				</div>
				<div class="role-lable">
					 	<p>Uživatelská role s omezenými právy.</p>
					 	<ul>
					 		<li>Uživatel s přidělenou rolí Editor má plný přístup do sekce CPR.</li>
					 		<li>Má možnost přidávat, editovat, publikovat a odstraňovat aktuality</li>
					 		<li>Má možnost manipulovat se základním nastavením systému.</li>
					 	</ul> 
				
				</div>
			</div>
			 -->				
			<c:if test="${not empty model.roles}">
				<c:forEach items="${model.roles}" var="role">
						<div class="role">
							<div class="hbox">
								<h3>${role.name}</h3>
							</div>
							<div class="role-lable">
								${role.longDescription}
							</div>
						</div>
							
				</c:forEach>
			</c:if>

		
		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>