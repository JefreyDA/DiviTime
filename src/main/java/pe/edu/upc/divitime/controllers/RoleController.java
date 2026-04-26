package pe.edu.upc.divitime.controllers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.RoleDTO;
import pe.edu.upc.divitime.dtos.RoleGeneralDTO;
import pe.edu.upc.divitime.entities.Roles;
import pe.edu.upc.divitime.servicesinterfaces.IRoleService;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    private IRoleService rS;

    @GetMapping("/listRoles")
    public ResponseEntity<List<RoleDTO>> listar() {
        ModelMapper m = new ModelMapper();
        List<RoleDTO> listaRoles = rS.list().stream()
                .map(y -> m.map(y, RoleDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaRoles
        );

    }

    @PostMapping("/insertar")
    public ResponseEntity<RoleGeneralDTO> insertar(@RequestBody RoleGeneralDTO dto) {
        ModelMapper m = new ModelMapper();
        Roles r = m.map(dto, Roles.class);
        Roles role = rS.insert(r);
        RoleGeneralDTO responseDTO = m.map(role, RoleGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> BuscarId(@PathVariable int id) {
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

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizar(@RequestBody RoleGeneralDTO dto) {
        Optional<Roles> existe = rS.listId((dto.getIdRole()));
        if (existe.isEmpty()) {
            return ResponseEntity.status((HttpStatus.NOT_FOUND))
                    .body("Rol no encontrado");
        }
        if (dto.getNameRole() == null) {
            return ResponseEntity.badRequest()
                    .body(("Ingrese un nombre para el rol"));
        }


        Roles r = existe.get();
        r.setNameRole(dto.getNameRole());


        rS.update(r);
        return ResponseEntity.ok("Rol actualizado");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
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
