<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Ovládací panel</title>
</head>
<body>
		<div id="breadcrumb">
			 <a href="<c:url value="/" />">Test page</a> &raquo;
			 <a href="<c:url value="/" />">Test page 2</a> &raquo;
			 <span>Ovládací panel</span>
		</div>
	
	<div id="dashboard">
		
		
		<h1>Vitajte v administrácii informačného systému CPR</h1>
		
		<div id="speed-nav">
			<a href=""></a>
		</div>
		
		<table class="stats">
			<tr>
				<td>Počet evidovaných norem: </td>
				<td>125</td>
			</tr>
			
			<tr>
				<td>Počet evidovaných kategórií CPR: </td>
				<td>35</td>
			</tr>
			
			<tr>
				<td>Počet registrovaných uživateľov: </td>
				<td>89</td>
			</tr>
			
		</table>
	
	</div>
</body>
</html>