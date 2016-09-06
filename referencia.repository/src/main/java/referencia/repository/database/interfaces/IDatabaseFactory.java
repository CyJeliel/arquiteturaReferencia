package referencia.repository.database.interfaces;

import referencia.repository.database.contexts.ReferenciaContext;

public interface IDatabaseFactory {

    ReferenciaContext get();
}
