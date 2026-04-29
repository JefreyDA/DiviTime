package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.upc.divitime.dtos.ChatUserQuantityDTO;
import pe.edu.upc.divitime.entities.Chat;

import java.util.List;
import java.util.Optional;

@Repository
public interface IChatRepository extends JpaRepository<Chat, Integer> {
    Optional<Chat> findByUser_IdUser(int idUser);

    @Query(value = "Select c.id_chat, u.name_user, c.frequency_chat\n" +
            " from chat c\n" +
            " inner join tb_user u\n" +
            " on c.id_user = u.id_user\n" +
            " order by frequency_chat desc", nativeQuery = true)
    List<Object[]> obtenerFrecuenciaUsuarios();

    @Query(value = "SELECT *\n" +
            " FROM chat\n" +
            " WHERE frequency_chat <= 2", nativeQuery = true)
    List<Object[]> obtenerFrecuenciaMenor();
}
