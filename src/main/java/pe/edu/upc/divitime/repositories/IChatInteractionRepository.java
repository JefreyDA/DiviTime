package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.divitime.entities.ChatInteraction;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IChatInteractionRepository extends JpaRepository<ChatInteraction, Integer> {
    @Query(value = "SELECT c.id_chat, u.name_user,\n" +
            "\tcount(ci.id_interaction)\n" +
            " FROM chat c\n" +
            " JOIN tb_user u ON c.id_user = u.id_user\n" +
            " LEFT JOIN chat_interaction ci\n" +
            " ON c.id_chat = ci.id_chat \n" +
            " WHERE c.id_user = 1 \n" +
            " \tAND ci.interaction_date >= :limitDate" +
            " GROUP BY c.id_chat, u.name_user", nativeQuery = true)
    List<Object[]> countWeeklyInteractions(@Param("idUser") int idUser, @Param("limitDate")LocalDate limitDate);
}
