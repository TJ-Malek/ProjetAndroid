<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    
    include_once '../../config/database.php';
    include_once '../../class/utilisateur.php';
    
    $database = new Database();
    $db = $database->getConnection();
    
    $item = new Utilisateur($db);
    
    $data = json_decode(file_get_contents("php://input"));
    
    // Utilisateur values
    $item->IdUtilisateur = $data->IdUtilisateur;
    
    if($item->activateUser()){
        echo json_encode("Succès : L'utilisateur a été activé.");
    } else{
        echo json_encode("Erreur : L'utilisateur n'a pas été activé.");
    }
?>