package pe.edu.upc.divitime.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "expense")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idExpense;

    @Column(name = "amountExpense", nullable = false)
    private double amountExpense;

    @Column(name = "descriptionExpense", length = 100, nullable = false)
    private String descriptionExpense;

    @Column(name = "urlImageVoucherExpense", length = 100)
    private String urlImageVoucherExpense;

    @Column(name = "dateExpense", nullable = false)
    private LocalDate dateExpense;

    @Column(name = "statusExpense", nullable = false)
    private Boolean statusExpense;

    @ManyToOne
    @JoinColumn(name = "idExpenseType", nullable = false)
    private ExpenseType expenseType;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "idFamily")
    private Family family;

    public Expense(int idExpense, double amountExpense, String descriptionExpense, String urlImageVoucherExpense, LocalDate dateExpense, Boolean statusExpense, ExpenseType expenseType, User user, Family family) {
        this.idExpense = idExpense;
        this.amountExpense = amountExpense;
        this.descriptionExpense = descriptionExpense;
        this.urlImageVoucherExpense = urlImageVoucherExpense;
        this.dateExpense = dateExpense;
        this.statusExpense = statusExpense;
        this.expenseType = expenseType;
        this.user = user;
        this.family = family;
    }

    public Expense() {
    }

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

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }
}
