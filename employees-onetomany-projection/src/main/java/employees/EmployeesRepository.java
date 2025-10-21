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

    @Query("select new employees.EmployeeResource(e.id, e.name) from Employee e")
    List<EmployeeResource> findAllResources();

    @Query("select e from Employee e")
    <T> List<T> findAllDto(Class<T> dtoClass);

    // emp_id, emp_name, address_city
    @Query("""
            select new employees.EmployeeWithCity(e.id, e.name, a.city)
                from Employee e left join e.addresses a
           """)
    List<EmployeeWithCity> findAllStructuredDto();
}
