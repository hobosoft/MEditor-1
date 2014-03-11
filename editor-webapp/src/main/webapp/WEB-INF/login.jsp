<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String hostname = (String) request.getAttribute("hostname");

%>
<!doctype html>
<html>
  <head>
    <script type="text/javascript">
      var _gaq = _gaq || [];
      _gaq.push([ '_setAccount', 'UA-19922555-1' ]);
      _gaq.push([ '_trackPageview' ]);
      
      (function() {
      	var ga = document.createElement('script');
      	ga.type = 'text/javascript';
      	ga.async = true;
      	ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
      	var s = document.getElementsByTagName('script')[0];
      	s.parentNode.insertBefore(ga, s);
      })();
    </script>
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet" media="screen" />
    <style type="text/css">
      #logo {margin:5px; margin-bottom:30px;}
      #authn {margin:5px;}
      #ldap  {margin:25px; margin-top:5px;}
      #shib  {margin:5px; margin-left:25px;}
      .show  {display: block;}
      .hide  {display: none;}
    </style>
    <title>Login</title>
  </head>
  <body>
    <script src="../js/jquery-1.9.1.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <div id="logo"><a href="http://code.google.com/p/meta-editor/"><img src="../images/editor_logo.png" alt="editor logo" title="Version ${project.version}" /> </a> <br /> <a href="http://code.google.com/p/meta-editor/">Project Page</a>
    </div>
    <div class="alert alert-info fade in hide" id="notification">
      <button type="button" class="close" data-dismiss="alert">&times;</button>
      <div id="login_error"></div>
    </div>
    <div id="authn">Autentizace</div>
    <div class="tabbable">
    
    
    
      <!-- Menu, set the style to show/hide if you want to disable some authentication method -->
      <ul class="nav nav-tabs">
      
        <!-- OpenID -->
        <li class="active"><a href="#tab1" data-toggle="tab">OpenID</a></li>

          <!-- LDAP -->
          <li class="${filter.ldap}"><a href="#tab2" data-toggle="tab">LDAP</a></li>

          <!-- Shibboleth -->
          <li class="${filter.shib}"><a href="#tab3" data-toggle="tab">Shibboleth</a></li>
      </ul>
      
      
      
      <div class="tab-content">
        <div class="tab-pane active" id="tab1">
          <form name="f" action="../meditor/j_spring_security_check" method="POST">
            <iframe src="https://metaeditor.rpxnow.com/openid/embed?token_url=<%=hostname%>%2Fmeditor%2Fjanrain_spring_security_check" scrolling="no" frameBorder="no" allowtransparency="true" style="width: 400px; height: 240px" action="../meditor/janrain_spring_security_check" method="POST"></iframe>
          </form>
        </div>
        <div class="tab-pane" id="tab2">
          <form name="f" action="../meditor/j_spring_security_check" method="POST">
            <table id="ldap">
              <tr>
                <td>Login:</td>
                <td><input type="text" name="j_username" value="" /></td>
              </tr>
              <tr>
                <td>Password:</td>
                <td><input type="password" name="j_password" /></td>
              </tr>
              <tr>
                <td colspan="2"><input name="submit" type="submit" value="Login" /></td>
              </tr>
            </table>
          </form>
        </div>
        <div class="tab-pane" id="tab3">
          <a id="shib" class="login" href="http://<%=hostname%>/Shibboleth.sso/Login?target=https%3A%2F%2F<%=hostname%>%2Fmeditor%2Fshibboleth_spring_security_check">
          <img border="0" src="../images/shibboleth.png" alt="shibboleth logo" width="350px"/> </a>
        </div>
      </div>
    </div>
    <script type="text/javascript" language="javascript">
      var isError = function() {
      	var query = window.location.search.substring(1);
      	var vars = query.split("&");
      	for ( var i = 0; i < vars.length; i++) {
      	  var pair = vars[i].split("=");
      	  if (pair[0] === "login_error") {
            if (pair[1] == 1) {
              return "Unable to login - wrong username or password, please check the credentials and try it again.";
            } else if (pair[1] == 2) {
      	      return "Unable to login - please check whether the database is running. There should be more info in the server log.";
      	    }
          }
      	}
      	return false;
      }();
      if (isError) {
      	document.getElementById('login_error').innerHTML = isError;
      	$(".alert").fadeIn();
      }
    </script>
  </body>
</html>
