package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.UserFamilyDTO;
import pe.edu.upc.divitime.dtos.UserFamilyGeneralDTO;
import pe.edu.upc.divitime.entities.Family;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.entities.UserFamily;
import pe.edu.upc.divitime.servicesinterfaces.IFamilyService;
import pe.edu.upc.divitime.servicesinterfaces.IUserFamilyService;
import pe.edu.upc.divitime.servicesinterfaces.IUserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/userFamily")
public class UserFamilyController {
    @Autowired
    private IUserFamilyService ufS;
    @Autowired
    private IUserService uS;
    @Autowired
    private IFamilyService fS;

    @GetMapping("/list-userFamily")
    public ResponseEntity<List<UserFamilyDTO>> list() {
        ModelMapper m = new ModelMapper();
        List<UserFamilyDTO> listUserFamilyDTO = ufS.list().stream()
                .map(y -> m.map(y, UserFamilyDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listUserFamilyDTO);
    }

    @PostMapping("/register-userFamily")
    public ResponseEntity<?> insert(@RequestBody UserFamilyGeneralDTO dto) {

        Optional<User> user = uS.listId(dto.getUserId());
        Optional<Family> family = fS.listId(dto.getFamilyId());

        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        if(ufS.userHasFamily(dto.getUserId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El usuario ya pertenece a una familia.");
        }

        if(family.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Familia no encontrado");
        }

        ModelMapper m = new ModelMapper();
        UserFamily uf = m.map(dto, UserFamily.class);
        UserFamily userFamily = ufS.insert(uf);
        UserFamilyGeneralDTO responseDTO = m.map(userFamily, UserFamilyGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/update-userFamily")
    public ResponseEntity<String> updateUserFamily(@RequestBody UserFamilyGeneralDTO dto) {
        Optional<UserFamily> userFamily = ufS.listId(dto.getIdUserFamily());
        if (userFamily.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Relacion Usuario-Familia no encontrado");
        }

        Optional<User> user = uS.listId(dto.getUserId());
        Optional<Family> family = fS.listId(dto.getFamilyId());

        if(user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        if(family.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Familia no encontrado");
        }


        UserFamily uf = userFamily.get();
        uf.setUser(user.get());
        uf.setFamily(family.get());
        uf.setFamilyPosition(dto.getFamilyPosition());

        ufS.update(uf);
        return ResponseEntity.ok("Usuario-Familia actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserFamily(@PathVariable int id) {
        Optional<UserFamily> userFamily = ufS.listId(id);

        if (userFamily.isPresent()) {
            ufS.delete(id);
            return ResponseEntity.ok("Usuario-Familia eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario-Familia no encontrado");
        }
    }
}
