package referencia.domain.enumeracoes;

import lombok.Getter;

public enum StatusCredencialEnum {

    INVALIDO("Preencha todos os campos", "erro"), SENHAS_DIFERENTES("A senha e a confirmação não conferem", "erro"), VALIDO("Usuário salvo com sucesso", "sucesso"), LOGIN_INVALIDO("O login deve conter pelo menos 5 caracteres",
            "erro"), SENHA_INVALIDA("A senha deve conter pelo menos 6 caracteres", "erro"), SENHA_INCORRETA("Senha informada não confere", "erro"),LOGIN_JA_EXISTE("Login já existe no sistema", "erro"), ERRO("Ocorreu algum erro ao registrar o usuário.", "erro");

    @Getter
    private String feedback;

    @Getter
    private String tipoFeedback;

    private StatusCredencialEnum(String feedback, String tipoFeedback) {

        this.feedback = feedback;

        this.tipoFeedback = tipoFeedback;
    }
}
