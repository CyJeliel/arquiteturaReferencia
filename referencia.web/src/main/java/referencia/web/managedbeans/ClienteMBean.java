package referencia.web.managedbeans;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import lombok.Getter;
import lombok.Setter;
import referencia.application.implementations.excecoes.BCException;
import referencia.domain.entities.Cliente;
import referencia.domain.enumeracoes.StatusClienteEnum;
import referencia.web.managedbeans.rest.ClienteRestMBean;

@ManagedBean(name = "clienteMBean")
@ViewScoped
public class ClienteMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{enderecoMBean}")
    @Setter
    private EnderecoMBean enderecoMBean;

    @ManagedProperty(value = "#{clienteRestMBean}")
    @Setter
    private transient ClienteRestMBean clienteRestMBean;

    @Getter
    @Setter
    private transient List<Cliente> clientes;

    @Getter
    private List<String> clientesSelecao;

    @Getter
    @Setter
    private String nomeClienteSelecionado;

    @Getter
    @Setter
    private transient Cliente clienteSelecionado;

    @Getter
    @Setter
    private transient Cliente novoCliente;

    private boolean exibeDetalhes;

    private boolean exibeBotaoExcluir;

    @Getter
    private String feedback;

    @Getter
    private String tipoFeedback;

    @Getter
    private String titulo;

    @Setter
    @Getter
    private String numero;

    @Setter
    @Getter
    private String enderecoComplemento;

    @Setter
    @Getter
    private String cep;

    /**
     * Carrega os clientes e carrega os dados para o campo de busca com autopreenchimento
     * 
     * @throws BCException
     */
    @PostConstruct
    public void carregarClientes() throws BCException {

        clientes = clienteRestMBean.listarCliente();

        clientesSelecao = new ArrayList<>();

        for (Cliente cliente : clientes) {

            clientesSelecao.add(cliente.getNome());
        }

        Collections.sort(clientesSelecao);
    }

    /**
     * Carrega os detalhes de um cliente selecionado
     */
    public void onSelecionaCliente() {

        if (nomeClienteSelecionado != null && !"Selecione".equals(nomeClienteSelecionado)) {

            for (Cliente cliente : clientes) {

                if (cliente.getNome().equals(nomeClienteSelecionado)) {

                    clienteSelecionado = clienteRestMBean.buscar(cliente.getId());

                    selecionaCliente();

                    break;
                }
            }
        }

        nomeClienteSelecionado = null;
    }

    public List<String> autoCompleteClientes(String textoDigitado) {

        List<String> clientesAutoComplete = new ArrayList<>();

        for (String cliente : clientesSelecao) {

            if (cliente.toLowerCase().contains(textoDigitado.toLowerCase())) {

                clientesAutoComplete.add(cliente);
            }
        }

        return clientesAutoComplete;
    }

    public void excluir() throws BCException {

        StatusClienteEnum status = clienteRestMBean.excluir(clienteSelecionado.getId());

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();

        exibeDetalhes = false;

        exibeBotaoExcluir = false;

        carregarClientes();

        enderecoMBean.reset();
    }

    public void excluir(Cliente cliente) throws BCException {

        StatusClienteEnum status = clienteRestMBean.excluir(cliente.getId());

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();

        exibeDetalhes = false;

        exibeBotaoExcluir = false;

        carregarClientes();

        enderecoMBean.reset();
    }

    public void salvar() throws BCException {

        buildClienteSelecionado();

        StatusClienteEnum status = clienteRestMBean.salvar(clienteSelecionado);

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();

        carregarClientes();

        if (!"erro".equals(tipoFeedback)) {

            exibeDetalhes = false;

            exibeBotaoExcluir = false;

        } else if (clienteSelecionado.getId() != null) {

            clienteSelecionado = clienteRestMBean.buscar(clienteSelecionado.getId());

        } else {

            clienteSelecionado.setNome("");
        }
    }

    private void buildClienteSelecionado() {

        if (clienteSelecionado == null) {

            clienteSelecionado = new Cliente();
        }

        clienteSelecionado.setEndereco(enderecoMBean.getEndereco());
    }

    /**
     * Ações executadas quando uma linha da tabela de {@link Cliente}s é selecionada.
     * 
     * @param event
     */
    public void onRowSelect(SelectEvent event) {

        clienteSelecionado = (Cliente) event.getObject();

        clienteSelecionado = clienteRestMBean.buscar(clienteSelecionado.getId());

        selecionaCliente();
    }

    private void selecionaCliente() {

        String cepCliente = ("00000000" + clienteSelecionado.getCep()).substring(7);

        enderecoMBean.setCepDigitado(cepCliente);

        enderecoMBean.carregarEndereco();

        enderecoMBean.getEndereco().setComplemento(clienteSelecionado.getEnderecoComplemento());

        enderecoMBean.carregarComplemento();

        clienteSelecionado.setEndereco(enderecoMBean.getEndereco());

        nomeClienteSelecionado = clienteSelecionado.getNome();

        titulo = "Editar Cliente " + nomeClienteSelecionado;

        exibeDetalhes = true;

        exibeBotaoExcluir = true;
    }

    /**
     * Limpa e esconde o formulário
     */
    public void reset() {

        if (clienteSelecionado != null && isBlank(clienteSelecionado.getNome()) && clientes.contains(clienteSelecionado)) {

            clientes.remove(clienteSelecionado);
        }

        feedback = "";

        tipoFeedback = "";

        titulo = "";

        clienteSelecionado = new Cliente();

        nomeClienteSelecionado = "";

        exibeDetalhes = false;

        exibeBotaoExcluir = false;

        cep = null;

        enderecoComplemento = null;

        numero = null;

        enderecoMBean.reset();
    }

    /**
     * Exibe um formulário para criação de um novo {@link Cliente}
     */
    public void formNovoCliente() {

        reset();

        titulo = "Novo Cliente";

        this.exibeDetalhes = true;

        exibeBotaoExcluir = false;

        novoCliente = new Cliente();
    }

    public void ocultaDetalhes() {

        this.exibeDetalhes = false;

        exibeBotaoExcluir = false;
    }

    public boolean isExibeDetalhes() {

        return exibeDetalhes;
    }

    public boolean isExibeBotaoExcluir() {

        return exibeBotaoExcluir;
    }

    public String getNome() {

        if (clienteSelecionado != null) {

            return clienteSelecionado.getNome();
        }

        return "";
    }

    public void setNome(String nome) {

        if (clienteSelecionado == null) {

            clienteSelecionado = new Cliente();
        }

        clienteSelecionado.setNome(nome);
    }

    public String getEmail() {

        if (clienteSelecionado != null) {

            return clienteSelecionado.getEmail();
        }

        return "";
    }

    public void setEmail(String email) {

        if (clienteSelecionado == null) {

            clienteSelecionado = new Cliente();
        }

        clienteSelecionado.setEmail(email);
    }

    public String getTelefone() {

        if (clienteSelecionado != null) {

            return clienteSelecionado.getTelefone();
        }

        return "";
    }

    public void setTelefone(String telefone) {

        if (clienteSelecionado == null) {

            clienteSelecionado = new Cliente();
        }

        clienteSelecionado.setTelefone(telefone);
    }
}
