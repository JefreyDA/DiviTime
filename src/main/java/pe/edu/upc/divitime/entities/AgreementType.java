package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "AgreementType")
public class AgreementType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAgreementType;
    @Column(name = "nameAgreementType",length = 50,nullable = false)
    private String nameAgreementType;

    public AgreementType(int idAgreementType, String nameAgreementType) {
        this.idAgreementType = idAgreementType;
        this.nameAgreementType = nameAgreementType;
    }

    public AgreementType() {
    }

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
