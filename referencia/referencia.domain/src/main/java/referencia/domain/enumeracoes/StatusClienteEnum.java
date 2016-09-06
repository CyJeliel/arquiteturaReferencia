package referencia.domain.enumeracoes;

import lombok.Getter;

public enum StatusClienteEnum {

    VALIDO("", ""), SALVO("Cliente salvo com sucesso;", "sucesso"), ERRO("Erro ao salvar cliente", "erro"), INVALIDO("Preencha os campos obrigatórios", "erro"), EXISTENTE("Já existe um cliente com esse nome.", "erro"),
    ERRO_EXCLUSAO("Ocorreu algum erro ao tentar excluir esse cliente.", "erro"), EXCLUIDO("Cliente excluído com sucesso", "sucesso"), ERRO_OPERACAO("Ocorreu algum erro ao realizar essa operação", "erro");

    @Getter
    private String feedback;

    @Getter
    private String tipoFeedback;

    private StatusClienteEnum(String feedback, String tipoFeedback) {

        this.feedback = feedback;

        this.tipoFeedback = tipoFeedback;
    }

    public static StatusClienteEnum buscar(String statusOperacao) {

        for(StatusClienteEnum status: StatusClienteEnum.values()){
            
            if(status.getFeedback().equals(statusOperacao)){
                
                return status;
            }
        }
        
        return ERRO_OPERACAO;
    }
}
