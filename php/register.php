<?php

require"init.php";



$uname = $_POST["Name"];
$upass = $_POST["Pass"];
$uemail = $_POST["Email"];

$sql_query = "INSERT into tbl_userinfo (name,pass,email) values('$uname','$upass','$uemail')";

if(mysqli_query($con,$sql_query)){
	echo "Success";
}
else{
	echo "Failed".mysqli_error($con);
}


?>