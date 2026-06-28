package pe.edu.upc.divitime.servicesinterfaces;

import org.springframework.data.repository.query.Param;
import pe.edu.upc.divitime.entities.Chat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IChatService {
    public List<Chat> list();
    public Chat insert(Chat c);
    public Optional<Chat> listId(int id);
    Optional<Chat> findByUser_IdUser(int idUser);
    public Chat save(Chat c);
    List<Object[]> findNewChats(@Param("fecha")LocalDate fecha);
}
