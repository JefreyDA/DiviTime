package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.Expense;

import java.util.List;
import java.util.Optional;

public interface IExpenseService {
    public List<Expense> list();
    public Expense insert(Expense expense);
    public void update(Expense expense);
    public void deleteLogical(Expense expense);
    public Optional<Expense> listId(int id);

    List<Expense> listarGastosActivos();
    List<Expense> listarGastosEliminados();
}
