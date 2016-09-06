package referencia.web.managedbeans;

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
import referencia.domain.entities.Categoria;
import referencia.domain.entities.Produto;
import referencia.domain.enumeracoes.StatusCategoriaEnum;
import referencia.web.managedbeans.rest.CategoriaRestMBean;

@ManagedBean(name = "categoriaMBean")
@ViewScoped
public class CategoriaMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{categoriaRestMBean}")
    @Setter
    private transient CategoriaRestMBean categoriaRestMBean;

    @Getter
    private transient List<Categoria> categorias;

    @Getter
    private List<String> categoriasSelecao;

    @Getter
    @Setter
    private String nomeCategoriaSelecionada;

    @Getter
    private transient Categoria categoriaSelecionada;

    private boolean exibeDetalhes;

    private boolean exibeBotaoExcluir;

    @Getter
    private String feedback;

    @Getter
    private String tipoFeedback;

    @Getter
    private String titulo;
    
    @PostConstruct
    public void loadCategorias() throws BCException {
        
        categorias = categoriaRestMBean.listarCategoria();
        
        categoriasSelecao = new ArrayList<>();
        
        for (Categoria categoria : categorias) {
            
            categoriasSelecao.add(categoria.getDescricao());
        }
        
        Collections.sort(categoriasSelecao);
    }

    public void onSelecionaCategoria() {

        if (nomeCategoriaSelecionada != null && !"Selecione".equals(nomeCategoriaSelecionada)) {

            for (Categoria categoria : categorias) {

                if (categoria.getDescricao().equals(nomeCategoriaSelecionada)) {

                    categoriaSelecionada = categoriaRestMBean.buscar(categoria.getId());

                    titulo = "Editar Categoria " + nomeCategoriaSelecionada;

                    exibeDetalhes = true;

                    exibeBotaoExcluir = true;

                    break;
                }
            }
        }

        nomeCategoriaSelecionada = null;
    }

    public List<String> autoCompleteCategorias(String textoDigitado) {

        List<String> categoriasAutoComplete = new ArrayList<>();

        for (String categoria : categoriasSelecao) {

            if (categoria.toLowerCase().contains(textoDigitado.toLowerCase())) {

                categoriasAutoComplete.add(categoria);
            }
        }

        return categoriasAutoComplete;
    }

    public void excluir() throws BCException {

        StatusCategoriaEnum status = categoriaRestMBean.excluir(categoriaSelecionada.getId());

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();

        exibeDetalhes = false;

        exibeBotaoExcluir = false;

        loadCategorias();
    }

    public void excluir(Categoria categoria) throws BCException {

        StatusCategoriaEnum status = categoriaRestMBean.excluir(categoria.getId());

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();

        exibeDetalhes = false;

        exibeBotaoExcluir = false;

        loadCategorias();
    }

    public void salvar() throws BCException {

        StatusCategoriaEnum status = categoriaRestMBean.salvar(categoriaSelecionada);

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();

        loadCategorias();

        if (!"erro".equals(tipoFeedback)) {

            exibeDetalhes = false;

            exibeBotaoExcluir = false;

        } else if (categoriaSelecionada.getId() != null) {

            categoriaSelecionada = categoriaRestMBean.buscar(categoriaSelecionada.getId());

        } else {

            categoriaSelecionada.setDescricao("");
        }
    }

    public void aplicarCategoriaSelecionadaPor(Produto produto) throws BCException {

        categoriaSelecionada = categoriaRestMBean.buscar(produto.getCategoria().getId());

        if (categoriaSelecionada != null) {

            nomeCategoriaSelecionada = categoriaSelecionada.getDescricao();
        }
    }

    public void onRowSelect(SelectEvent event) {

        categoriaSelecionada = (Categoria) event.getObject();

        nomeCategoriaSelecionada = categoriaSelecionada.getDescricao();

        categoriaSelecionada = categoriaRestMBean.buscar(categoriaSelecionada.getId());

        titulo = "Editar Categoria " + nomeCategoriaSelecionada;

        exibeDetalhes = true;

        exibeBotaoExcluir = true;
    }

    public void reset() {

        categoriaSelecionada = new Categoria();

        nomeCategoriaSelecionada = "";

        exibeDetalhes = false;

        exibeBotaoExcluir = false;

        feedback = "";
    }

    public void ocultaDetalhes() {

        this.exibeDetalhes = false;

        exibeBotaoExcluir = false;
    }

    public void formNovaCategoria() {

        reset();

        titulo = "Nova Categoria";

        this.exibeDetalhes = true;

        exibeBotaoExcluir = false;

        categoriaSelecionada = new Categoria();

        feedback = "";
    }

    public boolean isExibeDetalhes() {

        return exibeDetalhes;
    }

    public boolean isExibeBotaoExcluir() {

        return exibeBotaoExcluir;
    }
    
    public void setCategoriaSelecionada(Categoria categoriaSelecionada) {
        
        if (categoriaSelecionada != null) {
            
            this.categoriaSelecionada = categoriaSelecionada;
            
        } else {
            
            this.categoriaSelecionada = new Categoria();
        }
    }
}
