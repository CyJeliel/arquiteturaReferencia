package referencia.application.interfaces;

import referencia.domain.entities.Cliente;
import referencia.domain.enumeracoes.StatusClienteEnum;

public interface IClienteValidoApplication {

    StatusClienteEnum isValido(Cliente cliente);

}
