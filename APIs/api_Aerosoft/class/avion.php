<?php
    class Avion{

        // Connection
        private $conn;

        // Table
        private $db_table = "avion";

        // Columns
        public $NumAvion;
        public $TypeAvion;
        public $BaseAeroport ;
        
        // Db connection
        public function __construct($db){
            $this->conn = $db;
        }

        // GET ALL
        public function getAvions(){
            $sqlQuery = "SELECT NumAvion, TypeAvion, BaseAeroport FROM " . $this->db_table . "";
            $stmt = $this->conn->prepare($sqlQuery);
            $stmt->execute();
            return $stmt;
        }

        // CREATE
        public function createAvion(){
            $sqlQuery = "INSERT INTO
                        ". $this->db_table ."
                    SET
                        NumAvion = :NumAvion, 
                        TypeAvion  = :TypeAvion, 
                        BaseAeroport = :BaseAeroport,
                        created = :created";
        
            $stmt = $this->conn->prepare($sqlQuery);
        
            // sanitize
            $this->NumAvion=htmlspecialchars(strip_tags($this->NumAvion));
            $this->TypeAvion=htmlspecialchars(strip_tags($this->TypeAvion ));
            $this->BaseAeroport=htmlspecialchars(strip_tags($this->BaseAeroport));
            
            // bind data
            $stmt->bindParam(":NumAvion", $this->NumAvion);
            $stmt->bindParam(":TypeAvion ", $this->TypeAvion );
            $stmt->bindParam(":BaseAeroport", $this->BaseAeroport);
                 
            if($stmt->execute()){
               return true;
            }
            return false;
        }

        // GET SINGLE
        public function getSingleAvion(){
            $sqlQuery = "SELECT
                        NumAvion, 
                        TypeAvion, 
                        BaseAeroport, 
                      FROM
                        ". $this->db_table ."
                    WHERE 
                       NumAvion = ?
                    LIMIT 0,1";

            $stmt = $this->conn->prepare($sqlQuery);

            $stmt->bindParam(1, $this->Numvion);
            echo json_encode($this->Numvion);
            //echo json_encode($stmt);
die();
            $stmt->execute();
            
            $dataRow = $stmt->fetch(PDO::FETCH_ASSOC);
            
            $this->NumAvion = $dataRow['NumAvion'];
            $this->TypeAvion  = $dataRow['TypeAvion'];
            $this->BaseAeroport = $dataRow['BaseAeroport'];
           
        }        

        // UPDATE
        public function updateAvion(){
            $sqlQuery = "UPDATE
                        ". $this->db_table ."
                    SET
                        NumAvion = :NumAvion, 
                        TypeAvion  = :TypeAvion , 
                        BaseAeroport = :BaseAeroport,   
                    WHERE 
                        NumAvion = :NumAvion";
        
            $stmt = $this->conn->prepare($sqlQuery);
        
            $this->NumAvion=htmlspecialchars(strip_tags($this->NumAvion));
            $this->TypeAvion =htmlspecialchars(strip_tags($this->TypeAvion));
            $this->BaseAeroport=htmlspecialchars(strip_tags($this->BaseAeroport));
           
            $this->NumAvion=htmlspecialchars(strip_tags($this->NumAvion));
        
            // bind data
            $stmt->bindParam(":NumAvion", $this->NumAvion);
            $stmt->bindParam(":TypeAvion ", $this->TypeAvion );
            $stmt->bindParam(":BaseAeroport", $this->BaseAeroport);
            
            $stmt->bindParam(":NumAvion", $this->NumAvion);
        
            if($stmt->execute()){
               return true;
            }
            return false;
        }

        // DELETE
        function deleteAvion(){
            $sqlQuery = "DELETE FROM " . $this->db_table . " WHERE NumAvion = ?";
            $stmt = $this->conn->prepare($sqlQuery);
        
            $this->NumAvion=htmlspecialchars(strip_tags($this->NumAvion));
        
            $stmt->bindParam(1, $this->NumAvion);
        
            if($stmt->execute()){
                return true;
            }
            return false;
        }

    }
?>

