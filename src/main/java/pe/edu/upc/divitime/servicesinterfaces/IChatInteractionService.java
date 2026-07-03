package pe.edu.upc.divitime.servicesinterfaces;

import org.springframework.data.repository.query.Param;
import pe.edu.upc.divitime.entities.ChatInteraction;

import java.time.LocalDate;
import java.util.List;

public interface IChatInteractionService {
    void registerEntry(ChatInteraction interaction);

    List<ChatInteraction> listByChatId(int idChat);

    List<Object[]> countWeeklyInteractions(@Param("idUser") int idUser,
                                           @Param("limitDate") LocalDate limitDate);

    List<Object[]> countWeeklyInteractionsByFamily(@Param("idFamily") int idFamily,
                                                   @Param("limitDate") LocalDate limitDate);
}
