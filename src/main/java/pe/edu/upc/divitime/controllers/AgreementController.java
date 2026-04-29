package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.QueryAgreementByFamilyDTO;
import pe.edu.upc.divitime.dtos.AgreementDTO;
import pe.edu.upc.divitime.dtos.AgreementGeneralDTO;
import pe.edu.upc.divitime.entities.Agreement;
import pe.edu.upc.divitime.entities.ContractType;
import pe.edu.upc.divitime.entities.Family;
import pe.edu.upc.divitime.servicesinterfaces.IAgreementService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/agreement")
public class AgreementController {

    @Autowired
    private IAgreementService aS;

    @GetMapping("/listAgreements")
    public ResponseEntity<List<AgreementDTO>> listar() {
        ModelMapper m = new ModelMapper();

        List<AgreementDTO> lista = aS.list().stream()
                .map(y -> m.map(y, AgreementDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }

    @PostMapping("/insertar")
    public ResponseEntity<AgreementGeneralDTO> insertar(@RequestBody AgreementGeneralDTO dto) {
        ModelMapper m = new ModelMapper();
        Agreement a = m.map(dto, Agreement.class);

        Family f = new Family();
        f.setIdFamily(dto.getIdFamily());
        a.setFamily(f);

        ContractType c = new ContractType();
        c.setIdContract(dto.getIdContract());
        a.setContractType(c);

        Agreement agreement = aS.insert(a);

        AgreementGeneralDTO responseDTO = m.map(agreement, AgreementGeneralDTO.class);
        responseDTO.setIdFamily(agreement.getFamily().getIdFamily());
        responseDTO.setIdContract(agreement.getContractType().getIdContract());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> BuscarId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();

        Optional<Agreement> agreement = aS.listId(id);

        if (agreement.isPresent()) {
            AgreementGeneralDTO dto = m.map(agreement.get(), AgreementGeneralDTO.class);
            dto.setIdFamily(agreement.get().getFamily().getIdFamily());
            dto.setIdContract(agreement.get().getContractType().getIdContract());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Acuerdo no encontrado");
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody AgreementGeneralDTO dto) {

        Optional<Agreement> existe = aS.listId(dto.getIdAgreement());

        if (existe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Acuerdo no encontrado");
        }

        if (dto.getTitleAgreement() == null || dto.getDescriptionAgreement() == null) {
            return ResponseEntity.badRequest()
                    .body("Campos obligatorios vacíos");
        }

        Agreement a = existe.get();

        a.setTitleAgreement(dto.getTitleAgreement());
        a.setDescriptionAgreement(dto.getDescriptionAgreement());
        a.setCreationDate(dto.getCreationDate());

        // 🔥 Relaciones
        Family f = new Family();
        f.setIdFamily(dto.getIdFamily());
        a.setFamily(f);

        ContractType c = new ContractType();
        c.setIdContract(dto.getIdContract());
        a.setContractType(c);

        aS.update(a);

        return ResponseEntity.ok("Acuerdo actualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        Optional<Agreement> a = aS.listId(id);

        if (a.isPresent()) {
            aS.delete(id);
            return ResponseEntity.ok("Acuerdo eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Acuerdo no encontrado");
        }
    }
    @GetMapping("/acuerdos-por-familia/{idFamily}")
    public ResponseEntity<?> obtenerAcuerdosPorFamilia(@PathVariable int idFamily) {
        List<QueryAgreementByFamilyDTO> listaBusqueda = aS.listAgreementsByFamilyJPQL(idFamily).stream()
                .map(y -> {
                    QueryAgreementByFamilyDTO dto = new QueryAgreementByFamilyDTO();
                    dto.setTitleAgreement(y.getTitleAgreement());
                    dto.setDescriptionAgreement(y.getDescriptionAgreement());
                    dto.setCreationDate(y.getCreationDate());
                    dto.setNameFamily(y.getFamily().getNameFamily());
                    dto.setNameContract(y.getContractType().getNameContract());
                    return dto;
                })
                .collect(Collectors.toList());

        if (listaBusqueda.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existen acuerdos para esta familia");
        }

        return ResponseEntity.ok(listaBusqueda);
    }
}