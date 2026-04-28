package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.divitime.entities.Family;

@Repository
public interface IFamilyRepository extends JpaRepository<Family, Integer> {
}
