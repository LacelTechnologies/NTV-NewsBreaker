<!DOCTYPE html>
<html>
  <head>
    <title>Job</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/custom.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <div id="wrap">
        <?php include 'includes/navbar.php'; ?>
        <div class="container">
            <div class="jumbotron">
                <h1>Job Site</h1>
                <p>This site helps you connect to people searching for jobs and to also post a job online.</p>
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h3 class="panel-title">Advertise a Job</h3>
                        </div>
                        <div class="panel-body">
						
						<!--...form for advertising a a new job by the administrator company..-->
                            <form ACTION="<?php //echo $loginFormAction; ?>" name ="myForm" class="form-signin" onsubmit="return validateFormIfEmpty()"  method="POST">
                                <div class="form-group"><input required type="text" class="form-control" placeholder="Company name" name="company_name"></div>
		                         <div class="form-group"><input required type="text" class="form-control" placeholder="Job Title" name="job_title"></div>
                                <div class="form-group"><input required type="text" class="form-control" placeholder="Vacancies available" name="job_vacancies"></div>
			                       <div class="form-group"> <input required type="text" class="form-control" placeholder="Job description" name="job_description"></div>
		                        <div class="form-group"><input required type="text" class="form-control" placeholder="Job qualification" name="job_qualification"></div>
			                        <div class="form-group"><input required type="text" class="form-control" placeholder="Job Deadline" name="ending_date"></div>
		                        <div class="form-group"><input required type="text" class="form-control" placeholder="How to apply" name="how_to_apply"></div>
			                        <div class="form-group"><input required type="text" class="form-control" placeholder="Other Information" name="other"></div>
       
                          <div class="form-group">
                              <button class="btn btn-info" type="submit" value="submit">Submit</button>
                              <button class="btn btn-default" type="reset">Clear Form</button>
                          </div>
                              </form>
                        </div>
                    </div>
                </div>
                <div class="col-sm-6">
                     <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title">Search Results</h3>
                        </div>
                        <div class="panel-body">
                            Put in an entry to view available job seekers according to their qualifications
                        </div>
                    <div class="panel-body">
                        <ul class="pager pull-right">
                              <li><a href="#">Previous</a></li>
                              <li><a href="#">Next</a></li>
                            </ul>
                    </div>
                    </div>
                </div>
            </div>
        <div id="push"></div>
        </div>
        <?php include 'includes/footer.php'; ?>
        </div>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="js/jquery.js"></script><!--remove upon upload-->
        <script src="https://code.jquery.com/jquery.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="js/bootstrap.min.js"></script>
    </div>
  </body>
</html>