package employees;

import jakarta.validation.constraints.NotBlank;

/**
 *
 * DTO for {@link Employee}
 * @param id
 * @param name
 */
public record EmployeeResource(Long id, @NotBlank(message = "A név nem lehet üres") String name) {

}
