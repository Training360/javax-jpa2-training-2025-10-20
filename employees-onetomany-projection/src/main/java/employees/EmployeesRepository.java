package employees;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeesRepository extends JpaRepository<Employee, Long> {

    @Query("""
        select e from Employee e left join fetch e.addresses
    """)
    List<Employee> findAllWithAddresses();

    @EntityGraph(attributePaths = {"addresses"})
    @Query("select e from Employee e")
    List<Employee> findAllWithAddressesWithEntityGraph();
}
