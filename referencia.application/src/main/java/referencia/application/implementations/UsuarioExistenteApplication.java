package referencia.application.implementations;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static referencia.domain.enumeracoes.StatusCredencialEnum.INVALIDO;
import static referencia.domain.enumeracoes.StatusCredencialEnum.SENHA_INCORRETA;
import static referencia.domain.enumeracoes.StatusCredencialEnum.VALIDO;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import referencia.application.interfaces.ICredenciaisApplication;
import referencia.application.interfaces.IUsuarioApplication;
import referencia.domain.entities.Usuario;
import referencia.domain.enumeracoes.StatusCredencialEnum;
import referencia.domain.interfaces.IUnitOfWork;
import referencia.domain.interfaces.repository.IUserRepository;

@Stateless
public class UsuarioExistenteApplication extends UsuarioApplication implements IUsuarioApplication {

    public UsuarioExistenteApplication() {
        super(null, null, null);
    }

    @Inject
    public UsuarioExistenteApplication(IUserRepository repo, IUnitOfWork unitOfWork, ICredenciaisApplication credenciaisApplication) {

        super(repo, unitOfWork, credenciaisApplication);
    }

    @Override
    public StatusCredencialEnum validar(Usuario usuario) {

        SenhaApplication senhaBC = new AlteracaoDeSenhaApplication(usuario);

        StatusCredencialEnum status = INVALIDO;

        if (!isBlank(usuario.getNome())) {

            status = senhaBC.validarSenha();

            if (VALIDO.equals(status) && !existe(usuario)) {

                status = SENHA_INCORRETA;
            }

        }

        return status;
    }

    @Override
    public boolean existe(Usuario usuario) {

        List<Usuario> usuarios = credenciaisApplication.verificarCredenciais(usuario);

        if (usuarios != null && !usuarios.isEmpty()) {

            return true;
        }

        return false;
    }
}
