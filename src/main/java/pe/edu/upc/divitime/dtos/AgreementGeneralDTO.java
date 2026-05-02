package pe.edu.upc.divitime.dtos;

import java.time.LocalDateTime;

public class AgreementGeneralDTO {
    private int idAgreement;
    private String titleAgreement;
    private String descriptionAgreement;
    private LocalDateTime creationDate;

    private int idFamily;
    private int idContract;

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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getIdFamily() {
        return idFamily;
    }

    public void setIdFamily(int idFamily) {
        this.idFamily = idFamily;
    }

    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContract) {
        this.idContract = idContract;
    }
}
