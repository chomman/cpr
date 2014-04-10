<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<tr data-tt-id="${node.id}" <c:if test="${not empty node.parent}">data-tt-parent-id="${node.parent.id}"</c:if> class="radius <c:if test="${node.isHomepage}"> is-homepage</c:if>">
		<td class="w-td-name">
			<span class="pj-ico pj-webpage-type-${node.webpageType.id}">
				<span class="w-name">
					<a:adminurl href="/webpage/edit/${node.id}">${node.defaultName}</a:adminurl>
				</span>
				<span class="pj-webpage-nav">
					<c:if test="${node.isHomepage}">
						<a:adminurl href="/webpage/add/0">
								<spring:message code="webpage.addSubpage" />
						</a:adminurl>
					</c:if>
					<c:if test="${not node.isHomepage}">
							<a:adminurl href="/webpage/add/${node.id}">
								<spring:message code="webpage.addSubpage" />
							</a:adminurl>
					</c:if>
					<a:adminurl href="/webpage/edit/${node.id}">
								<spring:message code="form.edit" />
					</a:adminurl>
					<webpage:a webpage="${node}" isPreview="true" withName="false" cssClass="preview">
								<spring:message code="form.view" />
					</webpage:a>
					<c:if test="${not node.lockedRemove}">
						<a:adminurl href="/webpage/delete/${node.id}" 
									cssClass="confirmMessage"
									extraAttr="data-message;Skutečně si přejete odstranit stránku a všechny její podstránky?"> 
							<spring:message code="form.delete" />
						</a:adminurl>			
					</c:if>
				</span>
			</span>
			
		</td>
		<td class="w50">
			<c:if test="${node.isPublished}">
				<span class="published yes tt" title="<spring:message code="published.yes.title" />" >
					<spring:message code="yes" />
				</span>
			</c:if>
			<c:if test="${not node.isPublished}">
				<span class="published no tt" title="<spring:message code="published.no.title" />" >
					<spring:message code="no" />
				</span>
			</c:if>
		</td>
		<td class="w50 c">
			<c:if test="${not empty node.webpageModule }">
				<a href="<c:url value="/admin/webpage/edit/${node.id}#settings" />" 
					class="pj-module tt" title="MODUL: <spring:message code="${node.webpageModule.name}" />" ></a>
			</c:if>
			<c:if test="${not empty node.webpageModule }">
			&nbsp;
			</c:if>
		</td>
		<td class="last-edit c">
			<span class="pj-published-by">
				<a:adminurl href="/user/edit/${node.publishedBy.id}">
					${node.publishedBy.firstName} ${node.publishedBy.lastName}
				</a:adminurl>
			</span>
			<span class="pj-published-by">
				<joda:format value="${node.published}" pattern="${common.dateTimeFormat}"/>
			</span>
		</td>
 </tr>