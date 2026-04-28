package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.repositories.IUserRepository;
import pe.edu.upc.divitime.servicesinterfaces.IUserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplements implements IUserService {

    @Autowired
    private IUserRepository iUsRep;

    @Override
    public List<User> list() {
        return iUsRep.findAll();
    }

    @Override
    public User insert(User user) {
        return iUsRep.save(user);
    }

    @Override
    public void update(User user) {
        iUsRep.save(user);
    }

    @Override
    public void delete(int id) {
        iUsRep.deleteById(id);
    }

    @Override
    public Optional<User> listId(int id) {
        return iUsRep.findById(id);
    }
}
