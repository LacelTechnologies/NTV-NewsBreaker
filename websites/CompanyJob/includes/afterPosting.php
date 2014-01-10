
 <form class="well"name="search" method="post" action="<?php //echo $_SERVER['PHP_SELF'];?>">
 Seach for: <input type="text" name="find" /> in 
 <Select NAME="field">
 <option>Search in ...</option>
 <Option VALUE="area_of_residence">Area of residence</option>
 <Option VALUE="institution">Institution</option>
 <Option VALUE="course_offered">Course offered</option>
 <Option VALUE="sex">Sex</option>
 </Select>
 <br>
 <br>
 And for: <input type="text" name="another" /> in 
 <Select NAME="other">
 <Option VALUE="area_of_residence">Area of residence</option>
 <Option VALUE="institution">Institution</option>
 <Option VALUE="course_offered">Course offered</option>
 <Option VALUE="sex">Sex</option>
 </Select>
 
 <input type="hidden" name="searching" value="yes" />
 <input type="submit" name="search" value="Search" />
 

 </form>
      
  



 







  
</div>
</body>
</html>
