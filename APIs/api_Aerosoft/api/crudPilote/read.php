<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    include_once '../../config/database.php';
    include_once '../../class/pilote.php';

    $database = new Database();
    $db = $database->getConnection();

    $items = new Pilote($db);

    $stmt = $items->getPilotes();
    $itemCount = $stmt->rowCount();


    // echo json_encode($itemCount);
    
    if($itemCount > 0){
        
        $PiloteArr = array();
        $PiloteArr["pilote"] = array();
       

        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            $e = array(
                "IdPilote" => $IdPilote,
                "NomPilote" => $NomPilote,
                "PrenomPilote" => $PrenomPilote,
                "Matricule" => $Matricule
            );

            array_push($PiloteArr["pilote"], $e);
        }
        echo json_encode($PiloteArr);
    }

    else{
        
        echo json_encode(
            array("message" => "No record found.")
        );
    }
?>