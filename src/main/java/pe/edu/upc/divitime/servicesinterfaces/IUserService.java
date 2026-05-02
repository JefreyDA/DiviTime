package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public List<User> list();
    public User insert(User user);
    public void update(User user);
    public void delete(int id);
    public Optional<User> listId(int id);
}
