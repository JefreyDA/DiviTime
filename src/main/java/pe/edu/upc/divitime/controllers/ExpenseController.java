package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.*;
import pe.edu.upc.divitime.entities.Expense;
import pe.edu.upc.divitime.entities.ExpenseType;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.repositories.IUserRepository;
import pe.edu.upc.divitime.servicesinterfaces.IExpenseService;
import pe.edu.upc.divitime.servicesinterfaces.IExpenseTypeService;
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

    @PostMapping("/register-expense")
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<?> registerExpense(@RequestBody ExpenseGeneralDTO dto) {
        ModelMapper m = new ModelMapper();
        Expense c = m.map(dto, Expense.class);

        if(dto.getAmountExpense() < 0){return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El monto del gasto no puede ser negativo");}

        Optional<User> user = uS.listId(dto.getIdUser());
        if(user.isEmpty()) {return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado o no existe\n Solicitud de registro rechazado");}
        if(user.get().getFamily() == null || user.get().getFamily().getIdFamily() == 0){ return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario no pertenece a ninguna familia\n Solicitud de registro rechazado");}

        Optional<ExpenseType> exTy = etS.listId(dto.getIdExpenseType());
        if(exTy.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de gasto no encontrado o no existe\n Solicitud de registro rechazado");}

        c.setDateExpense(LocalDate.now());
        c.setStatusExpense(true);
        c.setUser(user.get());
        c.setExpenseType(exTy.get());

        Expense expense = eS.insert(c);
        ExpenseGeneralDTO responseDTO = m.map(expense, ExpenseGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/update-expense")
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<String> updateExpense(@RequestBody ExpenseGeneralDTO dto) {

        Optional<Expense> exists = eS.listId(dto.getIdExpense());
        if (exists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Gasto no encontrado");
        }

        if(dto.getAmountExpense() < 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El monto del gasto no puede ser negativo");
        }

        Optional<ExpenseType> exTy = etS.listId(dto.getIdExpenseType());
        if(exTy.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de gasto no encontrado o no existe\n Solicitud de registro rechazado");}

        Expense e = exists.get();
        e.setAmountExpense(dto.getAmountExpense());
        e.setDescriptionExpense(dto.getDescriptionExpense());
        e.setExpenseType(exTy.get());

        eS.update(e);
        return ResponseEntity.ok("Gasto actualizado");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
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
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<List<ExpenseGeneralDTO>> listExpenses() {
        ModelMapper m = new ModelMapper();
        List<ExpenseGeneralDTO> listExpenses = eS.list().stream()
                .map(y -> m.map(y, ExpenseGeneralDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listExpenses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Optional<Expense> agreementType = eS.listId(id);
        if (agreementType.isPresent()) {
            ExpenseGeneralDTO dto = m.map(agreementType.get(), ExpenseGeneralDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Gasto no encontrado");
        }
    }

    @GetMapping("/list-deleted-expenses")
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<List<ExpenseDTO>> listDeletedExpenses() {
        ModelMapper m = new ModelMapper();
        List<ExpenseDTO> listDeletedExpenses = eS.listDeletedExpenses().stream()
                .map(y -> m.map(y, ExpenseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listDeletedExpenses);
    }

    @GetMapping("/list-active-expenses")
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<List<ExpenseDTO>> listActiveExpenses() {
        ModelMapper m = new ModelMapper();
        List<ExpenseDTO> listActiveExpenses = eS.listActiveExpenses().stream()
                .map(y -> m.map(y, ExpenseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listActiveExpenses);
    }

    @GetMapping("/list-expenses-percentage-by-type/{idUser}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<?> listExpensesPercentageByType(@PathVariable int idUser){
        List<Object[]> listExpensesPercentageByType = eS.expensesAmountAndPercentageByType(idUser);

        if(listExpensesPercentageByType.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay gastos registrados");}

        List<ExpenseQueryQuantityByTypeDTO> response = new ArrayList<>();
        for(Object[] fila:listExpensesPercentageByType){
            ExpenseQueryQuantityByTypeDTO dto = new ExpenseQueryQuantityByTypeDTO();
            dto.setNameExpenseType((String) fila[0]);
            dto.setQuantity((Double) fila[1]);
            dto.setPercentage((BigDecimal) fila[2]);
            response.add(dto);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/list-expensed-by-user-on-year-month-family")
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<?> listExpensedQuantityByUYMF(@RequestParam int idUser, @RequestParam int mes, @RequestParam int anio){
        List<Object[]> listExpensesByFamMembMY = eS.totalExpensesByFamilyMembersOnMonthAndYear(idUser, mes, anio);

        if(listExpensesByFamMembMY.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay gastos registrados");}

        List<ExpenseQueryQuantityExpensedByUserOnAYearMonthAndFamiliy> response = new ArrayList<>();
        for(Object[] fila:listExpensesByFamMembMY){
            ExpenseQueryQuantityExpensedByUserOnAYearMonthAndFamiliy dto = new ExpenseQueryQuantityExpensedByUserOnAYearMonthAndFamiliy();
            dto.setIdUser((Integer) fila[0]);
            dto.setNameUser((String) fila[1]);
            dto.setTotalExpensed((double) fila[2]);
            response.add(dto);
        }

        return ResponseEntity.ok(response);
    }



    @GetMapping("/comparacion")
    public List<UserExpenseDTO> compararGastos(
            @RequestParam int idFamily,
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {

        List<Object[]> lista = eS.compararGastos(
                idFamily,
                fechaInicio,
                fechaFin);

        List<UserExpenseDTO> respuesta = new ArrayList<>();

        for (Object[] fila : lista) {

            UserExpenseDTO dto = new UserExpenseDTO();

            dto.setUsuario((String) fila[0]);
            dto.setTotalGastado(((Number) fila[1]).doubleValue());

            respuesta.add(dto);
        }

        return respuesta;
    }

}
