package pe.edu.upc.divitime.controllers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.RoleDTO;
import pe.edu.upc.divitime.dtos.RoleGeneralDTO;
import pe.edu.upc.divitime.entities.Roles;
import pe.edu.upc.divitime.servicesinterfaces.IRoleService;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private IRoleService rS;

    //Validar la existencia de solo cuatro roles: Padre / Madre / Tutor Legal / Hijo

    @GetMapping("/list-roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<RoleGeneralDTO>> listaRoles() {
        ModelMapper m = new ModelMapper();
        List<RoleGeneralDTO> listRoles = rS.list().stream()
                .map(y -> m.map(y, RoleGeneralDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listRoles);

    }

    @PostMapping("/insert-rol")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> insertRol(@RequestBody RoleGeneralDTO dto) {
        if (dto.getNameRole() == null ||
                dto.getNameRole().trim().isEmpty()) {

            return ResponseEntity.badRequest()
                    .body("Ingrese un nombre para el rol");
        }

        List<String> validRoles = Arrays.asList(
                "PADRE DE FAMILIA",
                "ADMIN",
                "TUTOR LEGAL",
                "HIJO"
        );

        if (!validRoles.contains(dto.getNameRole())) {

            return ResponseEntity.badRequest()
                    .body("Solo se permiten los roles: PADRE DE FAMILIA, ADMIN, TUTOR LEGAL y HIJO");
        }

        boolean exists =
                rS.existsByNameRole(dto.getNameRole());

        if (exists) {

            return ResponseEntity.badRequest()
                    .body("Ya existe un rol con ese nombre");
        }
        ModelMapper m = new ModelMapper();
        Roles r = m.map(dto, Roles.class);
        Roles role = rS.insert(r);
        RoleGeneralDTO responseDTO = m.map(role, RoleGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> SeachById(@PathVariable int id) {
        ModelMapper m = new ModelMapper();
        Optional<Roles> role = rS.listId(id);
        if (role.isPresent()) {
            RoleGeneralDTO dto = m.map(role.get(), RoleGeneralDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol no encontrado");
        }
    }

    @PutMapping("/update-rol")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateRol(@RequestBody RoleGeneralDTO dto) {
        Optional<Roles> exists = rS.listId((dto.getIdRole()));
        if (exists.isEmpty()) {
            return ResponseEntity.status((HttpStatus.NOT_FOUND))
                    .body("Rol no encontrado");
        }
        if (dto.getNameRole() == null) {
            return ResponseEntity.badRequest()
                    .body(("Ingrese un nombre para el rol"));
        }

        List<String> validRoles = Arrays.asList(
                "PADRE DE FAMILIA",
                "ADMIN",
                "TUTOR LEGAL",
                "HIJO"
        );

        if (!validRoles.contains(dto.getNameRole())) {

            return ResponseEntity.badRequest()
                    .body("Solo se permiten los roles: PADRE DE FAMILIA, ADMIN, TUTOR LEGAL y HIJO");
        }

        boolean duplicated =
                rS.existsByNameRoleAndIdRoleNot(
                        dto.getNameRole(),
                        dto.getIdRole());

        if (duplicated) {

            return ResponseEntity.badRequest()
                    .body("Ya existe un rol con ese nombre");
        }

        Roles r = exists.get();
        r.setNameRole(dto.getNameRole());


        rS.update(r);
        return ResponseEntity.ok("Rol actualizado");

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteRol(@PathVariable int id) {
        Optional<Roles> r = rS.listId(id);
        if (r.isPresent()) {
            rS.delete(id);
            return ResponseEntity.ok("Rol eliminado");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol no encontrado");
        }
    }
}
