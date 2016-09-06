package referencia.domain.factory;

import java.util.ArrayList;
import java.util.List;

import referencia.domain.entities.Cliente;
import referencia.domain.entities.Endereco;

public class ClienteFactory {

    private ClienteFactory() {

    }

    public static List<Cliente> getLista() {

        List<Cliente> clientes = new ArrayList<>();

        Cliente cliente1 = new Cliente();

        Cliente cliente2 = new Cliente();

        Cliente cliente3 = new Cliente();

        Cliente cliente4 = new Cliente();

        Cliente cliente5 = new Cliente();

        Cliente cliente6 = new Cliente();

        cliente1.setNome("Wagner");

        cliente2.setNome("Eduardo");

        cliente3.setNome("João");

        cliente4.setNome("Letícia");

        cliente5.setNome("Amanda");

        cliente6.setNome("Cindy");

        clientes.add(cliente1);

        clientes.add(cliente2);

        clientes.add(cliente3);

        clientes.add(cliente4);

        clientes.add(cliente5);

        clientes.add(cliente6);

        return clientes;
    }

    public static Cliente getCliente() {

        Cliente cliente = new Cliente();

        cliente.setCep(0);

        cliente.setEmail("cindy@sp.gov.br");

        cliente.setEnderecoComplemento("1335 ap 44 Bloco B");

        cliente.setId(1);

        cliente.setNome("Cindy de Albuquerque");

        cliente.setTelefone("123456");

        return cliente;
    }

    public static Cliente getClienteComEndereco() {

        Cliente cliente = new Cliente();

        cliente.setCep(1313000);

        cliente.setEmail("cindy@sp.gov.br");

        Endereco endereco = EnderecoFactory.getEnderecoComComplemento();
        
        cliente.setEndereco(endereco);
        
        cliente.setEnderecoDescricao(endereco.getDescricao());
        
        cliente.setEnderecoComplemento(endereco.getNumero() + " " + endereco.getComplemento());
        
        cliente.setBairro(endereco.getBairro());
        
        cliente.setId(1);

        cliente.setNome("Cindy de Albuquerque");

        cliente.setTelefone("123456");

        return cliente;
    }
}
