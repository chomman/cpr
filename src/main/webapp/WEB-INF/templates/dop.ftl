<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html  PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="cs" lang="cs">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>Poptávka</title>	
	<style type="text/css">
		body {	font-family: Tahoma, sans-serif;
			font-size: 12px;
			background: #fff;
			color: #000;
		}
.top{border-top:1px solid #333;}
.dop-items{border-collapse:collapse;}
		table{width:99%;margin:5px;}
		h1{font-size:18px;margin:0px;padding:0px;text-align:center;}
		h2{font-size:16px;padding:0;margin:0px;text-align:center;}
                .label{width:30%}
		.name,.level,.value{padding: 5px 15px;}
		.name{width:40%;height:50px;}
		.level{width:15%;text-align:center;}
		.value{width:15%;}
		.ehn{width:15%;}
		table th{background:#f7f7f7;padding:10px;}
		table td{padding:5px;}

#dop{font-size:11px;}
#dop td{border:1px solid #ccc;}
.full{display:block;padding:5px;text-align:center;margin-top:50px;}
.left,.right{display:block;padding:5px;margin-top:50px;text-align:center;width:45%}
.left{float:left;}
.right{float:right;}
.border{border-top:1px dashed #333;}

		@page {
		  size: A4;
		  margin-top: 10mm;
		  margin-right: 5mm;
		  margin-left: 5mm;
		  margin-bottom: 10mm;
		  -fs-flow-top: "header";
		  -fs-flow-bottom: "footer";
		  -fs-flow-left: "left";
		  -fs-flow-right: "right";
		}
	</style>
</head>
<body>
<div id="page">

	<h1><@spring.message code="dop" /></h1>
	<h2>č.: ${dop.numberOfDeclaration?default("")}</h2>
	
					<table class="dop-items top">
					
					<tr>
						<td class="no">1.</td>
						<td class="label"><@spring.message code="dop.productid" /></td>
						<td class="val">${dop.productId?default("")}</td>
					</tr>
					
					<tr>
						<td class="no">2.</td>
						<td class="label"><@spring.message code="dop.serialid" /></td>
						<td class="val">${dop.serialId?default("")}</td>
					</tr>
					
					<tr>
						<td class="no">3.</td>
						<td class="label"><@spring.message code="dop.intendedUse" /></td>
						<td class="val">${dop.intendedUse?default("")}</td>
					</tr>
					
					<tr>
						<td class="no">4.</td>
						<td class="label"><@spring.message code="dop.manufacturer" /></td>
						<td class="val">${dop.manufacturer?default("")}</td>
					</tr>	
					
					<tr>
						<td class="no">5.</td>
						<td class="label"><@spring.message code="dop.authorisedRepresentative" /></td>
						<td class="val">${dop.authorisedRepresentative?default("")}</td>
					</tr>	
					
					<tr>
						<td class="no">6.</td>
						<td class="label"><@spring.message code="dop.assessmentSystem" /></td>
						<td class="val">
						${dop.assessmentSystem.name?default("")}
						<#if dop.assessmentSystemNote??>
						    <em>(${dop.assessmentSystemNote})</em>
						</#if>
						<#if dop.cumulative??>
							<#if dop.assessmentSystem2??>
							 	<br />  ${dop.assessmentSystem2.name?default("")}
							 	<#if dop.assessmentSystemNote2??>
							    	<em>(${dop.assessmentSystemNote2})</em>
								</#if>
							</#if>
						</#if>
						</td>
					</tr>	
					</table>
					<table>
					<tr>
						<td class="no">7.</td>
						<td><strong><@spring.message code="dop.point7" /></strong>
							${point7?default("")}
						</td>
					</tr>
					
					<tr>
						<td class="no">8.</td>
						<td><strong><@spring.message code="dop.point8" /></strong>
							${dop.eta?default("")}
						</td>
					</tr>
					<tr>
						<td class="no">9.</td>
						<td></td>
					</tr>
					
				</table>	
				
				<table id="dop">
					<thead>
						<tr>
							<th><@spring.message code="dop.table.essentialCharacteristics" /></th>
							<th><@spring.message code="dop.table.performance" /></th>
							<th><@spring.message code="dop.table.ehn" /></th>
						</tr>
					</thead>
					
					<tbody>
						<#list dop.essentialCharacteristics as i >
							<tr>
								<td class="name">${i.requirement.name?default("")}</td>
								<td class="value">${i.value?default("")}</td>
								<td class="ehn">
									<strong>${dop.standard.standardId?default("")}</strong>
									<em>${i.requirement.section?default("")}</em>
								</td>
							</tr>
						</#list>
					</tbody>
				</table>
						
				<table class="dop-items">
					<tr>
						<td class="no">10.</td>
						<td><strong><@spring.message code="dop.10" /><br /><@spring.message code="dop.11" /></strong></td>
					</tr>
				</table>
				
				
				<p class="full border">
					<@spring.message code="dop.sig.function" />
				</p>
					
				<p class="left border">
					<@spring.message code="dop.sig.left" />
				</p>
				<p class="right border">
					<@spring.message code="dop.sig.left" />
				</p>			
	
						
</div>
</body>
</html>