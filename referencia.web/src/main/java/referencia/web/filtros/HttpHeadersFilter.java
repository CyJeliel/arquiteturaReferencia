package referencia.web.filtros;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class HttpHeadersFilter implements Filter{

    @Override
    public void destroy() {

        //Nada a ser feito ao destruir o objeto
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        ((HttpServletResponse)response).addHeader("X-Content-Type-Options", "nosniff");
        
        ((HttpServletResponse)response).addHeader("X-XSS-Protection", "1; mode=block");
        
        ((HttpServletResponse)response).addHeader("X-Frame-Options", "DENY");
        
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

        //Nada a ser feito ao construir o objeto
    }

    
}
