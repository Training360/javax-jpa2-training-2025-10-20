package employees;

import java.util.List;

public record EmployeeDto(long id, String name, List<String> cities) {
}
