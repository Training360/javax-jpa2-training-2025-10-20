package employees;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "parking_place")
@NoArgsConstructor
public class ParkingPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String sign;

    @OneToOne
    @MapsId
    private Employee employee;

    public ParkingPlace(String sign) {
        this.sign = sign;
    }

    public void assignToEmployee(Employee employee) {
        this.employee = employee;
        employee.setParkingPlace(this);
    }
}