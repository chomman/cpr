<!DOCTYPE html  PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="cs" lang="cs">
<head>
	<title>
		<#if type == 1>
			<@spring.message code="invoice.proforma" />: 
		<#elseif type == 2>
			<@spring.message code="invoice.command" />: 
		<#else>	
			<@spring.message code="invoice.vpp" />:
		</#if>
		${portalOrder.orderNo?string}
	</title>	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<style type="text/css">
	html, body, *{margin:0;padding:0}
	body{padding:5px 10px 15px 10px;border:1px solid #555;font-family:Arial, Tahoma, sans-serif;}
	h1{font-size:18px;padding-bottom:10px}
	span{display: block;padding-top:1px;padding-bottom: 1px;}
	.tg .label{font-weight: bold;background:#f5f5f5;font-size: 12px;text-align: center;}
	.tg {border-collapse:collapse;border-spacing:0;width:100%}
	.tg td{font-size:11px;padding:5px 10px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
	.tg td .big{font-size:14px;font-weight: bold;padding-bottom: 10px;}
	.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;}
	.tg td .bx{padding-top:20px;}
	.tg .info{vertical-align:top;}
	.big-coll{vertical-align:top;}
	.total{text-align: right;font-size: 17px;font-weight: bold;}
	.total .big-price{font-weight: bold;font-size:15px;padding-top:8px}
	.footer{padding-top: 20px; font-size: 12px;}
	.items-wrapp{min-height: 440px;padding-top:10px;margin-top:5px;}
	.item{font-size:12px;padding:3px;}
	.total-wrapp{min-height: 130px;font-size:13px;}
	.total-wrapp span{padding:2px;}
	.total-wrapp i{display:inline-block;width:100px;}
	.w50{width:50%} 
	i{font-style:normal;}
	.bigger{font-size:14px;padding-bottom:10px;}
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
	<script type="text/javascript">
	<!--
	window.print();
	//-->
	</script>
</head>
<body>
<#setting locale="cs">
<div id="wrapp">
	<h1>
		<#if type == 1>
			<@spring.message code="invoice.proforma" />: 
		<#elseif type == 2>
			<@spring.message code="invoice.command" />: 
		<#else>	
			<@spring.message code="invoice.vpp" />:
		</#if>
		${portalOrder.orderNo?string}
	</h1>
	
	<table class="tg">
	  <tr>
	    <td class="label"><@spring.message code="invoice.supplier" /></td>
	    <td class="label"><@spring.message code="invoice.purchaser" /></td>
	  </tr>
	  <tr>
	    <td class="w50 info">
	    	<span class="big">${settings.companyName?default("")}</span>
	    	<span>${settings.street?default("")}</span>
	    	<span>${settings.zip?default("")}, ${settings.city?default("")}</span>
	    	<span>Česká republika</span>

	    	<span class="bx block">
	    		<span><strong><@spring.message code="invoice.ic" />:</strong> ${settings.ico?default("")}</span>
	    		<span><strong><@spring.message code="invoice.dic" />:</strong>${settings.dic?default("")}</span>
	    	</span>
	    </td>
	    <td class="w50 info">
	    	<#if portalOrder.companyName?length &gt; 0>
	    		<span class="big">${portalOrder.companyName?default("")}</span>
	    		<span>${portalOrder.firstName?default("")} ${portalOrder.lastName?default("")}</span>
	    	<#else>
	    		<span class="big">${portalOrder.firstName?default("")} ${portalOrder.lastName?default("")}</span>
	    	</#if>
	    	<span>${portalOrder.street?default("")} </span>
	    	<span>${portalOrder.zip?default("")}, ${portalOrder.city?default("")}</span>
	    	<span>${customerCountry?default("")}</span> 
	    	<span class="bx block">
	    	<#if portalOrder.ico?length &gt; 0>
			   <span>
					<strong><@spring.message code="invoice.ic" />:</strong> 
					${portalOrder.ico?default("")}
			   </span>
			</#if>
			<#if portalOrder.dic?length &gt; 0>
				<span>
					<strong><@spring.message code="invoice.dic" />:</strong>
					${portalOrder.dic?default("")}
				</span>
			</#if>
			</span>
	    </td>
		</tr>
		<tr>
			<td>
				<span>
					<strong><@spring.message code="invoice.bank.account" />:</strong> 
					<#if portalOrder.isInEuro >
						${settings.euAccountNumber?default("")}
					<#else>
						${settings.czAccountNumber?default("")}
					</#if>
				</span>
			</td>
			<td>
				<span>
					<b><@spring.message code="invoice.orderNo" />:</b> 
					${portalOrder.orderNo?default("")}
				</span>	
			</td>
		</tr>
		<tr>
			<td>
				<span>
					<strong>IBAN:</strong> 
					<#if portalOrder.isInEuro >
						${settings.euIban?default("")}
					<#else>
						${settings.czIban?default("")}
					</#if>
				</span>
			</td>
			<td>
				<span> 
					<strong><@spring.message code="invoice.payment.method" />:</strong> 
					<@spring.message code="invoice.payment.method.bank" />
				</span>
			</td>
		</tr>
		<tr>
			<td>	
				<span>
					<strong>SWIFT:</strong> 
					<#if portalOrder.isInEuro >
						${settings.euSwift?default("")}
					<#else>
						${settings.czSwift?default("")}
					</#if>
				</span>
			</td>
			<td> 	
				<span>
					<strong><@spring.message code="invoice.created" />:</strong> 
					${created?default("")}
				</span>
			</td>
		</tr>
				<tr>
			<td>
				<span>
					<strong><@spring.message code="invoice.vs" />:</strong> 
					${portalOrder.orderNo?default("")}
				</span>
			</td>
			<td>
				<#if type == 2>
					<strong>DUZP:</strong> 
					${portalOrder.duzp?default("")}
				</#if>
			</td>
		</tr>
			<tr>
			<td class="w50 big-coll" colspan="2">
				<#if type == 3 >
				<span class="bigger">
					<@spring.message code="invoice.paymentDate" />:
					${portalOrder.paymentDate?default("")}
				</span>
				</#if>
				<span><strong><@spring.message code="invoice.items" />: </strong></span>
				<span class="items-wrapp">
					<#assign idx = 1>
					<#list portalOrder.orderItems as i >
						<span class="item"><i>${idx}.</i>  ${i.portalProduct.czechName?default("")}</span>
						<#assign idx = idx + 1>
					</#list>
				</span>
			</td>
		</tr>
		<tr>
			<td class="total" colspan="2">
				<span class="total-wrapp">
					<#if type == 2 || type == 3>
						<span>
							<@spring.message code="invoice.price" />:
							<i>${portalOrder.totalPriceWithCurrency?default("")}</i>
						</span>
						<span>
							<@spring.message code="vat" /> (${portalOrder.percentageVat?default("")}):
							<i>${portalOrder.vatPriceValueWithCurrency?default("")}</i> 
						</span>
					</#if>
					<span class="big-price">
						<@spring.message code="invoice.total" />: 
						<i>${portalOrder.totalPriceWithVatAndCurrency?default("")}</i>
					</span>
				</span>
			</td>
		</tr>
		<tr>
			<td colspan="2"></td>
		</tr>
	
	</table>
	
	<div class="footer">
		<span>Vystavil: ${settings.portalAdminName?default("")}</span>
		<span>${settings.portalAdminContact?default("")}</span>
	</div>
	
</div>
</body>
</html>