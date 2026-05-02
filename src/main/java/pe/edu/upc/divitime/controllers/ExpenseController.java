package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.ExpenseDTO;
import pe.edu.upc.divitime.dtos.ExpenseGeneralDTO;
import pe.edu.upc.divitime.dtos.ExpenseQueryQuantityByTypeDTO;
import pe.edu.upc.divitime.dtos.ExpenseQueryQuantityExpensedByUserOnAYearMonthAndFamiliy;
import pe.edu.upc.divitime.entities.Expense;
import pe.edu.upc.divitime.entities.ExpenseType;
import pe.edu.upc.divitime.entities.Family;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.servicesinterfaces.IExpenseService;
import pe.edu.upc.divitime.servicesinterfaces.IExpenseTypeService;
import pe.edu.upc.divitime.servicesinterfaces.IFamilyService;
import pe.edu.upc.divitime.servicesinterfaces.IUserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private IExpenseService eS;

    @Autowired
    private IUserService uS;

    @Autowired
    private IExpenseTypeService etS;

    @Autowired
    private IFamilyService fS;

    @PostMapping("/register-expense")
    public ResponseEntity<?> registerExpense(@RequestBody ExpenseGeneralDTO dto) {
        ModelMapper m = new ModelMapper();
        Expense c = m.map(dto, Expense.class);

        c.setDateExpense(LocalDate.now());
        c.setStatusExpense(true);

        Optional<User> user = uS.listId(dto.getIdUser());
        if(user.isEmpty()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado o no existe\n Solicitud de registro rechazado");}

        Optional<ExpenseType> exTy = etS.listId(dto.getIdExpenseType());
        if(exTy.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de gasto no encontrado o no existe\n Solicitud de registro rechazado");}

        Optional<Family> fam = fS.listId(dto.getIdFamily());
        if(fam.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Familia no encontrada o no existe\n Solicitud de registro rechazado");}

        Expense expense = eS.insert(c);
        ExpenseGeneralDTO responseDTO = m.map(expense, ExpenseGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/update-expense")
    public ResponseEntity<String> updateExpense(@RequestBody ExpenseGeneralDTO dto) {

        Optional<Expense> exists = eS.listId(dto.getIdExpense());
        if (exists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Gasto no encontrado");
        }

        Optional<ExpenseType> exTy = etS.listId(dto.getIdExpenseType());
        if(exTy.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de gasto no encontrado o no existe\n Solicitud de registro rechazado");}

        Expense e = exists.get();
        e.setAmountExpense(dto.getAmountExpense());
        e.setDescriptionExpense(dto.getDescriptionExpense());
        e.setUrlImageVoucherExpense(dto.getUrlImageVoucherExpense());

        ExpenseType type = new ExpenseType();
        type.setIdExpenseType(dto.getIdExpenseType());
        e.setExpenseType(type);

        eS.update(e);
        return ResponseEntity.ok("Gasto actualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable int id) {

        Optional<Expense> exists = eS.listId(id);

        if (exists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Gasto no encontrado");
        }

        Expense e = exists.get();
        e.setStatusExpense(false);

        eS.deleteLogical(e);
        return ResponseEntity.ok("Gasto eliminado correctamente");
    }

    @GetMapping("/list-expenses")
    public ResponseEntity<List<ExpenseDTO>> listExpenses() {
        ModelMapper m = new ModelMapper();
        List<ExpenseDTO> listExpenses = eS.list().stream()
                .map(y -> m.map(y, ExpenseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listExpenses);
    }

    @GetMapping("/list-deleted-expenses")
    public ResponseEntity<List<ExpenseDTO>> listDeletedExpenses() {
        ModelMapper m = new ModelMapper();
        List<ExpenseDTO> listDeletedExpenses = eS.listDeletedExpenses().stream()
                .map(y -> m.map(y, ExpenseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listDeletedExpenses);
    }

    @GetMapping("/list-active-expenses")
    public ResponseEntity<List<ExpenseDTO>> listActiveExpenses() {
        ModelMapper m = new ModelMapper();
        List<ExpenseDTO> listActiveExpenses = eS.listActiveExpenses().stream()
                .map(y -> m.map(y, ExpenseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listActiveExpenses);
    }

    @GetMapping("/list-expenses-percentage-by-type")
    public ResponseEntity<?> listExpensesPercentageByType(){
        List<Object[]> listExpensesPercentageByType = eS.expensesPercentageByType();

        if(listExpensesPercentageByType.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay gastos registrados");}

        List<ExpenseQueryQuantityByTypeDTO> response = new ArrayList<>();
        for(Object[] fila:listExpensesPercentageByType){
            ExpenseQueryQuantityByTypeDTO dto = new ExpenseQueryQuantityByTypeDTO();
            dto.setNameExpenseType((String) fila[0]);
            dto.setQuantity((BigDecimal) fila[1]);
            response.add(dto);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-expenses-amount-by-type")
    public ResponseEntity<?> listExpensesAmountByType(){
        List<Object[]> listExpensesAmountByType = eS.expensesAmountByType();

        if(listExpensesAmountByType.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay gastos registrados");}

        List<ExpenseQueryQuantityByTypeDTO> response = new ArrayList<>();
        for(Object[] fila:listExpensesAmountByType){
            ExpenseQueryQuantityByTypeDTO dto = new ExpenseQueryQuantityByTypeDTO();
            dto.setNameExpenseType((String) fila[0]);
            dto.setQuantity((BigDecimal) fila[1]);
            response.add(dto);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-expensed-by-user-on-year-month-family")
    public ResponseEntity<?> listExpensedQuantityByUYMF(@RequestParam int year, @RequestParam int familyId){
        List<Object[]> listExpensedQuantityByUYMF = eS.amountExpensedByUserOnAYearMonthAndFamiliy(year, familyId);

        if(listExpensedQuantityByUYMF.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay gastos registrados");}

        List<ExpenseQueryQuantityExpensedByUserOnAYearMonthAndFamiliy> response = new ArrayList<>();
        for(Object[] fila:listExpensedQuantityByUYMF){
            ExpenseQueryQuantityExpensedByUserOnAYearMonthAndFamiliy dto = new ExpenseQueryQuantityExpensedByUserOnAYearMonthAndFamiliy();
            dto.setNameUser((String) fila[0]);
            dto.setMonth((BigDecimal) fila[1]);
            dto.setTotalExpensed((double) fila[2]);
            response.add(dto);
        }

        return ResponseEntity.ok(response);
    }
}
