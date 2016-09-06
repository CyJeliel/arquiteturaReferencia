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

/**
 * 
 * @author Cindy
 *
 */
@Entity
@Table(name = "Usuario")
@NamedQueries({

        @NamedQuery(name = "User.verificarCredenciais", query = "SELECT usuario FROM User usuario WHERE usuario.login = :login AND usuario.senha = :senha"),
        @NamedQuery(name = "User.buscarPorLogin", query = "SELECT usuario FROM User usuario WHERE usuario.login = :login") })
public class User extends BaseEntity<Integer> {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @Column(name = "Id")
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Getter
    @Setter
    @Column(name = "Login", columnDefinition = "varchar")
    private String login;

    @Getter
    @Setter
    @Column(name = "Senha", columnDefinition = "varchar")
    private String senha;

    @Getter
    @Setter
    @Column(name = "Nome", columnDefinition = "nvarchar")
    private String nome;

    @Setter
    @Getter
    private transient String confirmacaoSenha;

    @Setter
    @Getter
    private transient String novaSenha;
}
