package referencia.application;

import static referencia.domain.enumeracoes.StatusCategoriaEnum.CATEGORIA_COM_PRODUTOS;
import static referencia.domain.enumeracoes.StatusCategoriaEnum.EXCLUIDA;
import static referencia.domain.enumeracoes.StatusCategoriaEnum.EXISTENTE;
import static referencia.domain.enumeracoes.StatusCategoriaEnum.INVALIDA;
import static referencia.domain.enumeracoes.StatusCategoriaEnum.SALVA;

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
import referencia.application.implementations.CategoriaApplication;
import referencia.domain.entities.Categoria;
import referencia.domain.entities.Produto;
import referencia.domain.enumeracoes.StatusCategoriaEnum;
import referencia.domain.factory.CategoriaFactory;
import referencia.domain.factory.ProdutoFactory;
import referencia.domain.interfaces.IUnitOfWork;
import referencia.domain.interfaces.repository.ICategoryRepository;
import referencia.domain.interfaces.repository.IProductRepository;

@RunWith(MockitoJUnitRunner.class)
public class CategoriaApplicationTest extends TestCase {

    @InjectMocks
    private CategoriaApplication categoriaSC = new CategoriaApplication();

    @Mock
    private ICategoryRepository repo;

    @Mock
    private IUnitOfWork unitOfWork;

    @Mock
    private IProductRepository produtoRepositorio;

    private Map<String, Object> map;

    @Before
    public void beforeTest() {

        map = new HashMap<>();
    }

    @Test
    public void existeDeveriaRetornarFalse() {

        Categoria categoria = CategoriaFactory.getCategoria();

        map.put("descricao", categoria.getDescricao().toLowerCase());

        Mockito.when(repo.getMany("Category.buscarPorDescricao", map)).thenReturn(null);

        boolean existe = categoriaSC.existe(categoria);

        assertFalse(existe);
    }

    @Test
    public void existeDeveriaRetornarTrue() {

        Categoria categoria = CategoriaFactory.getCategoria();

        List<Categoria> categorias = CategoriaFactory.getLista();

        map.put("descricao", categoria.getDescricao().toLowerCase());

        Mockito.when(repo.getMany("Category.buscarPorDescricao", map)).thenReturn(categorias);

        boolean existe = categoriaSC.existe(categoria);

        assertTrue(existe);
    }

    @Test
    public void listarDeveriaTrazerListaVazia() {

        Mockito.when(repo.getAll()).thenReturn(new ArrayList<>());

        List<Categoria> categorias = categoriaSC.listar();

        Assert.assertEquals(0, categorias.size());
    }

    @Test
    public void salvarDeveriaRetornarInvalida() {

        Categoria categoria = CategoriaFactory.getCategoria();

        categoria.setDescricao("");

        StatusCategoriaEnum statusCategoriaEnum = categoriaSC.salvar(categoria);

        Assert.assertEquals(INVALIDA, statusCategoriaEnum);
    }

    @Test
    public void salvarDeveriaRetornarExistente() {

        Categoria categoria = CategoriaFactory.getCategoria();

        List<Categoria> categorias = CategoriaFactory.getLista();

        map.put("descricao", categoria.getDescricao().toLowerCase());

        Mockito.when(repo.getMany("Category.buscarPorDescricao", map)).thenReturn(categorias);

        StatusCategoriaEnum statusCategoriaEnum = categoriaSC.salvar(categoria);

        Assert.assertEquals(EXISTENTE, statusCategoriaEnum);
    }

    @Test
    public void salvarDeveriaRetornarSalva() {

        Categoria categoria = CategoriaFactory.getCategoria();

        map.put("descricao", categoria.getDescricao().toLowerCase());

        Mockito.when(repo.getMany("Category.buscarPorDescricao", map)).thenReturn(new ArrayList<>());

        Mockito.doNothing().when(repo).update(categoria);

        Mockito.doNothing().when(unitOfWork).commit();

        StatusCategoriaEnum statusCategoriaEnum = categoriaSC.salvar(categoria);

        Assert.assertEquals(SALVA, statusCategoriaEnum);
    }

    @Test
    public void excluirDeveriaRetornarExistemProdutosAssociados() {

        Categoria categoria = CategoriaFactory.getCategoria();

        map.put("categoriaId", categoria.getId());

        List<Produto> produtos = ProdutoFactory.getLista();

        Mockito.when(produtoRepositorio.getMany("Product.buscarPorCategoria", map)).thenReturn(produtos);

        Mockito.doNothing().when(repo).delete(categoria.getId());

        StatusCategoriaEnum statusCategoriaEnum = categoriaSC.excluir(categoria.getId());

        Assert.assertEquals(CATEGORIA_COM_PRODUTOS, statusCategoriaEnum);
    }

    @Test
    public void excluirDeveriaRetornarExcluida() {

        Categoria categoria = CategoriaFactory.getCategoria();

        map.put("categoriaId", categoria.getId());

        Mockito.when(produtoRepositorio.getMany("Product.buscarPorCategoria", map)).thenReturn(new ArrayList<>());

        Mockito.doNothing().when(repo).delete(categoria.getId());

        StatusCategoriaEnum statusCategoriaEnum = categoriaSC.excluir(categoria.getId());

        Assert.assertEquals(EXCLUIDA, statusCategoriaEnum);
    }
}
