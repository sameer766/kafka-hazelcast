package com.sameer.kafka.service;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.sameer.kafka.model.Employee;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  HazelcastInstance hazelcast = Hazelcast.newHazelcastInstance(new Config());

  @Value("${hazelcast.map}")
  public String hazelcast_map;

  public void save(Employee employee) {
    IMap<Integer, Employee> employeeIMap = hazelcast.getMap(hazelcast_map);
    employeeIMap.put(employee.getEmpId(), employee);
  }

  public List<Employee> getEmployer() {
    List<Employee> response = new ArrayList<>();
    System.out.println("Hit db first time");
    IMap<String, Employee> employeeIMap = hazelcast.getMap(hazelcast_map);
    employeeIMap.entrySet().forEach((key) -> response.add(key.getValue()));
    return response;
  }

  public Employee getParticularEmployer(Integer id) {
    System.out.println("Hit db first time");
    IMap<Integer, Employee> employeeIMap = hazelcast.getMap(hazelcast_map);
    return employeeIMap.get(id);
  }


  @CacheEvict(value = "userCache", allEntries = true)
  public void delete(Integer id) {
    System.out.println("Hit db first time");
    IMap<Integer, Employee> employeeIMap = hazelcast.getMap(hazelcast_map);
    employeeIMap.remove(id);
  }

}
