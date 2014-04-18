<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<c:forEach var="node" items="${node.childrens}">
  	<jsp:include page="webpage.node.template.jsp"/>
    <c:set var="node" value="${node}" scope="request"/>
    <jsp:include page="webpage.node.jsp"/>
</c:forEach>