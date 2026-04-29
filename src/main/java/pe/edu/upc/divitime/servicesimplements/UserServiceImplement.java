package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.repositories.IUserRepository;
import pe.edu.upc.divitime.servicesinterfaces.IUserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements IUserService {
    @Autowired
    private IUserRepository uR;

    @Override
    public List<User> list() {
        return uR.findAll();
    }

    @Override
    public User insert(User u) {
        return uR.save(u);
    }

    @Override
    public Optional<User> listId(int id) {
        return uR.findById(id);
    }

    @Override
    public void update(User u) {
        uR.save(u);
    }

    @Override
    public void delete(int id) {
        uR.deleteById(id);

    }
}
