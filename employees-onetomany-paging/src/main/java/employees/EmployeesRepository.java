package employees;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.stream.Stream;

public interface EmployeesRepository extends JpaRepository<Employee, Long> {

    @Query(value = """
        select e from Employee e left join fetch e.addresses
    """)
    List<Employee> findAllWithAddresses(Pageable pageable);

    @EntityGraph(attributePaths = {"addresses"})
    @Query("select e from Employee e")
    List<Employee> findAllWithAddressesWithEntityGraph();

    @Query("select e.id from Employee e")
    Slice<Long> findAllIds(Pageable pageable);

    @Query("select e from Employee e left join fetch e.addresses where e.id in ?1")
    List<Employee> findAllWithAddressesByIds(List<Long> ids);

    @Query("select new employees.EmployeeResource(e.id, e.name) from Employee e where e.id > :lastId")
    Slice<EmployeeResource> findAllResourcesGreaterThan(long lastId, Pageable pageable);

    @Query("select e from Employee e")
    <T> Stream<T> findAllStream(Class<T> type);
}
