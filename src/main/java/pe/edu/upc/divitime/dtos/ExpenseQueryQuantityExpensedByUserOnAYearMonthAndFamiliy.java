package pe.edu.upc.divitime.dtos;

import java.math.BigDecimal;

public class ExpenseQueryQuantityExpensedByUserOnAYearMonthAndFamiliy {
    private String nameUser;
    private BigDecimal month;
    private double totalExpensed;

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public BigDecimal getMonth() {
        return month;
    }

    public void setMonth(BigDecimal month) {
        this.month = month;
    }

    public double getTotalExpensed() {
        return totalExpensed;
    }

    public void setTotalExpensed(double  totalExpensed) {
        this.totalExpensed = totalExpensed;
    }
}
