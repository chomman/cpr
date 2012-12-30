<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CPR</title>
</head>
<body>
	<div id="wrapper">
	<div id="left">
	
		<jsp:include page="include/cpr-nav.jsp" />
		
	</div>	
	<div id="right">
		<div id="breadcrumb">
			 <a href="">Test page</a> &raquo;
			 <a href="">Test page 2</a> &raquo;
			 <span>Ovládací panel</span>
		</div>
		<h1>Vitajte v administrácii informačného systému CPR</h1>

		<div id="content">
			

			<form id="form1" class="stdform" method="post" action="">
                    	<p>
                        	<label>First Name</label>
                            <span class="field">
                            	<input type="text" name="firstname" id="firstname" class="longinput" />
                            </span>
                            
                        </p>
                        
                        <p>
                        	<label>Last Name</label>
                            <span class="field"><input type="text" name="lastname" id="lastname" class="longinput" /></span>
                        </p>
                        
                        <p>
                        	<label>Email</label>
                            <span class="field"><input type="text" name="email" id="email" class="longinput" /></span>
                        </p>
                        
                        <p>
                        	<label>Location</label>
                            <span class="field"><textarea cols="80" rows="5" name="location" class="mceEditor" id="location"></textarea></span> 
                        </p>
                        
                        <p>
                        	<label>Select</label>
                            <span class="field">
                            <select name="selection" id="selection">
                            	<option value="">Choose One</option>
                                <option value="1">Selection One</option>
                                <option value="2">Selection Two</option>
                                <option value="3">Selection Three</option>
                                <option value="4">Selection Four</option>
                            </select>
                            </span>
                        </p>                        
                        <p class="button-box">
                        	<input type="submit" class="button" value="Submit" />
                        </p>
                    </form>

		</div>	
	</div>
	<div class="clear"></div>	
</div>

</body>
</html>