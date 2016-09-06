package referencia.application.implementations;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static referencia.domain.enumeracoes.StatusCredencialEnum.INVALIDO;
import static referencia.domain.enumeracoes.StatusCredencialEnum.LOGIN_INVALIDO;
import static referencia.domain.enumeracoes.StatusCredencialEnum.LOGIN_JA_EXISTE;
import static referencia.domain.enumeracoes.StatusCredencialEnum.VALIDO;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import referencia.application.interfaces.ICredenciaisApplication;
import referencia.application.interfaces.IUsuarioApplication;
import referencia.domain.entities.Usuario;
import referencia.domain.enumeracoes.StatusCredencialEnum;
import referencia.domain.interfaces.IUnitOfWork;
import referencia.domain.interfaces.repository.IUserRepository;

@Stateless
@Slf4j
public class NovoUsuarioApplication extends UsuarioApplication implements IUsuarioApplication {

    public NovoUsuarioApplication() {
        super(null, null, null);
    }

    @Inject
    public NovoUsuarioApplication(IUserRepository repo, IUnitOfWork unitOfWork, ICredenciaisApplication credenciaisApplication) {

        super(repo, unitOfWork, credenciaisApplication);
    }

    @Override
    public StatusCredencialEnum validar(Usuario usuario) {

        SenhaApplication senhaBC = new NovoUsuarioSenhaApplication(usuario);

        StatusCredencialEnum status = VALIDO;

        String login = usuario.getLogin();

        String senha = usuario.getSenha();

        String confirmacaoSenha = usuario.getConfirmacaoSenha();

        if (isBlank(login) || isBlank(senha) || isBlank(confirmacaoSenha) || isBlank(usuario.getNome())) {

            status = INVALIDO;

        } else if (login.length() < 5) {

            status = LOGIN_INVALIDO;

        } else {

            status = senhaBC.validarSenha();

            if (VALIDO.equals(status)) {

                status = verificaExistencia(usuario);
            }
        }

        return status;
    }

    public StatusCredencialEnum verificaExistencia(Usuario usuario) {

        StatusCredencialEnum status = VALIDO;

        if (existe(usuario)) {

            status = LOGIN_JA_EXISTE;

            log.info("Usuário já existe: {}", usuario);
        }

        return status;
    }
}
