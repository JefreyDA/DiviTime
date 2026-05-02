package pe.edu.upc.divitime.dtos;

import java.time.LocalDateTime;

public class AgreementDTO {
    private String titleAgreement;
    private String descriptionAgreement;
    private LocalDateTime creationDate;

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
}
