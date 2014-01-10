<?php
# FileName="Connection_php_mysql.htm"
# Type="MYSQL"
# HTTP="true"
$hostname_job_conn = "localhost";
$database_job_conn = "jobs";
$username_job_conn = "root";
$password_job_conn = "";
$sVoice_conn = mysql_pconnect($hostname_job_conn, $username_job_conn, $password_job_conn) or trigger_error(mysql_error(),E_USER_ERROR); 
?>