package employees;

import jakarta.validation.constraints.NotBlank;

public record EmployeeResource(String id, @NotBlank(message = "A név nem lehet üres") String name) {

}
