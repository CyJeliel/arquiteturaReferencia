package referencia.application.interfaces;

import referencia.domain.entities.Usuario;
import referencia.domain.enumeracoes.StatusCredencialEnum;

public interface IUsuarioApplication {

    StatusCredencialEnum registrar(Usuario usuario);

    StatusCredencialEnum salvar(Usuario usuario);

    Usuario buscar(Usuario usuario);

    Usuario buscar(String login);
}
