package referencia.application.interfaces;

import java.util.List;

import referencia.domain.entities.Usuario;

public interface ICredenciaisApplication {

    List<Usuario> verificarCredenciais(Usuario usuario);
}
