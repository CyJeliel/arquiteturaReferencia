package referencia.application.implementations;

import static org.apache.commons.lang3.StringUtils.isBlank;

import lombok.Setter;
import referencia.domain.entities.Usuario;

public class AlteracaoDeSenhaApplication extends SenhaApplication {

    @Setter
    private String senhaAntiga;

    public AlteracaoDeSenhaApplication() {
        super(null, null);
    }

    public AlteracaoDeSenhaApplication(Usuario usuario) {

        super(usuario.getNovaSenha(), usuario.getConfirmacaoSenha());

        this.senhaAntiga = usuario.getSenha();
    }

    @Override
    public boolean validarTodosCamposPreenchidos() {

        return !isBlank(senhaAntiga) && !isBlank(getSenha()) && !isBlank(getConfirmacaoDeSenha());
    }
}
