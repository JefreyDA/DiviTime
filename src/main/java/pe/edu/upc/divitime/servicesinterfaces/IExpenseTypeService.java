package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.ExpenseType;

import java.util.List;
import java.util.Optional;

public interface IExpenseTypeService {
    public List<ExpenseType> list();
    public Optional<ExpenseType> listId(int id);
    public ExpenseType insert(ExpenseType eT);
    public void update(ExpenseType expenseType);
    public void delete(int id);

    public ExpenseType SearchByNameExpenseType(String name);
}
