package referencia.web.managedbeans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import lombok.Getter;
import lombok.Setter;
import referencia.application.implementations.excecoes.BCException;
import referencia.application.interfaces.IUsuarioApplication;
import referencia.domain.entities.Usuario;
import referencia.domain.enumeracoes.StatusCredencialEnum;

@ManagedBean(name = "credencialMBean")
@ViewScoped
public class CredencialMBean extends AbstractManagedBean {

    private static final long serialVersionUID = 1L;

    @EJB(beanName = "UsuarioExistenteApplication")
    private transient IUsuarioApplication usuarioSC;

    @Getter
    private String feedback;

    @Getter
    private String tipoFeedback;

    @Getter
    private String titulo;

    @Getter
    @Setter
    private transient Usuario usuarioAtualizado;

    @Getter
    @Setter
    private transient Usuario usuario;

    @PostConstruct
    public void loadUsuario() {

        usuario = getLoginMBean().getUsuario();

        usuarioAtualizado = usuario;
    }

    public void salvar() throws BCException {

        StatusCredencialEnum status = usuarioSC.salvar(usuarioAtualizado);

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();
    }

    public void reset() {

        usuarioAtualizado.setLogin(usuario.getLogin());

        usuarioAtualizado.setNome(usuario.getNome());

        usuarioAtualizado.setSenha(null);

        feedback = "";
    }
}
