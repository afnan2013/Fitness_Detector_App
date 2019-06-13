<?php

require "init.php";

$uname = $_POST["name"];
$upass = $_POST["pass"];


// $uname = "Afnan";
// $upass = "1234";

$sql_query = "SELECT name from tbl_userinfo where name like '$uname' and pass like '$upass';";

$result = mysqli_query($con,$sql_query);

if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_assoc($result);
	$user_name = $row["name"];
	echo "Login Successful";
}
else{
	echo "Login Failed...Try Again";
}

?>