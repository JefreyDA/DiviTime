package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.ChatGeneralDTO;
import pe.edu.upc.divitime.dtos.ChatRecentUserDTO;
import pe.edu.upc.divitime.entities.Chat;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.repositories.IUserRepository;
import pe.edu.upc.divitime.servicesinterfaces.IChatService;
import pe.edu.upc.divitime.servicesinterfaces.IUserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private IChatService chS;

    @Autowired
    private IUserService uS;
    @Autowired
    private IUserRepository uR;

    @PostMapping("/register")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'HIJO')")
    public ResponseEntity<?> registrar(@RequestBody ChatGeneralDTO dto){
        if(dto.getIdUser() == 0 || dto.getStartDateChat() == null){
            return ResponseEntity.badRequest()
                    .body("El ID del usuario y la fecha de registro no pueden ser nulos");
        }

        Optional<User> user = uS.listId(dto.getIdUser());
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        if(dto.getStartDateChat().isAfter(LocalDate.now())){
            return ResponseEntity.badRequest()
                    .body("La fecha de inicio no puede ser mayor a la actual");
        }

        ModelMapper m = new ModelMapper();
        Chat c = m.map(dto, Chat.class);
        Chat cur = chS.insert(c);
        ChatGeneralDTO responseDTO = m.map(cur, ChatGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> buscarPorId(@PathVariable int id){
        ModelMapper m = new ModelMapper();
        Optional<Chat> chat = chS.listId(id);

        if(chat.isPresent()){
            ChatGeneralDTO dto =m.map(chat.get(), ChatGeneralDTO.class);
            return ResponseEntity.ok(dto);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Chat no encontrado");
        }
    }

    @PutMapping("/{idUser}/increase")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE DE FAMILIA', 'TUTOR LEGAL')")
    public ResponseEntity<?> incrementarFrecuenciaChat(@PathVariable int idUser){
        Optional<Chat> chatO = chS.findByUser_IdUser(idUser);

        if(chatO.isPresent()){
            Chat chat = chatO.get();
            chat.setFrequencyChat(chat.getFrequencyChat() + 1);
            chS.save(chat);
            return ResponseEntity.ok("Frecuencia Incrementada");
        }else{
            Chat nuevo = new Chat();

            nuevo.setUser(
                    uR.findById(idUser)
                            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"))
            );

            nuevo.setStartDateChat(LocalDate.now());
            nuevo.setFrequencyChat(1);

            return ResponseEntity.ok("Chat creado e inicializado");
        }
    }


    @GetMapping("/recents-users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> obtenerRecientes() {
        LocalDate fechaFiltro = LocalDate.now().minusMonths(1);
        List<Object[]> lista = chS.findNewChats(fechaFiltro);

        if(lista.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ningún usuario registro un chat en el último mes.");
        }

            List<ChatRecentUserDTO> respuesta = new ArrayList<>();

            for (Object[] columna : lista) {
                ChatRecentUserDTO dto = new ChatRecentUserDTO();
                dto.setIdChat(((Number) columna[0]).intValue());
                dto.setIdUser(((Number) columna[1]).intValue());
                dto.setNameUser((String) columna[2]);
                respuesta.add(dto);
            }
        return ResponseEntity.ok(respuesta);
    }
}
