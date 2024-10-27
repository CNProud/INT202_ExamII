package sit.int202.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int202.demo.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
