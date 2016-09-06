package referencia.application.interfaces;

import referencia.application.implementations.excecoes.BCException;
import referencia.domain.entities.Endereco;

public interface IEnderecoApplication {

    Endereco buscar(String cep) throws BCException;
}
