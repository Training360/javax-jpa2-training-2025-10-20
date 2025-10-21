package employees;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    // Az Employee ebben a kapcsolatban az inverse side
    // Az Address az owning side, ő felelős a kapcsolatért
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    Set<Address> addresses = new HashSet<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    Set<Phone> phones = new HashSet<>();

    public void addAddress(Address address) {
        addresses.add(address);
        address.setEmployee(this);
    }

    public void addPhone(Phone phone) {
        phones.add(phone);
        phone.setEmployee(this);
    }

    public Employee(String name) {
        this.name = name;
    }
}
