package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.divitime.dtos.ExpenseTypeDTO;
import pe.edu.upc.divitime.servicesinterfaces.IExpenseTypeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expenseType")
public class ExpenseTypeController {

    @Autowired
    private IExpenseTypeService etS;

    @GetMapping("/listar-tipo-gasto")
    public ResponseEntity<List<ExpenseTypeDTO>> listar() {
        ModelMapper m = new ModelMapper();
        List<ExpenseTypeDTO> listaTipoGasto = etS.list().stream()
                .map(y -> m.map(y, ExpenseTypeDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaTipoGasto);
    }
}
