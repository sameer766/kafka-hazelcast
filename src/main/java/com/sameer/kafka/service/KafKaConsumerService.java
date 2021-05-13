package com.sameer.kafka.service;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.sameer.kafka.model.Employee;
import com.sameer.kafka.repo.CassandraRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafKaConsumerService {
  private final Logger logger = LoggerFactory.getLogger(KafKaConsumerService.class);
  public static boolean flag=false;

  @Autowired
  EmployeeService employeeService;

  @Autowired
  CassandraRepo cassandraRepo;

  public static Integer count =0;

  @KafkaListener(topics = "${topic.name}",
                 groupId = "${group.id}",
                 containerFactory = "userKafkaListenerContainerFactory")
  public void consume(Employee user) {
    logger.info(String.format("User created -> %s", user));
    if(!flag) {
        count = Math.toIntExact(cassandraRepo.count());
      flag = true;
    }
    user.setEmpId(count++);
    employeeService.save(user);
  }
}
