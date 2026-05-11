package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.divitime.entities.Chat;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IChatRepository extends JpaRepository<Chat, Integer> {
    Optional<Chat> findByUser_IdUser(int idUser);

    //Query: Hijos que crearon ingresaron al chat por primera vez en el último mes
    @Query(value = "SELECT c.id_chat, c.id_user, u.name_user\n" +
            " FROM chat c\n" +
            " JOIN tb_user u \n" +
            " ON c.id_user = u.id_user\n" +
            " WHERE c.start_date_chat >= :fecha", nativeQuery = true)
    List<Object[]> findNuevosChats(@Param("fecha")LocalDate fecha);
}
