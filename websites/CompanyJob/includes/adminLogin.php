
<form ACTION="<?php //echo $loginFormAction; ?>" name ="myForm" class="form-signin" onsubmit="return validateFormIfEmpty()"  method="POST">
        <h2 class="form-signin-heading" style="color:#00345c; text-shadow:#cccccc" 3px 2px 3px">Advertise a job</h2>
        <input type="text" class="input-block-level" placeholder="Company name" name="company_name">
		  <input type="text" class="input-block-level" placeholder="Job Title" name="job_title">
        <input type="text" class="input-block-level" placeholder="Vacancies available" name="job_vacancies">
			<input type="text" class="input-block-level" placeholder="Job description" name="job_description">
		<input type="text" class="input-block-level" placeholder="Job qualification" name="job_qualification">
			<input type="text" class="input-block-level" placeholder="Job Deadline" name="ending_date">
		<input type="text" class="input-block-level" placeholder="How to apply" name="how_to_apply">
			<input type="text" class="input-block-level" placeholder="Other Information" name="other">
       
  <button class="m-btn blue" type="submit" value="submit">Enter</button>
      </form>
