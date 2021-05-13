package com.sameer.kafka.service;

import com.sameer.kafka.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafKaProducerService {
  private static final Logger logger = LoggerFactory.getLogger(KafKaProducerService.class);

  @Value(value = "${topic.name}")
  private String topic;

  @Autowired
  private KafkaTemplate<String, Employee> userKafkaTemplate;

  public Employee publishToKafkaStream(Employee user) {
    logger.info(String.format("Employee created -> %s", user));
    this.userKafkaTemplate.send(topic, user);
    return user;
  }
}
