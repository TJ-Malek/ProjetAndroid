<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    include_once '../../config/database.php';
    include_once '../../class/vol.php';

    $database = new Database();
    $db = $database->getConnection();

    $items = new Vol($db);

    $stmt = $items->getVols();
    $itemCount = $stmt->rowCount();


    //echo json_encode($itemCount);

    if($itemCount > 0){
        
        $VolArr = array();
        $VolArr["vol"] = array();
       

        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            $vol = array(
                "NumVol" => $NumVol,
                "AeroportDept" => $AeroportDept,
                "HDepart" => $HDepart,
                "AeroportArr" => $AeroportArr,
                "HArrivee" => $HArrivee
            );

            array_push($VolArr["vol"], $vol);
        }
        echo json_encode($VolArr);
    }

    else{
        echo json_encode(
            array("message" => "No record found.")
        );
    }
?>