package employees;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp_name")
    private String name;

    @OneToOne(mappedBy = "employee")
    private ParkingPlace parkingPlace;

    public Employee(String name) {
        this.name = name;
    }
}
