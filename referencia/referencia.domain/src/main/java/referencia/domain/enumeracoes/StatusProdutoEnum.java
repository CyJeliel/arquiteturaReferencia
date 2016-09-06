package referencia.domain.enumeracoes;

import lombok.Getter;

public enum StatusProdutoEnum {

    VALIDO("", ""), SALVO("Produto salvo com sucesso", "sucesso"), ERRO("Erro ao salvar produto", "erro"), INVALIDO("Preencha os campos obrigatórios", "erro"), ERRO_EXCLUSAO("Ocorreu algum erro ao tentar excluir esse produto.", "erro"), EXCLUIDO(
            "Produto excluído com sucesso", "sucesso"), ESTOQUE_NEGATIVO("O estoque não pode ser negativo", "erro"), PRECO_NEGATIVO("O preço não pode ser negativo", "erro"), EXISTENTE("Já existe um produto com essa descrição", "erro"), ERRO_OPERACAO("Ocorreu algum erro ao realizar essa operação", "erro");

    @Getter
    private String feedback;

    @Getter
    private String tipoFeedback;

    private StatusProdutoEnum(String feedback, String tipoFeedback) {

        this.feedback = feedback;

        this.tipoFeedback = tipoFeedback;
    }

    public static StatusProdutoEnum buscar(String statusOperacao) {

        for (StatusProdutoEnum status : StatusProdutoEnum.values()) {

            if (status.getFeedback().equals(statusOperacao)) {

                return status;
            }
        }

        return ERRO_OPERACAO;
    }
}
