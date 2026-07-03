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
    List<ChatInteraction> findByChat_IdChatOrderByInteractionDateAsc(int idChat);

    @Query(value = "SELECT c.id_chat, u.name_user, COUNT(ci.id_interaction) " +
            "FROM chat c " +
            "JOIN tb_user u ON c.id_user = u.id_user " +
            "LEFT JOIN chat_interaction ci ON c.id_chat = ci.id_chat " +
            "WHERE c.id_user = :idUser " +
            "AND ci.interaction_date >= :limitDate " +
            "GROUP BY c.id_chat, u.name_user", nativeQuery = true)
    List<Object[]> countWeeklyInteractions(@Param("idUser") int idUser,
                                           @Param("limitDate") LocalDate limitDate);
    @Query(value = "SELECT c.id_chat, u.name_user, COUNT(ci.id_interaction) " +
            "FROM chat c " +
            "JOIN tb_user u ON c.id_user = u.id_user " +
            "JOIN roles r ON u.id_role = r.id_role " +
            "LEFT JOIN chat_interaction ci ON c.id_chat = ci.id_chat " +
            "AND ci.interaction_date >= :limitDate " +
            "AND ci.sender_role = 'USER' " +
            "WHERE u.id_family = :idFamily " +
            "AND r.name_role = 'HIJO' " +
            "GROUP BY c.id_chat, u.name_user " +
            "ORDER BY u.name_user", nativeQuery = true)
    List<Object[]> countWeeklyInteractionsByFamily(@Param("idFamily") int idFamily,
                                                   @Param("limitDate") LocalDate limitDate);
}
