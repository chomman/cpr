<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>404 - <spring:message code="error.pageNotFound" /></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value="/resources/errors/404/404.css" />" />
  </head>
<body>

<div class="container">
      <div id="#wrapper">
          <header class="clearfix">
            <div class="row c">
              <div>
                <h3 class="brand">
                  <a title="404" href="<c:url value="/" />">www.nlfnorm.cz</a>
                </h3>
              </div>
            </div>
          </header>
          
	      <article> 
	        <!-- Tab panes -->
	        <div class="tab-content-wrapper">
	          <div id="home" class="tb-content active">
	            <div class="box"> <span class="section-icon"><i class="ico-smile"></i></span>
	              <h1>404</h1>
	              <h4><spring:message code="error.pageNotFound" /></h4>
	              <p><spring:message code="error.pageNotFound.descr" /></p>
	            </div>
	            <form class="form-dark" id="search" method="get" action="<c:url value="/" />informacni-portal/search">
	              <div class="input-group">
	                <input type="text" placeholder="Vyhledat..." name="q" id="s">
	                <input type="submit" value="" id="search-submit">
	              </div>
	            </form>
	          </div>
	             
	        </div>
	    </article>
      </div>
</div>
</body>
</html>