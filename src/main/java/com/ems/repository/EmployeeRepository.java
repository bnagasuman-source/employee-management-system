package com.ems.repository;

import com.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByDepartmentId(Long departmentId);

    @Query("""
            SELECT e FROM Employee e LEFT JOIN e.department d
            WHERE LOWER(e.firstName) LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(e.lastName)  LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(e.email)     LIKE LOWER(CONCAT('%', :q, '%'))
               OR LOWER(d.name)      LIKE LOWER(CONCAT('%', :q, '%'))
            ORDER BY e.id DESC
            """)
    List<Employee> search(@Param("q") String q);

    @Query("SELECT COALESCE(SUM(e.salary), 0) FROM Employee e")
    BigDecimal totalSalary();
}
