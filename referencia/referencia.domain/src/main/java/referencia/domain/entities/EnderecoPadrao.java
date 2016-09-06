package referencia.domain.entities;

import prodespcep.dominio.cep.dto.CepProcDTO;

public class EnderecoPadrao extends Endereco {

    @Override
    public void aplicarCep(CepProcDTO cepProcDTO) {
        
        setBairro(cepProcDTO.getBairro());
        
        setCep(Integer.valueOf(cepProcDTO.getCep()));
        
        setDescricao(cepProcDTO.getTipoLogradouroAbrev() + " " + cepProcDTO.getEndereco());
        
        setCidade(cepProcDTO.getLocalidade());
        
        setEstado(cepProcDTO.getEstado());
        
        setUf(cepProcDTO.getUf());
        
        setDescricaoBloqueada(true);
        
        setNumeroBloqueado(false);
        
        setComplementoBloqueado(false);
        
        setBairroBloqueado(true);
    }
}
