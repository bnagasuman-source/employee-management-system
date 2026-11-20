package com.ems.controller;

import com.ems.dto.EmployeeDto;
import com.ems.dto.StatsDto;
import com.ems.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GET /api/employees            -> all
    // GET /api/employees?search=abc -> search by name, email or department
    @GetMapping
    public List<EmployeeDto> getAll(@RequestParam(required = false) String search) {
        return employeeService.findAll(search);
    }

    @GetMapping("/stats")
    public StatsDto stats() {
        return employeeService.stats();
    }

    @GetMapping("/{id}")
    public EmployeeDto getOne(@PathVariable Long id) {
        return employeeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeDto create(@Valid @RequestBody EmployeeDto dto) {
        return employeeService.create(dto);
    }

    @PutMapping("/{id}")
    public EmployeeDto update(@PathVariable Long id, @Valid @RequestBody EmployeeDto dto) {
        return employeeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }
}
