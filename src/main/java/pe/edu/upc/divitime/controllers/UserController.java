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

@RestController()
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService uS;

    @GetMapping("/list-users")
    public ResponseEntity<List<UserDTO>> listaUsers() {
        ModelMapper m = new ModelMapper();
        List<UserDTO> listaUsers = uS.list().stream()
                .map(y -> m.map(y, UserDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaUsers);
    }

    @PostMapping("/register-users")
    public ResponseEntity<UserGeneralDTO> registerUsers(@RequestBody UserGeneralDTO dto) {
        ModelMapper m = new ModelMapper();
        User c = m.map(dto, User.class);
        c.setAccountCreatedDateUser(LocalDate.now());
        User user = uS.insert(c);
        UserGeneralDTO responseDTO = m.map(user, UserGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/update-user")
    public ResponseEntity<String> updateUser(@RequestBody UserGeneralDTO dto) {
        Optional<User> exists = uS.listId(dto.getIdUser());
        if (exists.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        User u = exists.get();
        u.setNameUser(dto.getNameUser());
        u.setPaternalSurNameUser(dto.getPaternalSurNameUser());
        u.setMaternalSurNameUser(dto.getMaternalSurNameUser());
        u.setEmailUser(dto.getEmailUser());
        u.setBirthDateUser(dto.getBirthDateUser());

        uS.update(u);
        return ResponseEntity.ok("Usuario actualizado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
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
