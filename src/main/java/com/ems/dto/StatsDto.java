package com.ems.dto;

import java.math.BigDecimal;

public record StatsDto(long totalEmployees, long totalDepartments,
                       BigDecimal totalPayroll, BigDecimal averageSalary) {
}
