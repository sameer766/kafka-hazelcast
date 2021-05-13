package com.sameer.kafka.model;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(keyspace = "hazelcast", name = "employee",
       readConsistency = "ONE",
       writeConsistency = "ONE")
public class Employee implements Serializable {
  private static final long serialVersionUID = 238984982L;

  @PartitionKey
  private Integer empId;
  private String firstName;
  private String lastName;
}
