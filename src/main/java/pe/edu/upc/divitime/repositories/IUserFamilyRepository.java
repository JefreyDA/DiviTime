package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.divitime.entities.UserFamily;

@Repository
public interface IUserFamilyRepository extends JpaRepository<UserFamily, Integer> {
    boolean existsByUser_IdUser(int id);
}
