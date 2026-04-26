package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.Roles;


import java.util.List;
import java.util.Optional;

public interface IRoleService {
    public List<Roles> list();
    public Roles insert(Roles r);
    public Optional<Roles> listId(int id);
    public void update(Roles r);
    public void delete(int id);

}
