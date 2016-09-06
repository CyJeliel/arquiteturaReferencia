package referencia.repository.mapper.interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import referencia.domain.entities.Produto;
import referencia.repository.mapper.implementations.entities.Product;

@Mapper
public interface IProductMap extends IMapper<Product, Produto> {

    public static final IProductMap INSTANCE = Mappers.getMapper(IProductMap.class);

    Produto entityToDomain(Product product);

    Product domainToEntity(Produto produto);
}
