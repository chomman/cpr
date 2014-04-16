<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<sec:authorize access="hasRole('ROLE_WEBMASTER')">	
	<c:set var="isLoggedWebmaster" value="true"/>
</sec:authorize>

<!DOCTYPE html>
<html>
<head>
	<title><spring:message code="webpages" /></title>
	<link rel="stylesheet" href="<c:url value="/resources/admin/css/webpages.css" />" />
	<link rel="stylesheet" href="<c:url value="/resources/admin/js/jstree/themes/default/style.css" />" />
	<script src="<c:url value="/resources/admin/js/jstree/jstree.min.js" />"></script>
	<script>
	$(function () {
	  $("#jstree").jstree({
		    "core" : {
		      "check_callback" : true
		    },
		    "plugins" : [ "dnd" ]
		  });
	  
	  $(document).on('click', 'ul .pj-webpage-nav a:not(.preview)', function(e){
		 console.log('test');
		 e.stopImmediatePropagation();
		 document.location.href = $(this).attr('href');
	  });
	  
	  $(document).on('dnd_stop.vakata', function(e , dnd  ){
		  var oldPos = dnd.data.pos,
		  	  newPos = getPosition(dnd.data.pos.id);
		  console.log(oldPos);
		  console.log(newPos);
		  
		  if(newPos.order != oldPos.order || newPos.parent != oldPos.parent){
			  console.log('position changed');
		  } 
	  });
	  
	  $(document).on('dnd_start.vakata', function(e , dnd  ){
		  dnd.data.pos = getPosition(dnd.data.nodes);
		  console.log(dnd.data.pos);
	  });
	  
	  $(document).on('treechanged', function(e , oldPos  ){
		  var newPos = getPosition(oldPos.id);
		  console.log('new pos: ',newPos);
		  if(newPos.order != oldPos.order || newPos.parent != oldPos.parent){
			  console.log('position changed');
		  } 
	  });
	  
	  
	  function getPosition(id){
		    var $li = $("#" + id);
		    if($li.length == 1){
		        console.log('returning');
		        return {
		           id : id,
		           order : getOredFor($li),
		           parent : getParentFor($li)
		        };
		    }

		    return null;
		}

		function getOredFor($li){
		    return $li.index();
		}

		function getParentFor($li){
		    var parent = getInstance().get_parent($li);
		    console.log("parent: " + parent);
		    if(parent !== "#"){
		        return parent;
		    }
		    return null;
		}


		function getInstance(){
		    return $.jstree.reference('#jstree');
		}

	  
	});
	</script>
</head>
<body>
	<div id="wrapper">
	<div class="pj-webpages">
		
		<div id="content">
			<div id="breadcrumb">
				<a:adminurl href="/"><spring:message code="menu.home" /></a:adminurl>
				 &raquo;
				 <span><spring:message code="webpages" /></span>
			</div>
			
			
			<c:if test="${not empty successDelete}">
				<p class="msg ok"><spring:message code="success.delete" /></p>
			</c:if>
			
			<c:if test="${not empty isNotEmptyError}">
				<p class="msg error"><spring:message code="cpr.group.isnotempty" /></p>
			</c:if>
			
			
				
			
			<div class="root-node">
				www.nlfnorm.cz
				<div class="pj-webpage-nav">
					<a:adminurl href="/webpage/add/0"  cssClass="pj-ico pj-add tt" title="Přidat podstránku">
							<span></span>
					</a:adminurl>
					
					<a:adminurl href="/webpage/edit/${node.id}"  cssClass="pj-ico pj-edit tt" title="Upravit">
						<span></span>
					</a:adminurl>
					<webpage:a webpage="${model.homepage}" isPreview="true" withName="false" cssClass="pj-ico  preview tt" title="Zobrazit">
						<span></span>
					</webpage:a>
				</div>
			</div>
				
			<c:if test="${not empty model.webpages}">
				<div id="jstree">
					<ul id="0">
						  <c:forEach items="${model.webpages}" var="node" varStatus="s"  >
						 	<c:set var="node" value="${node}" scope="request"/>
						 	<c:set var="s" value="${s}" scope="request"/>
						 	<jsp:include page="webpage.li.node.jsp" />
						 </c:forEach>
					</ul>	 
				</div>
			</c:if>
			
			 
			<c:if test="${empty model.webpages}">
				<p class="msg alert">
					<spring:message code="alert.empty" />
				</p>
			</c:if>

		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>