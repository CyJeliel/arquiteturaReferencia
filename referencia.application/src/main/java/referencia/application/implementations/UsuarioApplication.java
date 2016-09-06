package referencia.application.implementations;

import static javax.ejb.TransactionAttributeType.REQUIRED;
import static referencia.domain.enumeracoes.StatusCredencialEnum.VALIDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.TransactionAttribute;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import referencia.application.interfaces.ICredenciaisApplication;
import referencia.domain.entities.Usuario;
import referencia.domain.enumeracoes.StatusCredencialEnum;
import referencia.domain.interfaces.IUnitOfWork;
import referencia.domain.interfaces.repository.IUserRepository;

@Slf4j
public abstract class UsuarioApplication {

    protected IUserRepository repo;

    private IUnitOfWork unitOfWork;

    protected ICredenciaisApplication credenciaisApplication;

    public UsuarioApplication() {

        // Empty constructor for instatiating EJB
    }

    @Inject
    public UsuarioApplication(IUserRepository repo, IUnitOfWork unitOfWork, ICredenciaisApplication credenciaisApplication) {

        this.repo = repo;

        this.unitOfWork = unitOfWork;

        this.credenciaisApplication = credenciaisApplication;
    }

    @TransactionAttribute(REQUIRED)
    public StatusCredencialEnum registrar(Usuario usuario) {

        StatusCredencialEnum status = validar(usuario);

        if (VALIDO.equals(status)) {

            repo.add(usuario);

            unitOfWork.commit();

            log.info("Usuário registrado: {}", usuario);
        }

        return status;
    }

    public StatusCredencialEnum salvar(Usuario usuario) {

        if (usuario.getId() != null) {

            return atualizar(usuario);

        } else {

            return registrar(usuario);
        }
    }

    @TransactionAttribute(REQUIRED)
    public StatusCredencialEnum atualizar(Usuario usuario) {

        StatusCredencialEnum status = validar(usuario);

        if (VALIDO.equals(status)) {

            usuario.setSenha(usuario.getNovaSenha());

            repo.update(usuario);

            unitOfWork.commit();
        }

        return status;
    }

    public abstract StatusCredencialEnum validar(Usuario usuario);

    public boolean existe(Usuario usuario) {

        Map<String, Object> propriedades = new HashMap<>();

        propriedades.put("login", usuario.getLogin());

        List<Usuario> usuarios = (List<Usuario>) repo.getMany("User.buscarPorLogin", propriedades);

        if (usuarios != null && !usuarios.isEmpty()) {

            return true;
        }

        return false;
    }

    public Usuario buscar(String login) {

        Map<String, Object> propriedades = new HashMap<>();

        propriedades.put("login", login);

        List<Usuario> usuarios = (List<Usuario>) repo.getMany("User.buscarPorLogin", propriedades);

        if (usuarios != null && usuarios.size() == 1) {

            return usuarios.get(0);
        }

        return null;
    }

    public Usuario buscar(Usuario usuario) {

        List<Usuario> usuarios = credenciaisApplication.verificarCredenciais(usuario);

        if (usuarios != null && usuarios.size() == 1) {

            return usuarios.get(0);
        }

        return null;
    }
}
