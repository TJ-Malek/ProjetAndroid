<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once '../../config/database.php';
    include_once '../../class/aeroport.php';

    $database = new Database();
    $db = $database->getConnection();

    $item = new Aeroport($db);

    $data = json_decode(file_get_contents("php://input"));
    
    $item->IdAeroport = $data->IdAeroport;
    $item->NomAeroport = $data->NomAeroport;
    $item->NomVilleDesservie = $data->NomVilleDesservie;
    
   
    
    if($item->createAeroport()){
        echo "Succès : L'aeroport a été ajouté.";
    } else{
        echo "Erreur : L'aeroport ne peut pas être créé.';
    }
?>