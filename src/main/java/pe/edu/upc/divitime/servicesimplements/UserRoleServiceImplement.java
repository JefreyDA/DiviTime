package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.UserRole;
import pe.edu.upc.divitime.repositories.IUserRoleRepository;
import pe.edu.upc.divitime.servicesinterfaces.IUserRoleService;

@Service
public class UserRoleServiceImplement implements IUserRoleService {
    @Autowired
    private IUserRoleRepository urR;


    @Override
    public UserRole insert(UserRole uR) {
        return urR.save(uR);
    }

    @Override
    public void detele(int id) {
        urR.deleteById(id);
    }
}
