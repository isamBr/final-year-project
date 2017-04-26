<?php 

require("connection.php"); 
$user_email =$_POST["email"];
$user_name =$_POST["user_name"];
$user_surname =$_POST["user_surname"];
$user_type =$_POST["user_type"];
$user_gender =$_POST["user_gender"];
$user_height = $_POST["user_height"];
$user_age = $_POST["user_age"];
$user_mobile = $_POST["user_mobile"];
// array for JSON response
$response = array();

/*$user_email ='isam';
$user_name ='isam';
$user_surname ='isam';
$user_type ='Type 2';
$user_gender ='Male';
$user_height ='1.23';
$user_age = '22';
$user_mobile = '08576351';*/


$sql_query = "UPDATE `users_details` SET `name`='$user_name',
`Surname`='$user_surname',`age`='$user_age',`height`='$user_height',`diabetes type`='$user_type',
`gender`='$user_gender',`Mobile`='$user_mobile' 
WHERE `email`='$user_email'";


$verify_query = mysqli_query($conn, $sql_query);

	if($verify_query)
	{
	$user = array();
	$user['email']=$user_email;
	$user['name']=$user_name;
	$user['Surname']=$user_surname;
	$user['age']=$user_age ; 
	$user['height']=$user_height; 
	$user['diabetes type']=$user_type ; 
	$user['gender'] =$user_gender ;
	$user["Mobile"]= $user_mobile;
	// success
	$response["operation"] = "user details";
	$response["message"] = "Users details update successfully";
	// user node
	$response["user"] = array();

	array_push($response["user"], $user);

	// echoing JSON response
	echo json_encode($response);

	}
	else
	{
	$response["operation"] = "user details";
	$response["message"] = "Users details update error";
	// echo no users JSON
	echo json_encode($response);
	}
	
	
	
	
    
	
	


 ?>