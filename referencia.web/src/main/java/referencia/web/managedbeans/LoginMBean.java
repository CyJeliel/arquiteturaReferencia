package referencia.web.managedbeans;

import static referencia.domain.enumeracoes.StatusUsuarioEnum.LOGADO;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import referencia.application.interfaces.ILoginApplication;
import referencia.application.interfaces.IUsuarioApplication;
import referencia.domain.entities.Usuario;
import referencia.domain.enumeracoes.StatusUsuarioEnum;

@SessionScoped
@ManagedBean(name = "loginMBean")
@Slf4j
public class LoginMBean implements Serializable {

    private static final String PAGINA_LOGIN = "/public/login?faces-redirect=true";

    private static final long serialVersionUID = 1L;

    private static final String USUARIO = "usuario";

    private static final String EMPTY = "";

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String senha;

    @Getter
    @Setter
    private String feedback;

    @Getter
    private String tipoFeedback;

    @Getter
    @Setter
    private Boolean isAdmin;

    @Getter
    @Setter
    private String menu;

    @EJB
    private transient ILoginApplication loginSC;

    @EJB(beanName = "UsuarioExistenteApplication")
    private transient IUsuarioApplication usuarioSC;

    /**
     * Limpa os campos da sessão.
     */
    public void reset() {

        log.info("logout {}", getLogin());

        setLogin(EMPTY);

        senha = EMPTY;
    }

    public String logout() {

        HttpServletRequest request = getRequest();

        try {

            request.logout();

            request.getSession().setAttribute(USUARIO, null);

        } catch (ServletException e) {

            request.getSession().setAttribute(USUARIO, null);

            log.error("Erro ao realizar logout", e);
        }

        return PAGINA_LOGIN;
    }

    /**
     * Login no sistema.
     * 
     * @return
     */
    public String loginUser() {

        Usuario usuario = getUsuario();

        if (usuario != null) {

            log.info("User: {} ja esta autenticado", getLogin());

            return "/pages/home?faces-redirect=true";

        }

        usuario = new Usuario();

        usuario.setLogin(login);

        usuario.setSenha(senha);

        StatusUsuarioEnum status = loginSC.login(usuario);

        feedback = status.getFeedback();

        if (LOGADO.equals(status)) {

            menu = "/pages/menu.xhtml";

            usuario = usuarioSC.buscar(usuario);

            getRequest().getSession().setAttribute(USUARIO, usuario);

            return "/pages/home?faces-redirect=true";
        }

        return PAGINA_LOGIN;
    }

    public boolean isLogado() {

        Usuario usuarioLogado = getUsuario();

        if (usuarioLogado != null) {

            return true;
        }

        return false;
    }
    
    private HttpServletRequest getRequest() {
        
        FacesContext context = getContext();
        
        return (HttpServletRequest) context.getExternalContext().getRequest();
    }
    
    private FacesContext getContext() {
        
        return FacesContext.getCurrentInstance();
    }
    
    public Usuario getUsuario() {
        
        return (Usuario) getRequest().getSession().getAttribute(USUARIO);
    }
    
    public String getBarraMenu() {
        
        return "/pages/barraMenu.xhtml";
    }
    
    public String paginaLogin() {
        
        return PAGINA_LOGIN;
    }
}
