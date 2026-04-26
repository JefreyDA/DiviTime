package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.ContractDTO;

import pe.edu.upc.divitime.dtos.ContractGeneralDTO;
import pe.edu.upc.divitime.entities.ContractType;
import pe.edu.upc.divitime.servicesinterfaces.IContractService;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contract")
public class ContractController {
    @Autowired
    private IContractService cS;

    @GetMapping("/listContracts")
    public ResponseEntity<List<ContractDTO>> listar() {
        ModelMapper m = new ModelMapper();
        List<ContractDTO> listaAcuerdos = cS.list().stream()
                .map(y -> m.map(y, ContractDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaAcuerdos
        );

    }

    @PostMapping("/insertar")
    public ResponseEntity<ContractGeneralDTO> insertar(@RequestBody ContractGeneralDTO dto) {
        ModelMapper m = new ModelMapper();
        ContractType c = m.map(dto, ContractType.class);
        ContractType contract = cS.insert(c);
        ContractGeneralDTO responseDTO = m.map(contract, ContractGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> BuscarId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Optional<ContractType> contract = cS.listId(id);
        if (contract.isPresent()) {
            ContractGeneralDTO dto = m.map(contract.get(), ContractGeneralDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tipo de acuerdo no encontrado");
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody ContractGeneralDTO dto) {
        Optional<ContractType> existe = cS.listId((dto.getIdContract()));
        if (existe.isEmpty()) {
            return ResponseEntity.status((HttpStatus.NOT_FOUND))
                    .body("Tipo de acuerdo no encontrado");
        }
        if (dto.getNameContract() == null) {
            return ResponseEntity.badRequest()
                    .body(("Ingresar el tipo de acuerdo"));
        }

        ContractType c = existe.get();
        c.setNameContract(dto.getNameContract());

        cS.update(c);
        return ResponseEntity.ok("Tipo de acuerdo actualizado");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        Optional<ContractType> c = cS.listId(id);
        if (c.isPresent()) {
            cS.delete(id);
            return ResponseEntity.ok("Tipo de acuerdo eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tipo de acuerdo no encontrado");
        }
    }
}