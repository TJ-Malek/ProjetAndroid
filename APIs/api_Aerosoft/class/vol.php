<?php
    class Vol{

        // Connection
        private $conn;

        // Table
        private $db_table = "vol";

        // Columns
        public $NumVol;
        public $AeroportDept;
        public $HDepart ;
        public $AeroportArr;
        public $HArrivee;
       

        // Db connection
        public function __construct($db){
            $this->conn = $db;
        }

        // GET ALL
        public function getVols(){
            $sqlQuery = "SELECT NumVol, AeroportDept, HDepart , AeroportArr, HArrivee FROM " . $this->db_table . "";
            $stmt = $this->conn->prepare($sqlQuery);
            $stmt->execute();
            return $stmt;
        }

        // CREATE
        public function createVol(){
            if($this->VerifVol()==null){
            $sqlQuery = "INSERT INTO
                        ". $this->db_table ."
                    SET
                        NumVol = :NumVol,
                        AeroportDept = :AeroportDept, 
                        HDepart  = :HDepart, 
                        AeroportArr = :AeroportArr, 
                        HArrivee = :HArrivee";
        
            $stmt = $this->conn->prepare($sqlQuery);
        
            // sanitize
            $this->NumVol=htmlspecialchars(strip_tags($this->NumVol));
            $this->AeroportDept=htmlspecialchars(strip_tags($this->AeroportDept));
            $this->HDepart =htmlspecialchars(strip_tags($this->HDepart));
            $this->AeroportArr=htmlspecialchars(strip_tags($this->AeroportArr));
            $this->HArrivee=htmlspecialchars(strip_tags($this->HArrivee));
            
        
            // bind data
            $stmt->bindParam(":NumVol", $this->NumVol);
            $stmt->bindParam(":AeroportDept", $this->AeroportDept);
            $stmt->bindParam(":HDepart", $this->HDepart);
            $stmt->bindParam(":AeroportArr", $this->AeroportArr);
            $stmt->bindParam(":HArrivee", $this->HArrivee);
           
        
            if($stmt->execute()){
               return true;
            }
            return false;
        }
        else {
            echo "Vol existe déjà dans la base";
        }
        }

        public function VerifVol(){
            $sqlQuery = "SELECT
                         
            AeroportDept, 
            HDepart , 
            AeroportArr, 
            HArrivee 
         
          FROM
            ". $this->db_table ."
        WHERE 
           NumVol = ?
        LIMIT 0,1";

        $stmt = $this->conn->prepare($sqlQuery);

        $stmt->bindParam(1, $this->NumVol);

        $stmt->execute();

        $data = $stmt->fetch(PDO::FETCH_ASSOC);
        return $data; 
        }
        // getSingle
        public function getSingleVol(){
           
            $dataRow = $this->VerifVol();
            $this->AeroportDept = $dataRow['AeroportDept'];
            $this->HDepart  = $dataRow['HDepart'];
            $this->AeroportArr = $dataRow['AeroportArr'];
            $this->HArrivee = $dataRow['HArrivee'];
           
        }        

        // UPDATE
        public function updateVol(){
            $sqlQuery = "UPDATE
                        ". $this->db_table ."
                    SET
                        AeroportDept = :AeroportDept, 
                        HDepart  = :HDepart, 
                        AeroportArr = :AeroportArr, 
                        HArrivee = :HArrivee
                        
                    WHERE 
                        NumVol = :NumVol";
        
            $stmt = $this->conn->prepare($sqlQuery);
        
            $this->AeroportDept=htmlspecialchars(strip_tags($this->AeroportDept));
            $this->HDepart =htmlspecialchars(strip_tags($this->HDepart));
            $this->AeroportArr=htmlspecialchars(strip_tags($this->AeroportArr));
            $this->HArrivee=htmlspecialchars(strip_tags($this->HArrivee));
            $this->NumVol=htmlspecialchars(strip_tags($this->NumVol));
        
            // bind data
            $stmt->bindParam(":AeroportDept", $this->AeroportDept);
            $stmt->bindParam(":HDepart", $this->HDepart);
            $stmt->bindParam(":AeroportArr", $this->AeroportArr);
            $stmt->bindParam(":HArrivee", $this->HArrivee);
            $stmt->bindParam(":NumVol", $this->NumVol);
        
            if($stmt->execute()){
               return true;
            }
            return false;
        }

        // DELETE
        function deleteVol(){
            $sqlQuery = "DELETE FROM " . $this->db_table . " WHERE NumVol = ?";
            $stmt = $this->conn->prepare($sqlQuery);
        
            $this->NumVol=htmlspecialchars(strip_tags($this->NumVol));
        
            $stmt->bindParam(1, $this->NumVol);
            echo "okkkkkkkkkkk";
        echo "numvol $this->NumVol";
            if($stmt->execute()){
                return true;
            }
            return false;
        }

    }
?>

