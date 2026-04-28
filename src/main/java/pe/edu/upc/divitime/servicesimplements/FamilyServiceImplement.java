package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.Family;
import pe.edu.upc.divitime.repositories.IFamilyRepository;
import pe.edu.upc.divitime.servicesinterfaces.IFamilyService;

import java.util.Optional;

@Service
public class FamilyServiceImplement implements IFamilyService {
    @Autowired
    private IFamilyRepository fR;


    @Override
    public Optional<Family> listId(int id) {
        return fR.findById(id);
    }
}
