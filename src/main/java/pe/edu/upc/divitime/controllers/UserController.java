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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService uS;
    @GetMapping("/listUsers")
    public ResponseEntity<List<UserDTO>> listar(){
        ModelMapper m=new ModelMapper();
        List<UserDTO> listaUsuarios=uS.list().stream()
                .map(y->m.map(y, UserDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaUsuarios);

    }

    @PostMapping("/insertar")
    public ResponseEntity<UserGeneralDTO>insertar(@RequestBody UserGeneralDTO dto)
    {
        ModelMapper m=new ModelMapper();
        User u=m.map(dto, User.class);
        User user=uS.insert(u);
        UserGeneralDTO responseDTO=m.map(user,UserGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?>BuscarId(@PathVariable int id)
    {
        ModelMapper m=new ModelMapper();
        Optional<User> user=uS.listId(id);
        if(user.isPresent())
        {
            UserGeneralDTO dto=m.map(user.get(),UserGeneralDTO.class);
            return ResponseEntity.ok(dto);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }
    @PutMapping("/actualizar")
    public ResponseEntity<String>actualizar(@RequestBody UserGeneralDTO dto)
    {
        Optional<User>existe=uS.listId((dto.getIdusuario()));
        if(existe.isEmpty())
        {
            return ResponseEntity.status((HttpStatus.NOT_FOUND))
                    .body("Usuario no encontrado");
        }
        if (dto.getBirthdate()==null||dto.getCreationDate()==null||dto.getPassword()==null)
        {
            return  ResponseEntity.badRequest()
                    .body(("No pueden tener valores nulos"));
        }
        if(!dto.getCreationDate().isAfter(dto.getBirthdate()))
        {
            return ResponseEntity.badRequest()
                    .body("La fecha de creacion deber ser mayor que la fecha de nacimiento");
        }

        User u=existe.get();
        u.setBirthdate(dto.getBirthdate());
        u.setEmail(dto.getEmail());
        u.setNameUser(dto.getNameUser());
        u.setLastname1(dto.getLastname1());
        u.setLastname2(dto.getLastname2());
        u.setPassword(dto.getPassword());
        u.setCreationDate(dto.getCreationDate());

        uS.update(u);
        return  ResponseEntity.ok("Usuario actualizado");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>eliminar(@PathVariable int id)
    {
        Optional<User>u=uS.listId(id);
        if(u.isPresent())
        {
            uS.delete(id);
            return ResponseEntity.ok("Usuario eliminado");
        }else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }


























}
