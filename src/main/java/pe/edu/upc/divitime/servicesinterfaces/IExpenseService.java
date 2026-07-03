package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.Expense;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IExpenseService {
    public List<Expense> list();
    public Expense insert(Expense expense);
    public void update(Expense expense);
    public void deleteLogical(Expense expense);
    public Optional<Expense> listId(int id);

    List<Expense> searchByUser_IdUser(int idUser);
    List<Expense> listActiveExpenses();
    List<Expense> listDeletedExpenses();

    List<Object[]> expensesAmountAndPercentageByType(int idUser);
    List<Object[]> totalExpensesByFamilyMembersOnMonthAndYear(int idUser, int mes, int anio);

    List<Object[]> compararGastos(
            int idUser,
            LocalDate fechaInicio,
            LocalDate fechaFin);
}
