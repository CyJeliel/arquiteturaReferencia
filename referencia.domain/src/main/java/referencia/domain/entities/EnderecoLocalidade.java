package referencia.domain.entities;

import prodespcep.dominio.cep.dto.CepProcDTO;

public class EnderecoLocalidade extends Endereco {

    @Override
    public void aplicarCep(CepProcDTO cepProcDTO) {
        
        setCep(Integer.valueOf(cepProcDTO.getCep()));
        
        setCidade(cepProcDTO.getLocalidade());
        
        setEstado(cepProcDTO.getEstado());
        
        setUf(cepProcDTO.getUf());
        
        setDescricaoBloqueada(false);
        
        setNumeroBloqueado(false);
        
        setComplementoBloqueado(false);
        
        setBairroBloqueado(false);
    }
}
