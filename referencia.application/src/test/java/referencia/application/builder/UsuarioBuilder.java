package referencia.application.builder;

import referencia.domain.entities.Usuario;

public class UsuarioBuilder {

    private Usuario usuario;
    
    public UsuarioBuilder() {

        usuario = new Usuario();
    }
    
    public UsuarioBuilder comLogin(String login) {

        usuario.setLogin(login);
        
        return this;
    }
    
    public UsuarioBuilder comNome(String nome) {

        usuario.setNome(nome);
        
        return this;
    }
    
    public UsuarioBuilder comSenha(String senha) {

        usuario.setSenha(senha);
        
        return this;
    }
    
    public UsuarioBuilder comNovaSenha(String novaSenha) {

        usuario.setNovaSenha(novaSenha);
        
        return this;
    }
    
    public UsuarioBuilder comConfirmacaoDeSenha(String confirmacaoDeSenha) {

        usuario.setConfirmacaoSenha(confirmacaoDeSenha);
        
        return this;
    }
    
    public UsuarioBuilder comId(Integer id) {

        usuario.setId(id);;
        
        return this;
    }
    
    public Usuario build() {

        return usuario;
    }
}
