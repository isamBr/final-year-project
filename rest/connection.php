<?php 

$server_name = "us-cdbr-iron-east-03.cleardb.net:3306";
$mysql_user="b422d8f356ab36";
$mysql_pass="b0be811f";
$db_name = "ad_b570bf3b72129fc";
$conn = new mysqli($server_name,$mysql_user,$mysql_pass,$db_name);

//$conn = mysqli_connect(DB_SERVER,DB_USER,DB_PASS,DB_NAME);


	if(!$conn){
	$output = 'unable to connect to database';
	echo "Connection Error...".mysqli_connect_error();
	include('output.html.php');
	exit();
	}
	else{
	//echo "<h3> Database connection Success to ibm data..</h3>";
	}

	if(!mysqli_set_charset($conn, 'utf8')){
	$output = 'unable to convert the caracter ';
	include('output.html.php');
	exit();

	}

	if(!mysqli_select_db($conn, 'ad_b570bf3b72129fc')){
		$output = 'unable to select database';
		include('output.html.php');
		exit();
	}
	
	function close(){
		mysql_close();
	}
	
	
	
    
	
	


 ?>