package edu.elearning.cassandra.connection;

import com.datastax.driver.core.Session;

public class CassandraConnectionFactory {

    public static CassandraConnectionManager getCassandraConnectionManager() {
        CassandraConnectionManager client = new CassandraConnectionManager();
        client.connect("127.0.0.1", 9042);
        return client;
    }
}
