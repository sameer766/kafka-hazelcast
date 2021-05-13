package com.sameer.kafka.config;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Query;
import com.sameer.kafka.model.Employee;

@Accessor
public interface EmployeeAccessor {
    @Query("SELECT * FROM user")
    Result<Employee> getAll();
}
