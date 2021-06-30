<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

    include_once '../../config/database.php';
    include_once '../../class/vol.php';

    $database = new Database();
    $db = $database->getConnection();

    $item = new Vol($db);

    $item->NumVol = isset($_GET['NumVol']) ? $_GET['NumVol'] : die();
  
    $item->getSingleVol();

    if($item->AeroportDept != null){
        // create array
        $vol = array(
            "NumVol" => $item->NumVol,
            "AeroportDept" => $item->AeroportDept,
            "HDepart" => $item->HDepart,
            "AeroportArr" => $item->AeroportArr,
            "HArrivee" => $item->HArrivee
        );

     
      
        http_response_code(200);
        echo json_encode($vol);
    }
      
    else{
        http_response_code(404);
        echo json_encode("Vol non trouvé.");
    }
?>