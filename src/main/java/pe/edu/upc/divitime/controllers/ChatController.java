package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.ChatGeneralDTO;
import pe.edu.upc.divitime.dtos.ChatUserQuantityDTO;
import pe.edu.upc.divitime.entities.Chat;
import pe.edu.upc.divitime.repositories.IUserRepository;
import pe.edu.upc.divitime.servicesinterfaces.IChatService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private IChatService chS;

    @Autowired
    private IUserRepository uR;

    @PostMapping("/registar")
    public ResponseEntity<ChatGeneralDTO> registrar(@RequestBody ChatGeneralDTO dto){
        ModelMapper m = new ModelMapper();
        Chat c = m.map(dto, Chat.class);
        Chat cur = chS.insert(c);
        ChatGeneralDTO responseDTO = m.map(cur, ChatGeneralDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseDTO);
    }

    @GetMapping("/{id}")
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

    @PutMapping("/{idUser}/incrementar")
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

    @GetMapping("/frecuencia-chat")
    public ResponseEntity<?> obtenerFrecuenciaChat(){
        List<Object[]> listaFrecuencia = chS.obtenerFrecuenciaUsuarios();
        if (listaFrecuencia.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay frecuencias registradas para este chat");
        }

        List<ChatUserQuantityDTO> respuesta = new ArrayList<>();
        for(Object[] fila:listaFrecuencia){
            ChatUserQuantityDTO dto = new ChatUserQuantityDTO();
            dto.setIdChat(((Number)fila[0]).intValue());
            dto.setNameUser((String) fila[1]);
            dto.setFrequencyChat(((Number)fila[2]).intValue());
            respuesta.add(dto);
        }

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/frecuencia-menor-chat")
    public ResponseEntity<?> obtenerFrecuenciaMenor(){
        List<Object[]> listaFrecuenciaMenor = chS.obtenerFrecuenciaMenor();
        if (listaFrecuenciaMenor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay frecuencias registradas para este chat");
        }

        List<ChatGeneralDTO> respuesta = new ArrayList<>();
        for(Object[] fila:listaFrecuenciaMenor){
            ChatGeneralDTO dto = new ChatGeneralDTO();
            dto.setFrequencyChat(((Number)fila[0]).intValue());
            dto.setIdChat(((Number)fila[1]).intValue());
            dto.setIdUser(((Number)fila[2]).intValue());
            dto.setStartDateChat(((LocalDate)fila[3]));
            respuesta.add(dto);
        }

        return ResponseEntity.ok(respuesta);
    }
}
