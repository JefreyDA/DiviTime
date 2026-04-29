package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.divitime.entities.ExpenseType;

@Repository
public interface IExpenseTypeRepository extends JpaRepository<ExpenseType, Integer> {
}
