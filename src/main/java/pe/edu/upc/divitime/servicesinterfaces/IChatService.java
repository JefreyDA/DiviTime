package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.dtos.ChatUserQuantityDTO;
import pe.edu.upc.divitime.entities.Chat;

import java.util.List;
import java.util.Optional;

public interface IChatService {
    public Chat insert(Chat c);
    public Optional<Chat> listId(int id);
    Optional<Chat> findByUser_IdUser(int idUser);
    public Chat save(Chat c);
    List<Object[]> obtenerFrecuenciaUsuarios();
    List<Object[]> obtenerFrecuenciaMenor();
}
