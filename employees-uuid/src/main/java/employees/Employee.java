package employees;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NaturalId
    private String cardNumber;

    @Column(name = "emp_name")
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    public Employee(String cardNumber, String name) {
        this.cardNumber = cardNumber;
        this.name = name;
    }
}
