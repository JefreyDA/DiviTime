package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.User;

import java.util.Optional;

public interface IUserService {
    public Optional<User> listId(int id);
}
