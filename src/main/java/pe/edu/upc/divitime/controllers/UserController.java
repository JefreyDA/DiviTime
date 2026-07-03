package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.UserGeneralDTO;
import pe.edu.upc.divitime.dtos.UserGeneralListDTO;
import pe.edu.upc.divitime.entities.Expense;
import pe.edu.upc.divitime.entities.Roles;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.servicesinterfaces.IExpenseService;
import pe.edu.upc.divitime.servicesinterfaces.IRoleService;
import pe.edu.upc.divitime.servicesinterfaces.IUserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// CONTROLLER ALTERADO PARA NUEVAS FUNCIONES

@RestController()
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService uS;

    @Autowired
    private IExpenseService eS;

    @Autowired
    private IRoleService rS;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register-users")
    public ResponseEntity<?> registerUsers(@RequestBody UserGeneralDTO dto) {
        ModelMapper m = new ModelMapper();
        User c = m.map(dto, User.class);

        Optional<User> Tempuser = uS.findByEmailUser(dto.getEmailUser());
        if (Tempuser.isPresent()) { return ResponseEntity.status(HttpStatus.CONFLICT).body("Correo ya asociado a otro usuario"); }

        Optional<Roles> role = rS.listId(dto.getIdRole());
        if (role.isEmpty()) { return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Rol no encontrada o no existe\nSolicitud de registro rechazada");}

        c.setStatusUser(true);
        c.setAccountCreatedDateUser(LocalDate.now());
        c.setFamily(null);
        c.setRoles(role.get());
        c.setPasswordUser(passwordEncoder.encode(dto.getPasswordUser()));

        User user = uS.insert(c);
        UserGeneralDTO responseDTO = m.map(user, UserGeneralDTO.class);
        responseDTO.setIdRole(user.getRoles().getIdRole());
        responseDTO.setPasswordUser(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/update-user")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<String> updateUser(@RequestBody UserGeneralDTO dto) {
        Optional<User> exists = uS.listId(dto.getIdUser());
        if (exists.isEmpty()) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado"); }

        Optional<User> Tempuser = uS.findByEmailUser(dto.getEmailUser());
        if (Tempuser.isPresent()
                && Tempuser.get().getIdUser() != dto.getIdUser()) {

            return ResponseEntity.badRequest()
                    .body("Correo ya asociado a otro usuario");}
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
    //@PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        Optional<User> user = uS.listId(id);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        User u = user.get();

        List<Expense> expensesFounden = eS.searchByUser_IdUser(id);
        for(Expense e : expensesFounden) {eS.deleteLogical(e);}

        u.setFamily(null);

        if(u.getStatusUser()==false){
            u.setStatusUser(true);
        }else{
            u.setStatusUser(false);
        }
        uS.deleteLogical(u);
        return ResponseEntity.ok("Usuario y gastos eliminados correctamente");
    }

    @DeleteMapping("/delete-by-email/{emailUser}")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<String> deleteUserByEmail(@PathVariable String emailUser) {
        Optional<User> user = uS.findByEmailUser(emailUser);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado por correo");
        }

        User u = user.get();

        u.setFamily(null);
        u.setStatusUser(false);
        uS.deleteLogical(u);
        return ResponseEntity.ok("Usuario y gastos eliminados correctamente");
    }

    @GetMapping("/list-all-users")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<List<UserGeneralListDTO>> listAllUsers() {
        ModelMapper m = new ModelMapper();
        List<UserGeneralListDTO> listUsers = uS.list().stream()
                .map(y -> m.map(y, UserGeneralListDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listUsers);
    }

    @GetMapping("/list-active-users")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<List<UserGeneralListDTO>> listActiveUsers() {
        ModelMapper m = new ModelMapper();
        List<UserGeneralListDTO> listActUsers = uS.findByStatusUserTrue().stream()
                .map(y -> m.map(y, UserGeneralListDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listActUsers);
    }

    @GetMapping("/list-inactive-users")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<List<UserGeneralListDTO>> listInactiveUsers() {
        ModelMapper m = new ModelMapper();
        List<UserGeneralListDTO> listInactUsers = uS.findByStatusUserFalse().stream()
                .map(y -> m.map(y, UserGeneralListDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listInactUsers);
    }

    @GetMapping("/list-user-by-id/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> listUserById(@PathVariable int id){
        ModelMapper m = new ModelMapper();

        Optional<User> user = uS.listId(id);

        if (user.isPresent()) {

            UserGeneralListDTO dto = m.map(user.get(), UserGeneralListDTO.class);
            dto.setIdRole(user.get().getRoles().getIdRole());

            if (user.get().getFamily() != null) {
                dto.setIdFamily(user.get().getFamily().getIdFamily());
            }

            dto.setPasswordUser(null);

            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }

    @GetMapping("/list-by-email/{emailUser}")
    public ResponseEntity<?> getByEmail(@PathVariable String emailUser) {
        Optional<User> user = uS.findByEmailUser(emailUser);

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        ModelMapper m = new ModelMapper();
        UserGeneralListDTO dto = m.map(user.get(), UserGeneralListDTO.class);
        dto.setIdRole(user.get().getRoles().getIdRole());

        if (user.get().getFamily() != null) {
            dto.setIdFamily(user.get().getFamily().getIdFamily());
        }

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/list-users")
    //@PreAuthorize("hasAnyAuthority('ADMIN','PADRE','TUTOR_LEGAL')")
    public ResponseEntity<List<UserGeneralDTO>> listUsers() {
        ModelMapper m = new ModelMapper();
        List<UserGeneralDTO> listUsers = uS.list().stream()
                .map(y -> m.map(y, UserGeneralDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listUsers);
    }
}
