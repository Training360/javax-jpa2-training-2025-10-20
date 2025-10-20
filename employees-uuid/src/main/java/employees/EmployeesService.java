package employees;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class EmployeesService {

    private EmployeesRepository repository;

    public List<EmployeeResource> listEmployees() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public EmployeeResource findEmployeeById(String id) {
        return toDto(repository.findById(id).orElseThrow(notFountException(id)));
    }

    public EmployeeResource createEmployee(EmployeeResource command) {
        Employee employee = new Employee(command.name());
        repository.save(employee);
        return toDto(employee);
    }

    @Transactional
    public EmployeeResource updateEmployee(String id, EmployeeResource command) {
        Employee employee = repository.findById(id).orElseThrow(notFountException(id));
        employee.setName(command.name());
        return toDto(employee);
    }

    public void deleteEmployee(String id) {
        repository.deleteById(id);
    }

    private EmployeeResource toDto(Employee employee) {
        return new EmployeeResource(employee.getId(), employee.getName());
    }

    private Supplier<EmployeeNotFoundException> notFountException(String id) {
        return () -> new EmployeeNotFoundException("Employee not found with id: %d".formatted(id));
    }

}
