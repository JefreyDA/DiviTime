package pe.edu.upc.divitime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.ChatInteractionCountWeeklyDTO;
import pe.edu.upc.divitime.entities.Chat;
import pe.edu.upc.divitime.entities.ChatInteraction;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.servicesinterfaces.IChatInteractionService;
import pe.edu.upc.divitime.servicesinterfaces.IChatService;
import pe.edu.upc.divitime.servicesinterfaces.IUserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chatInteraction")
public class ChatInteractionController {
    @Autowired
    private IChatInteractionService ciS;

    @Autowired
    private IUserService uS;

    @Autowired
    private IChatService cS;

    @PostMapping("/register/{idChat}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'HIJO')")
    public ResponseEntity<String> register(@PathVariable("idChat") int idChat){


        Optional<Chat> chat = cS.listId(idChat);

        if (chat.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Chat no encontrado");
        }

        ChatInteraction interaction = new ChatInteraction();
        interaction.setChat(chat.get());
        interaction.setInteractionDate(LocalDate.now());

        ciS.registerEntry(interaction);

        return ResponseEntity.ok("Interacción registrada correctamente");
    }

    @GetMapping("/weekly-count/{idUser}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE')")
    public ResponseEntity<?> getWeeklyCount(@PathVariable int idUser){
        LocalDate limitDate = LocalDate.now().minusDays(7);

        List<Object[]> datos = ciS.countWeeklyInteractions(idUser, limitDate);
        Optional<User> userOpt = uS.listId(idUser);
        if(userOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        if (datos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay interacciones registradas para este chat");
        }

        List<ChatInteractionCountWeeklyDTO> response = new ArrayList<>();

        for(Object[] fila: datos){
            ChatInteractionCountWeeklyDTO dto = new ChatInteractionCountWeeklyDTO();
            dto.setIdChat(((Number) fila[0]).intValue());
            dto.setNameUser((String) fila[1]);
            dto.setTotalInteractions(((Number) fila[2]).intValue());
            response.add(dto);
        }

        return ResponseEntity.ok(response);
    }
}
