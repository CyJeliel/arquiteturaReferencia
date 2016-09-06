package referencia.application.interfaces;

import java.util.List;

import referencia.application.implementations.excecoes.BCException;
import referencia.domain.entities.Produto;
import referencia.domain.enumeracoes.StatusProdutoEnum;

public interface IProdutoApplication {

    List<Produto> listar() throws BCException;

    StatusProdutoEnum salvar(Produto produtoSelecionado);

    StatusProdutoEnum excluir(Integer id);

    Produto buscar(Integer id) throws BCException;

    boolean existe(Produto produto);

}
