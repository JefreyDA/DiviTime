package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.divitime.entities.Agreement;
import pe.edu.upc.divitime.entities.AgreementType;

@Repository
public interface IAgreementTypeRepository extends JpaRepository<AgreementType,Integer> {
}
