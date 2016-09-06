package referencia.domain.factory;

import java.util.ArrayList;
import java.util.List;

import referencia.domain.entities.Categoria;

public class CategoriaFactory {

    private CategoriaFactory() {

    }

    public static List<Categoria> getLista() {

        List<Categoria> categorias = new ArrayList<>();

        Categoria categoria1 = new Categoria();

        Categoria categoria2 = new Categoria();

        Categoria categoria3 = new Categoria();

        Categoria categoria4 = new Categoria();

        Categoria categoria5 = new Categoria();

        Categoria categoria6 = new Categoria();

        categoria1.setDescricao("Wagner");

        categoria2.setDescricao("Eduardo");

        categoria3.setDescricao("João");

        categoria4.setDescricao("Letícia");

        categoria5.setDescricao("Amanda");

        categoria6.setDescricao("Cindy");

        categorias.add(categoria1);

        categorias.add(categoria2);

        categorias.add(categoria3);

        categorias.add(categoria4);

        categorias.add(categoria5);

        categorias.add(categoria6);

        return categorias;
    }

    public static Categoria getCategoria() {

        Categoria categoria = new Categoria();

        categoria.setDescricao("Cindy de Albuquerque");

        categoria.setId(1);

        return categoria;
    }
}
