CREATE TABLE drug_category (
   drug_id INT,
   category_id INT,
   PRIMARY KEY (drug_id, category_id),
   FOREIGN KEY (drug_id) REFERENCES drug (drug_id),
   FOREIGN KEY (category_id) REFERENCES disease_category (category_id)
);
