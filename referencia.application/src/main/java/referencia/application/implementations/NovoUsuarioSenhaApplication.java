package referencia.application.implementations;

import static org.apache.commons.lang3.StringUtils.isBlank;

import javax.inject.Inject;

import referencia.domain.entities.Usuario;

public class NovoUsuarioSenhaApplication extends SenhaApplication {

    public NovoUsuarioSenhaApplication() {
        super(null, null);
    }

    @Inject
    public NovoUsuarioSenhaApplication(Usuario usuario) {

        super(usuario.getSenha(), usuario.getConfirmacaoSenha());
    }

    @Override
    public boolean validarTodosCamposPreenchidos() {

        return !isBlank(getSenha()) && !isBlank(getConfirmacaoDeSenha());
    }
}
