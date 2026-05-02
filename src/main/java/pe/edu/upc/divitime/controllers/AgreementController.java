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
import pe.edu.upc.divitime.entities.AgreementType;
import pe.edu.upc.divitime.entities.Family;
import pe.edu.upc.divitime.servicesinterfaces.IAgreementService;
import pe.edu.upc.divitime.servicesinterfaces.IFamilyService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/agreement")
public class AgreementController {
    @Autowired
    private IAgreementService aS;
    @Autowired
    private IFamilyService fS;

    @GetMapping("/listAgreements")
    public ResponseEntity<List<AgreementDTO>> list() {
        ModelMapper m = new ModelMapper();

        List<AgreementDTO> list = aS.list().stream()
                .map(y -> m.map(y, AgreementDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @PostMapping("/insert-agreement")
    public ResponseEntity<?> insert(@RequestBody AgreementGeneralDTO dto) {
        ModelMapper m = new ModelMapper();
        Agreement a = m.map(dto, Agreement.class);

        Optional<Family> fam = fS.listId(dto.getIdFamily());
        if(fam.isEmpty()){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Familia no encontrada o no existe\n Solicitud de registro rechazado");}

        Family f = new Family();
        f.setIdFamily(dto.getIdFamily());
        a.setFamily(f);

        AgreementType aT = new AgreementType();
        aT.setIdAgreementType(dto.getIdAgreementType());
        a.setAgreementType(aT);

        Agreement agreement = aS.insert(a);

        AgreementGeneralDTO responseDTO = m.map(agreement, AgreementGeneralDTO.class);
        responseDTO.setIdFamily(agreement.getFamily().getIdFamily());
        responseDTO.setIdAgreementType(agreement.getAgreementType().getIdAgreementType());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> BuscarId(@PathVariable int id) {
        ModelMapper m = new ModelMapper();

        Optional<Agreement> agreement = aS.listId(id);

        if (agreement.isPresent()) {
            AgreementGeneralDTO dto = m.map(agreement.get(), AgreementGeneralDTO.class);
            dto.setIdFamily(agreement.get().getFamily().getIdFamily());
            dto.setIdAgreementType(agreement.get().getAgreementType().getIdAgreementType());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Acuerdo no encontrado");
        }
    }

    @PutMapping("/update-agreement")
    public ResponseEntity<String> update(@RequestBody AgreementGeneralDTO dto) {

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

        Family f = new Family();
        f.setIdFamily(dto.getIdFamily());
        a.setFamily(f);

        AgreementType aT = new AgreementType();
        aT.setIdAgreementType(dto.getIdAgreementType());
        a.setAgreementType(aT);

        aS.update(a);

        return ResponseEntity.ok("Acuerdo actualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        Optional<Agreement> a = aS.listId(id);

        if (a.isPresent()) {
            aS.delete(id);
            return ResponseEntity.ok("Acuerdo eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Acuerdo no encontrado");
        }
    }
    @GetMapping("/agreement-bt-damily/{idFamily}")
    public ResponseEntity<?> getAgreementFamily(@PathVariable int idFamily) {
        List<QueryAgreementByFamilyDTO> listaBusqueda = aS.listAgreementsByFamilyJPQL(idFamily).stream()
                .map(y -> {
                    QueryAgreementByFamilyDTO dto = new QueryAgreementByFamilyDTO();
                    dto.setTitleAgreement(y.getTitleAgreement());
                    dto.setDescriptionAgreement(y.getDescriptionAgreement());
                    dto.setCreationDate(y.getCreationDate());
                    dto.setNameFamily(y.getFamily().getNameFamily());
                    dto.setNameAgreement(y.getAgreementType().getNameAgreementType());
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