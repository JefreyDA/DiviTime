package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.UserFamily;
import pe.edu.upc.divitime.repositories.IUserFamilyRepository;
import pe.edu.upc.divitime.servicesinterfaces.IUserFamilyService;

import java.util.List;
import java.util.Optional;

@Service
public class UserFamilyServiceImplement implements IUserFamilyService {
    @Autowired
    private IUserFamilyRepository ufR;


    @Override
    public List<UserFamily> list() {
        return ufR.findAll();
    }

    @Override
    public UserFamily insert(UserFamily uF) {
        return ufR.save(uF);
    }

    @Override
    public boolean userHasFamily(int id) {
        return ufR.existsByUser_IdUser(id);
    }

    @Override
    public Optional<UserFamily> listId(int id) {
        return ufR.findById(id);
    }

    @Override
    public void delete(int id) {
        ufR.deleteById(id);
    }

    @Override
    public void update(UserFamily userFamily) {
        ufR.save(userFamily);
    }
}
