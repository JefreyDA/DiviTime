package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.Agreement;
import pe.edu.upc.divitime.repositories.IAgreementRepository;
import pe.edu.upc.divitime.servicesinterfaces.IAgreementService;

import java.util.List;
import java.util.Optional;

@Service
public class AgreementServiceImplement implements IAgreementService {
    @Autowired
    private IAgreementRepository aR;

    @Override
    public List<Agreement> list() {
        return aR.findAll();
    }

    @Override
    public Agreement insert(Agreement a) {
        return aR.save(a);
    }

    @Override
    public Optional<Agreement> listId(int id) {
        return aR.findById(id);
    }

    @Override
    public void update(Agreement a) {
        aR.save(a);
    }

    @Override
    public void delete(int id) {
        aR.deleteById(id);
    }
    @Override
    public List<Agreement> listAgreementsByFamilyJPQL(int idFamily) {
        return aR.listAgreementsByFamilyJPQL(idFamily);
    }
}