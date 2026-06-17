package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.User;

import java.util.List;
import java.util.Optional;

// SERVICIO ALTERADO PARA LA NUEVA VERSIÓN

public interface IUserService {
    public List<User> list();
    public User insert(User user);
    public void update(User user);
    public void deleteLogical(User user);
    public Optional<User> listId(int id);

    List<User> findByStatusUserTrue();
    List<User> findByStatusUserFalse();
    Optional<User> findByEmailUser(String emailUser);
}
