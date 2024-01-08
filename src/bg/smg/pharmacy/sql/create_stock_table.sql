CREATE TABLE stock (
   drug_id INT PRIMARY KEY,
   quantity INT,
   FOREIGN KEY (drug_id) REFERENCES drug (drug_id)
);
