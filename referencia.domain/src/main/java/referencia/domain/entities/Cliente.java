package referencia.domain.entities;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode(of = "id")
@XmlRootElement
public class Cliente {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String nome;

    @Getter
    @Setter
    private String enderecoDescricao;

    @Getter
    @Setter
    private String bairro;

    @Getter
    @Setter
    private String enderecoComplemento;

    @Getter
    @Setter
    private Integer cep;

    @Getter
    @Setter
    private String telefone;

    @Getter
    @Setter
    private String email;

    @Getter
    private Endereco endereco;

    public void atualizaEndereco() {

        this.cep = endereco.getCep();

        this.enderecoComplemento = endereco.getNumero() + " " + endereco.getComplemento();

        if (!endereco.isDescricaoBloqueada()) {

            this.enderecoDescricao = endereco.getDescricao();

        } else {

            this.enderecoDescricao = null;
        }

        if (!endereco.isBairroBloqueado()) {

            this.bairro = endereco.getBairro();

        } else {

            this.bairro = null;
        }
    }

    public void setEndereco(Endereco endereco) {

        if (this.enderecoDescricao != null && isNotBlank(enderecoDescricao)) {

            endereco.setDescricao(enderecoDescricao);

        }

        if (bairro != null && isNotBlank(bairro)) {

            endereco.setBairro(bairro);
        }

        this.endereco = endereco;
    }
}
