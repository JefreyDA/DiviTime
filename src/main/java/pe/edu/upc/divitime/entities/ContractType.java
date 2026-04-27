package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "contract")
public class ContractType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idContract;
    @Column(name = "NameContract")
    private String nameContract;

    public ContractType(int idContract, String nameContract) {
        this.idContract = idContract;
        this.nameContract = nameContract;
    }

    public ContractType() {
    }

    public int getIdContract() {
        return idContract;
    }

    public void setIdContract(int idContract) {
        this.idContract = idContract;
    }

    public String getNameContract() {
        return nameContract;
    }

    public void setNameContract(String nameContract) {
        this.nameContract = nameContract;
    }
}
