package pe.edu.upc.divitime.servicesinterfaces;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import pe.edu.upc.divitime.entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    public List<User> list();
    public User insert(User u);
    public Optional<User>listId(int id);
    public void update(User u);
    public void delete(int id);

}
