<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    include_once '../../config/database.php';
    include_once '../../class/constructeur.php';

    $database = new Database();
    $db = $database->getConnection();

    $items = new Constructeur($db);

    $stmt = $items->getConstructeurs();
    $itemCount = $stmt->rowCount();

    //echo json_encode($itemCount);

    if($itemCount > 0){
        
        $ConstructeurArr = array();
        $ConstructeurArr["constructeur"] = array();
       
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            $constructeur = array(
                "IdConstructeur" => $IdConstructeur,
                "NomConstructeur" => $NomConstructeur,          
            );

            array_push($ConstructeurArr["constructeur"], $constructeur);
        }
        echo json_encode($ConstructeurArr);
    }

    else{
        http_response_code(404);
        echo json_encode(
            array("message" => "Aucun constructeur trouvé.")
        );
    }
?>