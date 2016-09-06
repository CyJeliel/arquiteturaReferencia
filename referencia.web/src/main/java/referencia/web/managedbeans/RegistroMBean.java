package referencia.web.managedbeans;

import static referencia.domain.enumeracoes.StatusCredencialEnum.VALIDO;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import referencia.application.interfaces.IUsuarioApplication;
import referencia.domain.entities.Usuario;
import referencia.domain.enumeracoes.StatusCredencialEnum;

@SessionScoped
@ManagedBean(name = "registroMBean")
@Slf4j
public class RegistroMBean extends AbstractManagedBean {

    private static final long serialVersionUID = 1L;

    private static final String EMPTY = "";

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String senha;

    @Getter
    @Setter
    private String confirmacaoSenha;

    @Getter
    @Setter
    private String nome;

    @Getter
    @Setter
    private String feedback;

    @Getter
    @Setter
    private String tipoFeedback;

    @EJB(beanName = "NovoUsuarioApplication")
    private transient IUsuarioApplication registroSC;

    /**
     * Limpa os campos da sessão.
     */
    public void reset() {

        log.debug("reset {}", getLogin());

        setLogin(EMPTY);

        senha = EMPTY;
    }

    public String pagRegistrar() {

        return "/public/registroUsuario";
    }

    /**
     * Login no StarTeam
     * 
     * @param request
     * @param context
     * @return
     */
    public String registrar() {

        Usuario usuario = new Usuario();

        usuario.setLogin(login);

        usuario.setSenha(senha);

        usuario.setConfirmacaoSenha(confirmacaoSenha);

        usuario.setNome(nome);

        StatusCredencialEnum status = registroSC.salvar(usuario);

        if (VALIDO.equals(status)) {

            getLoginMBean().setLogin(usuario.getLogin());

            getLoginMBean().setSenha(usuario.getSenha());

            return getLoginMBean().loginUser();
        }

        feedback = status.getFeedback();

        tipoFeedback = status.getTipoFeedback();

        return "";
    }
}
