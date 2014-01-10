<!--php-->
<?php
// *** Validate request to login to this site.
/*if (!isset($_SESSION)) {
  session_start();
}*/

$loginFormAction = $_SERVER['PHP_SELF'];
/*if (isset($_GET['accesscheck'])) {
  $_SESSION['PrevUrl'] = $_GET['accesscheck'];
}*/

#datbase variables		
 $server_name ="localhost";
 $user_name = "root";
 $password="";
 $database_name ="ideaslab_db";
 


if ((isset($_POST['username'])) && $_POST['username']!="") {
  $name=$_POST['username'];
  $comment=$_POST['comment'];
 $con = mysql_connect($server_name, $user_name , $password);  //this is the real username and password

 #checking for connection
if (!$con)
  {
      die('Could not connect: ' . mysql_error());
  }
  
  
   mysql_select_db($database_name , $con);
  
  $LoginRS__query="INSERT INTO user_comments(name,comment) 
  VALUES('$name', '$comment')";
  
   // GetSQLValueString($loginUsername, "text"), GetSQLValueString($password, "text")); 
 
  $LoginRS = mysql_query($LoginRS__query, $con) or die(mysql_error());
 //$name ="";
 $_POST['username']="";
}

?>
<!-- Modal -->
<div id="com_modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="panel panel-success">
            <div class="panel-heading">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Comment</h4>
            </div>
            <div class="panel-body">
                <form class="form-horizontal" role="form" id="com_form" method="post" action="<?php echo $current_file; ?>">
                    <div class="form-group">
                        <label for="input_names" class="col-sm-2 control-label">Name:</label>
                        <div class="col-sm-10">
                            <input type="text" name="username" class="form-control" id="input_names" placeholder="Name" required>
                        </div>
                    </div>

                 

                    <div class="form-group">
                        <label for="enter_comment" class="col-sm-2 control-label">Comment:</label>
                        <div class="col-sm-10">
                            <textarea class="form-control" name="comment" rows="5" placeholder="Type your comment below" required></textarea>
                        </div>
                    </div>
                    <div class="pull-right btn-group">
                        <button type="reset" class="btn btn-default">Clear Form</button>
                        <button type="submit" type="reset" name="postcomment" class="btn btn-success">Post Comment</button>
                        <a  type="button" class="btn btn-danger" data-dismiss="modal">Close</a>
                    </div>
                </form>
            </div>
        
        </div>
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->



<!--Form Validation-->
<script>
    $(document).ready;
  $("#enter_names").validate
    expression: "if(VAL != '') return true; else return false;"
    message: "Name is required."

  $("#enter_email").validate
    expression: "if(VAL != '') return true; else return false;"
    message: "Email is required."

  $("#enter_comment").validate
    expression: "if(VAL != '') return true; else return false;"
    message: "Comment is required."
</script>