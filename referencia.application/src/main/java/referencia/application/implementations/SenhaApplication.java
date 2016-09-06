package referencia.application.implementations;

import static referencia.domain.enumeracoes.StatusCredencialEnum.INVALIDO;
import static referencia.domain.enumeracoes.StatusCredencialEnum.SENHAS_DIFERENTES;
import static referencia.domain.enumeracoes.StatusCredencialEnum.SENHA_INVALIDA;
import static referencia.domain.enumeracoes.StatusCredencialEnum.VALIDO;

import lombok.Data;
import referencia.domain.enumeracoes.StatusCredencialEnum;

@Data
public abstract class SenhaApplication {

    private String senha;

    private String confirmacaoDeSenha;

    protected SenhaApplication(String senha, String confirmacaoDeSenha) {

        this.senha = senha;

        this.confirmacaoDeSenha = confirmacaoDeSenha;
    }

    public StatusCredencialEnum validarSenhaConfirmacao() {

        StatusCredencialEnum status = VALIDO;

        if (getSenha().length() < 5) {

            status = SENHA_INVALIDA;

        } else if (!senha.equals(confirmacaoDeSenha)) {

            status = SENHAS_DIFERENTES;
        }

        return status;
    }

    public StatusCredencialEnum validarSenha() {

        if (validarTodosCamposPreenchidos()) {

            return validarSenhaConfirmacao();
        }

        return INVALIDO;
    }

    public abstract boolean validarTodosCamposPreenchidos();
}
