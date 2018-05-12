<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DocType html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
<form name="form-logout" action="/logout" method="post">
<button onclick="document.form-logout.submit()">logout</button>
</form>

<form name="SendMessage" action="/PostMessage" method="post">
<div class="form-style-8" style="width:200px;">
<div> 
   <c:forEach var="list" items="${usermessages}">
       <option value="${list.}">${list.getUsername()}</option>   
   </c:forEach>
</div>
<textarea rows="5" cols="25" name="message"></textarea>
<button onclick="document.SendMessage.submit()">MessagesPage</button>
</div>
</form>


</body>
</html>