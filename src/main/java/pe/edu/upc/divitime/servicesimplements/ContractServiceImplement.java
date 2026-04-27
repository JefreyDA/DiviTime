package pe.edu.upc.divitime.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.divitime.entities.ContractType;

import pe.edu.upc.divitime.repositories.IContractRepository;
import pe.edu.upc.divitime.servicesinterfaces.IContractService;

import java.util.List;
import java.util.Optional;

@Service
public class ContractServiceImplement implements IContractService {
    @Autowired
    private IContractRepository cR;
    @Override
    public List<ContractType> list() {
        return cR.findAll();
    }

    @Override
    public ContractType insert(ContractType c) {
        return cR.save(c);
    }

    @Override
    public Optional<ContractType> listId(int id) {
        return cR.findById(id);
    }

    @Override
    public void update(ContractType c) {
        cR.save(c);
    }

    @Override
    public void delete(int id) {
        cR.deleteById(id);

    }
}
