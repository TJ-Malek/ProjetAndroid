<?php
    class Affectation{

        // Connection
        private $conn;

        // Table
        private $db_table = "affectation";

        // Columns
        public $IdAffectation;
        public $NumVol;
        public $DateVol;
        public $AffectationCode;
        public $NumAvion;
        public $IdPilote; 

        // Db connection
        public function __construct($db){
            $this->conn = $db;
        }

        // GET ALL
        public function getAffectations(){
            $sqlQuery = "SELECT IdAffectation, NumVol, DateVol, AffectationCode, NumAvion,IdPilote  FROM " . $this->db_table . "";
            $stmt = $this->conn->prepare($sqlQuery);
            $stmt->execute();
            return $stmt;
        }

       

        public function VerifAffectation(){
            $sqlQuery = "SELECT
                         
            NumVol, 
            DateVol, 
            AffectationCode, 
            NumAvion,
            IdPilote 
         
          FROM
            ". $this->db_table ."
        WHERE 
           IdAffectation = ?
        LIMIT 0,1";

        $stmt = $this->conn->prepare($sqlQuery);

        $stmt->bindParam(1, $this->IdAffectation);

        $stmt->execute();

        $data = $stmt->fetch(PDO::FETCH_ASSOC);
        return $data; 

        }
        // getSingle
        public function getSingleAffectation(){
           
            $dataRow = $this->VerifAffectation();
            $this->NumVol = $dataRow['NumVol'];
            $this->DateVol = $dataRow['DateVol'];
            $this->AffectationCode = $dataRow['AffectationCode'];
            $this->NumAvion = $dataRow['NumAvion'];
            $this->IdPilote = $dataRow['IdPilote'];
        }        

      

    }
?>

