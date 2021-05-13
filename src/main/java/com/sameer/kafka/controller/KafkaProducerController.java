package com.sameer.kafka.controller;

import com.sameer.kafka.model.Employee;
import com.sameer.kafka.model.ResponseStatus;
import com.sameer.kafka.service.EmployeeService;
import com.sameer.kafka.service.KafKaConsumerService;
import com.sameer.kafka.service.KafKaProducerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaProducerController {
  @Autowired
  KafKaConsumerService kafKaConsumerService;
  @Autowired
  KafKaProducerService kafKaProducerService;
  @Autowired
  CacheManager cacheManager;
  @Autowired
  EmployeeService employeeService;


  @PostMapping(value = "/createUser")
  public com.sameer.kafka.model.ResponseStatus sendMessageToKafkaTopic(@RequestBody Employee employee) {
    Employee saveEmployee = kafKaProducerService.publishToKafkaStream(employee);
    return new com.sameer.kafka.model.ResponseStatus("SUCCESS", saveEmployee);
  }

  @GetMapping("/getAll")
  public List<Employee> getEmployer() {
    final List<Employee> employee = employeeService.getEmployer();
    return employee;
  }

  @GetMapping("/get/{id}")
  public Employee getEmployer(@PathVariable Integer id) {
    return employeeService.getParticularEmployer(id);

  }

  @DeleteMapping("/delete/{id}")
  public com.sameer.kafka.model.ResponseStatus delete(@PathVariable Integer id) {
     employeeService.delete(id);
     return new ResponseStatus("SUCCESS", null);
  }


  @GetMapping("/clearCache")
  public void evictAllCacheValues() {
    for (String name : cacheManager.getCacheNames()) {
      cacheManager.getCache(name).clear();
    }
  }
}