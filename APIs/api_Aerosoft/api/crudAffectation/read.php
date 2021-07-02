<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    include_once '../../config/database.php';
    include_once '../../class/affectation.php';

    $database = new Database();
    $db = $database->getConnection();

    $items = new Affectation($db);

    $stmt = $items->getAffectations();
    $itemCount = $stmt->rowCount();

    //echo json_encode($itemCount);

    if($itemCount > 0){
        
        $AffectationArr = array();
        $AffectationArr["affectation"] = array();
       
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            $affectation = array(
                "IdAffectation" => $IdAffectation,
                "NumVol" => $NumVol,
                "DateVol" => $DateVol,
                "AffectationCode" => $AffectationCode,
                "NumAvion" => $NumAvion,
                "IdPilote" => $IdPilote         
            );

            array_push($AffectationArr["affectation"], $affectation);
        }
        echo json_encode($AffectationArr);
    }

    else{
        
        echo json_encode(
            array("affectation" => "Aucune affectation trouvée.")
        );
    }
?>