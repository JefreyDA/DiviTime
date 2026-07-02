package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.Family;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IFamilyService {
    public List<Family> list();

    public Family insert(Family f);

    public Optional<Family> listId(int id);

    public void update(Family f);

    public void delete(int id);

    public List<Family> listarFamiliasPorRangoFechasJPQL(
            LocalDate fechaInicio,
            LocalDate fechaFin
    );

    Optional<Family> findByLink(String link);
}
