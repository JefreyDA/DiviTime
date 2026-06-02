package pe.edu.upc.divitime.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.divitime.entities.Expense;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findByStatusExpenseTrue();
    List<Expense> findByStatusExpenseFalse();

    List<Expense> findByUser_IdUser(int idUser);

    @Query(value = "SELECT \n" +
            "et.name_expense_type AS tipo_gasto,\n" +
            "SUM(e.amount_expense) AS total_gastado,\n" +
            "ROUND(((SUM(e.amount_expense) * 100.0) /\n" +
            "\tNULLIF((\n" +
            "\t\tSELECT SUM(e2.amount_expense)\n" +
            "\t\tFROM expense e2\n" +
            "\t\t\n" +
            "        INNER JOIN tb_user u2\n" +
            "        ON e2.id_user = u2.id_user\n" +
            "\n" +
            "        INNER JOIN roles r2\n" +
            "        ON u2.id_role = r2.id_role\n" +
            "\n" +
            "        WHERE u2.id_family = (\n" +
            "        \tSELECT id_family\n" +
            "        \tFROM tb_user\n" +
            "        \tWHERE id_user = :idUser)\n" +
            "\n" +
            "        AND u2.status_user = true\n" +
            "        AND e2.status_expense = true\n" +
            "        AND r2.name_role IN ('PADRE', 'TUTOR_LEGAL')\n" +
            "\t\t), 0))::NUMERIC, 2) AS porcentaje_total\n" +
            "FROM expense e\n" +
            "\n" +
            "INNER JOIN expense_type et\n" +
            "ON e.id_expense_type = et.id_expense_type\n" +
            "\n" +
            "INNER JOIN tb_user u\n" +
            "ON e.id_user = u.id_user\n" +
            "\n" +
            "INNER JOIN roles r\n" +
            "ON u.id_role = r.id_role\n" +
            "\n" +
            "WHERE u.id_family = (\n" +
            "    SELECT id_family\n" +
            "    FROM tb_user\n" +
            "    WHERE id_user = :idUser)\n" +
            "\t\n" +
            "AND u.status_user = true\n" +
            "AND e.status_expense = true\n" +
            "AND r.name_role IN ('PADRE', 'TUTOR_LEGAL')\n" +
            "GROUP BY et.name_expense_type\n" +
            "ORDER BY porcentaje_total DESC;", nativeQuery = true)
    List<Object[]> expensesAmountAndPercentageByType(@Param("idUser") int idUser);

    @Query(value = "SELECT \n" +
            "    u.id_user,\n" +
            "    u.name_user,\n" +
            "    SUM(e.amount_expense) AS total_gastado\n" +
            "FROM expense e\n" +
            "\n" +
            "INNER JOIN tb_user u\n" +
            "ON e.id_user = u.id_user\n" +
            "\n" +
            "INNER JOIN roles r\n" +
            "ON u.id_role = r.id_role\n" +
            "\n" +
            "WHERE u.id_family = (\n" +
            "    SELECT id_family\n" +
            "    FROM tb_user\n" +
            "    WHERE id_user = :idUser)\n" +
            "\t\n" +
            "AND u.status_user = true\n" +
            "AND e.status_expense = true\n" +
            "AND r.name_role IN ('PADRE', 'TUTOR_LEGAL')\n" +
            "\n" +
            "AND EXTRACT(MONTH FROM e.date_expense) = :mes\n" +
            "AND EXTRACT(YEAR FROM e.date_expense) = :anio\n" +
            "\n" +
            "GROUP BY u.id_user, u.name_user\n" +
            "ORDER BY total_gastado DESC;", nativeQuery = true)
    List<Object[]> totalExpensesByFamilyMembersOnMonthAndYear(@Param("idUser") int idUser, @Param("mes") int mes, @Param("anio") int anio);

    @Query(value = "SELECT " +
            " u.name_user AS usuario, " +
            " SUM(e.amount_expense) AS totalGastado " +
            " FROM expense e " +
            " INNER JOIN tb_user u ON e.id_user = u.id_user " +
            " WHERE e.status_expense = true " +
            " AND e.id_family = :familyId " +
            " AND e.date_expense BETWEEN :startDate AND :endDate " +
            " GROUP BY u.id_user, u.name_user " +
            " ORDER BY totalGastado DESC",
            nativeQuery = true)
    List<Object[]> compareExpensesByFamilyAndPeriod(
            @Param("familyId") int familyId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
