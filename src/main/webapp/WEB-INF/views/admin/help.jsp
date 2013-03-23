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
	 $(function() {
		 $("#help").accordion({collapsible: true, active: false });
		 $('#help-search').quicksearch("#help h3");
	 });
	</script>
	
	<div class="search-box" >
		<span title="<spring:message code="form.quicksearch.title" />" class="tt"><spring:message code="form.quicksearch" />:</span>
		<input id="help-search" type="text" />
	</div>
				
				
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
			<ol>
				<li>Nový požadavek lze přidat v sekci <strong>"CPR"</strong> v podsekci <strong>"Správa norem"</strong></li>
				<li>Je nutné vybrat (kliknout na upravit) konkrétní normu, ke které má být Požadavek přidán.</li>
				<li>Následne kliknout na <strong>2 - Správa základních požadavků</strong></li>
				<li>Zvolt stát</li>
				<li>A kliknout na tlačítko <strong> Přidat nový požadavek +</strong></li>
			</ol>
		</div>
		<h3>Kde a jak se dá přidat k normě ČSN?</h3>
		<div class="help-box">
			<ol>
				<li>ČSN lze přidat v sekci <strong>"CPR"</strong> v podsekci <strong>"Správa norem"</strong></li>
				<li>Je nutné vybrat (kliknout na upravit) konkrétní normu, ke které má být ČSN přidána.</li>
				<li>Následne kliknout na kartu <strong>3 - ČSN</strong> a <strong>Přidat ČSN k normě + </strong></li>
			</ol>
		</div>
		
		<h3>Kde a jak se dá upravovat působnost notifikovaných/autorizovaných osob?</h3>
		<div class="help-box">
			<ol>
				<li>No/AO lze přiřadit v sekci <strong>"CPR"</strong> v podsekci <strong>"Správa norem"</strong></li>
				<li>Je nutné vybrat (kliknout na upravit) konkrétní normu, ke které má být NO/AO přirazena.</li>
				<li>Následne kliknout na <strong>4 - Notifikované / autorizované osoby </strong></li>
				<li>Levý box obsahuje všechny evidované NO/AO, pravý box obsahuje vybrané (přiřazené) NO/AO k zvolené normě.</li>
				<li>Kliknutím na konkrétní osobu v levém boxu, se přiřadí a přesune do pravého boxu.</li>
				<li>Následně je nutné uložit změny, kliknutím na tlačítko <strong>"Uložit"</strong></li>
			</ol>
		</div>
		
		<h3>Kde a jak lze přiřadit ke konkrétní normě systém posuzování a ověřování stálosti vlastností?</h3>
		<div class="help-box">
			<ol>
				<li>Systém PS lze přidat v sekci <strong>"CPR"</strong> v podsekci <strong>"Správa norem"</strong></li>
				<li>Je nutné vybrat (kliknout na upravit) konkrétní normu, ke které má být systém přirazen.</li>
				<li>Následne kliknout na <strong>5 - Systémy PS a mandáty</strong></li>
				<li>Levý box obsahuje všechny Systémy PS, pravý box obsahuje vybrané (přiřazené) Systémy k zvolené normě.</li>
				<li>Kliknutím na konkrétní systém v levém boxu, se přiřadí a přesune do pravého boxu.</li>
				<li>Následně je nutné uložit změny, kliknutím na tlačítko <strong>"Uložit"</strong></li>
			</ol>
		</div>
		
		<h3>Kde a jak lze povolit u normy možnost kumulativních systémů?</h3>
		<div class="help-box">
			<ol>
				<li>Možnosť kumulativních systémů lze přidat v sekci <strong>"CPR"</strong> v podsekci <strong>"Správa norem"</strong></li>
				<li>Je nutné vybrat (kliknout na upravit) konkrétní normu, u které má být tato možnosť povolena.</li>
				<li>Následne kliknout na <strong>5 - Systémy PS a mandáty</strong></li>
				<li>Ve spodní části se nachází box <strong>Povolit možnost kumulativních systémů </strong></li>
				<li>Označením takzvaného "checkboxu" se povolí možnosť kumulativních systému v DoP formuláři pri danej normě</li>
			</ol>
		</div>
		
		<h3>Kde a jak lze povolit u normy možnost kumulativních systémů?</h3>
		<div class="help-box">
			<ol>
				<li>Možnosť kumulativních systémů lze přidat v sekci <strong>"CPR"</strong> v podsekci <strong>"Správa norem"</strong></li>
				<li>Je nutné vybrat (kliknout na upravit) konkrétní normu, u které má být tato možnosť povolena.</li>
				<li>Následne kliknout na <strong>5 - Systémy PS a mandáty</strong></li>
				<li>Ve spodní části se nachází box <strong>Povolit možnost kumulativních systémů </strong></li>
				<li>Označením takzvaného "checkboxu" se povolí možnosť kumulativních systému v DoP formuláři pri danej normě</li>
			</ol>
		</div>
		
		
		<h3>Kde a jak lze upravit informace o skupině výrobku?</h3>
		<div class="help-box">
			<ol>
				<li>Možnosť kumulativních systémů lze přidat v sekci <strong>"CPR"</strong> v podsekci <strong>"Správa skupin výrobků"</strong></li>
				<li>Je nutné vybrat (kliknout na upravit) konkrétní položku, která má být modifikována.</li>
				<li>Následne se zobrazí editační formulář s aktuálními informacemi o skupině</li>
			</ol>
		</div>
		
		
		<h3>Kde a jak lze upravit informace o již evidovaných NO/AO, alebo přidať novou?</h3>
		<div class="help-box">
			<ol>
				<li>Upravit, nebo přidat novou NO/AO je možné přidat v sekci <strong>"CPR"</strong> v podsekci <strong>"Správa AO/NO"</strong></li>
				<li>Je nutné vybrat (kliknout na upravit) konkrétní položku, která má být modifikována.</li>
				<li>Následne se zobrazí editační formulář s aktuálními informacemi o skupině</li>
			</ol>
		</div>
		
		
		<h3>Kde se nacházejí vygenerované Declaration of performace (DoP)?</h3>
		<div class="help-box">
			<p>
				Všechny vygenerované DoP se nacházejí v sekci <strong>"CPR"</strong> v podsekci <strong>"Vygenerovaná prohlášení"</strong>.
			
			</p>
		</div>
		
		<h3>Jak upravit informace o základních požadavcích podle CPR?</h3>
		<div class="help-box">
			<ol>
				<li>Manipulovat se základními požadavky dle CPR, je možné v sekci <strong>"CPR"</strong> v podsekci <strong>"Správa základních požadavků"</strong></li>
			</ol>
		</div>
		
		
		<h3>Přidal/Přidala jsem aktualitu, ale nezobrazuje se ve veřejné části systému?</h3>
		<div class="help-box">
			<p>Aby se aktuálně zobrazovala ve veřejné části systému, musí být splněny následující podmínky:</p>
			
			<ol>
				<li>Formulářové políčko <strong>Publikovat</strong>, musí být označeno</li>
				<li>Pokud je vyplněna hodnota formulářového políčka <strong>Publikovat od:</strong>, zkontrolujte zadaný čas (nesmí být menší, než aktuální)</li>
				<li>Pokud je vyplněna hodnota formulářového políčka <strong>Publikovat od: </strong>, zkontrolujte zadaný čas (nesmí být větší, než aktuální)</li>
			</ol>
		</div>
	</div>


</div>

</body>
</html>