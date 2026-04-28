package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.User;
import pe.edu.upc.divitime.repositories.IUserRepository;
import pe.edu.upc.divitime.servicesinterfaces.IUserService;

import java.util.Optional;

@Service
public class UserServiceImplement implements IUserService {
    @Autowired
    private IUserRepository uR;

    @Override
    public Optional<User> listId(int id) {
        return uR.findById(id);
    }
}
