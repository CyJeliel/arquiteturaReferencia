package referencia.web.managedbeans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;

import lombok.Getter;
import lombok.Setter;
import referencia.application.implementations.excecoes.BCException;
import referencia.domain.entities.Categoria;
import referencia.domain.entities.Produto;
import referencia.domain.enumeracoes.StatusProdutoEnum;
import referencia.web.managedbeans.rest.ProdutoRestMBean;
import referencia.web.modelodedados.ProdutoModeloDeDados;

@ManagedBean(name = "produtoMBean")
@ViewScoped
public class ProdutoMBean extends AbstractManagedBean {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{categoriaMBean}")
    @Setter
    private CategoriaMBean categoriaMBean;

    @ManagedProperty(value = "#{produtoRestMBean}")
    @Setter
    private transient ProdutoRestMBean produtoRestMBean;

    @Getter
    private transient List<Produto> produtos;

    @Getter
    private List<String> nomesProdutos;

    @Getter
    private transient Produto produtoSelecionado;

    @Getter
    @Setter
    private String nomeProdutoSelecionado;

    private boolean exibeDetalhes;

    private boolean exibeBotaoExcluir;

    @Getter
    private String feedback;

    @Getter
    private String tipoFeedback;

    @Getter
    private String titulo;

    @Getter
    private LazyDataModel<Produto> lazyModel;

    @PostConstruct
    public void carregarProdutos() throws BCException {

        produtos = produtoRestMBean.listarProduto();

        nomesProdutos = new ArrayList<>();

        for (Produto produto : produtos) {

            nomesProdutos.add(produto.getDescricao());
        }

        Collections.sort(nomesProdutos);

        lazyModel = new ProdutoModeloDeDados(produtos);
    }

    public void salvar() throws BCException {

        Categoria categoria = categoriaMBean.getCategoriaSelecionada();

        produtoSelecionado.setCategoria(categoria);

        StatusProdutoEnum status = produtoRestMBean.salvar(produtoSelecionado);

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();

        carregarProdutos();

        if (!"erro".equals(tipoFeedback)) {

            exibeDetalhes = false;

            exibeBotaoExcluir = false;

        } else if (produtoSelecionado.getId() != null) {

            produtoSelecionado = produtoRestMBean.buscar(produtoSelecionado.getId());

        } else {

            produtoSelecionado.setDescricao("");
        }
    }

    public void excluir() throws BCException {

        StatusProdutoEnum status = produtoRestMBean.excluir(produtoSelecionado.getId());

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();

        reset();

        exibeDetalhes = false;

        exibeBotaoExcluir = false;

        carregarProdutos();
    }

    public void excluir(Produto produto) throws BCException {

        StatusProdutoEnum status = produtoRestMBean.excluir(produto.getId());

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();

        reset();

        exibeDetalhes = false;

        exibeBotaoExcluir = false;

        carregarProdutos();
    }

    public void reset() {

        produtoSelecionado = new Produto();

        nomeProdutoSelecionado = null;

        exibeDetalhes = false;

        exibeBotaoExcluir = false;

        categoriaMBean.reset();

        feedback = "";
    }

    public void onRowSelect(SelectEvent event) throws BCException {

        produtoSelecionado = (Produto) event.getObject();

        nomeProdutoSelecionado = produtoSelecionado.getDescricao();

        produtoSelecionado = produtoRestMBean.buscar(produtoSelecionado.getId());

        categoriaMBean.aplicarCategoriaSelecionadaPor(produtoSelecionado);

        titulo = "Editar Produto " + nomeProdutoSelecionado;

        exibeDetalhes = true;

        exibeBotaoExcluir = true;
    }

    public void onSelecionaProduto(SelectEvent event) throws BCException {

        String nomeProduto = (String) event.getObject();

        produtoSelecionado = new Produto();

        for (Produto produto : produtos) {

            if (produto.getDescricao().equals(nomeProduto)) {

                produtoSelecionado = produtoRestMBean.buscar(produto.getId());

                categoriaMBean.aplicarCategoriaSelecionadaPor(produtoSelecionado);

                exibeDetalhes = true;

                exibeBotaoExcluir = true;

                titulo = "Editar Produto " + nomeProdutoSelecionado;

                break;
            }
        }

        nomeProdutoSelecionado = null;
    }

    public List<String> autoCompleteProdutos(String textoDigitado) {

        List<String> produtosAutoComplete = new ArrayList<>();

        for (Produto produto : produtos) {

            if (produto.getDescricao().toLowerCase().contains(textoDigitado.toLowerCase())) {

                produtosAutoComplete.add(produto.getDescricao());
            }
        }

        return produtosAutoComplete;
    }

    public void ocultaDetalhes() {

        this.exibeDetalhes = false;
    }

    public void formNovoProduto() {

        reset();

        titulo = "Novo Produto";

        produtoSelecionado = new Produto();

        this.exibeDetalhes = true;

        exibeBotaoExcluir = false;

        feedback = "";
    }
    
    public boolean isExibeDetalhes() {
        
        return exibeDetalhes;
    }
    
    public boolean isExibeBotaoExcluir() {
        
        return exibeBotaoExcluir;
    }

    public void setProdutoSelecionado(Produto produtoSelecionado) {

        if (produtoSelecionado != null) {

            this.produtoSelecionado = produtoSelecionado;

        } else {

            this.produtoSelecionado = new Produto();
        }
    }
}
