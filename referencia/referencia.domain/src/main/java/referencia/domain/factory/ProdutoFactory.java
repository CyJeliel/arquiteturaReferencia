package referencia.domain.factory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import referencia.domain.entities.Produto;

public class ProdutoFactory {

    private ProdutoFactory() {
    }

    public static List<Produto> getLista() {

        List<Produto> produtos = new ArrayList<>();

        Produto produto1 = new Produto();

        Produto produto2 = new Produto();

        Produto produto3 = new Produto();

        Produto produto4 = new Produto();

        Produto produto5 = new Produto();

        Produto produto6 = new Produto();

        produto1.setDescricao("Wagner");

        produto2.setDescricao("Eduardo");

        produto3.setDescricao("João");

        produto4.setDescricao("Letícia");

        produto5.setDescricao("Amanda");

        produto6.setDescricao("Cindy");

        produtos.add(produto1);

        produtos.add(produto2);

        produtos.add(produto3);

        produtos.add(produto4);

        produtos.add(produto5);

        produtos.add(produto6);

        return produtos;
    }

    public static Produto getProduto() {

        Produto produto = new Produto();

        produto.setDescricao("Cindy de Albuquerque");

        produto.setId(1);

        return produto;
    }

    public static Produto getProdutoCompleto() {

        Produto produto = new Produto();

        produto.setDescricao("Cindy de Albuquerque");

        produto.setId(1);

        produto.setCategoria(CategoriaFactory.getCategoria());

        produto.setEstoque(100);

        produto.setPreco(new BigDecimal(100));

        return produto;
    }
}
