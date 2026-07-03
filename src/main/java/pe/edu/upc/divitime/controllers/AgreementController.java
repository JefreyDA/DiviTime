package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.QueryAgreementByFamilyDTO;
import pe.edu.upc.divitime.dtos.AgreementDTO;
import pe.edu.upc.divitime.dtos.AgreementGeneralDTO;
import pe.edu.upc.divitime.entities.Agreement;
import pe.edu.upc.divitime.entities.AgreementType;
import pe.edu.upc.divitime.entities.Family;
import pe.edu.upc.divitime.servicesinterfaces.IAgreementService;
import pe.edu.upc.divitime.servicesinterfaces.IAgreementTypeService;
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

    @Autowired
    private IAgreementTypeService atS;

    @GetMapping("/listAgreements")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<AgreementGeneralDTO>> list() {

        ModelMapper m = new ModelMapper();

        List<AgreementGeneralDTO> list = aS.list().stream()
                .map(y -> m.map(y, AgreementGeneralDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @PostMapping("/insert-agreement")
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<?> insert(@RequestBody AgreementGeneralDTO dto) {

        ModelMapper m = new ModelMapper();

        if (dto.getTitleAgreement() == null ||
                dto.getDescriptionAgreement() == null ||
                dto.getCreationDate() == null ||
                dto.getIdFamily() == 0 ||
                dto.getIdAgreementType() == 0) {

            return ResponseEntity.badRequest()
                    .body("Campos obligatorios vacíos");
        }

        Optional<Family> fam = fS.listId(dto.getIdFamily());

        if (fam.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Familia no encontrada o no existe\nSolicitud de registro rechazada");
        }

        Optional<AgreementType> type = atS.listId(dto.getIdAgreementType());

        if (type.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tipo de acuerdo no encontrado o no existe\nSolicitud de registro rechazada");
        }

        Agreement a = m.map(dto, Agreement.class);

        a.setFamily(fam.get());
        a.setAgreementType(type.get());

        Agreement agreement = aS.insert(a);

        AgreementGeneralDTO responseDTO = m.map(agreement, AgreementGeneralDTO.class);

        responseDTO.setIdFamily(agreement.getFamily().getIdFamily());
        responseDTO.setIdAgreementType(agreement.getAgreementType().getIdAgreementType());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> SearchId(@PathVariable int id) {

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
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<String> update(@RequestBody AgreementGeneralDTO dto) {

        Optional<Agreement> existe = aS.listId(dto.getIdAgreement());

        if (existe.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Acuerdo no encontrado");
        }

        if (dto.getTitleAgreement() == null ||
                dto.getDescriptionAgreement() == null ||
                dto.getCreationDate() == null ||
                dto.getIdFamily() == 0 ||
                dto.getIdAgreementType() == 0) {

            return ResponseEntity.badRequest()
                    .body("Campos obligatorios vacíos");
        }

        Optional<Family> fam = fS.listId(dto.getIdFamily());

        if (fam.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Familia no encontrada o no existe\nSolicitud de actualización rechazada");
        }

        Optional<AgreementType> type = atS.listId(dto.getIdAgreementType());

        if (type.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tipo de acuerdo no encontrado o no existe\nSolicitud de actualización rechazada");
        }

        Agreement a = existe.get();

        a.setTitleAgreement(dto.getTitleAgreement());
        a.setDescriptionAgreement(dto.getDescriptionAgreement());
        a.setCreationDate(dto.getCreationDate());
        a.setFamily(fam.get());
        a.setAgreementType(type.get());

        aS.update(a);

        return ResponseEntity.ok("Acuerdo actualizado");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
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

    @GetMapping("/agreement-by-family/{idFamily}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<?> getAgreementFamily(@PathVariable int idFamily) {

        List<QueryAgreementByFamilyDTO> listaBusqueda =
                aS.listAgreementsByFamilyJPQL(idFamily)
                        .stream()
                        .map(y -> {

                            QueryAgreementByFamilyDTO dto =
                                    new QueryAgreementByFamilyDTO();

                            dto.setTitleAgreement(y.getTitleAgreement());
                            dto.setDescriptionAgreement(y.getDescriptionAgreement());
                            dto.setCreationDate(y.getCreationDate());
                            dto.setNameFamily(y.getFamily().getNameFamily());
                            dto.setNameAgreement(
                                    y.getAgreementType().getNameAgreementType());

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