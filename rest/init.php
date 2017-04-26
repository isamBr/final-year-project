<?php 

$server_name = "localhost";
$mysql_user="root";
$mysql_pass="";
$db_name = "databaseapp";
$conn = mysqli_connect($server_name,$mysql_user,$mysql_pass,$db_name);


	if(!$conn)
	{
	//echo "Connection Error...".mysqli_connect_error();
	}
	else
	{
	//echo "<h3> Database connection Success... </h3>";
	}
	
	
	
    
	
	


 ?>