package referencia.repository.mapper.implementations.entities;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import referencia.repository.mapper.implementations.entities.core.BaseEntity;

@Entity
@Table(name = "Product")
@NamedQueries({ @NamedQuery(name = "Product.listar", query = "SELECT produto FROM Product produto"), @NamedQuery(name = "Produto.buscarPorDescricao", query = "SELECT produto FROM Product produto WHERE produto.descricao = :descricao"),
        @NamedQuery(name = "Product.buscarPorCategoria", query = "SELECT produto FROM Product produto WHERE produto.categoria.id = :categoriaId") })
public class Product extends BaseEntity<Integer> {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "Price", columnDefinition = "decimal")
    @Getter
    @Setter
    private BigDecimal preco;

    @Getter
    @Setter
    @Column(name = "Stock")
    private int estoque;

    @Getter
    @Setter
    @Column(name = "Name", columnDefinition = "nvarchar")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "CategoryId")
    @Getter
    @Setter
    private Category categoria;
}
