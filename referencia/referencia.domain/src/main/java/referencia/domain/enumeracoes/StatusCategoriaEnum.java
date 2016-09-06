package referencia.domain.enumeracoes;

import lombok.Getter;

public enum StatusCategoriaEnum {

    VALIDA("", ""), SALVA("Categoria salva com sucesso;", "sucesso"), ERRO("Erro ao salvar categoriao", "erro"), INVALIDA("Preencha os campos obrigatórios", "erro"), 
    ERRO_EXCLUSAO("Ocorreu algum erro ao tentar excluir essa categoria.", "erro"), EXCLUIDA("Categoria excluída com sucesso", "sucesso"), EXISTENTE("Já existe uma categoria com essa descrição", "erro"), ERRO_OPERACAO("Ocorreu algum erro ao realizar essa operação", "erro"), CATEGORIA_COM_PRODUTOS("Não é possível excluir essa categoria, pois há produtos associados à mesma", "erro");

    @Getter
    private String feedback;

    @Getter
    private String tipoFeedback;

    private StatusCategoriaEnum(String feedback, String tipoFeedback) {

        this.feedback = feedback;

        this.tipoFeedback = tipoFeedback;
    }

    public static StatusCategoriaEnum buscar(String statusOperacao) {

        for(StatusCategoriaEnum status: StatusCategoriaEnum.values()){
            
            if(status.getFeedback().equals(statusOperacao)){
                
                return status;
            }
        }
        
        return ERRO_OPERACAO;
    }
}
