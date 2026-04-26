package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.divitime.entities.ContractType;

public interface IContractRepository extends JpaRepository<ContractType,Integer> {
}
