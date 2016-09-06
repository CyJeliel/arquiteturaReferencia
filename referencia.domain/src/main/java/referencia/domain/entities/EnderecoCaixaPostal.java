package referencia.domain.entities;

import org.apache.commons.lang3.StringUtils;

import prodespcep.dominio.cep.dto.CepProcDTO;

public class EnderecoCaixaPostal extends Endereco {

    @Override
    public void aplicarCep(CepProcDTO cepProcDTO) {

        setCep(Integer.valueOf(cepProcDTO.getCep()));

        setComplemento(cepProcDTO.getComplemento());

        setCidade(cepProcDTO.getLocalidade());

        setEstado(cepProcDTO.getEstado());

        setUf(cepProcDTO.getUf());

        String enderecoSemDescricao = aplicarDescricao(cepProcDTO.getEndereco());

        String enderecoSemNumero = aplicarNumero(enderecoSemDescricao);

        aplicarBairro(enderecoSemNumero);

        setComplementoBloqueado(true);
    }

    private String aplicarDescricao(String endereco) {

        int indiceSeparadorInicial = endereco.indexOf(",");

        if (indiceSeparadorInicial < 0) {

            indiceSeparadorInicial = endereco.indexOf("nº");
        }

        String descricao = endereco.substring(0, indiceSeparadorInicial);

        setDescricao(descricao);

        setDescricaoBloqueada(true);

        return endereco.substring(indiceSeparadorInicial + 1);
    }

    private String aplicarNumero(String endereco) {

        int indiceSeparadorFinal = endereco.indexOf("-");

        if (indiceSeparadorFinal < 0) {

            indiceSeparadorFinal = endereco.length();
        }

        String numero = endereco.substring(0, indiceSeparadorFinal).replace("º", "").trim();

        try {

            Integer.parseInt(numero);

        } catch (NumberFormatException e) {

            numero = "sem número";
        }

        setNumero(numero);

        setNumeroBloqueado(true);

        String enderecoParse = "";
        
        if(indiceSeparadorFinal + 1 < endereco.length()){
            
            enderecoParse = endereco.substring(indiceSeparadorFinal + 1);
        }
        
        return enderecoParse;
    }

    private void aplicarBairro(String endereco) {

        int indiceSeparadorFinal = endereco.indexOf("-");

        if (indiceSeparadorFinal < 0) {

            indiceSeparadorFinal = endereco.length();
        }

        String bairro = endereco.substring(0, indiceSeparadorFinal);

        bairro = bairro.replace("Bairro ", "");

        if (StringUtils.isBlank(bairro)) {

            setBairroBloqueado(false);
            
        } else {
            
            setBairroBloqueado(true);
        }

        setBairro(bairro);
    }
}
