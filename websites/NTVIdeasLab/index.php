<?php
    
?>
<!DOCTYPE html>
<html>
    <head>
        <title>Our App &middot; Lacel Technologies</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!-- Bootstrap -->
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/carousel.css" rel="stylesheet">
		<link href="css/style.css" rel="stylesheet" type="text/css">
        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>

        <div class="container">
            <!--Ideas Lab Nav Bar
            ##########################-->
            <div class="row">
                <?php include 'includes/modal_signup.php'; ?>
                <?php include 'includes/navbar.php'; ?>
            </div><!--/.  ##########################-->
            <!--Carousel with Login and Sign up
            ##########################-->
            <div class="row">
                <?php include 'includes/modal_comment.php'; ?>
                <?php include 'includes/carousel.php'; ?>
            </div><!--/.  ##########################-->
            
            <!--Social Links
            ##########################-->
            <div class="row"><hr>
                        <div class="col-sm-4"><a href="#" class="btn btn-default btn-lg btn-block">Connect with us on Facebook</a></div>
                        <div class="col-sm-4"><a href="#" class="btn btn-default btn-lg btn-block">Connect with us on Twitter</a></div>
                        <div class="col-sm-4"><a href="#" class="btn btn-default btn-lg btn-block">Connect with us on Google+</a></div>
                        
                       
            </div><!--/.  ##########################-->
             <hr>            
            <!--App features and user comments
            ##########################-->
          <div class="row">
                <?php include 'includes/modal_signup.php'; ?>
                <?php include 'includes/modal_comment.php'; ?>
          </div>
        </div>
        <div class="row">
          <?php include 'includes/commentsAndAppFeatures.php'; ?>
        </div>
        <!--/.  ##########################-->
            <!--footer
            ##########################-->
            <div class="row"><?php include 'includes/footer.php'; ?></div><!--/.  ##########################-->


            <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
            <script src="js/jquery.js"></script><!--remove when you upload-->
            <script src="https://code.jquery.com/jquery.js"></script>
            <!-- Include all compiled plugins (below), or include individual files as needed -->
            <script src="js/bootstrap.min.js"></script>
            <script src="js/jquery.validate.min.js"></script>

        </div><!--/.  Container ##########################-->
    </body>
</html>

