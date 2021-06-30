<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    
    include_once '../../config/database.php';
    include_once '../../class/avion.php';
    
    $database = new Database();
    $db = $database->getConnection();
    
    $item = new Avion($db);
    
    $data = json_decode(file_get_contents("php://input"));
    

    
    // Vol values
    $item->NumAvion  = $data->NumAvion;
    $item->TypeAvion = $data->TypeAvion;
    $item->BaseAeroport = $data->BaseAeroport;
    
    
    if($item->updateAvion()){
        echo json_encode("Succès : L'avion a été modifié.");
    } else{
        echo json_encode("Erreur : L'avion n'a pas été modifié.");
    }
?>