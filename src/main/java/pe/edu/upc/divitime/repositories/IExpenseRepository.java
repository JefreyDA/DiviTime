package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.divitime.entities.Expense;

import java.util.List;

@Repository
public interface IExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByStatusExpenseTrue();
    List<Expense> findByStatusExpenseFalse();
}
