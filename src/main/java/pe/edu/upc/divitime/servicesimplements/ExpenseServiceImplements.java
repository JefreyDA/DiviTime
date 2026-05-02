package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.Expense;
import pe.edu.upc.divitime.repositories.IExpenseRepository;
import pe.edu.upc.divitime.servicesinterfaces.IExpenseService;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImplements implements IExpenseService {

    @Autowired
    private IExpenseRepository iExpRep;

    @Override
    public List<Expense> list() {
        return iExpRep.findAll();
    }

    @Override
    public Expense insert(Expense expense) {
        return iExpRep.save(expense);
    }

    @Override
    public void update(Expense expense) {
        iExpRep.save(expense);
    }

    @Override
    public void deleteLogical(Expense expense) {
        iExpRep.save(expense);
    }

    @Override
    public Optional<Expense> listId(int id) {
        return iExpRep.findById(id);
    }

    @Override
    public List<Expense> listActiveExpenses() {
        return iExpRep.findByStatusExpenseTrue();
    }

    @Override
    public List<Expense> listDeletedExpenses() {
        return iExpRep.findByStatusExpenseFalse();
    }

    @Override
    public List<Object[]> expensesPercentageByType() {
        return iExpRep.expensesPercentageByType();
    }

    @Override
    public List<Object[]> expensesAmountByType() {
        return iExpRep.expensesAmountByType();
    }

    @Override
    public List<Object[]> amountExpensedByUserOnAYearMonthAndFamiliy(int year, int familyId) {
        return iExpRep.amountExpensedByUserOnAYearMonthAndFamiliy(year, familyId);
    }
}
