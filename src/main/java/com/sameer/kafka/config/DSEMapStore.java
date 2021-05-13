/*
 * Sample implementation of hazelcast mapstore with DSE object mapper.
 * https://docs.datastax.com/en/developer/java-driver-dse/1.5/manual/object_mapper/
 * http://docs.hazelcast.org/docs/latest/manual/html-single/#loading-and-storing-persistent-data
 */

package com.sameer.kafka.config;

import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import com.hazelcast.core.MapStore;
import com.hazelcast.core.PostProcessingMapStore;
import com.sameer.kafka.model.Employee;
import java.util.*;


public class DSEMapStore implements MapStore<Integer, Employee>, PostProcessingMapStore {
  private Dse dse = new Dse();
  private Mapper<Employee> mapper = dse.getMapper(Employee.class);
  private EmployeeAccessor userAccessor = dse.getAccessor();


  public void store(Integer key, Employee user) {
    mapper.save(user);
  }


  public void storeAll(Map<Integer, Employee> map) {
    Collection<Employee> users = map.values();
    for (Employee user : users) {
      mapper.save(user);
    }
  }

  public void delete(Integer name) {
    mapper.delete(name);
  }

  public void deleteAll(Collection<Integer> keys) {
    for (Integer name : keys) {
      mapper.delete(name);
    }
  }

  public Employee load(Integer key) {
    return mapper.get(key);
  }

  public Map<Integer, Employee> loadAll(Collection<Integer> names) {
    Map<Integer, Employee> result = new HashMap<Integer, Employee>();
    for (Integer name : names) {
      result.put(name, mapper.get(name));
    }
    return result;
  }

  public Iterable<Integer> loadAllKeys() {
    System.out.println("load all keys");
    Set<Integer> result = new HashSet<Integer>();
    Result<Employee> users = userAccessor.getAll();
    for (Employee user : users) {
      System.out.println("Employee : " + user.getFirstName());
      result.add(user.getEmpId());
    }
    return result;
  }
}
