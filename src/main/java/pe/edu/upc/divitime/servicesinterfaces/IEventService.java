package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IEventService {
    public List<Event> listEvents();
    public Event insertEvents(Event e);
    public Optional<Event> listEventById(int id);
    public void updateEvent(Event e);
    public void deleteEvent(int id);
    List<Event> listEventsByDate(LocalDate startDate, LocalDate endDate);
    List<Event> findByTitleEvent(String titleEvent);
}
