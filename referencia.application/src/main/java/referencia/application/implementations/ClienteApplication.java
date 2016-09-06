package referencia.application.implementations;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static referencia.domain.enumeracoes.StatusClienteEnum.EXCLUIDO;
import static referencia.domain.enumeracoes.StatusClienteEnum.EXISTENTE;
import static referencia.domain.enumeracoes.StatusClienteEnum.SALVO;
import static referencia.domain.enumeracoes.StatusClienteEnum.VALIDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;

import referencia.application.interfaces.IClienteApplication;
import referencia.application.interfaces.IClienteValidoApplication;
import referencia.domain.entities.Cliente;
import referencia.domain.enumeracoes.StatusClienteEnum;
import referencia.domain.interfaces.IUnitOfWork;
import referencia.domain.interfaces.repository.IClientRepository;

@Stateless
public class ClienteApplication implements IClienteApplication {

    private IClientRepository repo;

    private IClienteValidoApplication clienteValidoSC;

    private IUnitOfWork unitOfWork;

    public ClienteApplication() {
        // Empty constructor for instatiating EJB
    }

    @Inject
    public ClienteApplication(IClientRepository repo, IClienteValidoApplication clienteValidoSC, IUnitOfWork unitOfWork) {

        this.repo = repo;

        this.clienteValidoSC = clienteValidoSC;

        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Cliente> listar() {

        List<Cliente> clientes = (List<Cliente>) repo.getAll();

        if (clientes == null) {

            clientes = new ArrayList<>();
        }

        return clientes;
    }

    @Override
    public StatusClienteEnum salvar(Cliente cliente) {

        StatusClienteEnum status = clienteValidoSC.isValido(cliente);

        if (VALIDO.equals(status)) {

            boolean existe = existe(cliente);

            if (existe) {

                status = EXISTENTE;

            } else {

                status = gravar(cliente);
            }
        }

        return status;
    }

    @TransactionAttribute(REQUIRED)
    private StatusClienteEnum gravar(Cliente cliente) {

        StatusClienteEnum status;

        if (cliente.getId() != null) {

            repo.update(cliente);

            unitOfWork.commit();

        } else {

            repo.add(cliente);

            unitOfWork.commit();
        }

        status = SALVO;

        return status;
    }

    @Override
    public boolean existe(Cliente cliente) {

        boolean existe = false;

        Map<String, Object> map = new HashMap<>();

        map.put("nome", cliente.getNome().toLowerCase());

        List<Cliente> clientes = (List<Cliente>) repo.getMany("Client.buscarPorDescricao", map);

        if (isNotEmpty(clientes)) {

            if (cliente.getId() == null) {

                existe = true;

            } else if (!clientes.contains(cliente)) {

                existe = true;
            }
        }

        return existe;

    }

    @Override
    public StatusClienteEnum excluir(Integer id) {

        StatusClienteEnum status = EXCLUIDO;

        repo.delete(id);

        return status;
    }

    @Override
    public Cliente buscar(Integer id) {

        return repo.getById(id);
    }

}
