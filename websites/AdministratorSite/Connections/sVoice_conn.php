<?php
# FileName="Connection_php_mysql.htm"
# Type="MYSQL"
# HTTP="true"
$hostname_sVoice_conn = "localhost";
$database_sVoice_conn = "svoice";
$username_sVoice_conn = "root";
$password_sVoice_conn = "";
$sVoice_conn = mysql_pconnect($hostname_sVoice_conn, $username_sVoice_conn, $password_sVoice_conn) or trigger_error(mysql_error(),E_USER_ERROR); 
?>