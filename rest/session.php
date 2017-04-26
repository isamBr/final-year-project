<?php 
session_start();


function logged_in(){

return isset($_SESSION['user_id']);//global session

}



function confirm_logged_in(){

if(!logged_in()){
redirect_to("login.php");

}


}


?>