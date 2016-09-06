package referencia.repository.implementations;

import javax.inject.Inject;

import referencia.domain.entities.Categoria;
import referencia.domain.interfaces.repository.ICategoryRepository;
import referencia.repository.database.implementations.Repository;
import referencia.repository.database.interfaces.IDatabaseFactory;
import referencia.repository.mapper.implementations.entities.Category;
import referencia.repository.mapper.interfaces.ICategoryMap;

public class CategoryRepository extends Repository<Category, Categoria> implements ICategoryRepository {

    private static final long serialVersionUID = 1L;

    @Inject
    public CategoryRepository(IDatabaseFactory databaseFactory) {

        super(databaseFactory, ICategoryMap.INSTANCE);
    }
}