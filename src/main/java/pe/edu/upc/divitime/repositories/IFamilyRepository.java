package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.divitime.entities.Family;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IFamilyRepository extends JpaRepository<Family, Integer> {
    @Query("SELECT f FROM Family f WHERE f.creationDate BETWEEN :fechaInicio AND :fechaFin")
    List<Family> listarFamiliasPorRangoFechasJPQL(@Param("fechaInicio") LocalDateTime fechaInicio,
                                                  @Param("fechaFin") LocalDateTime fechaFin);
}

