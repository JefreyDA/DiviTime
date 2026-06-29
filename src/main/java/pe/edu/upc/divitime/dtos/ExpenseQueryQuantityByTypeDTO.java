package pe.edu.upc.divitime.dtos;

import java.math.BigDecimal;

public class ExpenseQueryQuantityByTypeDTO {
    private String nameExpenseType;
    private Double quantity;
    private BigDecimal percentage;

    public String getNameExpenseType() {
        return nameExpenseType;
    }

    public void setNameExpenseType(String nameExpenseType) {
        this.nameExpenseType = nameExpenseType;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }
}
