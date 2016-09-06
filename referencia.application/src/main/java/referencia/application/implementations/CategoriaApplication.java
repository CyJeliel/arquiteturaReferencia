package referencia.application.implementations;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static referencia.domain.enumeracoes.StatusCategoriaEnum.CATEGORIA_COM_PRODUTOS;
import static referencia.domain.enumeracoes.StatusCategoriaEnum.EXCLUIDA;
import static referencia.domain.enumeracoes.StatusCategoriaEnum.EXISTENTE;
import static referencia.domain.enumeracoes.StatusCategoriaEnum.INVALIDA;
import static referencia.domain.enumeracoes.StatusCategoriaEnum.SALVA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;

import referencia.application.implementations.excecoes.BCException;
import referencia.application.interfaces.ICategoriaApplication;
import referencia.domain.entities.Categoria;
import referencia.domain.entities.Produto;
import referencia.domain.enumeracoes.StatusCategoriaEnum;
import referencia.domain.interfaces.IUnitOfWork;
import referencia.domain.interfaces.repository.ICategoryRepository;
import referencia.domain.interfaces.repository.IProductRepository;

@Stateless
public class CategoriaApplication implements ICategoriaApplication {

    private ICategoryRepository repo;

    private IProductRepository produtoRepositorio;

    private IUnitOfWork unitOfWork;

    public CategoriaApplication() {
        // Empty constructor for instatiating EJB
    }

    @Inject
    public CategoriaApplication(ICategoryRepository repo, IProductRepository produtoRepositorio, IUnitOfWork unitOfWork) {

        this.repo = repo;

        this.produtoRepositorio = produtoRepositorio;

        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Categoria> listar() {

        List<Categoria> categorias = (List<Categoria>) repo.getAll();

        if (categorias == null) {

            categorias = new ArrayList<>();
        }

        return categorias;
    }

    @Override
    public Categoria buscarPor(Produto produto) throws BCException {

        Map<String, Object> filtros = new HashMap<>();

        filtros.put("idProduto", produto.getId());

        return repo.get("Category.buscarPorProduto", filtros);
    }

    @Override
    public StatusCategoriaEnum salvar(Categoria categoria) {

        StatusCategoriaEnum status;

        if (isBlank(categoria.getDescricao())) {

            status = INVALIDA;

        } else {

            boolean existe = existe(categoria);

            if (existe) {

                status = EXISTENTE;

            } else {

                status = gravar(categoria);
            }
        }

        return status;
    }

    @TransactionAttribute(REQUIRED)
    private StatusCategoriaEnum gravar(Categoria categoria) {

        StatusCategoriaEnum status;

        if (categoria.getId() != null) {

            repo.update(categoria);

            unitOfWork.commit();

        } else {

            repo.add(categoria);

            unitOfWork.commit();
        }

        status = SALVA;

        return status;
    }

    @Override
    public boolean existe(Categoria categoria) {

        boolean existe = false;

        List<Categoria> categorias = listar(categoria);

        if (isNotEmpty(categorias)) {

            if (categoria.getId() == null) {

                existe = true;

            } else if (!categorias.contains(categoria)) {

                existe = true;
            }
        }

        return existe;
    }

    private List<Categoria> listar(Categoria categoria) {

        Map<String, Object> map = new HashMap<>();

        map.put("descricao", categoria.getDescricao().toLowerCase());

        return (List<Categoria>) repo.getMany("Category.buscarPorDescricao", map);
    }

    @Override
    public StatusCategoriaEnum excluir(Integer id) {

        StatusCategoriaEnum status = EXCLUIDA;

        Map<String, Object> map = new HashMap<>();

        map.put("categoriaId", id);

        List<Produto> produtos = (List<Produto>) produtoRepositorio.getMany("Product.buscarPorCategoria", map);

        if (CollectionUtils.isEmpty(produtos)) {

            repo.delete(id);

        } else {

            status = CATEGORIA_COM_PRODUTOS;
        }

        return status;
    }

    @Override
    public Categoria buscar(Integer id) {

        return repo.getById(id);
    }

}
