package referencia.web.managedbeans;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "referenciaMBean")
public class ReferenciaMBean extends AbstractManagedBean {

    private static final long serialVersionUID = 1L;

    public String callPagCredenciais() {

        return "/pages/credenciais";
    }

    public String callPagClientes() {

        return "/pages/clientes";
    }

    public String callPagProdutos() {

        return "/pages/produtos";
    }

    public String callPagCategorias() {

        return "/pages/categorias";
    }

    public String callPagContato() {

        return "/public/contato";
    }

    public String callPagSobre() {

        return "/public/sobre";
    }

    public String callPagDetalheProjeto() {

        return "/pages/detalheProjeto";
    }

    public String callPagHome() {

        boolean logado = getLoginMBean().isLogado();

        if (logado) {

            return "/pages/home";
        }

        return "/public/login";
    }

    public String callPagLogin() {

        return "/public/login";
    }
}
