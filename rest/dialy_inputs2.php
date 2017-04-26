<?php 

require("connection.php"); 
$user_email = $_POST["email"];
$user_input_date = $_POST["input_date"];
$user_input_time = $_POST["input_time"];
$user_input_glucose = $_POST["input_glucose"];
$user_input_insulin = $_POST["input_insulin"];
$user_input_note= $_POST["input_note"];
$user_input_day = $_POST["day"];
$user_input_month = $_POST["month"];
$user_input_year= $_POST["year"];

/*$user_email = 'isam';
$user_input_date ='12-11-2016';
$user_input_time = '17:17:00';
$user_input_glucose = '2';
$user_input_insulin = '4';
$user_input_note= 'hi me';*/

// array for JSON response
$response = array();
$sql_query = "INSERT INTO `dailyinput`(`email`, `time`, `input_glucose`, `input_insulin`, `input_note`, `day`, `month`, `year`) VALUES ('$user_email','$user_input_time','$user_input_glucose','$user_input_insulin','$user_input_note','$user_input_day','$user_input_month','$user_input_year')";

$verify_query = mysqli_query($conn, $sql_query);

	if($verify_query)
	{
	// success
	$response["operation"] = "user daily input";
	$response["message"] = "Input User Insert success";
	$user = array();
	$user['email']=$user_email;
	$user['day']=$user_input_date;
	$user['month']=$user_input_day;
	$user['year']=$user_input_month;
	$user['time']=$user_input_year;
	$user['input_glucose']=$user_input_glucose; 
	$user['input_insulin']=$user_input_insulin; 
	$user['input_note']=$user_input_note; 
	// user node
	$response["user"] = array();

	array_push($response["user"], $user);
	
	echo json_encode($response);
	}
	else
	{
	$response["operation"] = "user daily input";
	$response["message"] = "input User insert failed ";
	// echo no users JSON
	echo json_encode($response);
	}
	

 ?>