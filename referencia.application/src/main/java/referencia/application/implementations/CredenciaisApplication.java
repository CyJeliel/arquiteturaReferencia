package referencia.application.implementations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import referencia.application.interfaces.ICredenciaisApplication;
import referencia.domain.entities.Usuario;
import referencia.domain.interfaces.repository.IUserRepository;

@Stateless
public class CredenciaisApplication implements ICredenciaisApplication {

    private static final String LOGIN = "login";

    private IUserRepository repo;

    public CredenciaisApplication() {
        // Empty constructor for instatiating EJB
    }

    @Inject
    public CredenciaisApplication(IUserRepository repo) {

        this.repo = repo;
    }

    @Override
    public List<Usuario> verificarCredenciais(Usuario usuario) {

        Map<String, Object> propriedades = new HashMap<>();

        propriedades.put(LOGIN, usuario.getLogin());

        propriedades.put("senha", usuario.getSenha());

        return (List<Usuario>) repo.getMany("User.verificarCredenciais", propriedades);
    }

}
