<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<ul class="w-nav">
	
	<li ${webpageModel.type == 2 ? 'class="active"'  : '' }>
		<a href="<a:url href="/widget/registrace/2" linkOnly="true" />${webpageModel.params}">
			<spring:message code="widget.about" />
		</a>
	</li>
	
	<li ${webpageModel.type == 1 ? 'class="active"'  : '' }>
		<a href="<a:url href="/widget/registrace/1" linkOnly="true" />${webpageModel.params}">
			<spring:message code="widget.registation" />
		</a>
	</li>
	
	<li>
		<a class="pj-fwd" target="_blank" href="<a:url href="/widget/registrace/6${webpageModel.params}" linkOnly="true" />">
			<spring:message code="widget.enter" />
			<span class="pj-ico"></span>
		</a>
	</li>
	
</ul>