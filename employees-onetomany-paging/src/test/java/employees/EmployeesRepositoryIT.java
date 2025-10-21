package employees;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Sql(statements = {"delete from addresses", "delete from employees"})
class EmployeesRepositoryIT {

    @Autowired
    EmployeesRepository repository;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    AddressRepository addressRepository;

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
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void saveWithAddresses() {
        for (int i = 0; i < 10; i++) {
            var employee = new Employee("John");
            employee.addAddress(new Address("Budapest"));
            employee.addAddress(new Address("Peking"));
            repository.save(employee);
        }

        System.out.println("***");
//        var loaded = repository.findById(employee.getId()).orElseThrow();
//        System.out.println(loaded.getName());
//        System.out.println(loaded.getAddresses().getFirst().getCity());

//        transactionTemplate.executeWithoutResult(status -> {
//            var employees = repository.findAll();
//            for (Employee employee : employees) {
//                System.out.println(employee.getName() + " " + employee.getAddresses().getFirst().getCity());
//            }
//        });

        var employees = repository.findAllWithAddresses(Pageable.unpaged());
        for (Employee employee : employees) {
            System.out.println(employee.getName() + " " + employee.getAddresses().getFirst().getCity());
        }


    }

    @Test
    @Commit
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void findAllWithEntityGraph() {
        for (int i = 0; i < 10; i++) {
            var employee = new Employee("John");
            employee.addAddress(new Address("Budapest"));
            employee.addAddress(new Address("Peking"));
            repository.save(employee);
        }

        var employees = repository.findAllWithAddressesWithEntityGraph();
        for (Employee employee : employees) {
            System.out.println(employee.getName() + " " + employee.getAddresses().getFirst().getCity());
        }
    }

    @Test
    @Commit
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
   void addAddressToExistingEmployee()  {
        var employee = new Employee("John");
        employee.addAddress(new Address("Budapest"));
        employee.addAddress(new Address("Peking"));
        repository.save(employee);

        transactionTemplate.executeWithoutResult(status -> {
//            var loaded = repository.findById(employee.getId()).orElseThrow();
            var loaded = repository.getReferenceById(employee.getId());
            System.out.println(loaded.getClass().getName());
//            System.out.println(loaded.getAddresses().getClass().getName()); // org.hibernate.collection.spi.PersistentBag
//            loaded.addAddress(new Address("London"));
            var address = new Address("London");
            address.setEmployee(loaded);
            addressRepository.save(address);
//            repository.save(loaded);
        });
    }

    // POST /api/employees/{id}/addresses

    @Test
    @Commit
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void paging() {
        for (int i = 0; i < 10; i++) {
            var employee = new Employee("John " + i);
            employee.addAddress(new Address("Budapest"));
            employee.addAddress(new Address("Peking"));
            repository.save(employee);
        }

//        var employees = repository.findAll(PageRequest.of(2, 3));
//        for (Employee employee : employees) {
//            System.out.println(employee.getName());
//        }

        var ids = repository.findAllIds(PageRequest.of(2, 3));
        var employees = repository.findAllById(ids);
        for (Employee employee : employees) {
            System.out.println(employee.getName());
        }
    }


}
