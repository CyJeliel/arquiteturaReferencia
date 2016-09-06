package referencia.application.implementations;

import static java.math.BigDecimal.ZERO;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static referencia.domain.enumeracoes.StatusProdutoEnum.ESTOQUE_NEGATIVO;
import static referencia.domain.enumeracoes.StatusProdutoEnum.EXCLUIDO;
import static referencia.domain.enumeracoes.StatusProdutoEnum.EXISTENTE;
import static referencia.domain.enumeracoes.StatusProdutoEnum.INVALIDO;
import static referencia.domain.enumeracoes.StatusProdutoEnum.PRECO_NEGATIVO;
import static referencia.domain.enumeracoes.StatusProdutoEnum.SALVO;
import static referencia.domain.enumeracoes.StatusProdutoEnum.VALIDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;

import referencia.application.implementations.excecoes.BCException;
import referencia.application.interfaces.IProdutoApplication;
import referencia.domain.entities.Categoria;
import referencia.domain.entities.Produto;
import referencia.domain.enumeracoes.StatusProdutoEnum;
import referencia.domain.interfaces.IUnitOfWork;
import referencia.domain.interfaces.repository.ICategoryRepository;
import referencia.domain.interfaces.repository.IProductRepository;

@Stateless
public class ProdutoApplication implements IProdutoApplication {

    private IProductRepository repo;

    private ICategoryRepository categoriaRepo;

    private IUnitOfWork unitOfWork;

    public ProdutoApplication() {
        // Empty constructor for instatiating EJB
    }

    @Inject
    public ProdutoApplication(IProductRepository repo, ICategoryRepository categoriaRepo, IUnitOfWork unitOfWork) {

        this.repo = repo;

        this.categoriaRepo = categoriaRepo;

        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Produto> listar() {

        return (List<Produto>) repo.getAll();

    }

    @Override
    public StatusProdutoEnum salvar(Produto produto) {

        StatusProdutoEnum status = isValido(produto);

        if (VALIDO.equals(status)) {

            boolean existe = existe(produto);

            if (existe) {

                status = EXISTENTE;

            } else {

                status = gravar(produto);
            }
        }

        return status;
    }

    @TransactionAttribute(REQUIRED)
    private StatusProdutoEnum gravar(Produto produto) {

        StatusProdutoEnum status;

        aplicarCategoria(produto);

        if (produto.getId() != null) {

            repo.update(produto);

            unitOfWork.commit();

        } else {

            repo.add(produto);

            unitOfWork.commit();
        }

        status = SALVO;

        return status;
    }

    private void aplicarCategoria(Produto produto) {

        Categoria categoria = categoriaRepo.getById(produto.getCategoria().getId());

        produto.setCategoria(categoria);
    }

    @Override
    public boolean existe(Produto produto) {

        boolean existe = false;

        Map<String, Object> map = new HashMap<>();

        map.put("descricao", produto.getDescricao().toLowerCase());

        List<Produto> produtos = (List<Produto>) repo.getMany("Product.buscarPorDescricao", map);

        if (isNotEmpty(produtos)) {

            if (produto.getId() == null) {

                existe = true;

            } else if (!produtos.contains(produto)) {

                existe = true;
            }
        }

        return existe;
    }

    @Override
    public StatusProdutoEnum excluir(Integer id) {

        StatusProdutoEnum status = EXCLUIDO;

        repo.delete(id);

        return status;
    }

    @Override
    public Produto buscar(Integer id) throws BCException {

        return repo.getById(id);

    }

    public StatusProdutoEnum isValido(Produto produto) {

        Categoria categoria = produto.getCategoria();

        if (isBlank(produto.getDescricao()) || produto.getPreco() == null || categoria == null || isBlank(categoria.getDescricao())) {

            return INVALIDO;
        }

        if (produto.getEstoque() < 0) {

            return ESTOQUE_NEGATIVO;
        }

        if (produto.getPreco().compareTo(ZERO) < 0) {

            return PRECO_NEGATIVO;
        }

        return VALIDO;
    }
}
