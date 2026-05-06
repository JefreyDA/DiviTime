package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.UserRole;

import java.util.Optional;


public interface IUserRoleService {
    public Optional<UserRole> listId(int id);
    public UserRole insert(UserRole uR);
    public void detele(int id);
    public boolean existsByUserAndRole(Integer userId, Integer roleId);
    public void update(UserRole userRole);
}
