package referencia.repository.database.implementations;

import javax.inject.Inject;

import referencia.repository.database.contexts.ReferenciaContext;
import referencia.repository.database.interfaces.IDatabaseFactory;

public class DatabaseFactory implements IDatabaseFactory {

    private ReferenciaContext dataContext;

    @Inject
    public DatabaseFactory(ReferenciaContext dataContext) {

        this.dataContext = dataContext;
    }
    
    @Override
    public ReferenciaContext get() {

        return dataContext;
    }

}
