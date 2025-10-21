package employees;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeesRepository extends JpaRepository<Employee, Long> {

    @Query(value = """
        select e from Employee e left join fetch e.addresses
    """)
    List<Employee> findAllWithAddresses(Pageable pageable);

    @EntityGraph(attributePaths = {"addresses"})
    @Query("select e from Employee e")
    List<Employee> findAllWithAddressesWithEntityGraph();

    @Query("select e.id from Employee e")
    List<Long> findAllIds(Pageable pageable);

    @Query("select e from Employee e left join fetch e.addresses where e.id in ?1")
    List<Employee> findAllByIds(List<Long> ids);
}
