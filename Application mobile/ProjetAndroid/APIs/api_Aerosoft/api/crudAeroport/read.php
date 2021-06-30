<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    include_once '../../config/database.php';
    include_once '../../class/aeroport.php';

    $database = new Database();
    $db = $database->getConnection();

    $items = new Aeroport($db);

    $stmt = $items->getAeroports();
    $itemCount = $stmt->rowCount();

    //echo json_encode($itemCount);

    if($itemCount > 0){
        
        $AeroportArr = array();
        $AeroportArr["aeroport"] = array();
       
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            $aeroport = array(
                "IdAeroport" => $IdAeroport,
                "NomAeroport" => $NomAeroport,
                "NomVilleDesservie" => $NomVilleDesservie              
            );

            array_push($AeroportArr["aeroport"], $aeroport);
        }
        echo json_encode($AeroportArr);
    }

    else{
        http_response_code(404);
        echo json_encode(
            array("message" => "Aucun avion trouvé.")
        );
    }
?>