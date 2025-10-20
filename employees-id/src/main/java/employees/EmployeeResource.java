package employees;

import jakarta.validation.constraints.NotBlank;

public record EmployeeResource(Long id, @NotBlank(message = "A név nem lehet üres") String name) {

}
