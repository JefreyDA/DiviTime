package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.ExpenseType;
import pe.edu.upc.divitime.repositories.IExpenseTypeRepository;
import pe.edu.upc.divitime.servicesinterfaces.IExpenseTypeService;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseTypeServiceImplements implements IExpenseTypeService {
    @Autowired
    private IExpenseTypeRepository iExpTyRep;

    @Override
    public List<ExpenseType> list() {
        return iExpTyRep.findAll();
    }

    @Override
    public Optional<ExpenseType> listId(int id) {
        return iExpTyRep.findById(id);
    }

    @Override
    public ExpenseType insert(ExpenseType eT) {
        return iExpTyRep.save(eT);
    }

    @Override
    public void update(ExpenseType expenseType) {
        iExpTyRep.save(expenseType);
    }

    @Override
    public void delete(int id) {
        iExpTyRep.deleteById(id);
    }

    @Override
    public ExpenseType SearchByNameExpenseType(String name) {
        return iExpTyRep.findByNameExpenseType(name);
    }
}
