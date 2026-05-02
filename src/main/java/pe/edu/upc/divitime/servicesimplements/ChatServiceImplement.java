package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.dtos.ChatUserQuantityDTO;
import pe.edu.upc.divitime.entities.Chat;
import pe.edu.upc.divitime.repositories.IChatRepository;
import pe.edu.upc.divitime.servicesinterfaces.IChatService;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImplement implements IChatService {
    @Autowired
    private IChatRepository cR;


    @Override
    public Chat insert(Chat c) {
        return cR.save(c);
    }

    @Override
    public Optional<Chat> listId(int id) {
        return cR.findById(id);
    }

    @Override
    public Optional<Chat> findByUser_IdUser(int idUser) {
        return cR.findByUser_IdUser(idUser);
    }

    @Override
    public Chat save(Chat c) {
        return cR.save(c);
    }

    @Override
    public List<Object[]> obtenerFrecuenciaUsuarios() {
        return cR.obtenerFrecuenciaUsuarios();
    }

    @Override
    public List<Object[]> obtenerFrecuenciaMenor() {
        return cR.obtenerFrecuenciaMenor();
    }


}

