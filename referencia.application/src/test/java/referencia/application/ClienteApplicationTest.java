package referencia.application;

import static referencia.domain.enumeracoes.StatusClienteEnum.EXCLUIDO;
import static referencia.domain.enumeracoes.StatusClienteEnum.EXISTENTE;
import static referencia.domain.enumeracoes.StatusClienteEnum.INVALIDO;
import static referencia.domain.enumeracoes.StatusClienteEnum.SALVO;
import static referencia.domain.enumeracoes.StatusClienteEnum.VALIDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import junit.framework.TestCase;
import referencia.application.implementations.ClienteApplication;
import referencia.application.implementations.ClienteValidoApplication;
import referencia.application.implementations.excecoes.BCException;
import referencia.domain.entities.Cliente;
import referencia.domain.enumeracoes.StatusClienteEnum;
import referencia.domain.factory.ClienteFactory;
import referencia.domain.interfaces.IUnitOfWork;
import referencia.domain.interfaces.repository.IClientRepository;

@RunWith(MockitoJUnitRunner.class)
public class ClienteApplicationTest extends TestCase {

    @InjectMocks
    private ClienteApplication clienteSC = new ClienteApplication();

    @Mock
    private ClienteValidoApplication clienteValidoSC;

    @Mock
    private IClientRepository repo;

    @Mock
    private IUnitOfWork unitOfWork;

    private Map<String, Object> map;

    @Before
    public void beforeTest() {

        map = new HashMap<>();
    }

    @Test
    public void existeDeveriaRetornarFalse() {

        Cliente cliente = ClienteFactory.getCliente();

        map.put("nome", cliente.getNome().toLowerCase());

        Mockito.when(repo.getMany("Client.buscarPorDescricao", map)).thenReturn(null);

        boolean existe = clienteSC.existe(cliente);

        assertFalse(existe);
    }

    @Test
    public void existeDeveriaRetornarTrue() {

        Cliente cliente = ClienteFactory.getCliente();

        List<Cliente> clientes = ClienteFactory.getLista();

        map.put("nome", cliente.getNome().toLowerCase());

        Mockito.when(repo.getMany("Client.buscarPorDescricao", map)).thenReturn(clientes);

        boolean existe = clienteSC.existe(cliente);

        assertTrue(existe);
    }

    @Test
    public void listarDeveriaTrazerListaVazia() throws BCException {

        Mockito.when(repo.getAll()).thenReturn(new ArrayList<>());

        List<Cliente> clientes = clienteSC.listar();

        Assert.assertEquals(0, clientes.size());
    }

    @Test
    public void salvarDeveriaRetornarExistente() {

        Cliente cliente = ClienteFactory.getClienteComEndereco();

        List<Cliente> clientes = ClienteFactory.getLista();

        map.put("nome", cliente.getNome().toLowerCase());

        Mockito.when(repo.getMany("Client.buscarPorDescricao", map)).thenReturn(clientes);

        Mockito.when(clienteValidoSC.isValido(cliente)).thenReturn(VALIDO);

        StatusClienteEnum statusClienteEnum = clienteSC.salvar(cliente);

        Assert.assertEquals(EXISTENTE, statusClienteEnum);
    }

    @Test
    public void salvarDeveriaRetornarInvalido() {

        Cliente cliente = ClienteFactory.getClienteComEndereco();

        Mockito.doNothing().when(repo).update(cliente);

        Mockito.doNothing().when(unitOfWork).commit();

        Mockito.when(clienteValidoSC.isValido(cliente)).thenReturn(INVALIDO);

        StatusClienteEnum statusClienteEnum = clienteSC.salvar(cliente);

        Assert.assertEquals(INVALIDO, statusClienteEnum);
    }

    @Test
    public void salvarDeveriaRetornarSalvo() {

        Cliente cliente = ClienteFactory.getClienteComEndereco();

        Mockito.doNothing().when(repo).update(cliente);

        Mockito.doNothing().when(unitOfWork).commit();

        Mockito.when(clienteValidoSC.isValido(cliente)).thenReturn(VALIDO);

        StatusClienteEnum statusClienteEnum = clienteSC.salvar(cliente);

        Assert.assertEquals(SALVO, statusClienteEnum);
    }

    @Test
    public void excluirDeveriaRetornarExcluido() {

        Cliente cliente = ClienteFactory.getClienteComEndereco();

        Mockito.doNothing().when(repo).delete(cliente.getId());

        StatusClienteEnum statusClienteEnum = clienteSC.excluir(cliente.getId());

        Assert.assertEquals(EXCLUIDO, statusClienteEnum);
    }
}
