package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.FamilyDTO;
import pe.edu.upc.divitime.dtos.FamilyGeneralDTO;
import pe.edu.upc.divitime.dtos.QueryFamilyByDate;
import pe.edu.upc.divitime.entities.Family;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.servicesinterfaces.IFamilyService;
import pe.edu.upc.divitime.servicesinterfaces.IUserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
    @RequestMapping("/api/family")
    public class FamilyController {

    @Autowired
    private IFamilyService fS;

    @Autowired
    private IUserService uS;

    @GetMapping("/listFamilies")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<FamilyGeneralDTO>> list() {

        ModelMapper m = new ModelMapper();

        List<FamilyGeneralDTO> listFamilies = fS.list().stream()
                .map(y -> m.map(y, FamilyGeneralDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listFamilies);
    }

    @PostMapping("/insert-family")
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<?> insert(@RequestBody FamilyGeneralDTO dto) {

        ModelMapper m = new ModelMapper();

        if (dto.getNameFamily() == null ||
                dto.getCreationDate() == null ||
                dto.getIdCreatorFamily() == 0) {

            return ResponseEntity.badRequest()
                    .body("Campos obligatorios vacíos");
        }

        Optional<User> user = uS.listId(dto.getIdCreatorFamily());

        if (user.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario creador no encontrado o no existe\nSolicitud de registro rechazada");
        }

        Family f = m.map(dto, Family.class);
        f.setCreatorFamily(user.get());
        Family family = fS.insert(f);

        User u = user.get();
        u.setFamily(family);
        uS.update(u);

        FamilyGeneralDTO responseDTO =
                m.map(family, FamilyGeneralDTO.class);
        responseDTO.setIdCreatorFamily(
                family.getCreatorFamily().getIdUser());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<?> SearchById(@PathVariable int id) {

        ModelMapper m = new ModelMapper();

        Optional<Family> family = fS.listId(id);

        if (family.isPresent()) {

            FamilyGeneralDTO dto =
                    m.map(family.get(), FamilyGeneralDTO.class);

            dto.setIdCreatorFamily(
                    family.get().getCreatorFamily().getIdUser());

            return ResponseEntity.ok(dto);

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Familia no encontrada");
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<String> update(
            @RequestBody FamilyGeneralDTO dto) {

        Optional<Family> existe = fS.listId(dto.getIdFamily());

        if (existe.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Familia no encontrada");
        }

        if (dto.getNameFamily() == null ||
                dto.getCreationDate() == null ||
                dto.getIdCreatorFamily() == 0) {

            return ResponseEntity.badRequest()
                    .body("No pueden tener valores nulos");
        }

        Optional<User> user = uS.listId(dto.getIdCreatorFamily());

        if (user.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario creador no encontrado o no existe\nSolicitud de actualización rechazada");
        }

        Family f = existe.get();

        f.setNameFamily(dto.getNameFamily());
        f.setCreationDate(dto.getCreationDate());
        f.setLinkInvitationFamily(dto.getLinkInvitationFamily());
        f.setCreatorFamily(user.get());

        fS.update(f);

        return ResponseEntity.ok("Familia actualizada");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<String> delete(@PathVariable int id) {

        Optional<Family> f = fS.listId(id);

        if (f.isPresent()) {

            fS.delete(id);

            return ResponseEntity.ok("Familia eliminada");

        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Familia no encontrada");
        }
    }

    @GetMapping("/familias-by-dates")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> getFamiliesByDate(
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin) {

        List<QueryFamilyByDate> listaBusqueda =
                fS.listarFamiliasPorRangoFechasJPQL(
                                fechaInicio,
                                fechaFin)
                        .stream()
                        .map(y -> {

                            QueryFamilyByDate dto =
                                    new QueryFamilyByDate();

                            dto.setNameFamily(y.getNameFamily());
                            dto.setCreationDate(y.getCreationDate());
                            dto.setLinkInvitationFamily(
                                    y.getLinkInvitationFamily());

                            return dto;
                        })
                        .collect(Collectors.toList());

        if (listaBusqueda.isEmpty()) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existen familias creadas en ese rango de fechas");
        }

        return ResponseEntity.ok(listaBusqueda);
    }

    @PutMapping("/join/{idUser}")
    public ResponseEntity<?> joinFamily(@PathVariable int idUser, @RequestBody String link) {

        Optional<User> user = uS.listId(idUser);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Optional<Family> family = fS.findByLink(link);
        if (family.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Link de invitación inválido");
        }

        User u = user.get();
        u.setFamily(family.get());
        uS.update(u);

        return ResponseEntity.ok("Usuario unido a la familia correctamente");
    }

    @PutMapping("/leave/{idUser}")
    public ResponseEntity<?> leaveFamily(@PathVariable int idUser) {

        Optional<User> user = uS.listId(idUser);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        User u = user.get();
        u.setFamily(null);
        uS.update(u);

        return ResponseEntity.ok("Usuario salió de la familia correctamente");
    }
}

