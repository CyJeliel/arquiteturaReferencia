package referencia.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Cindy
 *
 */
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String senha;

    @Getter
    @Setter
    private String nome;

    @Getter
    @Setter
    private String confirmacaoSenha;

    @Getter
    @Setter
    private String novaSenha;
}
