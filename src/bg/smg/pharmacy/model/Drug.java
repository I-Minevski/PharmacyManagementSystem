package bg.smg.pharmacy.model;
public class Drug {
    private Integer drug_id;
    private String name;
    private Double price;
    private String description;
    private Boolean prescriptionRequired;
    private String standardDosage;
    private Boolean isDeleted;

    public Drug (){
    }

    public Drug(String name, Double price, String description, Boolean prescriptionRequired, String standardDosage) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.prescriptionRequired = prescriptionRequired;
        this.standardDosage = standardDosage;
    }

    public Integer getDrugId() {
        return drug_id;
    }

    public void setDrugId(Integer drug_id) {
        this.drug_id = drug_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isPrescriptionRequired() {
        return prescriptionRequired;
    }

    public void setPrescriptionRequired(Boolean prescriptionRequired) {
        this.prescriptionRequired = prescriptionRequired;
    }

    public String getStandardDosage() {
        return standardDosage;
    }

    public void setStandardDosage(String standardDosage) {
        this.standardDosage = standardDosage;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
