<?php
    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Max-Age: 3600");
    header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
    
    include_once '../../config/database.php';
    include_once '../../class/entreprise.php';
    
    $database = new Database();
    $db = $database->getConnection();
    
    $item = new Entreprise($db);
    
    $data = json_decode(file_get_contents("php://input"));

    echo json_encode($data);
    
    
    // Entreprise values
    $item->IdEntreprise = $data->IdEntreprise;
    $item->Designation = $data->Designation;
    $item->Logo = $data->Logo;
    $item->Description = $data->Description;
    $item->url = $data->url;
    $item->Statut = $data->Statut;

    echo json_encode($item);
    
    if($item->updateEntreprise()){
        $res = array(
            "res" => "ok"
        );
echo json_encode($res);
    } else{
        $res = array(
            "res" => "Erreur : Les paramètres de l'entreprise ne sont pas modifié."
        );
echo json_encode($res);
       // echo json_encode("Erreur : Le vol n'a pas été modifié.");
    }
?>