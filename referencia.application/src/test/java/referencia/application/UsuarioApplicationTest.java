package referencia.application;

import static referencia.domain.enumeracoes.StatusCredencialEnum.INVALIDO;
import static referencia.domain.enumeracoes.StatusCredencialEnum.LOGIN_INVALIDO;
import static referencia.domain.enumeracoes.StatusCredencialEnum.LOGIN_JA_EXISTE;
import static referencia.domain.enumeracoes.StatusCredencialEnum.SENHAS_DIFERENTES;
import static referencia.domain.enumeracoes.StatusCredencialEnum.SENHA_INCORRETA;
import static referencia.domain.enumeracoes.StatusCredencialEnum.SENHA_INVALIDA;
import static referencia.domain.enumeracoes.StatusCredencialEnum.VALIDO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import junit.framework.TestCase;
import referencia.application.builder.UsuarioBuilder;
import referencia.application.implementations.NovoUsuarioApplication;
import referencia.application.implementations.UsuarioExistenteApplication;
import referencia.application.interfaces.ICredenciaisApplication;
import referencia.domain.entities.Usuario;
import referencia.domain.enumeracoes.StatusCredencialEnum;
import referencia.domain.interfaces.repository.IUserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioApplicationTest extends TestCase {

    private static final String LOGIN_CURTO = "1234";

    private static final String LOGIN = "123456";

    @InjectMocks
    private NovoUsuarioApplication novoUsuarioApplication;

    @Mock
    private ICredenciaisApplication credenciaisApplication;

    @InjectMocks
    private UsuarioExistenteApplication usuarioExistenteApplication;

    @Mock
    private IUserRepository repo;

    private Map<String, Object> map;

    @Before
    public void beforeTest() {

        map = new HashMap<>();
    }

    @Test
    public void testValidarSemPreencherDados() {

        Usuario usuario = new UsuarioBuilder().build();

        StatusCredencialEnum status = novoUsuarioApplication.validar(usuario);

        assertEquals(INVALIDO, status);
    }

    @Test
    public void testValidarPreencherSomenteLogin() {

        Usuario usuario = new UsuarioBuilder().comLogin(LOGIN).build();

        StatusCredencialEnum status = novoUsuarioApplication.validar(usuario);

        assertEquals(INVALIDO, status);
    }

    @Test
    public void testValidarPreencherSomenteLoginSenha() {

        Usuario usuario = new UsuarioBuilder().comLogin(LOGIN).comSenha(LOGIN).build();

        StatusCredencialEnum status = novoUsuarioApplication.validar(usuario);

        assertEquals(INVALIDO, status);
    }

    @Test
    public void testValidarPreencherSomenteLoginSenhaNome() {

        Usuario usuario = new UsuarioBuilder().comLogin(LOGIN).comSenha(LOGIN).comNome(LOGIN).build();

        StatusCredencialEnum status = novoUsuarioApplication.validar(usuario);

        assertEquals(INVALIDO, status);
    }

    @Test
    public void testValidarLoginInvalido() {

        Usuario usuario = new UsuarioBuilder().comLogin(LOGIN_CURTO).comSenha(LOGIN).comNome(LOGIN).comConfirmacaoDeSenha(LOGIN).build();

        StatusCredencialEnum status = novoUsuarioApplication.validar(usuario);

        assertEquals(LOGIN_INVALIDO, status);
    }

    @Test
    public void testValidarSenhaInvalida() {

        Usuario usuario = new UsuarioBuilder().comLogin(LOGIN).comSenha(LOGIN_CURTO).comNome(LOGIN).comConfirmacaoDeSenha(LOGIN).build();

        StatusCredencialEnum status = novoUsuarioApplication.validar(usuario);

        assertEquals(SENHA_INVALIDA, status);
    }

    @Test
    public void testValidarSenhasDiferentes() {

        Usuario usuario = new UsuarioBuilder().comLogin(LOGIN).comSenha(LOGIN).comNome(LOGIN).comConfirmacaoDeSenha("1234567").build();

        StatusCredencialEnum status = novoUsuarioApplication.validar(usuario);

        assertEquals(SENHAS_DIFERENTES, status);
    }

    @Test
    public void testValidarLoginExistente() {

        Usuario usuario = new UsuarioBuilder().comLogin(LOGIN).comSenha(LOGIN).comNome(LOGIN).comConfirmacaoDeSenha(LOGIN).build();

        List<Usuario> usuarios = new ArrayList<>();

        usuarios.add(usuario);

        map.put("login", usuario.getLogin());

        Mockito.when(repo.getMany("User.buscarPorLogin", map)).thenReturn(usuarios);

        StatusCredencialEnum status = novoUsuarioApplication.validar(usuario);

        assertEquals(LOGIN_JA_EXISTE, status);
    }

    @Test
    public void testValidarAlteracaoSemPreencherDados() {

        Usuario usuario = new UsuarioBuilder().comId(1).build();

        StatusCredencialEnum status = usuarioExistenteApplication.validar(usuario);

        assertEquals(INVALIDO, status);
    }

    @Test
    public void testValidarAlteracaoPreencherSomenteNome() {

        Usuario usuario = new UsuarioBuilder().comId(1).comNome(LOGIN).build();

        StatusCredencialEnum status = usuarioExistenteApplication.validar(usuario);

        assertEquals(INVALIDO, status);
    }

    @Test
    public void testValidarAlteracaoPreencherSomenteNomeSenha() {

        Usuario usuario = new UsuarioBuilder().comId(1).comNome(LOGIN).comSenha(LOGIN).build();

        StatusCredencialEnum status = usuarioExistenteApplication.validar(usuario);

        assertEquals(INVALIDO, status);
    }

    @Test
    public void testValidarAlteracaoPreencherSomenteNomeSenhaNovaSenha() {

        Usuario usuario = new UsuarioBuilder().comId(1).comNome(LOGIN).comSenha(LOGIN).comNovaSenha(LOGIN).build();

        StatusCredencialEnum status = usuarioExistenteApplication.validar(usuario);

        assertEquals(INVALIDO, status);
    }

    @Test
    public void testValidarAlteracaoSenhaInvalida() {

        Usuario usuario = new UsuarioBuilder().comId(1).comNome(LOGIN).comSenha(LOGIN).comNovaSenha(LOGIN_CURTO).comConfirmacaoDeSenha(LOGIN).build();

        StatusCredencialEnum status = usuarioExistenteApplication.validar(usuario);

        assertEquals(SENHA_INVALIDA, status);
    }

    @Test
    public void testValidarAlteracaoSenhaIncorreta() {

        Usuario usuario = new UsuarioBuilder().comId(1).comSenha("1234567").comNome(LOGIN).comNovaSenha(LOGIN).comConfirmacaoDeSenha(LOGIN).build();

        List<Usuario> usuarios = new ArrayList<>();

        map.put("login", usuario.getLogin());

        Mockito.when(repo.getMany("User.buscarPorLogin", map)).thenReturn(usuarios);

        StatusCredencialEnum status = usuarioExistenteApplication.validar(usuario);

        assertEquals(SENHA_INCORRETA, status);
    }

    @Test
    public void testValidarAlteracaoSenhasDiferentes() {

        Usuario usuario = new UsuarioBuilder().comId(1).comSenha(LOGIN).comNome(LOGIN).comNovaSenha(LOGIN).comConfirmacaoDeSenha("1234567").build();

        StatusCredencialEnum status = usuarioExistenteApplication.validar(usuario);

        assertEquals(SENHAS_DIFERENTES, status);
    }

    @Test
    public void testValidarAlteracaoLoginExistente() {

        Usuario usuario = new UsuarioBuilder().comId(1).comNome(LOGIN).comSenha(LOGIN).comNovaSenha(LOGIN + LOGIN_CURTO).comConfirmacaoDeSenha(LOGIN + LOGIN_CURTO).build();

        List<Usuario> usuarios = new ArrayList<>();

        usuarios.add(usuario);

        map.put("login", usuario.getLogin());

        Mockito.when(credenciaisApplication.verificarCredenciais(usuario)).thenReturn(usuarios);

        StatusCredencialEnum status = usuarioExistenteApplication.validar(usuario);

        assertEquals(VALIDO, status);
    }
}
