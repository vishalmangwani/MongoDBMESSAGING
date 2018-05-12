<!DocType html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>
<div class="login-page">
  <div class="form" >
    <form class="register-form" name="form1" action="/addUser" method="POST">
      <input type="text" placeholder="name" name="name"/>
      <input type="password" placeholder="password" name="password"/>
      <input type="text" placeholder="email address" name="username"/>
      <button onclick="document.form1.submit()">create</button>
      <p class="message">Already registered? <a href="#">Sign In</a></p>
    </form>
    <form name="form2" class="login-form" action="/updateUser" method="POST">
      <input type="text" name="username" placeholder="username"/>
      <input type="password" name="password" placeholder="password"/>
      <button onclick="document.form2.submit()">login</button>
      <p class="message">Not registered? <a href="#">Create an account</a></p>
    </form>
  </div>
</div>
</body>
</html>