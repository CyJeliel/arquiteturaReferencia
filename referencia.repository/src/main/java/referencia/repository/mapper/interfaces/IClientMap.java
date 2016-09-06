package referencia.repository.mapper.interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import referencia.domain.entities.Cliente;
import referencia.repository.mapper.implementations.entities.Client;

@Mapper
public interface IClientMap extends IMapper<Client, Cliente> {

    public static final IClientMap INSTANCE = Mappers.getMapper(IClientMap.class);

    Cliente clientToCliente(Client client);

    Client domainToEntity(Cliente cliente);
}
