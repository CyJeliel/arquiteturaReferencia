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
@Table(name = "TB_CLIENTE")
@NamedQueries({ @NamedQuery(name = "Client.listar", query = "SELECT cliente FROM Client cliente"), @NamedQuery(name = "Client.buscarPorDescricao", query = "SELECT cliente FROM Client cliente WHERE cliente.nome = :nome") })
public class Client extends BaseEntity<Integer> {

    private static final long serialVersionUID = 1L;

    @Id
    @Getter
    @Setter
    @Column(name = "Cod_Cliente")
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(name = "NM_Cliente", columnDefinition = "varchar")
    private String nome;

    @Getter
    @Setter
    @Column(name = "DS_Endereco", columnDefinition = "varchar")
    private String enderecoDescricao;

    @Getter
    @Setter
    @Column(name = "Bairro", columnDefinition = "varchar")
    private String bairro;

    @Getter
    @Setter
    @Column(name = "DS_Compl_End", columnDefinition = "varchar")
    private String enderecoComplemento;

    @Getter
    @Setter
    @Column(name = "CD_CEP", columnDefinition = "varchar")
    private Integer cep;

    @Getter
    @Setter
    @Column(name = "Telefone", columnDefinition = "varchar")
    private String telefone;

    @Getter
    @Setter
    @Column(name = "NM_Email", columnDefinition = "varchar")
    private String email;
}
