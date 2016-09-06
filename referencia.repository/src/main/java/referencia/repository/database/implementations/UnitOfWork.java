package referencia.repository.database.implementations;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;

import referencia.domain.interfaces.IUnitOfWork;
import referencia.repository.database.contexts.ReferenciaContext;
import referencia.repository.database.interfaces.IDatabaseFactory;
import referencia.repository.mapper.implementations.entities.core.BaseEntity;

public class UnitOfWork implements IUnitOfWork {

    private static final String ID_NOT_NULL = "id not null";

    private List<BaseEntity<?>> newObjects = new ArrayList<>();

    private List<BaseEntity<?>> dirtyObjects = new ArrayList<>();

    private List<BaseEntity<?>> removedObjects = new ArrayList<>();

    private ReferenciaContext context;

    @Inject
    public UnitOfWork(IDatabaseFactory databaseFactory) {

        context = databaseFactory.get();
    }

    @Override
    public void commit() {

        context.commit();
    }

    public void registerNew(BaseEntity<?> obj) {

        Assert.assertNotNull(ID_NOT_NULL, obj.getId());

        Assert.assertTrue("object not dirty", !dirtyObjects.contains(obj));

        Assert.assertTrue("object not removed", !removedObjects.contains(obj));

        Assert.assertTrue("object not already registered new", !newObjects.contains(obj));

        newObjects.add(obj);
    }

    public void registerDirty(BaseEntity<?> obj) {

        Assert.assertNotNull(ID_NOT_NULL, obj.getId());

        Assert.assertTrue("object not removed", !removedObjects.contains(obj));

        if (!dirtyObjects.contains(obj) && !newObjects.contains(obj)){

            dirtyObjects.add(obj);
        }
    }

    public void registerRemoved(BaseEntity<?> obj) {

        Assert.assertNotNull(ID_NOT_NULL, obj.getId());

        if (newObjects.remove(obj))
            return;

        dirtyObjects.remove(obj);

        if (!removedObjects.contains(obj)) {

            removedObjects.add(obj);
        }

    }

    public void registerClean(BaseEntity<?> obj) {

        Assert.assertNotNull(ID_NOT_NULL, obj.getId());

    }
}
