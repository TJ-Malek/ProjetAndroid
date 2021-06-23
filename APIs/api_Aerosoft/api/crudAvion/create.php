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
    $item->NumAvion = $data->NumAvion;
    $item->IdAeroport = $data->IdAeroport;
    $item->TypeAvion = $data->TypeAvion;
    
   
    
    if($item->createAvion()){
        echo "Succès : L'avion a été ajouté.";
    } else{
        echo "Erreur : L'avion ne peut pas être créé.';
    }
?>