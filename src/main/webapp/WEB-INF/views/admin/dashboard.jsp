<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Ovládací panel</title>
</head>
<body>
	
	<h1>Vitajte v administrácii informačného systému CPR</h1>
	
	<div id="dashboard">
		
		<div class="hbox">
			<h2>Ovládací panel</h2>
		</div>
		<ul id="speed-nav">
			<li><a href="" class="ico-cpr tt" title="Správa Construction Products Regulation">CPR</a></li>
			<li><a href="" class="ico-cal tt" title="Správa noviek">Aktuality</a></li>
			<li><a href="" class="ico-user tt" title="Správa užívateľov" >Uživateľia </a></li>
			<li><a href="" class="ico-sett tt" title="Nastavenie systému" >Nastavenia</a></li>
			<li><a href="" class="ico-info tt" title="Neviete si rady?">Pomocník</a></li>
		</ul>

		<div class="hbox">
			<h2>Štatistiky</h2>
		</div>

		<table class="data">
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
		<div class="clear"></div>
	</div>
	
</body>
</html>