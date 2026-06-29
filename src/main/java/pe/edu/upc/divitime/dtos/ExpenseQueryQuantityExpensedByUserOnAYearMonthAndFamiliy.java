package pe.edu.upc.divitime.dtos;

import java.math.BigDecimal;

public class ExpenseQueryQuantityExpensedByUserOnAYearMonthAndFamiliy {
    private int idUser;
    private String nameUser;
    private double totalExpensed;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public double getTotalExpensed() {
        return totalExpensed;
    }

    public void setTotalExpensed(double totalExpensed) {
        this.totalExpensed = totalExpensed;
    }
}
