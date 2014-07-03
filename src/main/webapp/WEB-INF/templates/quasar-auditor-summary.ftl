<!DOCTYPE html  PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="cs" lang="cs">
<head>
	<title>
		${eFunctions.auditor.nameWithDegree?default("")}
	</title>	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<style type="text/css">
	html, body, *{margin:0;padding:0}
	body{font-family:Arial, Tahoma, sans-serif;}
	#wrapp{padding:5px 10px 15px 10px;border:1px solid #ccc;}
	h1{font-size:18px;padding-bottom:10px}
	table.qs-scope,
	div.qs-scope h2{font-size:15px;padding-bottom:5px;font-weight:bold;}
	div.qs-scope div{width:96%;min-height:100px;font-size:13px;}
	div.qs-scope{margin:20px 0;}
	div.qs-scope div{border:1px solid #555;padding:10px;}
	table.qs-scope{width:100%;margin:20px 0;padding:10px 0;border-top:2px solid #ccc;border-bottom:5px solid #ccc;border-collapse:collapse;border-right:1px solid #eee;border-left:1px solid #eee;}
	table.qs-scope td{padding:10px 10px;border-bottom:1px solid #eee;}
	@page {
		  size: A4;
		  margin-top: 5mm;
		  margin-right: 5mm;
		  margin-left: 5mm;
		  margin-bottom: 5mm;
		  -fs-flow-top: "header";
		  -fs-flow-bottom: "footer";
		  -fs-flow-left: "left";
		  -fs-flow-right: "right";
		}
	</style>
</head>
<body>
<#setting locale="cs">

	<h1><@spring.message code="qualificationScope" /></h1>
<div id="wrapp">
	<table class="qs-scope">
	  <tr>
	    <td><@spring.message code="auditor.name" /></td>
	    <td><strong>${eFunctions.auditor.nameWithDegree?default("")}</strong></td>
	  </tr>
	   <tr>
	    <td><@spring.message code="auditor.itcId" /></td>
	    <td><strong>${eFunctions.auditor.itcId?default("")}</strong></td>
	  </tr>
	   <tr>
	    <td><@spring.message code="auditor.reassessmentDate.short" /></td>
	    <td><strong>${reassessmentDate?default("")}</strong></td>
	  </tr>
	 </table>
	 
	 <div class="qs-scope">
		<h2><@spring.message code="qsAuditor.scope" /></h2>
		<div class="mw500 mh100">${eFunctions.qsAuditor.grantedCodes}</div>
	</div>
	<div class="qs-scope">
		<h2><@spring.message  code="qsAuditor.scope" /></h2>
		<div class="mw500 mh100">${eFunctions.qsAuditor.grantedCodes}</div>
	</div>
	
	<div class="qs-scope">
		<h2><@spring.message code="productAssessorA.scope" /></h2>
		<div class="mw500 mh100">${eFunctions.productAssessorA.grantedCodes}</div>
	</div>
	
	<div class="qs-scope">
		<h2><@spring.message code="productAssessorR.scope" /></h2>
		<div class="mw500 mh100">${eFunctions.productAssessorR.grantedCodes}</div>
	</div>
	
	<div class="qs-scope">
		<h2><@spring.message code="productSpecialist.scope" /></h2>
		<div class="mw500 mh100">${eFunctions.productSpecialist.grantedCodes}</div>
	</div>
	
</div>
</body>
</html>