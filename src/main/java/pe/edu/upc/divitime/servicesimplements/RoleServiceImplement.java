package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.Roles;
import pe.edu.upc.divitime.repositories.IRoleRepository;
import pe.edu.upc.divitime.servicesinterfaces.IRoleService;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImplement implements IRoleService {
    @Autowired
    private IRoleRepository rR;
    @Override
    public List<Roles> list() {
        return rR.findAll();
    }

    @Override
    public Roles insert(Roles r) {
        return rR.save(r);
    }

    @Override
    public Optional<Roles> listId(int id) {
        return rR.findById(id);
    }

    @Override
    public void update(Roles r) {
        rR.save(r);
    }

    @Override
    public void delete(int id) {
        rR.deleteById(id);
    }
}
