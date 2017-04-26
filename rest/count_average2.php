<?php 

require("connection.php"); 

/*$user_email = $_POST["email"];
$input_month = $_POST["month"];
$user_year = $_POST["year"];*/
$user_email = "isam.brahim@mycit.ie";
$input_month = "2";
$user_year = "2017";
$input_day = "23";
// array for JSON response
$response = array();
$sql_query = "select AVG(input_glucose) from dailyinput where email like '$user_email' And month='$input_month' And year = '$user_year' And day='$input_day';";



$result = mysqli_query($conn,$sql_query);
		echo json_encode($result); 


	if(mysqli_num_rows($result) >= 1) {
    // looping through all results
    // input node
    $response["input"] = array();
 
    while ($row = mysqli_fetch_array($result)) {
        // temp input array
        $input = array();
		//$user['email']=$row['email'];
		$input['day']=$row['day'];
		$input['month']=$row['month'];
		$input['year']=$row['year'];
		$input['time']=$row['time'];
		$input['input_glucose']=$row['input_glucose'] ; 
		$input['input_insulin']=$row['input_insulin']; 
		$input['input_note']=$row['input_note'] ;
 
        // push single product into final response array
        array_push($response["input"], $input);
    }
	
	// success
	$response["operation"] = "viewInput";
	$response["message"] = "view input successful";
	


	// echoing JSON response
	echo json_encode($response);

	}
	else
	{
	$response["operation"] = "viewInput";
	$response["message"] = "Selected month and year not valid";
	// echo no users JSON
	echo json_encode($response);
	
	}
	
	
	
	
    
	
	


 ?>