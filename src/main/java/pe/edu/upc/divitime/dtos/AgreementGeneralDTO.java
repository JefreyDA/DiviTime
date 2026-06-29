package pe.edu.upc.divitime.dtos;

import java.time.LocalDate;

public class AgreementGeneralDTO {
    private int idAgreement;
    private String titleAgreement;
    private String descriptionAgreement;
    private LocalDate creationDate;

    private int idFamily;
    private int idAgreementType;

    public int getIdAgreement() {
        return idAgreement;
    }

    public void setIdAgreement(int idAgreement) {
        this.idAgreement = idAgreement;
    }

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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public int getIdFamily() {
        return idFamily;
    }

    public void setIdFamily(int idFamily) {
        this.idFamily = idFamily;
    }

    public int getIdAgreementType() {
        return idAgreementType;
    }

    public void setIdAgreementType(int idAgreementType) {
        this.idAgreementType = idAgreementType;
    }
}
