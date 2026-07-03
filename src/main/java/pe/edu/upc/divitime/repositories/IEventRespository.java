package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.divitime.entities.Event;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IEventRespository extends JpaRepository<Event, Integer> {

    @Query("SELECT e FROM Event e WHERE e.user.idUser = ?1")
    List<Event> listEventsByUserId(@Param("idUser") int idUser);

    @Query("SELECT e FROM Event e WHERE e.user.family.idFamily = ?1")
    List<Event> listEventsByFamilyId(@Param("idFamily") int idFamily);

    @Query(" SELECT e FROM Event e WHERE e.user.family.idFamily = :idFamily AND e.startDateEvent >= CURRENT_DATE ORDER BY e.startDateEvent ASC")
    List<Event> findUpcomingByFamily(@Param("idFamily") int idFamily);

    @Query("""
SELECT e.user.nameUser, COUNT(e)
FROM Event e
WHERE e.user.family.idFamily = :idFamily
AND e.startDateEvent BETWEEN :fechaInicio AND :fechaFin
AND e.user.roles.nameRole IN ('PADRE','TUTOR_LEGAL')
GROUP BY e.user.nameUser
""")
    List<Object[]> compararEventos(
            @Param("idFamily") int idFamily,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);
}
