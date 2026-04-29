package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.Event;
import pe.edu.upc.divitime.repositories.IEventRespository;
import pe.edu.upc.divitime.servicesinterfaces.IEventService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImplement implements IEventService {
    @Autowired
    private IEventRespository eR;

    @Override
    public List<Event> listEvents() {
        return eR.findAll();
    }

    @Override
    public Event insertEvents(Event e) {
        return eR.save(e);
    }

    @Override
    public Optional<Event> listEventById(int id) {
        return eR.findById(id);
    }

    @Override
    public void updateEvent(Event e) {
        eR.save(e);
    }

    @Override
    public void deleteEvent(int id) {
        eR.deleteById(id);
    }

    @Override
    public List<Event> listEventsByDate(LocalDate startDate, LocalDate endDate) {
        return eR.listEventsByDate(startDate, endDate);
    }

    @Override
    public List<Event> findByTitleEvent(String titleEvent) {
        return eR.findByTitleEventContainingIgnoreCase(titleEvent);
    }

    @Override
    public List<Event> listUpcomingByFamily(int idFamily) {
        return eR.findUpcomingByFamily(idFamily);
    }
}
