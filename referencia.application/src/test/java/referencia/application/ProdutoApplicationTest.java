package referencia.application;

import static referencia.domain.enumeracoes.StatusProdutoEnum.ESTOQUE_NEGATIVO;
import static referencia.domain.enumeracoes.StatusProdutoEnum.EXCLUIDO;
import static referencia.domain.enumeracoes.StatusProdutoEnum.EXISTENTE;
import static referencia.domain.enumeracoes.StatusProdutoEnum.INVALIDO;
import static referencia.domain.enumeracoes.StatusProdutoEnum.PRECO_NEGATIVO;
import static referencia.domain.enumeracoes.StatusProdutoEnum.SALVO;

import java.math.BigDecimal;
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
import referencia.application.implementations.ProdutoApplication;
import referencia.domain.entities.Categoria;
import referencia.domain.entities.Produto;
import referencia.domain.enumeracoes.StatusProdutoEnum;
import referencia.domain.factory.CategoriaFactory;
import referencia.domain.factory.ProdutoFactory;
import referencia.domain.interfaces.IUnitOfWork;
import referencia.domain.interfaces.repository.ICategoryRepository;
import referencia.domain.interfaces.repository.IProductRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProdutoApplicationTest extends TestCase {

    @InjectMocks
    private ProdutoApplication produtoSC = new ProdutoApplication();

    @Mock
    private IProductRepository repo;

    @Mock
    private ICategoryRepository categoriaRepo;
    
    @Mock
    private IUnitOfWork unitOfWork;

    private Map<String, Object> map;

    @Before
    public void beforeTest() {

        map = new HashMap<>();
    }

    @Test
    public void existeDeveriaRetornarFalse() {

        Produto produto = ProdutoFactory.getProduto();

        map.put("descricao", produto.getDescricao().toLowerCase());

        Mockito.when(repo.getMany("Product.buscarPorDescricao", map)).thenReturn(null);

        boolean existe = produtoSC.existe(produto);

        assertFalse(existe);
    }

    @Test
    public void existeDeveriaRetornarTrue() {

        Produto produto = ProdutoFactory.getProduto();

        List<Produto> produtos = ProdutoFactory.getLista();

        map.put("descricao", produto.getDescricao().toLowerCase());

        Mockito.when(repo.getMany("Product.buscarPorDescricao", map)).thenReturn(produtos);

        boolean existe = produtoSC.existe(produto);

        assertTrue(existe);
    }

    @Test
    public void listarDeveriaTrazerListaVazia() {

        Mockito.when(repo.getAll()).thenReturn(new ArrayList<>());

        List<Produto> produtos = produtoSC.listar();

        Assert.assertEquals(0, produtos.size());
    }

    @Test
    public void salvarDeveriaRetornarDescricaoInvalida() {

        Produto produto = ProdutoFactory.getProdutoCompleto();

        produto.setDescricao("");

        StatusProdutoEnum statusProdutoEnum = produtoSC.salvar(produto);

        Assert.assertEquals(INVALIDO, statusProdutoEnum);
    }

    @Test
    public void salvarDeveriaRetornarPrecoNulo() {

        Produto produto = ProdutoFactory.getProdutoCompleto();

        produto.setPreco(null);

        StatusProdutoEnum statusProdutoEnum = produtoSC.salvar(produto);

        Assert.assertEquals(INVALIDO, statusProdutoEnum);
    }

    @Test
    public void salvarDeveriaRetornarCategoriaNula() {

        Produto produto = ProdutoFactory.getProdutoCompleto();

        produto.setCategoria(null);

        StatusProdutoEnum statusProdutoEnum = produtoSC.salvar(produto);

        Assert.assertEquals(INVALIDO, statusProdutoEnum);
    }

    @Test
    public void salvarDeveriaRetornarCategoriaInvalida() {

        Produto produto = ProdutoFactory.getProdutoCompleto();

        produto.setCategoria(new Categoria());

        StatusProdutoEnum statusProdutoEnum = produtoSC.salvar(produto);

        Assert.assertEquals(INVALIDO, statusProdutoEnum);
    }

    @Test
    public void salvarDeveriaRetornarEstoqueInvalido() {

        Produto produto = ProdutoFactory.getProdutoCompleto();

        produto.setEstoque(-99);

        StatusProdutoEnum statusProdutoEnum = produtoSC.salvar(produto);

        Assert.assertEquals(ESTOQUE_NEGATIVO, statusProdutoEnum);
    }

    @Test
    public void salvarDeveriaRetornarPrecoInvalido() {

        Produto produto = ProdutoFactory.getProdutoCompleto();

        produto.setPreco(new BigDecimal(-99));

        StatusProdutoEnum statusProdutoEnum = produtoSC.salvar(produto);

        Assert.assertEquals(PRECO_NEGATIVO, statusProdutoEnum);
    }

    @Test
    public void salvarDeveriaRetornarExistente() {

        Produto produto = ProdutoFactory.getProdutoCompleto();

        List<Produto> produtos = ProdutoFactory.getLista();

        map.put("descricao", produto.getDescricao().toLowerCase());

        Mockito.when(repo.getMany("Product.buscarPorDescricao", map)).thenReturn(produtos);

        StatusProdutoEnum statusProdutoEnum = produtoSC.salvar(produto);

        Assert.assertEquals(EXISTENTE, statusProdutoEnum);
    }

    @Test
    public void salvarDeveriaRetornarSalvo() {

        Produto produto = ProdutoFactory.getProdutoCompleto();

        map.put("descricao", produto.getDescricao().toLowerCase());

        Mockito.when(repo.getMany("Product.buscarPorDescricao", map)).thenReturn(new ArrayList<>());

        Mockito.when(categoriaRepo.getById(produto.getCategoria().getId())).thenReturn(CategoriaFactory.getCategoria());

        Mockito.doNothing().when(repo).update(produto);

        Mockito.doNothing().when(unitOfWork).commit();

        StatusProdutoEnum statusProdutoEnum = produtoSC.salvar(produto);

        Assert.assertEquals(SALVO, statusProdutoEnum);
    }

    @Test
    public void excluirDeveriaRetornarExcluido() {

        Produto produto = ProdutoFactory.getProdutoCompleto();

        Mockito.doNothing().when(repo).delete(produto.getId());

        StatusProdutoEnum statusProdutoEnum = produtoSC.excluir(produto.getId());

        Assert.assertEquals(EXCLUIDO, statusProdutoEnum);
    }
}
