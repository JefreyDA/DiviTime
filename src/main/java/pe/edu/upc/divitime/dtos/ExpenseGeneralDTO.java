package pe.edu.upc.divitime.dtos;

public class ExpenseGeneralDTO {
    private int idExpense;
    private double amountExpense;
    private String descriptionExpense;
    private String urlImageVoucherExpense;
    private int idExpenseType;
    private int idUser;

    public int getIdExpense() {
        return idExpense;
    }

    public void setIdExpense(int idExpense) {
        this.idExpense = idExpense;
    }

    public double getAmountExpense() {
        return amountExpense;
    }

    public void setAmountExpense(double amountExpense) {
        this.amountExpense = amountExpense;
    }

    public String getDescriptionExpense() {
        return descriptionExpense;
    }

    public void setDescriptionExpense(String descriptionExpense) {
        this.descriptionExpense = descriptionExpense;
    }

    public String getUrlImageVoucherExpense() {
        return urlImageVoucherExpense;
    }

    public void setUrlImageVoucherExpense(String urlImageVoucherExpense) {
        this.urlImageVoucherExpense = urlImageVoucherExpense;
    }

    public int getIdExpenseType() {
        return idExpenseType;
    }

    public void setIdExpenseType(int idExpenseType) {
        this.idExpenseType = idExpenseType;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
