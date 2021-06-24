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

    $item->NumVol = isset($_GET['IdConstructeur']) ? $_GET['IdConstructeur'] : die();
  
    $item->getSingleConstructeur();

    if($item->TypeAvion != null){
        // create array
        $vol = array(
            "IdConstructeur" => $item->IdConstructeur,
            "NomConstructeur" => $item->NomConstructeur,
            
        );

        http_response_code(200);
        echo json_encode($constructeur);
    }
      
    else{
        http_response_code(404);
        echo json_encode("Constructeur non trouvé.");
    }
?>