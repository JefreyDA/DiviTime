package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.Agreement;

import java.util.List;
import java.util.Optional;

public interface IAgreementService {
    public List<Agreement> list();
    public Agreement insert(Agreement a);
    public Optional<Agreement> listId(int id);
    public void update(Agreement a);
    public void delete(int id);
    public List<Agreement> listAgreementsByFamilyJPQL(int idFamily);
}
