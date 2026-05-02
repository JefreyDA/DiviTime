package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.divitime.entities.Agreement;

import java.util.List;

@Repository
public interface IAgreementRepository extends JpaRepository<Agreement, Integer> {
    @Query("SELECT a FROM Agreement a WHERE a.family.idFamily = :idFamily")
    public List<Agreement> listAgreementsByFamilyJPQL(@Param("idFamily") int idFamily);
}