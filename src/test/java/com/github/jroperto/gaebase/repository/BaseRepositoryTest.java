package com.github.jroperto.gaebase.repository;

import com.github.jroperto.gaebase.model.EntityMock;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Closeable;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BaseRepositoryTest {

    private static final long       ID = 1L;
    private static final long       EXISTING_ID = 100L;
    private static final String     PROPERTY = "PROPERTY";
    private static final String     NEW_PROPERTY = "NEW_PROPERTY";


    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private Closeable objectifyContext;

    private final BaseRepository<EntityMock> baseRepository = new BaseRepository<>(EntityMock.class);


    @Before
    public void setUp() throws Exception {
        helper.setUp();
        objectifyContext = ObjectifyService.begin();
        ObjectifyService.register(EntityMock.class);
    }

    @After
    public void tearDown() throws Exception {
        objectifyContext.close();
        helper.tearDown();

    }

    @Test
    public void thatEntityIsSaved() {
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

        EntityMock entity = new EntityMock()
                .setProperty(PROPERTY);

        Query entityQuery = new Query("EntityMock");
        assertEquals("Datastore should not have entities", 0, ds.prepare(entityQuery).countEntities(withLimit(100)));

        baseRepository.save(entity);


        assertEquals("Datastore should have 1 entity", 1, ds.prepare(entityQuery).countEntities(withLimit(1)));

        Entity datastoreEntity = ds.prepare(entityQuery).asSingleEntity();
        assertNotNull("ID should not be null", datastoreEntity.getKey().getId());
        assertTrue("ID not valid", datastoreEntity.getKey().getId() > 0);
        assertEquals("Property is not the expected", PROPERTY, datastoreEntity.getProperty("property"));
    }

    @Test
    public void thatEntityIsUpdated() {
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

        EntityMock entity = new EntityMock()
                .setId(EXISTING_ID)
                .setProperty(PROPERTY);

        Query entityQuery = new Query("EntityMock");
        assertEquals("Datastore should not have entities", 0, ds.prepare(entityQuery).countEntities(withLimit(100)));

        baseRepository.save(entity);
        assertEquals("Datastore should have 1 entity", 1, ds.prepare(entityQuery).countEntities(withLimit(3)));
        Entity datastoreEntity = ds.prepare(entityQuery).asSingleEntity();
        assertNotNull("ID should not be null", datastoreEntity.getKey().getId());
        assertTrue("ID not valid", datastoreEntity.getKey().getId() > 0);
        assertEquals("ID not expected", EXISTING_ID, datastoreEntity.getKey().getId());
        assertEquals("Property is not the expected", PROPERTY, datastoreEntity.getProperty("property"));

        entity.setProperty(NEW_PROPERTY);
        baseRepository.save(entity);
        assertEquals("Datastore should have 1 entity", 1, ds.prepare(entityQuery).countEntities(withLimit(3)));

        Entity updatedDatastoreEntity = ds.prepare(entityQuery).asSingleEntity();
        assertNotNull("ID should not be null", updatedDatastoreEntity.getKey().getId());
        assertTrue("ID not valid", updatedDatastoreEntity.getKey().getId() > 0);
        assertEquals("ID not expected", EXISTING_ID, updatedDatastoreEntity.getKey().getId());
        assertEquals("Property is not the expected", NEW_PROPERTY, updatedDatastoreEntity.getProperty("property"));
    }

    @Test
    public void thatEntityIsRetrievedUsingKey() {
        EntityMock entity = new EntityMock()
                .setProperty(PROPERTY);

        Key<EntityMock> savedEntityKey = baseRepository.save(entity);

        EntityMock datastoreEntity = baseRepository.get(savedEntityKey);

        assertNotNull("ID should not be null", datastoreEntity.getId());
        assertTrue("ID not valid", datastoreEntity.getId() > 0);
        assertEquals("Property is not the expected", PROPERTY, datastoreEntity.getProperty());
    }

    @Test
    public void thatEntityIsRetrievedUsingId() {
        EntityMock entity = new EntityMock()
                .setProperty(PROPERTY);

        baseRepository.save(entity);

        EntityMock datastoreEntity = baseRepository.get(ID);

        assertNotNull("ID should not be null", datastoreEntity.getId());
        assertTrue("ID not valid", datastoreEntity.getId() > 0);
        assertEquals("Property is not the expected", PROPERTY, datastoreEntity.getProperty());
    }

    @Test
    public void thatEntityIsDeleted() {
        EntityMock entity = new EntityMock()
                .setProperty(PROPERTY);

        baseRepository.save(entity);

        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query articleQuery = new Query("EntityMock");
        assertEquals("Datastore should have 1 entity", 1, ds.prepare(articleQuery).countEntities(withLimit(100)));

        baseRepository.delete(ID);

        assertEquals("Datastore should not have entities", 0, ds.prepare(articleQuery).countEntities(withLimit(100)));

    }

}