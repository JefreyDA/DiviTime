package pe.edu.upc.divitime.servicesinterfaces;

import pe.edu.upc.divitime.entities.ContractType;

import java.util.List;
import java.util.Optional;

public interface IContractService {
    public List<ContractType> list();
    public ContractType insert(ContractType c);
    public Optional<ContractType>listId(int id);
    public void update(ContractType c);
    public void delete(int id);

}
