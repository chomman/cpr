<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="dashboard.help"/></title>
</head>
<body>
<div id="wrapper">

	<div id="breadcrumb">
		 <a href="<c:url value="/admin/" />"><spring:message code="menu.home" /></a> &raquo;
		 <span><spring:message code="dashboard.help"/></span>
	</div>
		
	<h1><spring:message code="dashboard.help"/></h1>
			
	 <script>
	 $(function() {$( "#help" ).accordion(); });
	</script>
	<div id="help">
		<h3>Kde a jak mohu přidat novou normu?</h3>
		<div class="help-box">
			<ol>
				<li>Novou normu lze přidat v sekci <strong>"CPR"</strong> v podsekci <strong>"Správa norem"</strong></li>
				<li>Kliknutím na kartu <strong>Přidat novou normu</strong></li>
				<li>Následně je nutné zadat základní informace o přidávání normě a kliknout na tlačítko "Uložit"</li>
			</ol>
		</div>
		<h3>Norma je přidána, ale nezobrazuje se ve veřejné sekci systému.</h3>
		<div class="help-box">
			<p>
			Aby se evidována norma zobrazovala i ve veřejné části systému, je nutné aby byla v základních nastaveních normy zaškrtunutá položka <strong>Publikovat</strong>.
			</p>
		</div>
		<h3>Kde a jak mohu přidat požadavek k evidované normě?</h3>
		<div class="help-box">
			<p>
			Nam enim risus, molestie et, porta ac, aliquam ac, risus. Quisque lobortis.
			Phasellus pellentesque purus in massa. Aenean in pede. Phasellus ac libero
			ac tellus pellentesque semper. Sed ac felis. Sed commodo, magna quis
			lacinia ornare, quam ante aliquam nisi, eu iaculis leo purus venenatis dui.
			</p>
			<ul>
			<li>List item one</li>
			<li>List item two</li>
			<li>List item three</li>
			</ul>
		</div>
		<h3>Section 4</h3>
		<div class="help-box">
			<p>
			Cras dictum. Pellentesque habitant morbi tristique senectus et netus
			et malesuada fames ac turpis egestas. Vestibulum ante ipsum primis in
			faucibus orci luctus et ultrices posuere cubilia Curae; Aenean lacinia
			mauris vel est.
			</p>
			<p>
			Suspendisse eu nisl. Nullam ut libero. Integer dignissim consequat lectus.
			Class aptent taciti sociosqu ad litora torquent per conubia nostra, per
			inceptos himenaeos.
			</p>
		</div>
	</div>


</div>

</body>
</html>