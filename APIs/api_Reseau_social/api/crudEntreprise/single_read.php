<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once '../../config/database.php';
    include_once '../../class/entreprise.php';

    $database = new Database();
    $db = $database->getConnection();

    $item = new Entreprise($db);

    $item->IdEntreprise = isset($_GET['IdEntreprise']) ? $_GET['IdEntreprise'] : die();
  
    $item->getSingleEntreprise();

    if($item->Designation != null){
        // create array
        $entreprise = array(
            "IdEntreprise" => $item->IdEntreprise,
            "Designation" => $item->Designation,
            "Logo" => $item->Logo,
            "Description" => $item->Description,
            "url" => $item->url,
            "Statut" => $item->Statut       
        );

        http_response_code(200);
        echo json_encode($entreprise);
    }
      
    else{
        http_response_code(404);
        echo json_encode("Entreprise non trouvée.");
    }
?>