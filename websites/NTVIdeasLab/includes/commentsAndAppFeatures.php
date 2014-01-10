<?php
require 'connect.php';
require 'includes/core.php';
?>


<?php 

#Ensure that the client has provided a values 

$loginFormAction = $_SERVER['PHP_SELF'];
    if (
	isset($_POST["signing"])
	//(isset($_POST["email"]) && $_POST["email"] != "")
	//&&
	//(isset($_POST["pword"]) && $_POST["pword"] != "")
	){ 
         
        #Setup variables 
        $name= $_POST["email"];
        $pwd= $_POST["pword"];	
       
				
 #database variables 	
 $server_name ="localhost";
 $user_name = "root";
 $password="";
 $database_name ="ideaslab_db";
 
 #query variables
 $fetch_query="SELECT *FROM users WHERE email='$name' AND password='$pwd' ";
 
 #establishing connection
 $con = mysql_connect($server_name, $user_name , $password);  //this is the real username and password

 #checking for connection
if (!$con)
  {
      die('Could not connect: ' . mysql_error());
  }
  
  #connecting to database
  mysql_select_db( $database_name, $con);
 
 #fetch
$result = mysql_query($fetch_query) or die(mysql_error());
 
#check for empty result
if (mysql_num_rows($result) > 0) {
    
	#getting result
    $rsinfo = mysql_fetch_array($result);
	
  
	echo " You have successfully logged in as ";
   
} else {
   
    $response = "You are either not registered or using wrong username or password";
    echo " $response";
}
		}else{ 
        echo "Could not complete query. Missing parameters";  
    } 
?>






<?php
//This is only displayed if they have submitted the form 
/* if ($searching =="yes") 
  {
  echo "<h2>Results</h2><p>"; */

//If they did not enter a search term we give them an error 
// Otherwise we connect to our Database 

$dbhost = 'localhost';
$dbuser = 'root';
$dbpass = '';
$rec_limit = 5;

$conn = mysql_connect($dbhost, $dbuser, $dbpass);
if(! $conn )
{
  die('Could not connect: ' . mysql_error());
}
mysql_select_db('ideaslab_db');
/* Get total number of records */
$sql = "SELECT count(*) FROM user_comments ";
$retval = mysql_query( $sql, $conn );
if(! $retval )
{
  die('Could not get data: ' . mysql_error());
}
$row = mysql_fetch_array($retval, MYSQL_NUM );
$rec_count = $row[0];

if( isset($_GET{'page'} ) )
{
   $page = $_GET{'page'} + 1;
   $offset = $rec_limit * $page ;
}
else
{
   $page = 0;
   $offset = 0;
}
$left_rec = $rec_count - ($page * $rec_limit);

$sql = "SELECT name, comment ".
       "FROM user_comments ".
       "LIMIT $offset, $rec_limit";

$retval = mysql_query( $sql, $conn );
if(! $retval )
{
  die('Could not get data: ' . mysql_error());
}
while($row = mysql_fetch_array($retval, MYSQL_ASSOC))
{
  
} 

mysql_close($conn);
?>










<!--Comments and App Features
    ================================================== -->
<div class="col-sm-4"><!--Sign in-->
    <div class="page-header">
        <h4>Sign in to Comment</h4>
    </div>
    <div class="panel-body">
        <form class="form-horizontal" role="form" id="login_form"  method="post" ACTION="<?php echo $loginFormAction; ?>" name ="myForm" onsubmit="return validateFormIfEmpty()">
            <div class="form-group">
                <label for="inputEmail3" class="col-sm-3 control-label">Email:</label>
                <div class="col-sm-9">
                    <input type="email" class="form-control" id="inputEmail3" placeholder="Email" name="email">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword3" class="col-sm-3 control-label ">Password:</label>
                <div class="col-sm-9">
                    <input type="password" class="form-control" id="inputPassword3" placeholder="Password" name="pword">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-9">
                    <a type="submit" value="submit" name= "signing" data-target="#com_modal" data-toggle="modal"  class="btn btn-success "> Sign in</a>
                    <p>Or,</p>
                </div>

                <div class="col-sm-offset-3 col-sm-9">
                    <a type="button" class="btn btn-warning" href="#sig_modal" data-toggle="modal">Sign up Today</a><br><br>
                    <p>If you dont have an account. </p>
                </div>
            </div>
        </form>
    </div>
</div><!--/. Log in-->
<div class="col-sm-8"><!--Post Comment-->
    <div class="page-header">
        <h4>User Comments:</h4>
    </div>
    <div class="panel-body">
        <div class="media-body">
            <h4 class="media-heading"><?php echo   $commentName;?></h4>
            <p><?php echo   $comment;?></p>
        </div>

    </div>
    <ul class="pager">
        <li class="previous disabled"><a href="#">&larr; Older</a></li>
        <li class="next"><a href="#">Newer &rarr;</a></li>
    </ul>
</div>
</div><!--/. Post Comment-->
