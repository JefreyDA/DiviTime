package pe.edu.upc.divitime.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.divitime.dtos.ChatDTO;
import pe.edu.upc.divitime.servicesinterfaces.IChatService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private IChatService chS;

    @GetMapping("/listar")
    public ResponseEntity<List<ChatDTO>> listar(){
        ModelMapper m = new ModelMapper();
        List<ChatDTO> listaChat = chS.list().stream()
                .map(y -> m.map(y, ChatDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaChat);
    }
}
