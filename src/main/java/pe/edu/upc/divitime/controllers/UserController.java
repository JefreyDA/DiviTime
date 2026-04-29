package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.UserDTO;
import pe.edu.upc.divitime.dtos.UserGeneralDTO;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.servicesinterfaces.IUserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private IUserService uS;

    @GetMapping("/listar-usuarios")
    public ResponseEntity<List<UserDTO>> listarUsuarios() {
        ModelMapper m = new ModelMapper();
        List<UserDTO> listarUsuarios = uS.list().stream()
                .map(y -> m.map(y, UserDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listarUsuarios);
    }

    @PostMapping("/registrar-usuario")
    public ResponseEntity<UserGeneralDTO> registrarUsuario(@RequestBody UserGeneralDTO dto) {
        ModelMapper m = new ModelMapper();
        User c = m.map(dto, User.class);
        c.setAccountCreatedDateUser(LocalDate.now());
        User user = uS.insert(c);
        UserGeneralDTO responseDTO = m.map(user, UserGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/actualizar-usuario")
    public ResponseEntity<String> actualizarUsuario(@RequestBody UserGeneralDTO dto) {
        Optional<User> existente = uS.listId(dto.getIdUser());
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        User u = existente.get();
        u.setNameUser(dto.getNameUser());
        u.setPaternalSurNameUser(dto.getPaternalSurNameUser());
        u.setMaternalSurNameUser(dto.getMaternalSurNameUser());
        u.setEmailUser(dto.getEmailUser());
        u.setBirthDateUser(dto.getBirthDateUser());

        uS.update(u);
        return ResponseEntity.ok("Usuario actualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable int id) {
        Optional<User> user = uS.listId(id);

        if (user.isPresent()) {
            uS.delete(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }
}
