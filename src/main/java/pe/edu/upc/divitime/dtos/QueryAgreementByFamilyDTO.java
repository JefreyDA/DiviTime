package pe.edu.upc.divitime.dtos;

import java.time.LocalDateTime;

public class QueryAgreementByFamilyDTO {
    private String titleAgreement;
    private String descriptionAgreement;
    private LocalDateTime creationDate;
    private String nameFamily;
    private String nameContract;

    public String getTitleAgreement() {
        return titleAgreement;
    }

    public void setTitleAgreement(String titleAgreement) {
        this.titleAgreement = titleAgreement;
    }

    public String getDescriptionAgreement() {
        return descriptionAgreement;
    }

    public void setDescriptionAgreement(String descriptionAgreement) {
        this.descriptionAgreement = descriptionAgreement;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getNameFamily() {
        return nameFamily;
    }

    public void setNameFamily(String nameFamily) {
        this.nameFamily = nameFamily;
    }

    public String getNameContract() {
        return nameContract;
    }

    public void setNameContract(String nameContract) {
        this.nameContract = nameContract;
    }
}
