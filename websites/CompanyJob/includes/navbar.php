<nav class="navbar navbar-default" role="navigation">
        <div class="container">
             <!-- Brand and toggle get grouped for better mobile display -->
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">Job site</a>
      </div>

      <!-- Collect the nav links, forms, and other content for toggling -->
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        
        <form class="navbar-form navbar-right" role="search">
          <div class="form-group">
            <input type="text" class="form-control" placeholder="Search for .." name="find">
          </div>
          <div class="form-group">
              <Select NAME="field" class="form-control">
                 <option>Search in ...</option>
                 <Option VALUE="area_of_residence">Area of residence</option>
                 <Option VALUE="institution">Institution</option>
                 <Option VALUE="course_offered">Course offered</option>
                 <Option VALUE="sex">Sex</option>
              </Select>
          </div>
          <div class="btn-group">
              <button type="submit" class="btn btn-default">Search</button>
              <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">Advanced Search</button>
              <!--#########################################Advanced Search Modal-->
              <div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                    <div class="modal-content">
	                    <div class="modal-body">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			                    <h3 id="myModalLabel">Advanced Search</h3>
		                    <form>
                              <div class="form-group">
                                <input type="text" class="form-control" placeholder="Search for .." name="find" required>
                              </div>
                              <div class="form-group">
                                  <Select NAME="field" class="form-control" required>
                                     <option>Search in ...</option>
                                     <Option VALUE="area_of_residence">Area of residence</option>
                                     <Option VALUE="institution">Institution</option>
                                     <Option VALUE="course_offered">Course offered</option>
                                     <Option VALUE="sex">Sex</option>
                                  </Select>
                              </div>

                            <h3>And for: </h3>
                                <div class="form-group">
                                <input type="text" class="form-control" placeholder="Search for .." name="find" required>
                              </div>
                              <div class="form-group">
                                  <Select NAME="field" class="form-control" required>
                                     <option>Search in ...</option>
                                     <Option VALUE="area_of_residence">Area of residence</option>
                                     <Option VALUE="institution">Institution</option>
                                     <Option VALUE="course_offered">Course offered</option>
                                     <Option VALUE="sex">Sex</option>
                                  </Select>
                              </div><br><br><br>
		                            <button type="submit" class="btn btn-primary form-control">submit</button>
                            </form>
	                    </div>
                    </div>
                    </div>
                    </div>
              <!--/. Advanced Search Modal ######################################-->
          </div>
        </form>

      </div><!-- /.navbar-collapse -->
        </div>
    </nav>