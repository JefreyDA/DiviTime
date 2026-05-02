package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.Agreement;
import pe.edu.upc.divitime.entities.AgreementType;

import java.util.List;
import java.util.Optional;

public interface IAgreementTypeService {
    public List<AgreementType> list();
    public AgreementType insert(AgreementType aT);
    public Optional<AgreementType>listId(int id);
    public void update(AgreementType aT);
    public void delete(int id);

}
