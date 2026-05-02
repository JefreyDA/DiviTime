package pe.edu.upc.divitime.dtos;

import jakarta.persistence.Column;

public class AgreementTypeDTO {
    private String nameAgreementType;

    public String getNameAgreementType() {
        return nameAgreementType;
    }

    public void setNameAgreementType(String nameAgreementType) {
        this.nameAgreementType = nameAgreementType;
    }
}
