<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<%@ page trimDirectiveWhitespaces="true" %>
<ul id="0">
	  <c:forEach items="${model.webpages}" var="node" varStatus="s"  >
	 	<c:set var="node" value="${node}" scope="request"/>
	 	<c:set var="s" value="${s}" scope="request"/>
	 	<jsp:include page="webpage.li.node.jsp" />
	 </c:forEach>
</ul>