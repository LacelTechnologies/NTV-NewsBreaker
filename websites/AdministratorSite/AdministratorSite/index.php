<?php require_once('../Connections/sVoice_conn.php'); ?>
<?php
if (!function_exists("GetSQLValueString")) {
function GetSQLValueString($theValue, $theType, $theDefinedValue = "", $theNotDefinedValue = "") 
{
  $theValue = get_magic_quotes_gpc() ? stripslashes($theValue) : $theValue;

  $theValue = function_exists("mysql_real_escape_string") ? mysql_real_escape_string($theValue) : mysql_escape_string($theValue);

  switch ($theType) {
    case "text":
      $theValue = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
      break;    
    case "long":
    case "int":
      $theValue = ($theValue != "") ? intval($theValue) : "NULL";
      break;
    case "double":
      $theValue = ($theValue != "") ? "'" . doubleval($theValue) . "'" : "NULL";
      break;
    case "date":
      $theValue = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
      break;
    case "defined":
      $theValue = ($theValue != "") ? $theDefinedValue : $theNotDefinedValue;
      break;
  }
  return $theValue;
}
}
?>
<?php
// *** Validate request to login to this site.
if (!isset($_SESSION)) {
  session_start();
}

$loginFormAction = $_SERVER['PHP_SELF'];
if (isset($_GET['accesscheck'])) {
  $_SESSION['PrevUrl'] = $_GET['accesscheck'];
}

// *** set up a connection to the database and declare variables
if (isset($_POST['adminEmail'])) {
  $loginUsername=$_POST['adminEmail'];
  $password=$_POST['adminPassword'];
  $MM_fldUserAuthorization = "";
  $MM_redirectLoginSuccess = "adminPage.php";
  $MM_redirectLoginFailed = "#alert";
  $MM_redirecttoReferrer = false;
  mysql_select_db($database_sVoice_conn, $sVoice_conn);
  
  
// *** Supply a username and password for verification by the system 
  $LoginRS__query=sprintf("SELECT username, password FROM `admin` WHERE username=%s AND password=%s",
    GetSQLValueString($loginUsername, "text"), GetSQLValueString($password, "text")); 
   
  $LoginRS = mysql_query($LoginRS__query, $sVoice_conn) or die(mysql_error());
  $loginFoundUser = mysql_num_rows($LoginRS);
  if ($loginFoundUser) {
     $loginStrGroup = "";
    
    //declare two session variables and assign them
    $_SESSION['MM_Username'] = $loginUsername;
    $_SESSION['MM_UserGroup'] = $loginStrGroup;	      

    if (isset($_SESSION['PrevUrl']) && false) {
      $MM_redirectLoginSuccess = $_SESSION['PrevUrl'];	
    }
    header("Location: " . $MM_redirectLoginSuccess );
  }
  else {
    header("Location: ". $MM_redirectLoginFailed );
  }
}
?>


<!DOCTYPE html>
<html>
  <head>
    <title>Admin Site &middot;</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  </head>
  
  
  <body>
    <header style="background-color: #428bca; height: 60px"></header>
    <div style="  min-height: 100%;  height: auto;  /* Negative indent footer by its height */  margin: 0 auto -60px;  /* Pad bottom by footer height */  padding: 0 0 60px;">
        <div class="container">
          <div class="row" style="padding-top: 40px"><!-- padding is space ebfore the jumbotron-->
        <div class="col-md-7 jumbotron">
            <h1 class="text-center" style="color: #428bca">Administrators page..</h1>
            <p class="text-center">Please login with a valid email address and enter your password. Be sure to protect the PASSWORD from unauthorized users that may compromise the company standards. If possible, please change your password often to avoid unauthorized access.</p>
        </div>
        <div class="col-md-5" style="padding-top: 40px"> <!--padding isSpace before the the Sign in Box-->
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h1>Sign in Below:</h1>
                </div>
                <br><br><br>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" ACTION="<?php echo $loginFormAction; ?>" name ="myForm" onSubmit="return validateFormIfEmpty()"  method="POST">
                     <div class="form-group">
                     <label for="inputEmail1" class="col-lg-3 control-label">Email</label>
                     <div class="col-lg-6">
                       <input type="email" name="adminEmail" class="form-control" id="inputEmail1" placeholder="Email" required>
                     </div>
                     </div>
                     <div class="form-group">
                     <label for="inputPassword1" class="col-lg-3 control-label">Password</label>
                     <div class="col-lg-6">
                       <input type="password" name="adminPassword" class="form-control" id="inputPassword1" placeholder="Password" required>
                     </div>
                     </div>
                     <div class="form-group">
                     <div class="col-lg-offset-3 col-lg-6">
                       <div class="checkbox">
                         <label>
                           <input type="checkbox"> Remember me
                         </label>
                       </div>
                     </div>
                     </div>
                     <div class="form-group">
                     <div class="col-lg-offset-3 col-lg-6">
                       <button type="submit" class="btn btn-default" >Sign in</button>
                     </div>
                     </div>
                    </form>
                </div>
				
            </div>
        </div>
    </div>
    </div>
    </div>
    <footer style="background-color: #428bca; height: 100px; alignment-adjust: baseline">
        <div class="container text-center">
            <h4 style="color: #fff"> &copy 2013. Lacel Technologies LTD.</h4>
        </div>
   </footer>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
  </body>
</html>