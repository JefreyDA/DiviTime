package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.ExpenseDTO;
import pe.edu.upc.divitime.dtos.ExpenseGeneralDTO;
import pe.edu.upc.divitime.entities.Expense;
import pe.edu.upc.divitime.entities.ExpenseType;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.servicesinterfaces.IExpenseService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    @Autowired
    private IExpenseService eS;

    @PostMapping("/registrar-gasto")
    public ResponseEntity<ExpenseGeneralDTO> registrarGasto(@RequestBody ExpenseGeneralDTO dto) {
        ModelMapper m = new ModelMapper();
        Expense c = m.map(dto, Expense.class);
        c.setDateExpense(LocalDate.now());
        c.setStatusExpense(true);

        Expense expense = eS.insert(c);
        ExpenseGeneralDTO responseDTO = m.map(expense, ExpenseGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/actualizar-gasto")
    public ResponseEntity<String> actualizarGasto(@RequestBody ExpenseGeneralDTO dto) {

        Optional<Expense> existente = eS.listId(dto.getIdExpense());
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Gasto no encontrado");
        }

        Expense e = existente.get();
        e.setAmountExpense(dto.getAmountExpense());
        e.setDescriptionExpense(dto.getDescriptionExpense());
        e.setUrlImageVoucherExpense(dto.getUrlImageVoucherExpense());

        ExpenseType tipo = new ExpenseType();
        tipo.setIdExpenseType(dto.getIdExpenseType());
        e.setExpenseType(tipo);

        eS.update(e);
        return ResponseEntity.ok("Gasto actualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarGasto(@PathVariable int id) {

        Optional<Expense> existente = eS.listId(id);

        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Gasto no encontrado");
        }

        Expense e = existente.get();
        e.setStatusExpense(false);

        eS.deleteLogical(e);
        return ResponseEntity.ok("Gasto eliminado correctamente");
    }

    @GetMapping("/listar-gastos")
    public ResponseEntity<List<ExpenseDTO>> listarGastos() {
        ModelMapper m = new ModelMapper();
        List<ExpenseDTO> listarGastos = eS.list().stream()
                .map(y -> m.map(y, ExpenseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listarGastos);
    }

    @GetMapping("/listar-gastos-eliminados")
    public ResponseEntity<List<ExpenseDTO>> listarGastosEliminados() {
        ModelMapper m = new ModelMapper();
        List<ExpenseDTO> listarGastosEliminados = eS.listarGastosEliminados().stream()
                .map(y -> m.map(y, ExpenseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listarGastosEliminados);
    }

    @GetMapping("/listar-gastos-no-eliminados")
    public ResponseEntity<List<ExpenseDTO>> listarGastosNoEliminados() {
        ModelMapper m = new ModelMapper();
        List<ExpenseDTO> listarGastosNoEliminados = eS.listarGastosActivos().stream()
                .map(y -> m.map(y, ExpenseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listarGastosNoEliminados);
    }
}
