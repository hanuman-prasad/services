package edu.elearning.cassandra.repository.ops;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import edu.elearning.cassandra.projection.EntityRegistry;
import edu.elearning.cassandra.repository.utils.FieldManipulator;
import edu.elearning.cassandra.serializer.AsteriModelSerializer;
import edu.elearning.se.AsteriModel;
import org.apache.cassandra.utils.UUIDGen;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CassandraInsertOps implements CassandraOps {

    private final Session session;

    private static PreparedStatement preparedStaxdata;
    private static PreparedStatement preparedProjection;
    private static final String STAXDATA_QUERY = "INSERT INTO local_entities.staxdata (key, payload) VALUES (?, ?)";
    private static final String PROJECTION_QUERY = "INSERT INTO local_entities.entity_projection_1 (entity_class, index_name, index_value, entity_id, key) VALUES (?, ?, ?, ?, ?)";

    public CassandraInsertOps(Session session) {
        this.session = session;
    }

    @Override
    public void insert(Class<? extends AsteriModel> kClass, AsteriModel model) {
        String modelName = kClass.getSimpleName();

        List<BoundStatement> statements = new ArrayList<>(2);

        UUID key = UUIDGen.getTimeUUID();
        statements.add(createStaxdataInsertQuery(modelName, model, key));

        statements.addAll(createProjectionQuery(modelName, model, key));

        statements.stream()
                .forEach(stmt -> {
                    session.execute(stmt);
                });

    }

    private List<BoundStatement> createProjectionQuery(String modelName, AsteriModel model, UUID key) {

        if (preparedProjection == null) {
            preparedProjection = session.prepare(PROJECTION_QUERY);
        }

        List<String> projections = getProjectionForModel(modelName);

        List<BoundStatement> statements = new ArrayList<>(projections.size());

        projections.stream()
                .forEach(p -> {
                            Object indexValue = FieldManipulator.getFieldValue(model, p);
                            if(indexValue == null) {
                                return;
                            }

                            BoundStatement bind = preparedProjection.bind(modelName, p, indexValue, model.getId(), key);
                            statements.add(bind);
                        }
                );


        return statements;
    }

    private List<String> getProjectionForModel(String model) {

        return EntityRegistry.projections.getOrDefault(model, Collections.emptyList());

    }

    private BoundStatement createStaxdataInsertQuery(String modelName, AsteriModel model, UUID key) {

        if (preparedStaxdata == null) {
            preparedStaxdata = session.prepare(STAXDATA_QUERY);
        }


        ByteBuffer serialize = AsteriModelSerializer.serialize(model);
        return preparedStaxdata.bind(key, serialize);
    }
}