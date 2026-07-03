package pe.edu.upc.divitime.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.divitime.dtos.ChatInteractionCountWeeklyDTO;
import pe.edu.upc.divitime.entities.Chat;
import pe.edu.upc.divitime.entities.ChatInteraction;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.servicesinterfaces.IChatInteractionService;
import pe.edu.upc.divitime.servicesinterfaces.IChatService;
import pe.edu.upc.divitime.servicesinterfaces.IUserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        interaction.setMessageText("Interacción registrada");
        interaction.setSenderRole("USER");
        interaction.setInteractionDate(LocalDateTime.now());

        ciS.registerEntry(interaction);

        return ResponseEntity.ok("Interacción registrada correctamente");
    }

    @GetMapping("/weekly-count/{idUser}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'TUTOR_LEGAL')")
    public ResponseEntity<?> getWeeklyCount(@PathVariable int idUser){
        LocalDate limitDate = LocalDate.now().minusDays(7);

        Optional<User> userOpt = uS.listId(idUser);
        if(userOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        List<Object[]> datos = ciS.countWeeklyInteractions(idUser, limitDate);
        return ResponseEntity.ok(toWeeklyDTO(datos));
    }

    @GetMapping("/weekly-count-family/{idFamily}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PADRE', 'TUTOR_LEGAL')")
    public ResponseEntity<?> getWeeklyCountByFamily(@PathVariable int idFamily, Authentication authentication){
        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sesión no válida");
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (!isAdmin) {
            Optional<User> loggedUser = uS.findByEmailUser(authentication.getName());

            if (loggedUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario autenticado no encontrado");
            }

            if (loggedUser.get().getFamily() == null || loggedUser.get().getFamily().getIdFamily() != idFamily) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No puedes ver reportes de una familia distinta a la tuya");
            }
        }

        LocalDate limitDate = LocalDate.now().minusDays(7);
        List<Object[]> datos = ciS.countWeeklyInteractionsByFamily(idFamily, limitDate);

        return ResponseEntity.ok(toWeeklyDTO(datos));
    }

    private List<ChatInteractionCountWeeklyDTO> toWeeklyDTO(List<Object[]> datos) {
        List<ChatInteractionCountWeeklyDTO> response = new ArrayList<>();

        for(Object[] fila: datos){
            ChatInteractionCountWeeklyDTO dto = new ChatInteractionCountWeeklyDTO();
            dto.setIdChat(((Number) fila[0]).intValue());
            dto.setNameUser((String) fila[1]);
            dto.setTotalInteractions(((Number) fila[2]).intValue());
            response.add(dto);
        }

        return response;
    }
}
