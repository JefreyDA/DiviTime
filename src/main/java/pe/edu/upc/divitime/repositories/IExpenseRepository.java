package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.upc.divitime.entities.Expense;

import java.util.List;

@Repository
public interface IExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByStatusExpenseTrue();
    List<Expense> findByStatusExpenseFalse();

    @Query(value = "SELECT\n" +
            "    et.name_expense_type AS \"Tipo de gasto\",\n" +
            "    ROUND(\n" +
            "        (SUM(e.amount_expense) * 100.0 / \n" +
            "        (SELECT SUM(amount_expense) \n" +
            "         FROM expense \n" +
            "         WHERE status_expense = true))::numeric, 2\n" +
            "\t\t ) AS porcentaje\n" +
            " FROM expense e\n" +
            " INNER JOIN expense_type et\n" +
            "    ON e.id_expense_type = et.id_expense_type\n" +
            " WHERE e.status_expense = true\n" +
            " GROUP BY et.name_expense_type;", nativeQuery = true)
    List<Object[]> expensesPercentageByType();

    @Query(value = "SELECT\n" +
            "    et.name_expense_type AS \"Tipo de gasto\",\n" +
            "    SUM(e.amount_expense)::numeric AS total\n" +
            " FROM expense e\n" +
            " INNER JOIN expense_type et\n" +
            "    ON e.id_expense_type = et.id_expense_type\n" +
            " WHERE e.status_expense = true\n" +
            " GROUP BY et.name_expense_type;", nativeQuery = true)
    List<Object[]> expensesAmountByType();

    @Query(value = "SELECT\n" +
            "    u.name_user AS Nombre,\n" +
            "    EXTRACT(MONTH FROM e.date_expense) AS Mes,\n" +
            "    SUM(e.amount_expense) AS \"Total Gastado\"\n" +
            " FROM expense e\n" +
            " INNER JOIN tb_user u\n" +
            " ON e.id_user = u.id_user\n" +
            " WHERE e.status_expense = true\n" +
            " AND EXTRACT(YEAR FROM e.date_expense) = :year\n" +
            " AND e.id_family = :familyId\n" +
            " GROUP BY u.name_user, Mes\n" +
            " ORDER BY Mes;", nativeQuery = true)
    List<Object[]> amountExpensedByUserOnAYearMonthAndFamiliy(int year, int familyId);
}
