<?php 

require("init.php"); 
/*$user_email = $_POST["email"];
$user_input_month = $_POST["month"];
$user_input_year= $_POST["year"];
*/
$user_email = 'isam.brahim@mycit.ie';
$user_input_month ='2';
$user_input_year = '2017';


// array for JSON response
$response = array();
$sql_query = "SELECT `time`, `input_glucose`, `input_insulin`, `input_note`, `day` FROM `dailyinput` 
WHERE email like '$user_email' and year like '$user_input_year' and month like '$user_input_month' ORDER BY `day`";

$result = mysqli_query($conn, $sql_query);

	if(mysqli_num_rows($result) >= 1) {
    // looping through all results
    // input node
    $response["input"] = array();
 
    while ($row = mysqli_fetch_array($result)) {
        // temp input array
        $input = array();
		$input['time']=$row['time'];
		$input['day']=$row['day'];
		$input['input_glucose']=$row['input_glucose'];
		$input['input_insulin']=$row['input_insulin']; 
		$input['input_note']=$row['input_note'] ;
		
		
        // push single product into final response array
        array_push($response["input"], $input);
    }
	
	// success
	//$response["operation"] = "Retrieve daily input";
	//$response["message"] = "view input successful";
	


	// echoing JSON response
	echo json_encode($response);
	}
	else
	{
	//$response["operation"] = "Retrieve daily input";
	//$response["message"] = "failed ";
	// echo no users JSON
	echo json_encode($response);
	}
	

 ?>