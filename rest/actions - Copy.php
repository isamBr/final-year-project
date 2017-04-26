<?php 
    
    // Determine DB settings
    $localhost = array(
        '127.0.0.1',
        '::1'
    );
    if(in_array($_SERVER['REMOTE_ADDR'], $localhost)){ // your machine
        $servername = "localhost";
        $dbname = "databaseapp";
        $username = "root";
        $password = "";
    }
    else {
        $servername = "localhost"; // your host
        $dbname = "applandr_yellow";
        $username = "applandr_yellow";
        $password = "yellowproject"; 
    }

    // Create & check connection
    $conn = new mysqli($servername, $username, $password);
    if ($conn->connect_error) {
        die("Connection failed: " . $conn->connect_error);
    } 
    else {
        mysqli_select_db($conn, $dbname);
    }


    $email = $conn->real_escape_string($_GET['email']);
    $password = $conn->real_escape_string($_GET['password']);
    $registerUser = $_GET['registerUser'];
    $getUser = $_GET['getUser'];

    writeToLogs("Users.txt", "\n\n\n New User... [" . date("Y-m-d h:i:sa", time()) . "]");
    writeToLogs("Users.txt", "\n ----------------------------------------");
    writeToLogs("Users.txt", "\n Email: " . $email);
    writeToLogs("Users.txt", "\n Password: " . $password);
    writeToLogs("Users.txt", "\n Register User: " . $registerUser);
    writeToLogs("Users.txt", "\n Get User: " . $getUser);
    
    
    if (isset($_GET['registerUser'])) {

        // if($email != "" && $password != "") {
            $sql = "INSERT INTO users (email, password) VALUES ('{$email}', '{$password}')";
            $result = mysqli_query($conn, $sql);
            

            if(!$result) {
                writeToLogs("Users.txt", "\n Failure. " . $sql);
            }
            else {
                writeToLogs("Users.txt", "\n Success. " . $sql);
            }
        // }
        // else {
        //     writeToLogs("Users.txt", "\n Email or Password is blank!";
        // }
    }

    else if (isset($_GET['getUser'])) {
        echo "Joey";

        $sql = "SELECT * FROM users WHERE email = '{$email}' ";
        $result = mysqli_query($conn, $sql);
        

        if(mysqli_num_rows($result) > 0) {
            echo json_encode(mysqli_fetch_all($result, MYSQLI_ASSOC));
            writeToLogs("Users.txt", "\n Success. " . $sql);
        }
        else {
            writeToLogs("Users.txt", "\n Failure. " . $sql);
        }
    }


    function writeToLogs($fileToWrite, $textToWrite) {
        $updatedFile = file_get_contents($fileToWrite);
        $updatedFile .= $textToWrite;
        file_put_contents($fileToWrite, $updatedFile);
    }
?>