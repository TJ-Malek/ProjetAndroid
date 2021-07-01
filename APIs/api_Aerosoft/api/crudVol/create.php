<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once '../../config/database.php';
    include_once '../../class/vol.php';

    $database = new Database();
    $db = $database->getConnection();

    $item = new Vol($db);

    $data = json_decode(file_get_contents("php://input"));
    
    $item->AeroportDept = $data->AeroportDept;
    $item->HDepart = $data->HDepart;
    $item->AeroportArr = $data->AeroportArr;
    $item->HArrivee = $data->HArrivee;
   
    
    if($item->createVol()){
        echo "Succès : Le vol a été ajouté.";
    } /*else{
        echo 'Vol could not be created.';
    }*/
?>