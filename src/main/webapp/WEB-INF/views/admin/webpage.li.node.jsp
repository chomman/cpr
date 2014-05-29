<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ page trimDirectiveWhitespaces="true" %>
<c:if test="${not node.isHomepage}">
	<li
	 ${not empty node.childrens ? 'class="jstree-closed" ' : ''}
	 data-order="${node.order}" data-jstree='{"icon": "pj-enabled-${node.isPublished} pj-type-${node.webpageType.id}"}' id="${node.id}" class="pj-enabled-${node.isPublished} pj-type-${node.webpageType.id}">
		<c:if test="${fn:length(node.defaultName) gt 50}">
			${fn:substring(node.defaultName, 0, 50)}...
		</c:if>
		<c:if test="${fn:length(node.defaultName) lt 51}">
			${node.defaultName}
		</c:if>
			<span class="pj-webpage-nav">
					<a:adminurl href="/webpage/add/${node.id}"  cssClass="pj-ico pj-add tt" title="Přidat podstránku">
						<span></span>
					</a:adminurl>
					<a:adminurl href="/webpage/edit/${node.id}"  cssClass="pj-ico pj-edit tt" title="Upravit">
						<span></span>
					</a:adminurl>
					<webpage:a webpage="${node}" isPreview="true" withName="false" cssClass="pj-ico  preview tt" title="Zobrazit">
						<span></span>
					</webpage:a>
					<c:if test="${not node.lockedRemove}">
						<a:adminurl href="/webpage/delete/${node.id}" 
									cssClass="confirmMessage pj-ico pj-del tt"
									title="Odstranit"
									extraAttr="data-message;Skutečně si přejete odstranit stránku a všechny její podstránky?"> 
							<span></span>
						</a:adminurl>			
					</c:if>
				</span>
	</li>
</c:if>