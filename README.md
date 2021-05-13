# kafka-hazelcast
Taking data from stream into kafka and then putting it on hazelcast cache which in turn will update it into cassandra aynchronously

At the present time the project takes data from REST api into kafka stream which will then get updated to hazelcast cache. HazelCast Mapstore implementation is used to 
asynchronously push data into the cassandra DB.
