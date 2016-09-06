package referencia.application.implementations.transformers;

import prodespcep.dominio.cep.dto.CepProcDTO;
import referencia.domain.entities.Endereco;
import referencia.domain.entities.EnderecoCaixaPostal;
import referencia.domain.entities.EnderecoGrandesUsuarios;
import referencia.domain.entities.EnderecoLocalidade;
import referencia.domain.entities.EnderecoPadrao;

public class EnderecoTransformer {

    public Endereco paraEndereco(CepProcDTO cepProcDTO) {

        String tipoCep = cepProcDTO.getTipoCEP();

        Endereco endereco = null;

        switch (tipoCep) {
            case "CEP Padrão":

                endereco = new EnderecoPadrao();

                break;

            case "Grande Usuário":

            case "Unidade Operacional":

                endereco = new EnderecoGrandesUsuarios();

                break;
            case "Caixa Postal Comunitária":

                endereco = new EnderecoCaixaPostal();

                break;
            case "Localidade":

                endereco = new EnderecoLocalidade();

                break;
            default:
                break;
        }

        endereco.aplicarCep(cepProcDTO);

        return endereco;
    }

}
