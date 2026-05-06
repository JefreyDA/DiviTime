package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.UserRole;

public interface IUserRoleService {
    public UserRole insert(UserRole uR);
    public void detele(int id);
}
