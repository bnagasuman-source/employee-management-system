package com.ems.service;

import com.ems.model.Department;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentService(DepartmentRepository departmentRepository,
                             EmployeeRepository employeeRepository) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department create(Department department) {
        String name = department.getName().trim();
        if (departmentRepository.existsByNameIgnoreCase(name)) {
            throw new BadRequestException("Department already exists");
        }
        department.setId(null);
        department.setName(name);
        return departmentRepository.save(department);
    }

    public void delete(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Department not found with id " + id);
        }
        if (employeeRepository.existsByDepartmentId(id)) {
            throw new BadRequestException("Cannot delete a department that still has employees");
        }
        departmentRepository.deleteById(id);
    }
}
