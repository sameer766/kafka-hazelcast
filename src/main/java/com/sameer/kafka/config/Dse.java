package com.sameer.kafka.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class Dse {
  private MappingManager manager;

  public MappingManager getManager() {
    if (manager == null) {
      manager = init();
    }
    return manager;
  }

  private MappingManager init() {
    Cluster cluster = Cluster.builder().withoutJMXReporting().addContactPoint("127.0.0.1").build();
    Session session = cluster.connect("hazelcast");
    return new MappingManager(session);
  }

  public <T> Mapper<T> getMapper(Class<T> clazz) {
    return getManager().mapper(clazz);
  }

  public EmployeeAccessor getAccessor() {
    return manager.createAccessor(EmployeeAccessor.class);
  }
}
