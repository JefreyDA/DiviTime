package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.ExpenseTypeDTO;
import pe.edu.upc.divitime.dtos.ExpenseTypeGeneralDTO;
import pe.edu.upc.divitime.entities.Expense;
import pe.edu.upc.divitime.entities.ExpenseType;
import pe.edu.upc.divitime.servicesinterfaces.IExpenseTypeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expenseType")
public class ExpenseTypeController {

    @Autowired
    private IExpenseTypeService etS;

    @PostMapping("/register-expense-type")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> registerExpenseType(@RequestBody ExpenseTypeGeneralDTO dto) {
        ModelMapper m = new ModelMapper();
        ExpenseType c = m.map(dto, ExpenseType.class);

        ExpenseType temp = etS.SearchByNameExpenseType(dto.getNameExpenseType());
        if(dto.getNameExpenseType().equals(temp.getNameExpenseType())) {return ResponseEntity.status(HttpStatus.CONFLICT).body("El tipo de gasto ya existe\n Solicitud de registro rechazado");}

        ExpenseType expenset = etS.insert(c);
        ExpenseTypeGeneralDTO responseDTO = m.map(expenset, ExpenseTypeGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/update-expense-type")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateExpenseType(@RequestBody ExpenseTypeGeneralDTO dto) {
        Optional<ExpenseType> exists = etS.listId(dto.getIdExpenseType());
        if (exists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tipo de gasto no encontrado");
        }

        ExpenseType expenset = exists.get();
        expenset.setNameExpenseType(dto.getNameExpenseType());
        expenset.setDescriptionExpenseType(dto.getDescriptionExpenseType());

        etS.update(expenset);
        return ResponseEntity.ok("Tipo de gasto actualizado");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteExpenseType(@PathVariable int id) {

        Optional<ExpenseType> exists = etS.listId(id);
        if (exists.isPresent()) {
            etS.delete(id);
            return ResponseEntity.ok("Tipo de gasto eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tipo de gasto no encontrado");
        }
    }

    @GetMapping("/list-expense-types")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<ExpenseTypeDTO>> listExpenseTypes() {
        ModelMapper m = new ModelMapper();
        List<ExpenseTypeDTO> listExpenseTypes = etS.list().stream()
                .map(y -> m.map(y, ExpenseTypeDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listExpenseTypes);
    }
}
