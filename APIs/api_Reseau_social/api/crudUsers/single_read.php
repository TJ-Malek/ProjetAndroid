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

    $item->IdUtilisateur = isset($_GET['IdUtilisateur']) ? $_GET['IdUtilisateur'] : die();
  
    $item->getSingleUtilsateur();

    if($item->NomUtilisateur != null){
        // create array
        $utilisateur = array(
            "IdUtilisateur" => $item->IdUtilisateur,
            "NomUtilisateur" => $item->NomUtilisateur,
            "Mail" => $item->Mail,
            "Role" => $item->Role,
            "Statut" => $item->Statut       
        );

        http_response_code(200);
        echo json_encode($utilisateur);
    }
      
    else{
        http_response_code(404);
        echo json_encode("Utilisateur non trouvée.");
    }
?>