<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login page</title>
</head>
<body>
	LOGIN PAGE
	
	      <form action="<c:url value="/j_spring_security_check" />" method="post">
              
              <table class="form-firma">
                <tr class="even">
                  <td class="span-3"><label for="j_username">Email:</label></td>
                  <td><input type="text" class="title span-6" name="j_username" value="<c:if test="${not empty username}">${username}</c:if>"/></td>
                </tr>
                <tr class="even">
                  <td class="span-3"><label for="j_password">Heslo:</label></td>
                  <td><input type="password" class="title span-6" name="j_password" value=""/></td>
                </tr>
                <tr>
                  <td colspan="2" class="right">
                    <input type="submit" value="Submit" class="submit"/>
                  </td>
                </tr>
              </table>
              
      </form>
</body>
</html>