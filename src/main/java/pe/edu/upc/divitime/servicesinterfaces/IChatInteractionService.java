package pe.edu.upc.divitime.servicesinterfaces;

import org.springframework.data.repository.query.Param;
import pe.edu.upc.divitime.entities.ChatInteraction;

import java.time.LocalDate;
import java.util.List;

public interface IChatInteractionService {
    public void registerEntry(ChatInteraction interaction);
    List<Object[]> countWeeklyInteractions(@Param("idUser") int idUser, @Param("limitDate") LocalDate limitDate);
}
