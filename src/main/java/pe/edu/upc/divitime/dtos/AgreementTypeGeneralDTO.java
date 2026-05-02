package pe.edu.upc.divitime.dtos;

import jakarta.persistence.Column;

public class AgreementTypeGeneralDTO {
    private int idAgreementType;
    private String nameAgreementType;

    public int getIdAgreementType() {
        return idAgreementType;
    }

    public void setIdAgreementType(int idAgreementType) {
        this.idAgreementType = idAgreementType;
    }

    public String getNameAgreementType() {
        return nameAgreementType;
    }

    public void setNameAgreementType(String nameAgreementType) {
        this.nameAgreementType = nameAgreementType;
    }
}
