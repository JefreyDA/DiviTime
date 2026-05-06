package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.UserRole;
import pe.edu.upc.divitime.repositories.IUserRoleRepository;
import pe.edu.upc.divitime.servicesinterfaces.IUserRoleService;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleServiceImplement implements IUserRoleService {
    @Autowired
    private IUserRoleRepository urR;


    @Override
    public Optional<UserRole> listId(int id) {
        return urR.findById(id);
    }

    @Override
    public UserRole insert(UserRole uR) {
        return urR.save(uR);
    }

    @Override
    public void detele(int id) {
        urR.deleteById(id);
    }

    @Override
    public boolean existsByUserAndRole(Integer userId, Integer roleId) {
        return urR.existsByUser_IdUserAndRole_IdRole(userId, roleId);
    }

    @Override
    public void update(UserRole userRole) {
        urR.save(userRole);
    }
}
