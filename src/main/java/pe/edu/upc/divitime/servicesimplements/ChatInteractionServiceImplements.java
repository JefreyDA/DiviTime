package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.ChatInteraction;
import pe.edu.upc.divitime.repositories.IChatInteractionRepository;
import pe.edu.upc.divitime.servicesinterfaces.IChatInteractionService;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChatInteractionServiceImplements implements IChatInteractionService {
    @Autowired
    private IChatInteractionRepository ciR;


    @Override
    public void registerEntry(ChatInteraction interaction) {
        ciR.save(interaction);
    }

    @Override
    public List<Object[]> countWeeklyInteractions(int idUser, LocalDate limitDate) {
        return ciR.countWeeklyInteractions(idUser, limitDate);
    }
}
