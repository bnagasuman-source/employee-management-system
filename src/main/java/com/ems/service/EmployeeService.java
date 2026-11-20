package com.ems.service;

import com.ems.dto.EmployeeDto;
import com.ems.dto.StatsDto;
import com.ems.model.Department;
import com.ems.model.Employee;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional(readOnly = true)
    public List<EmployeeDto> findAll(String search) {
        List<Employee> list = (search == null || search.isBlank())
                ? employeeRepository.findAll()
                : employeeRepository.search(search.trim());
        return list.stream().map(this::toDto).toList();
    }

    @Transactional(readOnly = true)
    public EmployeeDto findById(Long id) {
        return toDto(getOrThrow(id));
    }

    public EmployeeDto create(EmployeeDto dto) {
        if (employeeRepository.existsByEmail(dto.getEmail().trim())) {
            throw new BadRequestException("An employee with this email already exists");
        }
        Employee employee = new Employee();
        apply(employee, dto);
        return toDto(employeeRepository.save(employee));
    }

    public EmployeeDto update(Long id, EmployeeDto dto) {
        Employee employee = getOrThrow(id);
        if (employeeRepository.existsByEmailAndIdNot(dto.getEmail().trim(), id)) {
            throw new BadRequestException("Another employee already uses this email");
        }
        apply(employee, dto);
        return toDto(employeeRepository.save(employee));
    }

    public void delete(Long id) {
        employeeRepository.delete(getOrThrow(id));
    }

    @Transactional(readOnly = true)
    public StatsDto stats() {
        long count = employeeRepository.count();
        BigDecimal total = employeeRepository.totalSalary();
        BigDecimal avg = count == 0
                ? BigDecimal.ZERO
                : total.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
        return new StatsDto(count, departmentRepository.count(), total, avg);
    }

    // ---- helpers ----
    private Employee getOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    private void apply(Employee e, EmployeeDto dto) {
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new BadRequestException("Selected department does not exist"));
        e.setFirstName(dto.getFirstName().trim());
        e.setLastName(dto.getLastName().trim());
        e.setEmail(dto.getEmail().trim());
        e.setPhone(dto.getPhone());
        e.setSalary(dto.getSalary());
        e.setHireDate(dto.getHireDate());
        e.setDepartment(department);
    }

    private EmployeeDto toDto(Employee e) {
        EmployeeDto dto = new EmployeeDto();
        dto.setId(e.getId());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setEmail(e.getEmail());
        dto.setPhone(e.getPhone());
        dto.setSalary(e.getSalary());
        dto.setHireDate(e.getHireDate());
        if (e.getDepartment() != null) {
            dto.setDepartmentId(e.getDepartment().getId());
            dto.setDepartmentName(e.getDepartment().getName());
        }
        return dto;
    }
}
