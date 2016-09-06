package referencia.application.implementations;

import static referencia.domain.enumeracoes.StatusUsuarioEnum.LOGADO;
import static referencia.domain.enumeracoes.StatusUsuarioEnum.NAO_LOGADO;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import referencia.application.interfaces.ICredenciaisApplication;
import referencia.application.interfaces.ILoginApplication;
import referencia.domain.entities.Usuario;
import referencia.domain.enumeracoes.StatusUsuarioEnum;

@Stateless
public class LoginApplication implements ILoginApplication {

    private ICredenciaisApplication credenciaisApplication;

    public LoginApplication() {
        // Empty constructor for instatiating EJB
    }

    @Inject
    public LoginApplication(ICredenciaisApplication credenciaisApplication) {

        this.credenciaisApplication = credenciaisApplication;
    }

    @Override
    public StatusUsuarioEnum login(Usuario usuario) {

        StatusUsuarioEnum status = NAO_LOGADO;

        List<Usuario> usuarios = credenciaisApplication.verificarCredenciais(usuario);

        if (usuarios != null && usuarios.size() == 1) {

            return LOGADO;
        }

        return status;
    }
}
