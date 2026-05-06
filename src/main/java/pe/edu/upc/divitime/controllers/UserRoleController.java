package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.UserRoleGeneralDTO;
import pe.edu.upc.divitime.entities.Roles;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.entities.UserRole;
import pe.edu.upc.divitime.servicesinterfaces.IRoleService;
import pe.edu.upc.divitime.servicesinterfaces.IUserRoleService;
import pe.edu.upc.divitime.servicesinterfaces.IUserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/userRole")
public class UserRoleController {
    @Autowired
    private IUserRoleService urS;
    @Autowired
    private IUserService uS;
    @Autowired
    private IRoleService rS;

    @PostMapping("/register-userRole")
    public ResponseEntity<?> insert(@RequestBody UserRoleGeneralDTO dto){
        Optional<User> userOpt = uS.listId(dto.getUserId());
        Optional<Roles> roleOpt = rS.listId(dto.getUserRole());
        boolean exists = urS.existsByUserAndRole(dto.getUserId(), dto.getUserRole());

        if(userOpt.isEmpty()){
            return ResponseEntity.status((HttpStatus.NOT_FOUND))
                    .body("Usuario no encontrado.");
        }

        if(roleOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol no encontrado");
        }

        if(exists) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El usuario ya tiene este rol asignado.");
        }

        UserRole ur = new UserRole();
        ur.setUser(userOpt.get());
        ur.setRole(roleOpt.get());

        UserRole userRole = urS.insert(ur);

        ModelMapper m = new ModelMapper();

        UserRoleGeneralDTO resposeDTO = m.map(userRole, UserRoleGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resposeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Integer id, @RequestBody UserRoleGeneralDTO dto){

        Optional<UserRole> existingUR = urS.listId(id);

        if(existingUR.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("UserRole no encontrado.");
        }

        Optional<Roles> role = rS.listId(dto.getUserRole());

        if(role.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol no encontrado.");
        }

        UserRole uR = existingUR.get();

        uR.setRole(role.get());

        UserRole updated = urS.insert(uR);

        ModelMapper m = new ModelMapper();
        UserRoleGeneralDTO responseDTO = m.map(updated, UserRoleGeneralDTO.class);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id){
        Optional<UserRole> userRole = urS.listId(id);

        if(userRole.isPresent()){
            urS.detele(id);
            return ResponseEntity.ok("Usuario Rol eliminado correctamente.");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario Rol no encontrado.");
        }
    }
}
