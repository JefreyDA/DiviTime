package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.Agreement;
import pe.edu.upc.divitime.entities.AgreementType;
import pe.edu.upc.divitime.repositories.IAgreementTypeRepository;
import pe.edu.upc.divitime.servicesinterfaces.IAgreementTypeService;

import java.util.List;
import java.util.Optional;
@Service
public class AgreementTypeServiceImplement implements IAgreementTypeService {
    @Autowired
    private IAgreementTypeRepository aTR;

    @Override
    public List<AgreementType> list() {
        return aTR.findAll();
    }

    @Override
    public AgreementType insert(AgreementType aT) {
        return aTR.save(aT);
    }

    @Override
    public Optional<AgreementType> listId(int id) {
        return aTR.findById(id);
    }

    @Override
    public void update(AgreementType aT) {
        aTR.save(aT);
   }

    @Override
    public void delete(int id) {
        aTR.deleteById(id);

    }
}







