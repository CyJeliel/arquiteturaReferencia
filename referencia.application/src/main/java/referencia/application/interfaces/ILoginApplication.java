package referencia.application.interfaces;

import referencia.domain.entities.Usuario;
import referencia.domain.enumeracoes.StatusUsuarioEnum;

public interface ILoginApplication {

    StatusUsuarioEnum login(Usuario user);

}
