package referencia.application.interfaces;

import java.util.List;

import referencia.application.implementations.excecoes.BCException;
import referencia.domain.entities.Cliente;
import referencia.domain.enumeracoes.StatusClienteEnum;

public interface IClienteApplication {

    List<Cliente> listar() throws BCException;

    StatusClienteEnum salvar(Cliente cliente);

    StatusClienteEnum excluir(Integer id);

    Cliente buscar(Integer id);

    boolean existe(Cliente cliente);
}
