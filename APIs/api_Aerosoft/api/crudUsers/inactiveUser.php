<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    
    include_once '../../config/database.php';
    include_once '../../class/utilisateur.php';

    $database = new Database();
    $db = $database->getConnection();

    $items = new Utilisateur($db);

    $stmt = $items->GetUserToActivate();
    $itemCount = $stmt->rowCount();


    // echo json_encode($itemCount);
    
    if($itemCount > 0){
        
        $UserArr = array();
        $UserArr["utilisateurs"] = array();
       

        while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
            extract($row);
            $e = array(
                "IdUtilisateur" => $IdUtilisateur,
                "Nom" => $Nom,
                "Prenom" => $Prenom,
                "Mail" => $Mail,
                "Statut" => $Statut
            );

            array_push($UserArr["utilisateurs"], $e);
        }
        echo json_encode($UserArr);
    }

    else{
        echo json_encode(
            array("message" => "No record found.")
        );
    }
?>