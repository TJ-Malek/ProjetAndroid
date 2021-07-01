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

    $item->NumAvion = isset($_GET['NumAvion']) ? $_GET['NumAvion'] : die();
  
    $item->getSingleAvion();

    if($item->TypeAvion != null){
        // create array
        $avion = array(
            "NumAvion" => $item->NumAvion,
            "TypeAvion" => $item->TypeAvion,
            "BaseAeroport" => $item->BaseAeroport
            
        );

        http_response_code(200);
        echo json_encode($avion);
    }
      
    else{
        
        echo json_encode("Avion non trouvé.");
    }
?>