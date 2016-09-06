package referencia.repository.implementations;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import referencia.domain.entities.Usuario;
import referencia.domain.interfaces.repository.IUserRepository;
import referencia.repository.database.implementations.Repository;
import referencia.repository.database.interfaces.IDatabaseFactory;
import referencia.repository.mapper.implementations.entities.User;
import referencia.repository.mapper.interfaces.IUserMap;

@Dependent
public class UserRepository extends Repository<User, Usuario> implements IUserRepository {

    private static final long serialVersionUID = 1L;

    @Inject
    public UserRepository(IDatabaseFactory databaseFactory) {

        super(databaseFactory, IUserMap.INSTANCE);
    }
}
