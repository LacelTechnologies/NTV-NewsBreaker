<?php require_once('../../Connections/sVoice_conn.php'); ?>
<?php
//initialize the session
if (!isset($_SESSION)) {
  session_start();
}

// ** Logout the current user. **
$logoutAction = $_SERVER['PHP_SELF']."?doLogout=true";
if ((isset($_SERVER['QUERY_STRING'])) && ($_SERVER['QUERY_STRING'] != "")){
  $logoutAction .="&". htmlentities($_SERVER['QUERY_STRING']);
}

if ((isset($_GET['doLogout'])) &&($_GET['doLogout']=="true")){
  //to fully log out a visitor we need to clear the session varialbles
  $_SESSION['MM_Username'] = NULL;
  $_SESSION['MM_UserGroup'] = NULL;
  $_SESSION['PrevUrl'] = NULL;
  unset($_SESSION['MM_Username']);
  unset($_SESSION['MM_UserGroup']);
  unset($_SESSION['PrevUrl']);
	
  $logoutGoTo = "index.php";//restrictions after the user logsout of the system
  if ($logoutGoTo) {
    header("Location: $logoutGoTo");//user stays on the logout page and cannot return back
    exit;
  }
}
?>
<?php
include("MCrypt.php");
 	$crypt = new MCrypt;
	$encoded= $crypt->encrypt('poor working conditions at the station. workers are obliged to work multiple shifts a day.');
//echo $encoded."\n";
//echo $crypt->decrypt($encoded);?>


		<?php
		//code for uploading an image from the local machine to the website
        function UploadOne($fname)
        {
        $uploaddir = 'img/';//img - folder to store our uploaded images
        if (is_uploaded_file($fname['tmp_name']))
        {
        $filname = basename($fname['name']);//name of the file or image
        $uploadfile = $uploaddir . basename($fname['name']);
        if (move_uploaded_file ($fname['tmp_name'], $uploadfile))
        $res = "File " . $filname . " was successfully uploaded and stored.<br>";//feed back to the user incase os success
        else
        $res = "Could not move ".$fname['tmp_name']." to ".$uploadfile."<br>";//feedback incase of upload failure
        }
        else
        $res = "File ".$fname['name']." failed to upload.";//feedback incase of upload failure
        return ($res);
        }
        ?>
        <?php
        if ($_FILES['picture']['name'] != "")//incase of success, do below
        {
        $res = UploadOne($_FILES['picture']);
        $filname = $_FILES['picture']['name'];//filename for the image or picture
        echo ($res);
        }
        ?>


<?php 
$id=$_GET['id'];//asssigning the echoed id a variable to be used in the sql query
/*if ($id=""){
$id = 1;
}*/
//sql database query to fetch values from a single row to be viewed by the admin
	$sql="SELECT * FROM ranking WHERE Newsid='$id'";
	$result = mysql_query($sql);
	$rowss = mysql_fetch_assoc($result);
?>


<?php //begin the php tag
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

$editFormAction = $_SERVER['PHP_SELF'];
if (isset($_SERVER['QUERY_STRING'])) {
  $editFormAction .= "?" . htmlentities($_SERVER['QUERY_STRING']);
}

if ((isset($_POST["MM_insert"])) && ($_POST["MM_insert"] == "submit_Form")) {
  $insertSQL = sprintf("INSERT INTO ranking (Usrid, headline, `full`, category, image, Caption, location, Status) VALUES (%s, %s, %s, %s, %s, %s, %s, %s)",
                       GetSQLValueString($_POST['admin_id'], "int"),
                       GetSQLValueString($_POST['admin_headline'], "text"),
                       GetSQLValueString($_POST['admin_details'], "text"),
                       GetSQLValueString($_POST['admin_category'], "text"),
                       GetSQLValueString($_POST['filename'], "text"),
                       GetSQLValueString($_POST['admin_caption'], "text"),
                       GetSQLValueString($_POST['admin_location'], "text"),
                       GetSQLValueString($_POST['admin_status'], "text"));//inserting new details by the administrator into the server database

  mysql_select_db($database_sVoice_conn, $sVoice_conn);
  $Result1 = mysql_query($insertSQL, $sVoice_conn) or die(mysql_error());

  $insertGoTo = "adminPage.php";//after inserting the data direct the user to the page
  if (isset($_SERVER['QUERY_STRING'])) {
    $insertGoTo .= (strpos($insertGoTo, '?')) ? "&" : "?";
    $insertGoTo .= $_SERVER['QUERY_STRING'];//go to query
  }
  header(sprintf("Location: %s", $insertGoTo));
}

	$currentPage = $_SERVER["PHP_SELF"];//variable assigned for the current page (adminPage.php)
	$editFormAction = $_SERVER['PHP_SELF'];//variable assigned for the current page (adminPage.php)- this will be used for testing
	
if (isset($_SERVER['QUERY_STRING'])) {
  $editFormAction .= "?" . htmlentities($_SERVER['QUERY_STRING']);
}

//below we update our table *ranking* with editted values submitted by the administartor
if ((isset($_POST["MM_update"])) && ($_POST["MM_update"] == "update_Form")) {
  $updateSQL = sprintf("UPDATE ranking SET headline=%s, `full`=%s, category=%s, Caption=%s, location=%s WHERE Newsid=%s",//sql query for updating
                       GetSQLValueString($_POST['headline'], "text"),
                       GetSQLValueString($_POST['details'], "text"),
                       GetSQLValueString($_POST['category'], "text"),
                       GetSQLValueString($_POST['caption'], "text"),
                       GetSQLValueString($_POST['location'], "text"),
                       GetSQLValueString($_POST['hiddenid'], "int"));//we get values submitted in the textboxes and assign them to the columns in our table. Columns must match variables

  		mysql_select_db($database_sVoice_conn, $sVoice_conn);//selecting a database name using a variable defined in the connection folder
  		$Result1 = mysql_query($updateSQL, $sVoice_conn) or die(mysql_error());//executing the sql query for updating

  		$updateGoTo = "../../sVoice/adminHome.php";//php page to go to after updating the database with new string data
  if (isset($_SERVER['QUERY_STRING'])) {
    $updateGoTo .= (strpos($updateGoTo, '?')) ? "&" : "?";
    $updateGoTo .= $_SERVER['QUERY_STRING'];
  }
  header(sprintf("Location: %s", $updateGoTo));//header displays
}

	$maxRows_rs_paging = 3;//we define the maximum number of results that must be viewed at a time
	$pageNum_rs_paging = 0;//assign a null to the original page numbeer for the very first page
if (isset($_GET['pageNum_rs_paging'])) {
  	$pageNum_rs_paging = $_GET['pageNum_rs_paging'];//assigning a variable for the page number
}
	$startRow_rs_paging = $pageNum_rs_paging * $maxRows_rs_paging;

//below, we select our database and query the database
//the query selects all data stored in the table and orders it in descending as per the date
	mysql_select_db($database_sVoice_conn, $sVoice_conn);
	$query_rs_paging = "SELECT * FROM newsstory_tb  ORDER BY `when` DESC";
	$query_limit_rs_paging = sprintf("%s LIMIT %d, %d", $query_rs_paging, $startRow_rs_paging, $maxRows_rs_paging);
	$rs_paging = mysql_query($query_limit_rs_paging, $sVoice_conn) or die(mysql_error());
	$row_rs_paging = mysql_fetch_assoc($rs_paging);

//<!..Pagination.>
//get number of pages to be listed depending on the records to be viewed
if (isset($_GET['totalRows_rs_paging'])) {
  	$totalRows_rs_paging = $_GET['totalRows_rs_paging'];
} else {
  	$all_rs_paging = mysql_query($query_rs_paging);
  	$totalRows_rs_paging = mysql_num_rows($all_rs_paging);
}
		$totalPages_rs_paging = ceil($totalRows_rs_paging/$maxRows_rs_paging)-1;//final figure for the total number of pages
		$queryString_rs_paging = "";// we set the string query for pagination to null
		
if (!empty($_SERVER['QUERY_STRING'])) {
  $params = explode("&", $_SERVER['QUERY_STRING']);
  $newParams = array();
  	foreach ($params as $param) {
    	if (stristr($param, "pageNum_rs_paging") == false && 
        	stristr($param, "totalRows_rs_paging") == false) {
      	array_push($newParams, $param);
    }
  }
  		if (count($newParams) != 0) {
    		$queryString_rs_paging = "&" . htmlentities(implode("&", $newParams));
  	}
}
	$queryString_rs_paging = sprintf("&totalRows_rs_paging=%d%s", $totalRows_rs_paging, $queryString_rs_paging);
	$maxRows_user_news = 4;//we define the maximum number of results that must be viewed at a time
	$pageNum_user_news = 0;//assign a null to the original page numbeer for the very first page
if (isset($_GET['pageNum_user_news'])) {
  	$pageNum_user_news = $_GET['pageNum_user_news'];//assigning a variable for the page number
}
	$startRow_user_news = $pageNum_user_news * $maxRows_user_news;
	
//SQL query statement

	mysql_select_db($database_sVoice_conn, $sVoice_conn);
	$query_user_news = "SELECT * FROM ranking where Usrid!=1 ORDER BY `time` DESC";
	$query_limit_user_news = sprintf("%s LIMIT %d, %d", $query_user_news, $startRow_user_news, $maxRows_user_news);
	$user_news = mysql_query($query_limit_user_news, $sVoice_conn) or die(mysql_error());
	$row_user_news = mysql_fetch_assoc($user_news);

/*while ($res = mysql_fetch_array($user_news)){
$loc =$res['location'];
echo'
<div class="list-group" style="padding-top: 10px">
                    <a href="#news" class="list-group-item">
                    <h3 class="list-group-item-heading">'.$crypt->decrypt($res['location']);'</h3>
                  </a>
				  </div>
				  
';
$id=$_GET['id'];
}*/
	$id=$_GET['id'];//establish a variable for our id echoed

//<!--..Pagination.-->
//get number of pages to be listed depending on the records to be viewed
if (isset($_GET['totalRows_user_news'])) {
  $totalRows_user_news = $_GET['totalRows_user_news'];
} else {
  $all_user_news = mysql_query($query_user_news);
  $totalRows_user_news = mysql_num_rows($all_user_news);
}
	$totalPages_user_news = ceil($totalRows_user_news/$maxRows_user_news)-1;
// Tab contents
    $newsContent = '';
?>

<?php 
// script to fetch longitude and latitude of the news source
####################
$latitude=0.331648 ;
$longitude=32.581222 ;
####################
?>


<!--...FRONT USER END..-->
<!DOCTYPE html>
<html>
  <head>
  
  <!--JS1...Javascript code for using google maps to view locations..-->
              <script type="text/javascript"
            src="http://maps.googleapis.com/maps/api/js?key=AIzaSyC2JLzBr17EBckFGIkGT4OYiuqSueHn8eM&sensor=false">
            </script>
            
            <script type="text/javascript">
            var latitude=<?php echo $latitude ;?>;<!--...Assign a variable to the latitude..-->
            var longitude= <?php echo $longitude ;?>;<!--...Get the value of the longitude and assign it to the variable longitutde..-->
            var news_source_coordinates =new google.maps.LatLng(latitude,longitude);<!--...assigning variables to our map session..-->
            
            function initialize()
            {
            var mapProp = {
              center:news_source_coordinates,
              zoom:10,<!--...defining zoom levels..-->
              mapTypeId:google.maps.MapTypeId.ROADMAP<!--...defining the type of map view the user should access..-->
              };
            
            var map=new google.maps.Map(document.getElementById("news_source_googleMap"),mapProp);<!--...getting the id that will be used to display the maps..-->
            
            var marker=new google.maps.Marker({
              position:news_source_coordinates,<!--...Actual position to be shown on the map..-->
              animation:google.maps.Animation.BOUNCE,<!--...Defining the place marker to show the exact location..-->
              title:"cool"
              });
			  marker.setMap(map);
            }
            
            google.maps.event.addDomListener(window, 'load', initialize);<!--...Loading a new window to show the maps..-->
            </script>
  
  
  <!--JS2...We define a script to help prevent the user from reurning to the previous login page..-->
<script type="text/javascript"> window.history.forward();
  function noback(){
  window.history.forward();
  }
  </script>
  
  
  <!--JS3...VALIDATION Script to show the user how many characters have been typed..-->
     <script type="text/javascript">
function countChars(countfrom,displayto) {<!--...function to show which type of field to count the text input..-->
  var len = document.getElementById(countfrom).value.length;<!--...Get the number of characters and assign to a variable len..-->
  document.getElementById(displayto).innerHTML = len;
}
</script>
    <title>Administrator Site &middot;</title><!--...title for the site..-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    <script src="../../SpryAssets/SpryValidationTextField.js" type="text/javascript"></script>
    <link href="../../SpryAssets/SpryValidationTextField.css" rel="stylesheet" type="text/css">
</head>
  
  <!--...We call our noback function to be run within the body tags..-->
  <body onLoad="noback();" onUnload="" >
    <header style="background-color: #428bca; height: 60px">
    <?php include'passwordChange_modal.php';?>
        <div class="pull-right" style="padding-top: 10px; padding-right: 20px">
            <a class="btn btn-success" data-toggle="modal" href="#ConfirmModal">Change Password</a>
          <a href="<?php echo $logoutAction ?>" class="btn btn-danger">Log out</a>
        </div>
    </header>
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <div class="list-group" style="padding-top: 40px">
                    <a href="#" class="list-group-item active">
                    <h4 class="list-group-item-heading">User news</h4>
                    <p class="list-group-item-text">Citizen Journalists</p>
                    </a>
                    
                    
                    <!--...We intergrate PHP in HTML to echo our database items..-->
                    <!--...Use aa while loop to iterate and return the desired variables..-->
                      <?php do { ?>
                    <a href="adminPage.php?id=<?php echo $row_user_news['Newsid']; ?>" class="list-group-item"><!--...echoing a unique key for the item clicked..-->
                    <h4 class="list-group-item-heading"><?php echo ($row_user_news['headline']); ?></h4><!--...Listing items *headline for news* from the database..-->
                    <p align="left" class="list-group-item-text">Location:<?php echo ($row_user_news['location']); ?></p> <p align="right"> <?php echo ($row_user_news['time']); ?></p>
                  </a>
                   <?php } while ($row_user_news = mysql_fetch_assoc($user_news)); ?>
                  
                   
                    <!--Pagination-->
                    <ul class="pagination pagination-lg">
                   	  <li><a href="<?php printf("%s?pageNum_user_news=%d%s", $currentPage, 0, $queryString_user_news); ?>">&laquo;First</a></li>
                   	  
                   	  <li><a href="<?php printf("%s?pageNum_user_news=%d%s", $currentPage, max(0, $pageNum_user_news - 1), $queryString_user_news); ?>">Previous</a></li>
					  
                   	  <li><a href="<?php printf("%s?pageNum_user_news=%d%s", $currentPage, min($totalPages_user_news, $pageNum_user_news + 1), $queryString_user_news); ?>">Next</a></li>
					  
                   	  <li><a href="<?php printf("%s?pageNum_user_news=%d%s", $currentPage, $totalPages_user_news, $queryString_user_news); ?>">Last&raquo;</a></li>
                    
                  </ul>  
                </div>
            </div>
            <div class="col-md-6">
                 <!--Tab--><!-- Initiate tabs via Javascript-->
                <script type="text/javascript">
                    $('#myTab a').click(function (e) {
	                     e.preventDefault();
	                     $(this).tab('show');
                    });

                    $(function () {
                    $('#myTab a:last').tab('show');
                    })
                </script>

 			<!--...We use tabs here for easy navigation..-->
<ul class="nav nav-tabs" id="myTab" style="padding-top: 40px">
	                <li><a href="#news" data-toggle="tab">News</a></li>
	                <li><a href="#news_source_googleMap" data-toggle="tab">Map</a></li>
	                <li><a href="#editNews" data-toggle="tab">Edit News</a></li>
                    <li><a href="#createStory" data-toggle="tab">Post New Story</a></li>
              </ul>
                <div class="tab-content"> <!--Contents of the tabs-->
                
                
<!--...1st tab for viewing news..-->
	                <div class="tab-pane active" id="news" style="padding-top: 10px">  <h3 class="list-group-item active">Displaying the users news in details</h3> 
                    <p align="left" class="list-group-item-text"><h2><?php echo ($rowss['headline']); ?></h2></p>
                    <p align="left" class="list-group-item-text"><h3><?php echo ($rowss['full']); ?></h3></p><br>
                    <p align="left" class="list-group-item-text"><img src="img/<?php echo $rowss['image']; ?>" width="400" height="200" /></p>
                    <p align="right" class="list-group-item-text"><?php echo ($rowss['location']); ?></p>
                    <p align="right" class="list-group-item-text">Administrator's approval: <?php echo ($rowss['Status']); ?></p>
                  </div>
                  
                  
           <!--...2nd tab for maps location..-->
           
                  <div class="tab-pane" id="news_source_googleMap" style="width:500px;height:380px;">
                  </div>
	       
           
           <!--...3rd and final tab for editting, approving and udating the news stories..-->
           <!--...FOR VALIDATION - We use required so that important fields are not left empty-->
           	<form class="tab-pane" id="editNews" style="padding-top: 10px" ACTION="<?php echo $editFormAction; ?>" name ="update_Form"  onsubmit="return validateFormIfEmpty()"  method="POST">
       		  <div class="col-lg-6">
           		<input type="text" name="headline" value="<?php echo ($rowss['headline']); ?>" class="form-control" id="headline" placeholder="Headline maximum characers - 100" required maxlength="100" ><br>
       		  </div>
                <div class="col-lg-6">
                    <input type="text" name="location"  value="<?php echo ($rowss['location']); ?>" class="form-control" id="location" placeholder="Location" required><br>
       		  </div> 
              <div class="col-lg-6">
                       <!--<input type="text" name="category"  value="<?php echo ($rowss['category']); ?>" class="form-control" id="category" placeholder="Category" required><br>-->
                       <select class="form-control" name="category" >
                       		<option>Category</option>
                            <option value="top stories">Top Stories</option>
                            <option value="local news">Local News</option>
                            <option value="international news">International News</option>
                            <option value="business news">Business News</option>
                            <option value="sports news">Sports News</option>
</select>
                     </div>
             	<div class="col-lg-6">
               		 <input type="text" name="caption"  value="<?php echo ($rowss['caption']); ?>" class="form-control"  id="caption" placeholder="Caption" required><br>
       		  </div>
             		 <textarea class="form-control" title="" name="details" id="details" rows="17" placeholder="story details" required onkeyup="countChars('details','charcount1');" onkeydown="countChars('details','charcount1');" onmouseout="countChars('details','charcount1');" required maxlength="5000"><?php echo ($rowss['full']); ?></textarea>
<span id="charcount1">0</span>/5000 characters entered.<br>
<br>
                     <input name="hiddenid" type="hidden" value="<?php echo ($rowss['Newsid']); ?>">
           	  <div class="pull-right btn-group" style="padding-top: 20px">
                        
                        
            <!--...Buttons for the 3rd tab..-->
               <button type="reset" class="btn btn-default" id="">Clear Form</button>
                      <?php include'confirm_modal.php';?><!--Confirm action for VALIDATION-->
               <button type="submit" class="btn btn-success" data-toggle="modal" data-target="#myModal" onClick="updating()">Approve & Upload to Site</button>
                      </div>
                      <input type="hidden" name="MM_update" value="update_Form">
       			  </form>
                  
                  
                  <!--...4th tab; form for creating new story..-->
                  <form enctype="multipart/form-data" class="tab-pane" id="createStory" style="padding-top: 10px" ACTION="adminPage.php" name ="submit_Form"  onsubmit="return validateFormIfEmpty()"  method="POST">
       		  <div class="col-lg-6">
           		<input type="text" name="admin_headline" value="" class="form-control" id="headline" placeholder="Headline maximum characers - 100" required maxlength="100" ><br>
       		  </div>
                <div class="col-lg-6">
                    <input type="text" name="admin_location"  value="" class="form-control" id="admin_location" placeholder="Location" required><br>
       		  </div>
                
              <div class="col-lg-6">
                       <!--<input type="text" name="category"  value="<?php echo ($rowss['category']); ?>" class="form-control" id="category" placeholder="Category" required><br>-->
                       <select class="form-control" name="admin_category" id="admin_category">
                            <option value="top stories">Top Stories</option>
                            <option value="local news">Local News</option>
                            <option value="international news">International News</option>
                            <option value="business news">Business News</option>
                            <option value="sports news">Sports News</option>
</select>
                     </div>
             	<div class="col-lg-6">
               		 <input type="text" name="admin_caption"  value="" class="form-control"  id="admin_caption" placeholder="Caption" required><br>
       		  </div>
           
             	<textarea class="form-control" title="" name="admin_details" id="admin_details" rows="17" placeholder="story details" onkeyup="countChars('admin_details','charcount');" onkeydown="countChars('admin_details','charcount');" onmouseout="countChars('admin_details','charcount');" required maxlength="5000"></textarea>
<span id="charcount">0</span>/5000 characters entered.<br>
<br>
                  <div class="col-lg-6">
               		 <input type="file" name="picture"  value="" class="form-control"  id="picture" placeholder="Browse" required><br>
       		  </div>   
              
           	  <div class="pull-right btn-group" style="padding-top: 20px">
              
                        
                        
            <!--...Buttons for the 4th tab..-->
               <button type="reset" class="btn btn-default" id="">Clear Form</button><!--
               <button type="button" class="btn btn-primary" id="">Load Drafts</button>-->
               <?php include'confirm_modal2.php';?><!--Confirm action for VALIDATION-->
               <button type="submit" class="btn btn-success" data-toggle="modal" data-target="#myModal2">Submit story</button>
                      </div>
                    
                      <input name="admin_status" type="hidden" value="approved">
                      <input name="admin_id" type="hidden" value="1">
                      <input name="filename" type="hidden" value= "">
                      <input type="hidden" name="MM_insert" value="submit_Form">
       			  </form>
                  
                    
              </div>
            </div>
      </div>
    </div>
    <footer style="background-color: #428bca; height: 100px; alignment-adjust: baseline; padding-top: 20px" >
        <div class="container text-center">
            <h4 style="color: #fff"> &copy 2013. Lacel Technologies LTD.</h4>
        </div>
   </footer>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="js/jquery.js"></script><!--remove upon upload-->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
<!--
var sprytextfield1 = new Spry.Widget.ValidationTextField("sprytextfield1");
var sprytextfield2 = new Spry.Widget.ValidationTextField("sprytextfield2", "none", {maxChars:10});
//-->
</script>

 <!--...FOR VALIDATION - spry for testing
                    <div class="col-lg-6"><span id="sprytextfield2">
                    <label>
                    <input type="text" class="form-control" name="character" id="character">
                    </label>
                    <span class="textfieldRequiredMsg">Cannot be left empty.</span><span class="textfieldMaxCharsMsg">Exceeded maximum number of characters.</span></span>
  <span class="help-block textfieldMaxCharsState">Cannot be left empty.</span></span> </div>--> 

</body>
</html>
<?php
mysql_free_result($rs_paging);

mysql_free_result($user_news);
?>