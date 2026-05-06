package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
        Optional<User> user = uS.listId(dto.getUserId());
        Optional<Roles> role = rS.listId(dto.getUserRole());

        if(user.isEmpty()){
            return ResponseEntity.status((HttpStatus.NOT_FOUND))
                    .body("Usuario no encontrado.");
        }

        //Ingresar validación en la que el usuario ya tenga un rol asignado
        //Ingresar validaciones si es que no se encuentra el id de Rol o Usuario1
        ModelMapper m = new ModelMapper();
        UserRole uR = m.map(dto, UserRole.class);
        UserRole userRole = urS.insert(uR);
        UserRoleGeneralDTO resposeDTO = m.map(userRole, UserRoleGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resposeDTO);
    }
}
