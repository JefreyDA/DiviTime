package pe.edu.upc.divitime.dtos;

import java.math.BigDecimal;

public class ExpenseQueryQuantityByTypeDTO {
    private String nameExpenseType;
    private BigDecimal quantity;

    public String getNameExpenseType() {
        return nameExpenseType;
    }

    public void setNameExpenseType(String nameExpenseType) {
        this.nameExpenseType = nameExpenseType;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
