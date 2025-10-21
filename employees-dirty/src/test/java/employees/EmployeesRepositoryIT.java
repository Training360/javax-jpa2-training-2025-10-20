package employees;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Sql(statements = "delete from employees")
class EmployeesRepositoryIT {

    @Autowired
    EmployeesRepository repository;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Test
    @Commit
    void saveThenFindAll() {
        var statistics = entityManagerFactory.unwrap(SessionFactory.class).getStatistics();

        repository.save(new Employee("John"));
        statistics.clear();
        var employees = repository.findAll();
        assertThat(employees)
                .extracting(Employee::getName)
                .containsExactly("John");

        assertEquals(1, statistics.getQueryExecutionCount());
    }

    @Test
    @Commit
    void saveThenFindById() {
        transactionTemplate.executeWithoutResult(status -> {
            for (int i = 0; i < 10; i++) {
                var employee = new Employee("John");
                repository.save(employee);
                System.out.println(employee.getId());
            }
        });
    }

}
