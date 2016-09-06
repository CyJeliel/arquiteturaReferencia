package referencia.repository.implementations;

import javax.inject.Inject;

import referencia.domain.entities.Produto;
import referencia.domain.interfaces.repository.IProductRepository;
import referencia.repository.database.implementations.Repository;
import referencia.repository.database.interfaces.IDatabaseFactory;
import referencia.repository.mapper.implementations.entities.Product;
import referencia.repository.mapper.interfaces.IProductMap;

public class ProductRepository extends Repository<Product, Produto> implements IProductRepository {

    private static final long serialVersionUID = 1L;

    @Inject
    public ProductRepository(IDatabaseFactory databaseFactory) {

        super(databaseFactory, IProductMap.INSTANCE);
    }
}
