package referencia.repository.mapper.interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import referencia.domain.entities.Categoria;
import referencia.repository.mapper.implementations.entities.Category;

@Mapper
public interface ICategoryMap extends IMapper<Category, Categoria> {

    public static final ICategoryMap INSTANCE = Mappers.getMapper(ICategoryMap.class);

    Categoria entityToDomain(Category category);

    Category domainToEntity(Categoria categoria);
}
