package referencia.application.implementations;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static referencia.domain.enumeracoes.StatusClienteEnum.INVALIDO;
import static referencia.domain.enumeracoes.StatusClienteEnum.VALIDO;

import javax.ejb.Stateless;

import referencia.application.interfaces.IClienteValidoApplication;
import referencia.domain.entities.Cliente;
import referencia.domain.enumeracoes.StatusClienteEnum;

@Stateless
public class ClienteValidoApplication implements IClienteValidoApplication {

    @Override
    public StatusClienteEnum isValido(Cliente cliente) {

        boolean enderecoInvalido = complementoInvalido(cliente) || cepInvalido(cliente) || bairroInvalido(cliente) || enderecoInvalido(cliente);

        boolean telefoneInvalido = telefoneInvalido(cliente);

        if (isBlank(cliente.getNome()) || enderecoInvalido || telefoneInvalido || isBlank(cliente.getEmail())) {

            return INVALIDO;
        }

        return VALIDO;
    }

    private boolean telefoneInvalido(Cliente cliente) {

        String telefone = cliente.getTelefone();

        return isBlank(telefone) || Long.valueOf(telefone.replace("-", "").replace("(", "").replace(")", "")) == 0;
    }

    private boolean complementoInvalido(Cliente cliente) {

        String enderecoComplemento = cliente.getEnderecoComplemento();

        return isBlank(enderecoComplemento) || enderecoComplemento.startsWith(" ");
    }

    private boolean cepInvalido(Cliente cliente) {

        Integer cep = cliente.getCep();

        return cep == null || cep == 0;
    }

    private boolean enderecoInvalido(Cliente cliente) {

        boolean enderecoDescricaoInvalida = false;

        String enderecoDescricao = cliente.getEnderecoDescricao();

        if (enderecoDescricao == null || isBlank(enderecoDescricao)) {

            enderecoDescricaoInvalida = true;
        }
        return enderecoDescricaoInvalida;
    }

    private boolean bairroInvalido(Cliente cliente) {

        boolean bairroInvalido = false;

        String bairro = cliente.getBairro();

        if (bairro == null || isBlank(bairro)) {

            bairroInvalido = true;
        }

        return bairroInvalido;
    }
}