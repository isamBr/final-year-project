<?php 

require("init.php"); 
/*$user_email = "sams";
$user_pass = "1234";*/
$user_email = $_POST["email"];
$user_pass = $_POST["password"];
$hashed_password=sha1($user_pass);
// array for JSON response
$response = array();

$sql_query = "INSERT INTO `users_details` (`email`, `password`) VALUES ('$user_email', '$hashed_password')";
$verify_query = mysqli_query($conn, $sql_query);

	if($verify_query)
	{
	// success
	$response["operation"] = "new user register";
	$response["message"] = "User register successfully";
	$user = array();
	$user['email']=$user_email;
	$user['name']="";
	$user['Surname']="";
	$user['age']=""; 
	$user['height']=""; 
	$user['diabetes type']=""; 
	$user['gender'] ="";
	$user["Mobile"]= "";
	
	// user node
	$response["user"] = array();
	array_push($response["user"], $user);
	
	// echo no users JSON
	echo json_encode($response);
	}
	else
	{
	//echo " Data insertion error... ";
	$response["operation"] = "new user register";
	$response["message"] = "User register failed ..exist already";
	// echo no users JSON
	echo json_encode($response);
	}
	
 ?>