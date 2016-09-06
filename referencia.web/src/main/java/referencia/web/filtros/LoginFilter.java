package referencia.web.filtros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginFilter implements Filter {

    @Override
    public void destroy() {

        //Nada a ser feito ao destruir o objeto

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((HttpServletRequest) request).getSession();
        
        Object usuario = session.getAttribute("usuario");
        
        if(usuario != null){
        
            chain.doFilter(request, response);
            
        } else {
            
            ((HttpServletResponse) response).sendRedirect("/referencia/public/login.jsf");
        }

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

        //Nada a ser feito ao construir o objeto

    }

}
