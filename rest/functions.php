<?php require_once("connection.php"); ?>
<?php 

function sanisInput($data){

  $data =trim($data);
  $data = stripslashes($data);//prevent redirect the page and spoof
  $data = htmlspecialchars($data,ENT_QUOTES,'UTF-8');
  return $data;
  




}
function redirect_to($location=NULL){

  if ($location!=NULL){
	header("Location: {$location}");
	exit();
	
 }
  




}





























?>