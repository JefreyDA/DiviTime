package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.UserFamily;

import java.util.List;
import java.util.Optional;

public interface IUserFamilyService {
    public List<UserFamily> list();
    public UserFamily insert(UserFamily uF);
    public boolean userHasFamily(int id);
    public Optional<UserFamily> listId(int id);
    public void delete(int id);
    public void update(UserFamily userFamily);
}
