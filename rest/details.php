<?php 

require("init.php"); 
//$user_email = $_POST["email"];
$user_email = "isam.brahim@mycit.ie";
// array for JSON response
$response = array();
$sql_query = "select email from users_details where email like '$user_email' ;";

$result = mysqli_query($conn,$sql_query);


	if(mysqli_num_rows($result) == 1)
	{
	$row = mysqli_fetch_array($result);
	$email = $row["email"];
	$newQuery = "SELECT * FROM users_details WHERE `email` = '{$user_email}'";
	$verify_query = mysqli_query($conn, $newQuery);
	$row = $verify_query->fetch_assoc();
	//echo "Login Success...";
	$user = array();
	$user['email']=$row['email'];
	$user['name']=$row['name'];
	$user['Surname']=$row['Surname'];
	$user['age']=$row['age'] ; 
	$user['height']=$row['height']; 
	$user['diabetes type']=$row['diabetes type'] ; 
	$user['gender'] =$row['gender'] ;
	$user["Mobile"]= $row["Mobile"];
	$user['doctor_email'] =$row['doctor_email'] ;
	$user["emergency_phone"]= $row["emergency_phone"];
	// success
	$response["operation"] = "emergency";
	$response["message"] = "success";
	// user node
	$response["user"] = array();

	array_push($response["user"], $user);

	// echoing JSON response
	echo json_encode($response);

	}
	else
	{
	$response["operation"] = "emergency";
	$response["message"] = "Failed..";
	// echo no users JSON
	echo json_encode($response);
	
	}
	
	
	
	
    
	
	


 ?>