<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    include_once '../../config/database.php';
    include_once '../../class/avion.php';

    $database = new Database();
    $db = $database->getConnection();

    $items = new Avion($db);

    $stmt = $items->getAvions();
    $itemCount = $stmt->rowCount();

    //echo json_encode($itemCount);

    if($itemCount > 0){
        
        $AvionArr = array();
        $AvionArr["avion"] = array();
       
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            $avion = array(
                "NumAvion" => $NumAvion,
                "TypeAvion" => $TypeAvion,
                "BaseAeroport" => $BaseAeroport              
            );

            array_push($AvionArr["avion"], $avion);
        }
        echo json_encode($AvionArr);
    }

    else{
        
        echo json_encode(
            array("avion" => "Aucun avion trouvé.")
        );
    }
?>