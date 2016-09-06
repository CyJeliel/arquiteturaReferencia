package referencia.repository.mapper.implementations.entities;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import referencia.repository.mapper.implementations.entities.core.BaseEntity;

@Entity
@Table(name = "Category")
@NamedQueries({ @NamedQuery(name = "Category.listar", query = "SELECT categoria FROM Category categoria"),
        @NamedQuery(name = "Category.buscarPorProduto", query = "SELECT categoria FROM Category categoria WHERE categoria.id = (SELECT categoria.id FROM Product produto WHERE produto.id = :id)"),
        @NamedQuery(name = "Category.buscarPorDescricao", query = "SELECT categoria FROM Category categoria WHERE categoria.descricao = :descricao") })
public class Category extends BaseEntity<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = IDENTITY)
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    @Column(name = "Description", columnDefinition = "nvarchar")
    private String descricao;
}
