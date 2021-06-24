<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    
    include_once '../../config/database.php';
    include_once '../../class/constructeur.php';
    
    $database = new Database();
    $db = $database->getConnection();
    
    $item = new Constructeur($db);
    
    $data = json_decode(file_get_contents("php://input"));
    
    
    // Vol values
    $item->IdConstructeur  = $data->IdConstructeur;
    $item->NomConstructeur = $data->NomConstructeur;
    
    
    if($item->updateConstructeur()){
        echo json_encode("Succès : Le constructeur a été modifié.");
    } else{
        echo json_encode("Erreur : Le constructeur n'a pas été modifié.");
    }
?>