package referencia.application.interfaces;

import java.util.List;

import referencia.application.implementations.excecoes.BCException;
import referencia.domain.entities.Categoria;
import referencia.domain.entities.Produto;
import referencia.domain.enumeracoes.StatusCategoriaEnum;

public interface ICategoriaApplication {

    List<Categoria> listar() throws BCException;

    Categoria buscarPor(Produto produto) throws BCException;

    StatusCategoriaEnum salvar(Categoria categoriaSelecionada);

    StatusCategoriaEnum excluir(Integer id);

    Categoria buscar(Integer id);

    boolean existe(Categoria categoria);

}
