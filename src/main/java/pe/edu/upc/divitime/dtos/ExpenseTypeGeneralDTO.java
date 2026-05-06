package pe.edu.upc.divitime.dtos;

public class ExpenseTypeGeneralDTO {
    private int idExpenseType;
    private String nameExpenseType;
    private String descriptionExpenseType;

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
