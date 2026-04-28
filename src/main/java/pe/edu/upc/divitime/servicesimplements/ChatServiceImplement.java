package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.Chat;
import pe.edu.upc.divitime.repositories.IChatRepository;
import pe.edu.upc.divitime.servicesinterfaces.IChatService;

import java.util.List;

@Service
public class ChatServiceImplement implements IChatService {
    @Autowired
    private IChatRepository cR;

    @Override
    public List<Chat> list() {
        return cR.findAll();
    }
}
