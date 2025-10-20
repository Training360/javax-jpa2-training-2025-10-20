package employees;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Sql(statements = "delete from employees")
class EmployeesRepositoryIT {

    @Autowired
    EmployeesRepository repository;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    EntityManager entityManager;

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
    void findByNaturalId() {
        // https://www.baeldung.com/spring-boot-hibernate-natural-ids
        var employee = new Employee("abc123", "John");
        repository.save(employee);

        var loaded = entityManager.unwrap(Session.class)
                .byNaturalId(Employee.class)
                .using("cardNumber", "abc123")
                .load();

        assertEquals("John", loaded.getName());
    }

}
