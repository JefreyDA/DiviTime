package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.AgreementTypeDTO;
import pe.edu.upc.divitime.dtos.AgreementTypeGeneralDTO;
import pe.edu.upc.divitime.entities.AgreementType;
import pe.edu.upc.divitime.servicesinterfaces.IAgreementTypeService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/agreementType")
public class AgreementTypeController {
    @Autowired
    private IAgreementTypeService aTS;

    @GetMapping("/list-agreementType")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AgreementTypeDTO>> list() {
        ModelMapper m = new ModelMapper();
        List<AgreementTypeDTO> listAgreementType = aTS.list().stream()
                .map(y -> m.map(y, AgreementTypeDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listAgreementType);

    }

    @PostMapping("/register-agreementType")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> insert(@RequestBody AgreementTypeGeneralDTO dto) {
        if (dto.getNameAgreementType() == null ||
                dto.getNameAgreementType().trim().isEmpty()) {

            return ResponseEntity.badRequest()
                    .body("El nombre del tipo de acuerdo es obligatorio");
        }
        boolean exists =
                aTS.existsByNameAgreementType(
                        dto.getNameAgreementType());

        if (exists) {
            return ResponseEntity.badRequest()
                    .body("Ya existe un tipo de acuerdo con ese nombre");
        }
        ModelMapper m = new ModelMapper();
        AgreementType aT = m.map(dto, AgreementType.class);
        AgreementType agreementType = aTS.insert(aT);
        AgreementTypeGeneralDTO responseDTO = m.map(agreementType, AgreementTypeGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Optional<AgreementType> agreementType = aTS.listId(id);
        if (agreementType.isPresent()) {
            AgreementTypeGeneralDTO dto = m.map(agreementType.get(), AgreementTypeGeneralDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tipo de acuerdo no encontrado");
        }
    }

    @PutMapping("/update-agreementType")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> update(@RequestBody AgreementTypeGeneralDTO dto) {
        Optional<AgreementType> exists = aTS.listId((dto.getIdAgreementType()));
        if (exists.isEmpty()) {
            return ResponseEntity.status((HttpStatus.NOT_FOUND))
                    .body("Tipo de acuerdo no encontrado");
        }
        if (dto.getNameAgreementType() == null) {
            return ResponseEntity.badRequest()
                    .body(("Ingresar el tipo de acuerdo"));
        }
        boolean duplicated =
                aTS.existsByNameAgreementTypeAndIdAgreementTypeNot(
                        dto.getNameAgreementType(),
                        dto.getIdAgreementType());

        if (duplicated) {
            return ResponseEntity.badRequest()
                    .body("Ya existe un tipo de acuerdo con ese nombre");
        }

        AgreementType aT = exists.get();
        aT.setNameAgreementType(dto.getNameAgreementType());

        aTS.update(aT);
        return ResponseEntity.ok("Tipo de acuerdo actualizado");

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable int id) {
        Optional<AgreementType> aT = aTS.listId(id);
        if (aT.isPresent()) {
            aTS.delete(id);
            return ResponseEntity.ok("Tipo de acuerdo eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tipo de acuerdo no encontrado");
        }
    }

}
