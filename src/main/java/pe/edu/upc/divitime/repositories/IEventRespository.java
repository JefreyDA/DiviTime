package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.upc.divitime.entities.Event;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IEventRespository extends JpaRepository<Event, Integer> {

    @Query("SELECT e FROM Event e WHERE e.startDateEvent <= ?2 AND e.endDateEvent >= ?1")
    List<Event> listEventsByDate(LocalDate startDate, LocalDate endDate);

    List<Event> findByTitleEventContainingIgnoreCase(String titleEvent);
}
