<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once '../../config/database.php';
    include_once '../../class/affectation.php';

    $database = new Database();
    $db = $database->getConnection();

    $item = new Affectation($db);

    $item->IdAffectation = isset($_GET['IdAffectation']) ? $_GET['IdAffectation'] : die();
  
    $item->getSingleAffectation();

    if($item->AffectationCode != null){
        // create array
        $affectation = array(
            "IdAffectation" => $item->IdAffectation,
            "NumVol" => $item->NumVol,
            "DateVol" => $item->DateVol,
            "AffectationCode" => $item->AffectationCode,
            "NumAvion" => $item->NumAvion,
            "IdPilote" => $item->IdPilote  
            
        );

        http_response_code(200);
        echo json_encode($affectation);
    }
      
    else{
        
        echo json_encode("Affectation non trouvé.");
    }
?>