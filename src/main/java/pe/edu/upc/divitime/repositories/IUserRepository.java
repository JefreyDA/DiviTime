package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.divitime.entities.User;

import java.util.List;
import java.util.Optional;

// REPOSITORIO ALTERADO PARA LA NUEVA VERSIÓN

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    public User findOneByEmailUser(String emailUser); //seguridad

    List<User> findByStatusUserTrue();
    List<User> findByStatusUserFalse();
    Optional<User> findByEmailUser(String emailUser);

    @Query("select count(u.emailUser) from User u where u.emailUser = :emailUser")
    public int buscarEmailUser(@Param("emailUser") String emailUser);

    @Transactional
    @Modifying
    @Query(value = "insert into roles (NameRole, user_id) VALUES (:rol, :user_id)", nativeQuery = true)
    public void insRol(@Param("rol") String rol,
                       @Param("user_id") int user_id);
}
