package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.ExpenseType;
import pe.edu.upc.divitime.repositories.IExpenseTypeRepository;
import pe.edu.upc.divitime.servicesinterfaces.IExpenseTypeService;

import java.util.List;

@Service
public class ExpenseTypeServiceImplements implements IExpenseTypeService {
    @Autowired
    private IExpenseTypeRepository iExpTyRep;

    @Override
    public List<ExpenseType> list() {
        return iExpTyRep.findAll();
    }
}
