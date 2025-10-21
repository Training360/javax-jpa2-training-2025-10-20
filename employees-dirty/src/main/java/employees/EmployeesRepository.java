package employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeesRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e")
    <T> List<T> findAllWithType(Class<T> type);

    @Query(value = "select id, emp_name as name from employees", nativeQuery = true)
    List<EmployeeResource> findAllWithNativeQuery();

}
