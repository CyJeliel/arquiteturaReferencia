package referencia.repository.mapper.interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import referencia.domain.entities.Usuario;
import referencia.repository.mapper.implementations.entities.User;

@Mapper
public interface IUserMap extends IMapper<User, Usuario> {

    public static final IUserMap INSTANCE = Mappers.getMapper(IUserMap.class);

    Usuario entityToDomain(User user);

    User domainToEntity(Usuario usuario);
}
