package referencia.repository.implementations;

import javax.inject.Inject;

import referencia.domain.entities.Cliente;
import referencia.domain.interfaces.repository.IClientRepository;
import referencia.repository.database.implementations.Repository;
import referencia.repository.database.interfaces.IDatabaseFactory;
import referencia.repository.mapper.implementations.entities.Client;
import referencia.repository.mapper.interfaces.IClientMap;

public class ClientRepository extends Repository<Client, Cliente> implements IClientRepository {

    private static final long serialVersionUID = 1L;

    @Inject
    public ClientRepository(IDatabaseFactory databaseFactory) {

        super(databaseFactory, IClientMap.INSTANCE);
    }
}
