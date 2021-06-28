<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    include_once '../../config/database.php';
    include_once '../../class/entreprise.php';

    $database = new Database();
    $db = $database->getConnection();

    $items = new Entreprise($db);

    $stmt = $items->getEntreprises();
    $itemCount = $stmt->rowCount();

    //echo json_encode($itemCount);

    if($itemCount > 0){
        
        $EntrepriseArr = array();
        $EntrepriseArr["entreprise"] = array();
       
        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            $entreprise = array(
                "IdEntreprise" => $IdEntreprise,
                "Designation" => $Designation,
                "Logo" => $Logo,
                "Description" => $Description,
                "url" => $url,
                "Statut" => $Statut          
            );

            array_push($EntrepriseArr["entreprise"], $entreprise);
        }
        echo json_encode($EntrepriseArr);
    }

    else{
        http_response_code(404);
        echo json_encode(
            array("message" => "Aucune entreprise trouvée.")
        );
    }
?>