package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ExpenseType")
public class ExpenseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idExpenseType;

    @Column(name = "nameExpenseType", length = 50, nullable = false)
    private String nameExpenseType;

    @Column(name = "descriptionExpenseType", length = 100, nullable = false)
    private String descriptionExpenseType;

    public ExpenseType(int idExpenseType, String nameExpenseType, String descriptionExpenseType) {
        this.idExpenseType = idExpenseType;
        this.nameExpenseType = nameExpenseType;
        this.descriptionExpenseType = descriptionExpenseType;
    }

    public ExpenseType() {
    }

    public int getIdExpenseType() {
        return idExpenseType;
    }

    public void setIdExpenseType(int idExpenseType) {
        this.idExpenseType = idExpenseType;
    }

    public String getNameExpenseType() {
        return nameExpenseType;
    }

    public void setNameExpenseType(String nameExpenseType) {
        this.nameExpenseType = nameExpenseType;
    }

    public String getDescriptionExpenseType() {
        return descriptionExpenseType;
    }

    public void setDescriptionExpenseType(String descriptionExpenseType) {
        this.descriptionExpenseType = descriptionExpenseType;
    }
}
