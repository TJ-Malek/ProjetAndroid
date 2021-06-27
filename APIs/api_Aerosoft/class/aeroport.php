<?php
    class Aeroport{

        // Connection
        private $conn;

        // Table
        private $db_table = "aeroport";

        // Columns
        public $IdAeroport;
        public $NomAeroport;
        public $NomVilleDesservie;
       

        // Db connection
        public function __construct($db){
            $this->conn = $db;
        }

        // GET ALL
        public function getAeroports(){
            $sqlQuery = "SELECT IdAeroport, NomAeroport, NomVilleDesservie FROM " . $this->db_table . "";
            $stmt = $this->conn->prepare($sqlQuery);
            $stmt->execute();
            return $stmt;
        }
        // CREATE
        public function createAeroport(){
            $sqlQuery = "INSERT INTO
                        ". $this->db_table ."
                    SET
                        IdAeroport = :IdAeroport, 
                        NomAeroport  = :NomAeroport, 
                        NomVilleDesservie = :NomVilleDesservie,
                        created = :created";
        
            $stmt = $this->conn->prepare($sqlQuery);
        
            // sanitize
            $this->IdAeroport=htmlspecialchars(strip_tags($this->IdAeroport));
            $this->NomAeroport=htmlspecialchars(strip_tags($this->NomAeroport ));
            $this->NomVilleDesservie=htmlspecialchars(strip_tags($this->NomVilleDesservie));
            
            // bind data
            $stmt->bindParam(":IdAeroport", $this->IdAeroport);
            $stmt->bindParam(":NomAeroport", $this->NomAeroport);
            $stmt->bindParam(":NomVilleDesservie", $this->NomVilleDesservie);
                 
            if($stmt->execute()){
               return true;
            }
            return false;
        }
        //IdAeroport, 
        public function VerifAeroport(){
            $sqlQuery = "SELECT
                         
            
            NomAeroport, 
            NomVilleDesservie
                      
          FROM
            ". $this->db_table ."
        WHERE 
           IdAeroport = ?
        LIMIT 0,1";

        $stmt = $this->conn->prepare($sqlQuery);

        $stmt->bindParam(1, $this->IdAeroport);

        $stmt->execute();

        $data = $stmt->fetch(PDO::FETCH_ASSOC);
        return $data; 
        }
        // GET SINGLE
        public function getSingleAeroport(){

            $dataRow = $this->VerifAeroport();
            //$this->IdAeroport = $dataRow['IdAeroport'];
            $this->NomAeroport = $dataRow['NomAeroport'];
            $this->NomVilleDesservie = $dataRow['NomVilleDesservie']; 


        /*    $sqlQuery = "SELECT
                        IdAeroport, 
                        NomAeroport, 
                        NomVilleDesservie, 
                      FROM
                        ". $this->db_table ."
                    WHERE 
                       IdAeroport = ?
                    LIMIT 0,1";

            $stmt = $this->conn->prepare($sqlQuery);

            $stmt->bindParam(1, $this->IdAeroport);

            $stmt->execute();

            $dataRow = $stmt->fetch(PDO::FETCH_ASSOC);
            
            $this->IdAeroport = $dataRow['IdAeroport'];
            $this->NomAeroport = $dataRow['NomAeroport'];
            $this->NomVilleDesservie = $dataRow['NomVilleDesservie'];    */
           
        }        

        // UPDATE
        public function updateAeroport(){
            $sqlQuery = "UPDATE
                        ". $this->db_table ."
                    SET
                    IdAeroport = :IdAeroport, 
                    NomAeroport = :NomAeroport , 
                    NomVilleDesservie = :NomVilleDesservie,   
                    WHERE 
                    IdAeroport = :IdAeroport";
        
            $stmt = $this->conn->prepare($sqlQuery);
        
            $this->IdAeroport=htmlspecialchars(strip_tags($this->IdAeroport));
            $this->NomAeroport =htmlspecialchars(strip_tags($this->NomAeroport));
            $this->NomVilleDesservie=htmlspecialchars(strip_tags($this->NomVilleDesservie));
        
            // bind data
            $stmt->bindParam(":IdAeroport", $this->IdAeroport);
            $stmt->bindParam(":NomAeroport ", $this->NomAeroport );
            $stmt->bindParam(":NomVilleDesservie", $this->NomVilleDesservie);
            
            if($stmt->execute()){
               return true;
            }
            return false;
        }

       
        // DELETE
        function deleteAeroport(){
            $sqlQuery = "DELETE FROM " . $this->db_table . " WHERE IdAeroport = ?";
            $stmt = $this->conn->prepare($sqlQuery);
        
            $this->IdAeroport=htmlspecialchars(strip_tags($this->IdAeroport));
        
            $stmt->bindParam(1, $this->IdAeroport);
        
            if($stmt->execute()){
                return true;
            }
            return false;
        }
        

      

    }
?>

