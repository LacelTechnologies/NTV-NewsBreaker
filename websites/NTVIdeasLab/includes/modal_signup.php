

<?php
   $server_name ="localhost";
 $user_name = "root";
 $password="";
 $database_name ="ideaslab_db";  
 
 if (
 ((isset ($_POST["inputName"])) && $_POST["inputName"] !="") && ((isset ($_POST["inputPassword"])) && $_POST["inputPassword"] !=""))
 {
 $inputName = $_POST['inputName'];
 $InputEmail = $_POST['InputEmail'];
  $confirmEmail = $_POST['confirmEmail'];
 $inputPassword = $_POST['inputPassword'];
 $confirmPassword = $_POST['confirmPassword'];
 
 
 $con = mysql_connect($server_name, $user_name , $password);  //this is the real username and password

 #checking for connection
if (!$con)
  {
      die('Could not connect: ' . mysql_error());
  }
  
  
   mysql_select_db($database_name , $con);
  
  $LoginRS__query="INSERT INTO users(name,email,password) 
  VALUES('$inputName', '$InputEmail',' $inputPassword')";
  
   // GetSQLValueString($loginUsername, "text"), GetSQLValueString($password, "text")); 
   
 
  $LoginRS = mysql_query($LoginRS__query, $con);
 
  $_POST['inputName']="";
    $_POST['inputPassword']="";
	/*if (!$LoginRS){
    if (mysql_errno() == 1062) {
        echo "This email has already been used";
    } else {
	 echo "well";
       // die('Invalid query: ' . mysql_error());
    }
}*/
  
 }
?>

<?php
if (mysql_errno() == 1062) {
          echo"<script language=javascript>alert('Email already used')</script> ";
        }
		else {
		 echo"<script language=javascript>alert('You have been successfully registered')</script> ";
	 // die('Invalid query: ' . mysql_error());
    }
?>

 <script type='text/javascript'>
 function clearing(){
               
					//document.signup.inputName.value="";
					alert('Data saved');
                });
				}
            </script> 

 <script type="text/javascript">
  function validate() {
 
   document.getElementById("confirmEmail").value="email mismatch ";
  
  }
  </script>

<!-- Modal -->
<div id="sig_modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Sign up</h4>
            </div>
            <div class="panel-body">
                <form id="sign_up_form" name="signup" class="form-horizontal" role="form" method="post" >
                    <div class="form-group">
                        <label for="inputName" class="col-sm-2 control-label">Name:</label>
                        <div class="col-sm-10">
                            <input type="text" name="inputName" class="form-control" id="inputName" placeholder="Name" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="InputEmail" class="col-sm-2 control-label">Email:</label>
                        <div class="col-sm-10">
                            <input type="email" name="InputEmail" class="form-control" id="InputEmail" placeholder="Email" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="confirmEmail" class="col-sm-2 control-label">Confirm:</label>
                        <div class="col-sm-10">
                            <input type="email" name="confirmEmail" class="form-control" id="confirmEmail" placeholder="Confirm Email" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="inputPassword" class="col-sm-2 control-label">Password:</label>
                        <div class="col-sm-10">
                            <input type="password" name="inputPassword" class="form-control" id="inputPassword" placeholder="Password" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="confirmPassword" class="col-sm-2 control-label">Confirm:</label>
                        <div class="col-sm-10">
                            <input type="password" name="confirmPassword" class="form-control" id="confirmPassword" placeholder="Confirm Password" required>
                        </div>
                    </div>


                    <div class="pull-right btn-group">
                        <button type="reset" class="btn btn-default">Clear Form</button>
                        <button type="submit"  name="save" class="btn btn-primary" onclick="clearing()" >Save</Button>
                        <a  type="button" class="btn btn-danger" data-dismiss="modal">Close</a>
                    </div>
                </form>
            </div>
        </div>
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<!--Marivin you will validate from here-->

<script type="text/javascript">
    $('#buttonId').click(function(){
       if($('#InputId').val()===""){
        //valid
        $('#InputId').next('.help-inline').show();
        return false;
        }else{
            //submit the form here
            $('#infroTest').submit();
            return true;
        }

    }

</script>