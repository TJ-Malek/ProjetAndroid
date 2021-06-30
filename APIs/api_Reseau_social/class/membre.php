<?php
    class Membre{

        // Connection
        private $conn;

        // Table
        private $db_table = ["membre","entreprise"];

        // Columns
        public $IdMembre;
        public $Nom;
        public $Prenom;
        public $Photo;
        public $Poste;
        public $Grade;
        public $Departement;
        public $Date_Embauche;
        public $IdEntreprise;
        

        // Db connection
        public function __construct($db){
            $this->conn = $db;
        }

        // GET ALL
        public function getMembre(){
            $sqlQuery = "SELECT * FROM " . $this->db_table[0] . "
                            INNER JOIN " . $this->db_table[1] ."
                            ON entreprise.IdEntreprise = membre.IdEntreprise";
            $stmt = $this->conn->prepare($sqlQuery);
            $stmt->execute();
            return $stmt;
        }

        //IdAeroport, 
        public function VerifMembre(){
            $sqlQuery = "SELECT * FROM " . $this->db_table[0] . "
            INNER JOIN " . $this->db_table[1] ."
            ON entreprise.IdEntreprise = membre.IdEntreprise
            WHERE 
            IdMembre = ?
            LIMIT 0,1";

            $stmt = $this->conn->prepare($sqlQuery);
            $stmt->bindParam(1, $this->IdMembre);
            $stmt->execute();
            $data = $stmt->fetch(PDO::FETCH_ASSOC);
            return $data; 
        }

        // GET SINGLE
        public function getSingleMembre(){
            $dataRow = $this->VerifMembre();
            $this->IdMembre = $dataRow['IdMembre'];
            $this->Nom = $dataRow['Nom'];
            $this->Prenom = $dataRow['Prenom'];
            $this->Photo = $dataRow['Photo'];
            $this->Poste = $dataRow['Poste'];
            $this->Grade = $dataRow['Grade'];
            $this->Departement = $dataRow['Departement'];
            $this->Date_Embauche = $dataRow['Date_Embauche'];
            $this->IdEntreprise = $dataRow['IdEntreprise'];
            $this->Designation = $dataRow['Designation'];
        }
    }
?>

