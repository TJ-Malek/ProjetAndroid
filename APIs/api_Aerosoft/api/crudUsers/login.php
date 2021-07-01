<?php
$Mail = $_POST['Mail'];
$MotDePasse = $_POST['MotDePasse'];
$response = array();
//Check if all fieds are given
if (empty($Mail) || empty($MotDePasse)) {
    $response['success'] = "0";
    $response['message'] = "Some fields are empty. Please try again!";
    echo json_encode($response);
    die;
}
$Maildetails = array(
    'Mail' => $Mail,
    'MotDePasse' => $MotDePasse
);
//Insert the Mail into the database
$success = loginMail($Maildetails);
if (!empty($success)) {
    $response['success'] = "1";
    $response['message'] = "Login successfully!";
    $response['details'] = $success;
    echo json_encode($response);
} else {
    $response['success'] = "0";
    $response['message'] = "Login failed. Please try again!";
    echo json_encode($response);
}
function loginMail($Maildetails) {
    require '../../config/database.php';
    $database = new Database();
    $db = $database->getConnection();
    $array = array();
    $stmt = $db->prepare("SELECT * FROM utilisateur WHERE Mail = :Mail  AND MotDePasse = :MotDePasse");
    $stmt->execute($Maildetails);
    $array = $stmt->fetch(PDO::FETCH_ASSOC);
    $stmt = null;
    return $array;
}