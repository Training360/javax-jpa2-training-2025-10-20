package employees;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Sql(statements = {"delete from parking_place", "delete from employees"})
class EmployeesRepositoryIT {

    @Autowired
    EmployeesRepository repository;

    @Autowired
    ParkingPlaceRepository parkingPlaceRepository;

    @Autowired
    EntityManagerFactory entityManagerFactory;

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
//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void saveWithParkingPlace() {
        repository.save(new Employee("Jack"));

        var employee = new Employee("John");
        repository.save(employee);
        var parkingPlace = new ParkingPlace("jjj");

//        parkingPlace.setEmployee(employee);
//        employee.setParkingPlace(parkingPlace);

        parkingPlace.assignToEmployee(employee);

        parkingPlaceRepository.save(parkingPlace);

        System.out.println("*****");

        var found = repository.findById(employee.getId()).get();
        System.out.println(found.getName());
    }

}
