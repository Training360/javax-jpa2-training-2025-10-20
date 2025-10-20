package employees;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Slf4j
@RequiredArgsConstructor
public class EmployeesController {

    private final EmployeesService employeesService;

    @GetMapping
    public List<EmployeeResource> listEmployees() {
        return employeesService.listEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeResource findEmployeeById(@PathVariable("id") long id) {
        return employeesService.findEmployeeById(id);
    }

    @PostMapping
    public ResponseEntity<EmployeeResource> createEmployee(@Valid @RequestBody EmployeeResource command, UriComponentsBuilder builder) {
        var resource = employeesService.createEmployee(command);
        return ResponseEntity.created(builder.path("/api/employees/{id}").buildAndExpand(resource.id()).toUri()).body(resource);
    }

    @PutMapping("/{id}")
    public EmployeeResource updateEmployee(@PathVariable("id") long id, @RequestBody EmployeeResource command) {
        if (id != command.id()) {
            throw new IllegalArgumentException("ID in path and body must be the same: %d != %d".formatted(id, command.id()));
        }
        return employeesService.updateEmployee(id, command);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable("id") long id) {
        employeesService.deleteEmployee(id);
    }

}
