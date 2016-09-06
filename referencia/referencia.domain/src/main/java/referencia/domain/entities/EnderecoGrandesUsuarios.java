package referencia.domain.entities;

import prodespcep.dominio.cep.dto.CepProcDTO;

public class EnderecoGrandesUsuarios extends Endereco {

    @Override
    public void aplicarCep(CepProcDTO cepProcDTO) {
        
        setBairro(cepProcDTO.getBairro());
        
        setCep(Integer.valueOf(cepProcDTO.getCep()));
        
        int indiceEspaco = cepProcDTO.getEndereco().lastIndexOf(" ");
        
        String numero = cepProcDTO.getEndereco().substring(indiceEspaco + 1);
        
        String descricao = cepProcDTO.getEndereco().substring(0, indiceEspaco);
                
        setDescricao(descricao);
        
        setNumero(numero);
        
        setCidade(cepProcDTO.getLocalidade());
        
        setEstado(cepProcDTO.getEstado());
        
        setUf(cepProcDTO.getUf());
        
        setComplemento(cepProcDTO.getComplemento());
        
        setDescricaoBloqueada(true);
        
        setNumeroBloqueado(true);
        
        setComplementoBloqueado(true);
        
        setBairroBloqueado(true);
    }
}
