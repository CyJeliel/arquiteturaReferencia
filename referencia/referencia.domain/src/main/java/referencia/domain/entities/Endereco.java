package referencia.domain.entities;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import prodespcep.dominio.cep.dto.CepProcDTO;

@ToString
@EqualsAndHashCode(of = "cep")
@XmlRootElement
public abstract class Endereco {

    @Getter
    @Setter
    private Integer cep;

    @Setter
    @Getter
    private String descricao;

    @Getter
    @Setter
    private String bairro;

    @Setter
    @Getter
    private String tipoLogradouro;

    @Getter
    @Setter
    private String cidade;

    @Getter
    @Setter
    private String estado;

    @Getter
    @Setter
    private String uf;

    @Setter
    @Getter
    private String numero;

    @Setter
    @Getter
    private String complemento;

    @Setter
    private boolean descricaoBloqueada;

    @Setter
    private boolean numeroBloqueado;

    @Setter
    private boolean complementoBloqueado;

    @Setter
    private boolean bairroBloqueado;

    public abstract void aplicarCep(CepProcDTO cepProcDTO);

    public boolean isDescricaoBloqueada() {

        return descricaoBloqueada;
    }

    public boolean isNumeroBloqueado() {

        return numeroBloqueado;
    }

    public boolean isComplementoBloqueado() {

        return complementoBloqueado;
    }

    public boolean isBairroBloqueado() {

        return bairroBloqueado;
    }

    public boolean isValido() {

        boolean valido = true;

        if (!bairroBloqueado || !descricaoBloqueada) {

            valido = isNotBlank(bairro) && isNotBlank(descricao);
        }

        return valido;
    }
}
