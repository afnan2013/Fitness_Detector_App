<?php

$db_name = "fitness";
$mysql_user = "root";
$mysql_pass = "";
$server_name = "localhost";

$con = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);

// if($con){
// 	echo "Database Connection Successful";
// }

// else{
// 	echo "Connection Error".mysqli_connect_error();
// }

?>