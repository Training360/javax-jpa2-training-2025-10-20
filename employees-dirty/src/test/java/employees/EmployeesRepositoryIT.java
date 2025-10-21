package employees;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    JdbcTemplate jdbcTemplate;

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

    @Test
    @Commit
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void saveAndSelect() {
        transactionTemplate.executeWithoutResult(status -> {
            // PC open
            var employee = new Employee("John");
            System.out.println("Save employee");
            repository.save(employee);
            System.out.println("Update employee");
            employee.setName("Jack");
            System.out.println("Find all employees");
            // flush: insert, update
//            var employees = repository.findAllWithType(EmployeeResource.class);
//            var employees = repository.findAllWithNativeQuery();

            repository.flush();
            var employees = jdbcTemplate.query("select id, emp_name as name from employees",
                    (rs, rowNum) -> new EmployeeResource(
                            rs.getLong("id"),
                            rs.getString("name")
                    ));

            for (var e : employees) {
                System.out.println(e.name());
            }
            // PC closed
        });
    }

}
