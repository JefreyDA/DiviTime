package pe.edu.upc.divitime.dtos;

import pe.edu.upc.divitime.entities.ExpenseType;
import pe.edu.upc.divitime.entities.User;

import java.time.LocalDate;

public class ExpenseDTO {
    private double amountExpense;
    private String descriptionExpense;
    private String urlImageVoucherExpense;
    private LocalDate dateExpense;
    private Boolean statusExpense;
    private ExpenseType expenseType;
    private User user;

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

    public LocalDate getDateExpense() {
        return dateExpense;
    }

    public void setDateExpense(LocalDate dateExpense) {
        this.dateExpense = dateExpense;
    }

    public Boolean getStatusExpense() {
        return statusExpense;
    }

    public void setStatusExpense(Boolean statusExpense) {
        this.statusExpense = statusExpense;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
