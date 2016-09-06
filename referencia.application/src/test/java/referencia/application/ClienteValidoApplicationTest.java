package referencia.application;

import static referencia.domain.enumeracoes.StatusClienteEnum.INVALIDO;
import static referencia.domain.enumeracoes.StatusClienteEnum.VALIDO;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import junit.framework.TestCase;
import referencia.application.implementations.ClienteValidoApplication;
import referencia.domain.entities.Cliente;
import referencia.domain.enumeracoes.StatusClienteEnum;
import referencia.domain.factory.ClienteFactory;

@RunWith(MockitoJUnitRunner.class)
public class ClienteValidoApplicationTest extends TestCase {

    @InjectMocks
    private ClienteValidoApplication clienteValidoSC = new ClienteValidoApplication();

    @Test
    public void isValidoDeveriaRetornarComplementoInvalido() {

        Cliente cliente = ClienteFactory.getClienteComEndereco();

        cliente.setEnderecoComplemento(" ");

        StatusClienteEnum statusClienteEnum = clienteValidoSC.isValido(cliente);

        Assert.assertEquals(INVALIDO, statusClienteEnum);
    }

    @Test
    public void isValidoDeveriaRetornarCepInvalido() {

        Cliente cliente = ClienteFactory.getClienteComEndereco();

        cliente.setCep(0);

        StatusClienteEnum statusClienteEnum = clienteValidoSC.isValido(cliente);

        Assert.assertEquals(INVALIDO, statusClienteEnum);
    }

    @Test
    public void isValidoDeveriaRetornarBairroInvalido() {

        Cliente cliente = ClienteFactory.getClienteComEndereco();

        cliente.setBairro(null);

        StatusClienteEnum statusClienteEnum = clienteValidoSC.isValido(cliente);

        Assert.assertEquals(INVALIDO, statusClienteEnum);
    }

    @Test
    public void isValidoDeveriaRetornarEnderecoInvalido() {

        Cliente cliente = ClienteFactory.getClienteComEndereco();

        cliente.setEnderecoDescricao(null);

        StatusClienteEnum statusClienteEnum = clienteValidoSC.isValido(cliente);

        Assert.assertEquals(INVALIDO, statusClienteEnum);
    }

    @Test
    public void isValidoDeveriaRetornarTelefoneInvalido() {

        Cliente cliente = ClienteFactory.getClienteComEndereco();

        cliente.setTelefone("00000-0000");

        StatusClienteEnum statusClienteEnum = clienteValidoSC.isValido(cliente);

        Assert.assertEquals(INVALIDO, statusClienteEnum);
    }

    @Test
    public void isValidoDeveriaRetornarNomeInvalido() {

        Cliente cliente = ClienteFactory.getClienteComEndereco();

        cliente.setNome(null);

        StatusClienteEnum statusClienteEnum = clienteValidoSC.isValido(cliente);

        Assert.assertEquals(INVALIDO, statusClienteEnum);
    }

    @Test
    public void isValidoDeveriaRetornarEmailInvalido() {

        Cliente cliente = ClienteFactory.getClienteComEndereco();

        cliente.setEmail(null);

        StatusClienteEnum statusClienteEnum = clienteValidoSC.isValido(cliente);

        Assert.assertEquals(INVALIDO, statusClienteEnum);
    }

    @Test
    public void isValidoDeveriaRetornarValido() {

        Cliente cliente = ClienteFactory.getClienteComEndereco();

        StatusClienteEnum statusClienteEnum = clienteValidoSC.isValido(cliente);

        Assert.assertEquals(VALIDO, statusClienteEnum);
    }
}
