package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "agreement")
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAgreement;

    @Column(name = "title_agreement", length = 20, nullable = false)
    private String titleAgreement;

    @Column(name = "description_agreement", length = 150, nullable = false)
    private String descriptionAgreement;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "id_family", nullable = false)
    private Family family;

    @ManyToOne
    @JoinColumn(name = "id_contract", nullable = false)
    private ContractType contractType;

    public Agreement() {
    }

    public Agreement(int idAgreement, String titleAgreement, String descriptionAgreement, LocalDateTime creationDate, Family family, ContractType contractType) {
        this.idAgreement = idAgreement;
        this.titleAgreement = titleAgreement;
        this.descriptionAgreement = descriptionAgreement;
        this.creationDate = creationDate;
        this.family = family;
        this.contractType = contractType;
    }

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

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }
}
