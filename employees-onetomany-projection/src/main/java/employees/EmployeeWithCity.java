package employees;

/**
 * DTO for {@link Employee}
 */
public record EmployeeWithCity(long employeeId, String employeeName, String city) {
}
